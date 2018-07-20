package zhyu.common.objectUtils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

/**
 * 图片压缩处理
 * 
 * @author 崔素强
 */
public class ImageCompress {
	private Image mImage;
	private int mWidth;
	private int mHeight;

	/**
	 * 构造函数
	 */
	public ImageCompress(String fileName) throws IOException {
		mImage = ImageIO.read(new File(fileName)); // 构造Image对象
		mWidth = mImage.getWidth(null); // 得到源图宽
		mHeight = mImage.getHeight(null); // 得到源图长
	}

	/**
	 * 构造函数
	 */
	public ImageCompress(File file) throws IOException {
		mImage = ImageIO.read(file); // 构造Image对象
		mWidth = mImage.getWidth(null); // 得到源图宽
		mHeight = mImage.getHeight(null); // 得到源图长
	}

	/**
	 * 构造函数
	 */
	public ImageCompress(InputStream input) throws IOException {
		mImage = ImageIO.read(input); // 构造Image对象
		mWidth = mImage.getWidth(null); // 得到源图宽
		mHeight = mImage.getHeight(null); // 得到源图长
	}

	/**
	 * 按照宽度还是高度进行压缩
	 */
	public ByteArrayInputStream compress() throws IOException {
		return compress(1280, 800);
	}

	/**
	 * 按照宽度还是高度进行压缩
	 * 
	 * @param width
	 *            int 最大宽度
	 * @param height
	 *            int 最大高度
	 */
	public ByteArrayInputStream compress(int width, int height) throws IOException {
		if (mWidth / mHeight > width / height) {
			return resizeByWidth(width);
		} else {
			return resizeByHeight(height);
		}
	}

	/**
	 * 按照宽度还是高度进行压缩
	 */
	public void compressToOutputStream(OutputStream outputStream) throws IOException {
		compressToOutputStream(1280, 800, outputStream);
	}

	/**
	 * 按照宽度还是高度进行压缩
	 * 
	 * @param width
	 *            int 最大宽度
	 * @param height
	 *            int 最大高度
	 */
	public void compressToOutputStream(int width, int height, OutputStream outputStream) throws IOException {
		if (mWidth / mHeight > width / height) {
			resizeByWidth(width, outputStream);
		} else {
			resizeByHeight(height, outputStream);
		}
	}

	/**
	 * 以宽度为基准，等比例放缩图片
	 * 
	 * @param w
	 *            int 新宽度
	 */
	public ByteArrayInputStream resizeByWidth(int w) throws IOException {
		int h = (int) (mHeight * w / mWidth);
		return resize(w, h);
	}

	/**
	 * 以宽度为基准，等比例放缩图片
	 * 
	 * @param w
	 *            int 新宽度
	 */
	public void resizeByWidth(int w, OutputStream outputStream) throws IOException {
		int h = (int) (mHeight * w / mWidth);
		resize(w, h, outputStream);
	}

	/**
	 * 以高度为基准，等比例缩放图片
	 * 
	 * @param h
	 *            int 新高度
	 */
	public ByteArrayInputStream resizeByHeight(int h) throws IOException {
		int w = (int) (mWidth * h / mHeight);
		return resize(w, h);
	}

	/**
	 * 以高度为基准，等比例缩放图片
	 * 
	 * @param h
	 *            int 新高度
	 */
	public void resizeByHeight(int h, OutputStream outputStream) throws IOException {
		int w = (int) (mWidth * h / mHeight);
		resize(w, h, outputStream);
	}

	/**
	 * 强制压缩/放大图片到固定的大小
	 * 
	 * @param w
	 *            int 新宽度
	 * @param h
	 *            int 新高度
	 */
	public ByteArrayInputStream resize(int w, int h) throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		resize(w, h, outputStream);
		ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
		outputStream.close();
		return inputStream;
	}

	/**
	 * 强制压缩/放大图片到固定的大小
	 * 
	 * @param w
	 *            int 新宽度
	 * @param h
	 *            int 新高度
	 */
	public void resize(int w, int h, OutputStream outputStream) throws IOException {
		if (outputStream != null && mImage != null) {
			// SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
			BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
			image.getGraphics().drawImage(mImage, 0, 0, w, h, null); // 绘制缩小后的图
			ImageIO.write(image, "JPEG", outputStream);
		}
	}
}
