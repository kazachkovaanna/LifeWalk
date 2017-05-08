package spblife.walk.place;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import spblife.walk.NearbyActivity;
import spblife.walk.settings.Settings;

/**
 * Created by kazac on 28.04.2017.
 */
public class PlaceFabric {
    public List<String> allObjects;
    public List<String> allCategories;
    public List<Place>  places;

    //URL по которому можно получиь json с местами
    private static String urlString = "http://192.168.100.14/lifewalk/places.php";
    //тэги json
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PLACES = "places";
    private static final String TAG_LAT = "lat";
    private static final String TAG_LON = "lon";
    private static final String TAG_DIST = "dist";
    private static final String TAG_OLIST = "objectList";
    private static final String TAG_CLIST = "categoriesList";
    private static final String TAG_SHORT = "shortDescription";
    private static final String TAG_LONG = "longDescription";
    private static final String TAG_NAME = "name";
    private static final String TAG_ADDRESS = "address";
    private double lat, lon;
    private int dist;

    //Массив, пришедший в json
    JSONArray jplaces = null;

    public PlaceFabric(){
        //ПОКА ЧТО ТУТ ГЕНЕРИРУЮТСЯ МЕСТА
        //В БУДУЩЕМ - УСТАНОВКА СОЕДИНЕНИЯ С СЕРВЕРО МЕСТ!!!!
        //ТО ЖЕ КАСАЕТСЯ СПИСКОВ КАТЕГОРИЙ И ОБЪЕКТОВ
        allObjects = new ArrayList<>();
        allCategories = new ArrayList<>();
        places = new ArrayList<>();
        /*allObjects.add("Дом");
        allObjects.add("Памятник");
        allObjects.add("Двор");

        allCategories.add("Кирпич");
        allCategories.add("Церковь");
        allCategories.add("Сказка");


        //Одно место рядом со мной
        //Артилеррийская лаборатория
        Place place = new Place();
        place.setLat(59.986193);
        place.setLon(30.367601);
        place.setName("Артилеррийская лаборатория");
        place.setShortDescription("Ансабль из восьми зданийй был построен в 1841–1853 годах.");
        place.setLongDescription("До революции здесь находились Артиллерийская лаборатория и Петербургский окружной склад огнестрельных припасов. Краснокирпичный комплекс был построен в 1841–1853 годах по проекту академиков архитекторы Романа Генрихсена и Александра Гемилиана. В квартале много более поздних построек. В последнее время территория находилась в ведении ФГУП «755 Артиллерийский ремонтный завод», потом земля была продана.\n" +
                "\n" +
                "Распоряжением комитета по охране памятников от 2009 года статус регионального памятника архитектуры получил ансамбль из восьми зданий, сгруппированный в юго-восточной части квартала, в том числе здание канцелярии со звонницей. Также под охраной находится один из прудов.");
        ArrayList<String> olist = new ArrayList<>();
        olist.add("Дом");
        ArrayList<String> clist = new ArrayList<>();
        clist.add("Кирпич");
        place.setCategoriesList(clist);
        place.setObjectList(olist);
        places.add(place);

        //Два - в центре, рядом с заданным адресом
        place = new Place();
        place.setName("Три ангела");
        place.setShortDescription("«Три ангела» - памятник разрушенным церквям Петербурга");
        place.setLongDescription("Скульптуры установили в память о трех церквях, находившихся поблизости: церкви Иоанна Предтечи, церкви Святого Александра Невского при училище Синода и церкви Преображения Господня. Здания, в которых находились церкви, можно опознать по стрельчатым окнам и \"неправильному\" виду крыш. Под это описание подходит и здание Университета кино и телевидения. Обратите вниание на странную форму окон, остатки колокольни , а так же \"замурованное\" алтарное окно. Да, действительно, это и есть одна из трех бывших церквей - Святого Александра Невского.");
        olist = new ArrayList<>();
        olist.add("Памятник");
        place.setObjectList(olist);
        clist = new ArrayList<>();
        clist.add("Церковь");
        place.setCategoriesList(clist);
        place.setLat(59.922835);
        place.setLon(30.341612);
        places.add(place);

        place = new Place();
        place.setName("Изумрудный город");
        place.setShortDescription("Изумрудный город - страна чудес Петербурга");
        place.setLongDescription("Совсем не далеко от метро Маяковская, во дворах домов 2-8 по улице правды, расположился Изумрудный Город. Персонажи культовой сказки буквально заполонили собою тихий дворик: на стене дома номер 4 засела старуха Гингема, чуть дальше, у забора между домами 4 и 6 поселился людоед, а в небольшом скверике между домами 6 и 8 гордо обитают железный человек и страшила.\n" +
                "\n" +
                "Интересно заметить, что дорожки во дворах, как в сказке, вымощены желтым кирпичем. Кроме того, сохранена и хронология событий: например, при входе в первый двор на фасаде размещен барельеф доброй феи Виллины, которая первой повстречалась Элли в Волшебной стране.");
        olist = new ArrayList<>();
        olist.add("Памятник");
        olist.add("Двор");
        place.setObjectList(olist);
        clist = new ArrayList<>();
        clist.add("Сказка");
        place.setCategoriesList(clist);
        place.setLat(59.924269);
        place.setLon(30.344786);
        places.add(place);*/
    }

