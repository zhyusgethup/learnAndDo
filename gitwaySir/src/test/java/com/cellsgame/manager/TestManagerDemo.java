package  com.cellsgame.manager;

import com.cellsgame.TestGuild;
import org.junit.Test;

public class TestManagerDemo {
    private String[] accountsAndNames;

    public TestManagerDemo(String[] accounts) {
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
                case "快乐交友1":
                    new TestDemo(account,CHAT_GET_1v1_CACHE, name,new Object[]{"pid", 32,"msg","你好，我是快乐交友1"}).start();
//                    new TestDemo(account,CHAT_GET_MAIN_UI_CACHE, name,new Object[]{"tid", 1}).start();
                    new TestDemo(account,CHAT_PRIVATE_MSG, name,new Object[]{"pid",19, "msg","ok"}).start();
                    break;
                case "快乐交友2":
                    new TestDemo(account,CHAT_PRIVATE_MSG,name,new Object[]{"pid", 1,"msg","私聊"}).start();
//                    new TestDemo(account,CHAT_CHAT, name,new Object[]{}).start();
//                    new TestDemo(account,CHAT_GET_MAIN_UI_CACHE, name,new Object[]{}).start();
                    break;
                case "快乐交友3":
//                    new TestDemo(account,Player_Test_Logout,name,new Object[]{"pid", 2,"msg","你好， 我是快乐交友3"}).start();
                    new TestDemo(account,CHAT_GET_MAIN_UI_CACHE, name,new Object[]{}).start();
//                    new TestDemo(account,CHAT_CHAT, name,new Object[]{}).start();
                    break;
                default:
                   /* String gn = name.substring(name.length() - 5);
                    new TestDemo(account,Guild_Donate, name,new Object[]{"name",gn}).start();*/
            }
        }
    }

    public static void main(String[] args) {
        new TestManagerDemo(new String[]{
//                "快乐交友1", "快乐交友1",//33
                "快乐交友2", "快乐交友2",//32
//                "快乐交友3", "快乐交友3",//
//                "快乐交友4", "快乐交友4"//34
        }).start();
//
    }

    public static final int FRIEND_ADD_NEW = 300003;
    public static final int CHAT_CHAT = 190001;
    public static final int CHAT_PRIVATE_MSG = 190003;
    public static final int CHAT_GET_1v1_CACHE = 190008;
    public static final int CHAT_GET_MAIN_UI_CACHE = 190009;
    public static final int Player_Test_Logout = 20100;

}
