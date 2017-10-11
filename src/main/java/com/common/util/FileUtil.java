package com.common.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 
 * @author zhouyelin@chinamobile.com
 *
 */

public class FileUtil {

	private static Class<?> cl = FileUtil.class;

	/**
	 * 判断指定文件是否存在
	 * 
	 * @param path：文件路径，如：/storage/sdcard0/Manual/test.pdf
	 * @return true if and only if the file or directory denoted by this
	 *         abstract pathname exists; false otherwise
	 */
	public static boolean fileIsExists(String path) {
		try {
			File f = new File(path);
			if (!f.exists()) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static boolean fileIsExists(File file) {
		if (file.exists()) {
			return true;
		}
		return false;
	}
	
	/**
	 * 删除单个文件
	 * 
	 * @param sPath
	 *            被删除文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(String sPath) {
		boolean result = false;
		File file = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			result = file.delete();
		}
		return result;
	}

	/**
	 * 删除目录（文件夹）以及目录下的文件
	 * 
	 * @param sPath
	 *            被删除目录的文件路径
	 * @return 目录删除成功返回true，否则返回false
	 */
	public boolean deleteDirectory(String sPath) {
		// 如果sPath不以文件分隔符结尾，自动添加文件分隔符
		if (!sPath.endsWith(File.separator)) {
			sPath = sPath + File.separator;
		}
		File dirFile = new File(sPath);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		boolean flag = true;
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			} // 删除子目录
			else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag)
			return false;
		// 删除当前目录
		if (dirFile.delete()) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean delete(String url) {
		java.io.File file = new java.io.File(url);
		if (!file.exists()) {
			return true;
		}
		if (file.isFile()) {
			file.delete();
			return true;
		} else {
			java.io.File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				String root = files[i].getAbsolutePath();// 得到子文件或文件夹的绝对路径
				delete(root);
			}
			file.delete();
			return true;
		}
	}

