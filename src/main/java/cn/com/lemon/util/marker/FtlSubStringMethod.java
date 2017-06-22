package cn.com.lemon.util.marker;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;
import java.util.List;

import cn.com.lemon.util.StringUtil;

public class FtlSubStringMethod implements TemplateMethodModel {
	public Object exec(@SuppressWarnings("rawtypes") List arguments) throws TemplateModelException {
		String str = arguments.get(0).toString();
		int len = Integer.valueOf(arguments.get(1).toString()).intValue();
		if (arguments.size() > 2) {
			return StringUtil.subStringByByte(str, len, arguments.get(2).toString());
		}
		return StringUtil.subStringByByte(str, len);
	}
}
