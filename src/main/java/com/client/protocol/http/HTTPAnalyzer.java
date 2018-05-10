package com.client.protocol.http;

import java.nio.charset.Charset;

import com.client.common.Constants;
import com.client.common.util.PropertiesUtil;
import com.client.common.util.StringUtil;
import com.client.netcap.DataCache;

import jpcap.packet.TCPPacket;
import net.sf.json.JSONObject;

public class HTTPAnalyzer {
	
	public final static String CRLF = "\r\n";
	
	public final static String PROTOCOL = "(HTTP)/[0,1].[0,1]";
	
	public final static String VERSION = "HTTP/([0,1].[0,1])";
	
	public final static String BLANK_LINE = "(\\r\\n)+\\s*" + "(\\r\\n)";
	
	public final static String REQ_REGEX = "[a-zA-Z]{3,7} .* HTTP/[0,1].[0,1]";
	
	public final static String REQ_LINE_REGEX = "([a-zA-Z]{3,7} .* HTTP/[0,1].[0,1])";
	
	public final static String REQ_MEHTOD_REGEX = "(GET|POST|PUT|DELETE|HEAD|OPTIONS|TRACE|CONNECT) .* HTTP/[0,1].[0,1]";
	
	public final static String REQ_URL_REGEX = "[a-zA-Z]{3,7} (.*)\\?\\\\?.* HTTP/[0,1].[0,1]";
	
	public final static String REQ_URL_PARAM_REGEX = "[a-zA-Z]{3,7} .*\\?\\\\?(.*) HTTP/[0,1].[0,1]";
	
	public final static String REQ_HOST_REGEX = "HOST:\\s+(.*)";
	
	public final static String REQ_COOKIE_REGEX = "Cookie:\\s+(.*)";
	
	public final static String CONTENTTYPE_REGEX = "Content-Type:\\s+(.*)";
	
	public final static String CONTENTLENGTH_REGEX = "Content-Length:\\s+(\\d+)";
	
	public final static String RSP_REGEX = "^HTTP/1.[0,1] [0-9]{0,3} *";
	
	public final static String RSP_STATUSLINE_REGEX = "(^HTTP/1.[0,1] [0-9]{0,3} *)";
	
	public final static String RSP_STATUSCODE_REGEX = "^HTTP/1.[0,1] ([0-9]{0,3}) *";
	
	public final static String RSP_STATUSMSG_REGEX = "^HTTP/1.[0,1] [0-9]{0,3} (.*)";
	
