package com.client.netcap.captor;

import java.io.IOException;

import com.client.common.util.LogUtil;
import com.client.netcap.DataCache;

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
				NetworkInterface nif = DataCache.DEVICES_MAP.get(DataCache.NET_DEVICE_NAME);
				if (null == nif)
					nif = DataCache.DEVICES_MAP.values().iterator().next();
				/**
				 *  创建一个与指定设备的连接并返回该连接
				 *  intrface 要打开连接的设备的实例
				 *  snaplen 这个参数不是限制只能捕捉多少数据包，而是限制每一次收到一个数据包，只提取该数据包中前多少字节
				 *  promisc 设置是否混杂模式。处于混杂模式将接收所有数据包，若之后又调用了包过滤函数setFilter()将不起任何作用
				 *  to_ms 这个参数主要用于processPacket()方法，指定超时的时间
				 */
				captor = JpcapCaptor.openDevice(nif, 65535, false, 5000);
				java.util.List<String> domains = DataCache.getCaptureDomains();
				if(null != domains){
					String filter = "ip host ";
					int size = domains.size();
					for(int i = 0; i < size; i++){
						if(i == size - 1){
							filter = filter + domains.get(i);
						} else {
							filter = filter + domains.get(i) + " or ";
						}
					}
					System.out.println("----------->" + filter);
					captor.setFilter(filter, true);
				} else {
					/**
					 *  设定要提取的包的关键字
					 *  condition 过滤的关键字条件
					 *  optimize 是否开启优化模式
					 */
					captor.setFilter("ip and tcp", true);// filter是一个文本,遵循了tcpdump syntax
				}
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
			DataCache.init();
		}
	}
	
}