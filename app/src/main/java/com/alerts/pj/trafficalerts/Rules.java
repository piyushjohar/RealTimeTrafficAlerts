package com.alerts.pj.trafficalerts;

public class Rules {
    private String rule_descr;
    private String _id;
    private int location_id;
    private String rule_path;

    public void setRule_descr ( String descr ) {
        rule_descr = descr;
    }
    public String getRule_descr ( ) {
        return rule_descr;
    }

    public void set_id ( String id ) {
        _id = id;
    }
    public String get_id( ) {
        return _id;
    }

    public void setLocation_id ( int rule ) {
        location_id = rule;
    }
    public int getLocation_id(){
        return location_id;
    }

    public void setRule_path ( String path ) {
        rule_path = path;
    }
    public String getRule_path(){
        return rule_path;
    }

}