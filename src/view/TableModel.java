package view;
import javax.swing.table.*;
import java.util.*;

/**
 * This class is used to override the regular tablemodel. We have changed methods for 
 * addRow and removeRow and isCellEditable
 */
@SuppressWarnings("serial")
class TableModel extends AbstractTableModel {
    private String[] columnNames;
    private Object[][] data;
    private HashMap<Integer, Boolean> cellEditable;
    
    public TableModel(Object[][] data, String[] columnNames, HashMap<Integer, Boolean> cellEditable)
    {
    	this.data = data;
    	this.columnNames = columnNames;
    	this.cellEditable = cellEditable;
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

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public Class getColumnClass(int column){   
    	Object value=this.getValueAt(0,column);   
    	return (value==null?Object.class:value.getClass());   
    }  
    /**
     * Method isCellEditable
     * 
     * This method sets the rows and columns editable in the table
     * 
     * @param row The row that is being queried
     * @param col The column that is being queried
     * @return boolean Whether or not the col/row is editable
     */
    public boolean isCellEditable(int row, int col) {
        return cellEditable.get(col);
    }

    public void setValueAt(Object value, int row, int col) {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }
    /**
     * Method addRow
     * 
     * This method adds a row to the table
     * 
     * @param dataInsert
     */
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
    /**
     * Method removeRow
     * 
     * This method removes a row from the table
     * 
     * @param rowID
     */
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
