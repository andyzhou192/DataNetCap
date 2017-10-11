package com.netcap.captor;

import java.io.IOException;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.common.util.LogUtil;
import com.common.util.StringUtil;
import com.MainFrame;
import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;

@SuppressWarnings("restriction")
public class NetCaptor {
	private static Class<?> cl = NetCaptor.class;

	public static Map<String, NetworkInterface> devicesMap = NetCaptor.getNetDeviceMap();
	private JpcapCaptor captor = null;
	private PacketReceiverImpl receiver = null;

	public NetCaptor() {
		initCaptor();
		this.receiver = new PacketReceiverImpl();
	}

	/**
	 * 输入配置信息
	 */
	public void initCaptor() {
		if (null == captor) {
			try {
				NetworkInterface nif = NetCaptor.devicesMap.get(MainFrame.netDevicesName);
				if (null == nif)
					nif = NetCaptor.devicesMap.values().iterator().next();
				captor = JpcapCaptor.openDevice(nif, 65535, false, 5000);
				captor.setFilter("tcp", true);
			} catch (IOException e) {
				LogUtil.err(cl, e);
			}
		}
	}

	/**
	 * 
	 * @param parent
	 */
	public int startCaptor() {
		if (null == captor) {
			initCaptor();
		}
		return captor.loopPacket(-1, receiver);
	}

	/**
	 * 
	 */
	public void stopCaptor() {
		if (null != captor) {
			captor.breakLoop();
			captor.close();
			captor = null;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	private static Map<String, NetworkInterface> getNetDeviceMap(){
		Map<String, NetworkInterface> devicesMap = new HashMap<String, NetworkInterface>();
		Map<String, NetworkInterface> jpcapDevicesMap = getNetDeviceMapFromJpcap();
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
	private static Map<String, NetworkInterface> getNetDeviceMapFromJpcap() {
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
							String desc = devices[i].description == null ? "" : devices[i].description;
							devicesMap.put(desc + "[" + ip + "]", devices[i]);
							LogUtil.debug(cl, desc + "[" + ip + "]");
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

//	public static void main(String[] args) throws java.net.SocketException {
		// javax.swing.JFrame frame = new javax.swing.JFrame();
		// JcaptureSettingView inst = new JcaptureSettingView(frame);
		// inst.setVisible(true);
//		getNetDeviceMap();
//		java.util.Enumeration<java.net.NetworkInterface> e = java.net.NetworkInterface.getNetworkInterfaces();
//		while (e.hasMoreElements()) {
//			java.net.NetworkInterface ni = e.nextElement();
//			System.out.println("displayname: " + ni.getDisplayName());
//			System.out.println("name: " + ni.getName());
//			System.out.println("MTU: " + ni.getMTU());
//			System.out.println("Loopback: " + ni.isLoopback());
//			System.out.println("Virtual: " + ni.isVirtual());
//			System.out.println("Up: " + ni.isUp());
//			System.out.println("PointToPoint: " + ni.isPointToPoint());
//			java.util.Enumeration<java.net.InetAddress> addresses = ni.getInetAddresses();
//			while (addresses.hasMoreElements()) {
//				String ip = addresses.nextElement().getHostAddress();
//				if (ip.contains(".")){
//					System.out.println("displayname: " + ni.getDisplayName());
//					System.out.println(ip);
//				}
//			}
			// byte[] mac = ni.getHardwareAddress();
			// if (mac != null)
			// System.out.println(displayMac(mac));
			// else
			// System.out.println("mac is null");
			// System.out.println("-----");
//		}

		// NetworkInterface[] devices = JpcapCaptor.getDeviceList();
		// if (devices == null) {
		// return;
		// } else {
		// String[] names = new String[devices.length];
		// for (int i = 0; i < names.length; i++) {
		// names[i] = (devices[i].description == null ? devices[i].name :
		// devices[i].description);
		// System.out.println(" : " + devices[i].name);
		// System.out.println(" : " + devices[i].description);
		// System.out.println(" : " +
		// JcaptureSettingView.displayMac(devices[i].mac_address));
		// System.out.println(" : " +
		// devices[i].addresses[1].address.getHostAddress());
		// System.out.println();
		// }
		// }
//	}

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