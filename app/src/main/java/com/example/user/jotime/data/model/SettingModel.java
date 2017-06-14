package com.example.user.jotime.data.model;


public class SettingModel {
    private int id;
    private int intervalDays;
    private long fromDate;
    private long tillDate;

    public SettingModel() {
    }

    public SettingModel(int id, int intervalDays) {
        this.id = id;
        this.intervalDays = intervalDays;
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

    public int getIntervalDays() {
        return intervalDays;
    }

    public void setIntervalDays(int intervalDays) {
        this.intervalDays = intervalDays;
    }
}
