package test.util.table;

import java.io.Serializable;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.table.AbstractTableModel;

import com.common.util.LogUtil;

@SuppressWarnings("serial")
public class HttpDataTableModel extends AbstractTableModel implements Serializable {
	
	private final Class<?> cl = HttpDataTableModel.class;

	private final String[] columnNames = {"ALL", "url", "method", "reqHeader", "reqParams", "statusCode", "reasonPhrase", "rspHeader", "rspBody", "Operate"};
	private Vector<Vector<Object>> dataVector;
	private Vector<Object> columnIdentifiers;
	
	public HttpDataTableModel() {
		this.dataVector = new Vector<Vector<Object>>();
		this.columnIdentifiers = convertToVector(columnNames);
		justifyRows(0, getRowCount());
        fireTableStructureChanged();
	}

	@Override
	public int getRowCount() {
		return dataVector.size();
	}

	@Override
	public int getColumnCount() {
		return columnIdentifiers.size();
	}

	@Override
	public String getColumnName(int columnIndex) {
		if (columnIndex < columnIdentifiers.size() && (columnIndex >= 0)) {
			return String.valueOf(columnIdentifiers.elementAt(columnIndex));
        } else {
        	LogUtil.err(cl, "Column Index Out Of Bounds:" + columnIndex);
        }
		return super.getColumnName(columnIndex);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return Object.class;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Vector<?> rowVector = (Vector<?>)dataVector.elementAt(rowIndex);
        return rowVector.elementAt(columnIndex);
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Vector<Object> rowVector = (Vector<Object>)dataVector.elementAt(rowIndex);
        rowVector.setElementAt(aValue, columnIndex);
        fireTableCellUpdated(rowIndex, columnIndex);
	}

	/**
     * Returns a vector that contains the same objects as the array.
     * @param anArray  the array to be converted
     * @return  the new vector; if <code>anArray</code> is <code>null</code>,
     *                          returns <code>null</code>
     */
    protected static Vector<Object> convertToVector(Object[] anArray) {
        if (anArray == null) {
            return null;
        }
        Vector<Object> v = new Vector<Object>(anArray.length);
        for (Object o : anArray) {
            v.addElement(o);
        }
        return v;
    }
    
    /**
     * Returns a vector of vectors that contains the same objects as the array.
     * @param anArray  the double array to be converted
     * @return the new vector of vectors; if <code>anArray</code> is
     *                          <code>null</code>, returns <code>null</code>
     */
    protected static Vector<Vector<Object>> convertToVector(Object[][] anArray) {
        if (anArray == null) {
            return null;
        }
        Vector<Vector<Object>> v = new Vector<Vector<Object>>(anArray.length);
        for (Object[] o : anArray) {
            v.addElement(convertToVector(o));
        }
        return v;
    }
    
    //
 // Manipulating rows
 //

     private void justifyRows(int from, int to) {
         // Sometimes the DefaultTableModel is subclassed
         // instead of the AbstractTableModel by mistake.
         // Set the number of rows for the case when getRowCount
         // is overridden.
         dataVector.setSize(getRowCount());

         for (int i = from; i < to; i++) {
             if (dataVector.elementAt(i) == null) {
                 dataVector.setElementAt(new Vector<Object>(), i);
             }
             ((Vector<Object>)dataVector.elementAt(i)).setSize(getColumnCount());
         }
     }
     
     public void addRow(String[] names){
    	 int rowIndex = getRowCount();
    	 dataVector.insertElementAt(convertToVector(names), rowIndex);
    	 justifyRows(rowIndex, rowIndex+1);
    	 fireTableRowsInserted(rowIndex, rowIndex);
     }

	public static void main(String[] args) throws InterruptedException {
		//String[] names = {"10", "11", "12", "13", "14", "15", "16", "17", "18", "19"};
		HttpDataTableModel model = new HttpDataTableModel();
		javax.swing.JTable table = new javax.swing.JTable(model);
		table.getTableHeader().setVisible(true);
		javax.swing.JFrame frame = new javax.swing.JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 600);
		frame.add(table);
		frame.setVisible(true);
		for(int i = 1; i < 10; i++){
			String[] names = new String[10];
			for(int j = 0; j < 10; j++){
				names[j] = String.valueOf(i * 10 + j);
				System.out.println(model.getColumnName(j) + ":" + names[j]);
				//table.setTableHeader(tableHeader);
			}
			model.addRow(names);
			TimeUnit.SECONDS.sleep(2);
		}
	}
}
