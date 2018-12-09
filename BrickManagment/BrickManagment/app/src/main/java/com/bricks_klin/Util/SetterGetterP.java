package com.bricks_klin.Util;

/**
 * Created by Anonymous on 8/2/2018.
 */

public class SetterGetterP {

    private String Id;
    private String rawmat;
    private String date;
    private String price;
    private String qty;
    private String klinid;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getRawmat() {
        return rawmat;
    }

    public void setRawmat(String rawmat) {
        this.rawmat = rawmat;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getKlinid() {
        return klinid;
    }

    public void setKlinid(String klinid) {
        this.klinid = klinid;
    }

    public String getPby() {
        return pby;
    }

    public void setPby(String pby) {
        this.pby = pby;
    }

    private String pby;
}
