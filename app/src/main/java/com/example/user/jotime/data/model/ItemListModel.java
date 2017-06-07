package com.example.user.jotime.data.model;


public class ItemListModel {
    private String dates;
    private String missingTime;

    public ItemListModel(String dates, String missingTime) {
        this.dates = dates;
        this.missingTime = missingTime;
    }


    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public String getMissingTime() {
        return missingTime;
    }

    public void setMissingTime(String missingTime) {
        this.missingTime = missingTime;
    }
}
