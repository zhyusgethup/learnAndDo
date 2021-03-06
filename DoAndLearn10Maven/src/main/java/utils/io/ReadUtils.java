package utils.io;

import java.io.*;
import java.nio.charset.Charset;

public class ReadUtils {

    public static StringBuilder getWordFromInputStreamAndAnayseCharset(InputStream inputStream) {
        StringBuilder sb = new StringBuilder();
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(inputStream);
            String charset = analyseCharset(bis);
            bis.reset();
            byte[] bytes = new byte[bis.available()];
            StringBuilder sbb = new StringBuilder();
            for (int i; (i = bis.read(bytes)) != -1;) {
                sb.append(new String(bytes, 0, i, Charset.forName(charset)));
                if(i == 0)
                    break;
            }
            int pos = bis.read(bytes);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb;
    }
    public static StringBuilder getWordsFromFileAndAnalyseCharset(File file) throws FileNotFoundException {
        if(file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            getWordFromInputStreamAndAnayseCharset(new BufferedInputStream(fis));
        }
        return null;
    }

    public static String analyseCharset(BufferedInputStream bis) throws IOException {
        String charset = "GBK";
        byte[] first3Bytes = new byte[3];
        boolean checked = false;
        bis.mark(0);
        int read = bis.read(first3Bytes, 0, 3);
        if (read == -1) {
            return charset; //文件编码为 ANSI
        } else if (first3Bytes[0] == (byte) 0xFF
                && first3Bytes[1] == (byte) 0xFE) {
            charset = "UTF-16LE"; //文件编码为 Unicode
            checked = true;
        } else if (first3Bytes[0] == (byte) 0xFE
                && first3Bytes[1] == (byte) 0xFF) {
            charset = "UTF-16BE"; //文件编码为 Unicode big endian
            checked = true;
        } else if (first3Bytes[0] == (byte) 0xEF
                && first3Bytes[1] == (byte) 0xBB
                && first3Bytes[2] == (byte) 0xBF) {
            charset = "UTF-8"; //文件编码为 UTF-8
            checked = true;
        }
        bis.reset();
        if (!checked) {
            int loc = 0;
            while ((read = bis.read()) != -1) {
                loc++;
                if (read >= 0xF0)
                    break;
                if (0x80 <= read && read <= 0xBF) // 单独出现BF以下的，也算是GBK
                    break;
                if (0xC0 <= read && read <= 0xDF) {
                    read = bis.read();
                    if (0x80 <= read && read <= 0xBF) // 双字节 (0xC0 - 0xDF)
                        // (0x80
                        // - 0xBF),也可能在GB编码内
                        continue;
                    else
                        break;
                } else if (0xE0 <= read && read <= 0xEF) {// 也有可能出错，但是几率较小
                    read = bis.read();
                    if (0x80 <= read && read <= 0xBF) {
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) {
                            charset = "UTF-8";
                            break;
                        } else
                            break;
                    } else
                        break;
                }
            }
        }
        return charset;
    }
}
