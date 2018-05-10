package com.client.netcap.handler;

import com.client.common.util.LogUtil;

public class PacketAsyncHandler implements Runnable {
	private static Class<?> cl = PacketAsyncHandler.class;

	public PacketAsyncHandler(){
	}
	
	@Override
	public void run() {
		while(true){
            synchronized (PacketQueues.packetQueue) {
                while(PacketQueues.packetQueue.isEmpty()){ //
                    try {
                    	PacketQueues.packetQueue.wait(); //队列为空时，使线程处于等待状态
                    } catch (InterruptedException e) {
                    	LogUtil.err(cl, e);
                    }
                    LogUtil.debug(cl, "wait...");
                }
                PacketQueues.Task t= PacketQueues.packetQueue.removeLast(); //得到第一个
                t.packetProcess(); //执行该任务
            }
        }
	}
	
}
