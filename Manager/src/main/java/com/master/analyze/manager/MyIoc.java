package com.master.analyze.manager;

import org.nutz.dao.Dao;
import org.nutz.ioc.Ioc;

import javax.sql.DataSource;

public class MyIoc {
    public static Ioc ioc;
    public static DataSource ds;
    public static Dao dao;
    public static TableManager tableManager;
}
