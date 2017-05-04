package spblife.walk.place;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import spblife.walk.settings.Settings;

/**
 * Created by kazac on 28.04.2017.
 */
public class PlaceFabric {
    public List<String> allObjects;
    public List<String> allCategories;
    public List<Place>  places;

    public PlaceFabric(){
        //ПОКА ЧТО ТУТ ГЕНЕРИРУЮТСЯ МЕСТА
        //В БУДУЩЕМ - УСТАНОВКА СОЕДИНЕНИЯ С СЕРВЕРО МЕСТ!!!!
        //ТО ЖЕ КАСАЕТСЯ СПИСКОВ КАТЕГОРИЙ И ОБЪЕКТОВ
        allObjects = new ArrayList<>();
        allObjects.add("Дом");
        allObjects.add("Памятник");
        allObjects.add("Двор");
        allCategories = new ArrayList<>();
        allCategories.add("Кирпич");
        allCategories.add("Церковь");
        allCategories.add("Сказка");
        places = new ArrayList<>();

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
        places.add(place);
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

    public List<Place> getNearPlaces(double lat, double lon, int distance){
        List<Place> near = new ArrayList<>();
        for(Place p : places){
            if(getDistance(lat, lon, p.getLat(), p.getLon()) <= distance)
                near.add(p);
        }
        return  near;
    }
}
