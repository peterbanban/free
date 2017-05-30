package com.example.administrator.free.DataBaseRelated;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/05/09.
 */

public class DateRecode extends DataSupport {
    private int  counts;
    private String dates;

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
}
