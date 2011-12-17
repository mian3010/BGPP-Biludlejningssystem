package view;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.TableModelEvent;

import model.Customer;

import bgpp2011.Canvas;
import bgpp2011.Controller;


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
    }
    public void updateCustomers()
    {
    	controller = new Controller();
    	customers = controller.getModel().getCustomers();
    }
    @Override
    public JPanel draw()
    {
    	updateCustomers();
        JPanel contentPane = super.draw();
        String[] columnNames = {"ID", "Name", "Address", "Phone Number", "Account", "Show reservations", "Remove"};
        Object[][] data = parseObjects(customers);
        HashMap<Integer, Boolean> cellEditable = new HashMap<Integer, Boolean>();
        cellEditable.put(0, false);
        cellEditable.put(1, true);
        cellEditable.put(2, true);
        cellEditable.put(3, true);
        cellEditable.put(4, true);
        cellEditable.put(5, false);
        cellEditable.put(6, false);
        int[] columnSizes = {30,1000,1000,300,300,30,30};
       
        table = createTable(columnNames, data, cellEditable, columnSizes);
        table.doLayout();
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
            data[j][2] = object.getValue().getAddress();
            data[j][3] = object.getValue().getNumber();
            data[j][4] = object.getValue().getBankAccount();
            data[j][5] = generateIcon("update");
            data[j][6] = generateIcon("delete");
            j++;
        }
        return data;
    }
    public boolean addEntry(Object[] input)
    {
    	Customer success = controller.createCustomer(((JTextField)input[0]).getText(), Integer.parseInt(((JTextField)input[1]).getText()), ((JTextField)input[2]).getText(), ((JTextField)input[3]).getText());
    	updateCustomers();
    	if (success != null)
    	{
    		HashMap<Integer, Customer> tempMap = new HashMap<Integer, Customer>();
    		tempMap.put(success.getId(), success);
    		Object[][] tempData = parseObjects(tempMap);
    		addToTable(tempData[0], table);
    		return true;
    	}
    	else
    	{
    		JOptionPane.showMessageDialog(canvas.getFrame(), "Could not create customer - Maybe it already exists", "Error", JOptionPane.ERROR_MESSAGE);
    		return false;
    	}
    }
    public void drawAddFrame()
    {
    	addText = "Add customer";
    	JLabel[] labels = new JLabel[4];
    	labels[0] = new JLabel("First and last name: ", JLabel.TRAILING);
    	labels[1] = new JLabel("Phone number: ", JLabel.TRAILING);
    	labels[2] = new JLabel("Address: ", JLabel.TRAILING);
    	labels[3] = new JLabel("Bank account information: ", JLabel.TRAILING);
    	JFrame frame = new JFrame(addText);
    	frame.setResizable(false);
    	JPanel pane = drawAddFrameHead();

    	JPanel panel = new JPanel();
    	panel.setLayout(new GridLayout(0,2));
    	
    	Object[] input = new Object[labels.length];
    	input[0] = new JTextField();
    	input[1] = new JTextField();
    	input[2] = new JTextField();
    	input[3] = new JTextField();
    	
    	for (int i = 0; i < labels.length; i++)
    	{
    		panel.add(labels[i]);
    		panel.add((JTextField)input[i]);
    	}

    	panel.setAlignmentX(0);
    	
    	JPanel foot = drawAddFrameFoot(frame, input);
    	
    	pane.add(panel);
    	pane.add(foot);
    	pane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    	frame.setContentPane(pane);
    	frame.pack();
    	frame.setVisible(true);
    }
    public void removeCustomer(int rowID, JTable table)
    {
    	boolean success = controller.deleteCustomer(customers.get(table.getValueAt(rowID, 0)));
    	if (success)
    		removeFromTable(rowID, table);
    	else
    		JOptionPane.showMessageDialog(canvas.getFrame(), "Could not remove customer - SQLException", "Error", JOptionPane.ERROR_MESSAGE);
    }
    public void tableChanged(TableModelEvent e)
    {
    	if (!noChange)
    	{
	    	int row = e.getFirstRow();
	    	int column = e.getColumn();
	    	if (column != 5 && column != 6)
	    		changeCustomer(row, column, table);
    	}
    }
    public void changeCustomer(int rowID, int columnID, JTable table)
    {
    	Customer oldCustomer = customers.get(table.getValueAt(rowID, 0));
    	int arg0 = oldCustomer.getId();
    	String arg1 = oldCustomer.getName();
    	int arg2 = oldCustomer.getNumber();
    	String arg3 = oldCustomer.getAddress();
    	String arg4 = oldCustomer.getBankAccount();
    	switch (columnID)
    	{
    	case 1:
    		arg1 = (String)table.getValueAt(rowID, 1);
    	break;
    	case 2:
    		arg2 = (Integer)table.getValueAt(rowID, 3);
    	break;
    	case 3:
    		arg4 = (String)table.getValueAt(rowID, 4);
    	break;
    	case 4:
    		arg3 = (String)table.getValueAt(rowID, 2);
    	break;
    	default:
    	break;
    	}
    	Customer newCustomer = new Customer(arg0, arg1, arg2, arg3, arg4);
    	boolean success = controller.editCustomer(newCustomer, oldCustomer);
    	if (!success)
    	{
    		JOptionPane.showMessageDialog(canvas.getFrame(), "Could not change customer - SQLException", "Error", JOptionPane.ERROR_MESSAGE);
    		noChange = true;
    		switch (columnID)
        	{
        	case 1:
        		table.setValueAt(oldCustomer.getName(), rowID, 1);
        	break;
        	case 2:
        		table.setValueAt(oldCustomer.getAddress(), rowID, 2);
        	break;
        	case 3:
        		table.setValueAt(oldCustomer.getNumber(), rowID, 3);
        	break;
        	case 4:
        		table.setValueAt(oldCustomer.getBankAccount(), rowID, 4);
        	break;
        	default:
        	break;
        	}
    		noChange = false;
    	}
    }
    public void mouseClicked(MouseEvent e)
    {
    	int column = table.columnAtPoint(e.getPoint());
    	int row = table.rowAtPoint(e.getPoint());
    	String questionRemove = "Are you sure you want to remove this customer?";
    	String titleRemove = "Removing customer";
    	switch (column)
    	{
    	case 5:
    		((ReservationView)canvas.getView("reservation")).setID((Integer)table.getValueAt(row, 0));
    	break;
    	case 6:
    		int response = JOptionPane.showConfirmDialog(canvas.getFrame(), questionRemove, titleRemove, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			if (response == 0)
    			removeCustomer(row, table);
    	break;
    	}
    }
	@Override
	public void mouseEntered(MouseEvent e) {
		
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		
		
	}
}
