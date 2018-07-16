package cn.com.lemon.test.http;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.util.EntityUtils;
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

		Map<String, String> map = new HashMap<String, String>();
		map.put("type", "1");
		map.put("successCallback", "");

		HttpUriRequest post = httpclients.post(map, "http://www.zgshfp.com.cn/shfp/newIndexData/donate/5/1");
		HttpEntity entity = httpclients.execute(post).getEntity();
		String message;
		try {
			message = EntityUtils.toString(entity, "utf-8");
			System.out.println(message);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
