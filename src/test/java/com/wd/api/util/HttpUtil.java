package com.wd.api.util;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * get和post方法
 * @author Lenovo
 *
 */
public class HttpUtil {

	/**
	 * post方法调用
	 * @param url 地址
	 * @param type 调用类型
	 * @param params 参数
	 * @return
	 */
	public static String doPost(String url,Map<String, String> params) {
		HttpPost post =new HttpPost(url);
		List<BasicNameValuePair> parameters=new ArrayList<BasicNameValuePair>();
		Set<String> keys=params.keySet();
		for (String name : keys) {
			String value=params.get(name);
			parameters.add(new BasicNameValuePair(name, value));
		}
		String result="";
		try {
			//设置编码格式
			post.setEntity(new UrlEncodedFormEntity(parameters,"utf-8"));
			HttpClient client=HttpClients.createDefault();
			HttpResponse response=client.execute(post);
			//状态码
			int code=response.getStatusLine().getStatusCode();
			System.out.println(code);
			result=EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return result;
	}
	
	/**
	 * get方法调用
	 * @param url地址
	 * @param type调用类型
	 * @param params参数
	 * @return
	 */
	public static String doGet(String url,Map<String, String> params) {
		Set<String> keys=params.keySet();
		int mark=0;
		for (String  key: keys) {
			if (mark==0) {
				url+=("?"+key+"="+params.get(key));
			}else {				
				url+=("&"+key+"="+params.get(key));
			}
			mark++;
		}
		
		HttpGet get=new HttpGet(url);
		HttpClient client=HttpClients.createDefault();
		String result="";
		try {
			HttpResponse response=client.execute(get);
			int code=response.getStatusLine().getStatusCode();
			System.out.println(code);
			result=EntityUtils.toString(response.getEntity());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return result;
		
	}
	
	/**根据传入的参数选择是post或get
	 * @param url
	 * @param type
	 * @param params
	 * @return
	 */
	public static String doService(String url,String type,Map<String, String>params) {
		String result="";
		if ("post".equalsIgnoreCase(type)) {
			result=HttpUtil.doPost(url, params);
		}else if ("get".equalsIgnoreCase(type)) {			
			result=HttpUtil.doGet(url,  params);
		}
		return result;
	}
	
	
}