	public final static String RSP_SETCOOKIE_REGEX = "Set-Cookie:\\s+(.*)";
	
//	public static void main(String[] args) {
//		String reqdata = "POST http://112.54.207.52:30905/allInOne/login?timestamp=1513299819 HTTP/1.1\r\nHost: 112.54.207.52:30905\r\nConnection: keep-alive\r\nContent-Length: 345\r\nAccept: application/json, text/plain, */*\r\nOrigin: http://112.54.207.52:30905\r\nUser-Agent: Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36\r\nContent-Type: multipart/form-data; boundary=----WebKitFormBoundary6vsIc901qLucqrGZ\r\nReferer: http://112.54.207.52:30905/allInOne/crow/index.html\r\nAccept-Encoding: gzip, deflate\r\nAccept-Language: zh-CN,zh;q=0.8\r\nCookie: SESSION=958bdc3b-a088-44e6-9531-bebaef867a6b\r\n\r\n------WebKitFormBoundary6vsIc901qLucqrGZ\r\nContent-Disposition: form-data; name=\"userName\"\r\n\r\nadmin\r\n------WebKitFormBoundary6vsIc901qLucqrGZ\r\nContent-Disposition: form-data; name=\"password\"\r\n\r\n123456\r\n------WebKitFormBoundary6vsIc901qLucqrGZ\r\nContent-Disposition: form-data; name=\"validCode\"\r\n\r\n1234\r\n------WebKitFormBoundary6vsIc901qLucqrGZ--";
//		System.out.println(StringUtil.getSubStrByRegex(reqdata, "(" + REQ_REGEX + ")"));
//		System.out.println(StringUtil.getSubStrByRegex(reqdata, REQ_LINE_REGEX));
//		System.out.println(StringUtil.getSubStrByRegex(reqdata, REQ_MEHTOD_REGEX));
//		System.out.println(StringUtil.getSubStrByRegex(reqdata, REQ_URL_REGEX));
//		System.out.println(StringUtil.getSubStrByRegex(reqdata, REQ_URL_PARAM_REGEX));
//		System.out.println(StringUtil.getSubStrByRegexDotall(reqdata, REQ_HEAD_REGEX));
//		System.out.println(StringUtil.getSubStrByRegexIgnoreCase(reqdata, REQ_HOST_REGEX));
//		System.out.println(getReqHead(reqdata));
//		
//		String rspData = "HTTP/1.1 200\r\nX-Content-Type-Options: nosniff\r\nX-XSS-Protection: 1; mode=block\r\nCache-Control: no-cache, no-store, max-age=0, must-revalidate\r\nPragma: no-cache\r\nExpires: 0\r\nX-Frame-Options: DENY\r\nSet-Cookie: SESSION=f8f7fc03-bc9f-42c4-96a7-2fd84c8e10b1;path=/allInOne/;HttpOnly\r\nContent-Type: application/json;charset=UTF-8\r\nTransfer-Encoding: chunked\r\nDate: Fri, 15 Dec 2017 01:02:09 GMT\r\n\r\n362\r\n{\"msg\":\"成功\",\"data\":{\"accountNonExpired\":true,\"accountNonLocked\":true,\"authorities\":[{\"authority\":\"1000\"},{\"authority\":\"3000\"},{\"authority\":\"3100\"},{\"authority\":\"3200\"},{\"authority\":\"3300\"},{\"authority\":\"3400\"},{\"authority\":\"3500\"},{\"authority\":\"4000\"},{\"authority\":\"4100\"},{\"authority\":\"4200\"},{\"authority\":\"4300\"},{\"authority\":\"4400\"},{\"authority\":\"8000\"},{\"authority\":\"9000\"}],\"credentialsNonExpired\":true,\"enabled\":true,\"name\":\"amdin\",\"orgId\":1,\"orgName\":\"中国移动\",\"regionIds\":[0],\"roleIds\":[0],\"userId\":0,\"username\":\"admin\"},\"recode\":200}\r\n0";
//		System.out.println(StringUtil.getSubStrByRegex(rspData, "(" + RSP_REGEX + ")"));
//		System.out.println(StringUtil.getSubStrByRegex(rspData, RSP_STATUSLINE_REGEX));
//		System.out.println(StringUtil.getSubStrByRegex(rspData, RSP_STATUSCODE_REGEX));
//		System.out.println(StringUtil.getSubStrByRegex(rspData, RSP_STATUSMSG_REGEX));
//		System.out.println(StringUtil.getSubStrByRegex(rspData, RSP_SETCOOKIE_REGEX));
//		System.out.println(getRspHead(rspData));
//		System.out.println(getRspBody(rspData));
//	}
	
	/**
	 * 获取HTTP协议版本
	 * @param data
	 * @return
	 */
	public static String getHttpVersion(String data){
		return StringUtil.getSubStrByRegex(data, HTTPAnalyzer.VERSION);
	}
	
	public static int getContentLengthWithHead(String data){
		int headerLen = 0;
		String regex = "Content-Type:\\s+multipart/form-data;\\s+boundary=(.*)\\r";
    	String boundary = StringUtil.getSubStrByRegexIgnoreCase(data, regex);
    	if(null != boundary){
    		headerLen = (data.substring(0, data.lastIndexOf(boundary)) + boundary).getBytes().length;
    	} else {
    		headerLen = data.split(HTTPAnalyzer.BLANK_LINE)[0].getBytes().length;
    	}
		int lenValue = HTTPAnalyzer.getContentLength(data);
		return (headerLen + lenValue);
	}
	
	/**
	 * 
	 * @param data
	 * @param name
	 * @return
	 */
	public static String getHeader(String data, String name){
		String regex = name + ": (.*)";
		String header = StringUtil.getSubStrByRegexIgnoreCase(data, regex);
		return null == header ? "" : header;
	}

	/**
	 * 判断给定字符串是否为http请求
	 * @param data
	 * @return
	 */
	public static boolean isHttpRequest(String data){
		//判断是否为HTTP 报文（通过正则表达式 判断数据是否具有HTTP 协议的格式）  
		//String regex = "[a-zA-Z]{3,7} .* HTTP/1.[0,1]";
        return StringUtil.contain(data, HTTPAnalyzer.REQ_REGEX);
	}
	
	/**
	 * 获取http请求的method
	 * @param requestData
	 * @return
	 */
	public static String getMethod(String requestData){
		// 把要匹配的字符串写成正则表达式，然后要提取的字符使用括号括起来
		//String regex = "(GET|POST|PUT|DELETE|HEAD|OPTIONS|TRACE|CONNECT)";
		return StringUtil.getSubStrByRegexIgnoreCase(requestData, HTTPAnalyzer.REQ_MEHTOD_REGEX);
	}
	
