package cn.com.lemon.util.stabilize;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 * Java web project use the class to create static pages by
 * <code>FreeMarker</code>,before using this API's.You need the parameter as
 * follow.
 * <ul>
 * <li>Template Directory</li>
 * <li>Template Name</li>
 * <li>Wrap Data</li>
 * <li>Output Name</li>
 * </ul>
 * 
 * @see Configuration
 * @see Template
 * @see Logger
 * @author shellpo shih
 * @version 1.0
 */
public final class Staticizing {
	private static final Logger logger = Logger.getLogger(Staticizing.class);

	private Staticizing() {
	}

	/**
	 * Create the output files
	 * 
	 * @param templateDirectory
	 * @param templateFileName
	 * @param data
	 * @param htmlFileName
	 * @return {@code true} if the file is created
	 */
	public static boolean publish(String templateDirectory, String templateFileName, Map<String, Object> data,
			String htmlFileName) {
		try {
			Configuration cfg = new Configuration();
			cfg.setEncoding(Locale.getDefault(), "UTF-8");
			cfg.setDirectoryForTemplateLoading(new File(templateDirectory));
			Template template = cfg.getTemplate(templateFileName, "UTF-8");
			template.setEncoding("UTF-8");
			File htmlFile = new File(htmlFileName);
			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(htmlFile), "UTF-8"));
			template.process(data, out);
			out.flush();
			out.close();
		} catch (TemplateException e) {
			logger.error("Publish Error" + templateFileName, e);
			return false;
		} catch (IOException e) {
			logger.error("Publish Error" + templateFileName, e);
			return false;
		}
		return true;
	}

}
