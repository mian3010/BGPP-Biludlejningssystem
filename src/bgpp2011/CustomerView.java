package bgpp2011;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.TableModelEvent;

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
    	customers = controller.getCustomers();
    }
    @Override
    public JPanel draw()
    {
    	updateCustomers();
        super.draw();
        String[] columnNames = {"ID", "Name", "Address", "Phone Number", "Account", "Show reservations", "Remove"};
        Object[][] data = parseObjects(customers);
        int[] columnSizes = {20,1000,1000,200,200,100, 100};
       
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
            data[j][2] = object.getValue().getAddress();
            data[j][3] = object.getValue().getNumber();
            data[j][4] = object.getValue().getBankAccount();
            data[j][5] = (Boolean)false;
            data[j][6] = (Boolean)false;
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
    public void removeCustomer(JTable table, int rowID)
    {
        
    }
    public JTable createTable(String[] columnNames, Object[][] data, int[] columnSizes)
    {
    	JTable table = super.createTable(columnNames, data, columnSizes);
    	
    	return table;
    }
    public void tableChanged(TableModelEvent e)
    {
    	String questionRemove = "Are you sure you want to remove this customer?";
    	String titleRemove = "Removing customer";
    	int row = e.getFirstRow();
    	int column = e.getColumn();
    	
    	switch (column)
    	{
    	case 5:
    		canvas.changeView(new ReservationView(canvas, (int)table.getValueAt(row, 0)));
    	break;
    	case 6:
    		int response = JOptionPane.showConfirmDialog(canvas.getFrame(), questionRemove, titleRemove, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			if (response == 0)
    			removeFromTable(row, table);
    	break;
    	default:
    		
    	break;
    	}
    }
}
