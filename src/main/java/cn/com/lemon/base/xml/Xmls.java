package cn.com.lemon.base.xml;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import static cn.com.lemon.base.Preasserts.checkArgument;
import static cn.com.lemon.base.Strings.isNullOrEmpty;

import com.thoughtworks.xstream.XStream;

/**
 * Static utility methods pertaining to {@code Xmls} primitives.
 * <p>
 * The base utility contain basic operate.
 * <p>
 * Create XML data from Java Object and Parser XML data to Java Object
 * 
 * @author shellpo shih
 * @version 1.0
 */
public final class Xmls {
	private static final Map<String, XStream> XSTREAMS = new ConcurrentHashMap<String, XStream>();
	private static final String DEFAULT_NAME = "FALSE";
	private static final String DEFAULT_XML_HEADER = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";

	private Xmls() {
	}

	static {
		XSTREAMS.put("TRUE", new XStream(new XmlsDriver(true)));
		XSTREAMS.put("FALSE", new XStream(new XmlsDriver(false)));
	}

	public static XStream newInstance() {
		return newInstance(DEFAULT_NAME);
	}

	public static XStream newInstance(String name) {
		XStream stream = XSTREAMS.get(name);
		checkArgument(stream != null);
		return stream;
	}

	/**
	 * Create XML data from Java Object
	 * 
	 * @param data
	 *            {@code Object} the data
	 * @param isUseCDATA
	 *            is use CDATA
	 * @param isContainHeader
	 *            is contail xml header
	 * @param clazz
	 * @return {@link String} the xml data
	 */
	public static String generator(Object data, boolean isUseCDATA, boolean isContainHeader, Class<?> clazz) {
		if (null == data)
			return null;
		XStream xstream = isUseCDATA ? newInstance("TRUE") : newInstance();
		xstream.processAnnotations(clazz);
		xstream.autodetectAnnotations(true);
		return isContainHeader ? DEFAULT_XML_HEADER + xstream.toXML(data) : xstream.toXML(data);
	}

	/**
	 * Create XML data from Java Object and write the XML data to files
	 * 
	 * @param data
	 *            {@code Object} the data
	 * @param xmlFilePath
	 *            write date to xml file
	 * @param isUseCDATA
	 *            is use CDATA
	 * @param isContainHeader
	 *            is contail xml header
	 * @param clazz
	 * @return {@link String} the xml data
	 */
	public static boolean generator(Object data, String xmlFilePath, boolean isUseCDATA, boolean isContainHeader,
			Class<?> clazz) {
		if (null == data || isNullOrEmpty(xmlFilePath))
			return false;
		File file = new File(xmlFilePath);
		FileOutputStream out = null;
		BufferedWriter writer = null;
		try {
			if (!file.exists() && !file.isFile())
				file.createNewFile();
			out = new FileOutputStream(file);
			writer = new BufferedWriter(new OutputStreamWriter(out));
			writer.write(generator(data, isUseCDATA, isContainHeader, clazz));
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (writer != null) {
				try {
					writer.close();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Parser XML data to Java Object
	 * 
	 * @param data
	 *            {@code Object} the data
	 * @param isUseCDATA
	 *            is use CDATA
	 * @param clazz
	 * @return {@link String} the xml data
	 */
	@SuppressWarnings("unchecked")
	public static <T> T parser(String data, boolean isUseCDATA, Class<?> clazz) {
		if (isNullOrEmpty(data)) {
			return null;
		}
		XStream xstream = isUseCDATA ? newInstance("TRUE") : newInstance();
		if (clazz != null) {
			xstream.processAnnotations(clazz);
		}
		return (T) xstream.fromXML(data);
	}

}
