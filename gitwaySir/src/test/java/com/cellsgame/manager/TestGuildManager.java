package  com.cellsgame.manager;

import com.cellsgame.TestGuild;

public class TestGuildManager {
    private String[] accountsAndNames;

    public TestGuildManager(String[] accounts) {
        this.accountsAndNames = accounts;
    }

    private void start() {
        if (accountsAndNames == null || accountsAndNames.length % 2 != 0) {
            System.out.println("账号设置有问题");
            System.exit(0);
        }
        for (int i = 0; i < accountsAndNames.length; i += 2) {
            String account = accountsAndNames[i];
            String name = accountsAndNames[i + 1];
            switch (name) {
                case "公长测试解散4":
                    new TestGuild(account,Guild_Out, "会长测试解散4",new Object[]{"id", 17}).start();
//                        new TestGuild(account,Guild_Dissolution,"会长测试解散",new Object[]{}).start();
                    break;

                case "唯一会员":
                    new TestGuild(account,  RANK_GET_RANK_LIST, "唯一会员",new Object[]{"name",""}).start();
                    break;
                case "快乐交友1":
//                    new TestGuild(account,  Guild_Create, "快乐交友1",new Object[]{"name","快乐23"}).start();
//                    new TestGuild(account,  Guild_DetailInfo, "快乐交友1",new Object[]{"name","快乐23"}).start();
//                    new TestGuild(account,  Guild_OnekeyAcceptOrRefuse, "快乐交友2",new Object[]{"name","快乐23"}).start();
                    new TestGuild(account,  Guild_Out, "快乐交友2",new Object[]{"name","快乐23"}).start();
                    break;
                    default:
                        String gn = name.substring(name.length() - 5);
//                        new TestGuild(account,Guild_Donate, name,new Object[]{"dCid",60200001}).start();
//                        new TestGuild(account,Guild_Join, name,new Object[]{"id",3}).start();
                        new TestGuild(account,Guild_Donate, name,new Object[]{"id",3}).start();
            }
        }
    }

    public static void main(String[] args) {
        new TestGuildManager(new String[]{
//                "快乐交友1", "快乐交友1",//33
//                "030c4330-8b54-7518-6d0-4198c7a935f94","公长测试解散4",
                "030c4330-8b54-9517-6d0-4198c7a935f9-a600", "公长排名4600",
//                "030c4330-8b54-9517-6d0-4198c7a935f9-b618", "公长排名3638"
//                "030c4330-8b54-9517-6d0-4198c7a935f9-c6", "公长排名测试36",
//                "030c4330-8b54-9517-6d0-4198c7a935f9-d6", "公长排名测试46",
//                "030c4330-8b54-9517-6d0-4198c7a935f9-e6", "公长排名测试56",
//                "030c4330-8b54-9517-6d0-4198c7a935f9-f2", "公长排名测试6",
//                "030c4330-8b54-9517-6d0-4198c7a935f9-g2", "公长排名测试7",
//                "030c4330-8b54-9517-6d0-4198c7a935f9-h2", "公长排名测试8",
//                "030c4330-8b54-9517-6d0-4198c7a935f9-i2", "公长排名测试9",
//                "030c4330-8b54-9517-6d0-4198c7a935f9-j2", "公长排名测试0",
//                "030c4330-8b54-9517-6d0-4198c7a935f9-k1", "公长排名测试10",
//                "030c4330-8b54-9517-6d0-4198c7a935f9-l1", "公长排名测试11",
//                "030c4330-8b54-9517-6d0-4198c7a935f9-m1", "公长排名测试12",


//                "030c4330-8b54-9517-6d0-4198c7a935f9-n1", "公长排名测试13",
//                "030c4330-8b54-9517-6d0-4198c7a935f9-o1", "公长排名测试14",
//                "030c4330-8b54-9517-6d0-4198c7a935f9-p1", "公长排名测试15",
//                "030c4330-8b54-9517-6d0-4198c7a935f9-q1", "公长排名测试16",
//                "030c4330-8b54-9517-6d0-4198c7a935f9-r1", "公长排名测试17",
//                "030c4330-8b54-9517-6d0-4198c7a935f9-s5", "公长排名测试22"


                }).start();
//
    }

    public static final String Guild_Create = "guild_Create";
    public static final String Guild_Join = "guild_Join";
    public static final String Guild_Query = "guild_Query";
    public static final String Guild_AcceptJoin = "guild_AcceptJoin";
    public static final String Guild_ModifyNotice = "guild_ModifyNotice";
    public static final String Guild_ModifyDesc = "guild_ModifyDesc";
    public static final String Guild_ModifyQQ = "guild_ModifyQQ";
    public static final String Guild_ModifyName = "guild_ModifyName";
    public static final String Guild_ModifyRight = "guild_ModifyRight";
    public static final String Guild_Out = "guild_Out";
    public static final String Guild_ChgOwner = "guild_ChgOwner";
    public static final String Guild_Dissolution = "guild_Dissolution";
    public static final String Guild_CancelDissolution = "guild_CancelDissolution";
    public static final String Guild_Donate = "guild_Donate";
    public static final String Guild_Enter = "guild_Enter";
    public static final String Guild_ShowMemberList = "guild_ShowMemberList";
    public static final String Guild_ShowReqList = "guild_ShowReqList";
    public static final String Guild_InvitePlayer = "guild_InvitePlayer";
    public static final String Guild_ModifyVX = "guild_ModifyVX";
    public static final String Guild_ModifyNeedReqStatus = "guild_ModifyNeedReqStatus";
    public static final String Guild_GetGuildLog = "guild_GetGuildLog";
    public static final String Guild_GetBoss = "guild_GetBoss";
    public static final String Guild_OpenBoss = "guild_OpenBoss";
    public static final String Guild_FightBoss = "guild_FightBoss";
    public static final String Guild_GetRank = "guild_GetRank";
    public static final String Guild_NotAllJoinReq = "guild_NotAllJoinReq";
    public static final String Guild_BossRef = "guild_BossRef";
    public static final String Guild_OneKeyJoin = "guild_OneKeyJoin";
    public static final String Guild_CancelJoin = "guild_CancelJoin";
    public static final String Guild_ModifyLogo = "guild_ModifyLogo";
    public static final String Guild_DetailInfo = "guild_DetailInfo";
    public static final String Guild_OnekeyAcceptOrRefuse = "guild_OnekeyAcceptOrRefuse";
    public static final String Guild_Allocate = "guild_Allocate";
    public static final String Mail_List = "130006";
    public static final String Mail_Pick = "130004";
    public static final String Mail_Get = "130002";
    public static final String RANK_GET_RANK_LIST = "340001";

}
