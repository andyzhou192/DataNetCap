package com.net.view.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.SpringLayout;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import com.net.common.asserts.Assert;

public class ViewModules {
	
	public static Font defaultFont = new Font("", Font.BOLD, 14);
	
	/**
	 * 
	 * @param jMenuBar
	 * @param name
	 * @param width
	 * @param height
	 * @return
	 */
	public static JMenu addMenu(JMenuBar jMenuBar, String name, int width, int height) {
		return jMenuBar.add(ViewModules.getMenu(name, width, height));
	}
	
	/**
	 * 
	 * @param jMenuBar
	 * @param name
	 * @return
	 */
	public static JMenu addMenu(JMenuBar jMenuBar, String name) {
		return jMenuBar.add(ViewModules.getMenu(name, -1, -1));
	}

	/**
	 * 
	 * @param name
	 * @param width
	 * @param height
	 * @return
	 */
	public static JMenu getMenu(String name, int width, int height){
		JMenu menu = new JMenu();
		menu.setText(name);
		if(width != -1 || height != -1)
			menu.setPreferredSize(new java.awt.Dimension(width, height));
		return menu;
	}
	
	/**
	 * 
	 * @param menu
	 * @param name
	 * @param command
	 * @param listener
	 * @return
	 */
	public static JMenuItem addSimpleMenuItem(JMenu menu, String name, String command, ActionListener listener) {
		return menu.add(ViewModules.createSimpleMenuItem(name, command, listener));
	}
	
	/**
	 * 
	 * @param menu
	 * @param action
	 */
	public static JMenuItem addMenuItem(JMenu menu, Action action) {
		JMenuItem item = new JMenuItem(action);
		menu.add(item);
		return item;
	}
	
	/**
	 * 
	 * @param toolBar
	 * @param action
	 * @return
	 */
	public static JButton addToolButton(JToolBar toolBar, Action action) {
		JButton btn = new JButton(action);
		toolBar.add(btn);
		return btn;
	}
	
	/**
	 * 创建JMenuItem
	 * @param name
	 * @param command
	 * @param listener
	 * @return
	 */
	public static JMenuItem createSimpleMenuItem(String name, String command, ActionListener listener){
		JMenuItem menuItem = new JMenuItem();
		menuItem.setText(name);
		menuItem.setActionCommand(command);
		menuItem.addActionListener(listener);
		return menuItem;
	}

	/**
	 * @param orientation  0:HORIZONTAL  1:VERTICAL
	 * @return
	 */
	public static JSeparator createSeparator(int orientation){
		Assert.verify(orientation != 0 || orientation != 1, "orientation 取值为0或1，0:HORIZONTAL  1:VERTICAL");
		return new JSeparator(orientation);
	}
	
	/**
	 * 创建JTable
	 * @param dataVector  rows
	 * @param columnIdentifiers  column
	 * @return
	 */
	public static JTable createTable(Vector<?> dataVector, Vector<?> columnIdentifiers){
		DefaultTableModel tabModel = new DefaultTableModel();
		tabModel.setDataVector(dataVector, columnIdentifiers);
		return new JTable(tabModel);
	}
	
	/**
	 * 
	 * @param values
	 * @return
	 */
	public static Vector<Object> createVector(Object[] values) {
		Vector<Object> v = new Vector<Object>();
		for(Object title:values){
			v.addElement(title);
		}
		return v;
	}
	
	/**
	 * 创建JButton
	 * @param name
	 * @param command
	 * @param listener
	 * @return
	 */
	public static JButton createButton(String name, String command, ActionListener listener){
		JButton button = new JButton();
		button.setText(name);
		button.setActionCommand(command);
		button.addActionListener(listener);
		return button;
	}
	
	/**
	 * 
	 * @param name
	 * @param command
	 * @param iconName
	 * @param listener
	 * @return
	 */
	public static JButton createButton(String name, String command, URL iconUrl, ActionListener listener){
		JButton button = new JButton(name, new ImageIcon(iconUrl));
		button.setActionCommand(command);
		button.addActionListener(listener);
		button.setBackground(Color.LIGHT_GRAY);
		// 以下两行设置icon和文案垂直居中显示，文案显示在icon下面
		//button.setVerticalTextPosition(JButton.BOTTOM);
		//button.setHorizontalTextPosition(JButton.CENTER);
		return button;
	}
	
	/**
	 * 
	 * @param panel
	 * @param name
	 * @param command
	 * @param listener
	 * @return
	 */
	public static Component addButton(JPanel panel, String name, String command, ActionListener listener){
		JButton button = ViewModules.createButton(name, command, listener);
		return panel.add(button);
	}
	
