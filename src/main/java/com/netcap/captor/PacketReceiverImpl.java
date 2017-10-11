package com.netcap.captor;

//import com.common.util.LogUtil;
import com.netcap.handler.PacketAsyncHandler;
import com.netcap.handler.PacketQueues;
import jpcap.PacketReceiver;
import jpcap.packet.Packet;

@SuppressWarnings("restriction")
public class PacketReceiverImpl implements PacketReceiver {
	
	private Thread packetHandlerThread = null;
	static int index = 1;
	public static int STATUS = 1;
	
	public PacketReceiverImpl(){
	}
	
	/**
	 * 
	 */
	@Override
	public void receivePacket(Packet packet) {
		if(null != packet.data && packet.data.length > 0){
			synchronized(this){
				if(STATUS == 1){
					startPacketThread();
					PacketQueues.Task task = new PacketQueues.Task(packet);
					PacketQueues.add(task);
				} else {
					return;
				}
			}
		}
	}
	
	/**
	 * 
	 */
	private void startPacketThread(){
		if(null == packetHandlerThread){
			PacketAsyncHandler handler = new PacketAsyncHandler();
			//开启线程执行队列中的任务，那就是先到先得了
			packetHandlerThread = new Thread(handler);
			packetHandlerThread.start();
		} else if(!packetHandlerThread.isAlive()){
			packetHandlerThread.start();
		} else {
			//packetHandlerThread.notifyAll();
		}
		
	}

}
