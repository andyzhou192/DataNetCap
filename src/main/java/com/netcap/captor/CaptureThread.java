package com.netcap.captor;

import com.common.util.LogUtil;

public class CaptureThread implements Runnable {
	private Class<?> cl = CaptureThread.class;

	private NetCaptor captor;
	public Thread t;
    private String threadName;
    private boolean stopped = false;
    
    public CaptureThread(String threadName){
        this.threadName = threadName;
        this.captor = new NetCaptor();
    }

    public void run() {
    	synchronized(this) {
    		while(!stopped) {
    			int packetNum = captor.startCaptor();
    			LogUtil.console(cl, "received packet number : " + packetNum);
    			LogUtil.console(cl, "Thread " +  threadName + " starting.");
    		}
    		LogUtil.console(cl, "Thread " +  threadName + " exiting.");
    	}
    }
    
    /**
     * 开始
     */
    public void start(){
    	LogUtil.console(cl, "Starting " +  threadName );
    	LogUtil.debug(cl, "Starting " +  threadName );
		PacketReceiverImpl.STATUS = 1;
        stopped = false;
        if(t == null){
            t = new Thread(this, threadName);
            t.start();
        } else if (!t.isAlive()){
        	t.start();
        }
    }
    
    /**
     * 停止
     */
    public synchronized void stop(){
		PacketReceiverImpl.STATUS = 0;
    	stopped = true;
    	captor.stopCaptor();
    	notify();
    }
    
    /**
     * 暂停
     */
    public synchronized void suspend(){
    	PacketReceiverImpl.STATUS = 2;
    }
     
     /**
      * 继续
      */
     public synchronized void resume(){
     	PacketReceiverImpl.STATUS = 1;
        notify();
     }
}
