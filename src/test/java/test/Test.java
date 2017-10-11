package test;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Test {
	public static void main(String[] args) throws FileNotFoundException {
//		FileReader fr = new FileReader("requests.txt");
//		System.out.println(fr instanceof Reader);
//		// 这里是多态，父类引用指向子类对象
//		Reader reader = new FileReader("requests.txt");
//		System.out.println(reader instanceof FileReader);
//		System.out.println(reader instanceof Reader);
//		
//		Class c = ArrayList.class;
//		c.isPrimitive(); //判断c是否为基本数据类型
//		System.out.println(c.isPrimitive());
//		c.isAssignableFrom(List.class);  //判断c是否是List类的子类或父类
//		System.out.println(c.isAssignableFrom(List.class));
//		//c.getGenericType(); //得到泛型类型
		
//		deleteFolder("D:\\workspace\\JavaNetCap2\\target");
		run_cmd("exts/create_maven_project.bat");
	}

	public static void run_cmd(String strcmd) { //
		Runtime rt = Runtime.getRuntime(); // Runtime.getRuntime()返回当前应用程序的Runtime对象
		Process ps = null; // Process可以控制该子进程的执行或获取该子进程的信息。
		try {
			ps = rt.exec(strcmd); // 该对象的exec()方法指示Java虚拟机创建一个子进程执行指定的可执行程序，并返回与该子进程对应的Process对象实例。
			ps.waitFor(); // 等待子进程完成再往下执行。
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e) { 
			e.printStackTrace();
		}
		int i = ps.exitValue(); // 接收执行完毕的返回值
		if (i == 0) {
			System.out.println("执行完成.");
		} else {
			System.out.println("执行失败.");
		}
		ps.destroy(); // 销毁子进程
		ps = null;
	}

	
	public static boolean deleteFolder(String url){  
		java.io.File file=new java.io.File(url);  
	    if(!file.exists()){  
	        return false;  
	    }  
	    if(file.isFile()){  
	        file.delete();  
	        return true;  
	    }else{  
	    	java.io.File[] files=file.listFiles();  
	        for(int i=0;i<files.length;i++){  
	            String root=files[i].getAbsolutePath();//得到子文件或文件夹的绝对路径  
	            //System.out.println(root);  
	            deleteFolder(root);  
	        }  
	        file.delete();  
	        return true;  
	    }  
	}
	      
}
