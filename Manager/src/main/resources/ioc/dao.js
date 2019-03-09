var ioc={
    conf : {
        type : "org.nutz.ioc.impl.PropertiesProxy",
        fields : {
            paths : ["custom/"]
        }
    },
    dataSource : {
        factory : "$conf#make",
        args : ["com.alibaba.druid.pool.DruidDataSource", "db."],
        type : "com.alibaba.druid.pool.DruidDataSource",
        events : {
            create : "init",
            depose : 'close'
        },
       /* fields : {
            url : "jdbc:mysql://localhost:3306/analysis_log?autoReconnect=true&useUnicode=true&characterEncoding=utf-8",
            username : "root",
            password : "Ostudio123456Db?",
            maxWait: 15000, // 若不配置此项,如果数据库未启动,druid会一直等可用连接,卡住启动过程
            defaultAutoCommit : false // 提高fastInsert的性能
        },*/ fields : {
            url : "jdbc:mysql://192.168.10.234:3306/test_analysis_log?autoReconnect=true&useUnicode=true&characterEncoding=utf-8",
            username : "root",
            password : "tiandong123",
            maxWait: 15000, // 若不配置此项,如果数据库未启动,druid会一直等可用连接,卡住启动过程
            defaultAutoCommit : false // 提高fastInsert的性能
        },
    },
    dao : {
        type : "org.nutz.dao.impl.NutDao",
        args : [{refer:"dataSource"}]
    }
};