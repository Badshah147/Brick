package com.bricks_klin.Util;

/**
 * Created by Anonymous on 7/29/2018.
 */

public class SetterGetter {
    private String ID;
    private String rawname;
    private String date;
    private String price;
    private String qty;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getRawname() {
        return rawname;
    }

    public void setRawname(String rawname) {
        this.rawname = rawname;
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

    public String getOldqty() {
        return oldqty;
    }

    public void setOldqty(String oldqty) {
        this.oldqty = oldqty;
    }

    public String getCqty() {
        return cqty;
    }

    public void setCqty(String cqty) {
        this.cqty = cqty;
    }

    public String getPby() {
        return pby;
    }

    public void setPby(String pby) {
        this.pby = pby;
    }

    public String getKlinid() {
        return klinid;
    }

    public void setKlinid(String klinid) {
        this.klinid = klinid;
    }

    private String oldqty;
    private String cqty;
    private String pby;
    private String klinid;
}
