package utils.windows.cmd;

import utils.windows.cmd.impl.EasyCMD;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

/**
 * @author zhyu
 * @date 2019-05-27 16:46:53
 */
public class EasyRun {
    public static void main(String[] args) {
        CMD cmd = new EasyCMD("cmd /c G: && cd G:\\GitAndSvn\\servers\\v4-server\\server && mvn clean");
        cmd.setTimeout(1, TimeUnit.MINUTES);
        cmd.run();
        CMD cmd2 = new EasyCMD("cmd /c G: && cd G:\\GitAndSvn\\servers\\v4-server\\server && mvn package");
        cmd2.setTimeout(1, TimeUnit.MINUTES);
        cmd2.run();
    }
}
