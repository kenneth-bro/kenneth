package com.kenneth.boot.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.util.ClassUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.common.collect.Maps;

/**
 * 工具类
 * @author liq
 *
 */
public class AppUtil {
	//日志
	private static Logger logger = LoggerFactory.getLogger(AppUtil.class);
	
	/**
	 * 将对象转换为JSON字符数据
	 * @param obj
	 * @return
	 */
	public static String Object2Json(Object obj){
		if(obj == null){
			return null;
		}
		String result = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			//设置时间格式
			objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
			result = objectMapper.writeValueAsString(obj);
		} catch (Exception e) {
			logger.error("[Jackson] Object2Json error ", e);
		}
		return result;
	}
	
	/**
	 * json转为list Object对象
	 * @author liq
	 * @date 2017年12月18日
	 * @param typeDef
	 * @param json
	 * @param clazz
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static <T> List<T> json2ObjectList(T typeDef, String json, Class clazz ) throws JsonParseException, JsonMappingException, IOException{
		List<T> list;
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		
		TypeFactory typeFactory = TypeFactory.defaultInstance();
		list = objectMapper.readValue(json, typeFactory.constructCollectionType(ArrayList.class, clazz));
		return list;
	}
	
	/**
	 * 获取SpringBoot项目项目的绝对路径
	 * @author liq
	 * @date 2017年5月15日
	 * @return
	 */
	public static String getPathByBoot(){
		String osName = System.getProperties().getProperty("os.name").toLowerCase();
		String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
		logger.info("[path] " + osName + ":" + path);
		return path;
	}
	
	/**
	 * 计算时间差，即执行时间差
	 * @author liq
	 * @date 2017年10月9日
	 * @param beginTime 开始时间，long类型
	 * @return
	 */
	public static String countExecTime(long beginTime){
		long endTime = System.currentTimeMillis();
		long msCount = endTime - beginTime;
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS");  
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        String hms = formatter.format(msCount);
		return "TimeConsume: " + msCount +" ms, " + hms;
	}
	
	/**
	 * 返回系统类型
	 * @author liq
	 * @date 2017年12月25日
	 * @return
	 */
	public static String getSystemType(){
		String osType = "windows";
		String os = System.getProperties().getProperty("os.name").toLowerCase();
		if(os.contains("windows")){
			osType = "windows";
		}else if(os.contains("linux")){
			osType = "linux";
		}
		return osType;
	}
	
	/**
	 * 获取请求者的IP地址
	 * @author liq
	 * @date 2017年11月22日
	 * @param request
	 * @return
	 */
	public static String getRequestIp(HttpServletRequest request){
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	/**
	 * 将Bean转换为Map
	 * @author liq
	 * @date 2018年1月3日
	 * @param bean
	 * @return
	 */
	public static <T> Map<String, Object> beanToMap(T bean) {  
	    Map<String, Object> map = Maps.newHashMap();  
	    if (bean != null) {  
	        BeanMap beanMap = BeanMap.create(bean);  
	        for (Object key : beanMap.keySet()) {  
	            map.put(key+"", beanMap.get(key));  
	        }             
	    }  
	    return map;  
	}  
	
	public static <T> Map<String, String> beanToMapStr(T bean) {  
	    Map<String, String> map = Maps.newHashMap();  
	    if (bean != null) {  
	        BeanMap beanMap = BeanMap.create(bean);  
	        for (Object key : beanMap.keySet()) {  
	        	if(beanMap.get(key) != null){
	        		map.put(key+"", beanMap.get(key) + "");  
	        	}
	        }             
	    }  
	    return map;  
	}  
	
	/**
	 * double:两数相加
	 * @author 2015年8月9日 Li
	 * @param d1 
	 * @param d2
	 * @return
	 */
	public static double DDSum(double d1, double d2){
		BigDecimal bd1 = new BigDecimal(Double.toString(d1));
		BigDecimal bd2 = new BigDecimal(Double.toString(d2));
		return bd1.add(bd2).doubleValue();
	}
	
	/**
	 * double:两数相减
	 * @author 2015年8月9日 Li
	 * @param d1 被减数
	 * @param d2 减数
	 * @return
	 */
	public static double DDSub(double d1, double d2){
		BigDecimal bd1 = new BigDecimal(Double.toString(d1));
		BigDecimal bd2 = new BigDecimal(Double.toString(d2));
		return bd1.subtract(bd2).doubleValue(); 
	}
	
	/**
	 * double:两数相乘
	 * @author 2015年8月9日 Li
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static double DDMul(double d1, double d2){
		BigDecimal bd1 = new BigDecimal(Double.toString(d1)); 
	    BigDecimal bd2 = new BigDecimal(Double.toString(d2)); 
	    return bd1.multiply(bd2).doubleValue(); 
	}
	
	/**
	 * 两数相除
	 * @author 2015年8月9日 Li
	 * @param d1 被除数
	 * @param d2 除数
	 * @param scale 精确位数
	 * @return
	 */
	public static double DDDiv(double d1, double d2, int scale){
		if(d2 == 0){
			return 0;
		}
		BigDecimal bd1 = new BigDecimal(Double.toString(d1)); 
	    BigDecimal bd2 = new BigDecimal(Double.toString(d2)); 
	    return bd1.divide(bd2,scale,BigDecimal.ROUND_HALF_UP).doubleValue(); 
	}
	
}
