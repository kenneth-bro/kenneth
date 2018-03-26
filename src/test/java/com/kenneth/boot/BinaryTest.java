package com.kenneth.boot;

import org.junit.Test;

/**
 * 二进制测试
 *
 * @author liq
 * @date 2018年3月18日
 *
 */
public class BinaryTest {

	@Test
	public void StringToBinary(){
		String str = "在中国最早建立分析师数据库（2002年），最早从事量化研究(2002年)，最早建立分析师客观评价体系（2005年），最早推出机器人投顾（Robo-Advisor）(2014年)，并成为该领域第一品牌。";
	    char[] strChar=str.toCharArray();
	    String result="";
	    for(int i=0;i<strChar.length;i++){
	        result +=Integer.toBinaryString(strChar[i])+ " ";
	    }
	    System.out.println(result);
	}
}
