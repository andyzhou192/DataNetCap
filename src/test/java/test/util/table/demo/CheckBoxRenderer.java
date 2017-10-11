package test.util.table.demo;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;  
  
class CheckBoxRenderer implements TableCellRenderer {  
  
    public Component getTableCellRendererComponent(JTable table, Object value,  
            boolean isSelected, boolean hasFocus, int row, int column) {  
		JCheckBox checkBox = (JCheckBox) value;
    	if (hasFocus) {  
    		checkBox.setForeground(table.getForeground());  
    		checkBox.setBackground(UIManager.getColor("Button.background"));  
        } else if (isSelected) {  
        	checkBox.setForeground(table.getSelectionForeground());  
        	checkBox.setBackground(table.getSelectionBackground());  
        } else {  
        	checkBox.setForeground(table.getForeground());  
        	checkBox.setBackground(UIManager.getColor("Button.background"));  
        }  
        return checkBox; 
    }  
}  