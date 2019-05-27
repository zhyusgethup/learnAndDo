package utils.file.decompress;

/**
 * @author zhyu
 * @date zip 解压
 */

import java.io.*;
import java.util.zip.*;

public class ZipDecompressUtil { // 创建文件
    public static void main(String[] temp) {
        ZipInputStream zin; // 创建ZipInputStream对象
        try { // try语句捕获可能发生的异常
            zin = new ZipInputStream(new FileInputStream("G:\\GitAndSvn\\servers\\v4-server\\server\\target\\server-2.0.war"));
// 实例化对象，指明要进行解压的文件
            ZipEntry entry = zin.getNextEntry(); // 获取下一个ZipEntry

            zin.close(); // 关闭流
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
