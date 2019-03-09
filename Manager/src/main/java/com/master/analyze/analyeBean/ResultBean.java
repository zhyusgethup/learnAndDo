package com.master.analyze.analyeBean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gmdesign.exception.GmException;
import sun.applet.Main;

import java.util.HashMap;
import java.util.Map;

public class ResultBean {
    public static final String CODE  = "\"code\":";
    public static final String MSG  = "\"msg\":";
    public static final String DATA  = "\"da\":";

    public static final int SUC = 1;
    public static final int DEFAULT_FAIL = 2;
    public static final String DEF_MSG  = "系统异常,请稍后再试";
    public static final String OK = "ok";

    public static ResultBean getDefaultWrongBean() {
       return new ResultBean(DEFAULT_FAIL,DEF_MSG);
    }
    public static ResultBean getEmptyBean() {
        return new ResultBean(SUC,OK);
    }
    private int code;
    private String msg;
    private Object data;
    private ResultBean(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public ResultBean( String msg) {
        this.code = 2;
        this.msg = msg;
    }



    public ResultBean(AnalyeException e) {
        this.code = e.getCode();
        this.msg = e.getMessage();
    }
    public ResultBean(GmException e) {
        this.code = e.getCode();
        this.msg = e.getMessage();
    }

    public Object toJsonObj(){
        return JSON.parse(toJsonStr());
    }
    public ResultBean(Object datas) {
        this.code = SUC;
        this.msg = OK;
        this.data = datas;

    }
    public String toJsonStr() {
        return JSONObject.toJSONString(this);
    }
//    public String toJsonStr() {
//        StringBuilder sb = new StringBuilder("{");
//        if(code != 0) {
//            sb.append(CODE  + code +",");
//        }
//        if(null != msg) {
//            sb.append(MSG + "\"" + msg + "\",");
//        }
//        if(null != data) {
//            sb.append(DATA + data + ",");
//        }
//        sb.deleteCharAt(sb.length()-1);
//        sb.append("}");
//        return sb.toString();
//    }

    public static void main(String[] args) {
        String data = "{}";
        System.out.println(new ResultBean(data).toJsonStr());
    }
}
