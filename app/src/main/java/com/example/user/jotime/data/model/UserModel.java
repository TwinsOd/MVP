package com.example.user.jotime.data.model;


public class UserModel {
    private int id;
    private long fromDate;
    private long tillDate;

    public UserModel(int id) {
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
