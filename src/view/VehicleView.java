package view;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Map.Entry;

import javax.swing.*;
import javax.swing.event.TableModelEvent;

import model.Vehicle;
import model.VehicleType;

import bgpp2011.Canvas;
import bgpp2011.Controller;

/**
 *
 * @author Michael Søby Andersen
 * @mail msoa@itu.dk
 * 
 * This class is a subclass to view and draws the vehicle type of view.
 * It therefore extends View
 * 
 */
public class VehicleView extends View {
	
	/**
	 * Instance variables
	 * vehicles & vehicletypes are used to store the vehicles and vehicletypes from the database
	 * table is used to store all the tables created when the view is drawn
	 * columnNames is used to store the names of the columns in the tables
	 */
	private HashMap<Integer, Vehicle> vehicles;
	private HashMap<Integer, VehicleType> vehicletypes;
    private ArrayList<JTable> table;
    private String[] columnNames;
    
    /**
     * Constructor for VehicleView
     * 
     * The constructor initializes the canvas and sets the text on top of the page
     * 
     * @param canvas The canvas
     */
    public VehicleView(Canvas canvas)
    {
        super(canvas);
        topText = "Vehicles";
        
    }
    /**
     * Method updateVehicles
     * 
     * This method updates the hashmaps containing vehicles and vehicletypes
     * from the controller
     */
    private void updateVehicles()
    {
    	controller = new Controller();
    	vehicletypes = controller.getModel().getTypes();
        vehicles = controller.getModel().getVehicles();
    }
    /**
     * Method draw
     * 
     * This method draws the entire view for vehicles. It overrides the superclass draw
     * but also calls this. It draws the table, the buttons and everything else
     * 
     * @return JPanel The panel containing the page
     */
    @Override
    public JPanel draw()
    {
    	updateVehicles();
        JPanel contentPane = super.draw();
        columnNames = new String[6];
        columnNames[0] = "ID";
        columnNames[1] = "Make";
        columnNames[2] = "Model";
        columnNames[3] = "Year";
        columnNames[4] = "Change Type";
        columnNames[5] = "Remove";
        int[] columnSizes = {30,1000,1000,300,30,30};
        HashMap<Integer, Boolean> cellEditable = new HashMap<Integer, Boolean>();
        cellEditable.put(0, false);
        cellEditable.put(1, true);
        cellEditable.put(2, true);
        cellEditable.put(3, true);
        cellEditable.put(4, false);
        cellEditable.put(5, false);
        ArrayList<JLabel> label = new ArrayList<JLabel>();
        //We need to make several tables, therefore this is an arraylist.
        //In the other views it is just a variable
        table = new ArrayList<JTable>();
        ArrayList<JPanel> panel = new ArrayList<JPanel>();
        Iterator<Entry<Integer, VehicleType>> i = vehicletypes.entrySet().iterator();
        while (i.hasNext())
        {
        	Map.Entry<Integer, VehicleType> entry = (Map.Entry<Integer, VehicleType>)i.next();
        	label.add(new JLabel(entry.getValue().getName()+":"));
        	Object[][] data = parseObjects(vehicles, entry.getValue());
        	final JTable temp = createTable(columnNames, data, cellEditable, columnSizes);
        	temp.removeMouseListener(temp.getMouseListeners()[0]);
        	final VehicleView view = this;
        	temp.addMouseListener(new MouseListener() {
        		//In this view we need to pass the table along when we click the table
                @Override
                public void mouseClicked(MouseEvent e) {
                	view.mouseClicked(e, temp);
                }
                //The rest of the mousemethods are required, but not used
				@Override
				public void mouseEntered(MouseEvent e) {}
				@Override
				public void mouseExited(MouseEvent e) {}
				@Override
				public void mousePressed(MouseEvent e) {}
				@Override
				public void mouseReleased(MouseEvent e) {}
            });
        	table.add(temp);
        	panel.add(new JPanel());
        }
        
        for (JLabel currLabel : label)
        {
        	currLabel.setFont(new Font("Arial", Font.BOLD, 15));
        }
        
        ArrayList<JScrollPane> scrollpane = new ArrayList<JScrollPane>();
        for (JTable currTable : table)
        	scrollpane.add(new JScrollPane(currTable));
        
        
        for (int j = 0; j < panel.size();j++)
        {
        	panel.get(j).setLayout(new BoxLayout(panel.get(j), BoxLayout.Y_AXIS));
        	scrollpane.get(j).setBorder(BorderFactory.createEmptyBorder(0,10,0,10));
        	panel.get(j).add(label.get(j));
        	panel.get(j).add(scrollpane.get(j));
        }
        
        JPanel outerPanel = new JPanel();
        outerPanel.setLayout(new GridLayout(0,1));
        for (JPanel currPanel : panel)
        {
        	outerPanel.add(currPanel);
        }
        
        JPanel buttons = createButtons();
        
        contentPane.add(outerPanel, BorderLayout.CENTER);
        contentPane.add(buttons, BorderLayout.WEST);
        
        return contentPane;
    }
    /**
     * Method parseObjects
     * 
     * This method takes a hashmap containing the vehicles, and converts it to
     * a 2d-array that is required to make a table. It only takes the types
     * specified by type in the 2d-array
     * 
     * @param dataObjects The hashmap containing the data to parse
     * @param type The type to create 2d array with
     * @return Object[][] The 2d-array for the table
     */
    private Object[][] parseObjects(HashMap<Integer, Vehicle> dataObjects, VehicleType type)
    {
    	HashMap<Integer, Vehicle> vehicle = new HashMap<Integer, Vehicle>();
    	Iterator<Entry<Integer, Vehicle>> i = dataObjects.entrySet().iterator();
    	while (i.hasNext())
    	{
        	Map.Entry<Integer, Vehicle> object = (Map.Entry<Integer, Vehicle>)i.next();
    		if (object.getValue().getType().getId() == type.getId())
    			vehicle.put(object.getValue().getId(), object.getValue());
    	}
    			
        Object[][] data = new Object[vehicle.size()][6];
        Iterator<Entry<Integer, Vehicle>> i2 = vehicle.entrySet().iterator();
        int j = 0;
        while (i2.hasNext())
        {
        	Map.Entry<Integer, Vehicle> object2 = (Map.Entry<Integer, Vehicle>)i2.next();
            data[j][0] = object2.getValue().getId();
            data[j][1] = object2.getValue().getMake();
            data[j][2] = object2.getValue().getModel();
            data[j][3] = object2.getValue().getYear();
            data[j][4] = generateIcon("update");
            data[j][5] = generateIcon("delete");
            j++;
        }
        return data;
    }
    /**
     * Method addEntry
     * 
     * This method adds an entry to the database, and if that was successfull adds it
     * to the table as well. If it is not successsfull it prints a message, and returns false
     * 
     * @param input The input fields from the add-form
     * @return boolean Whether or not the method was successful
     */
    @Override
    public boolean addEntry(Object[] input)
    {
    	Vehicle success = controller.createVehicle(((JTextField)input[0]).getText(), ((JTextField)input[1]).getText(), Integer.parseInt(((JTextField)input[2]).getText()), (VehicleType)((JComboBox)input[3]).getSelectedItem());
    	updateVehicles();
    	if (success != null)
    	{
    		HashMap<Integer, Vehicle> tempMap = new HashMap<Integer, Vehicle>();
    		tempMap.put(success.getId(), success);
    		Object[][] tempData = parseObjects(tempMap, success.getType());
    		addToTable(tempData[0], table.get(success.getType().getId()-1));
    		return true;
    	}
    	else
    	{
    		JOptionPane.showMessageDialog(canvas.getFrame(), "Vehicle already exists!", "Error", JOptionPane.ERROR_MESSAGE);
    		return false;
    	}
    	
    }
    /**
     * Method tableChanged
     * 
     * This method is called whenever the table has changed. When it has the method
     * changes the field via the method changeVehicle
     * 
     * @param e The event
     */
    public void tableChanged(TableModelEvent e)
    {
    	if (!noChange) //The application has not changed the data, user must have
    	{
	    	int row = e.getFirstRow();
	    	int column = e.getColumn();
	    	TableModel tablemodel = (TableModel)e.getSource();
	    	int id = (Integer)tablemodel.getValueAt(row, 0);
	    	JTable table = this.table.get(vehicles.get(id).getType().getId()-1);
	    	
	    	if (column != 5 && column != 6) 
	    		changeVehicle(row, column, table);
    	}
    }
    /**
     * Method makeChoices
     * 
     * This method makes an array from the vehicletypes hashmap. The array is required to create a
     * combobox with the vehicletypes
     * 
     * @return Object[] The array for the combobox
     */
    private Object[] makeChoices()
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
    /**
     * Method changeType
     * 
     * This method is called when the change type field in the table is clicked
     * It changes the type of a vehicle
     * 
     * @param row The row that is trying to be changed
     * @param prevType The id of the previous type
     * @param newType The id of the new type
     */
    private void changeType(int row, int prevType, int newType)
    {
    	Object[] data = new Object[columnNames.length];
    	for (int i = 0; i < columnNames.length; i++)
    	{
    		data[i] = table.get(prevType).getValueAt(row, i);
    	}
    	Vehicle oldVehicle = new Vehicle((Integer)data[0], (String)data[1], (String)data[2], (Integer)data[3], vehicletypes.get(prevType));
    	Vehicle newVehicle = new Vehicle((Integer)data[0], (String)data[1], (String)data[2], (Integer)data[3], vehicletypes.get(newType));
    	boolean success = controller.editVehicle(newVehicle, oldVehicle);
    	if (success)
    	{
    		//Move the entry to the table for the new type
    		removeFromTable(row, table.get(prevType));
    		addToTable(data, table.get(newType-1));
    	}
    	else 
    	{
    		JOptionPane.showMessageDialog(canvas.getFrame(), "Could not change type", "Error", JOptionPane.ERROR_MESSAGE);
    	}
    }
    /**
     * Method drawAddFrame
     * 
     * This method draws the frame used to add vehicles
     */
    public void drawAddFrame()
    {
    	addText = "Add vehicle";
    	JLabel[] labels = new JLabel[4];
    	labels[0] = new JLabel("Make:");
    	labels[1] = new JLabel("Model:");
    	labels[2] = new JLabel("Year");
    	labels[3] = new JLabel("Type");
    	JFrame frame = new JFrame(addText);
    	frame.setResizable(false);
    	JPanel pane = drawAddFrameHead();
    	
    	JPanel panel = new JPanel();
    	panel.setLayout(new GridLayout(0,2));
    	
    	Object[] input = new Object[labels.length];
    	input[0] = new JTextField();
    	input[1] = new JTextField();
    	input[2] = new JTextField();
    	input[3] = new JComboBox(makeChoices());
    	
   		panel.add(labels[0]);
    	panel.add((JTextField)input[0]);
    	panel.add(labels[1]);
    	panel.add((JTextField)input[1]);
    	panel.add(labels[2]);
    	panel.add((JTextField)input[2]);
    	panel.add(labels[3]);
    	panel.add((JComboBox)input[3]);
    	
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
     * Method removeVehicle
     * 
     * This method removes a vehicle from the database. On success it also removes it 
     * from the specific table. On fail it displays a messagebox
     * 
     * @param rowID The row to be removed
     * @param table The table to remove from
     */
    public void removeVehicle(int rowID, JTable table)
    {
    	boolean success = controller.deleteVehicle(vehicles.get(table.getValueAt(rowID, 0)));
    	if (success)
    		removeFromTable(rowID, table);
    	else
    		JOptionPane.showMessageDialog(canvas.getFrame(), "Could not remove vehicle - SQLException", "Error", JOptionPane.ERROR_MESSAGE);
    }
    /**
     * Method changeVehicle
     * 
     * This method is called from the tableChanged method, and changes a vehicle. On fail
     * it shows a messagebox, and reverts the field to the original content
     * 
     * @param rowID The row that has changed
     * @param columnID The column that has changed
     * @param table The table that has changed
     */
    public void changeVehicle(int rowID, int columnID, JTable table)
    {
    	Vehicle oldVehicle = vehicles.get(table.getValueAt(rowID, 0));
    	int arg0 = oldVehicle.getId();
    	String arg1 = oldVehicle.getMake();
    	String arg2 = oldVehicle.getModel();
    	int arg3 = oldVehicle.getYear();
    	VehicleType arg4 = oldVehicle.getType();
    	switch (columnID)
    	{
    	case 1:
    		arg1 = (String)table.getValueAt(rowID, 1);
    	break;
    	case 2:
    		arg2 = (String)table.getValueAt(rowID, 2);
    	break;
    	case 3:
    		arg3 = (Integer)table.getValueAt(rowID, 3);
    	break;
    	default:
    	break;
    	}
    	Vehicle newVehicle = new Vehicle(arg0, arg1, arg2, arg3, arg4);
    	boolean success = controller.editVehicle(newVehicle, oldVehicle);
    	if (!success)
    	{
    		JOptionPane.showMessageDialog(canvas.getFrame(), "Could not change customer - SQLException", "Error", JOptionPane.ERROR_MESSAGE);
    		noChange = true;
    		switch (columnID)
        	{
        	case 1:
        		table.setValueAt(oldVehicle.getMake(), rowID, 1);
        	break;
        	case 2:
        		table.setValueAt(oldVehicle.getModel(), rowID, 2);
        	break;
        	case 3:
        		table.setValueAt(oldVehicle.getYear(), rowID, 3);
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
     * This method is called when the user clicks a table. It either tries to change type
     * or to remove vehicles depending on what the user has clicked
     * 
     * @param e The event
     * @param table The table that it has been fired from
     */
    public void mouseClicked(MouseEvent e, JTable table) {
    	int column = table.columnAtPoint(e.getPoint());
    	int row = table.rowAtPoint(e.getPoint());
    	String questionRemove = "Are you sure you want to remove this vehicle?";
    	String titleRemove = "Removing vehicle";
    	int id = (Integer)table.getModel().getValueAt(row, 0);
		switch (column)
		{
		case 4:
			Object[] choices = makeChoices();
        	VehicleType chosentype = (VehicleType)JOptionPane.showInputDialog(canvas.getFrame(), "Choose new type:", "Change type", JOptionPane.QUESTION_MESSAGE, null, choices, choices[vehicles.get(id).getType().getId()-1]);
        	try {
        		changeType(row, vehicles.get(id).getType().getId()-1, chosentype.getId());
        	} catch (NullPointerException e1) {
        		
        	}
		break;
		case 5:
			int response = JOptionPane.showConfirmDialog(canvas.getFrame(), questionRemove, titleRemove, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			if (response == 0)
    			removeVehicle(row, table);
		break;
		}
    	
	}
    /**
     * Required when implementing MouseListener
     * Not used
     */
    public void mouseClicked(MouseEvent e) {
    	
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
