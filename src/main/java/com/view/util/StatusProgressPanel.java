package com.view.util;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

@SuppressWarnings("serial")
public class StatusProgressPanel extends JPanel {

	private JProgressBar progress;
	private JLabel statusLabel;
	
	public StatusProgressPanel() {
		this.setLayout(new BorderLayout());
		statusLabel = new JLabel();
		progress = new JProgressBar(); 
		progress.setForeground(Color.GREEN);
		progress.setBounds(0, 0, 50, 20);
		this.add(statusLabel, BorderLayout.WEST);
		this.add(progress, BorderLayout.EAST);
	}
	
	public void setStatus(String status){
		statusLabel.setText(status);
	}
	
	public void startProgress(String status){
		statusLabel.setText(status);
		progress.setStringPainted(false);  
		progress.setIndeterminate(true);  
	}
	
	public void stopProgress(String status){  
		progress.setIndeterminate(false);  
		progress.setValue(100);  
		statusLabel.setText(status);
    }

}
