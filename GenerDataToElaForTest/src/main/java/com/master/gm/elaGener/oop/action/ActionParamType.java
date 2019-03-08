package com.master.gm.elaGener.oop.action;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ActionParamType<T>{
    public static final ActionParamType<Integer> GOOD_ADD = new ActionParamType<>("add");
    public static final ActionParamType<Integer> GOOD_MINUS = new ActionParamType<>("minus");
    public static final ActionParamType<Integer> GOOD_CID = new ActionParamType<>("cid");
    public static final ActionParamType<Integer> GUILD_COUNT = new ActionParamType<>("g_count");
    public static final ActionParamType<Integer> GUILD_DONATE = new ActionParamType<>("g_donate");
    public static final ActionParamType<Integer> GUILD_G_ID = new ActionParamType<>("g_id");
    public static final ActionParamType<String> GUILD_G_NAME = new ActionParamType<>("g_name");
    public static final ActionParamType<Integer> GUILD_G_M_COUNT  = new ActionParamType<>("g_member_count");
    public static final ActionParamType<Integer> ROLE_GROW  = new ActionParamType<>("role_grow");
    public static final ActionParamType<Integer> U_TYPE  = new ActionParamType<>("u_type");

    public static Map<String,ParamVal> vals;
    static {
        vals = new ConcurrentHashMap<>();
    }
    private String name;
    private T val;
    private ActionParamType(String name) {
        this.name = name;
    }

    private static <T> ActionParamType<T> valueOf(String name) {
        return new ActionParamType<>(name);
    }
    public void val(T val) {
      vals.put(name, new ParamVal<T>(val));
    }
    public T val(){
        ParamVal paramVal = vals.get(name);
        return (T)paramVal.val;
    }


    // ---------------------------------------------constructor---------------------------------------------------------
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActionParamType<?> evtParamType = (ActionParamType<?>) o;
        return name.equals(evtParamType.name);

    }

    static class ParamVal<T>{
        public ParamVal(T val) {
            this.val = val;
        }
        private T val;
        public T val(){return val;}
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }
}
