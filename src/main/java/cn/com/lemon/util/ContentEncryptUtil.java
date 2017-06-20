package cn.com.lemon.util;

import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * The <code>ContentEncryptUtil</code>class is used to encrypt the content.
 * <p>
 * Before encrypt the content,need the <code>Key</code>
 * 
 * @see KeyGenerator
 * @see SecureRandom
 * @see SecretKeySpec
 * @see Cipher
 * @author shishb
 * @version 1.0
 */
public class ContentEncryptUtil {

	/**
	 * Encrypt the content
	 * 
	 * @param content
	 * @param key
	 * @return
	 */
	public static String encrypt(String content, String key) {
		assert content != null;
		assert key != null;
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			random.setSeed(key.getBytes());
			kgen.init(128, random);
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			byte[] byteContent = content.getBytes("utf-8");
			cipher.init(1, secretKeySpec);
			byte[] result = cipher.doFinal(byteContent);
			StringBuffer encryptStr = new StringBuffer();
			for (int i = 0; i < result.length; i++) {
				String hex = Integer.toHexString(result[i] & 0xFF);
				if (hex.length() == 1) {
					hex = '0' + hex;
				}
				encryptStr.append(hex.toUpperCase());
			}
			return encryptStr.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Decrypt the content
	 * 
	 * @param content
	 * @param key
	 * @return
	 */
	public static String decrypt(String content, String key) {
		assert content != null;
		assert key != null;
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(key.getBytes());
			kgen.init(128, secureRandom);
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(2, secretKeySpec);
			byte[] parseByte = new byte[content.length() / 2];
			for (int i = 0; i < content.length() / 2; i++) {
				int high = Integer.parseInt(content.substring(i * 2, i * 2 + 1), 16);
				int low = Integer.parseInt(content.substring(i * 2 + 1, i * 2 + 2), 16);
				parseByte[i] = ((byte) (high * 16 + low));
			}
			byte[] result = cipher.doFinal(parseByte);
			return new String(result);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
