package bgpp2011;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.lang.reflect.*;

import java.util.*;
import java.util.Map.Entry;

/**
 *
 * @author Michael Søby Andersen
 * @mail msoa@itu.dk
 * @date 26/11-2011
 * @time 12:33
 * 
 */
public class CustomerView extends View {
    
    private HashMap<Integer, Customer> customers;
    private JTable table;
    
    public CustomerView(Canvas canvas)
    {
        super(canvas);
        topText = "Customers";
        Controller controller = new Controller();
        customers = controller.getCustomers();
    }
    @Override
    public JPanel draw()
    {
        super.draw();
        String[] columnNames = {"ID", "Name", "Address", "Phone Number", "Account", "Show reservations", "Remove"};
        Object[][] data = parseObjects(customers);
        int[] columnSizes = {10,1000,1000,200,200,100, 100};
       
        table = createTable(columnNames, data, columnSizes);
        JScrollPane scrollPane = new JScrollPane(table);
        
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));
        contentPane.add(scrollPane, BorderLayout.CENTER);
        
        JPanel boxLayout = createButtons();
        contentPane.add(boxLayout, BorderLayout.WEST);
        
        return contentPane;
    }
    public Object[][] parseObjects(HashMap<Integer, Customer> dataObjects)
    {
        Object[][] data = new Object[dataObjects.size()][7];
        Iterator<Entry<Integer, Customer>> i = dataObjects.entrySet().iterator();
        int j = 0;
        while (i.hasNext())
        {
        	Map.Entry<Integer, Customer> object = (Map.Entry<Integer, Customer>)i.next();
            data[j][0] = object.getValue().getId();
            data[j][1] = object.getValue().getName();
            data[j][2] = object.getValue().getNumber();
            data[j][3] = object.getValue().getAddress();
            data[j][4] = object.getValue().getBankAccount();
            data[j][5] = (Boolean)false;
            data[j][6] = (Boolean)false;
            j++;
        }
        return data;
    }
    public void addEntry(JTable table)
    {
        addToTable(table);
    }
    public void removeCustomer(JTable table, int rowID)
    {
        removeFromTable(rowID, table);
    }
    public JTable createTable(String[] columnNames, Object[][] data, int[] columnSizes)
    {
    	JTable table = super.createTable(columnNames, data, columnSizes);
    	
    	return table;
    }
    public void tableChanged(TableModelEvent e)
    {
    	super.tableChanged(e);
    	int row = e.getFirstRow();
    	int column = e.getColumn();
    	
    	switch (column)
    	{
    	case 5:
    		canvas.changeView(new ReservationView(canvas, (int)table.getValueAt(row, 0)));
    	break;
    	default:
    		
    	break;
    	}
    }
}
