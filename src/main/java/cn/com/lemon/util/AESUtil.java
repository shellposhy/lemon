package cn.com.lemon.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {
	public static String encrypt(String content, String password) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(password.getBytes());
			kgen.init(128, secureRandom);
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			byte[] byteContent = content.getBytes("utf-8");
			cipher.init(1, key);
			byte[] result = cipher.doFinal(byteContent);
			return parseByte2HexStr(result);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String decrypt(String content, String password) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(password.getBytes());
			kgen.init(128, secureRandom);
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(2, key);
			byte[] result = cipher.doFinal(parseHexStr2Byte(content));
			return new String(result);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String parseByte2HexStr(byte[] buf) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	private static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1) {
			return null;
		}
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = ((byte) (high * 16 + low));
		}
		return result;
	}

	public static void main(String[] args) {
		String content = "test";
		String password = "+MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAKI6dhHpUKTrCzdKer37rpD7zxYHIcEAJY5zGefeV+s82FS0aLIDAEvgCWdZZo9bJMQSXbRAE0mKCWbAd0chF012mGxZnPTlTWRbV/HjvgN3V1S4/rcVLjjuUrjTqKFbG3vOPkqY7mZCtvMMNarxxH/+uU2qtnpvLj4ZPNXBUFudAgMBAAECgYBxu76vC/2HwWkZmFO5Acv+xSsH7HQTlQC/f5532n+U4hFgQSLtOgFpMGYnWJv4gROjiqS7C/j5o5a3vNNiwKZOfFpnEs657b7QUmnWzZgNxrhwWKAts0iRJuQhhHCSaHyt70//ZyQlydvcVQ4SceoDFaWhXetlcfRRn4HAinbUgQJBAPL93pbWIbTp34eUMyy0gPXOX3jtfLCDURCNSfY045fHJrTQNY4YfkZQOVsteflyoaor/wJhPrvuFYV4XzexO+0CQQCq6cEAzXZfPjt2bPaxKOS4e4NS/G63DNUgPDLJhA/RZcq90c0xmGCua8ABOskfp2Ovx6vdSzf+NuDY7XWzW4hxAkEAuTq5ATZ+P1DAqrNYR03ZuIb21FIE6PFRTFLH/LzYeYzAfrjw+j7Fk4f4EP8bqgGuJvCe1guXLN8S3Mmjm+qpCQJBAI9iXdXTiTwsFGysy2UjHocar7i+KBAPhvltJ1piHXcZw7XtvgyefnW11bXyYGra3knI5IlZolb2KTo0NOtMnjECQQCIlHHvxZz2RgejOKCPdKsk6Qeh1rUMxjHW3ZW5fBNZv2Lw+LQivUbzOs1dWNYUCFbtUaXtNwp5QOazfZWQdNNp";

		System.out.println("加密前：" + content);
		String encryptResultStr = encrypt(content, password);
		System.out.println("加密后：" + encryptResultStr);

		String decryptResult = decrypt(encryptResultStr, password);
		System.out.println("解密后：" + decryptResult);
	}
}
