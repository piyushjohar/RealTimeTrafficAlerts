package com.alerts.pj.trafficalerts;


public class Locations {

    private int _id;
    private String name;
    private double latitude;
    private double longitude;
    private String descr;
    private String loc_path;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    private String flag;

    public void set_id ( int id ) {
        _id = id;
    }
    public int get_id( ) {
        return _id;
    }

    public void setName ( String Name ) {
        name = Name;
    }
    public String getName( ) {
        return name;
    }

    public void setLatitude( double lat ) {
        latitude = lat;
    }
    public double getLatitude(){
        return latitude;
    }

    public void setLongitude( double lon ) {
        longitude = lon;
    }
    public double getLongitude(){
        return longitude;
    }

    public void setDescr ( String desc ) {
        descr = desc;
    }
    public String getDescr( ) {
        return descr;
    }

    public void setLoc_path ( String path) {
        loc_path = path;
    }
    public String getLoc_path(){
        return loc_path;
    }
}
