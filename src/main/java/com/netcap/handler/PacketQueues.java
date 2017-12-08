package com.netcap.handler;

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;

import com.common.util.LogUtil;
import com.common.util.PropertiesUtil;
import com.common.util.StringUtil;
import com.protocol.http.HttptHelper;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;

public class PacketQueues {

	public static LinkedList<Task> packetQueue = new LinkedList<Task>();
	
	/**
	 * 假如 参数t 为任务
	 * 
	 * @param T
	 */
	public static void add(Task task) {
		synchronized (PacketQueues.packetQueue) {
			PacketQueues.packetQueue.addFirst(task); // 添加任务
			PacketQueues.packetQueue.notifyAll();// 激活该队列对应的执行线程，全部Run起来
		}
	}
	
	public static class Task{
    	private static Class<?> cl = Task.class;
    	
    	private Packet packet;
    	
    	private static String reqData = "";
    	private static String rspData = "";
    	private static int contentLen = -1000;
    	private static int packetDataLen = 0;

    	public Task(Packet packet){
    		this.packet = packet;
    	}
    	
    	/**
    	 * 
    	 */
        public void packetProcess(){
        	if(packet instanceof TCPPacket){
    			TCPPacket tcpPacket = (TCPPacket) packet;
    			//String dstIP = tcpPacket.dst_ip.getHostAddress();
    			//String srcIP = tcpPacket.src_ip.getHostAddress();
    			assemblePacket(tcpPacket);
    		}
        }
        
        /**
    	 * 
    	 * @param data
    	 */
    	private void assemblePacket(TCPPacket tcpPacket) {
    		String encoding = PropertiesUtil.getProperty("encoding");
    		String data = null;
    		if(StringUtil.isEmpty(encoding)) {
    			data = new String(tcpPacket.data);
    		} else {
    			try {
    				data = new String(tcpPacket.data, encoding);
    			} catch (UnsupportedEncodingException e) {
    				e.printStackTrace();
    			}
    		}
    		String method = HttptHelper.checkHttpRequest(data);
    		if(method != null){
    			if(reqData.length() > 0 && rspData.length() > 0){
    				dealData();
    			}
    			reqData = data;
    			rspData = "";
    		} else {
    			assembleRspPacket(tcpPacket, method);
    		}
    	}

    	/**
    	 * 
    	 * @param data
    	 * @param method
    	 */
    	private void assembleRspPacket(TCPPacket tcpPacket, String method) {
    		String encoding = PropertiesUtil.getProperty("encoding");
    		String data = null;
    		if(StringUtil.isEmpty(encoding)) {
    			data = new String(tcpPacket.data);
    		} else {
    			try {
    				data = new String(tcpPacket.data, encoding);
    			} catch (UnsupportedEncodingException e) {
    				e.printStackTrace();
    			}
    		}
    		if(HttptHelper.isFirstResponse(data)){
    			if("head".equalsIgnoreCase(method)){
    				rspData = data;
    				dealData();
    				return;
    			} else {
    				contentLen = HttptHelper.getContentLenth(tcpPacket);
    			}
    		} else if(rspData.length() < 1){ // 如果非首个响应数据包，且此时响应数据为空，则该包属于请求包
    			reqData = reqData + data;
    			return;
    		} else {
    			LogUtil.info(cl, "response data is not first response packet, and not a request data...");
    		}
    		if(contentLen == -1){ // statusCode < 200 || statusCode == 204 || statusCode == 304
    			rspData = rspData + data;
    			dealData();
    		} else if(contentLen == -2){ //Transfer-Encoding: chunked
    			if(HttptHelper.isLastChunk(data)){
    				dealData();
    			} else{
    				rspData = rspData + data;
    			}
    		} else {
    			packetDataLen = packetDataLen + tcpPacket.data.length;
    			rspData = rspData + data;
    			if(contentLen <= packetDataLen){
    				LogUtil.info(cl, "reqData ---------> " + reqData);
    				LogUtil.info(cl, "rspData =========> " + rspData);
    				dealData();
    			} else{
    				LogUtil.info(cl, "response data has not complete...");
    			}
    		}
    	}

    	/**
    	 * 
    	 */
    	private void dealData() {
    		LogUtil.info(cl, "begin to show and save data...");
    		startDealDataThread();
            //添加一个任务
    		DataQueues.Task t =new DataQueues.Task(reqData, rspData);
    		DataQueues.add(t); //执行该方法，激活所有对应队列，那两个线程就会开始执行啦
    		reqData = "";
    		rspData = "";
    		packetDataLen = 0;
    	}

    	private Thread dealDataThread = null;
    	
    	/**
    	 * 
    	 */
    	private void startDealDataThread(){
    		if(null == dealDataThread){
    			AsyncHandler handler = new AsyncHandler();
    			//开启线程执行队列中的任务，那就是先到先得了
    			dealDataThread = new Thread(handler);
    			dealDataThread.start();
    		} else if(!dealDataThread.isAlive()){
    			dealDataThread.start();
    		}
    		
    	}
    }
}
