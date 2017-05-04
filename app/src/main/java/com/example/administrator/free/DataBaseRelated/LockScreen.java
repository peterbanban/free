package com.example.administrator.free.DataBaseRelated;

import org.litepal.crud.DataSupport;

import java.util.Date;


/**
 * Created by Administrator on 2017/04/30.
 */

public class LockScreen extends DataSupport{
    private long  id;
    private Date dateStart;
    private Date dateEnd;
    private long interval;

    public void setId(long id) {
        this.id = id;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public void setInterval(long interval) {
       this.interval =interval;
    }

    public long getId() {
        return id;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }
    public long getInterval(){
        return interval;
    }
}
