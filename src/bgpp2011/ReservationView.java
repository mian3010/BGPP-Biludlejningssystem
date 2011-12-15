package bgpp2011;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.TableModelEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

/**
 *
 * @author Michael
 */
public class ReservationView extends View {
	
	private HashMap<Integer, Reservation> reservations;
	private HashMap<Integer, VehicleType> vehicletypes;
	private HashMap<Integer, Customer> customers;
    private JTable table;
    private int id;
    private boolean drawGraphical = false;
    private ReservationGraphical graphical; 
    
    public ReservationView(Canvas canvas)
    {
    	super(canvas);
        topText = "Reservations";
        
    }
    public void updateReservations()
    {
    	controller = new Controller();
    	reservations = controller.getModel().getReservations();
    	vehicletypes = controller.getModel().getTypes();
    	customers = controller.getModel().getCustomers();
    }
    public ReservationView(Canvas canvas, int customerID)
    {
        this(canvas);
        this.id = customerID;
    }
    @Override
    public JPanel draw()
    {
        updateReservations();
        super.draw();
        if (!drawGraphical)
        {
	        String[] columnNames = {"ID","Customer","Vehicle","Start date","End date", "Change vehicletype", "Remove"};
	        Object[][] data = parseObjects(reservations);
	        int[] columnSizes = {20,1000,1000,200,200,100, 100};
	        HashMap<Integer, Boolean> cellEditable = new HashMap<Integer, Boolean>();
	        cellEditable.put(0, false);
	        cellEditable.put(1, false);
	        cellEditable.put(2, false);
	        cellEditable.put(3, true);
	        cellEditable.put(4, true);
	        cellEditable.put(5, true);
	        cellEditable.put(6, true);
	       
	        table = createTable(columnNames, data, cellEditable, columnSizes);
	        JScrollPane scrollPane = new JScrollPane(table);
	        
	        scrollPane.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));
	        contentPane.add(scrollPane, BorderLayout.CENTER);
	        
	        JPanel boxLayout = createButtons();
	        contentPane.add(boxLayout, BorderLayout.WEST);
        }
        else
        {
        	graphical = new ReservationGraphical(controller, new Date(System.currentTimeMillis()), 15);
        	contentPane.add(graphical, BorderLayout.CENTER);
        	JPanel boxLayout = createButtons();
	        contentPane.add(boxLayout, BorderLayout.WEST);
        }
        return contentPane;
    }
    public void graphical(boolean graphical)
    {
    	drawGraphical = graphical;
    	canvas.changeView(this);
    }
    public void paint(Graphics g)
    {

    }
    public Object[][] parseObjects(HashMap<Integer, Reservation> dataObjects)
    {
        Object[][] data = new Object[dataObjects.size()][7];
        Iterator<Entry<Integer, Reservation>> i = dataObjects.entrySet().iterator();
        int j = 0;
        while (i.hasNext())
        {
        	Map.Entry<Integer, Reservation> object = (Map.Entry<Integer, Reservation>)i.next();
            data[j][0] = object.getValue().getId();
            data[j][1] = object.getValue().getCustomer().toString();
            data[j][2] = object.getValue().getVehicle().toString();
            data[j][3] = object.getValue().getStartdate();
            data[j][4] = object.getValue().getEnddate();
            data[j][5] = generateIcon("update");
            data[j][6] = generateIcon("delete");
            j++;
        }
        return data;
    }
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
    		Object[][] tempData = parseObjects(tempMap);
    		addToTable(tempData[0], table);
    		return true;
    	}
    	else
    	{
    		JOptionPane.showMessageDialog(canvas.getFrame(), "Could not create reservation - Maybe it already exists", "Error", JOptionPane.ERROR_MESSAGE);
    		return false;
    	}
    }
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
    public void tableChanged(TableModelEvent e)
    {
    	String questionRemove = "Are you sure you want to remove this reservation?";
    	String titleRemove = "Removing reservation";
    	int row = e.getFirstRow();
    	int column = e.getColumn();
    	
    	switch (column)
    	{
    	case 5:
    		changeCustomer();
    	break;
    	case 6:
    		int response = JOptionPane.showConfirmDialog(canvas.getFrame(), questionRemove, titleRemove, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			if (response == 0)
    			removeReservation(row, table);
    	break;
    	default:
    		changeReservation(row, column, table);
    	break;
    	}
    }
    public void changeCustomer()
    {
    	
    }
    public void removeReservation(int rowID, JTable table)
    {
    	boolean success = controller.deleteReservation(reservations.get(table.getValueAt(rowID, 0)));
    	if (success)
    		removeFromTable(rowID, table);
    	else
    		JOptionPane.showMessageDialog(canvas.getFrame(), "Could not remove reservation - SQLException", "Error", JOptionPane.ERROR_MESSAGE);
    }
    public void changeReservation(int rowID, int columnID, JTable table)
    {
    	Reservation oldReservation = reservations.get(table.getValueAt(rowID, 0));
    	int arg0 = oldReservation.getId();
    	Customer arg1 = oldReservation.getCustomer();
    	Vehicle arg2 = oldReservation.getVehicle();
    	Date arg3 = Date.valueOf(oldReservation.getStartdate());
    	Date arg4 = Date.valueOf(oldReservation.getEnddate());
    	switch (columnID)
    	{
    	case 3:
    		arg3 = Date.valueOf((String)table.getValueAt(rowID, 3));
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
    public JPanel createButtons()
    {
    	JPanel boxLayout = super.createButtons();
    	if (!drawGraphical)
    	{
	    	JButton button = new JButton("Graphical");
	    	button.setMaximumSize(new Dimension(130,30));
	        boxLayout.add(button);
	        final ReservationView view = this;
	        button.addActionListener(new ActionListener() {
	        	@Override
	        	public void actionPerformed(ActionEvent e) {
	        		view.graphical(true);
	            }
	        });
	    	return boxLayout;
    	}
    	else
    	{
	    	JButton button1 = new JButton("Textual");
	    	button1.setMaximumSize(new Dimension(200,30));
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
	        
	        final ReservationView view = this;
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
	    	return boxLayout;
    	}
    }
}
