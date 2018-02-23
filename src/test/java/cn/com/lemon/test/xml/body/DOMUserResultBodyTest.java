package cn.com.lemon.test.xml.body;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import cn.com.lemon.base.xml.Xmls;
import cn.com.lemon.framework.model.cmcc.MessageBody;
import cn.com.lemon.framework.model.cmcc.body.DomFailInfo;
import cn.com.lemon.framework.model.cmcc.body.DomSuccInfo;
import cn.com.lemon.framework.model.cmcc.body.DomUserResultBody;

public class DOMUserResultBodyTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		DomSuccInfo succInfo1 = new DomSuccInfo("13572811245", "20171012");
		DomSuccInfo succInfo2 = new DomSuccInfo("13572811246", "20171012");
		List<DomSuccInfo> succList = new ArrayList<DomSuccInfo>();
		succList.add(succInfo1);
		succList.add(succInfo2);

		DomFailInfo failInfo = new DomFailInfo("13572811247", "03", "02:用户状态不正确或者用户基础通信能力不正确");
		List<DomFailInfo> failList = new ArrayList<DomFailInfo>();
		failList.add(failInfo);

		DomUserResultBody userResultBody = new DomUserResultBody();
		userResultBody.setTransIDO("SN10011222");
		userResultBody.setStatus("01");
		userResultBody.setOperSeq("320101198501247895");
		userResultBody.setSuccNum(2);
		userResultBody.setSuccList(succList);
		userResultBody.setFailNum(1);
		userResultBody.setFailList(failList);

		String svcCont = DomUserResultBody.generator(userResultBody, false, true);

		MessageBody body = new MessageBody();
		body.setSvcCont(svcCont);

		System.out.println(Xmls.generator(body, true, true, MessageBody.class));

	}

}
