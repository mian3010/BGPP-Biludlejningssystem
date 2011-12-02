package bgpp2011;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.lang.reflect.*;

import java.util.*;

/**
 *
 * @author Michael Søby Andersen
 * @mail msoa@itu.dk
 * @date 26/11-2011
 * @time 12:33
 * 
 */
public class CustomerView extends View {
    
    private ArrayList<Customer> customers;
    
    public CustomerView(Canvas canvas)
    {
        super(canvas);
        customers = new ArrayList<Customer>();
        topText = "Customers";
        customers.add(new Customer(1, "Toke Jensen", 11111111, "Vej 1, By 1", "Konto 1"));
        customers.add(new Customer(2, "Magnus Stahl", 22222222, "Vej 2, By 2", "Konto 2"));
        customers.add(new Customer(3, "Michael Søby Andersen", 33333333, "Vej 3, By 3", "Konto 3"));
        customers.add(new Customer(4, "Jens Jensen", 44444444, "Vej 4, By 4", "Konto 4"));
        customers.add(new Customer(5, "Lars Larsen", 55555555, "Vej 5, By 5", "Konto 5"));
    }
    @Override
    public JPanel draw()
    {
        super.draw();
        String[] columnNames = {"ID", "Name", "Address", "Phone Number", "Account", "Remove"};
        Object[][] data = parseObjects(customers);
        int[] columnSizes = {10,1000,1000,200,200,100};
        //TableModel model = new TableModel(columnNames, data);
       
        JTable table = createTable(columnNames, data, columnSizes);
        JScrollPane scrollPane = new JScrollPane(table);
        
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));
        contentPane.add(scrollPane, BorderLayout.CENTER);
        
        JPanel boxLayout = new JPanel();
        boxLayout.setLayout(new BoxLayout(boxLayout, BoxLayout.Y_AXIS));
        
        ArrayList<JButton> buttons = new ArrayList<JButton>();
        ArrayList<Object> views = new ArrayList<Object>();
        ArrayList<Object> params = new ArrayList<Object>();
        
        buttons.add(new JButton("< Back"));
        buttons.add(new JButton("Add Customer"));
        
        views.add(new FrontPageView(canvas));
        views.add("addCustomer");
        
        params.add(null);
        params.add(table);
        
        Iterator<JButton> i1 = buttons.iterator();
        Iterator<Object> i2 = views.iterator();
        Iterator<Object> i3 = params.iterator();
        while (i1.hasNext() && i2.hasNext() && i3.hasNext())
        {        
            final JButton button = i1.next();
            final Object object = i2.next();
            final Object parameters = i3.next();
            final View view = this;
            
            button.setMaximumSize(new Dimension(130,30));
            boxLayout.add(button);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	try
                	{
                		canvas.changeView((View)object);
                	}
                	catch (ClassCastException e1)
                	{
                		try
                		{
                			Method m = view.getClass().getMethod(object.toString(), JTable.class);
                			m.invoke(view, (JTable)parameters);
                		}
                		catch (NoSuchMethodException e2){}
                		catch (InvocationTargetException e2){}
                		catch (IllegalAccessException e2) {}
                		
                	}
                    
                }
            });
        }
        contentPane.add(boxLayout, BorderLayout.WEST);
        
        return contentPane;
    }
    public Object[][] parseObjects(ArrayList<Customer> dataObjects)
    {
        Object[][] data = new Object[dataObjects.size()][6];
        for (int j = 0; j < dataObjects.size(); j++)
        {
            data[j][0] = (int)dataObjects.get(j).getId();
            data[j][1] = (String)dataObjects.get(j).getName();
            data[j][2] = (int)dataObjects.get(j).getNumber();
            data[j][3] = (String)dataObjects.get(j).getAddress();
            data[j][4] = (String)dataObjects.get(j).getBankAccount();
            data[j][5] = (Boolean)false;
        }
        return data;
    }
    public void addCustomer(JTable table)
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
}
