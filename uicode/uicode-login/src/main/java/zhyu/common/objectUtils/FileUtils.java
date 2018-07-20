package zhyu.common.objectUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

public final class FileUtils {
	/**
	 * 得到文件的Md5码
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String getFileMD5Code(String file) throws IOException {
		return getFileMD5Code(new File(file));
	}

	/**
	 * 得到文件的Md5码
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String getFileMD5Code(File file) throws IOException {
		if (file.exists() && file.isFile()) {
			return getFileMD5Code(new FileInputStream(file));
		}
		return null;
	}

	/**
	 * 得到文件的Md5码
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static String getFileMD5Code(InputStream is) throws IOException {
		if (is != null) {
			String md5 = getFileMD5Code(IOUtils.toByteArray(is));
			IOUtils.closeQuietly(is);
			return md5;
		}
		return null;
	}

	/**
	 * 得到文件的Md5码
	 * 
	 * @param data
	 * @return
	 * @throws IOException
	 */
	public static String getFileMD5Code(byte[] data) throws IOException {
		if (data != null && data.length > 0) {
			return DigestUtils.md5Hex(data);
		}
		return null;
	}

	/**
	 * 文件扩展名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getExtension(String fileName) {
		if (StringUtils.isBlank(fileName)) {
			return null;
		}
		if (fileName.contains(".")) {
			return fileName.substring(fileName.lastIndexOf("."), fileName.length());
		} else {
			return ".file";
		}
	}
}