	public static float getProtocolVersion(String requestData){
		return Float.valueOf(StringUtil.getSubStrByRegexIgnoreCase(requestData, HTTPAnalyzer.VERSION));
	}
	
	public static String getProtocol(String requestData){
		return StringUtil.getSubStrByRegexIgnoreCase(requestData, HTTPAnalyzer.PROTOCOL);
	}
	
	/**
	 * 
	 * @param requestData
	 * @return
	 */
	public static String getURL(String requestData){
		String url = StringUtil.getSubStrByRegexIgnoreCase(requestData, HTTPAnalyzer.REQ_URL_REGEX);
		return (null == url ? "" : url);
	}
	
	/**
	 * 
	 * @param requestData
	 * @return
	 */
	public static String getHost(String requestData){
		return StringUtil.getSubStrByRegexIgnoreCase(requestData, HTTPAnalyzer.REQ_HOST_REGEX);
	}
	
	/**
	 * 获取post请求的参数
	 * @param requestData
	 * @return
	 */
	public static JSONObject getPostParams(String requestData){
		String regex = "HTTP/1.[0,1]\\s*" + Constants.NEW_LINE + "(.*)";
		String data = StringUtil.getSubStrByRegexDotall(requestData, regex);
		if(null == data || data.split(HTTPAnalyzer.BLANK_LINE).length < 2)
			return null;
		String params = data.split(HTTPAnalyzer.BLANK_LINE)[1];
		JSONObject requestParam = new JSONObject();
		if (null != params){
			for (String nv : params.split("&")) {
				if (StringUtil.validate(nv)) {
					String name = nv.split("=")[0];
					String value = nv.split("=").length > 1 ? nv.split("=")[1].trim() : "";
					requestParam.put(name, value);
				}
			}
		}
		return requestParam;
	}
	
	/**
	 * 获取get请求的参数
	 * @param requestData
	 * @return
	 */
	public static JSONObject getGetParams(String requestData){
		String params = StringUtil.getSubStrByRegex(requestData, REQ_URL_PARAM_REGEX);
		JSONObject requestParam = new JSONObject();
		if (null != params){
			for (String nv : params.split("&")) {
				if (StringUtil.validate(nv)) {
					String name = nv.split("=")[0];
					String value = nv.split("=").length > 1 ? nv.split("=")[1].trim() : "";
					requestParam.put(name, value);
				}
			}
		}
		return requestParam;
	}
	
	/**
	 * 获取请求头
	 * @param requestData
	 * @return
	 */
	public static JSONObject getReqHead(String requestData){
		String regex = "HTTP/1.[0,1]\\s*\\r\\n(.*)";
		String data = StringUtil.getSubStrByRegexDotall(requestData, regex);
		if(null == data)
			return null;
		String reqHeadStr = data.split(HTTPAnalyzer.BLANK_LINE)[0];
		JSONObject requestHeader = new JSONObject();
		for(String nv : reqHeadStr.split(HTTPAnalyzer.CRLF)){
			if(StringUtil.validate(nv)){
				String name = nv.split(": ")[0].trim();
				String value = nv.split(": ").length > 1 ? nv.split(": ")[1].trim() : "";
				/**
				 * 解决以下异常
				 * Caused by: org.apache.http.ProtocolException: Content-Length header already present
				 * Caused by: org.apache.http.ProtocolException: Content-Length header already present
				 */
				if(!name.equals("Content-Length") && !name.equals("Transfer-Encoding"))
					requestHeader.put(name, value);
			}
		}
		return requestHeader;
	}
	
	/**
	 * 从TCPPacket包中取出data并转换成字符串
	 * @param tcpPacket
	 * @return
	 */
	public static String getData(byte[] packetData){
		// 从配置文件获取编码方式
		String encoding = PropertiesUtil.getProperty("encoding");
		String data = null;
		if(StringUtil.isEmpty(encoding)) {
			data = new String(packetData);
		} else {
			data = new String(packetData, Charset.forName(encoding));
		}
		return data;
	}
	
	/**
	 * 判断给定字符串是否为http首个响应
	 * @param data
	 * @return
	 */
	public static boolean isHttpResponse(String data){
		//String regex = "^HTTP/1.[0,1] [0-9]{0,3} *";
		return StringUtil.containIgnoreCase(data, HTTPAnalyzer.RSP_REGEX);
	}
	
	/**
	 * 
	 * @param rspData
	 * @return
	 */
	public static int getStatusCode(String rspData){
		String statusCode = StringUtil.getSubStrByRegex(rspData, HTTPAnalyzer.RSP_STATUSCODE_REGEX);
		if(null == statusCode){
			return -1;
		}
		return Integer.valueOf(statusCode);
	}
	
