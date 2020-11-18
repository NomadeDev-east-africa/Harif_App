package com.example.hariif;

public class RideInfo {

    public RideInfo(String DRiverId, String dateofride, String pricefinale, String destinationname) {
        this.DRiverId = DRiverId;
        this.dateofride = dateofride;
        this.pricefinale = pricefinale;

         this.destinationname = destinationname;
    }




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
        return destinationname;
    }

    public void setDestinationname(String destinationname) {
        this.destinationname = destinationname;
    }

    String DRiverId;
    String dateofride;

    public RideInfo() {
    }

    String pricefinale;
    String destinationname;
}
