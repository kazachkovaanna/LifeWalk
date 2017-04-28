package spblife.walk.place;

import java.util.List;

/**
 * Created by kazac on 28.04.2017.
 */
public class Place {
    private double lat;
    private double lon;
    private String name;
    private String shortDescription;
    private String longDescription;
    private List<String> objectList;
    private List<String> categoriesList;

    public Place(double lat, double lon, String name, String shortDescription, String longDescription, List<String> objectList, List<String> categoriesList) {
        this.lat = lat;
        this.lon = lon;
        this.name = name;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.objectList = objectList;
        this.categoriesList = categoriesList;
    }

    public Place() {
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public List<String> getObjectList() {
        return objectList;
    }

    public void setObjectList(List<String> objectList) {
        this.objectList = objectList;
    }

    public List<String> getCategoriesList() {
        return categoriesList;
    }

    public void setCategoriesList(List<String> categoriesList) {
        this.categoriesList = categoriesList;
    }
}