	/**
	 * 
	 * @param rspData
	 * @return
	 */
	public static String getReasonPhrase(String rspData){
		return StringUtil.getSubStrByRegex(rspData, HTTPAnalyzer.RSP_STATUSMSG_REGEX);
	}
	
	/**
	 * 获取响应头Json对象
	 * @param responseData
	 * @return
	 */
	public static JSONObject getRspHeadForJson(String responseData){
		String data = HTTPAnalyzer.getRspHead(responseData);
		if(null == data)
			return null;
		String rspHeadStr = data.split(HTTPAnalyzer.BLANK_LINE)[0];
		JSONObject responseHeader = new JSONObject();
		for(String nv : rspHeadStr.split(HTTPAnalyzer.CRLF)){
			if(StringUtil.validate(nv)){
				String name = nv.split(": ")[0].trim();
				String value = nv.split(": ").length > 1 ? nv.split(": ")[1].trim() : "";
				responseHeader.put(name, value);
			}
		}
		return responseHeader;
	}
	
	/**
	 * 获取响应头字符串
	 * @param responseData
	 * @return
	 */
	public static String getRspHead(String responseData){
		String regex = HTTPAnalyzer.RSP_REGEX + "\\r\\n(.*)";
		return StringUtil.getSubStrByRegexDotall(responseData, regex);
	}
	
	/**
	 * 获取响应体
	 * @param responseData
	 * @return
	 */
	public static String getRspBody(String responseData){
		String regex = HTTPAnalyzer.RSP_REGEX + "\\r\\n(.*)";
		String data = StringUtil.getSubStrByRegexDotall(responseData, regex);
		if(null == data || data.split(HTTPAnalyzer.BLANK_LINE).length < 2)
			return null;
		return data.split(HTTPAnalyzer.BLANK_LINE)[1];
	}
	
	/**
	 * 获取响应头中的Content-Length值
	 * @param responseData
	 * @return
	 */
	public static int getContentLength(String responseData){
//		String regex = "Content-Length:\\s+(\\d+)";
		String length = StringUtil.getSubStrByRegexIgnoreCase(responseData, HTTPAnalyzer.CONTENTLENGTH_REGEX);
    	if(null != length){
    		return Integer.valueOf(length);
    	}
    	return -1;
	}
	
	/**
	 * 获取RequestLine
	 * @param request
	 * @return
	 */
	public static String getRequestLine(String requestData){
		//String regex = "([a-zA-Z]{3,7} .* HTTP/1.[0,1])\\r\\n";
        return StringUtil.getSubStrByRegexIgnoreCase(requestData, HTTPAnalyzer.REQ_LINE_REGEX);
	}
	
	/**
	 * 当header中有chunked时，需要去除data中标识chunk大小的十六进制数字
	 * @param data
	 * @return
	 */
	public static String removeChunkLengthFlag(PacketBean bean, String data){
		StringBuffer sb = new StringBuffer();
		for(String line : data.split("\r\n")){
			if(null != line && StringUtil.contain(line.trim(), "^[A-Fa-f0-9]+$")){
				bean.addLength(line.trim());
				continue;
			}
			sb.append(line + "\r\n");
		}
		return sb.toString();
	}
	
	/**
	 * 
	 * @param responseData
	 * @param lastPacket
	 * @return
	 */
	public static boolean isResponseFinish(PacketBean packetBean, TCPPacket lastPacket){
		String responseData = packetBean.getDataForStr();
		String lastData = HTTPAnalyzer.getData(lastPacket.data);
		int statusCode = HTTPAnalyzer.getStatusCode(responseData);
		if((statusCode > 99 && statusCode < 200) || statusCode == 204 || statusCode == 304){
			return true;
		} else if((HTTPAnalyzer.getHttpVersion(responseData).equals("1.0") || 
				HTTPAnalyzer.getHeader(responseData, "Connection").equalsIgnoreCase("close"))
				&& DataCache.HTTP_RESPONSE_MAP.get(lastPacket.ack_num).isFin()){
			return true;
		} else if(HTTPAnalyzer.getHeader(responseData, "Connection").equalsIgnoreCase("keep-alive") &&
				HTTPAnalyzer.getHeader(responseData, "Transfer-Encoding").contains("chunked") &&
				lastData.split(HTTPAnalyzer.CRLF)[0].trim().equals("0")){
			return true;
		} else {
			if(responseData.getBytes().length >= HTTPAnalyzer.getContentLengthWithHead(responseData)){
				return true;
			}
			if(packetBean.getLength() > 0 && HTTPAnalyzer.getContentLength(responseData) >= packetBean.getLength()){
				return true;
			}
		}
		return false;
	}
}
