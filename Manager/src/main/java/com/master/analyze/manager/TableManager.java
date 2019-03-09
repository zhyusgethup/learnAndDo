package com.master.analyze.manager;

import com.master.analyze.analyeBean.AbstractTable;
import com.master.analyze.analyeBean.TableEnum;
import com.master.analyze.analyeBean.TimeManager;
import com.master.analyze.table.MyTable;
import com.master.analyze.table.StayTable;
import com.master.gm.service.analyze.DBService;
import com.master.gm.service.analyze.LoginLogoutService;
import com.master.util.TransportClientFactory;
import lombok.Getter;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.ioc.loader.json.JsonLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.plugin2.message.transport.TransportFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@IocBean
public class TableManager {
    private static final Logger log = LoggerFactory.getLogger(TableManager.class);
    @Getter
    @Inject
    private DBService dbService;
    @Getter
    @Inject
    private LoginLogoutService service;

    public static void main(String[] args) {
        Ioc ioc = new NutIoc(new JsonLoader("ioc/dao.js"));
        TableManager tableManager = ioc.get(TableManager.class);
        tableManager.getTableByTableStr("gooc").startupLoad(tableManager.getService());
    }

    public Map<String, String> getTableStates() {
        Map<String, String> re = new HashMap<>();
        for (int i = 0; i < tableList.size(); i++) {
            re.put(tableList.get(i).getTableName(), tableList.get(i).printState());
        }
        return re;
    }

    @Getter
    private List<AbstractTable> tableList;
    @Inject
    private TimeManager timeManager;
    public void init() {
        log.info("tableManger开始初始化");
        resourceInit();
        regesterTable();
        timeManager.flush();
        startupLoadTable();
    }

    private void resourceInit() {
        try {
            Class.forName("com.master.analyze.http.HttpPropertiesLoader");
            TransportClientFactory.getInstance().initClient();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }


    public AbstractTable getTableByTableStr(String tableStr) {
        if (null == tableStr || tableStr.trim().equals(""))
            return null;
        for (int i = 0; i < tableList.size(); i++) {
            if (tableList.get(i).getTableStr().equals(tableStr)) {
                return tableList.get(i);
            }
        }
        return null;
    }

    private void startupLoadTable(String tableStr) {
        StringBuilder stringBuilder = getTableByTableStr(tableStr).startupLoad(service);
        System.out.println(stringBuilder.toString());
    }

    private void startupLoadTable() {
        List<StringBuilder> list = new ArrayList<>(20);
        for (int i = 0; i < tableList.size(); i++) {
            StringBuilder msg = tableList.get(i).startupLoad(service);
            list.add(msg);
        }
        for (int i = 0; i < list.size(); i++) {
            log.info(list.get(i).toString());
        }
    }

    private void regesterTable() {
        tableList = new CopyOnWriteArrayList<>();
        TableEnum[] values = TableEnum.values();
        for (int i = 0; i < values.length; i++) {
            TableEnum e = values[i];
            Class<? extends AbstractTable> cls = e.getClasz();
            try {
                if(cls.getSuperclass() == MyTable.class) {
                    Constructor<?> cons = cls.getConstructor(TableEnum.class);
                    MyTable table = (MyTable) cons.newInstance(e);
                    tableList.add(table);
                }else if(cls == StayTable.class){
                    StayTable table = new StayTable();
                    tableList.add(table);
                }
            } catch (NoSuchMethodException e1) {
                e1.printStackTrace();
                throw new RuntimeException("Table实例化异常");
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
                throw new RuntimeException("Table实例化异常");
            } catch (InstantiationException e1) {
                e1.printStackTrace();
                throw new RuntimeException("Table实例化异常");
            } catch (InvocationTargetException e1) {
                e1.printStackTrace();
                throw new RuntimeException("Table实例化异常");
            }
        }
    }
}
