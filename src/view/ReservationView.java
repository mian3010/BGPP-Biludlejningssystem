package view;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.TableModelEvent;

import model.Customer;
import model.Reservation;
import model.Vehicle;
import model.VehicleType;

import bgpp2011.Canvas;
import bgpp2011.Controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

/**
 * 
 * This class is a subclass to view and draws the reservation type of view.
 * It therefore extends View
 * 
 * @author Michael Søby Andersen, msoa@itu.dk
 * 
 */
public class ReservationView extends View {
	
	/**
	 * Instance variables
	 * 
	 * reservations, vehicletypes and customers is used to store the data from the database
	 * table is used to store the table
	 * id is used to specify whether or not it should show for one customer or for all
	 * drawGraphical is used to speficy whether or not to display graphical view
	 * graphical is used to store the grapical jpanel
	 * 
	 */
	private HashMap<Integer, Reservation> reservations;
	private HashMap<Integer, VehicleType> vehicletypes;
	private HashMap<Integer, Customer> customers;
    private JTable table;
    private int id = -1;
    private boolean drawGraphical = false;
    private ReservationGraphical graphical; 
    
    /**
     * Constructor for class ReservationView
     * 
     * The constructor initializes the canvas
     * 
     * @param canvas The canvas
     */
    public ReservationView(Canvas canvas)
    {
    	super(canvas);
    }
    /**
     * Method updateReservations
     * 
     * This method updates the hashmaps containing reservations, vehicletypes
     * and customers from the controller
     * 
     */
    public void updateReservations()
    {
    	controller = new Controller();
    	reservations = controller.getModel().getReservations();
    	vehicletypes = controller.getModel().getTypes();
    	customers = controller.getModel().getCustomers();
    }
    /**
     * Method setID
     * 
     * This method sets the ID of the customer that reservations should be displayed for
     * -1 for all customers
     * 
     * @param id The id to set
     */
    public void setID(int id)
    {
    	this.id = id;
    	canvas.changeView(canvas.getView("reservation"));
    }
    /**
     * Method draw
     * 
     * This method draws the entire view for reservations. It overrides the superclass draw
     * but also calls this. It draws the table, the buttons and everything else
     * 
     * @return JPanel The panel that contains the entire view
     */
    @Override
    public JPanel draw()
    {
        updateReservations();
        if (id != -1)
        	topText = "Reservations for customer: " + customers.get(id).toString();
        else
        	topText = "Reservations";
        JPanel contentPane = super.draw();
        if (!drawGraphical)
        {
	        String[] columnNames = {"ID","Customer","Vehicle","Start date","End date", "Remove"};
	        Object[][] data = parseObjects(reservations, id);
	        int[] columnSizes = {30,1000,1000,300,300,30};
	        HashMap<Integer, Boolean> cellEditable = new HashMap<Integer, Boolean>();
	        cellEditable.put(0, false);
	        cellEditable.put(1, false);
	        cellEditable.put(2, false);
	        cellEditable.put(3, true);
	        cellEditable.put(4, true);
	        cellEditable.put(5, false);
	       
	        table = createTable(columnNames, data, cellEditable, columnSizes);
	        JScrollPane scrollPane = new JScrollPane(table);
	        
	        scrollPane.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));
	        contentPane.add(scrollPane, BorderLayout.CENTER);
	        
