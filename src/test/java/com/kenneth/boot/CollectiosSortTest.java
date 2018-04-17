package com.kenneth.boot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

/**
 * 排序测试
 * @author Kenneth-IVS
 * @date 2018年4月17日
 *
 */
public class CollectiosSortTest {

	@Test
	public void test1(){
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> m1 = new HashMap<String, Object>();
		m1.put("t", 1);
		Map<String, Object> m2 = new HashMap<String, Object>();
		m2.put("t", 3);
		Map<String, Object> m3 = new HashMap<String, Object>();
		m3.put("t", 2);
		Map<String, Object> m4 = new HashMap<String, Object>();
		m4.put("t", 2);
		
		result.add(m1);
		result.add(m2);
		result.add(m3);
		result.add(m4);
		
		Collections.sort(result, new Comparator<Map<String, Object>>() {

			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				int flag1 = (int) o1.get("t");
				int flag2 = (int) o2.get("t");
				return flag2 - flag1;
			}
		});
		
		//Lambda表达式
//		result.sort((Map<String, Object> o1, Map<String, Object> o2) -> (int)o1.get("t") - (int)o2.get("t"));
		System.out.println(result);
	}
}
