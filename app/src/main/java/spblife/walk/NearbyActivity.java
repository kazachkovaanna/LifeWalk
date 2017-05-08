package spblife.walk;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ru.yandex.yandexmapkit.MapController;
import ru.yandex.yandexmapkit.MapView;
import ru.yandex.yandexmapkit.OverlayManager;
import ru.yandex.yandexmapkit.overlay.Overlay;
import ru.yandex.yandexmapkit.overlay.OverlayItem;
import ru.yandex.yandexmapkit.overlay.balloon.BalloonItem;
import ru.yandex.yandexmapkit.utils.GeoPoint;
import spblife.walk.nearby.BalloonListener;
import spblife.walk.place.Place;
import spblife.walk.place.PlaceFabric;
import spblife.walk.settings.Settings;

public class NearbyActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Settings settings;
    MapController mapController;
    OverlayManager overlayManager;
    BalloonListener balloonListener;
    MapView mapView;
    private static PlaceFabric placeFabric = new PlaceFabric();
    boolean nearMeMode;
    private static final int PERMISSIONS_CODE = 109;
    private List<Overlay> overlays;
    private Overlay placeOverlay;

    LinearLayout mapLayout;

    private GeoPoint nearPoint;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

       // mapLayout = (LinearLayout) findViewById(R.id.mapLinearView);

        //Настройки
        settings = Settings.getSettings(getSharedPreferences("spblife.walk_preferences", Context.MODE_PRIVATE));

        //Работа с картой
        mapView = (MapView) findViewById(R.id.map);
        mapController = mapView.getMapController();
        overlayManager = mapController.getOverlayManager();
        balloonListener = new BalloonListener();
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        placeOverlay = new Overlay(mapController);

        overlays = new ArrayList<>();
        nearPoint = null;
        //ну и поработать с ней
        //initMap();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Intent intent;
        int id = item.getItemId();

        if (id == R.id.nearby) {
            Settings.getSettings().setNearMeMode(true);
            Settings.getSettings().save();
            initMap();

        } else if (id == R.id.what) {
            Settings.getSettings().setNearMeMode(false);
            Settings.getSettings().save();
            initMap();
        }
        else if (id== R.id.distance){
            intent=new Intent(this, SettingsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            this.startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        initMap();
    }

    private LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            nearPoint = new GeoPoint(location.getLatitude(), location.getLongitude());
            initMap();
        }

        @Override
        public void onProviderDisabled(String provider) {
            //checkEnabled();
        }

        @Override
        public void onProviderEnabled(String provider) {
            //checkEnabled();
            //showLocation(locationManager.getLastKnownLocation(provider));
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
    };

    private void initMap(){
        nearMeMode = Settings.getSettings().getNearMeMode();
        TextView textView = (TextView) findViewById(R.id.text_places);
        textView.setText("");
        mapView.showFindMeButton(true);
        //Создать  слой для работы с объектами
       placeOverlay.clearOverlayItems();
        int dist =  Settings.getSettings().getDistance();
        if(nearMeMode){
            checkPermission();
            //mapView.showBuiltInScreenButtons(true);
            overlayManager.getMyLocation().setEnabled(true);

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    0, 10, locationListener);
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, 0, 10,
                    locationListener);
            Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(nearPoint != null) {
                if (lastLocation != null)
                    nearPoint = new GeoPoint(lastLocation.getLatitude(), lastLocation.getLongitude());
                overlayManager.getMyLocation().findMe();
                overlayManager.getMyLocation().setVisible(true);
                mapView.showFindMeButton(true);
                placeFabric.getNearPlaces(nearPoint.getLat(), nearPoint.getLon(), dist, this);
            }
        }
        else{
            overlayManager.getMyLocation().setEnabled(false);
            nearPoint = new GeoPoint(59.92057786922, 30.336764);
            placeFabric.getNearPlaces(nearPoint.getLat(), nearPoint.getLon(),dist, this);
        }


    }


    public synchronized void showPlaces(List<Place> places){
        placeOverlay.clearOverlayItems();
        //Создать  слой для работы с объектами
        Resources res = getResources();
        //placeOverlay = new Overlay(mapController);
        TextView textView = (TextView) findViewById(R.id.text_places);
        textView.setText("");
//        Log.d("text view",textView.getText().toString());

        for(Place p : places){
            OverlayItem overlayItem = new OverlayItem(new GeoPoint(Math.toDegrees(p.getLat()), Math.toDegrees(p.getLon())),res.getDrawable(R.drawable.ic_place, getTheme()));
            BalloonItem balloonItem = new BalloonItem(this,overlayItem.getGeoPoint());
            balloonItem.setOnBalloonListener(balloonListener);
            placeOverlay.addOverlayItem(overlayItem);
            overlayManager.addOverlay(placeOverlay);
            textView.append(p.getName());
            textView.append("\n");
            textView.append(p.getShortDescription());
            textView.append("\n");
            textView.append(p.getLongDescription());
        }

        if(!nearMeMode){
            OverlayItem overlayItem = new OverlayItem(nearPoint,res.getDrawable(R.drawable.ic_where, getTheme()));
            BalloonItem balloonItem = new BalloonItem(this,overlayItem.getGeoPoint());
            balloonItem.setOnBalloonListener(balloonListener);
            placeOverlay.addOverlayItem(overlayItem);
            overlayManager.addOverlay(placeOverlay);
        }

        //mapController.setZoomCurrent(17);

        //Отобразить места в списке

        //Масштабирование

        List<GeoPoint> gplist = new ArrayList<>();
        if(nearPoint!= null) gplist.add(nearPoint);
        for(Place p : places){
            gplist.add(new GeoPoint(Math.toDegrees(p.getLat()), Math.toDegrees(p.getLon())));
        }
        double maxLat, minLat, maxLon, minLon, avgLat, avgLon;
        maxLat = maxLon = Double.MIN_VALUE;
        minLat = minLon = Double.MAX_VALUE;
        avgLat = avgLon = 0;
        for (GeoPoint gp : gplist){
            double lat = gp.getLat();
            double lon = gp.getLon();
            avgLat+=gp.getLat();
            avgLon+=gp.getLon();

            maxLat = Math.max(lat, maxLat);
            minLat = Math.min(lat, minLat);
            maxLon = Math.max(lon, maxLon);
            minLon = Math.min(lon, minLon);
        }
        if(gplist.size() > 0) {
            avgLat /= gplist.size();
            avgLon /= gplist.size();
        }
        else{
            avgLat = nearPoint.getLat();
            avgLon = nearPoint.getLon();
        }
        mapController.setZoomToSpan(maxLat - minLat, maxLon - minLon);
        //mapController.setZoomCurrent(16);
        //mapController.setZoomToSpan();
        mapController.setPositionAnimationTo(new GeoPoint(avgLat, avgLon));

//        mapController.setPositionAnimationTo(nearPoint);

    }

    private void checkPermission() {
        int permACL = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int permAFL = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        if (permACL != PackageManager.PERMISSION_GRANTED ||
                permAFL != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_CODE);
        }

    }
}
