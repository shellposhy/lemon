package cn.com.lemon.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

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
public class PicCodeUtil {

	public static String QRCode(String value, String path) {
		return QRCode(value, path, null, 0, 0);
	}

	public static String QRCode(String value, String path, String name) {
		return QRCode(value, path, name, 0, 0);
	}

	public static String QRCode(String code, String filePath, String name, int width, int height) {
		assert code != null;
		assert filePath != null;
		name = name != null ? name : String.valueOf(System.currentTimeMillis());
		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		try {
			BitMatrix matrix = new MultiFormatWriter().encode(code, BarcodeFormat.QR_CODE, width, height, hints);
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

	public static String Barcode(String code, String path) {
		return Barcode(code, path, null);
	}

	public static String Barcode(String code, String path, String name) {
		assert code != null;
		assert path != null;
		File filePath = new File(path);
		if (!filePath.exists())
			filePath.mkdirs();
		try {
			int dpi = 100;
			Code128Bean code128Bean = new Code128Bean();
			code128Bean.setModuleWidth(UnitConv.in2mm(1.0f / dpi));
			code128Bean.doQuietZone(false);
			name = name != null ? name : String.valueOf(System.currentTimeMillis());
			File outputFile = new File(path + (path.endsWith("/") ? "" : "/") + name + ".png");
			OutputStream out = new FileOutputStream(outputFile);
			BitmapCanvasProvider provider = new BitmapCanvasProvider(out, "image/x-png", dpi,
					BufferedImage.TYPE_BYTE_BINARY, false, 0);
			code128Bean.generateBarcode(provider, code);
			provider.finish();
			out.close();
			return path + (path.endsWith("/") ? "" : "/") + name + ".png";
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
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
