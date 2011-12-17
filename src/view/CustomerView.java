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
 * 
 * This class is a subclass to view and draws the customer type of view.
 * It therefore extends View
 * 
 */
public class CustomerView extends View {
    
	/**
	 * Instance variables
	 * customers is used to store the customers
	 * table is used to store the table
	 */
    private HashMap<Integer, Customer> customers;
    private JTable table;
    
    /**
     * Constructor for CustomerView
     * 
     * The constructor initiates the controller and set the text to be displayed
     * at the top of the view
     * 
     * @param canvas The canvas
     */
    public CustomerView(Canvas canvas)
    {
        super(canvas);
        topText = "Customers";
    }
    /**
     * Method updateCustomers
     * 
     * This method updates the hashmaps containing customers from the controller
     */
    public void updateCustomers()
    {
    	controller = new Controller();
    	customers = controller.getModel().getCustomers();
    }
    /**
     * Method draw
     * 
     * This method draws the entire view for customers. It overrides the superclass draw
     * but also calls this. It draws the table, the buttons and everything else
     * 
     * @return JPanel The panel that contains the entire view
     */
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
    /**
     * Method parseObjects
     * 
     * This method takes a hashmap containing the customers, and converts it to
     * a 2d-array that is required to make a table.
     * 
     * @param dataObjects The hashmap containing customers
     * @return Object[][] The 2d-array that the table requires
     */
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
    /**
     * Method addEntry
     * 
     * This method adds an entry to the database, and if successfull adds it to the table
     * aswell. If not successfull it displays a message, and returns false
     * 
     * @param input The input fields
     * @return boolean Whether or not the method has succeeded
     */
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
    /**
     * Method drawAddFrame
     * 
     * This method draws the frame used to add customers
     */
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
    /**
     * Method removeCustomer
     * 
     * This method removes a customer. On success it removes it from
     * the table aswell. On fail it displays a messagebox
     * 
     * @param rowID The row to be removed
     * @param table The table to remove from
     */
    public void removeCustomer(int rowID, JTable table)
    {
    	boolean success = controller.deleteCustomer(customers.get(table.getValueAt(rowID, 0)));
    	if (success)
    		removeFromTable(rowID, table);
    	else
    		JOptionPane.showMessageDialog(canvas.getFrame(), "Could not remove customer - SQLException", "Error", JOptionPane.ERROR_MESSAGE);
    }
    /**
     * Method tableChanged
     * 
     * This method is called whenever the table has changed. When it has the method
     * changes the field via the method changeCustomer
     * 
     * @param e The event
     */
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
    /**
     * Method changeCustomer
     * 
     * This method changes a customer. On fail it displays a messagebox and
     * reverts the field to the original content
     * 
     * @param rowID The row that has changed
     * @param columnID The column that has changed
     * @param table The table that has changed
     */
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
    /**
     * Method mouseClicked
     * 
     * This method is called whenever the table is clicked. It checks what has been
     * clicked and calls the appropriate method. Either remove customer or view 
     * reservations for customer
     * 
     * @param e The event
     */
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
    /**
     * Required when implementing MouseListener
     * Not used
     */
    @Override
	public void mouseEntered(MouseEvent e) {
		
		
	}
    /**
     * Required when implementing MouseListener
     * Not used
     */
    @Override
	public void mouseExited(MouseEvent e) {
		
		
	}
    /**
     * Required when implementing MouseListener
     * Not used
     */
    @Override
	public void mousePressed(MouseEvent e) {
		
		
	}
    /**
     * Required when implementing MouseListener
     * Not used
     */
    @Override
	public void mouseReleased(MouseEvent e) {
		
		
	}
}
