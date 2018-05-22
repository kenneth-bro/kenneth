package com.kenneth.boot;

import org.junit.Test;

import com.kenneth.boot.util.request.Request;

public class RequestTest {

//	@Test
	public void test1(){
		String response = Request.requestGet("https://www.google.com", null);
		System.out.println(response);
	}
	
	@Test
	public void test2(){
		String testJson = "{"+
  "\"infile\":"+
  "{"+
    "\"credits\": {"+
      "\"enabled\": false"+
    "},"+
    "\"chart\": {"+
      "\"width\": 500,"+
      "\"height\": 200"+
    "},"+
    "\"title\": {"+
      "\"text\": \"这是测试\""+
    "},"+
    "\"xAxis\": {"+
          "\"categories\": [\"Jan\", \"Feb\", \"Mar\", \"Apr\", \"May\", \"Jun\", \"Jul\", \"Aug\", \"Sep\", \"Oct\", \"Nov\", \"Dec\"]"+
      "},"+
      "\"series\": [{"+
          "\"data\": [1, 3, 2, 4],"+
          "\"type\": \"line\""+
      "}, {"+
          "\"data\": [10, 3, 4, 2],"+
          "\"type\": \"line\""+
      "}]"+
  "}"+
"}";
		String response = Request.requestPostForBody("http://192.168.12.152:8700", testJson);
	}
}
