package com.net.netcap.captor;

import java.io.IOException;

import com.net.common.util.LogUtil;
import com.net.netcap.DataCache;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;

public class NetCaptor {
	private static Class<?> cl = NetCaptor.class;

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
				NetworkInterface nif = DataCache.getDevicesMap().get(DataCache.getNetDevicesName());
				if (null == nif)
					nif = DataCache.getDevicesMap().values().iterator().next();
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
	
}