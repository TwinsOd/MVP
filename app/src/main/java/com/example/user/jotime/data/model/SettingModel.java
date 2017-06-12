package com.example.user.jotime.data.model;


public class SettingModel {
    private int id;
    private long fromDate;
    private long tillDate;

    public SettingModel(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getFromDate() {
        return fromDate;
    }

    public void setFromDate(long fromDate) {
        this.fromDate = fromDate;
    }

    public long getTillDate() {
        return tillDate;
    }

    public void setTillDate(long tillDate) {
        this.tillDate = tillDate;
    }
}
