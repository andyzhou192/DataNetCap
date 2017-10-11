package com;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.netcap.captor.CaptureThread;
import com.netcap.captor.NetCaptor;
import com.netcap.handler.UploadToService;
import com.view.util.StatusProgressPanel;
import com.view.util.ViewModules;

import info.clearthought.layout.TableLayout;

@SuppressWarnings("serial")
public class MainFrame extends JFrame implements ActionListener {

	public StatusProgressPanel progress;
	
	private JLabel projectNameLabel;
	private JComboBox<?> projectJComboBox;
	
	private JLabel ethernetLabel;
	private JComboBox<?> netJComboBox;
	
	private JLabel urlLabel;
	private JTextArea urlFilterArea;
	
	private JButton applyButton;

	public static Map<String, Object> projectMap;
	public static String projectName;
	public static String netDevicesName;
	public static String captureUrl;
	
	public MainFrame() {
		super();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(600, 300);
		progress = new StatusProgressPanel();
		this.getContentPane().add(progress, BorderLayout.SOUTH);
		
		projectMap = UploadToService.getProjectMap();
		projectName = String.valueOf(projectMap.values().toArray()[0]);
		
		JPanel panel = new JPanel();
		//panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		double colSize[] = {0.05, 0.3, TableLayout.FILL, 0.3, 0.05}; // widths of columns in the format, {{col0, col1, col2, ..., colN}
		double rowSize[] = {5, 30, 5, 30, 5, 30, 5, TableLayout.FILL, 5, 30, 5}; // heights of rows in the format, {{row0, row1, row2, ..., rowN}
		double size[][] ={colSize, rowSize};
		panel.setLayout(new TableLayout(size));
		projectNameLabel = ViewModules.createJLabel("Project:", Color.RED);
		projectNameLabel.setToolTipText("选择项目");
		ethernetLabel = ViewModules.createJLabel("Ethernet:", Color.RED);
		ethernetLabel.setToolTipText("请选择网卡");
		urlLabel = ViewModules.createJLabel("Capture Url:(Multiple addresses are separated by ',')", Color.BLACK);
		urlLabel.setToolTipText("请填写待捕获的URL");
		projectJComboBox = ViewModules.createComboBox(projectMap.values().toArray());
		netJComboBox = ViewModules.createComboBox(NetCaptor.devicesMap.keySet().toArray());
		urlFilterArea = new JTextArea(3, 20);
		urlFilterArea.setEditable(true);
		urlFilterArea.setLineWrap(true);
		applyButton = ViewModules.createButton("Start", "SaveCaptureSetting", this);
		applyButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panel.add(projectNameLabel, "1, 1, 1, 1");
		panel.add(projectJComboBox, "2, 1, 3, 1");
		panel.add(ethernetLabel, "1, 3, 1, 1");
		panel.add(netJComboBox, "2, 3, 3, 1");
		panel.add(urlLabel, "1, 5, 3, 1");
		panel.add(urlFilterArea, "1, 7, 3, 1");
		panel.add(applyButton, "3, 9, 1, 1");
		this.getContentPane().add(panel, BorderLayout.CENTER);
		
		this.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent evt) {
		projectName = projectJComboBox.getSelectedItem().toString();
		netDevicesName = netJComboBox.getSelectedItem().toString();
		captureUrl = urlFilterArea.getText();
		new CaptureThread("CaptureThread").start();
		this.progress.startProgress("Starting...");
	}
	
	public static void main(String[] args) {
		new MainFrame();
	}
	
}