package test;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JSeparator;
import javax.swing.JTree;
import javax.swing.JLabel;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.border.LineBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JTextArea;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("unused")
public class AppWindow implements TreeSelectionListener {

	private JFrame frame;
	private JTree tree = new JTree();
	JPanel pane = new JPanel();
	
	private final JPanel panel = new JPanel();
	private final JCheckBox chckbxNewCheckBox = new JCheckBox("New check box");
	private final JTextField textField = new JTextField();
	private final JTextArea txtrTest = new JTextArea();
	private final JLabel workSpaceLabel = new JLabel("WorkSpace : ");
	private final JTextField workSpaceTextField = new JTextField();
	private final JButton browseButton = new JButton("browse");
	private final JButton applyButton = new JButton("Apply");
	private final JCheckBox smokeScripCheckBox = new JCheckBox("smokeScript");
	private final JLabel smokeScriptLabel = new JLabel("smokeScript");
	private DefaultComboBoxModel<String> cbm = new DefaultComboBoxModel<String>(new String[] {"", "1", "2", "3", "4"});
	private final JComboBox<String> comboBox = new JComboBox<String>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppWindow window = new AppWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AppWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		textField.setColumns(10);
		frame = new JFrame();
		frame.setBackground(Color.LIGHT_GRAY);
		frame.setTitle("Preferences");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container container = frame.getContentPane();
		
		JSeparator separator = new JSeparator();
		container.add(separator, BorderLayout.NORTH);
		
		tree.setSelectionRow(0);
		tree.setEditable(true);
		tree.setForeground(Color.BLUE);
		tree.setToolTipText("Preferences");
		tree.setBorder(new LineBorder(Color.ORANGE));
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Preferences");
		
		DefaultMutableTreeNode root_general = new DefaultMutableTreeNode("General");
		DefaultMutableTreeNode root_general_workspace = new DefaultMutableTreeNode("Workspace");
		
		DefaultMutableTreeNode root_jcapture = new DefaultMutableTreeNode("Jcapture Setting");
		DefaultMutableTreeNode root_jcapture_Ethernet = new DefaultMutableTreeNode("Ethernet");
		
		DefaultMutableTreeNode root_script = new DefaultMutableTreeNode("Script Setting");
		DefaultMutableTreeNode root_script_java = new DefaultMutableTreeNode("Java");

		DefaultTreeModel treeModel = new DefaultTreeModel(root);
		treeModel.insertNodeInto(root_general, root, root.getChildCount());
		treeModel.insertNodeInto(root_jcapture, root, root.getChildCount());
		treeModel.insertNodeInto(root_script, root, root.getChildCount());
		treeModel.insertNodeInto(root_general_workspace, root_general, root_general.getChildCount());
		treeModel.insertNodeInto(root_jcapture_Ethernet, root_jcapture, root_jcapture.getChildCount());
		treeModel.insertNodeInto(root_script_java, root_script, root_script.getChildCount());
		
		tree.setModel(treeModel);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.addTreeSelectionListener(this);
		
		container.add(tree, BorderLayout.WEST);
		
		pane.setBorder(new LineBorder(new Color(255, 200, 0), 2));
		container.add(pane, BorderLayout.CENTER);
		GridBagLayout gbl_pane = new GridBagLayout();
		gbl_pane.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_pane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_pane.columnWeights = new double[]{1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_pane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		pane.setLayout(gbl_pane);
		
		GridBagConstraints gbc_workSpaceLabel = new GridBagConstraints();
		gbc_workSpaceLabel.gridwidth = 2;
		gbc_workSpaceLabel.insets = new Insets(0, 0, 5, 5);
		gbc_workSpaceLabel.anchor = GridBagConstraints.EAST;
		gbc_workSpaceLabel.gridx = 0;
		gbc_workSpaceLabel.gridy = 0;
		pane.add(workSpaceLabel, gbc_workSpaceLabel);
		workSpaceTextField.setColumns(20);
		
		GridBagConstraints gbc_workSpaceTextField = new GridBagConstraints();
		gbc_workSpaceTextField.gridwidth = 5;
		gbc_workSpaceTextField.insets = new Insets(0, 0, 5, 5);
		gbc_workSpaceTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_workSpaceTextField.gridx = 2;
		gbc_workSpaceTextField.gridy = 0;
		pane.add(workSpaceTextField, gbc_workSpaceTextField);
		
		GridBagConstraints gbc_browseButton = new GridBagConstraints();
		gbc_browseButton.insets = new Insets(0, 0, 5, 5);
		gbc_browseButton.gridx = 7;
		gbc_browseButton.gridy = 0;
		pane.add(browseButton, gbc_browseButton);
		
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 1;
		pane.add(smokeScriptLabel, gbc_lblNewLabel);
		
		GridBagConstraints gbc_smokeScripCheckBox = new GridBagConstraints();
		gbc_smokeScripCheckBox.gridwidth = 4;
		gbc_smokeScripCheckBox.anchor = GridBagConstraints.NORTH;
		gbc_smokeScripCheckBox.insets = new Insets(0, 0, 5, 5);
		gbc_smokeScripCheckBox.gridx = 2;
		gbc_smokeScripCheckBox.gridy = 1;
		pane.add(smokeScripCheckBox, gbc_smokeScripCheckBox);
		
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.gridwidth = 5;
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 2;
		gbc_comboBox.gridy = 2;
		comboBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cbm.removeAllElements();
				cbm.addElement("a");
				cbm.addElement("a");
				cbm.addElement("a");
			}
		});
		comboBox.setModel(cbm);
		pane.add(comboBox, gbc_comboBox);
		
		GridBagConstraints gbc_applyButton = new GridBagConstraints();
		gbc_applyButton.insets = new Insets(0, 0, 0, 5);
		gbc_applyButton.gridx = 7;
		gbc_applyButton.gridy = 7;
		pane.add(applyButton, gbc_applyButton);
	}

	public void valueChanged(TreeSelectionEvent e) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        if (node == null){
        	return;
        }
        JLabel label = null;
        switch (node.getUserObject().toString()){
        case "Workspace":
        	pane.removeAll();
        	label = new JLabel(node.getUserObject().toString());
        	pane.add(label);
        	break;
        case "Ethernet":
        	pane.removeAll();
        	//pane.add(new JcaptureSettingView(frame));
        	break;
        case "Java":
        	pane.removeAll();
        	//pane.add(new ScriptSettingView(frame));
        	break;
        default:
        	break;
        }
        pane.updateUI();
	}

}
