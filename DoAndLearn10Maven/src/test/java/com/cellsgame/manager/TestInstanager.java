package com.cellsgame.manager;

import com.cellsgame.TestGuild;
import com.cellsgame.TestInstance;

public class TestInstanager {
    private String[] accountsAndNames;

    public TestInstanager(String[] accounts) {
        this.accountsAndNames = accounts;
    }

    private void start() {
        if (accountsAndNames == null || accountsAndNames.length % 3 != 0) {
            System.out.println("账号设置有问题");
            System.exit(0);
        }
        for (int i = 0; i < accountsAndNames.length; i += 3) {
            String account = accountsAndNames[i];
            String name = accountsAndNames[i + 1];
            int count = Integer.valueOf(accountsAndNames[i + 2]);
            switch (name) {
                case "排行观察":
                    new TestGuild(account,RANK_GET_RANK_LIST, "会长测试解散",new Object[]{"name", "AAAAA19"}).start();
//                        new TestGuild(account,Guild_Dissolution,"会长测试解散",new Object[]{}).start();
                    break;
                case "唯一会员":
                    new TestGuild(account,  RANK_GET_RANK_LIST, "唯一会员",new Object[]{"name",""}).start();
                    break;
                    default:
                        String gn = name.substring(name.length() - 5);
                        new TestInstance(account,DIRECT_TO_DIFFICULT, name,count,new Object[]{"num",90}).start();
            }
        }
    }

    public static void main(String[] args) {
        new TestInstanager(new String[]{
//                "030c4330-8b54-9517-6d011-4198c7a935f9-a1", "直接3站测试",
//                "030c4330-8b50-97023-86d7-4198c7a935f0", "排行观察",
                "030c4330-8b50-97025-86d7-4198c7a935f0", "排行人员1","1"
//                "030c4330-8b50-97027-86d7-4198c7a935f0", "唯一会员"
        }).start();
    }

    public static final String  INSTANCE_CHALLENGED = "challenged";
    public static final String  INSTANCE_FIGHT = "fight";
    public static final String  INSTANCE_FIGHT_RESULT = "result";
    public static final String DIRECT_TO_DIFFICULT = "direct_to_difficult";
    public static final String RANK_GET_RANK_LIST = "340001";

}
