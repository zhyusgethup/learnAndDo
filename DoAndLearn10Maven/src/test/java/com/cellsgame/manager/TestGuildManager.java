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
                case "公长测试解散":
                    new TestGuild(account,Guild_Join, "会长测试解散",new Object[]{"name", ""}).start();
//                        new TestGuild(account,Guild_Dissolution,"会长测试解散",new Object[]{}).start();
                    break;
                case "会长测试修改":
                    new TestGuild(account,Mail_Pick , "会长测试修改",new Object[]{"id", 12}).start();
                    break;
                case "会员测试权限":
                    new TestGuild(account, Guild_Dissolution, "会员测试权限", new Object[]{"name", "A3A17"}).start();
                    break;
                case "会员测试移交":
                    new TestGuild(account, RANK_GET_RANK_LIST, "会员测试移交", new Object[]{"name", "茶园支架"}).start();
                    break;
                case "会员测试详情":
                    new TestGuild(account, Guild_Create, "会员测试详情",new Object[]{"name", "A5AAA15"}).start();
                    break;
                case "会员测试列表":
                    new TestGuild(account, Guild_Create, "会员测试列表", new Object[]{"name", "A6AAA14"}).start();
                    break;
                case "非公会成员列表":
                    new TestGuild(account,  RANK_GET_RANK_LIST, "非公会成员列表",new Object[]{"name", "Asdsd13"}).start();
                    break;
                case "唯一会员":
                    new TestGuild(account,  RANK_GET_RANK_LIST, "唯一会员",new Object[]{"name",""}).start();
                    break;
                case "快乐交友2":
                    new TestGuild(account,  Guild_Out, "快乐交友2",new Object[]{"name","快乐23"}).start();
//                    new TestGuild(account,  Guild_OnekeyAcceptOrRefuse, "快乐交友2",new Object[]{"name","快乐23"}).start();
                    break;
                    default:
                        String gn = name.substring(name.length() - 5);
                        new TestGuild(account,Guild_Join, name,new Object[]{"name",gn}).start();
            }
        }
    }

    public static void main(String[] args) {
        new TestGuildManager(new String[]{
//                "快乐交友1", "快乐交友1",//33
                "快乐交友2", "快乐交友2",//32
//                "030c4330-8b54-9518-6d0-4198c7a935f12","公长测试解散3",
//                "030c4330-8b54-7518-6d0-4198c7a935f9","公长测试解散",
//                 "030c4330-8b54-9519-6d0-4198c7a935f9", "公长测试解散",
//               "030c4330-8b55-74116-86d5-4198c7a935f9", "会长测试修改",
//                "03331-8b57-745-i2-86d5-4198c7a935f9", "会员测试权限",
//                "030c4330-8b57-72/*istener1-86d5-4198c7a935f9", "会员测试移交",
//                "030c4330-8b58-7listener2-86d5-4198c7a935f9", "会员测试移",
//                "030c4330-8b60-7495-86d6-4198c7a935f9", "会员测试详情",
//                "030c4330-8b61-7000-86d5-4198c7a935f9", "会员测试列表",
//                "030c4330-8b51-7004-86d7-4198c7a935f9", "非公会成员列表",
//                "030c4330-8b50-97020-86d7-4198c7a935f0", "唯一会员",
//                "030c4330-8b50-97019-86d7-4198c7a935f0", "唯一会员",
//                "030c4330-8b50-97021-86d7-4198c7a935f0", "唯一会员",
//                "030c4330-8b50-97022-86d7-4198c7a935f0", "唯一会员",
//                "030c4330-8b50-97023-86d7-4198c7a935f0", "唯一会员",
//                "030c4330-8b50-97025-86d7-4198c7a935f0", "唯一会员",
//                "030c4330-8b50-97027-86d7-4198c7a935f0", "唯一会员"
//                "030c4330-8b54-9517-6d0-4198c7a935f9-a1", "公长排名测试1",
//                "030c4330-8b54-9517-6d0-4198c7a935f9-b1", "公长排名测试2",
//                "030c4330-8b54-9517-6d0-4198c7a935f9-c1", "公长排名测试3",
//                "030c4330-8b54-9517-6d0-4198c7a935f9-d1", "公长排名测试4",
//                "030c4330-8b54-9517-6d0-4198c7a935f9-e1", "公长排名测试5",
//                "030c4330-8b54-9517-6d0-4198c7a935f9-f1", "公长排名测试6",
//                "030c4330-8b54-9517-6d0-4198c7a935f9-g1", "公长排名测试7",
//                "030c4330-8b54-9517-6d0-4198c7a935f9-h1", "公长排名测试8",
//                "030c4330-8b54-9517-6d0-4198c7a935f9-i1", "公长排名测试9",
//                "030c4330-8b54-9517-6d0-4198c7a935f9-j1", "公长排名测试0",
//                "030c4330-8b54-9517-6d0-4198c7a935f9-k1", "公长排名测试10",
//                "030c4330-8b54-9517-6d0-4198c7a935f9-l1", "公长排名测试11",
//                "030c4330-8b54-9517-6d0-4198c7a935f9-m1", "公长排名测试12",
//
//
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
