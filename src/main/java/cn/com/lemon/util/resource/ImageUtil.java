package cn.com.lemon.util.resource;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import static cn.com.lemon.base.Preasserts.checkArgument;

/**
 * The <code>ImageUtil</code> class,the basic image handler
 * <p>
 * The log base on <code>slf4j</code>
 * 
 * @see Ant
 * @see Logger
 * @see File
 * @author shellpo shih
 * @version 1.0
 */
public final class ImageUtil {
	private static final Logger LOG = LoggerFactory.getLogger(ImageUtil.class);

	// not out instance
	private ImageUtil() {
	}

	/**
	 * Cut the picture according to the width and height
	 * 
	 * @param source
	 *            the source image path
	 * @param target
	 *            the target image path
	 * @param width
	 *            Specified width
	 * @param height
	 *            Specified height
	 */
	public static void change(String source, String target, int width, int height) {
		File file = new File(source);
		checkArgument(file.exists() && file.canRead(), "Picture cannot be find when the picture is changed.");
		int[] size = size(source);
		int w = size[0];
		int h = size[1];
		// the change picture width>height
		int hi = width * h / w;
		if (hi >= height) {
			try {
				Thumbnails.of(source).size(width, hi).toFile(target);
				crop(source, target, width, height);
			} catch (IOException e) {
				LOG.debug("=====Change Image Fail!=====");
			}
		} else {
			try {
				int wi = w * height / h;
				Thumbnails.of(source).size(wi, height).toFile(target);
				crop(source, target, width, height);
			} catch (IOException e) {
				LOG.debug("=====Change Image Fail!=====");
			}
		}
	}

	/**
	 * Rotate the image
	 * 
	 * @param source
	 *            the source image path
	 * @param target
	 *            the target image path
	 * @param degree
	 *            the degree
	 */
	public static void rotate(String source, String target, int degree) {
		File file = new File(source);
		checkArgument(file.exists() && file.canRead(), "Picture cannot be find when the picture is rotated.");
		try {
			Thumbnails.of(source).scale(1L).rotate(degree).toFile(target);
		} catch (IOException e) {
			LOG.debug("=====Rotate Image Fail!=====");
		}
	}

	/**
	 * Watermark the image
	 * 
	 * @param source
	 *            the source image path
	 * @param watermark
	 *            the source image path
	 * @param target
	 *            the image save path
	 * 
	 */
	public static void watermark(String source, String watermark, String target) {
		File file = new File(source);
		File waterFile = new File(watermark);
		checkArgument(file.exists() && file.canRead() && waterFile.exists() && waterFile.canRead(),
				"Watermark image and picture cannot be find when watermark is added.");
		try {
			Thumbnails.of(source).watermark(Positions.CENTER, ImageIO.read(waterFile), 0.5f).scale(1L).toFile(target);
		} catch (IOException e) {
			LOG.info("=====Wartermark Image Fail!=====");
		}
	}

	/**
	 * Zoom the image
	 * 
	 * @param source
	 *            the source image path
	 * @param target
	 *            the target image path
	 * @param size
	 *            the appoint size
	 */
	public static void zoom(String source, String target, int width, int height) {
		File file = new File(source);
		checkArgument(file.exists() && file.canRead(), "Picture cannot be find when the picture is zoomed.");
		int[] size = size(source);
		int w = size[0];
		int h = size[1];
		if (w != 0 && h != 0) {
			try {
				Thumbnails.of(source).size(width, height).toFile(target);
			} catch (IOException e) {
				LOG.info("=====Change Image Fail!=====");
			}
		}
	}

	/**
	 * Crop the image
	 * 
	 * @param source
	 *            the source image path
	 * @param target
	 *            the target image path
	 * @param x
	 *            The X coordinate of the upper-left corner of the
	 *            <code>Rectangle</code>.
	 * @param y
	 *            The Y coordinate of the upper-left corner of the
	 *            <code>Rectangle</code>.
	 * @param width
	 *            the crop width of the image
	 * @param height
	 *            the crop height of the image
	 * @return
	 */
	public static void crop(String source, String target, int width, int height) {
		File file = new File(source);
		checkArgument(file.exists() && file.canRead(), "Picture cannot be find when the picture is croped.");
		int[] size = size(target);
		int w = size[0];
		int h = size[1];
		if (width > w)
			width = w;
		if (height > h)
			height = h;
		try {
			Thumbnails.of(source).sourceRegion(Positions.CENTER, width, height).size(width, height).toFile(target);
		} catch (IOException e) {
			LOG.info("=====Image Corp Fail=====");
		}
	}

	/**
	 * Get the image size
	 * 
	 * @param path
	 *            the full image path
	 * @return {@code int} the size of the image
	 */
	public static int[] size(String path) {
		LOG.debug("=====Image Size=====");
		File file = new File(path);
		checkArgument(file.exists() && file.isFile(), "The image file is not exist");
		BufferedImage image = init(file);
		int[] result = new int[] { 0, 0 };
		if (image != null) {
			result[0] = image.getWidth();
			result[1] = image.getHeight();
		}
		return result;
	}

	// ------------private utilities-----------//
	private static BufferedImage init(File file) {
		checkArgument(file.exists() && file.isFile(), "The image file is not exist");
		try {
			return ImageIO.read(file);
		} catch (IOException e) {
			return null;
		}
	}
}
