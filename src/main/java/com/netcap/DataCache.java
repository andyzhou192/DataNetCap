package com.netcap;

import java.util.Map;

import com.netcap.handler.ConnectToPlatform;

import jpcap.NetworkInterface;

public class DataCache {

	private static Map<String, Object> projectMap;
	
	private static Map<String, NetworkInterface> devicesMap;
	
	private static String projectName;
	
	private static String netDevicesName;

	private static String captureUrl;
	
	public static Map<String, Object> getProjectMap(){
		if(null == DataCache.projectMap){
			DataCache.projectMap = ConnectToPlatform.getProjectMap();
		}
		return DataCache.projectMap;
	}
	
	public static Map<String, NetworkInterface> getDevicesMap(){
		if(null == DataCache.devicesMap){
			DataCache.devicesMap = NetDevice.getNetDeviceMap();
		}
		return DataCache.devicesMap;
	}
	
	public static String getProjectName() {
		if(null != projectName){
			return projectName;
		} else if(null != DataCache.projectMap && DataCache.projectMap.size() > 0) {
			projectName = DataCache.projectMap.entrySet().iterator().next().getValue().toString();
			return projectName;
		} else {
			return projectName;
		}
	}
	
	public static void setProjectName(String projectName) {
		DataCache.projectName = projectName;
	}
	
	public static String getNetDevicesName() {
		if(null != netDevicesName){
			return netDevicesName;
		} else if(null != DataCache.devicesMap && DataCache.devicesMap.size() > 0) {
			netDevicesName = DataCache.devicesMap.entrySet().iterator().next().getKey().toString();
			return netDevicesName;
		} else {
			return netDevicesName;
		}
	}

	public static void setNetDevicesName(String netDevicesName) {
		DataCache.netDevicesName = netDevicesName;
	}

	public static String getCaptureUrl() {
		return captureUrl;
	}

	public static void setCaptureUrl(String captureUrl) {
		DataCache.captureUrl = captureUrl;
	}
}
