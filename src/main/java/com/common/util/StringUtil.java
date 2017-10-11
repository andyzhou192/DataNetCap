package com.common.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

//import org.json.JSONException;
//import org.json.JSONObject;



import com.common.Constants;

public class StringUtil {
	public static final String DELIM_DEFAULT = ".";

    /**
     * 将指定对象转换成字符串
     *
     * @param obj
     *            指定对象
     * @return 转换后的字符串
     */
    public static String toString(Object obj) {
        StringBuffer buffer = new StringBuffer();
        if (obj != null) {
            buffer.append(obj);
        }
        return buffer.toString().trim();
    }
 
    /**
     * 根据默认分隔符获取字符串前缀
     *
     * @param str
     *            指定字符串
     * @return 返回前缀字符串
     */
    public static String getPrefix(String str) {
        return getPrefix(str, DELIM_DEFAULT);
    }
 
    /**
     * 根据指定分隔符获取字符串前缀
     *
     * @param str
     *            指定字符串
     * @param delim
     *            指定分隔符
     * @return 返回字符串前缀
     */
    public static String getPrefix(String str, String delim) {
        String prefix = "";
        if (isNotBlank(str) && isNotBlank(delim)) {
            int pos = str.indexOf(delim);
            if (pos > 0) {
                prefix = str.substring(0, pos);
            }
        }
        return prefix;
    }
 
    /**
     * 根据默认分隔符获取字符串后缀
     *
     * @param str
     *            指定字符串
     * @return 返回字符串后缀
     */
    public static String getSuffix(String str) {
        return getSuffix(str, DELIM_DEFAULT);
    }
 
    /**
     * 根据指定分隔符获取字符串后缀
     *
     * @param str
     *            指定字符串
     * @param delim
     *            指定分隔符
     * @return 返回字符串后缀
     */
    public static String getSuffix(String str, String delim) {
        String suffix = "";
        if (isNotBlank(str) && isNotBlank(delim)) {
            int pos = str.lastIndexOf(delim);
            if (pos > 0) {
                suffix = str.substring(pos + 1);
            }
        }
        return suffix;
    }
 
