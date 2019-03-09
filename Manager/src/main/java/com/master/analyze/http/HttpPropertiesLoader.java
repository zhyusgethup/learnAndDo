package com.master.analyze.http;

import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

public class HttpPropertiesLoader {
    public static final Logger log = LoggerFactory.getLogger(HttpPropertiesLoader.class);
    public static  String GAME_SERVER_URL;
    public static  String LOGIN_SERVER_URL;
    public static  String SERVER_LIST_URL;
    public static  String Q_INSTATNCE_CIDS_URL;
    public static  String Q_GET_PLAYER_BASEINFO_URL;
    public static  String O_OUT_PLAYER_URL;
    public static  String O_SET_PLAYER_STATE_URL;
    public static  String O_SEND_MAIL_URL;
    public static  String O_UPDATE_NOTICE_URL;
    public static  String O_ADD_NOTICE_URL;
    public static  String O_GET_NOTICE_URL;
    static {
        Properties urlProp = new Properties();
        try {
            urlProp.load(HttpPropertiesLoader.class.getClassLoader().getResourceAsStream("urls/url.properties"));
            GAME_SERVER_URL = urlProp.getProperty("gameServerUrl");
            SERVER_LIST_URL = urlProp.getProperty("serverListUrl");
            Q_INSTATNCE_CIDS_URL = GAME_SERVER_URL + urlProp.getProperty("instActUri");
            Q_GET_PLAYER_BASEINFO_URL = GAME_SERVER_URL + urlProp.getProperty("playerBaseInfoUri");
            O_OUT_PLAYER_URL = GAME_SERVER_URL + urlProp.getProperty("outPlayerUri");
            O_SET_PLAYER_STATE_URL = GAME_SERVER_URL + urlProp.getProperty("playerStateUri");
            O_SEND_MAIL_URL = GAME_SERVER_URL + urlProp.getProperty("sendMailUri");
            LOGIN_SERVER_URL = urlProp.getProperty("loginListUrl");
            O_UPDATE_NOTICE_URL = LOGIN_SERVER_URL + urlProp.getProperty("updateNotice");
            O_ADD_NOTICE_URL = LOGIN_SERVER_URL + urlProp.getProperty("addNotice");
            O_GET_NOTICE_URL = LOGIN_SERVER_URL + urlProp.getProperty("getNotice");


            //通过http获取缓存
            ParamCache.game_online_time = TableNeed.getGameOnLineTime();


            log.info("urls init ok GAME_SERVER_URL={} , Q_INSTATNCE_CIDS_URL={},  SERVER_LIST_URL ={} , GAME_ONLINE_TIME " +
                    "={} ," ,GAME_SERVER_URL,Q_INSTATNCE_CIDS_URL, SERVER_LIST_URL ,ParamCache.game_online_time);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
