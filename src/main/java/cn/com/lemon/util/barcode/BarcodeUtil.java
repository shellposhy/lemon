package cn.com.lemon.util.barcode;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

import sun.misc.BASE64Encoder;

/**
 * 图片base64转换工具类
 * 
 * @author shishb
 * @version 1.0
 */
public final class BarcodeUtil {
	private BarcodeUtil() {
	}

	/**
	 * Create the QRCode and Return the picture path
	 * 
	 * @param value
	 * @param path
	 * @return {@code String} the QRCode picture path
	 */
	public static String QRCode(String value, String path) {
		return Code(value, path, null, BarcodeFormat.QR_CODE, 0, 0);
	}

	/**
	 * Create the QRCode and Return the picture path
	 * 
	 * @param value
	 * @param path
	 * @param name
	 * @return {@code String} the QRCode picture path
	 */
	public static String QRCode(String value, String path, String name) {
		return Code(value, path, name, BarcodeFormat.QR_CODE, 0, 0);
	}

	/**
	 * Create the common code picture and Return the picture path
	 * <p>
	 * the code picture contains:
	 * <ul>
	 * <li>AZTEC</li>
	 * <li>CODABAR</li>
	 * <li>CODE_39</li>
	 * <li>CODE_93</li>
	 * <li>CODE_128</li>
	 * <li>DATA_MATRIX</li>
	 * <li>EAN_8</li>
	 * <li>EAN_13</li>
	 * <li>ITF</li>
	 * <li>MAXICODE</li>
	 * <li>PDF_417</li>
	 * <li>QR_CODE</li>
	 * <li>RSS_14</li>
	 * <li>RSS_EXPANDED</li>
	 * <li>UPC_A</li>
	 * <li>UPC_E</li>
	 * <li>UPC_EAN_EXTENSION</li>
	 * </ul>
	 * 
	 * @param value
	 * @param filePath
	 * @param name
	 * @param format
	 * @param width
	 * @param height
	 * @return {@code String} the picture code path
	 */
	public static String Code(String value, String filePath, String name, BarcodeFormat format, int width, int height) {
		assert value != null;
		assert filePath != null;
		name = name != null ? name : String.valueOf(System.currentTimeMillis());
		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		try {
			width = width == 0 ? 150 : width;
			height = height == 0 ? 150 : height;
			BitMatrix matrix = new MultiFormatWriter().encode(value, format, width, height, hints);
			Path path = FileSystems.getDefault().getPath(filePath, name + ".png");
			MatrixToImageWriter.writeToPath(matrix, "png", path);
			return filePath + (filePath.endsWith("/") ? "" : "/") + name + ".png";
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (WriterException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Decode the image content and return
	 * 
	 * @param picPath
	 *            the decode image path
	 * @return {@code String} the real content of the code image
	 */
	public static String Decode(String picPath) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(picPath));
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			Binarizer binarizer = new HybridBinarizer(source);
			BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
			Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
			hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
			Result result = new MultiFormatReader().decode(binaryBitmap, hints);
			return result.getText();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (NotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Create the picture base64 string
	 * 
	 * @param fullFileName
	 * @return {@code String} the base64 picture string
	 */
	public static String Base64ImageContent(String fullFileName) {
		try {
			InputStream in = new FileInputStream(fullFileName);
			byte[] data = new byte[in.available()];
			in.read(data);
			BASE64Encoder encoder = new BASE64Encoder();
			in.close();
			return encoder.encode(data);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}
}
