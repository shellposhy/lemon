package cn.com.lemon.test.xml.body;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import cn.com.lemon.base.xml.Xmls;
import cn.com.lemon.framework.model.cmcc.MessageBody;
import cn.com.lemon.framework.model.cmcc.body.DomAdditionResultBody;
import cn.com.lemon.framework.model.cmcc.body.DomFailInfo;
import cn.com.lemon.framework.model.cmcc.body.DomSuccInfo;

public class XmlsAdditionResultBodyTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		DomSuccInfo succInfo1 = new DomSuccInfo("13572811245");
		DomSuccInfo succInfo2 = new DomSuccInfo("13572811246");
		List<DomSuccInfo> succList = new ArrayList<DomSuccInfo>();
		succList.add(succInfo1);
		succList.add(succInfo2);

		DomFailInfo failInfo = new DomFailInfo("13572811247", "03", "02:用户状态不正确或者用户基础通信能力不正确");
		List<DomFailInfo> failList = new ArrayList<DomFailInfo>();
		failList.add(failInfo);

		DomAdditionResultBody additionResultBody  = new DomAdditionResultBody();
		additionResultBody.setTransIDO("SN10011222");
		additionResultBody.setStatus("01");
		additionResultBody.setOperSeq("320101198501247895");
		additionResultBody.setSuccNum(2);
		additionResultBody.setSuccList(succList);
		additionResultBody.setFailNum(1);
		additionResultBody.setFailList(failList);

		String svcCont = DomAdditionResultBody.generator(additionResultBody, false, true);

		MessageBody body = new MessageBody();
		body.setSvcCont(svcCont);

		System.out.println(Xmls.generator(body, true, true, MessageBody.class));

	}

}
