package cn.com.lemon.test.util;

import org.junit.Before;
import org.junit.Test;

import cn.com.lemon.base.util.Replaces;

public class ReplacesTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testFileStringStringString() {
		Replaces.file("C:\\Users\\Administrator\\Desktop\\bxy", "zgshfp.org.cn", "zgshfp.com.cn");
	}

}
