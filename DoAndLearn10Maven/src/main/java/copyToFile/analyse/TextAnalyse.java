package copyToFile.analyse;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;

public class TextAnalyse {

	public static void main(String[] args) {
		String src = "../srcFiles/";
//		String src = "../";
//		System.out.println(TextAnalyse.class.getResource(src));
		String path = TextAnalyse.class.getResource(src).getPath();
//		System.out.println(path);
//		File s = new File(path);
//		System.out.println(s.exists());
//		System.out.println(s.isDirectory());
//		File[] files = s.listFiles();
//		for (int i = 0; i < files.length; i++) {
//			System.out.println(files[i].getName());
//		}
		File file = new File(path + "暴恐词库.txt");
		StringBuilder sBuilder = getWordsFromFileAndAnalyseCharset(file);
		System.out.println(sBuilder.toString());
	}

	public static StringBuilder getWordsFromFileAndAnalyseCharset(File file) {
		StringBuilder sb = new StringBuilder();
		if(file.exists()) {
			FileInputStream fis = null;
			BufferedInputStream bis = null;
			try {
				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				String charset = analyseCharset(bis);
				System.out.println(charset);
				bis.reset();
				System.out.println(bis.available());
				byte[] bytes = new byte[bis.available()];
				int pos = bis.read(bytes);
				sb.append(new String(bytes,Charset.forName(charset)));
//				System.out.println(sb.toString());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				if(fis != null) {
					try {
						fis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return sb;
	}
	
	private static String analyseCharset(BufferedInputStream bis) throws IOException {
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
