package cn.com.lemon.util.marker;

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

public class FreeMarkerUtil {
	private static final Logger logger = Logger.getLogger(FreeMarkerUtil.class);

	public static boolean publish(String templateDirectoryPath, String templateFileName, Map<String, Object> data,
			String htmlFileName) {
		logger.debug("=====freemarker publish=====");
		try {
			Configuration cfg = new Configuration();
			cfg.setEncoding(Locale.getDefault(), "UTF-8");
			cfg.setDirectoryForTemplateLoading(new File(templateDirectoryPath));
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
