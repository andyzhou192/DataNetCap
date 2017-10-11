package test;

import java.awt.*;

import java.awt.event.*;

import java.io.*;

public class MyTest_35 extends WindowAdapter implements ActionListener, TextListener {
	Frame frame;
	TextArea textArea; // 多行文本区
	Panel panel;
	TextField textField; // 文本框
	Button openBtn, saveBtn, saveAsBtn;
	FileDialog fileDialog; // 文件对话框
	File file = null; // 文件对象初始化为空值

	public static void main(String args[]) { // 主方法调用自定义的display()方法
		(new MyTest_35()).display();
	}

	// display()方法
	public void display() { // 设置界面与监听
		frame = new Frame("EditFile");
		frame.setSize(680, 400);
		frame.setLocation(200, 140);
		frame.setBackground(Color.lightGray);
		frame.addWindowListener(this);
		textField = new TextField(); // 文本框
		textField.setEnabled(false);
		// 设置文本行的初始字体
		textField.setFont(new Font("Dialog", 0, 20));
		frame.add(textField, "North");

		textArea = new TextArea(); // 多行文本区
		// 设置文本区的初始字体
		textArea.setFont(new Font("Dialog", 0, 20));
		frame.add(textArea);
		// 注册文本区的事件监听程序
		textArea.addTextListener(this);
		panel = new Panel(); // 面板与布局
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		openBtn = new Button("Open");
		saveBtn = new Button("Save");
		saveAsBtn = new Button("Save As");
		panel.add(openBtn); // 三个按钮挂上面板
		panel.add(saveBtn);
		panel.add(saveAsBtn);
		saveBtn.setEnabled(false);
		saveAsBtn.setEnabled(false);
		// 注册按钮的事件监听程序
		openBtn.addActionListener(this);
		saveBtn.addActionListener(this);
		saveAsBtn.addActionListener(this);
		frame.add(panel, "South");// 面板贴上窗体
		frame.setVisible(true);

	}// display()方法结束

	// 实现TextListener接口中的方法，
	// 当对多行对文本区的内容编辑时触发
	public void textValueChanged(TextEvent e) { // "保存"和"另存为"两个按钮点亮
		saveBtn.setEnabled(true);
		saveAsBtn.setEnabled(true);
	}

	// 响应单击按钮的高级事件
	public void actionPerformed(ActionEvent e) {
		// 单击[打开]按钮时
		if (e.getSource() == openBtn) {
			fileDialog = new FileDialog(frame, "Open", FileDialog.LOAD);
			fileDialog.setVisible(true); // 创建并显示打开文件对话框
			if ((fileDialog.getDirectory() != null) && (fileDialog.getFile() != null)) { // 单行文本框显示文件路径名
				textField.setText(fileDialog.getDirectory() + fileDialog.getFile());
				// 以缓冲区方式读取文件内容
				try {
					// 文件对象赋值
					file = new File(fileDialog.getDirectory(), fileDialog.getFile());
					// 文件读入通道连向文件对象
					FileReader fr = new FileReader(file);
					// 定义文件缓冲区
					BufferedReader br = new BufferedReader(fr);
					String aline;
					// 按行读取文本，每行附加在多行文本区之后

					while ((aline = br.readLine()) != null)
						textArea.append(aline + "\r\n");
					fr.close();
					br.close(); // 关闭文件缓冲区
				} // 输入输出异常捕获
				catch (IOException ioe) {
					System.out.println(ioe);
				}
			} // 结束if文件不为空
		} // 结束if单击[打开]按钮

		// 单击[Save]或[SaveAs]按钮时
		if ((e.getSource() == saveBtn) || (e.getSource() == saveAsBtn)) { // 单击[SaveAs]按钮时,或单击[Save]按钮且文件对象为空时
			if ((e.getSource() == saveAsBtn) || (e.getSource() == saveBtn) && (file == null)) { // 文件对话框对象，保存方式
				fileDialog = new FileDialog(frame, "Save", FileDialog.SAVE);
				if (file == null)
					fileDialog.setFile("Edit1.txt"); // 缺省文件名
				else
					fileDialog.setFile(file.getName());
				fileDialog.setVisible(true);
				// 创建并显示保存文件对话框
				if ((fileDialog.getDirectory() != null) && (fileDialog.getFile() != null)) { // 单行文本框中显示文件路径和名称
					textField.setText(fileDialog.getDirectory() + fileDialog.getFile());
					// 文件对象的赋值
					file = new File(fileDialog.getDirectory(), fileDialog.getFile());
					save(file); // 调用自定义的save方法
				}
			} else // 文件对象不为空时
				save(file);
		}
	}// 响应单击按钮的高级事件结束

	// 自定义的save方法，参数为文件对象
	public void save(File file1) {
		// 将文本区内容写入字符输出流
		try { // 文件写入通道连向文件对象
			FileWriter fw = new FileWriter(file1);
			fw.write(textArea.getText());// 写入多行文本框的内容
			fw.close(); // 关闭通道
			saveBtn.setEnabled(false); // 点灭保存按钮
			saveAsBtn.setEnabled(false);
		} catch (IOException ioe) { // 异常处理
			System.out.println(ioe);
		}
	}

	// 响应关闭窗口事件
	public void windowClosing(WindowEvent e) {
		System.exit(0);
	}

}
