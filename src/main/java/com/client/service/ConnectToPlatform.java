package com.client.service;

import java.util.HashMap;
//import java.util.ArrayList;
//import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
//import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.client.common.Constants;
import com.client.common.util.JsonUtil;
import com.client.common.util.LogUtil;
import com.client.common.util.PropertiesUtil;
import com.client.netcap.DataCache;
import com.client.protocol.http.HTTPDataBean;
import com.client.view.util.ViewModules;

/**
 * 本Java类主要用于与云平台交互，可从云平台获取信息和将抓取到的接口数据上传到云平台
 * 
 * @author 周叶林
 *
 */
public class ConnectToPlatform {
	private static Class<?> cl = ConnectToPlatform.class;

	public static void uploadData(HTTPDataBean bean) {
		String json = JsonUtil.beanToJson(bean);
		LogUtil.debug(cl, "UploadToServiceData---->" + json);
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			String uri = PropertiesUtil.getProperty("uploadDataUrl");
			HttpPost httppost = new HttpPost(uri);
			// 添加http头信息
			httppost.addHeader("Content-Type", "application/json;charset=UTF-8");
			HttpEntity entity = new ByteArrayEntity(json.getBytes("UTF-8"));
			httppost.setEntity(entity);
			CloseableHttpResponse response = httpClient.execute(httppost);
			// 检验状态码，如果成功接收数据
			int code = response.getStatusLine().getStatusCode();
			String msg = response.getStatusLine().getReasonPhrase();
			LogUtil.debug(cl, code + ":" + msg);
		} catch (Exception e) {
			LogUtil.err(cl, e);
			if (Constants.OS_NAME.toLowerCase().contains("windows")) {
				ViewModules.showMessageDialog(null, "上传数据失败，请检查网络是否OK!", "Error", 0);
			}
		}
	}
	
	/**
	 * 获取平台侧项目列表
	 * @return
	 */
	public static Map<String, Object> getProjectMap(){
		Map<String, Object> projectMap = new HashMap<String, Object>();
		try {
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
		} catch (Exception e) {
			LogUtil.err(cl, e);
			if (Constants.OS_NAME.toLowerCase().contains("windows")) {
				//ViewModules.showMessageDialog(null, "获取项目列表失败！", "Error", 0);
				DataCache.setProjectName("获取项目列表失败！");
			} else {
				LogUtil.console(cl, "Failed to get project list... ");
			}
		}
		
		return projectMap;
	}
	
	// 根据value值获取到对应的一个key值
	public static int getProjectId() {
		String[] projectInfo = DataCache.PROJECT_NAME.split(":");
		return Integer.valueOf(projectInfo[projectInfo.length - 1].split("_")[0]);
	}
	
	public static int getModuleId(){
		String[] projectInfo = DataCache.PROJECT_NAME.split(":");
		String[] projectIndexs = projectInfo[projectInfo.length - 1].split("_");
		if(projectIndexs.length > 1)
			return Integer.valueOf(projectIndexs[1]);
		return -1;
	}
	
}
