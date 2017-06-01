package com.example.administrator.free.DataBaseRelated;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/05/09.
 */

public class DateRecode extends DataSupport {
    private int  counts;
    private String dates;
    private long mostTime;
    private long lessTime=1000000;

    public int getCounts() {
        return counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public long getMostTime() {
        return mostTime;
    }

    public void setMostTime(long mostTime) {
        this.mostTime = mostTime;
    }
    public long getLessTime() {
        return lessTime;
    }

    public void setLessTime(long  lessTime) {
        this.lessTime = lessTime;
    }
}
