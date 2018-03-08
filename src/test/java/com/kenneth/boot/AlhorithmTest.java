package com.kenneth.boot;

import java.security.NoSuchAlgorithmException;

import org.junit.Test;

import com.kenneth.boot.util.security.Alhorithm;

public class AlhorithmTest {
	@Test
	public void test1() throws NoSuchAlgorithmException{
//		System.out.println(Alhorithm.md5("123"));
//		System.out.println(Alhorithm.getSalt());
//		System.out.println(Alhorithm.sha("我是个"));
//		System.out.println(Alhorithm.pbkdf2WithHmacSHA1("我是实时"));
		System.out.println(Alhorithm.decryptBase64("5L2g5piv6LCB5ZWK"));
	}
}
