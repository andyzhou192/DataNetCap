package com.common.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 
 * 读取properties文件中的value
 *
 */
public class PropertiesUtil {
	private static Properties prop = new Properties();

	public static Properties loadProperties(String propertyFile){
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(propertyFile));
			InputStreamReader inr = new InputStreamReader(in, "UTF-8");// 解决读取的内容乱码问题
			prop.load(inr);
			return prop;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getProperty(String propertyFile, String proName) {
		PropertiesUtil.loadProperties(propertyFile);
		return prop.getProperty(proName);
	}
	
	public static String getProperty(String proName) {
		String proDir = System.getenv("ProDir");
		if(StringUtil.isEmpty(proDir)){
			PropertiesUtil.loadProperties("conf/setting.properties");
		} else {
			PropertiesUtil.loadProperties(proDir + "/conf/setting.properties");
		}
		return prop.getProperty(proName);
	}
	
	public static boolean storeProperty(String propertyFile, String key, String value, String comments){
		PropertiesUtil.loadProperties(propertyFile);
		///保存属性到config.properties文件
		try {
			//FileOutputStream oFile = new FileOutputStream(fileName + ".properties", true);//true表示追加打开
			FileOutputStream fos = new FileOutputStream(propertyFile);
			prop.setProperty(key, value);
			prop.store(fos, comments);
			fos.close();
			return true;
		} catch (IOException e) {
			LogUtil.err(PropertiesUtil.class, e);
		}
		return false;
	}
	
	public static boolean storeProperty(String propertyFile, Properties properties, String comments){
		PropertiesUtil.loadProperties(propertyFile);
		///保存属性到config.properties文件
		try {
			//FileOutputStream oFile = new FileOutputStream(fileName + ".properties", true);//true表示追加打开
			FileOutputStream fos = new FileOutputStream(propertyFile);
			for(Object key : properties.keySet()){
				String name = String.valueOf(key);
				String value = String.valueOf(properties.get(key));
				prop.setProperty(name, value);
			}
			prop.store(fos, comments);
			fos.close();
			return true;
		} catch (IOException e) {
			LogUtil.err(PropertiesUtil.class, e);
		}
		return false;
	}
	
//	public static void main(String[] args) {
//		storeProperty("config", "name", "value04", "test");
//	}
}
