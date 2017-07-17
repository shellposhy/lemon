package cn.com.lemon.util.stabilize;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;
import java.util.List;

import static cn.com.lemon.base.Strings.subString;

/**
 * When use {@code Staticizing} create output files,sometimes we need to limit
 * the content.This class provide the method to cut the string.
 * <p>
 * The limit number is different by the different chinese charset.(one chinese
 * in {@code UTF8} length 3,and in {@code GBK} length 2).
 * <p>
 * The default set chinese charset {@code GBK}
 * 
 * @see TemplateMethodModel
 * @see TemplateModelException
 * @see List
 * @see Strings
 * @author shellpo shih
 * @version 1.0
 */
public class PartitionMethod implements TemplateMethodModel {
	public Object exec(@SuppressWarnings("rawtypes") List condition) throws TemplateModelException {
		String content = condition.get(0).toString();
		int limit = Integer.valueOf(condition.get(1).toString()).intValue();
		if (condition.size() > 2) {
			String suffix = condition.get(2).toString();
			return subString(content, limit, suffix, "GBK");
		}
		return subString(content, limit);
	}
}
