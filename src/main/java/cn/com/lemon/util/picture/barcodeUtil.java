package cn.com.lemon.util.picture;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import sun.misc.BASE64Encoder;

/**
 * 图片base64转换工具类
 * 
 * @author shishb
 * @version 1.0
 */
public class barcodeUtil {

	public static String QRCode(String value, String path) {
		return Code(value, path, null, BarcodeFormat.QR_CODE, 0, 0);
	}

	public static String QRCode(String value, String path, String name) { 
		return Code(value, path, name, BarcodeFormat.QR_CODE, 0, 0);
	}

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
