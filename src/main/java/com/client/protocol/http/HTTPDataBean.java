package com.client.protocol.http;

import lombok.Getter;
import lombok.Setter;
import net.sf.json.JSONObject;

public class HTTPDataBean {

	@Setter @Getter
	private int projectId;
	
	@Setter @Getter
	private int moduleId;
	
	@Setter @Getter
	private int source = 0;
	
	@Setter @Getter
	private String method;
	
	@Setter @Getter
	private String protocol = "HTTP";
	
	@Setter @Getter
	private float protocolVersion = 1.1f;
	
	@Setter @Getter
	private String url;
	
	@Setter @Getter
	private JSONObject reqHeader;
	
	@Setter @Getter
	private JSONObject reqParams;
	
	@Setter @Getter
	private int statusCode;
	
	@Setter @Getter
	private String reasonPhrase;
	
	@Setter @Getter
	private JSONObject rspHeader;
	
	@Setter @Getter
	private Object rspBody;

}