	/**
	 * 判断指定文件中是否存在指定的内容
	 * 
	 * @param filePath
	 * @param contents
	 * @return
	 */
	@SuppressWarnings("resource")
	public static boolean isContainSpecialText(String url, String... contents) {
		boolean isContain = false;
		FileReader fr;
		try {
			fr = new FileReader(url);
			BufferedReader bf = new BufferedReader(fr);
			String temp = "";
			while (temp != null) {
				temp = bf.readLine();
				if (temp != null) {
					for (String content : contents) {
						isContain = temp.contains(content);
						if (!isContain)
							break;
					}
				}
				if (isContain)
					break;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return isContain;
	}

	/**
	 * 读取文件内容,并返回一个字符串
	 * 
	 * @param file
	 * @return
	 */
	public static String readFileToString(String file) {
		String str = "";
		try {
			FileReader fr = new FileReader(file);
			BufferedReader bf = new BufferedReader(fr);
			String temp = "";
			while (temp != null) {
				temp = bf.readLine();
				if (null != temp)
					str += temp;
				str += "\n";
			}
			bf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 读取文件内容,并返回一个根据指定分隔符分割的字符串数组
	 * 
	 * @param file
	 * @param separator
	 *            : 分隔符，支持正则表达式
	 * @return
	 */
	public static String[] readFileToString(String file, String separator) {
		String[] requests = readFileToString(file).split(separator);
		// for(String request : requests){
		// System.out.println("--------->" + request);
		// }
		return requests;
	}

	/**
	 * 将数据写入文件中
	 * 
	 * @param fileName
	 * @param data
	 */
	public static void writeTxtFile(String fileName, String data) {
		File file = new File(fileName);
		boolean flag = true;
		try {
			if (!fileIsExists(fileName)) {
				flag = file.createNewFile();
			}
			if (flag) {
				FileWriter fileWritter = new FileWriter(file.getName(), true);
				BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
				bufferWritter.write(data);
				bufferWritter.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param parentDir
	 * @param subDirs
	 */
	public static void mkdirs(String parentDir, String... subDirs) {
		if(null != subDirs){
			for (String subDir : subDirs) {
				File file = new File(parentDir, subDir);
				if (!file.exists())
					file.mkdirs();
			}
		} else {
			File file = new File(parentDir);
			if (!file.exists())
				file.mkdirs();
		}
	}
	
	/**
	 * 
	 * @param fileName  "test"
	 * @param fileSuffix  ".xls"
	 * @param targetDirectory
	 * @return
	 */
	public static boolean createFile(String fileName, String fileSuffix, String targetDirectory){
		boolean isSucc = false;
		File f = new File(targetDirectory, fileName + fileSuffix);
		File dir = new File(targetDirectory);
		if(!dir.exists())
			dir.mkdirs();
		if (!f.exists()){
			try {
				File file = File.createTempFile(fileName, fileSuffix, new File(targetDirectory));
				if(null != file)
					isSucc = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return isSucc;
	}

	/**
	 * 复制单个文件
	 * 
	 * @param srcFileName
	 *            待复制的文件名
	 * @param descFileName
	 *            目标文件名
	 * @param overlay
	 *            如果目标文件存在，是否覆盖
	 * @return 如果复制成功返回true，否则返回false
	 */
	public static boolean copyFile(String srcFileName, String destFileName, boolean overlay) {
		File srcFile = new File(srcFileName);

		// 判断源文件是否存在
		if (!srcFile.exists()) {
			String msg = "源文件：" + srcFileName + "不存在！";
			LogUtil.err(cl, msg);
			return false;
		} else if (!srcFile.isFile()) {
			String msg = "复制文件失败，源文件：" + srcFileName + "不是一个文件！";
			LogUtil.err(cl, msg);
			return false;
		}

		// 判断目标文件是否存在
		File destFile = new File(destFileName);
		if (destFile.exists()) {
			// 如果目标文件存在并允许覆盖
			if (overlay) {
				// 删除已经存在的目标文件，无论目标文件是目录还是单个文件
				new File(destFileName).delete();
			}
		} else {
			// 如果目标文件所在目录不存在，则创建目录
			if (!destFile.getParentFile().exists()) {
				// 目标文件所在目录不存在
				if (!destFile.getParentFile().mkdirs()) {
					// 复制文件失败：创建目标文件所在目录失败
					return false;
				}
			}
		}

		// 复制文件
		int byteread = 0; // 读取的字节数
		InputStream in = null;
		OutputStream out = null;

		try {
			in = new FileInputStream(srcFile);
			out = new FileOutputStream(destFile);
			byte[] buffer = new byte[1024];

			while ((byteread = in.read(buffer)) != -1) {
				out.write(buffer, 0, byteread);
			}
			return true;
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		} finally {
			try {
				if (out != null)
					out.close();
				if (in != null)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 复制整个目录的内容
	 * 
	 * @param srcDirName
	 *            待复制目录的目录名
	 * @param destDirName
	 *            目标目录名
	 * @param overlay
	 *            如果目标目录存在，是否覆盖
	 * @return 如果复制成功返回true，否则返回false
	 */
	public static boolean copyDirectory(String srcDirName, String destDirName, boolean overlay) {
		// 判断源目录是否存在
		File srcDir = new File(srcDirName);
		if (!srcDir.exists()) {
			String msg = "复制目录失败：源目录" + srcDirName + "不存在！";
			LogUtil.err(cl, msg);
			return false;
		} else if (!srcDir.isDirectory()) {
			String msg = "复制目录失败：" + srcDirName + "不是目录！";
			LogUtil.err(cl, msg);
			return false;
		}

		// 如果目标目录名不是以文件分隔符结尾，则加上文件分隔符
		if (!destDirName.endsWith(File.separator)) {
			destDirName = destDirName + File.separator;
		}
		File destDir = new File(destDirName);
		// 如果目标文件夹存在
		if (destDir.exists()) {
			// 如果允许覆盖则删除已存在的目标目录
			if (overlay) {
				new File(destDirName).delete();
			} else {
				String msg = "复制目录失败：目的目录" + destDirName + "已存在！";
				LogUtil.err(cl, msg);
				return false;
			}
		} else {
			// 创建目的目录
			LogUtil.debug(cl, "目的目录不存在，准备创建。。。");
			if (!destDir.mkdirs()) {
				LogUtil.err(cl, "复制目录失败：创建目的目录失败！");
				return false;
			}
		}

		boolean flag = true;
		File[] files = srcDir.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 复制文件
			if (files[i].isFile()) {
				flag = FileUtil.copyFile(files[i].getAbsolutePath(), destDirName + files[i].getName(), overlay);
				if (!flag)
					break;
			} else if (files[i].isDirectory()) {
				flag = FileUtil.copyDirectory(files[i].getAbsolutePath(), destDirName + files[i].getName(), overlay);
				if (!flag)
					break;
			}
		}
		if (!flag) {
			String msg = "复制目录" + srcDirName + "至" + destDirName + "失败！";
			LogUtil.err(cl, msg);
			return false;
		} else {
			return true;
		}
	}

	// public static void main(String[] args) {
	// String file =
	// "D:\\workspace\\XYRJAPITest\\src\\test\\resources\\request_1.txt";
	//// String content = readFileToString(file);
	// //System.out.println(content);
	// String REQUEST_SEPARATOR = "\n🐵🙈🙉\n";
	// String[] requests = readFileToString(file, REQUEST_SEPARATOR);
	// for(String request : requests){
	// System.out.println("--------->" + request);
	// }
	//
	// //String reconstitutedString = new
	// String("a020259212266cf49cfb2ec8753cb874d9ff18db");
	// //System.out.println(reconstitutedString);
	// }
}