    public List<String> getAllObjects() {
        return allObjects;
    }

    public List<String> getAllCategories() {
        return allCategories;
    }

    public List<Place> getPlaces() {
        return places;
    }

    private double getDistance(double latf, double lonf, double latto, double lonto){
        latf = Math.toRadians(latf);
        latto = Math.toRadians(latto);
        lonf = Math.toRadians(lonf);
        lonto = Math.toRadians(lonto);
        double dLon = (lonto-lonf);
        //double dLat = Math.abs(latf-latto);
        double delta;
        delta = 2.0 * Math.asin(
                Math.sqrt(
                        Math.pow(
                                Math.sin((latto-latf)/2.0), 2
                        ) +
                        Math.cos(latf) * Math.cos(latto) *Math.pow(
                                Math.sin(dLon/2), 2
                        )
                )
        );
        delta*=6372795;
        return delta;
    }

    public void getNearPlaces(double lat, double lon, int distance, NearbyActivity retActivity){
        places.clear();
        this.lat = Math.toRadians(lat);
        this.lon = Math.toRadians(lon);
        this.dist = distance;
        GetPlaces getPlaces = new GetPlaces(retActivity);

       getPlaces.execute();
    }

    private class GetPlaces extends AsyncTask<String, String, String>{

        HttpURLConnection connection = null;
        BufferedReader reader = null;
        String resultJson="";
        private NearbyActivity retActivity;


        public GetPlaces(NearbyActivity retActivity){
            this.retActivity = retActivity;
        }

        //Поменять код, например показав окошко "загрузка"
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String parameters = new StringBuilder().append("?").append(TAG_LAT).append("=").append(lat).append("&")
                        .append(TAG_LON).append("=").append(lon).append("&")
                        .append(TAG_DIST).append("=").append(dist).toString();
                String ustr = urlString+parameters;
                URL url = new URL(ustr);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                InputStream inputStream = connection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                reader=new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                resultJson = buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return resultJson;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            JSONObject result = null;
            try {
                result = new JSONObject(resultJson);
                jplaces = result.getJSONArray(TAG_PLACES);

                for(int i = 0; i<jplaces.length(); i++){
                    JSONObject place = jplaces.getJSONObject(i);
                    List<String> objectList;
                    String[] objects = place.getString(TAG_OLIST).split(";");
                    objectList = new ArrayList<>();
                    for(String o : objects){
                        objectList.add(o);
                    }
                    List<String> catList = new ArrayList<>();
                    String[] cats = place.getString(TAG_CLIST).split(";");
                    objectList = new ArrayList<>();
                    for(String o : cats){
                        catList.add(o);
                    }
                    Place p = new Place(
                        place.getDouble(TAG_LAT),
                            place.getDouble(TAG_LON),
                            place.getString(TAG_NAME),
                            place.getString(TAG_SHORT),
                            place.getString(TAG_LONG),
                            objectList,
                            catList,
                            place.getString(TAG_ADDRESS)
                    );

                    places.add(p);
                    retActivity.showPlaces(places);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