	/**
	 * 创建JRadioButton
	 * @param content
	 * @param listener
	 * @return
	 */
	public static JRadioButton createRadioButton(String name, String command, ActionListener listener){
		JRadioButton rb = new JRadioButton();
		rb.setText(name);
		rb.setActionCommand(command);
		rb.addActionListener(listener);
		return rb;
	}
	
	/**
	 * 
	 * @param panel
	 * @param content
	 * @param listener
	 * @return
	 */
	public static Component addRadioButton(JPanel panel, String name, String command, ActionListener listener){
		JRadioButton rb = ViewModules.createRadioButton(name, command, listener);
		return panel.add(rb);
	}
	
	/**
	 * 创建JTextField
	 * @param width
	 * @param content
	 * @param enabled
	 * @return
	 */
	public static JTextField createTextField(int width, String content,  boolean enabled){
		JTextField textField = new JTextField(width);
		textField.setText(content);//1514
		textField.setEnabled(enabled);
		textField.setForeground(Color.BLACK);
		return textField;
	}
	
	/**
	 * 
	 * @param content
	 * @return
	 */
	public static JTextArea createTextArea(String content){
		JTextArea textArea = new JTextArea(content);
		textArea.setEditable(true);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setForeground(Color.BLACK);
		return textArea;
	}
	
	/**
	 * 创建JTextPane
	 * @param content
	 * @return
	 */
	public static JTextPane createTextPane(String content){
		JTextPane textPane = new JTextPane();
		textPane.setText(content);
		return textPane;
	}
	
	public static JPanel createPanel(String title){
		JPanel panel = new JPanel();
		TitledBorder titleBorder = BorderFactory.createTitledBorder(title);
		titleBorder.setTitleColor(Color.DARK_GRAY);
		titleBorder.setTitleFont(new Font("", Font.ITALIC, 14));
		panel.setBorder(titleBorder);
		return panel;
	}
	
	/**
	 * 创建JPanel
	 * @param title
	 * @param alignmentX   the new vertical alignment,can be one of: -1f(not set), Component.CENTER_ALIGNMENT, Component.TOP_ALIGNMENT, Component.BOTTOM_ALIGNMENT, Component.LEFT_ALIGNMENT, Component.RIGHT_ALIGNMENT
	 * @param alignmentY   the new horizontal alignment, the value is same as alignmentX
	 * @param axis  the axis to lay out components along. Can be one of: BoxLayout.X_AXIS, BoxLayout.Y_AXIS, BoxLayout.LINE_AXIS or BoxLayout.PAGE_AXIS
	 * @return
	 */
	public static JPanel createSimplePanel(String title, float alignmentX, float alignmentY, int axis){
		JPanel panel = new JPanel();
		if(null != title){
			TitledBorder titleBorder = BorderFactory.createTitledBorder(title);
			titleBorder.setTitleColor(Color.BLACK);
			titleBorder.setTitleFont(defaultFont);
			panel.setBorder(titleBorder);
		}
		if(-1f != alignmentX)
			panel.setAlignmentX(alignmentX);
		if(-1f != alignmentY)
			panel.setAlignmentY(alignmentY);
		if(-1 != axis)
			panel.setLayout(new BoxLayout(panel, axis));
		return panel;
	}
	
	public static JPanel createSimplePanel(String title, float alignmentX, float alignmentY, LayoutManager mgr){
		JPanel panel = ViewModules.createSimplePanel(title, alignmentX, alignmentY, -1);
		if(null != mgr)
			panel.setLayout(mgr);
		return panel;
	}
	
	public static JPanel createSimplePanel(String title, float alignmentX, float alignmentY, boolean alignOnBaseline){
		JPanel panel = ViewModules.createSimplePanel(title, alignmentX, alignmentY, -1);
		panel.setLayout(ViewModules.createFlowLayout(alignOnBaseline, -1, -1, -1));
		return panel;
	}
	
	/**
	 * 创建JCheckBox
	 * @param content
	 * @param mgr
	 * @return
	 */
	public static JCheckBox createCheckBox(String content, LayoutManager mgr){
		JCheckBox checkBox = new JCheckBox();
		checkBox.setText(content);
		checkBox.setForeground(Color.BLACK);
		checkBox.setFont(defaultFont);
		if(null != mgr)
			checkBox.setLayout(mgr);
		return checkBox;
	}
	
