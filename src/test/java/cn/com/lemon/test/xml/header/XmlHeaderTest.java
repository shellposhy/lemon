package cn.com.lemon.test.xml.header;

import org.junit.Before;
import org.junit.Test;

import cn.com.lemon.base.xml.Xmls;
import cn.com.lemon.framework.model.cmcc.MessageHeader;
import cn.com.lemon.framework.model.cmcc.header.BIPType;
import cn.com.lemon.framework.model.cmcc.header.Routing;
import cn.com.lemon.framework.model.cmcc.header.RoutingInfo;
import cn.com.lemon.framework.model.cmcc.header.TransInfo;

public class XmlHeaderTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		MessageHeader messageHeader = new MessageHeader();
		messageHeader.setVersion("V1.0");
		messageHeader.setTestFlag("01");

		BIPType bipType = new BIPType();
		bipType.setActionCode("0");
		bipType.setBipCode("M12");
		bipType.setActivityCode("10");
		messageHeader.setBipType(bipType);

		RoutingInfo routingInfo = new RoutingInfo();
		routingInfo.setOrigDomain("DOMS");
		routingInfo.setRouteType("00");
		Routing routing = new Routing();
		routing.setHomeDomain("BBSS");
		routing.setRouteValue("998");
		routingInfo.setRouting(routing);
		messageHeader.setRoutingInfo(routingInfo);

		TransInfo transInfo = new TransInfo();
		transInfo.setSessionID("1000411");
		transInfo.setTransIDO("SN10");
		transInfo.setTransIDOTime("20150206164851");
		messageHeader.setTransInfo(transInfo);

		System.out.println(Xmls.generator(messageHeader, false, true, MessageHeader.class));
	}

}
