package cn.com.lemon.base.xml;

import java.io.Writer;

import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * XML Pull Parser
 * <p>
 * when use CDATA,set the {@code Boolean} for true
 * 
 * @see XppDriver
 * @author shellpo shih
 * @version 1.0
 */
public class XmlsDriver extends XppDriver {

	private boolean isUseCDATA = false;

	public XmlsDriver() {
		super(new XmlFriendlyNameCoder("__", "_"));
	}

	public XmlsDriver(boolean isUseCDATA) {
		super(new XmlFriendlyNameCoder("__", "_"));
		this.isUseCDATA = isUseCDATA;
	}

	@Override
	public HierarchicalStreamWriter createWriter(Writer out) {
		if (!isUseCDATA) {
			return super.createWriter(out);
		}
		return new PrettyPrintWriter(out) {
			@Override
			public void startNode(String name, @SuppressWarnings("rawtypes") Class clazz) {
				super.startNode(name, clazz);
			}

			@Override
			protected void writeText(QuickWriter writer, String text) {
				if (isUseCDATA) {
					writer.write("<![CDATA[" + text + "]]>");
				} else {
					writer.write(text);
				}
			}
		};
	}
}
