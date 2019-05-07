package copyToFile.analyse;

import utils.io.ReadUtils;

import java.io.*;
import java.nio.charset.Charset;

public class TextAnalyse {

	public static void main(String[] args) {
		String src = "../test";
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
		File parent = new File(path);
		StringBuilder sb = new StringBuilder();
		if(null != parent && parent.exists() && parent.isDirectory()) {
			String[] fileNames = parent.list(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return !name.endsWith(".xlsx");
				}
			});
			for (int i = 0; i < fileNames.length; i++) {
				System.out.println(fileNames[i]);
				StringBuilder sBuilder = ReadUtils.getWordsFromFileAndAnalyseCharset(new File(path + File.separator + fileNames[i]));
				sb.append(sBuilder);
			}
		}
		System.out.println(sb.toString());
	}



	
}
