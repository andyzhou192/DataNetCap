package com.client.netcap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import java.util.Map.Entry;

import com.client.common.util.StringUtil;
import com.client.protocol.http.PacketBean;
import com.client.service.ConnectToPlatform;

import jpcap.NetworkInterface;

public class DataCache {

	public static Map<String, Object> PROJECT_MAP;
	
	public static Map<String, NetworkInterface> DEVICES_MAP;
	
	public static String PROJECT_NAME;
	
	public static String NET_DEVICE_NAME;

	public static String CAPTURE_URL;
	
	public static Map<Long, PacketBean> HTTP_REQUEST_MAP = new HashMap<Long, PacketBean>();
	public static Map<Long, PacketBean> HTTP_RESPONSE_MAP = new HashMap<Long, PacketBean>();
	
	public static void init(){
		DataCache.PROJECT_MAP = ConnectToPlatform.getProjectMap();
		DataCache.DEVICES_MAP = NetDevice.getNetDeviceMap();
	}
	
//	public static Map<String, Object> getProjectMap(){
//		if(null == DataCache.PROJECT_MAP || DataCache.PROJECT_MAP.isEmpty()){
//			DataCache.PROJECT_MAP = ConnectToPlatform.getProjectMap();
//		}
//		return DataCache.PROJECT_MAP;
//	}
//	
//	public static void refreshProjectMap(){
//		DataCache.PROJECT_MAP = ConnectToPlatform.getProjectMap();
//	}
	
//	public static Map<String, NetworkInterface> getDevicesMap(){
//		if(null == DataCache.DEVICES_MAP || DataCache.DEVICES_MAP.isEmpty()){
//			DataCache.DEVICES_MAP = NetDevice.getNetDeviceMap();
//		}
//		return DataCache.DEVICES_MAP;
//	}
	
//	public static String getProjectName() {
//		if(null != PROJECT_NAME){
//			int indexSep = PROJECT_NAME.lastIndexOf(":");
//			if(indexSep >= 0){
//				String projectId = PROJECT_NAME.substring(indexSep + 1, PROJECT_NAME.length()).trim();
//				if(projectId.matches("^\\d+_?\\d*$") && DataCache.getProjectMap().containsKey(projectId))
//					return PROJECT_NAME;
//			}
//		}
//		if(null != DataCache.getProjectMap() && DataCache.getProjectMap().size() > 0) {
//			Entry<String, Object> firstProject = DataCache.getProjectMap().entrySet().iterator().next();
//			PROJECT_NAME = firstProject.getValue().toString() + ":" + firstProject.getKey();
//			return PROJECT_NAME;
//		} else {
//			return PROJECT_NAME;
//		}
//	}
	
	public static void setProjectName(String projectName) {
		DataCache.PROJECT_NAME = projectName;
	}
	
//	public static String getNetDevicesName() {
//		if(null != NET_DEVICE_NAME){
//			return NET_DEVICE_NAME;
//		} else if(null != DataCache.getDevicesMap() && DataCache.getDevicesMap().size() > 0) {
//			NET_DEVICE_NAME = DataCache.getDevicesMap().entrySet().iterator().next().getKey().toString();
//			return NET_DEVICE_NAME;
//		} else {
//			return NET_DEVICE_NAME;
//		}
//	}

	public static void setNetDevicesName(String netDevicesName) {
		DataCache.NET_DEVICE_NAME = netDevicesName;
	}

//	public static String getCaptureUrl() {
//		return DataCache.CAPTURE_URL;
//	}

	public static void setCaptureUrl(String captureUrl) {
		if(null != captureUrl){
			String capUrls = captureUrl.replaceAll(",\\s*{2,}", ",");
			if(capUrls.endsWith(",")){
				DataCache.CAPTURE_URL = capUrls.substring(0, capUrls.length()-2);
			} else {
				DataCache.CAPTURE_URL = capUrls;
			}
		} else {
			DataCache.CAPTURE_URL = captureUrl;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public static List<String> getCaptureDomains(){
		List<String> domainList = new ArrayList<String>();
		if(null != DataCache.CAPTURE_URL){
			String[] fileters = DataCache.CAPTURE_URL.split(",");
			for (String filter : fileters){
				String domain = StringUtil.getDomainString(filter);
				if(domain.length()>0){
					domainList.add(domain);
				}
			}
			if(fileters.length == domainList.size()){
				return domainList;
			}
		}
		return null;
	}
}
