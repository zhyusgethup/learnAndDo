package build.mvn;

import java.io.*;

public class Package {
    public static void main(String[] args) throws IOException {
        new Package().cmdTest();
    }
    Process process;
    public void cmdTest() throws IOException {
        process=Runtime.getRuntime().exec("cmd");
        new Thread(){
            public void run() {
                try {
                    BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
                  /*  bw.write("e:");
                    bw.newLine();*/

                    bw.write("dirs");
                    bw.newLine();//因为读取时是一行行读取的，不加newline无法继续往下读

                  /*  bw.write("echo 'tr'");
                    bw.newLine();*/

                    bw.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        new Thread() {
            public void run() {
                try {
                    BufferedReader br=new BufferedReader(new InputStreamReader(process.getInputStream(),"GBK"));
                    String cmdout = "";
                    while ((cmdout=br.readLine())!=null) {
                        System.out.println(cmdout);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
