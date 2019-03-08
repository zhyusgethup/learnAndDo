package com.master.gm.elaGener.log.ela.bean;

import com.alibaba.fastjson.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class BaseELog {
    private String logType;
    protected String server;
    protected JSONObject jsonData;
    private String date;
    public static final DateTimeFormatter YMD = DateTimeFormatter.ofPattern("YYYY-MM-dd");

    public void setServer(String server) {
        this.server = server;
    }

    public BaseELog(String logType) {
        this.logType = logType;
        date = LocalDate.now().format(YMD);
        data = new JSONObject();
        jsonData = new JSONObject();
    }


    protected JSONObject data;

    protected void geneData(){
        data.put("logType",logType);
        data.put("date",date);
        data.put("server",server);
        data.put("jsonData",jsonData);
        generData();
    }

    protected abstract void generData();


    public JSONObject getData(){
        geneData();
        return data;
    }
}
