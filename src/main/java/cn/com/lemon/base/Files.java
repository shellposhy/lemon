package cn.com.lemon.base;

import java.io.File;

import info.monitorenter.cpdetector.io.ASCIIDetector;
import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.JChardetFacade;
import info.monitorenter.cpdetector.io.ParsingDetector;
import info.monitorenter.cpdetector.io.UnicodeDetector;

import static cn.com.lemon.base.Preasserts.checkNotNull;

/**
 * Static utility methods pertaining to {@code Files} primitives.
 *
 *
 * @author shellpo shih
 * @version 1.0
 */
public final class Files {

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
		detector.add(new ParsingDetector(false));
		detector.add(JChardetFacade.getInstance());
		detector.add(ASCIIDetector.getInstance());
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
}
