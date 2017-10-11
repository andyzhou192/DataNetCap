package test.util.table;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

public class TableCellRendererImpl implements TableCellRenderer {

	public static int LABEL = 0;
	public static int CHECK_BOX = 1;
	public static int BUTTON = 3;
	public static int TEXT_FIELD = 4;
	
	private Component comp;
	public TableCellRendererImpl(int type){
		if(type == CHECK_BOX){
			comp = new JCheckBox();
		} else if(type == BUTTON){
			comp = new JButton();
		}
	}
	
	@Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		if (hasFocus) {
			comp.setForeground(table.getForeground());
			comp.setBackground(UIManager.getColor("Button.background"));
		} else if (isSelected) {
			comp.setForeground(table.getSelectionForeground());
			comp.setBackground(table.getSelectionBackground());
		} else {
			comp.setForeground(table.getForeground());
			comp.setBackground(UIManager.getColor("Button.background"));
		}
		if(comp instanceof JButton){
			JButton button = (JButton) comp;
			button.setText((value == null) ? " " : value.toString());
			return button;
		}
		if(comp instanceof JCheckBox){
			return (JCheckBox) value;
		}
		return null;
	}
}  