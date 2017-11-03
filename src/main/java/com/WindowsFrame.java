package com;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.netcap.DataCache;
import com.netcap.captor.CaptureThread;
import com.view.util.StatusProgressPanel;
import com.view.util.ViewModules;

import info.clearthought.layout.TableLayout;

@SuppressWarnings("serial")
public class WindowsFrame extends JFrame implements ActionListener {

	public StatusProgressPanel progress;
	
	private JLabel projectNameLabel;
	private JComboBox<?> projectJComboBox;
	
	private JLabel ethernetLabel;
	private JComboBox<?> netJComboBox;
	
	private JLabel urlLabel;
	private JTextArea urlFilterArea;
	
	private JButton applyButton;

	
	public WindowsFrame() {
		super();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(600, 300);
		progress = new StatusProgressPanel();
		this.getContentPane().add(progress, BorderLayout.SOUTH);
		
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
		projectJComboBox = ViewModules.createComboBox(DataCache.projectMap.values().toArray());
		netJComboBox = ViewModules.createComboBox(DataCache.devicesMap.keySet().toArray());
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
		String projectName = projectJComboBox.getSelectedItem() == null ? null : projectJComboBox.getSelectedItem().toString();
		DataCache.setProjectName(projectName);
		String deviceName = netJComboBox.getSelectedItem() == null ? null : netJComboBox.getSelectedItem().toString();
		DataCache.setNetDevicesName(deviceName);
		DataCache.setCaptureUrl(urlFilterArea.getText());
		new CaptureThread("CaptureThread").start();
		this.progress.startProgress("Starting...");
	}
	
}