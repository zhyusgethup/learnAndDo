package utils.windows;

import org.apache.maven.shared.utils.StringUtils;
import utils.io.ReadUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public class TestWindowsCmd {

    public static void runCmds(String... cmds) throws IOException {
        Process process = Runtime.getRuntime().exec("cmd");
        PrintWriter out = new PrintWriter(process.getOutputStream());

        for (int i = 0; i < cmds.length; i++) {
            String cmd; //你的cmd命令
            InputStream is = process.getInputStream(); //cmd返回出来的信息
            out.println(cmds[i]); //输入你的命令
            out.flush(); //写到控制台
        }
    }

    public static void runCmd(String cmd, long outTime) throws IOException, InterruptedException {
        if (StringUtils.isEmpty(cmd)) {
            return;
        }
        int maxWaitCount = 5;
        if (outTime > 1000) {
            maxWaitCount = (int) (outTime / 200);
        }
        Process process = Runtime.getRuntime().exec(cmd);
        int waitCount = 0;
        while (process.getInputStream().available() == 0 && waitCount < maxWaitCount) {
            Thread.sleep(200);
            waitCount++;
        }
        if (process.getInputStream().available() == 0) {
            //没有说到回应
            return;
        }
        System.out.println(ReadUtils.getWordFromInputStreamAndAnayseCharset(process.getInputStream()));
        process.destroy();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Process ipconfig = Runtime.getRuntime().exec("ipconfig");
//        System.out.println(getStringFromInputStream(ipconfig.getInputStream()));
       /* while(ipconfig.getInputStream().available() == 0){
            Thread.sleep(200);
        }*/
        InputStream in = null;
        ipconfig.waitFor(1000, TimeUnit.MILLISECONDS);
        if (ipconfig.getInputStream().available() == 0) {
            in = ipconfig.getErrorStream();
        } else {
            in = ipconfig.getInputStream();
        }
        System.out.println(ReadUtils.getWordFromInputStreamAndAnayseCharset(in));
        ipconfig.destroy();
    }

    public static String getStringFromInputStream(InputStream stream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        byte[] byt = new byte[4096];
        System.out.println(stream.available());
        for (int i; (i = stream.read(byt)) != -1; ) {
            stringBuilder.append(new String(byt, 0, i));
        }
        return stringBuilder.toString();
    }
}
