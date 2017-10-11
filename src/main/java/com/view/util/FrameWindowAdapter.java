package com.view.util;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

import java.awt.Frame;

public class FrameWindowAdapter extends WindowAdapter {
	
	private Frame parentFrame;
	private Frame currentFrame;
	
	public FrameWindowAdapter(Frame parentFrame, Frame currentFrame){
		this.parentFrame = parentFrame;
		this.currentFrame = currentFrame;
	}

	public void windowOpened(WindowEvent e) {
		if(null != parentFrame && null != currentFrame){
			parentFrame.setEnabled(false);
			currentFrame.setEnabled(true);
			//currentFrame.setAlwaysOnTop(true);
		}
	}
	
	public void windowClosing(WindowEvent e) {
		int operate = JOptionPane.showConfirmDialog(currentFrame, "Are you Sure ?", "Confirm", JOptionPane.OK_CANCEL_OPTION);
		if(operate == JOptionPane.YES_OPTION){
			currentFrame.dispose();
		}
	}
	
	public void windowClosed(WindowEvent e) {
		if(null != parentFrame){
			parentFrame.setEnabled(true);
			parentFrame.setVisible(true);
			//parentFrame.setAlwaysOnTop(true);
		}
	}
}
