package spblife.walk;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import ru.yandex.yandexmapkit.MapController;
import ru.yandex.yandexmapkit.MapView;
import ru.yandex.yandexmapkit.OverlayManager;
import ru.yandex.yandexmapkit.overlay.Overlay;
import ru.yandex.yandexmapkit.overlay.OverlayItem;
import ru.yandex.yandexmapkit.overlay.balloon.BalloonItem;
import ru.yandex.yandexmapkit.utils.GeoPoint;
import spblife.walk.nearby.BalloonListener;
import spblife.walk.settings.Settings;

public class NearbyActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Settings settings;
    MapController mapController;
    OverlayManager overlayManager;
    BalloonListener balloonListener;

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

        //Настройки
        settings = new Settings(getSharedPreferences(".settings", Context.MODE_PRIVATE));

        //Работа с картой
        MapView mapView = (MapView) findViewById(R.id.map);
        mapController = mapView.getMapController();
        overlayManager = mapController.getOverlayManager();
        overlayManager.getMyLocation().setEnabled(true);
        balloonListener = new BalloonListener();
        //ну и поработать с ней
        go();
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
        int id = item.getItemId();

        if (id == R.id.nearby) {
            // Handle the camera action
        } else if (id == R.id.what) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private  void go(){
        //Создать  слой для работы с объектами
        Resources res = getResources();
        Overlay overlay = new Overlay(mapController);
        OverlayItem overlayItem = new OverlayItem(new GeoPoint(59.987313, 30.370817),res.getDrawable(R.drawable.place, getTheme()));
        BalloonItem balloonItem = new BalloonItem(this,overlayItem.getGeoPoint());
        balloonItem.setOnBalloonListener(balloonListener);
        overlay.addOverlayItem(overlayItem);
        overlayManager.addOverlay(overlay);
        //Перемещение карты из Москвы в Положение точки на карте
        mapController.setPositionAnimationTo(overlayItem.getGeoPoint());
        //Масштабирование
        /*double maxLat, minLat, maxLon, minLon;
        maxLat = maxLon = Double.MIN_VALUE;
        minLat = minLon = Double.MAX_VALUE;
        GeoPoint geoPoint = overlayItem.getGeoPoint();
        double lat = geoPoint.getLat();
        double lon = geoPoint.getLon();

        maxLat = Math.max(lat, maxLat);
        minLat = Math.min(lat, minLat);
        maxLon = Math.max(lon, maxLon);
        minLon = Math.min(lon, minLon);

        mapController.setZoomToSpan(-10, -10);*/
    }
}
