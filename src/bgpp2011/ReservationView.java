package bgpp2011;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

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
    
    public ReservationView(Canvas canvas)
    {
    	super(canvas);
        topText = "Reservations";
        
    }
    public void updateReservations()
    {
    	controller = new Controller();
    	reservations = controller.getReservations();
    	vehicletypes = controller.getTypes();
    	customers = controller.getCustomers();
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
        String[] columnNames = {"ID","Customer","Vehicle","Start date","End date", "Change vehicletype", "Remove"};
        Object[][] data = parseObjects(reservations);
        int[] columnSizes = {20,1000,1000,200,200,100, 100};
       
        table = createTable(columnNames, data, columnSizes);
        JScrollPane scrollPane = new JScrollPane(table);
        
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));
        contentPane.add(scrollPane, BorderLayout.CENTER);
        
        JPanel boxLayout = createButtons();
        contentPane.add(boxLayout, BorderLayout.WEST);
        
        return contentPane;
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
            data[j][5] = (Boolean)false;
            data[j][6] = (Boolean)false;
            j++;
        }
        return data;
    }
    public boolean addEntry(Object[] input)
    {
    	Reservation success = controller.createReservation((Customer)((JComboBox)input[0]).getSelectedItem(),
    														(VehicleType)((JComboBox)input[1]).getSelectedItem(),
    														new Date(((JTextField)input[2]).getText()),
    														new Date(((JTextField)input[3]).getText()));
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
}
