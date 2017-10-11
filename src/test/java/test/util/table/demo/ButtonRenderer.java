package test.util.table.demo;

import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;  
  
/** 
 *  
 * @author __USER__ 
 */  
public class ButtonRenderer implements TableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		JButton button = new JButton();
		if (hasFocus) {  
			button.setForeground(table.getForeground());  
			button.setBackground(UIManager.getColor("Button.background"));  
        } else if (isSelected) {  
        	button.setForeground(table.getSelectionForeground());  
        	button.setBackground(table.getSelectionBackground());  
        } else {  
        	button.setForeground(table.getForeground());  
        	button.setBackground(UIManager.getColor("Button.background"));  
        }  
  
		button.setText((value == null) ? " " : value.toString());  
        return button; 
	}  
}  