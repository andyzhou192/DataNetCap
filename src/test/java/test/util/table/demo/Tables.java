package test.util.table.demo;

import java.awt.Dimension;
import java.util.Vector;

import javax.swing.JCheckBox;  
import javax.swing.JFrame;  
import javax.swing.JScrollPane;  
import javax.swing.JTable;  
import javax.swing.event.TableModelEvent;  
import javax.swing.table.DefaultTableModel;  
  
public class Tables {  
	public static Object[] header = { "选择", "内容", "操作" };
	public static Object[][] datas = {  
            { new JCheckBox("111"), "111", "111" },  
            { new JCheckBox("222"), "222", "222" },  
            { new JCheckBox("333"), "333", "333" }};
  
	public static Vector<Object> getHead(){
		Vector<Object> head = new Vector<Object>();
		for(Object value : header){
			head.addElement(value);
		}
		return head;
	}
	
	public static Vector<Vector<Object>> getData(){
		Vector<Vector<Object>> rows = new Vector<Vector<Object>>();
		Vector<Object> data = new Vector<Object>();
		for(Object[] values : datas){
			for(Object value : values){
				data.addElement(value);
			}
			rows.addElement(data);
		}
		return rows;
	}
	
    public static JTable gettable() {  
        DefaultTableModel dm = new DefaultTableModel();  
        dm.setDataVector(getData(), getHead());  
  
        @SuppressWarnings("serial")
		JTable table = new JTable(dm) {  
            public void tableChanged(TableModelEvent e) {  
                super.tableChanged(e); 
                System.out.println("table changed:" + e.getColumn() + ":" + e.getLastRow() + ":" + e.getFirstRow());
                repaint();  
            }  
        };  
        new CheckBoxEditor(table, 0);
        new ButtonEditor(table, 2);
        return table;  
    }  
  
    public static void main(String args[]) {  
    	JFrame frame = new JFrame("sjh");  
        frame.setLayout(null);  
        JTable table = Tables.gettable();  
        JScrollPane src = new JScrollPane(table);  
        src.setBounds(0, 0, 400, 200);  
        frame.setSize(new Dimension(400, 200));  
        frame.add(src);  
        frame.setVisible(true); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }   
}  