    /**
     * 根据指定字符串和重复次数生成新字符串
     *
     * @param str
     *            指定字符串
     * @param repeatCount
     *            重复次数
     * @return 返回生成的新字符串
     */
    public static String newString(String str, int repeatCount) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < repeatCount; i++) {
            buf.append(str);
        }
        return buf.toString();
    }
 
    /**
     * 隐藏字符串指定位置的字符
     *
     * @param str
     *            指定字符串
     * @param index
     *            起始位置
     * @param length
     *            字符长度
     * @return 返回隐藏字符后的字符串
     */
    public static String hideChars(String str, int index, int length) {
        return hideChars(str, index, length, true);
    }
 
    /**
     * 隐藏字符串指定位置的字符
     *
     * @param str
     *            指定字符串
     * @param start
     *            起始位置
     * @param end
     *            结束位置
     * @param confusion
     *            是否混淆隐藏的字符个数
     * @return 返回隐藏字符后的字符串
     */
    public static String hideChars(String str, int start, int end,
            boolean confusion) {
        StringBuffer buf = new StringBuffer();
        if (isNotBlank(str)) {
            int startIndex = Math.min(start, end);
            int endIndex = Math.max(start, end);
            // 如果起始位置超出索引范围则默认置为0
            if (startIndex < 0 || startIndex > str.length()) {
                startIndex = 0;
            }
            // 如果结束位置超出索引范围则默认置为字符串长度
            if (endIndex < 0 || endIndex > str.length()) {
                endIndex = str.length();
            }
            String temp = newString("*", confusion ? 4 : endIndex - startIndex);
            buf.append(str).replace(startIndex, endIndex, temp);
 
        }
        return buf.toString();
    }
 
    /**
     * 将指定字符串转换成大写
     *
     * @param str
     *            指定字符串
     * @return 返回转换后的大写字符串
     */
    public static String toLowerCase(String str) {
        StringBuffer buffer = new StringBuffer(str);
        for (int i = 0; i < buffer.length(); i++) {
            char c = buffer.charAt(i);
            buffer.setCharAt(i, Character.toLowerCase(c));
        }
        return buffer.toString();
    }
 
    /**
     * 将指定字符串转换成大写
     *
     * @param str
     *            指定字符串
     * @return 返回转换后的大写字符串
     */
    public static String toUpperCase(String str) {
        StringBuffer buffer = new StringBuffer(str);
        for (int i = 0; i < buffer.length(); i++) {
            char c = buffer.charAt(i);
            buffer.setCharAt(i, Character.toUpperCase(c));
        }
        return buffer.toString();
    }
 
    /**
     * 将指定字符串转换成驼峰命名方式
     *
     * @param str
     *            指定字符串
     * @return 返回驼峰命名方式
     */
    public static String toCalmelCase(String str) {
        StringBuffer buffer = new StringBuffer(str);
        if (buffer.length() > 0) {
            // 将首字母转换成小写
            char c = buffer.charAt(0);
            buffer.setCharAt(0, Character.toLowerCase(c));
            Pattern p = Pattern.compile("_\\w");
            Matcher m = p.matcher(buffer.toString());
            while (m.find()) {
                String temp = m.group(); // 匹配的字符串
                int index = buffer.indexOf(temp); // 匹配的位置
                // 去除匹配字符串中的下划线，并将剩余字符转换成大写
                buffer.replace(index, index + temp.length(),
                        temp.replace("_", "").toUpperCase());
            }
        }
        return buffer.toString();
    }
 
    /**
     * 将指定字符串转换成匈牙利命名方式
     *
     * @param str
     *            指定字符串
     * @return 转换后的匈牙利命名方式
     */
    public static String toHungarianCase(String str) {
        StringBuffer buffer = new StringBuffer(str);
        if (buffer.length() > 0) {
            Pattern p = Pattern.compile("[A-Z]");
            Matcher m = p.matcher(buffer.toString());
            while (m.find()) {
                String temp = m.group(); // 匹配的字符串
                int index = buffer.indexOf(temp); // 匹配的位置
                // 在匹配的字符串前添加下划线，并将其余字符转换成大写
                buffer.replace(index, index + temp.length(), (index > 0
                        ? "_"
                        : "") + temp.toLowerCase());
            }
        }
        return buffer.toString();
    }
 
    /**
     * 将指定字符串首字母转换成大写字母
     *
     * @param str
     *            指定字符串
     * @return 返回首字母大写的字符串
     */
    public static String firstCharUpperCase(String str) {
        StringBuffer buffer = new StringBuffer(str);
        if (buffer.length() > 0) {
            char c = buffer.charAt(0);
            buffer.setCharAt(0, Character.toUpperCase(c));
        }
        return buffer.toString();
    }
 
    /**
     * 将指定数组转换成字符串
     *
     * @param objs
     *            指定数组
     * @return 返回转换后的字符串
     */
    public static String array2String(Object[] objs) {
        StringBuffer buffer = new StringBuffer();
        if (objs != null) {
            for (int i = 0; i < objs.length; i++) {
                buffer.append(objs[i]).append(",");
            }
        }
        buffer.deleteCharAt(buffer.length() - 1);
        return buffer.toString();
    }
	
	/**
	 * 验证字符串是否为非空串和非空格串
	 * @param str
	 * @return
	 */
	public static boolean validate(String str){
		return (!isEmpty(str) && !isBlank(str));
	}
	
    /**
     * Returns true if the parameter is null or of zero length
     */
    public static boolean isEmpty(final CharSequence s) {
        if (s == null) {
            return true;
        }
        return s.length() == 0;
    }

    /**
     * Returns true if the parameter is null or contains only whitespace
     */
    public static boolean isBlank(final CharSequence s) {
        if (s == null) {
            return true;
        }
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * 判断指定字符串是否不等于null和空字符串
     *
     * @param str
     *            指定字符串
     * @return 如果不等于null和空字符串则返回true，否则返回false
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * @since 4.4
     */
    public static boolean containsBlanks(final CharSequence s) {
        if (s == null) {
            return false;
        }
        for (int i = 0; i < s.length(); i++) {
            if (Character.isWhitespace(s.charAt(i))) {
                return true;
            }
        }
        return false;
    }

	/**
	 * 判断字符串是否null
	 * @param str
	 * @return
	 */
	public static boolean isNull(String str) {
		return null == str ? true : false;
	}

	/**
	 * 将字符串数组使用指定的字符串（joinSymbol）连接起来
	 * @param joinSymbol
	 * @param strings
	 * @return
	 */
	public static String assembleStr(String joinSymbol, String... strings) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < strings.length; ++i) {
			if (i == strings.length - 1) {
				sb.append(strings[i]);
			} else {
				sb.append(strings[i]).append(joinSymbol);
			}
		}
		return sb.toString();
	}
	
	/**
	 * 
	 * @param strings
	 * @return
	 */
	public static String assembleStrWithSpace(String... strings){
		return StringUtil.assembleStr(" ", strings);
	}
	
	/**
	 * 将指定的字符串按给定的顺序组装成一个相对路径，以File.separator分割
	 * @param paths
	 * @return
	 */
	public static String assembleRelativeFilePath(String...paths){
		return StringUtil.assembleStr(File.separator, paths);
	}
	
	/**
	 * 将指定的字符串按给定的顺序组装成一个相对路径，以File.separator分割
	 * @param paths
	 * @return
	 */
	public static String assembleRootFilePath(String...paths){
		return File.separator + StringUtil.assembleStr(File.separator, paths);
	}

	/**
	 * 将Map使用指定的字符（joinSymbol）连接起来
	 * @param joinSymbol
	 * @param params
	 * @return
	 */
	public static String assembleStr(String joinSymbol, Map<String, String> params) {
		return assembleStr(joinSymbol, params, true);
	}

	/**
	 * 将Map使用指定的字符（joinSymbol）连接起来
	 * @param join
	 * @param params
	 * @param urlEncode 是否采用urlEncode编码
	 * @return
	 */
	public static String assembleStr(String join, Map<String, String> params, boolean urlEncode) {
		StringBuilder sb = new StringBuilder();

		for (Entry<String, String> entry : params.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();

			if (value == null) {
				continue;
			}

			if (sb.length() > 0) {
				sb.append(join);
			}

			try {
				if (urlEncode) {
					sb.append(String.format("%s=%s", key, URLEncoder.encode(value, Constants.UTF8)));
				} else {
					sb.append(String.format("%s=%s", key, value));
				}
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e.getCause());
			}
		}

		return sb.toString();
	}
	
	public static String removeExcessSpaces(String str){
		//将字符串中的一个或多个空格用一个空格替换
		Pattern p = Pattern.compile("\\s+");
		Matcher m = p.matcher(str);
		String newStr = m.replaceAll(" ").trim();
		return newStr;
	}
	
	public static String convertStreamToString(InputStream is) {      
        /*  
          * To convert the InputStream to String we use the BufferedReader.readLine()  
          * method. We iterate until the BufferedReader return null which means  
          * there's no more data to read. Each line will appended to a StringBuilder  
          * and returned as String.  
          */     
         BufferedReader reader = new BufferedReader(new InputStreamReader(is));      
         StringBuilder sb = new StringBuilder();      
     
         String line = null;      
        try {      
            while ((line = reader.readLine()) != null) {      
                 sb.append(line + "\n");      
             }      
         } catch (IOException e) {      
             e.printStackTrace();      
         } finally {      
            try {      
                 is.close();      
             } catch (IOException e) {      
                 e.printStackTrace();      
             }      
         }      
     
        return sb.toString();      
     }
	
	/**
	 * java 合并两个byte数组
	 * @param byte_1
	 * @param byte_2
	 * @return
	 */
	public static byte[] byteMerger(List<byte[]> byteList) {
		int len = 0;
		for(byte[] b:byteList){
			len += b.length;
		}
		byte[] byteArray = new byte[len];
		int destPos = 0;
		for(int i = 0; i<byteList.size(); i++){
			System.arraycopy(byteList.get(i), 0, byteArray, destPos, byteList.get(i).length);
			destPos += byteList.get(i).length;
		}
		return byteArray;
	}
	
	public static String gzipToString(byte[] data) {
		String result = "";
        try {  
            ByteArrayInputStream bis = new ByteArrayInputStream(data);  
            GZIPInputStream gzip = new GZIPInputStream(bis);  
            byte[] buf = new byte[1024];  
            int num = -1;  
            ByteArrayOutputStream baos = new ByteArrayOutputStream();  
            while ((num = gzip.read(buf, 0, buf.length)) != -1) {  
                baos.write(buf, 0, num);  
            }  
            byte[] b = baos.toByteArray();  
            baos.flush();  
            baos.close();  
            gzip.close();  
            bis.close(); 
            result = new String(b);
        } catch (Exception ex) {  
            ex.printStackTrace();  
        }  
		return result;
	}
	
	public static String getIpv4(String str){
	    String regex = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";  
	    Pattern p = Pattern.compile(regex);  
	    Matcher m = p.matcher(str);  
	    while (m.find()) {  
	        return m.group();
	    } 
	    return null;
	}
	
	/** 
	 * 判断是否为合法IP 
	 * @return the ip 
	 */  
	public static boolean isIPV4(String addr) {
		if(addr == null || addr.length() < 7 || addr.length() > 15 || "".equals(addr)) {
			return false;
		}
		/**
		 * 判断IP格式和范围
		 */
		String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
		Pattern pat = Pattern.compile(rexp);  
		Matcher mat = pat.matcher(addr);  
		boolean ipAddress = mat.find();
		return ipAddress;
	}  

//	 public static void main(String[] args) {
//		 String str = "192.168.1.1";  
//		 String str2 = "http://192.1.1.1/";
//		 System.out.println(getIpv4(str));
//		 System.out.println(getIpv4(str2));
//		 System.out.println(isIPV4(str));
//		 System.out.println(isIPV4(str2));
//	 }

}
