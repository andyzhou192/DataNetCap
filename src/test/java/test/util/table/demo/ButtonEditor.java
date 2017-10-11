package test.util.table.demo;

import java.awt.Component;  
import java.awt.event.ActionEvent;  
import java.awt.event.ActionListener;  
  
import javax.swing.AbstractCellEditor;  
import javax.swing.JButton;  
import javax.swing.JTable;  
import javax.swing.table.TableCellEditor;  
import javax.swing.table.TableColumnModel;

import test.util.table.TableCellRendererImpl;  
  
@SuppressWarnings("serial")
public class ButtonEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {  
    private JTable table;  
    private JButton button;  
    private String text;  
  
    public ButtonEditor(JTable table, int column) {  
        super();  
        this.table = table;  
        button = new JButton();  
        button.addActionListener(this);  
  
        TableColumnModel columnModel = table.getColumnModel();  
        columnModel.getColumn(column).setCellEditor(this);  
//        columnModel.getColumn(column).setCellRenderer(new ButtonRenderer());  
        columnModel.getColumn(column).setCellRenderer(new TableCellRendererImpl(TableCellRendererImpl.BUTTON));
    }  
  
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {  
        text = (value == null) ? " " : value.toString();  
        button.setText(text);  
        return button;  
    }  
  
    public Object getCellEditorValue() {  
        return text;  
    }  
  
    public void actionPerformed(ActionEvent e) {  
        fireEditingStopped();  
        System.out.println(e.getActionCommand() + "   :    "  
                + table.getSelectedRow());  
    }  
}  