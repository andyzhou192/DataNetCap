package com.net.netcap.captor;

import com.net.common.util.LogUtil;

public class CaptureThread implements Runnable {
	private Class<?> cl = CaptureThread.class;

	private NetCaptor captor;
	public static Thread T;
    private String threadName;
    private volatile boolean stopped = false;
    private String control = "";//只是任意的实例化一个对象而已,用到Object的wait()方法和notify()和notifyAll()方法控制线程暂停
    private volatile boolean suspend = false;//线程暂停标识
    
    private static CaptureThread capture;
    
	public void setSuspend(boolean suspend) {
        this.suspend = suspend;
	}

	public void setStopped(boolean stopped) {
		this.stopped = stopped;
	}

	private CaptureThread(String threadName){
        this.threadName = threadName;
        this.captor = new NetCaptor();
    }
	
	public static CaptureThread getInstance(String threadName){
		if(null == capture){
			capture = new CaptureThread(threadName);
		}
		return capture;
	}

    public void run() {
    	synchronized(control) {
    		while(!stopped) {
    			if (!suspend) {
    				int packetNum = captor.startCaptor();
    				LogUtil.console(cl, "received packet number : " + packetNum);
    				LogUtil.console(cl, "Thread " +  threadName + " starting.");
    			}
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
		this.setStopped(false);
        if(CaptureThread.T == null){
        	CaptureThread.T = new Thread(this, threadName);
        	CaptureThread.T.start();
        } else if (!CaptureThread.T.isAlive()){
        	CaptureThread.T.start();
        }
    }
    
    /**
     * 停止
     */
    public synchronized void stop(){
		PacketReceiverImpl.STATUS = 0;
		if(null != CaptureThread.T && !CaptureThread.T.isInterrupted()){
			CaptureThread.T.interrupt();
		}
		this.setStopped(true);
    	captor.stopCaptor();
    	CaptureThread.T = null;
    	notify();
    }
    
    /**
     * 暂停
     */
    public synchronized void pause(){
    	PacketReceiverImpl.STATUS = 2;
    	setSuspend(true);
    }
     
     /**
      * 继续
      */
     public synchronized void resume(){
     	PacketReceiverImpl.STATUS = 1;
     	setSuspend(false);
     }
}