	        JPanel boxLayout = createButtons();
	        contentPane.add(boxLayout, BorderLayout.WEST);
        }
        else
        {
        	//Graphical layout is selected, create the graphical JPanel
        	graphical = new ReservationGraphical(controller, new Date(System.currentTimeMillis()), 15);
        	contentPane.add(graphical, BorderLayout.CENTER);
        	JPanel boxLayout = createButtons();
	        contentPane.add(boxLayout, BorderLayout.WEST);
        }
        return contentPane;
    }
    /**
     * Method graphical
     * 
     * This method sets the boolean for graphical, and reloads the view
     * 
     * @param graphical Whether or not graphical or textual should be selected
     */
    public void graphical(boolean graphical)
    {
    	drawGraphical = graphical;
    	canvas.changeView(this);
    }
    /**
     * Method parseObjects
     * 
     * This method takes a hashmap containing the reservations, and converts it to
     * a 2d-array that is required to make a table. It either only takes the customers
     * defined by id, or all if id is -1
     * 
     * @param dataObjects The hashmap containing reservations
     * @param id The id of the customer, -1 for all
     * @return Object[][] The 2d-array that the table requires
     */
    public Object[][] parseObjects(HashMap<Integer, Reservation> dataObjects, int id)
    {
    	HashMap<Integer, Reservation> reservation = new HashMap<Integer, Reservation>();
    	Iterator<Entry<Integer, Reservation>> i = dataObjects.entrySet().iterator();
    	while (i.hasNext())
    	{
        	Map.Entry<Integer, Reservation> object = (Map.Entry<Integer, Reservation>)i.next();
    		if (object.getValue().getCustomer().getId() == id)
    			reservation.put(object.getValue().getId(), object.getValue());
    		else if (id == -1)
    			reservation.put(object.getValue().getId(), object.getValue());
    	}
    			
        Object[][] data = new Object[reservation.size()][6];
        Iterator<Entry<Integer, Reservation>> i2 = reservation.entrySet().iterator();
        int j = 0;
        while (i2.hasNext())
        {
        	Map.Entry<Integer, Reservation> object = (Map.Entry<Integer, Reservation>)i2.next();
            data[j][0] = object.getValue().getId();
            data[j][1] = object.getValue().getCustomer().toString();
            data[j][2] = object.getValue().getVehicle().toString();
            data[j][3] = object.getValue().getStartdate();
            data[j][4] = object.getValue().getEnddate();
            data[j][5] = generateIcon("delete");
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
     * @param input The input fields from add form
     * @return boolean Whether or not it has been successful
     */
    public boolean addEntry(Object[] input)
    {
    	Reservation success = controller.createReservation((Customer)((JComboBox)input[0]).getSelectedItem(),
    														(VehicleType)((JComboBox)input[1]).getSelectedItem(),
    														Date.valueOf(((JTextField)input[2]).getText()),
    														Date.valueOf(((JTextField)input[3]).getText()));
    	updateReservations();
    	
    	if (success != null)
    	{
    		HashMap<Integer, Reservation> tempMap = new HashMap<Integer, Reservation>();
    		tempMap.put(success.getId(), success);
    		Object[][] tempData = parseObjects(tempMap, id);
    		addToTable(tempData[0], table);
    		return true;
    	}
    	else
    	{
    		JOptionPane.showMessageDialog(canvas.getFrame(), "Could not create reservation - Maybe it already exists", "Error", JOptionPane.ERROR_MESSAGE);
    		return false;
    	}
    }
    /**
     * Method drawAddFrame
     * 
     * This method draws the frame used to add reservations
     */
    public void drawAddFrame()
    {
    	addText = "Add reservation";
    	JLabel[] labels = new JLabel[4];
    	labels[0] = new JLabel("Customer: ", JLabel.TRAILING);
    	labels[1] = new JLabel("Vehicle type: ", JLabel.TRAILING);
    	labels[2] = new JLabel("Start date: ", JLabel.TRAILING);
    	labels[3] = new JLabel("End date: ", JLabel.TRAILING);
    	JFrame frame = new JFrame(addText);
    	frame.setResizable(false);
    	JPanel pane = drawAddFrameHead();

    	JPanel panel = new JPanel();
    	panel.setLayout(new GridLayout(0,2));
    	
    	Object[] input = new Object[labels.length];
    	
    	Object[] customersObj = new Object[customers.size()];
    	Iterator<Entry<Integer, Customer>> i2 = customers.entrySet().iterator();
    	int j2 = 0;
    	while (i2.hasNext())
    	{
    		Map.Entry<Integer, Customer> customer = (Map.Entry<Integer, Customer>)i2.next();
    		customersObj[j2] = customer.getValue();
    		j2++;
    	}
    	
    	input[0] = new JComboBox(customersObj);
    	
    	Object[] types = new Object[vehicletypes.size()];
    	Iterator<Entry<Integer, VehicleType>> i = vehicletypes.entrySet().iterator();
    	int j = 0;
    	while (i.hasNext())
    	{
    		Map.Entry<Integer, VehicleType> type = (Map.Entry<Integer, VehicleType>)i.next();
    		types[j] = type.getValue();
    		j++;
    	}
    	input[1] = new JComboBox(types);
    	
    	input[2] = new JTextField();
    	input[3] = new JTextField();
    	
    	panel.add(labels[0]);
    	panel.add((JComboBox)input[0]);
    	panel.add(labels[1]);
    	panel.add((JComboBox)input[1]);
    	panel.add(labels[2]);
    	panel.add((JTextField)input[2]);
    	panel.add(labels[3]);
    	panel.add((JTextField)input[3]);

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
     * Method drawSearchFrame
     * 
     * This method draws the frame used to search for available vehicles
     */
    public void drawSearchFrame()
    {
    	addText = "Search available vehicles";
    	JLabel[] labels = new JLabel[2];
    	labels[0] = new JLabel("Start date: ", JLabel.TRAILING);
    	labels[1] = new JLabel("End date: ", JLabel.TRAILING);
    	JFrame frame = new JFrame(addText);
    	frame.setResizable(false);
    	JPanel pane = drawAddFrameHead();

    	JPanel panel = new JPanel();
    	panel.setLayout(new GridLayout(0,2));
    	
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	JFormattedTextField[] input = new JFormattedTextField[2];
    	input[0] = new JFormattedTextField(format);
    	input[1] = new JFormattedTextField(format);
    	
    	panel.add(labels[0]);
    	panel.add(input[0]);
    	panel.add(labels[1]);
    	panel.add(input[1]);

    	panel.setAlignmentX(0);
    	
    	JPanel foot = drawSearchFrameFoot(frame, input);
    	
    	pane.add(panel);
    	pane.add(foot);
    	pane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    	frame.setContentPane(pane);
    	frame.pack();
    	frame.setVisible(true);
    }
    /**
     * Method drawSearchFrameFoot
     * 
     * This method draws the foot of the frame for searching for available vehicles
     * It adds actionslisteners to the buttons
     * 
     * @param frame The frame that contains head and body
     * @param input The input-fields from body
     * @return JPanel The panel containing the foot
     */
    public JPanel drawSearchFrameFoot(final JFrame frame, final Object[] input)
    {
    	
    	JPanel buttonscont = new JPanel();
    	buttonscont.setLayout(new FlowLayout(FlowLayout.LEFT));
    	
    	JButton[] buttons = new JButton[2];
    	buttons[0] = new JButton("Search");
    	buttons[0].addActionListener(new ActionListener() {
            	@Override
            	public void actionPerformed(ActionEvent e) {
            		drawSearchResultFrame(input);
            		frame.dispose();
            	} 
            });
    	
    	buttons[1] = new JButton("Cancel");
    	buttons[1].addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		frame.dispose();
        	} 
        });
    	
    	for (JButton button : buttons)
    	{
    		buttonscont.add(button);
    	}
    	
    	buttonscont.setAlignmentX(0);
    	
    	return buttonscont;
    }
    /**
     * Method drawSearchResultFrame
     * 
     * This method draws the frame for showing the results from searching
     * for available vehicles
     * 
     * @param input The input fields from the search form
     */
    public void drawSearchResultFrame(Object[] input)
    {
    	addText = "Available vehicles";
    	JLabel[] labels_left = new JLabel[vehicletypes.size()];
    	JLabel[] labels_right = new JLabel[vehicletypes.size()];
    	
    	JFrame frame = new JFrame(addText);
    	frame.setResizable(false);
    	JPanel pane = drawAddFrameHead();

    	JPanel panel = new JPanel();
    	panel.setLayout(new GridLayout(0,2));
    	
    	Iterator<Entry<Integer, VehicleType>> i = vehicletypes.entrySet().iterator();
    	int j = 0;
    	while (i.hasNext())
    	{
    		Map.Entry<Integer, VehicleType> vehicletype = (Map.Entry<Integer, VehicleType>)i.next();
    		labels_left[j] = new JLabel(vehicletype.getValue().getName()+": ", JLabel.TRAILING);
    		panel.add(labels_left[j]);
    		Date startdate = new Date((((java.util.Date)((JFormattedTextField)input[0]).getValue()).getTime()));
    		Date enddate = new Date((((java.util.Date)((JFormattedTextField)input[1]).getValue()).getTime()));
    		labels_right[j] = new JLabel(""+controller.typeCounting(vehicletype.getValue(), startdate, enddate), JLabel.LEADING);
    		panel.add(labels_right[j]);
    		j++;
    	}

    	panel.setAlignmentX(0);
    	
    	JPanel foot = drawSearchFrameResultFoot(frame, input);
    	
    	pane.add(panel);
    	pane.add(foot);
    	pane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    	frame.setContentPane(pane);
    	frame.pack();
    	frame.setVisible(true);
    }
    /**
     * Method drawSearchFrameResultFoot
     * 
     * This method draws the foot of the frame for displaying results from searching
     * It adds actionslisteners to the button
     * 
     * @param frame The frame that contains head and body
     * @param input The input-fields from body
     * @return JPanel The panel containing the foot
     */
    public JPanel drawSearchFrameResultFoot(final JFrame frame, final Object[] input)
    {
    	
    	JPanel buttonscont = new JPanel();
    	buttonscont.setLayout(new FlowLayout(FlowLayout.LEFT));
    	
    	JButton button = new JButton("Close");
    	button.addActionListener(new ActionListener() {
            	@Override
            	public void actionPerformed(ActionEvent e) {
            		frame.dispose();
            	} 
            });
    	buttonscont.add(button);
    	
    	buttonscont.setAlignmentX(0);
    	
    	return buttonscont;
    }
    /**
     * Method tableChanged
     * 
     * This method is called whenever the table has changed. When it has the method
     * changes the field via the method changeReservation
     * 
     * @param e The event
     */
    public void tableChanged(TableModelEvent e)
    {
    	if (!noChange)
    	{
	    	int row = e.getFirstRow();
	    	int column = e.getColumn();
	    	if(column != 5 && column != 6)
	    		changeReservation(row, column, table);
    	}
    }
    /**
     * Method changeCustomer
     * 
     * This method changes the customer of a reservation.
     * Called by doubleclick on customer in table
     * 
     * @param rowID The row that has been changed
     * @param newCustomer The customer to change to
     */
    public void changeCustomer(int rowID, int newCustomer)
    {
    	Reservation newR = new Reservation(reservations.get(table.getValueAt(rowID, 0)).getId(),
    										customers.get(newCustomer),
    										reservations.get(table.getValueAt(rowID, 0)).getVehicle(),
    										reservations.get(table.getValueAt(rowID, 0)).getDateStart(),
    										reservations.get(table.getValueAt(rowID, 0)).getDateEnd()
    										);
    	Reservation oldR = new Reservation(reservations.get(table.getValueAt(rowID, 0)).getId(),
    										reservations.get(table.getValueAt(rowID, 0)).getCustomer(),
											reservations.get(table.getValueAt(rowID, 0)).getVehicle(),
											reservations.get(table.getValueAt(rowID, 0)).getDateStart(),
											reservations.get(table.getValueAt(rowID, 0)).getDateEnd()
											);

    	
    	boolean success = controller.editReservation(newR, oldR);
    	if (success)
    		table.setValueAt(newR.getCustomer().toString(), rowID, 1);
    	else
    		JOptionPane.showMessageDialog(canvas.getFrame(), "Could not change customer on the reservation - SQLException", "Error", JOptionPane.ERROR_MESSAGE);
    }
    /**
     * Method changeType
     * 
     * This method changes the vehicle type of a reservation.
     * Called by doubleclick on vehicle in table
     * 
     * @param rowID The row that has changed
     * @param newType The type to change to
     */
    public void changeType(int rowID, int newType)
    {
    	Vehicle vehicle = controller.searchVehicles(vehicletypes.get(newType), reservations.get(table.getValueAt(rowID, 0)).getDateStart(), reservations.get(table.getValueAt(rowID, 0)).getDateEnd());
    	if (vehicle != null)
    	{
    		Reservation newR = new Reservation(reservations.get(table.getValueAt(rowID, 0)).getId(),
    											reservations.get(table.getValueAt(rowID, 0)).getCustomer(),
												vehicle,
												reservations.get(table.getValueAt(rowID, 0)).getDateStart(),
												reservations.get(table.getValueAt(rowID, 0)).getDateEnd()
												);
			Reservation oldR = new Reservation(reservations.get(table.getValueAt(rowID, 0)).getId(),
												reservations.get(table.getValueAt(rowID, 0)).getCustomer(),
												reservations.get(table.getValueAt(rowID, 0)).getVehicle(),
												reservations.get(table.getValueAt(rowID, 0)).getDateStart(),
												reservations.get(table.getValueAt(rowID, 0)).getDateEnd()
												);
			boolean success = controller.editReservation(newR, oldR);
			if (success)
				table.setValueAt(newR.getVehicle().toString(), rowID, 2);
			else
				JOptionPane.showMessageDialog(canvas.getFrame(), "Could not change vehicle type on the reservation - SQLException", "Error", JOptionPane.ERROR_MESSAGE);
    	}
    	else
    		JOptionPane.showMessageDialog(canvas.getFrame(), "No available vehicles of that type", "Error", JOptionPane.ERROR_MESSAGE);
    }
    /**
     * Method removeReservation
     * 
     * This method removes a reservation. On success it removes it from
     * the table aswell. On fail it displays a messagebox
     * 
     * @param rowID The row to be removed
     * @param table The table to remove from
     */
    public void removeReservation(int rowID, JTable table)
    {
    	boolean success = controller.deleteReservation(reservations.get(table.getValueAt(rowID, 0)));
    	if (success)
    		removeFromTable(rowID, table);
    	else
    		JOptionPane.showMessageDialog(canvas.getFrame(), "Could not remove reservation - SQLException", "Error", JOptionPane.ERROR_MESSAGE);
    }
    /**
     * Method changeReservation
     * 
     * This method changes a reservation. On fail it displays a messagebox and
     * reverts the field to the original content
     * 
     * @param rowID The row that has been changed
     * @param columnID The column that has been changed
     * @param table The table that has been changed
     */
    public void changeReservation(int rowID, int columnID, JTable table)
    {
    	Reservation oldReservation = reservations.get(table.getValueAt(rowID, 0));
    	int arg0 = oldReservation.getId();
    	Customer arg1 = oldReservation.getCustomer();
    	Vehicle arg2 = oldReservation.getVehicle();
    	Date arg3 = oldReservation.getDateStart();
    	Date arg4 = oldReservation.getDateEnd();
    	switch (columnID)
    	{
    	case 3:
    		System.out.println(Date.valueOf((String)table.getValueAt(rowID, 3)));
    		break;
    	case 4:
    		arg4 = Date.valueOf((String)table.getValueAt(rowID, 4));
    	break;
    	default:
    	break;
    	}
    	Reservation newReservation = new Reservation(arg0, arg1, arg2, arg3, arg4);
    	boolean success = controller.editReservation(newReservation, oldReservation);
    	if (!success)
    	{
    		JOptionPane.showMessageDialog(canvas.getFrame(), "Could not change reservation - SQLException", "Error", JOptionPane.ERROR_MESSAGE);
    		noChange = true;
    		switch (columnID)
        	{
        	case 3:
        		table.setValueAt(oldReservation.getStartdate(), rowID, 3);
        	break;
        	case 4:
        		table.setValueAt(oldReservation.getEnddate(), rowID, 4);
        	break;
        	default:
        	break;
        	}
    		noChange = false;
    	}
    }
    /**
     * Method createButtons
     * 
     * This method creates the buttons specific for reservation view. It puts them in a 
     * JPanel and return that. It also adds actionlisteners to them
     * Overrides super createButtons and calls this
     * 
     * @return JPanel The panel containing the buttons
     */
    public JPanel createButtons()
    {
    	JPanel boxLayout = super.createButtons();
    	final ReservationView view = this;
    	if (!drawGraphical)
    	{
	    	JButton button = new JButton("Graphical");
	    	button.setMaximumSize(new Dimension(130,30));
	        boxLayout.add(button);
	        button.addActionListener(new ActionListener() {
	        	@Override
	        	public void actionPerformed(ActionEvent e) {
	        		view.graphical(true);
	            }
	        });
	        if (id != -1)
	        {
		        JButton button2 = new JButton("Reset customer");
		    	button2.setMaximumSize(new Dimension(130,30));
		        boxLayout.add(button2);
		        button2.addActionListener(new ActionListener() {
		        	@Override
		        	public void actionPerformed(ActionEvent e) {
		        		view.setID(-1);
		            }
		        });
	        }
    	}
    	else
    	{
	    	JButton button1 = new JButton("Textual");
	    	button1.setMaximumSize(new Dimension(130,30));
	        boxLayout.add(button1);
	        
	        JPanel dummy = new JPanel();
	        dummy.setMaximumSize(new Dimension(200,30));
	        boxLayout.add(dummy);
	        
	        JPanel buttons = new JPanel();
	        buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
	        buttons.setMaximumSize(new Dimension(200,60));
	        buttons.setBorder(BorderFactory.createEmptyBorder(0,10,0,0));
	        
	        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	        final JFormattedTextField textfield1 = new JFormattedTextField(format);
	        textfield1.setValue(new Date(System.currentTimeMillis()));
	        textfield1.setMaximumSize(new Dimension(200,30));
	        buttons.add(textfield1);
	        
	        final JFormattedTextField textfield2 = new JFormattedTextField(Integer.class);
	        textfield2.setValue(15);
	        textfield2.setMaximumSize(new Dimension(200,30));
	        buttons.add(textfield2);
	        
	        boxLayout.add(buttons);
	        JButton button2 = new JButton("Change");
	    	button2.setMaximumSize(new Dimension(130,30));
	        boxLayout.add(button2);
	        
	        final ReservationGraphical graphical = this.graphical;
	        button1.addActionListener(new ActionListener() {
	        	@Override
	        	public void actionPerformed(ActionEvent e) {
	        		view.graphical(false);
	            }
	        });
	       button2.addActionListener(new ActionListener() {
	        	@Override
	        	public void actionPerformed(ActionEvent e) {
	        		graphical.setStartDate(new Date(((java.util.Date)textfield1.getValue()).getTime()));
	        		graphical.setDays((Integer)textfield2.getValue());
	        		graphical.repaint();
	            }
	        });
    	}
    	JButton button3 = new JButton("Search vehicles");
    	button3.setMaximumSize(new Dimension(130,30));
        boxLayout.add(button3);
        button3.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		view.drawSearchFrame();
            }
        });
    	return boxLayout;
    }
    /**
     * Method mouseClicked
     * 
     * This method is called whenever the table is clicked. It checks what has been
     * clicked and calls the appropriate method. Either remove reservation or change
     * customer/type
     * 
     * @param e The event
     */
    @Override
	public void mouseClicked(MouseEvent e) {
    	int column = table.columnAtPoint(e.getPoint());
    	int row = table.rowAtPoint(e.getPoint());
    	int numClicks = e.getClickCount();
    	String questionRemove = "Are you sure you want to remove this reservation?";
    	String titleRemove = "Removing reservation";
    	switch (column)
    	{
    	case 1:
    		if (numClicks == 2)
    		{
	    		Object[] choices = makeCustomerChoices();
	        	Customer chosencustomer = (Customer)JOptionPane.showInputDialog(canvas.getFrame(), "Choose new customer:", "Change customer", JOptionPane.QUESTION_MESSAGE, null, choices, choices[reservations.get(table.getValueAt(row, 0)).getCustomer().getId()-1]);
	        	try {
	        		changeCustomer(row, chosencustomer.getId());
	        	} catch (NullPointerException e1) {
	        		
	        	}
    		}
    	break;
    	case 2:
    		if (numClicks == 2)
    		{
	    		Object[] choices = makeTypeChoices();
	        	VehicleType chosentype = (VehicleType)JOptionPane.showInputDialog(canvas.getFrame(), "Choose new vehicle type:", "Change vehicle type", JOptionPane.QUESTION_MESSAGE, null, choices, choices[reservations.get(table.getValueAt(row, 0)).getVehicle().getType().getId()-1]);
	        	try {
	        		changeType(row, chosentype.getId());
	        	} catch (NullPointerException e1) {
	        		
	        	}
    		}
    	break;
    	case 5:
    		int response = JOptionPane.showConfirmDialog(canvas.getFrame(), questionRemove, titleRemove, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			if (response == 0)
    			removeReservation(row, table);
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
	/**
	 * Method makeCustomerChoices
	 * 
	 * This method makes an array from the customers hashmap. The array is required to create a
     * combobox with the customers
     * 
     * @return Object[] The array for the combobox
	 */
    public Object[] makeCustomerChoices()
	{
		Object[] choices = new Object[customers.size()];
    	Iterator<Entry<Integer, Customer>> i = customers.entrySet().iterator();
    	int j = 0;
    	while (i.hasNext())
    	{
    		Map.Entry<Integer, Customer> object = (Map.Entry<Integer, Customer>)i.next();
    		choices[j++] = object.getValue();
    	}
    	return choices;
	}
    /**
	 * Method makeTypeChoices
	 * 
	 * This method makes an array from the vehicletypes hashmap. The array is required to create a
     * combobox with the vehicletypes
     * 
     * @return Object[] The array for the combobox
	 */
    public Object[] makeTypeChoices()
	{
		Object[] choices = new Object[vehicletypes.size()];
    	Iterator<Entry<Integer, VehicleType>> i = vehicletypes.entrySet().iterator();
    	int j = 0;
    	while (i.hasNext())
    	{
    		Map.Entry<Integer, VehicleType> object = (Map.Entry<Integer, VehicleType>)i.next();
    		choices[j++] = object.getValue();
    	}
    	return choices;
	}
}
