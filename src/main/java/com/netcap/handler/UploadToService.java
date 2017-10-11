package com.netcap.handler;

//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
//import java.util.ArrayList;
//import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
//import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.MainFrame;
import com.common.util.JsonUtil;
import com.common.util.LogUtil;
import com.common.util.PropertiesUtil;
import com.generator.bean.DataForJavaBean;
import com.view.util.ViewModules;

/**
 * 上传数据到平台
 * 
 * @author 周叶林
 *
 */
public class UploadToService {
	private static Class<?> cl = UploadToService.class;

	public static void upload(DataForJavaBean bean) {
		String json = JsonUtil.beanToJson(bean);
//		List<String> dataList = readDataFromFile(new java.io.File("D:\\work\\workspace\\JavaNetCap2\\data\\test.json"));
//		String json = dataList.get(0);
//		System.out.println(json);
		LogUtil.debug(cl, "UploadToServiceData---->" + json);
		try {
//			HttpClients httpClient = new DefaultHttpClient();
			CloseableHttpClient httpClient = HttpClients.createDefault();
			String uri = PropertiesUtil.getProperty("uploadDataUrl");
			HttpPost httppost = new HttpPost(uri);
			// 添加http头信息
			httppost.addHeader("Content-Type", "application/json;charset=UTF-8");
			HttpEntity entity = new ByteArrayEntity(json.getBytes("UTF-8"));
			httppost.setEntity(entity);
//			httppost.setEntity(new StringEntity(json));
			CloseableHttpResponse response = httpClient.execute(httppost);
			// 检验状态码，如果成功接收数据
			int code = response.getStatusLine().getStatusCode();
			String msg = response.getStatusLine().getReasonPhrase();
			LogUtil.debug(cl, code + ":" + msg);
//			System.out.println(code + ":" + msg);
//			if (code == 200) {
//				String rev = EntityUtils.toString(response.getEntity(), "UTF-8");
//			}
		} catch (Exception e) {
			LogUtil.err(cl, e);
			ViewModules.showMessageDialog(null, "上传数据失败，请检查网络是否OK!", "Error", 0);
		}
	}
	
	public static Map<String, Object> getProjectMap(){
		Map<String, Object> projectMap = new HashMap<String, Object>();
		
		try {
//			@SuppressWarnings("resource")
//			HttpClient httpClient = new DefaultHttpClient();
			CloseableHttpClient httpClient = HttpClients.createDefault();
			String uri = PropertiesUtil.getProperty("getProjectInfoUrl");
			HttpGet httppost = new HttpGet(uri);
			// 添加http头信息
			httppost.addHeader("Content-Type", "application/json;charset=UTF-8");
			CloseableHttpResponse response = httpClient.execute(httppost);
			// 检验状态码，如果成功接收数据
			int code = response.getStatusLine().getStatusCode();
			String msg = response.getStatusLine().getReasonPhrase();
			LogUtil.debug(cl, code + ":" + msg);
			if (code == 200) {
				String rev = EntityUtils.toString(response.getEntity());
				LogUtil.debug(cl, "prijectInfo--->" + rev);
				projectMap = JsonUtil.jsonToMap(rev);
			}
		} catch (ClientProtocolException e) {
			LogUtil.err(cl, e);
			ViewModules.showMessageDialog(null, "获取项目列表失败！", "Error", 0);
		} catch (IOException e) {
			LogUtil.err(cl, e);
			ViewModules.showMessageDialog(null, "获取项目列表失败！", "Error", 0);
		} catch (Exception e) {
			LogUtil.err(cl, e);
			ViewModules.showMessageDialog(null, "获取项目列表失败！", "Error", 0);
		}
		
		return projectMap;
	}
	
	// 根据value值获取到对应的一个key值
	public static String getProjectId() {
		String key = null;
		// Map,HashMap并没有实现Iteratable接口.不能用于增强for循环.
		for (String getKey : MainFrame.projectMap.keySet()) {
			if (MainFrame.projectMap.get(getKey).equals(MainFrame.projectName)) {
				key = getKey;
			}
		}
		return key;
		// 这个key肯定是最后一个满足该条件的key.
	}
	
//	public static void main(String[] args) {
////		upload(null);
////		String uri = PropertiesUtil.getProperty("conf/setting.properties", "getProjectInfoUrl");
////		System.out.println(uri);
//		Map<String, Object> projectMap = getProjectMap();
//		for (String key : projectMap.keySet()) {
//			 System.out.println("key= "+ key + " and value= " + projectMap.get(key));
//		}
//	}
}
