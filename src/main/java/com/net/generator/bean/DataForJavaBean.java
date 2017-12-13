package com.net.generator.bean;

import java.lang.reflect.Field;

import com.net.common.util.JsonUtil;

import net.sf.json.JSONObject;

public class DataForJavaBean {

	private String projectId = "";
	private String caseId = "caseId-";
	private String caseDesc = "";
	private String method = "";
	private String url = "";
	private JSONObject reqHeader;
	private JSONObject reqParams;
	private int statusCode;
	private String reasonPhrase = "";
	private JSONObject rspHeader;
	private Object rspBody;

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getCaseDesc() {
		return caseDesc;
	}

	public void setCaseDesc(String caseDesc) {
		this.caseDesc = caseDesc;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public JSONObject getReqHeader() {
		return reqHeader;
	}

	public void setReqHeader(JSONObject reqHeader) {
		this.reqHeader = reqHeader;
	}

	public JSONObject getReqParams() {
		return reqParams;
	}

	public void setReqParams(JSONObject reqParams) {
		this.reqParams = reqParams;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getReasonPhrase() {
		return reasonPhrase;
	}

	public void setReasonPhrase(String reasonPhrase) {
		this.reasonPhrase = reasonPhrase;
	}

	public JSONObject getRspHeader() {
		return rspHeader;
	}

	public void setRspHeader(JSONObject rspHeader) {
		this.rspHeader = rspHeader;
	}

	public Object getRspBody() {
		return rspBody;
	}

	public void setRspBody(Object rspBody) {
		this.rspBody = rspBody;
	}
	
	public static String[] getFields(){
		Field[] fields = DataForJavaBean.class.getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            String fieldName = fields[i].getName();
            fieldNames[i] = fieldName;
        }
		return fieldNames;
	}

	public Object toJson() {
		return JSONObject.fromObject(JsonUtil.beanToJson(this));
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
}
