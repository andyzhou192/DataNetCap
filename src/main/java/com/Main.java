package com;

import com.common.Constants;
import com.common.util.LogUtil;

public class Main {
	private static Class<?> cl = Main.class;

	public static void main(String[] args) {
		LogUtil.console(cl, "OS:" + Constants.OS_NAME);
		if (Constants.OS_NAME.toLowerCase().contains("windows")) {
			new WindowsFrame();
		} else if (Constants.OS_NAME.toLowerCase().contains("linux")) {
			new LinuxFrame();
		} else {
			LogUtil.err(cl, "The operating system is not supported, " + "Currently only support Windows and Linux, "
					+ "Your OS name is" + Constants.OS_NAME);
		}
	}
}
