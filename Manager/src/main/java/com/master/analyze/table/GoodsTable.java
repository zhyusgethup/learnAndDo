package com.master.analyze.table;

import com.master.analyze.analyeBean.TableEnum;
import com.master.cons.Cons;
import org.nutz.dao.Dao;
import org.nutz.dao.impl.NutDao;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.ioc.loader.json.JsonLoader;

import javax.sql.DataSource;

public class GoodsTable extends MyTable{
    public GoodsTable(TableEnum e) {
        super(e);
    }
    protected int varType = Cons.ONLY_VAR_COL;
    public static void main(String[] args) {
        MyTable table = new GoodsTable(TableEnum.GOOD_AGG_TABLE);
        Ioc ioc = new NutIoc(new JsonLoader("ioc/dao.js"));
        DataSource ds = ioc.get(DataSource.class);
        Dao dao = new NutDao(ds); //如果已经定义了dao,那么改成dao = ioc.get(Dao.class);
//        table.generateDatasIfNull(LoginLogoutServiceImpl service);
//        String s = table.toJsonStr();
//        JSONObject jsonObject = JSON.parseObject(s);
//        System.out.println(jsonObject);
//        System.out.println("--------");
//        System.out.println(table.toGraphicJsonStr("2019-02-19"));
//        ioc.depose();
    }
}
