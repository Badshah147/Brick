package com.bricks_klin.Util;

/**
 * Created by Anonymous on 8/2/2018.
 */

public class SetterGetterW {
    private String Id;
    private String value;
    private String degree;
    private String locname;
    private String wday;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getLocname() {
        return locname;
    }

    public void setLocname(String locname) {
        this.locname = locname;
    }

    public String getWday() {
        return wday;
    }

    public void setWday(String wday) {
        this.wday = wday;
    }

    public String getKlinid() {
        return klinid;
    }

    public void setKlinid(String klinid) {
        this.klinid = klinid;
    }

    private String klinid;
}