	/**
	 * 创建下拉列表JComboBox
	 * @param items
	 * @return
	 */
	public static JComboBox<Object> createComboBox(Object[] items){
		return new JComboBox<Object>(items);
	}
	
	/**
	 * 创建FlowLayout
	 * @param alignOnBaseline
	 * @return
	 */
	public static FlowLayout createFlowLayout(boolean alignOnBaseline, int align, int hgap, int vgap){
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setAlignOnBaseline(alignOnBaseline);
		if(-1 != align)
			flowLayout.setAlignment(align);
		if(-1 != hgap)
			flowLayout.setHgap(hgap);
		if(-1 != vgap)
			flowLayout.setVgap(vgap);
		return flowLayout;
	}
	
	/**
	 * 
	 * @param target
	 * @param axis
	 * @return
	 */
	public static BoxLayout createBoxLayout(Container target, int axis){
		BoxLayout boxLayout = new BoxLayout(target, axis);
		return boxLayout;
	}
	
	/**
	 * 在给定画板中添加多个组件
	 * @param jComp
	 * @param comps
	 */
	public static void addComponent(JComponent jComp, Component... comps){
		for(Component comp : comps){
			jComp.add(comp);
		}
	}
	
	/**
	 * 创建ButtonGroup，一般用于单选时的JRadioButton组
	 * @param buttons
	 * @return
	 */
	public static ButtonGroup createButtonGroup(AbstractButton... buttons){
		ButtonGroup group = new ButtonGroup();
		for(AbstractButton button : buttons){
			group.add(button);
		}
		return group;
	}
	
	/**
	 * 创建提示框
	 * @param parentComponent
	 * @param message
	 * @param title
	 * @param messageType  Can be one of:
	 * 		0:JOptionPane.ERROR_MESSAGE
	 * 		1:JOptionPane.INFORMATION_MESSAGE
	 * 		2:JOptionPane.WARNING_MESSAGE
	 * 		3:JOptionPane.QUESTION_MESSAGE
	 */
	public static void showMessageDialog(Component parentComponent, Object message, String title, int messageType){
		JOptionPane.showMessageDialog(parentComponent, message, title, messageType);
	}
	
	/**
	 * 创建提示框
	 * @param parentComponent
	 * @param message
	 */
	public static void showMessageDialog(Component parentComponent, Object message){
		JOptionPane.showMessageDialog(parentComponent, message);
	}
	
	/**
	 * 创建确认框
	 * @param parentComponent
	 * @param message
	 * @param title
	 * @param optionType Can be one of:
	 * 			-1:DEFAULT_OPTION
	 * 			0:YES_NO_OPTION
	 * 			1:YES_NO_CANCEL_OPTION
	 * 			2:OK_CANCEL_OPTION
	 * @return 
	 * 		-1:CLOSED_OPTION
	 * 		0:OK/YES is chosen
	 * 		1:NO is chosen
	 * 		2:CANCEL is chosen
	 */
	public static int showConfirmDialog(Component parentComponent, Object message, String title, int optionType){
		return JOptionPane.showConfirmDialog(parentComponent, message, title, optionType);
	}
	
	/**
	 * 设置JTextPane的文字为加粗显示
	 * @param textPanes
	 */
	public static void setTextPaneFontToBold(JTextPane... textPanes){
		for(JTextPane textPane : textPanes)
			textPane.getStyledDocument().setCharacterAttributes(0, 50, getBoldStyle(), true);
	}
	
	/**
	 * 设置文字为加粗
	 * @return
	 */
	public static Style getBoldStyle(){
		StyleContext sc = new StyleContext(); 
		Style defaultStyle = sc.getStyle(StyleContext.DEFAULT_STYLE);  
        StyleConstants.setFontSize(defaultStyle, 14);  
        StyleConstants.setBold(defaultStyle, true);
		return defaultStyle;  
	}
	
	/**
	 * 获取确定取消按钮区块
	 * @return
	 */
	public static JPanel getOkCanelPanel(ActionListener listener){
		JPanel buttonPanel = new JPanel();
		JButton ok = ViewModules.createButton("确定", "OK", listener);
		JButton cancel = ViewModules.createButton("取消", "CANCEL", listener);
		ViewModules.addComponent(buttonPanel, ok, cancel);
		return buttonPanel;
	}

	/**
	 * 
	 * @param string
	 * @return
	 */
	public static JLabel createJLabel(String text, Color color) {
		JLabel label = new JLabel(text);
		if(null == color)
			label.setForeground(Color.BLACK);
		else
			label.setForeground(color);
		label.setFont(defaultFont);
		return label;
	}
	
