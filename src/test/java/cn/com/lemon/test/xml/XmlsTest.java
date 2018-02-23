package cn.com.lemon.test.xml;

import org.junit.Before;
import org.junit.Test;

import cn.com.lemon.base.xml.Xmls;
import cn.com.lemon.framework.model.fupin.MessageHeader;
import cn.com.lemon.framework.model.fupin.base.BIPType;

public class XmlsTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		MessageHeader header = new MessageHeader();
		BIPType bipType = new BIPType();
		bipType.setActionCode("0");
		//header.setBipType(bipType);
		header.setVersion("0100");
		header.setTestFlag("1");
		System.out.println(Xmls.generator(header, false, true, MessageHeader.class));
	}

}
