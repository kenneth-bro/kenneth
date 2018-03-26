package com.kenneth.boot;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenneth.boot.cloud.tencent.youtu.YoutuCall;
import com.kenneth.boot.util.AppUtil;
import com.youtu.Youtu;

public class youtuTest {
	public static final String APP_ID = "10123667";
	public static final String SECRET_ID = "AKIDiEWZWHsAG6g05t8jlX3JJ3YnnX4MuFqC";
	public static final String SECRET_KEY = "GH4kCQPAhz2pNfhU3OULyMWR8bpnWzDB";
	public static final String USER_ID = "329234069"; //qq号
	
	@Test
	public void test1(){
		try {
			long beginTime = System.currentTimeMillis();
			YoutuCall youtuCall = new YoutuCall(APP_ID, SECRET_ID, SECRET_KEY, Youtu.API_YOUTU_END_POINT, USER_ID);
			JSONObject response = youtuCall.handWritingOcr(null, "http://mmbiz.qpic.cn/mmbiz_jpg/LXcMVc2TgC5f4XOIv0x4brYdBXU4e3gHVGhEcpot3ic5M5VMiamUwrf1UkEZDc1sJia85W1fA4eOGKMOedUfSMOAw/0");
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, Object> baseResponse = new HashMap<String, Object>();
			baseResponse = objectMapper.readValue(response.toString(), baseResponse.getClass());
			//最终结果
			List<String> results = new ArrayList<String>();
			String errorMsg = (String) baseResponse.get("errormsg");
			if("OK".equals(errorMsg)){
				List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
				items = (List<Map<String, Object>>) baseResponse.get("items");
				for (Map<String, Object> map : items) {
					String itemString = (String) map.get("itemstring");
					//6位数字
					if(Pattern.matches("^[0-9]{6}", itemString)){
						results.add(itemString);
					}
				}
			}
			System.out.println(results);
			System.out.println(AppUtil.countExecTime(beginTime));
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
