package cn.com.lemon.base;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

import info.monitorenter.cpdetector.io.ASCIIDetector;
import info.monitorenter.cpdetector.io.ByteOrderMarkDetector;
import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.JChardetFacade;
import info.monitorenter.cpdetector.io.ParsingDetector;
import info.monitorenter.cpdetector.io.UnicodeDetector;

import static cn.com.lemon.base.Preasserts.checkNotNull;
import static cn.com.lemon.base.Strings.isNullOrEmpty;

/**
 * Static utility methods pertaining to {@code Files} primitives.
 *
 *
 * @author shellpo shih
 * @version 1.0
 */
public final class Files {

	private static final String DEFAULT_ENCODING = "utf-8";

	private Files() {
	}

	/**
	 * Identification of file coding.
	 * <p>
	 * Detector is the detector, which gives the probe task to the specific
	 * detection implementation class.</br>
	 * The cpDetector is built with some commonly used detection implementation
	 * classes, which can be added through the add method, such as
	 * ParsingDetector, JChardetFacade, ASCIIDetector, UnicodeDetector.<br>
	 * Detector returns the detected character set encoding in accordance with
	 * the principle of
	 * "who is the first to return the non-null detection result, whichever is the result"
	 * .The use of three third-party JAR packages: antlr.jar, chardet.jar, and
	 * jargs-1.0. JAR, cpDetector is based on statistical principles and is not
	 * guaranteed to be completely correct.
	 * 
	 * @param file
	 *            {@code File} the check file
	 * @return {@code String} the file encoding char
	 */
	public static String encode(File file) {
		CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();
		// ParsingDetector can be used to check the encoding of a file or
		// character stream, such as HTML, XML, or a character stream, and the
		// parameters in the constructor are used to indicate whether the
		// details of the detection process are displayed, or false.
		detector.add(new ParsingDetector(false));
		detector.add(new ByteOrderMarkDetector());
		// The JChardetFacade encapsulates JChardet, which is provided by the
		// Mozilla organization, which can code for most files.As a result, the
		// detector can meet the requirements of most projects. If you are not
		// comfortable, you can add a few more detectors, such as ASCIIDetector,
		// UnicodeDetector, etc.
		detector.add(JChardetFacade.getInstance());
		// ASCIIDetector is used for the determination of ASCII encoding.
		detector.add(ASCIIDetector.getInstance());
		// UnicodeDetector is used for the determination of Unicode family
		// encoding.
		detector.add(UnicodeDetector.getInstance());
		java.nio.charset.Charset charset = null;
		try {
			charset = detector.detectCodepage(file.toURI().toURL());
			if (null != charset)
				return charset.name();
			else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Identification of file coding.
	 * 
	 * @param filePath
	 *            {@code String} File absolute path
	 * @return {@code String} the file encoding char
	 */
	public static String encode(String filePath) {
		checkNotNull(filePath, "Before check the file encode,the file path is not null!");
		File file = new File(filePath);
		if (file.exists() && file.isFile())
			return encode(file);
		return null;
	}

	/**
	 * File coding converter based on cpdetector test file coding.
	 * <p>
	 * If the file conversion is encoded as empty, the default is encoded in
	 * utf-8.<br>
	 * You can encode a file for a single file or directory.
	 * 
	 * @param path
	 *            {@code String} the file directory or the file path
	 * @param {@code String} the define file encoding,default {@code Charset}
	 *            utf-8
	 * @return
	 */
	public static void transcode(String filePath, String encode) {
		checkNotNull(filePath, "The file path or directory is not null");
		encode = isNullOrEmpty(encode) ? DEFAULT_ENCODING : encode;
		File fileOrDirectory = new File(filePath);
		if (fileOrDirectory.isFile() || fileOrDirectory.isDirectory()) {
			File[] files = fileOrDirectory.listFiles();
			if (null == files)
				return;
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					transcode(files[i].getAbsolutePath(), encode);
				} else {
					String oldEncode = encode(files[i]);
					if (null != oldEncode && !oldEncode.equals(encode)) {
						FileInputStream fis = null;
						InputStreamReader in = null;
						try {
							fis = new FileInputStream(files[i].getAbsolutePath());
							in = new InputStreamReader(fis, oldEncode);
							BufferedReader reader = new BufferedReader(in);
							StringBuffer strBuffer = new StringBuffer();
							String line = null;
							while ((line = reader.readLine()) != null) {
								strBuffer.append(line).append(System.getProperty("line.separator"));
							}
							reader.close();
							in.close();
							fis.close();
							// write new files
							Writer writer = new BufferedWriter(
									new OutputStreamWriter(new FileOutputStream(files[i]), encode));
							writer.write(strBuffer.toString().toCharArray());
							writer.flush();
							writer.close();
						} catch (IOException e) {
						}
					}
				}
			}
		} else {
			checkNotNull(null, "The given file path is not directory or real file path!");
		}
	}
}
