package com.net;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

import com.net.common.Constants;
import com.net.common.util.LogUtil;

public class Main {
	private static Class<?> cl = Main.class;

	public static void main(String[] args) {
		LogUtil.console(cl, "OS:" + Constants.OS_NAME);
		if (Constants.OS_NAME.toLowerCase().contains("windows")) {
			try {
				addLibraryDir(System.getProperty("user.dir") + File.separator + "lib");
			} catch (IOException e) {
				LogUtil.err(cl, e);
			}
			new WindowsFrame();
		} else if (Constants.OS_NAME.toLowerCase().contains("linux")) {
			new LinuxFrame();
		} else {
			LogUtil.err(cl, "The operating system is not supported, " + "Currently only support Windows and Linux, "
					+ "Your OS name is" + Constants.OS_NAME);
		}
	}
	
	// 动态修改java.library.path的值
	public static void addLibraryDir(String libraryPath) throws IOException {  
        try {  
        	// usr_paths为jdk源码中java.library.path值的存储变量名
            Field field = ClassLoader.class.getDeclaredField("usr_paths");  
            field.setAccessible(true);  
            String[] paths = (String[]) field.get(null);  
            for (int i = 0; i < paths.length; i++) {  
                if (libraryPath.equals(paths[i])) {  
                    return;  
                }  
            }  
  
            String[] tmp = new String[paths.length + 1];  
            System.arraycopy(paths, 0, tmp, 0, paths.length);  
            tmp[paths.length] = libraryPath;  
            field.set(null, tmp);  
        } catch (IllegalAccessException e) {  
            throw new IOException(  
                    "Failed to get permissions to set library path");  
        } catch (NoSuchFieldException e) {  
            throw new IOException(  
                    "Failed to get field handle to set library path");  
        }  
    } 
}
