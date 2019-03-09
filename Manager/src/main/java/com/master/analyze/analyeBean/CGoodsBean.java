package com.master.analyze.analyeBean;

import lombok.Data;
import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.entity.Record;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.dao.impl.NutDao;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.ioc.loader.json.JsonLoader;

import javax.sql.DataSource;
import java.util.List;

@Table("forGoodsCompute")
@Data
public class CGoodsBean {
    @Id
    private int id;
    @Column
    private String server;
    @Column
    private int cid;
    @Column
    private String acc;
    private int cost;
    private int product;
    @Column
    private int hisCount;
    @Column
    private int curCount;
    @Column
    private long start;
    @Column
    private long end;

    public CGoodsBean() {
    }

    public CGoodsBean(String server, int cid, String acc, int hisCount, int curCount, long start, long end) {
        this.server = server;
        this.cid = cid;
        this.acc = acc;
        this.hisCount = hisCount;
        this.curCount = curCount;
        this.start = start;
        this.end = end;
    }

    public static void main(String[] args) {
        Ioc ioc = new NutIoc(new JsonLoader("ioc/dao.js"));
        DataSource ds = ioc.get(DataSource.class);
        Dao dao = new NutDao(ds); //如果已经定义了dao,那么改成dao = ioc.get(Dao.class);
//        Chain chains = Chain.make("TIME","2");
//        chains.add("SERVER","LJF");
//        dao.insert("com",chains);
//
//        List<Record> list =dao.query("com", Cnd.where("id","<","3"));
//        System.out.println(list);

//        dao.create(CGoodsBean.class, true);
//        dao.insert(new CGoodsBean("2004",1200023,"dadian1",50,50,1548777600000L,1548864000000L));
//         Condition c = Cnd.where("server","=","2004");
//        List<Record> forGoodsCompute = dao.query("forGoodsCompute", c);
//        CGoodsBean bean = new CGoodsBean();
//        bean.setServer("2004");
//        bean.setCurCount(1200023);
//        bean.setAcc("dadian1");
//        dao.q
        ioc.depose(); //关闭Ioc容器
//        System.out.println(forGoodsCompute);
    }
}
