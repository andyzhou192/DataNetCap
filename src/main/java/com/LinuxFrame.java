package com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.common.util.LogUtil;
import com.netcap.DataCache;
import com.netcap.captor.CaptureThread;

public class LinuxFrame {
	
	private static Class<?> cl = LinuxFrame.class;
	
	public LinuxFrame(){
		Map<Integer, String> deviceMap = new HashMap<Integer, String>();
		int i = 0;
		for (String key : DataCache.devicesMap.keySet()) {
			deviceMap.put(i, key);
			LogUtil.console(cl, i + ":" + key);
			i++;
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str = null;
		LogUtil.console(cl, "Please input the sequence to choose a network card:");
		try {
			str = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		LogUtil.console(cl, "Your choice is :" + deviceMap.get(Integer.valueOf(str)));
		DataCache.setNetDevicesName(deviceMap.get(Integer.valueOf(str)));

		if(null != DataCache.projectMap && DataCache.projectMap.size() > 0){
			Map<Integer, String> projectMap = new HashMap<Integer, String>();
			int j = 0;
			for (String key : DataCache.projectMap.keySet()) {
				projectMap.put(j, key);
				LogUtil.console(cl, j + ":" + key);
				j++;
			}
			
			BufferedReader br2 = new BufferedReader(new InputStreamReader(System.in));
			String str2 = null;
			LogUtil.console(cl, "Please input the sequence to choose a project:");
			try {
				str2 = br2.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			LogUtil.console(cl, "Your choice is :" + projectMap.get(Integer.valueOf(str2)));
			DataCache.setProjectName(projectMap.get(Integer.valueOf(str2)));
		}

		CaptureThread.getInstance("CaptureThread").start();
	}
}
