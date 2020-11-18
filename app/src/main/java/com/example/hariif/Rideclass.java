package com.example.hariif;

public class Rideclass {


    public Rideclass() {
    }

    String DRiverId;

    public Rideclass(String DRiverId, String dateofride, String pricefinale, String destinationname) {
        this.DRiverId = DRiverId;
        this.dateofride = dateofride;
        this.pricefinale = pricefinale;
        Destinationname = destinationname;
    }

    String dateofride;
    String pricefinale;

    public String getDRiverId() {
        return DRiverId;
    }

    public void setDRiverId(String DRiverId) {
        this.DRiverId = DRiverId;
    }

    public String getDateofride() {
        return dateofride;
    }

    public void setDateofride(String dateofride) {
        this.dateofride = dateofride;
    }

    public String getPricefinale() {
        return pricefinale;
    }

    public void setPricefinale(String pricefinale) {
        this.pricefinale = pricefinale;
    }

    public String getDestinationname() {
        return Destinationname;
    }

    public void setDestinationname(String destinationname) {
        Destinationname = destinationname;
    }

    String Destinationname;

}
