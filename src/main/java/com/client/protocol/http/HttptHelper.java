package com.client.protocol.http;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.client.common.asserts.Assert;
import com.client.common.util.StringUtil;

import jpcap.packet.TCPPacket;

public class HttptHelper {
//	private static Class<?> cl = HttptHelper.class;

	/**
	 * 
	 * @param reqData
	 * @param rspData
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static HTTPDataBean getDataBean(String reqData, String rspData) {
		HTTPDataBean bean = new HTTPDataBean();
		try {
			reqData = URLDecoder.decode(reqData.replaceAll("%(?![0-9a-fA-F]{2})", "%25"), "utf-8");
			rspData = URLDecoder.decode(rspData.replaceAll("%(?![0-9a-fA-F]{2})", "%25"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			reqData = URLDecoder.decode(reqData.replaceAll("%(?![0-9a-fA-F]{2})", "%25"));
			rspData = URLDecoder.decode(rspData.replaceAll("%(?![0-9a-fA-F]{2})", "%25"));
		} finally {
			initRequestData(bean, reqData);
			initResponseData(bean, rspData);
		}
		return bean;
	}
	
	/**
	 * 
	 * @param bean
	 * @param requestData
	 */
	private static void initRequestData(HTTPDataBean bean, String requestData){
		String requestLine = HTTPAnalyzer.getRequestLine(requestData);
		if(null == requestLine) return;
		bean.setMethod(HTTPAnalyzer.getMethod(requestData));
		bean.setProtocol(HTTPAnalyzer.getProtocol(requestData));
		bean.setProtocolVersion(HTTPAnalyzer.getProtocolVersion(requestData));
		
		String url = HTTPAnalyzer.getURL(requestData);
		String host = HTTPAnalyzer.getHost(requestData);
		bean.setUrl(bean.getProtocol().toLowerCase() + "://" + host + url);
		bean.setReqHeader(HTTPAnalyzer.getReqHead(requestData));
		switch(bean.getMethod()){
		case "POST":
			bean.setReqParams(HTTPAnalyzer.getPostParams(requestData));
		case "GET":
			bean.setReqParams(HTTPAnalyzer.getGetParams(requestData));
			break;
		case "PUT":
		case "DELETE":
		case "OPTIONS":
		case "HEAD":
		case "TRACE":
		case "CONNECT":
		default:
			break;
		}
	}
	
	/**
	 * 
	 * @param bean
	 * @param rspData
	 * 
	 **/
	private static void initResponseData(HTTPDataBean bean, String rspData){
		if(!HTTPAnalyzer.isHttpResponse(rspData)) return;
		bean.setStatusCode(HTTPAnalyzer.getStatusCode(rspData));
		bean.setReasonPhrase(HTTPAnalyzer.getReasonPhrase(rspData));
		bean.setRspHeader(HTTPAnalyzer.getRspHeadForJson(rspData));
		String body = HTTPAnalyzer.getRspBody(rspData);
		bean.setRspBody(null != body ? body.replaceAll("(\r\n)+", "\r\n") : null);
	}
	
	/**
	 * 
	 * @param data
	 **/
	public static int getContentLenth(TCPPacket tcpPacket){// Content-Type:multipart/form-data; boundary=
		String data = new String(tcpPacket.data);
		int result = -1000;
		if(isIgnoreEntity(data)){
			result = -1;
		} else if(hasChunked(data)){
			result = -2;
		} else {
			int headerLen = 0;
			String regex = "Content-Type:\\s+multipart/form-data;\\s+boundary=(.*)\\r";
	    	String boundary = StringUtil.getSubStrByRegexIgnoreCase(data, regex);
	    	if(null != boundary){
	    		headerLen = data.lastIndexOf(boundary) + boundary.length();
	    	} else {
	    		headerLen = data.split(HTTPAnalyzer.BLANK_LINE)[0].getBytes().length;
	    	}
			int lenValue = HTTPAnalyzer.getContentLength(data);
			result = headerLen + lenValue;
		}
		return result;
	}

	/**
	 * 
	 * @param data
	 **/
	public static boolean hasChunked(String data){ //Transfer-Encoding: chunked
		return data.contains("Transfer-Encoding: chunked");
	}
	
	/**
	 * 
	 * @param data
	 **/
	public static boolean isLastChunk(String data){
		return ("0".equals(data.split(HTTPAnalyzer.CRLF)[0].trim()));
	}
	
	/**
	 * 
	 * @param data
	 **/
	public static boolean isIgnoreEntity(String data){
		int statusCode = HTTPAnalyzer.getStatusCode(data);
		if(statusCode < 200 || statusCode == 204 || statusCode == 304)
			return true;
		return false;
	}
	
	/**
	 * 
	 * @param url
	 **/
	public static String getInterfaceMethodName(String url){
		Assert.notEmpty(url, "url");
		String fileName = "";
		if(url.contains("?")){
			String temp = url.substring(0, url.lastIndexOf("?") - 1);
			fileName = getInterfaceMethodName(temp);
		} else if(url.contains("/")){
			if(url.trim().endsWith("/")){
				String temp = url.substring(0, url.lastIndexOf("/") - 1);
				fileName = getInterfaceMethodName(temp);
			} else {
				String temp = url.substring(url.lastIndexOf("/") + 1).trim();
				fileName = getInterfaceMethodName(temp);
			}
		} else {
			if(url.contains(":")){
				fileName = url.substring(0, url.lastIndexOf(":") - 1).trim();
			} else {
				fileName = url;
			}
			return fileName;
		}
		return fileName;
	}
	
//	public static void main(String[] args) {
//		HttpDataBean bean = HttptHelper.getDataBean("GET / HTTP/1.1\r\nHost: 172.23.29.173\r\nContent-Type: */*\r\n\r\na=b&c=d", "HTTP/1.1 200 OK\r\nContent-Length: 10\r\n\r\nabcdef");
//		String jsonString = com.common.util.JsonUtil.beanToJson(bean);
//		System.out.println(jsonString);
//		HttpDataBean data = com.common.util.JsonUtil.jsonToBean(jsonString, HttpDataBean.class);
//	}
}
