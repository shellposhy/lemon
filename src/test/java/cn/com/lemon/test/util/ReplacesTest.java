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
		Replaces.file("F:\\project\\release\\px1017", "shfp1017.org.cn", "px.shfp1017.org.cn");
	}

}
