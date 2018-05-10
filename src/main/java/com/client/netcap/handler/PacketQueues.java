package com.client.netcap.handler;

import java.util.LinkedList;

import com.client.common.util.LogUtil;
import com.client.netcap.DataCache;
import com.client.protocol.http.HTTPAnalyzer;
import com.client.protocol.http.PacketBean;

import jpcap.packet.ARPPacket;
import jpcap.packet.IPPacket;
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
    	
    	public Task(Packet packet){
    		this.packet = packet;
    	}
    	
    	/**
    	 * 
    	 */
        public void packetProcess(){
        	if(packet instanceof IPPacket){
        		IPPacket ipPacket = (IPPacket) packet;
        		switch (ipPacket.protocol) {
        		case IPPacket.IPPROTO_HOPOPT: //0
        			break;
        		case IPPacket.IPPROTO_ICMP: //1
        			break;
        		case IPPacket.IPPROTO_IGMP: //2
        			break;
        		case IPPacket.IPPROTO_IP: //4
        			break;
        		case IPPacket.IPPROTO_TCP: //6
        			TCPPacket tcpPacket = (TCPPacket) ipPacket;
        			assembleTCPPacket(tcpPacket);
        			break;
        		case IPPacket.IPPROTO_UDP: //17
        			//UDPPacket udpPacket = (UDPPacket) ipPacket;
        			break;
        		case IPPacket.IPPROTO_IPv6: //41
        			break;
        		case IPPacket.IPPROTO_IPv6_Route: //43
        			break;
        		case IPPacket.IPPROTO_IPv6_Frag: //44
        			break;
        		case IPPacket.IPPROTO_IPv6_ICMP: //58
        			break;
        		case IPPacket.IPPROTO_IPv6_NoNxt: //59
        			break;
        		case IPPacket.IPPROTO_IPv6_Opts: //60
        			break;
        		default:
        			break;
        		}
    		} else if (packet instanceof ARPPacket){
    			
    		}
        }
        
        /**
    	 * 
    	 * @param data
    	 */
    	private void assembleTCPPacket(TCPPacket tcpPacket) {
    		if(DataCache.HTTP_REQUEST_MAP.containsKey(tcpPacket.ack_num)){
    			DataCache.HTTP_REQUEST_MAP.get(tcpPacket.ack_num).appendData(tcpPacket.data);
    		} else if(DataCache.HTTP_RESPONSE_MAP.containsKey(tcpPacket.ack_num)){
    			PacketBean rspBean = DataCache.HTTP_RESPONSE_MAP.get(tcpPacket.ack_num);
    			if(HTTPAnalyzer.getHeader(rspBean.getDataForStr(), "Transfer-Encoding").contains("chunked")){
    				String dataStr = HTTPAnalyzer.getData(tcpPacket.data);
    				rspBean.appendData(HTTPAnalyzer.removeChunkLengthFlag(rspBean, dataStr).getBytes());
    			} else {
    				rspBean.appendData(tcpPacket.data);
    			}
    			PacketBean packetBean = DataCache.HTTP_RESPONSE_MAP.get(tcpPacket.ack_num);
    			if(HTTPAnalyzer.isResponseFinish(packetBean, tcpPacket)){
    				long reqSeq = DataCache.HTTP_RESPONSE_MAP.get(tcpPacket.ack_num).getSeqNum();
					String requestData = DataCache.HTTP_REQUEST_MAP.get(reqSeq).getDataForStr();
					dealData(requestData, packetBean.getDataForStr());
					DataCache.HTTP_REQUEST_MAP.remove(reqSeq);
					DataCache.HTTP_RESPONSE_MAP.remove(tcpPacket.ack_num);
    			}
    			
    		} else {
    			String data = HTTPAnalyzer.getData(tcpPacket.data);
    			PacketBean bean = new PacketBean(tcpPacket);
    			if(HTTPAnalyzer.isHttpRequest(data)){
    				DataCache.HTTP_REQUEST_MAP.put(tcpPacket.ack_num, bean);
    			} else if(HTTPAnalyzer.isHttpResponse(data)){
    				if(HTTPAnalyzer.getHeader(data, "Transfer-Encoding").contains("chunked")){
    					// 当header中有chunked时，需要去除data中标识chunk大小的十六进制数字
    					bean.setData(HTTPAnalyzer.removeChunkLengthFlag(bean, data).getBytes());
    				}
    				DataCache.HTTP_RESPONSE_MAP.put(tcpPacket.ack_num, bean);
    				
    				PacketBean packetBean = DataCache.HTTP_RESPONSE_MAP.get(tcpPacket.ack_num);
    				if(HTTPAnalyzer.isResponseFinish(packetBean, tcpPacket)){
    					long reqSeq = DataCache.HTTP_RESPONSE_MAP.get(tcpPacket.ack_num).getSeqNum();
    					String requestData = DataCache.HTTP_REQUEST_MAP.get(reqSeq).getDataForStr();
    					dealData(requestData, packetBean.getDataForStr());
    					DataCache.HTTP_REQUEST_MAP.remove(reqSeq);
    					DataCache.HTTP_RESPONSE_MAP.remove(tcpPacket.ack_num);
    				}
    			} else {
    				LogUtil.info(cl, "this packet is not http packet.");
    				LogUtil.info(cl, "packet data ====> " + new String(tcpPacket.data));
    			}
    		}
    		
    	}
    	
    	/**
    	 * 
    	 * @param requestData
    	 * @param responseData
    	 */
    	private void dealData(String requestData, String responseData){
    		startDealDataThread();
            //添加一个任务
    		DataQueues.Task t =new DataQueues.Task(requestData, responseData);
    		DataQueues.add(t); //执行该方法，激活所有对应队列，那两个线程就会开始执行啦
    	}

    	private Thread dealDataThread = null;
    	
    	/**
    	 * 
    	 */
    	private void startDealDataThread(){
    		if(null == dealDataThread){
    			DataAsyncHandler handler = new DataAsyncHandler();
    			//开启线程执行队列中的任务，那就是先到先得了
    			dealDataThread = new Thread(handler);
    			dealDataThread.start();
    		} else if(!dealDataThread.isAlive()){
    			dealDataThread.start();
    		}
    		
    	}
    }
}
