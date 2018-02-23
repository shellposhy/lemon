package cn.com.lemon.test.xml.body;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import cn.com.lemon.base.xml.Xmls;
import cn.com.lemon.framework.model.cmcc.MessageBody;
import cn.com.lemon.framework.model.cmcc.body.EcAdditionInfoBody;
import cn.com.lemon.framework.model.cmcc.body.EcUserData;

public class ECAdditionInfoBodyTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		EcUserData userData1 = new EcUserData("13572811245", "10");
		EcUserData userData2 = new EcUserData("13572811246", "12");
		List<EcUserData> list = new ArrayList<EcUserData>();
		list.add(userData1);
		list.add(userData2);

		EcAdditionInfoBody additionInfoBody = new EcAdditionInfoBody();
		additionInfoBody.setProductID("11111");
		additionInfoBody.setList(list);

		String svcCont = EcAdditionInfoBody.generator(additionInfoBody, false, true);

		MessageBody body = new MessageBody();
		body.setSvcCont(svcCont);

		System.out.println(Xmls.generator(body, true, true, MessageBody.class));

	}

}
