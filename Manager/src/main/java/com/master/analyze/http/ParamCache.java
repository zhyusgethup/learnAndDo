package com.master.analyze.http;

/**
 * 用于缓存一些不易改变的量
 */
public class ParamCache {
   static  String game_online_time;
   public static String getServerOnlineTime(){
       return game_online_time;
   }
}
