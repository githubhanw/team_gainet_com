package com.giant.zzidc.base.utils;

import java.io.IOException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 景安加解密模块（DES混合BASE64）
 */
public class PlusCut {
	/** DES密钥 */
	private byte[] desKey;
	private static PlusCut plusCut = null;
	/** 默认密钥 */
	private static final String defaultKey = "giant_lulinke";

	/**
	 * 私有构造方法
	 * 
	 * @param key
	 *            密钥
	 */
	private PlusCut(String key) {
		this.desKey = key.getBytes();
	}

	/**
	 * 获取使用默认Key的加解密实例
	 */
	public static PlusCut getInstance() {
		if (plusCut == null) {
			return new PlusCut(defaultKey);
		} else {
			return plusCut;
		}
	}

	/**
	 * 获取指定Key的加解密实例
	 */
	public static PlusCut getInstance(String key) {
		return new PlusCut(key);
	}

	private byte[] desEncrypt(byte[] input) throws Exception {
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey key = keyFactory.generateSecret(new DESKeySpec(desKey));

		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.ENCRYPT_MODE, key, new SecureRandom());
		return cipher.doFinal(input);
	}

	private byte[] desDecrypt(byte[] input) throws Exception {
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey key = keyFactory.generateSecret(new DESKeySpec(desKey));

		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.DECRYPT_MODE, key, new SecureRandom());
		return cipher.doFinal(input);
	}

	public static String base64Encode(byte[] input) {
		if (input == null) {
			return null;
		}
		BASE64Encoder b = new sun.misc.BASE64Encoder();
		return b.encode(input);
	}

	public static byte[] base64Decode(String input) throws IOException {
		if (input == null) {
			return null;
		}
		BASE64Decoder decoder = new BASE64Decoder();
		return decoder.decodeBuffer(input);
	}

	/**
	 * 加密
	 * 
	 * @param input
	 *            要加密的字符串（允许为空）
	 * @return 空字符串 或 加密后的字符串
	 */
	public String plus(String input) {
		if (input == null || "".equals(input)) {
			return "";
		}
		try {
			return base64Encode(desEncrypt(input.getBytes()));
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 解密
	 * 
	 * @param input
	 *            要解密的字符串（允许为空）
	 * @return 空字符串 或 加密后的字符串
	 */
	public String cut(String input) {
		if (input == null || "".equals(input)) {
			return "";
		}
		try {
			return new String(desDecrypt(base64Decode(input)));
		} catch (Exception e) {
			return "";
		}
	}

}
