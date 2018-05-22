package com.kenneth.boot.util.request;

import static org.hamcrest.CoreMatchers.nullValue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.handler.codec.http.HttpObject;

/**
 * 发送URL请求
 * @author liq
 * @date 2017年3月17日
 */
public class Request {
	
	private static Logger logger = LoggerFactory.getLogger(Request.class);
	/**
     * POST请求
     * @Medtod requestPostURL
     * @author 2016年9月5日 liq
     * @param url
     * @param formData
     * @return
     */
    public static String requestPost(String url, Map<String, String> formData){
    	try {
    		HttpClient httpClient = new DefaultHttpClient();
        	httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 30000);
        	HttpPost post = new HttpPost(url);
        	//参数编码
        	AppRquestUtil.urlEncode(formData);
        	UrlEncodedFormEntity formEntity = buildFormEntity(formData);
        	if(formEntity != null){
        		post.setEntity(formEntity);
        	}
        	logger.info("POST " + url);
        	HttpResponse response =  httpClient.execute(post);
        	String result = getResult(response);
        	return result;
		} catch (Exception e) {
			logger.error("Http Request:POST error", e);
			return null;
		}
    }
    
    /**
     * POST For Body
     * @author Kenneth-IVS
     * @date 2018年5月22日
     * @param url
     * @param json
     * @return
     */
    public static String requestPostForBody(String url, String json){
    	try {
    		DefaultHttpClient defaultHttpClient = new DefaultHttpClient(new PoolingClientConnectionManager());
    		HttpPost httpPost = new HttpPost(url);
        	httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
        	StringEntity se = new StringEntity(json);
        	se.setContentType("text/json");
        	httpPost.setEntity(se);
        	HttpResponse response = defaultHttpClient.execute(httpPost);
        	String result = getResult(response);
        	return result;
		} catch (Exception e) {
			logger.error("Http Request:POST error", e);
			return null;
		}
    	
    }
    
    
    /**
     * GET请求-标准版
     * @author liq
     * @date 2017年9月14日
     * @param url
     * @param params
     * @return
     */
    public static String requestGet(String url, Map<String, String> params){
    	try {
    		HttpClient httpClient = new DefaultHttpClient();
    		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 30000);
    		if(params != null && !params.isEmpty() && !url.contains("?")){
    			Set<String> keys = params.keySet();
    			url = url + "?";
    			for (String key : keys) {
					url = url + key + "=" + params.get(key) + "&";
				}
    			//去掉最后一个&符号
    			url = url.substring(0, url.length() - 1);
    		}
    		//编码
    		url = AppRquestUtil.urlEncode(url);
    		logger.info("GET " + url);
    		HttpGet get = new HttpGet(url);
    		HttpResponse response = httpClient.execute(get);
    		String result = getResult(response);
        	return result;
		} catch (Exception e) {
			logger.error("Http Request:GET error", e);
			return null;
		}
    }
    
    /**
     * 提取结果
     * @Medtod getResult
     * @author 2016年4月27日 liq
     * @param response
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    public static String getResult(HttpResponse response) throws IllegalStateException, IOException{
    	return readStreamAsStr(response.getEntity().getContent()).trim();
    }
    
    /**
     * 将流转换为字符串
     *
     * @param is
     * @return
     * @throws IOException
     */
    public static String readStreamAsStr(InputStream is) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        WritableByteChannel dest = Channels.newChannel(bos);
        ReadableByteChannel src = Channels.newChannel(is);
        ByteBuffer bb = ByteBuffer.allocate(4096);

        while (src.read(bb) != -1) {
            bb.flip();
            dest.write(bb);
            bb.clear();
        }
        src.close();
        dest.close();
        return new String(bos.toByteArray(), Constants.ENCODING);
    }
    
    /**
     * 构建FormEntity
     * @param formParam
     * @return
     * @throws UnsupportedEncodingException
     */
    private static UrlEncodedFormEntity buildFormEntity(Map<String, String> formParam) throws UnsupportedEncodingException {
        if (formParam != null) {
            List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();

            for (String key : formParam.keySet()) {
                nameValuePairList.add(new BasicNameValuePair(key, formParam.get(key)));
            }
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nameValuePairList);
            formEntity.setContentType(ContentType.CONTENT_TYPE_FORM);
            return formEntity;
        }

        return null;
    }
    
}
