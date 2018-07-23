package cn.com.lemon.test.http;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import cn.com.lemon.http.client.Httpclients;

public class HttpclientsTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testExecute() {
		Httpclients httpclients = Httpclients.init("zgshfp.com.cn", 80);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "卢海市");
		map.put("idCard", "431125199712020611");
		
		System.out.println(httpclients);
	}

}
