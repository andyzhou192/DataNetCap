package com.client.handler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.client.common.util.LogUtil;

public class DataSaveHandler {
	private static Class<?> cl = DataSaveHandler.class;

	// 自定义的save方法，参数为文件对象
	public static boolean save(File file, String content) {
		LogUtil.debug(cl, file.getAbsolutePath());
		// 将文本区内容写入字符输出流
		try { // 文件写入通道连向文件对象
			FileWriter fw = new FileWriter(file, false);
			fw.write(content);// 写入多行文本框的内容
			fw.close(); // 关闭通道
			LogUtil.debug(cl, "properties is saved:" + true);
			return true;
		} catch (IOException ioe) { // 异常处理
			LogUtil.err(cl, ioe);
		}
		LogUtil.debug(cl, "properties is saved:" + false);
		return false;
	}

	// public static void main(String[] args) {
	// java.util.HashMap<String, Object> map = new java.util.HashMap<String,
	// Object>();
	// map.put("URL", "11111111111");
	// map.put("Method", "22222222222222");
	// map.put("ReqHeader", "3333333333333333");
	// map.put("ReqParams", "44444444444444444");
	// map.put("StatusCode", "55555555555555555555");
	// map.put("ReasonPhrase", "6666666666666666666666");
	// map.put("RspHeader", "777777777777777777777");
	// map.put("RspBody", "8888888888888888888");
	// }
}
