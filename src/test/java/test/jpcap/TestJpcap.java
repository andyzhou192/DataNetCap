package test.jpcap;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.Scanner;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.NetworkInterfaceAddress;
import jpcap.packet.IPPacket;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;

public class TestJpcap {

	public static void main(String[] args) throws IOException {
		try {
			addLibraryDir(System.getProperty("user.dir") + File.separator + "lib");
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scanner console = new Scanner(System.in);
		getDevices();//显示PC机上可用的网卡的信息
		System.out.println("请输入网卡号<0-2>");
		int num = console.nextInt();//获取用户要打开的网卡号
		System.out.println("请输入抓包时间（分钟）");
		int minuets = console.nextInt();//获取用户抓包时间
		oneByOneReceiver(num, minuets);//调用抓包方法
	}
	
	// 获取网络接口列表
	public static void getDevices() {
	    NetworkInterface[] devices = JpcapCaptor.getDeviceList();
	    for (int i = 0; i < devices.length; i++) {
	        System.out.println(i + ": " + devices[i].name + "("
	                + devices[i].description + ")");
	        System.out.println(" datalink: " + devices[i].datalink_name + "("
	                + devices[i].datalink_description + ")");
	        System.out.print(" MAC address:");
	        for (byte b : devices[i].mac_address)
	            System.out.print(Integer.toHexString(b & 0xff) + ":");
	        System.out.println();
	        for (NetworkInterfaceAddress a : devices[i].addresses)
	            System.out.println(" address:" + a.address + " " + a.subnet
	                    + " " + a.broadcast);
	    }
	}
	
	// 使用逐个捕获方法, 从网络接口捕获数据包
	public static void oneByOneReceiver(int index, int time) throws IOException {
	    NetworkInterface[] devices = JpcapCaptor.getDeviceList();
	    JpcapCaptor captor = JpcapCaptor.openDevice(devices[index], 65535,
	            false, 20);
	    File file = new File("packets.txt");
	    System.out.println("------------>" + file.getAbsolutePath());
	    PrintWriter pw = new PrintWriter(new FileWriter(file));
	    // 设置过滤器
	    captor.setFilter("ip", true);
	    int counter = 0;
	    Packet packet;
	    long startTime = System.currentTimeMillis();
	    while (startTime + time * 60 * 10 >= System.currentTimeMillis()) {
	        packet = captor.getPacket();
	        //System.out.println(packet);
	        if(packet instanceof IPPacket){
	        	IPPacket ipPacket = (IPPacket)packet;
	        	if(ipPacket.protocol == IPPacket.IPPROTO_TCP){
	        		TCPPacket tcpPacket = (TCPPacket)ipPacket;
	        		System.out.println(tcpPacket);
	        		System.out.println("=============>dataLen:" + tcpPacket.data.length);
	        		System.out.println("----->len:" + tcpPacket.len);
	        		System.out.println("----->caplen:" + tcpPacket.caplen);
	        		System.out.println(new String(tcpPacket.data,"UTF-8"));
	        	}
	        }
	        if (packet != null) {
	            String ips = packet.toString().split("\\s+")[1];
	            pw.write(ips);
	            pw.println();
	            counter++;
	        }
	    }
	    pw.close();
	    CounterPackets cp = new CounterPackets();
	    cp.readPackets();
	    cp.print();
	    System.out.println("PacketNumbers:" + counter);
	}

	// 动态修改java.library.path的值
	public static void addLibraryDir(String libraryPath) throws IOException {  
        try {  
        	// usr_paths为jdk源码中java.library.path值的存储变量名
            Field field = ClassLoader.class.getDeclaredField("usr_paths");  
            field.setAccessible(true);  
            String[] paths = (String[]) field.get(null);  
            for (int i = 0; i < paths.length; i++) {  
                if (libraryPath.equals(paths[i])) {  
                    return;  
                }  
            }  
  
            String[] tmp = new String[paths.length + 1];  
            System.arraycopy(paths, 0, tmp, 0, paths.length);  
            tmp[paths.length] = libraryPath;  
            field.set(null, tmp);  
        } catch (IllegalAccessException e) {  
            throw new IOException(  
                    "Failed to get permissions to set library path");  
        } catch (NoSuchFieldException e) {  
            throw new IOException(  
                    "Failed to get field handle to set library path");  
        }  
    } 
}
