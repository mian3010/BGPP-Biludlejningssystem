package bgpp2011;
import javax.swing.table.*;

class TableModel extends AbstractTableModel {
    private String[] columnNames;
    private Object[][] data;
    
    public TableModel(Object[][] data, String[] columnNames)
    {
    	this.data = data;
    	this.columnNames = columnNames;
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    public Class getColumnClass(int column){   
    	Object value=this.getValueAt(0,column);   
    	return (value==null?Object.class:value.getClass());   
    }  

    /*
     * Don't need to implement this method unless your table's
     * editable.
     */
    public boolean isCellEditable(int row, int col) {
        return true;
    }

    /*
     * Don't need to implement this method unless your table's
     * data can change.
     */
    public void setValueAt(Object value, int row, int col) {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }
    
    public void addRow(Object[] dataInsert) {   
    	Object[][] temp = data;
        data = new Object[temp.length+1][temp[0].length];
        for (int i = 0; i < temp.length; i++)
        {
        	data[i] = temp[i];
        }
        if (dataInsert == null)
        	dataInsert = new Object[data[0].length];
        data[data.length-1] = dataInsert;
        this.fireTableDataChanged();   
    }
    public void removeRow(int rowID)
    {
    	Object[][] temp = data;
        data = new Object[temp.length-1][temp[0].length];
        int j = 0;
        for (int i = 0; i < temp.length; i++)
        {
        	if (i == rowID)
        		i++;
        	if (i < temp.length)
        		data[j] = temp[i];
        	j++;
        }
        this.fireTableDataChanged(); 
    }
}
