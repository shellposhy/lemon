package cn.com.lemon.test.xml.body;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import cn.com.lemon.base.xml.Xmls;
import cn.com.lemon.framework.model.cmcc.MessageBody;
import cn.com.lemon.framework.model.cmcc.body.EcUserData;
import cn.com.lemon.framework.model.cmcc.body.EcUserInfoBody;

public class ECUserinfoBodyTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		EcUserData userData1 = new EcUserData("13572811245", "01", "10", "1");
		EcUserData userData2 = new EcUserData("13572811246", "01", "12", "1");
		List<EcUserData> list = new ArrayList<EcUserData>();
		list.add(userData1);
		list.add(userData2);

		EcUserInfoBody userInfoBody = new EcUserInfoBody();
		userInfoBody.setProductID("11111");
		userInfoBody.setList(list);
		userInfoBody.setEffRule("0");

		String svcCont = EcUserInfoBody.generator(userInfoBody, false, true);

		MessageBody body = new MessageBody();
		body.setSvcCont(svcCont);

		System.out.println(Xmls.generator(body, true, true, MessageBody.class));

	}

}
