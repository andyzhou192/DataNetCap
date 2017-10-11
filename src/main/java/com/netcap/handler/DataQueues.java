package com.netcap.handler;

import java.util.LinkedList;

import com.common.util.LogUtil;
import com.common.util.StringUtil;
import com.generator.bean.DataForJavaBean;
import com.protocol.http.HttptHelper;
import com.MainFrame;

public class DataQueues {
	private static Class<?> cl = DataQueues.class;

	public static LinkedList<Task> queue = new LinkedList<Task>();
	
    /**
     * 假如 参数t 为任务
     * @param t
     */
    public static void add (Task t){
        synchronized (DataQueues.queue) {
        	DataQueues.queue.addFirst(t); //添加任务
        	DataQueues.queue.notifyAll();//激活该队列对应的执行线程，全部Run起来
        }
    }
    
    public static class Task{
    	private static Class<?> cl = Task.class;
    	
    	private String reqData;
    	private String rspData;
    	
    	public Task(String reqData, String rspData){
    		this.reqData = reqData;
    		this.rspData = rspData;
    	}
    	
    	/**
    	 * 
    	 */
        public void dataProcess(){
        	DataForJavaBean bean = HttptHelper.getDataBean(reqData, rspData);
        	bean.setProjectId(UploadToService.getProjectId());
        	if (bean != null && isValidReq(bean.getUrl())) {
            	LogUtil.debug(cl, bean.toJson());
            	UploadToService.upload(bean);
            }
        }
    }
    
    /**
     * 
     * @param url
     * @return
     */
    public static boolean isValidReq(String url){
    	String captureUrl = MainFrame.captureUrl;
    	if(null == url || url.endsWith(".js") || url.endsWith(".css") || url.endsWith(".png") || url.endsWith(".jpg") || url.endsWith(".jpeg") || url.endsWith(".gif")){
    		return false;
    	}else {
    		if(StringUtil.isEmpty(captureUrl) || StringUtil.isBlank(captureUrl)){
    			return true;
    		} else {
    			for(String address : captureUrl.split(",")){
    				LogUtil.debug(cl, "--------->" + url);
    				if(url.contains(address.trim())){
    					return true;
    				}
    			} 
    			return false;
    		}
    			
    	}
    }

}
