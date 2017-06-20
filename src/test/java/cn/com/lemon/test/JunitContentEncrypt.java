package cn.com.lemon.test;

import org.junit.Before;
import org.junit.Test;

import cn.com.lemon.util.security.ContentEncryptUtil;

public class JunitContentEncrypt {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testEncrypt() {
		ContentEncryptUtil.decrypt(null, null);
	}

	@Test
	public void testDecrypt() {
		ContentEncryptUtil.encrypt(null, null);
	}

}
