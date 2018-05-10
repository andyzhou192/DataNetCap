package com.client.common;

import java.io.File;
import java.net.URL;
import java.util.Properties;

import com.client.common.util.StringUtil;

/**
 * 
 * @author zhouyelin@chinamobile.com
 *
 */
public final class Constants {

//	static {
//		String srcPath = System.getProperty("java.library.path");
//		String dllPath = System.getProperty("user.dir") + File.separator + "lib";
//		System.setProperty("java.library.path", srcPath + dllPath);
//		System.out.println(System.getProperty("java.library.path"));
//	}
	
	public final static String UTF8 = "UTF-8"; //Charset UTF_8 = Charset.forName("UTF-8");
	public final static String SP = " "; // 空格
	public final static String CRLF = "\r\n"; // 回车换行（carriage return/line feed）
	public final static String CR = "\r"; //回车
	public final static String LF = "\n"; // 换行
	public final static String CASEID = "caseId";
	public final static String GLOBAL_MSG = "msg";
	public final static String COMMA = ",";
	public final static String COLON = ":";
	public final static String QUES = "\\?";
	public final static String EQUAL = "=";
	public final static String AND = "&";
	public final static String VERTICAL_LINE = "\\|";
	public final static String NEW_LINE = System.getProperty("line.separator");
	
	public final static String OS_NAME = System.getProperty("os.name");
	public final static String OS_ARCH = System.getProperty("os.arch");

	public static URL ADD_ICON = Constants.class.getClass().getResource("/img/Add.png");
	public static URL ADD_SCRIPT_ICON = Constants.class.getClass().getResource("/img/AddScript.png");
	public static URL ABOUT_ICON = Constants.class.getClass().getResource("/img/About.png");
	public static URL BOOKMARK_ICON = Constants.class.getClass().getResource("/img/BookMark.png");
	public static URL BROWSE_ICON = Constants.class.getClass().getResource("/img/Browse.png");
	public static URL CAPTURE_NORMAL_ICON = Constants.class.getClass().getResource("/img/CaptureNormal.png");
	public static URL CAPTURE_DISABLED_ICON = Constants.class.getClass().getResource("/img/CaptureDisabled.png");
	public static URL CREATE_SCRIPT_ICON = Constants.class.getClass().getResource("/img/CreateScript.png");
	public static URL COLLAPSE_ICON = Constants.class.getClass().getResource("/img/Collapse.png");
	public static URL CLOSE_ICON = Constants.class.getClass().getResource("/img/Close.png");
	public static URL DOWN_ICON = Constants.class.getClass().getResource("/img/Down.png");
	public static URL DETAIL_ICON = Constants.class.getClass().getResource("/img/Detail.png");
	public static URL CLEAR_ICON = Constants.class.getClass().getResource("/img/Clear.png");;
	public static URL DELETE_ICON = Constants.class.getClass().getResource("/img/Delete.png");
	public static URL DATA_IMPORT_ICON = Constants.class.getClass().getResource("/img/DataImport.png");
	public static URL DATA_EXPORT_ICON = Constants.class.getClass().getResource("/img/DataExport.png");
	public static URL EDIT_ICON = Constants.class.getClass().getResource("/img/Edit.png");
	public static URL EXPAND_ICON = Constants.class.getClass().getResource("/img/Expand.png");
	public static URL EXIT_ICON = Constants.class.getClass().getResource("/img/Exit.png");
	public static URL GENE_SCRIPT_ICON = Constants.class.getClass().getResource("/img/GeneScript.png");
	public static URL HELP_ICON = Constants.class.getClass().getResource("/img/Help.png");
	public static URL INFO_ICON = Constants.class.getClass().getResource("/img/Info.png");
	public static URL OPEN_ICON = Constants.class.getClass().getResource("/img/Open.png");
	public static URL OPEN_SCRIPT_ICON = Constants.class.getClass().getResource("/img/OpenScript.png");
	public static URL PAUSE_NORMAL_ICON = Constants.class.getClass().getResource("/img/PauseNormal.png");
	public static URL PAUSE_DISABLED_ICON = Constants.class.getClass().getResource("/img/PauseDisabled.png");
	public static URL RESUME_ICON = Constants.class.getClass().getResource("/img/Resume.png");
	public static URL REFRESH_ICON = Constants.class.getClass().getResource("/img/Refresh.png");
	public static URL RELOAD_ICON = Constants.class.getClass().getResource("/img/Reload.png");
	public static URL SAVE_ICON = Constants.class.getClass().getResource("/img/Save.png");
	public static URL SAVEAS_ICON = Constants.class.getClass().getResource("/img/SaveAs.png");
	public static URL START_NORMAL_ICON = Constants.class.getClass().getResource("/img/StartNormal.png");
	public static URL START_DISABLED_ICON = Constants.class.getClass().getResource("/img/StartDisabled.png");
	public static URL STOP_NORMAL_ICON = Constants.class.getClass().getResource("/img/StopNormal.png");
	public static URL STOP_DISABLED_ICON = Constants.class.getClass().getResource("/img/StopDisabled.png");
	public static URL SETTINGS_ICON = Constants.class.getClass().getResource("/img/Settings.png");
	public static URL UP_ICON = Constants.class.getClass().getResource("/img/Up.png");
	
	public static String DEF_SET_PROP_FILE = StringUtil.assembleRelativeFilePath("." + File.separator + "conf", "setting.properties");
	
	public static Properties PROPS = null;
	
//	public static ProjectInfo PROJECT_INFO = null;
	
	public static String DEFAULT_PROJECT_DIR = new File("projects").getAbsolutePath();
	public static String MAVEN_BAT = StringUtil.assembleRelativeFilePath("." + File.separator + "maven", "create_maven_project.bat");
	public static String TEMPLATE_POM = StringUtil.assembleRelativeFilePath("." + File.separator + "maven", "template_pom.xml");
	public static String DEFAULT_MAVEN_PROJECT = StringUtil.assembleRelativeFilePath("." + File.separator + "maven", "MyTest");
	public static String HTTP_TEST_JAR = StringUtil.assembleRelativeFilePath("." + File.separator + "exts", "server-interface-ats-1.0.0.jar");
	
	public static String DEFAULT_TEMPLATE_SCRIPT_DIR = "." + File.separator + "template" + File.separator;//new File("template").getAbsolutePath();
	public static String DEFAULT_TEMPLATE_SCRIPT = StringUtil.assembleRelativeFilePath("." + File.separator + "template", "HttpTestScriptEntity.ftlh");
	
	
	
//	public static void main(String[] args) {
//		System.out.println(NEW_LINE.equals("\r\n"));
//		String str = "123" + NEW_LINE + "abc" + NEW_LINE + NEW_LINE + "=_+";
//		String regex = "(" + NEW_LINE + ")+\\s*" + "(" + NEW_LINE + ")";
//		String regex2 = "(\\r\\n)+\\s*(\\r\\n)";
//		System.out.println(StringUtil.contain(str, regex));
//		System.out.println(StringUtil.contain(str, regex2));
//	}
}