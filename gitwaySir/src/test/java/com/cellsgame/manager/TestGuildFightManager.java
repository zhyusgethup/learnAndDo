package com.cellsgame.manager;

import com.cellsgame.TestGuild;
import com.cellsgame.TestSummon;
import com.cellsgame.gateway.message.processor.JavaThreadMessageProcessor;

import java.util.concurrent.Executors;

public class TestGuildFightManager {
    private String[] accountsAndNames;

    public TestGuildFightManager(String[] accounts) {
        this.accountsAndNames = accounts;
    }

    private void start() throws InterruptedException {
        if (accountsAndNames == null || accountsAndNames.length % 2 != 0) {
            System.out.println("账号设置有问题");
            System.exit(0);
        }
        for (int i = 0; i < accountsAndNames.length; i += 2) {
            String account = accountsAndNames[i];
            String name = accountsAndNames[i + 1];
            switch (name) {
                case "凶凶凶5":
                    new TestGuildFight(account, Guild_Create, name, new Object[]{"name", "凶神恶煞"}).start();
                    break;
                case "凶凶凶6":
                    new TestGuildFight(account, Guild_Join,name, new Object[]{123}).start();
                    break;
                case "凶凶凶4":
//                    new TestGuildFight(account, Guild_WordInfo, name, new Object[]{"namez", "快乐交友"}).start();
//                    new TestGuildFight(account, ShopOpen, name, new Object[]{"cityId", 1}).start();
//                    new TestGuildFight(account, RANK_GET_RANK_LIST, name, new Object[]{"type", 7}).start();
//                    new TestGuildFight(account, ARENA_CHALLENGE, name, new Object[]{"tid", 0}).start();
//                    new TestGuildFight(account, MakeTeam, name, new Object[]{"teamIndex", 0,"hid",25,"heroIndex",3}).start();
//                    new TestGuildFight(account, ShopBuy, name, new Object[]{"cityId", 1,"itemIndex",1}).start();
//                    new TestGuildFight(account, Refresh, name, new Object[]{"cityId", 1}).start();

//                    new TestGuildFight(account,Guild_Create, name,new Object[]{"name","newHero"}).start();
//                    Thread.sleep(5000);
//                    new TestGuildFight(account,Guild_Investigate, name,new Object[]{"cityId",3,"shId",11,"gid",32355}).start();
//                    new TestGuildFight(account,Guild_Attack_In, name,new Object[]{"cityId",3,"shId",11}).start();

//                    new TestGuildFight(account,Guild_Attack_Troops, name,null).start();
//                    new TestGuildFight(account,Guild_Attack_In, name,new Object[]{"cityId",3,"shId",11}).start();

//                    new TestGuildFight(account,Guild_CityInfo, name,new Object[]{"cityId",1}).start();
//                      new TestGuildFight(account,Guild_Guard_Troops, name,null).start();
//                    new TestGuildFight(account,StrongHolGuild_Enter, name,new Object[]{"cityId",1,"shId",3}).start();
//                    new TestSummon(new JavaThreadMessageProcessor(Executors.newFixedThreadPool(1)),10,5,true).start();
//                      new TestGuildFight(account,FightBegin, name,new Object[]{"typ",3,"tIdx",0,"val",1}).start();

//                    break;
                default:
                   /* String gn = name.substring(name.length() - 5);
                    new TestDemo(account,Guild_Donate, name,new Object[]{"name",gn}).start();*/
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new TestGuildFightManager(new String[]{
                "030c4330-8b55-4474-86d5-4198c7a935f4", "凶凶凶4",
//                "030c4330-8b55-4474-86d5-4198c7a935f5", "凶凶凶5",
//                "030c4330-8b55-4474-86d5-4198c7a935f6", "凶凶凶6"
        }).start();
//
    }

    public static final int Player_Test_Logout = 20100;
    public static final int Guild_Create = 120001;
    // 公会玩法相关 begin------------
    // 进入世界
    public static final int Guild_WordInfo = 120100;
    // 进入城镇
    public static final int Guild_CityInfo = 120101;
    // 侦查
    public static final int Guild_Investigate = 120102;
    // 编辑防守部队
    public static final int Guild_Guard_Troops = 120103;
    // 编辑攻击部队
    public static final int Guild_Attack_Troops = 120104;
    // 进入攻打状态
    public static final int Guild_Attack_In = 120105;
    // 退出攻打状态
    public static final int Guild_Attack_Out = 120106;
    // 入驻据点
    public static final int StrongHolGuild_Enter = 120107;
    // 任命城主
    public static final int Guild_City_Host = 120108;
    // 城主布防
    public static final int Guild_Host_Guard = 120109;
    // 更换国家
    public static final int Guild_Change_Nation = 120110;
    public static final int FightBegin = 460001;
    public static final int ShopOpen = 110001;
    public static final int ShopBuy = 110002;
    public static final int Refresh = 110004;
    public static final int RANK_GET_RANK_LIST = 340003;
    public static final int ARENA_CHALLENGE = 490002;
    public static final int MakeTeam = 450008;
    public static final int Guild_Join = 450008;

}
