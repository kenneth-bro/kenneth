package com.kenneth.boot;

import org.junit.Test;

/**
 * Java Lamda 表达式测试
 * @author Kenneth-IVS
 * @date 2018年5月7日
 *
 */
public class LamdaTest {
	
	@Test
	public void test1()
	{
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("线程启动");
			}
		}).start();
		
		//lambda表达式
		new Thread(() -> System.out.println("线程2")).start();
	}
}
