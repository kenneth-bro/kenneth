package com.kenneth.boot.util.security;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 常用算法及加密
 * 
 * @author liq
 * @date 2018年3月6日
 */
public class Alhorithm {
	
	private static final String CHAR_ENCODE = "UTF-8";
	
	private static final String KEY_MD5 = "MD5";

	private static final String[] KEY_SHAS = { "SHA-1", "SHA-256", "SHA-384", "SHA-512" };

	private static final String KEY_PBKDF2 = "PBKDF2WithHmacSHA1";

	/**
	 * 消息摘要加密算法 （32位16进制的数字）- 可用于密码保存（建议添加salt来加强安全性）
	 * 
	 * @author liq
	 * @date 2018年3月6日
	 * @param original
	 *            原文
	 * @return
	 */
	public static String md5(String original) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance(KEY_MD5);
			// 使用指定的字节更新摘要
			mdInst.update(original.getBytes());
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 使用SecureRandom提供的"SHA1PRNG"算法来生产伪随机数
	 * 
	 * @author liq
	 * @date 2018年3月6日
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	private static String getSalt() throws NoSuchAlgorithmException {
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		byte[] salt = new byte[16];
		sr.nextBytes(salt);
		return salt.toString();
	}

	/**
	 * SHA 加密算法，默认采用SHA-512加密
	 * 
	 * @author liq
	 * @date 2018年3月6日
	 * @param original
	 * @return
	 */
	public static String sha(String original) {
		return sha(original, 3);
	}

	/**
	 * SHA 加密算法
	 * 
	 * @author liq
	 * @date 2018年3月6日
	 * @param original
	 *            原文
	 * @param shasIndex
	 *            0、SHA1；1、SHA-256；2、SHA-384；3-SHA-512
	 * @return
	 */
	public static String sha(String original, Integer shasIndex) {
		try {
			MessageDigest md = MessageDigest.getInstance(KEY_SHAS[shasIndex]);
			md.update(original.getBytes());
			byte[] bytes = md.digest(original.getBytes());
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 转十六进制
	 * 
	 * @author liq
	 * @date 2018年3月6日
	 * @param array
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	private static String toHex(byte[] array) throws NoSuchAlgorithmException {
		BigInteger bi = new BigInteger(1, array);
		String hex = bi.toString(16);
		int paddingLength = (array.length * 2) - hex.length();
		if (paddingLength > 0) {
			return String.format("%0" + paddingLength + "d", 0) + hex;
		} else {
			return hex;
		}
	}

	/**
	 * PBKDF2 加密算法（推荐使用于密码，减小暴力攻击速度）
	 * 
	 * @author liq
	 * @date 2018年3月6日
	 * @param original
	 * @return
	 */
	public static String pbkdf2WithHmacSHA1(String original) {
		try {
			int iterations = 1000;
			char[] chars = original.toCharArray();
			byte[] salt = original.getBytes();
			PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
			SecretKeyFactory skf = SecretKeyFactory.getInstance(KEY_PBKDF2);
			byte[] hash = skf.generateSecret(spec).getEncoded();
			return toHex(salt) + toHex(hash);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Base64加密
	 * @author liq
	 * @date 2018年3月6日
	 * @param original
	 * @return
	 */
	public static String encryptBase64(String original) {
		byte[] b = null;
		String s = null;
		try {
			b = original.getBytes(CHAR_ENCODE);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (b != null) {
			s = new BASE64Encoder().encode(b);
			s = s.replaceAll("\r", "").replaceAll("\n", "");
		}
		return s;
	}
	
	/**
	 * Base64解密
	 * @author liq
	 * @date 2018年3月6日
	 * @param original
	 * @return
	 */
	public static String decryptBase64(String original){
		byte[] b = null;
		String result = null;
		if (original != null) {
			BASE64Decoder decoder = new BASE64Decoder();
			try {
				b = decoder.decodeBuffer(original);
				result = new String(b, CHAR_ENCODE);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
