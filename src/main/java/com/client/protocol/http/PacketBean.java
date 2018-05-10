package com.client.protocol.http;

import com.client.common.util.PropertiesUtil;
import com.client.common.util.StringUtil;

import jpcap.packet.TCPPacket;

public class PacketBean {

	private long seqNum;
	private long ackNum;
	private byte[] data;
	private boolean fin;
	private int length = 0;
	
	public PacketBean(TCPPacket tcpPacket){
		this.seqNum = tcpPacket.sequence;
		this.ackNum = tcpPacket.ack_num;
		this.data = tcpPacket.data;
		this.fin = tcpPacket.fin;
	}
	
	public long getSeqNum() {
		return seqNum;
	}
	
	public void setSeqNum(long seqNum) {
		this.seqNum = seqNum;
	}
	
	public long getAckNum() {
		return ackNum;
	}
	
	public void setAckNum(long ackNum) {
		this.ackNum = ackNum;
	}
	
	public byte[] getData() {
		return data;
	}
	
	public String getDataForStr() {
		// 从配置文件获取编码方式
		String encoding = PropertiesUtil.getProperty("encoding");
		String result = StringUtil.getString(this.getData(), encoding);
		return result;
	}
	
	public void setData(byte[] data) {
		this.data = data;
	}
	
	public void appendData(byte[] appendData){
		byte[] newData = new byte[this.data.length + appendData.length];  
	    System.arraycopy(this.data, 0, newData, 0, this.data.length);  
	    System.arraycopy(appendData, 0, newData, this.data.length, appendData.length);  
	    this.setData(newData);
	}
	
	public boolean isFin() {
		return fin;
	}
	
	public void setFin(boolean fin) {
		this.fin = fin;
	}

	/**
	 * @return the length
	 */
	public int getLength() {
		return length;
	}

	/**
	 * @param length the length to set
	 */
	public void setLength(int length) {
		this.length = length;
	}
	
	/**
	 * 加上一个十六进制的长度
	 * @param hexString
	 */
	public void addLength(String hexString){
		Integer addLen = 0;
		if(hexString.startsWith("0x")){
			addLen = Integer.parseInt(hexString.substring(2),16);//从第2个字符开始截取
		} else {
			addLen = Integer.parseInt(hexString,16);
		}
		this.setLength(this.getLength() + addLen);
	}
	
}
