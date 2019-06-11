package utils.file.decompress;

/**
 * @author zhyu
 * @date zip 解压
 */

import org.apache.maven.shared.utils.StringUtils;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.*;

public class ZipDecompressUtil { // 创建文件

    public static InputStream getInputStreamFromZip(String zipPath, String srcPath) throws IOException {
        if(StringUtils.isEmpty(srcPath)) {
           return new ZipInputStream(new FileInputStream(zipPath));
        }
        ZipFile zip = new ZipFile(zipPath);
        return zip.getInputStream(zip.getEntry(srcPath));
    }
    public static void main(String[] temp) throws IOException {

//        ZipFile zipFile = new ZipFile("G:\\GitAndSvn\\servers\\v4-server\\server\\target\\server-2.0.war");
        ZipFile zipFile = new ZipFile("D:\\sample.war");

        ZipEntry entry = zipFile.getEntry("WEB-INF/web.xml");
        BufferedReader bf = new BufferedReader(new InputStreamReader(zipFile.getInputStream(entry)));
        System.out.println(bf.readLine());
        bf.close();
//        @SuppressWarnings("unchecked")
//        Enumeration<ZipEntry> enu = (Enumeration<ZipEntry>) zipFile
//                .entries();
//        while (enu.hasMoreElements()) {
//            ZipEntry zipElement = (ZipEntry) enu.nextElement();
//            InputStream read = zipFile.getInputStream(zipElement);
//            String fileName = zipElement.getName();
//            if (fileName != null && fileName.indexOf(".") != -1) {// 是否为文件
//                if(fileName.equals("WEB-INF/classes/config.properties")) {
//                    System.out.println(fileName);
//                }
//            }
//        }
    }
}
