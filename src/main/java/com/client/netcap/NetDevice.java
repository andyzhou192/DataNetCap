package com.client.netcap;

import java.net.SocketException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.client.common.Constants;
import com.client.common.util.LogUtil;
import com.client.common.util.StringUtil;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;

public class NetDevice {
	private static Class<?> cl = NetDevice.class;
	
	public static Map<String, NetworkInterface> getNetDeviceMap(){
		if (Constants.OS_NAME.toLowerCase().contains("linux")){
			return getNetDeviceMapForLinux();
		} else {
			return getNetDeviceMapForWindows();
		}
	}
	
	/**
	 * Windows获取网卡列表Map
	 * @return
	 */
	private static Map<String, NetworkInterface> getNetDeviceMapForWindows(){
		Map<String, NetworkInterface> devicesMap = new HashMap<String, NetworkInterface>();
		Map<String, NetworkInterface> jpcapDevicesMap = new HashMap<String, NetworkInterface>();
		NetworkInterface[] devices = JpcapCaptor.getDeviceList();
		if (devices == null) {
			LogUtil.warn(cl, "Device List is null.");
			return null;
		} 
		for (int i = 0; i < devices.length; i++) {
			for (jpcap.NetworkInterfaceAddress addr : devices[i].addresses) {
				if (addr != null && addr.address != null) {
					String ip = addr.address.getHostAddress();
					LogUtil.debug(cl, "Device IP:" + ip);
					if (StringUtil.isIPV4(ip) || "0.0.0.0".equals(ip.trim())) {
						String desc = devices[i].description == null ? "" : devices[i].description;
						jpcapDevicesMap.put(desc + "[" + ip + "]", devices[i]);
						break;
					}
				}
				continue;
			}
		}
		
		Map<String, String> localDeviceMap = getLocalNetworkInterface();
		Iterator<String> it = jpcapDevicesMap.keySet().iterator();
		while(it.hasNext()){
			String key = it.next();
			String name = key.split("\\[")[0];
			String ip = key.split("\\[")[1].replaceAll("]", "");
			if("0.0.0.0".equals(ip) && localDeviceMap.containsValue(name)){
				Iterator<String> itr = localDeviceMap.keySet().iterator();
				while(itr.hasNext()){
					String localIp = itr.next();
					if(name.equals(localDeviceMap.get(localIp))){
						devicesMap.put(localDeviceMap.get(localIp) + "[" + localIp + "]", jpcapDevicesMap.get(key));
						break;
					}
				}
			} else if(null != localDeviceMap.get(ip)) {
				devicesMap.put(name + "[" + ip + "]", jpcapDevicesMap.get(key));
			} else {
				devicesMap.put(key, jpcapDevicesMap.get(key));
			}
		}
		return devicesMap;
	}

	/**
	 * 通过JpcapCaptor.getDeviceList()获取所有网卡的名称
	 * 
	 * @return
	 */
	private static Map<String, NetworkInterface> getNetDeviceMapForLinux() {
		Map<String, NetworkInterface> devicesMap = new HashMap<String, NetworkInterface>();
		NetworkInterface[] devices = JpcapCaptor.getDeviceList();
		if (devices == null) {
			return null;
		} else {
			for (int i = 0; i < devices.length; i++) {
				for (jpcap.NetworkInterfaceAddress addr : devices[i].addresses) {
					if (addr != null && addr.address != null) {
						String ip = addr.address.getHostAddress();
						if (StringUtil.isIPV4(ip) || "0.0.0.0".equals(ip.trim())) {
							String name = devices[i].name == null ? "" : devices[i].name;
							devicesMap.put(name + "[" + ip + "]", devices[i]);
							break;
						}
					}
					continue;
				}
			}
			return devicesMap;
		}
	}
	
	/**
	 * 通过java.net.NetworkInterface获取本地网卡ip及显示名称
	 * JpcapCaptor.getDeviceList()获取的网卡信息显示名与Windows本地显示不太一致，且有时VPN网卡的ip显示为0.0.0.0
	 * @return 返回本地网卡ip和显示名称的map，key为ip，value为显示名
	 */
	private static Map<String, String> getLocalNetworkInterface(){
		Map<String, String> map = new HashMap<String, String>();
		java.util.Enumeration<java.net.NetworkInterface> e;
		try {
			e = java.net.NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e1) {
			LogUtil.err(cl, e1);
			return map;
		}
		while (e.hasMoreElements()) {
			java.net.NetworkInterface ni = e.nextElement();
			java.util.Enumeration<java.net.InetAddress> addresses = ni.getInetAddresses();
			while (addresses.hasMoreElements()) {
				String ip = addresses.nextElement().getHostAddress();
				if (ip.contains(".")){
					map.put(ip, ni.getDisplayName());
					LogUtil.debug(cl, ip + "[" + ni.getDisplayName() + "]");
				}
			}
		}
		return map;
	}

	public static String displayMac(byte[] mac) {
		String macStr = "";
		for (int i = 0; i < mac.length; i++) {
			byte b = mac[i];
			int intValue = 0;
			if (b >= 0)
				intValue = b;
			else
				intValue = 256 + b;
			// System.out.print(Integer.toHexString(intValue));
			macStr = macStr + Integer.toHexString(intValue);
			if (i != mac.length - 1) {
				// System.out.print("-");
				macStr = macStr + "-";
			}
		}
		return macStr;
	}
}