	/**
	 * 创建JLabel与JTextField组合JPanel
	 * @param text
	 * @param color
	 * @return
	 */
	public static JPanel createLabelTextFieldPair(String text, Color color, JComponent comp) {
		SpringLayout layout = new SpringLayout();
		JPanel panel = new JPanel();
		panel.setLayout(layout);
		JLabel label = ViewModules.createJLabel(text, color);
		comp.setSize(20, label.getHeight() + 5);
		layout.putConstraint(SpringLayout.WEST, comp, 20, SpringLayout.EAST, label);
		layout.putConstraint(SpringLayout.WEST, panel, 10, SpringLayout.WEST, label);
		//layout.putConstraint(SpringLayout.WEST, panel, 10, SpringLayout.WEST, label);
		layout.putConstraint(SpringLayout.SOUTH, panel, 10, SpringLayout.SOUTH, comp);
		layout.putConstraint(SpringLayout.SOUTH, panel, 10, SpringLayout.SOUTH, label);
		//layout.putConstraint(SpringLayout.NORTH, panel, 20, SpringLayout.NORTH, comp);
		layout.putConstraint(SpringLayout.NORTH, panel, 20, SpringLayout.NORTH, label);
		panel.add(label);
		panel.add(comp);
		return panel;
	}
	
	/**
	 * 与GridBagLayout mainLayout = new GridBagLayout(); 配合使用
	 * @param gridx 在 X轴（横向） 所处的位置。
	 * @param gridy 在 Y轴（纵向） 所处的位置。
	 * @param gridwidth X轴占据的单元格数 
	 * @param gridheight Y轴占据的单元格数
	 * @return
	 */
	public static GridBagConstraints getGridBagConstraints(int gridx, int gridy, int gridwidth, int gridheight){
		return getGridBagConstraints(gridx, gridy, gridwidth, gridheight, GridBagConstraints.BOTH);
	}
	
	/**
	 * 与GridBagLayout mainLayout = new GridBagLayout(); 配合使用
	 * @param gridx 在 X轴（横向） 所处的位置。
	 * @param gridy 在 Y轴（纵向） 所处的位置。
	 * @param gridwidth X轴占据的单元格数 
	 * @param gridheight Y轴占据的单元格数
	 * @return
	 */
	public static GridBagConstraints getGridBagConstraints(int gridx, int gridy, int gridwidth, int gridheight, int fill){
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = gridx; // 在 X轴（横向） 所处的位置。
		gbc.gridy = gridy; // 在 Y轴（纵向） 所处的位置。
		gbc.gridwidth = gridwidth; // X轴占据的单元格数
		gbc.gridheight = gridheight; // Y轴占据的单元格数
		gbc.weightx = 0.5; // 当窗口缩放时，是否缩放组件的左右间距
		gbc.weighty = 0.0; // 当窗口缩放时，是否缩放组件的上下间距
		gbc.anchor = GridBagConstraints.NORTHWEST; // 当组件小于其显示区域时使用此字段。它可以确定在显示区域中放置组件的位置。
		gbc.fill = fill; // 当格子有剩余空间时，填充空间
		gbc.insets = new Insets(5, 5, 5, 5); // 组件彼此的间距
		gbc.ipadx = 0; // 组件内部填充空间，即给组件的最小宽度添加多大的空间
		gbc.ipady = 0; // 组件内部填充空间，即给组件的最小高度添加多大的空间
		return gbc;
	}
	
	/**
	 * 
	 * @return
	 */
	public static GridBagLayout getGridBagLayout(int rowNum, int columnNum, int rowHeight, int columnWidth, double rowWeight, double colWeight){
		int[] rowHight = new int[rowNum];
		double[] rowWeights = new double[rowNum];
		for(int i = 0; i < rowNum; i++){
			rowHight[i] = rowHeight;
			rowWeights[i] = 1.0;
		}
		int[] colWidth = new int[columnNum];
		double[] colWeights = new double[columnNum];
		for(int i = 0; i < columnNum; i++){
			colWidth[i] = columnWidth;
			colWeights[i] = 1.0;
		}
		GridBagLayout mainLayout = new GridBagLayout(); 
		mainLayout.rowHeights = rowHight;
		mainLayout.rowWeights = rowWeights;
		mainLayout.columnWidths = colWidth;
		mainLayout.columnWeights = colWeights;
		return mainLayout;
	}
}
