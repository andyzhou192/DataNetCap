package com.net;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map.Entry;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import com.net.common.Constants;
import com.net.netcap.DataCache;
import com.net.netcap.captor.CaptureThread;
import com.net.view.util.StatusProgressPanel;
import com.net.view.util.ViewModules;

import info.clearthought.layout.TableLayout;

@SuppressWarnings("serial")
public class WindowsFrame extends JFrame implements ActionListener {

	public StatusProgressPanel progress;
	
	private JLabel projectNameLabel;
	private JComboBox<Object> projectJComboBox;
	
	private JLabel ethernetLabel;
	private JComboBox<?> netJComboBox;
	
	private JLabel urlLabel;
	private JTextArea urlFilterArea;
	
	private JButton startBtn, stopBtn, pauseBtn, resumeBtn;

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
		projectJComboBox = new JComboBox<Object>();
		projectJComboBox.addPopupMenuListener(new PopupMenuListener(){  
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) { 
            	projectJComboBox.removeAllItems();
            	if(!DataCache.getProjectMap().isEmpty()){
            		for(Entry<String, Object> project : DataCache.getProjectMap().entrySet()){
            			String item = project.getValue().toString() + "_" + project.getKey();
            			projectJComboBox.addItem(item);
            			if(null != DataCache.getProjectName() && DataCache.getProjectName().length() > 0 && DataCache.getProjectName().equals(item.toString())){
            				projectJComboBox.setSelectedItem(DataCache.getProjectName());
            			}
            		}
            	} else if(null != DataCache.getProjectName()){
            		projectJComboBox.addItem(DataCache.getProjectName());
            		projectJComboBox.setSelectedItem(DataCache.getProjectName());
            	}
            }  
            
            public void popupMenuCanceled(PopupMenuEvent e) {
            }  
            
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) { 
            	if(null != projectJComboBox.getSelectedItem()){
            		DataCache.setProjectName(((String) projectJComboBox.getSelectedItem()).trim());  
            	}
            }  
        });  

		ethernetLabel = ViewModules.createJLabel("Ethernet:", Color.RED);
		ethernetLabel.setToolTipText("请选择网卡");
		netJComboBox = ViewModules.createComboBox(DataCache.getDevicesMap().keySet().toArray());
		urlLabel = ViewModules.createJLabel("Capture Url:(Multiple addresses are separated by ',')", Color.BLACK);
		urlLabel.setToolTipText("请填写待捕获的URL");
		urlFilterArea = new JTextArea(3, 20);
		urlFilterArea.setEditable(true);
		urlFilterArea.setLineWrap(true);
		startBtn = ViewModules.createButton("StartCapture", "Start", this);
//		startBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
		pauseBtn = ViewModules.createButton("PauseCapture", "Pause", this);
//		pauseBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		resumeBtn = ViewModules.createButton("ResumeCapture", "Resume", this);
//		resumeBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		stopBtn = ViewModules.createButton("StopCapture", "Stop", this);
//		stopBtn.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panel.add(projectNameLabel, "1, 1, 1, 1");
		panel.add(projectJComboBox, "2, 1, 3, 1");
		
		panel.add(ethernetLabel, "1, 3, 1, 1");
		panel.add(netJComboBox, "2, 3, 3, 1");
		
		panel.add(urlLabel, "1, 5, 3, 1");
		panel.add(urlFilterArea, "1, 7, 3, 1");
//		panel.add(startBtn, "3, 6, 1, 1");
//		panel.add(pauseBtn, "3, 7, 1, 1");
//		panel.add(resumeBtn, "3, 8, 1, 1");
//		panel.add(stopBtn, "3, 9, 1, 1");
		this.getContentPane().add(this.createToolBar(), BorderLayout.NORTH);
		this.getContentPane().add(panel, BorderLayout.CENTER);
		this.setCaptureEnabled(true, false, false, false);
		this.setVisible(true);
	}
	
	/**
	 * 创建工具栏
	 * @return
	 */
	public JToolBar createToolBar() {
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setAutoscrolls(true);
		toolBar.setBackground(new Color(216,218,254));
		
		startBtn.setIcon(new ImageIcon(Constants.START_NORMAL_ICON));
		startBtn.setToolTipText("Start Capture");
		startBtn.setBackground(new Color(216,218,254));
		toolBar.add(startBtn);
		
		pauseBtn.setIcon(new ImageIcon(Constants.PAUSE_DISABLED_ICON));
		pauseBtn.setToolTipText("Pause Capture");
		pauseBtn.setBackground(new Color(216,218,254));
		toolBar.add(pauseBtn);
		
		resumeBtn.setIcon(new ImageIcon(Constants.RESUME_ICON));
		resumeBtn.setToolTipText("Resume Capture");
		resumeBtn.setBackground(new Color(216,218,254));
		toolBar.add(resumeBtn);
		
		stopBtn.setIcon(new ImageIcon(Constants.STOP_NORMAL_ICON));
		stopBtn.setToolTipText("Stop Capture");
		stopBtn.setBackground(new Color(216,218,254));
		toolBar.add(stopBtn);
		
		return toolBar;
	}
	
	/**
	 * 
	 * @param start
	 * @param pause
	 * @param resume
	 * @param stop
	 */
	public void setCaptureEnabled(boolean start, boolean pause, boolean resume, boolean stop){
		startBtn.setEnabled(start);
		pauseBtn.setEnabled(pause);
		resumeBtn.setEnabled(resume);
		stopBtn.setEnabled(stop);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
		case "Start":
			String projectName = projectJComboBox.getSelectedItem() == null ? null : projectJComboBox.getSelectedItem().toString();
			DataCache.setProjectName(projectName);
			String deviceName = netJComboBox.getSelectedItem() == null ? null : netJComboBox.getSelectedItem().toString();
			DataCache.setNetDevicesName(deviceName);
			DataCache.setCaptureUrl(urlFilterArea.getText());
			CaptureThread.getInstance("CaptureThread").start();
			this.progress.startProgress("Capture Starting...");
			this.setCaptureEnabled(false, true, false, true);
			break;
		case "Pause":
			CaptureThread.getInstance("CaptureThread").pause();
			this.progress.startProgress("Capture Paused...");
			this.setCaptureEnabled(false, false, true, false);
			break;
		case "Resume":
			CaptureThread.getInstance("CaptureThread").resume();
			this.progress.startProgress("Capture Resume...");
			this.setCaptureEnabled(false, true, false, true);
			break;
		case "Stop":
			CaptureThread.getInstance("CaptureThread").stop();
			this.progress.stopProgress("Capture Stopped!");
			this.setCaptureEnabled(true, false, false, false);
			break;
		default:
			break;
		}
	}
	
}