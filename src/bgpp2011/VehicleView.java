package bgpp2011;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Map.Entry;

import javax.swing.*;
import javax.swing.event.TableModelEvent;

/**
 *
 * @author Michael
 */
public class VehicleView extends View {
	
	private HashMap<Integer, Vehicle> vehicles;
	private HashMap<Integer, VehicleType> vehicletypes;
    private ArrayList<JTable> table;
    private String[] columnNames;
    
    public VehicleView(Canvas canvas)
    {
        super(canvas);
        topText = "Vehicles";
        
    }
    public void updateVehicles()
    {
    	controller = new Controller();
    	vehicletypes = controller.getTypes();
        vehicles = controller.getVehicles();
    }
    @Override
    public JPanel draw()
    {
    	updateVehicles();
        super.draw();
        columnNames = new String[6];
        columnNames[0] = "ID";
        columnNames[1] = "Make";
        columnNames[2] = "Model";
        columnNames[3] = "Year";
        columnNames[4] = "Change Type";
        columnNames[5] = "Remove";
        int[] columnSizes = {20,1000,1000,200,100,100};
        ArrayList<JLabel> label = new ArrayList<JLabel>();
        table = new ArrayList<JTable>();
        ArrayList<JPanel> panel = new ArrayList<JPanel>();
        Iterator<Entry<Integer, VehicleType>> i = vehicletypes.entrySet().iterator();
        while (i.hasNext())
        {
        	Map.Entry<Integer, VehicleType> entry = (Map.Entry<Integer, VehicleType>)i.next();
        	label.add(new JLabel(entry.getValue().getName()+":"));
        	Object[][] data = parseObjects(vehicles, entry.getValue());
        	table.add(createTable(columnNames, data, columnSizes));
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
    public Object[][] parseObjects(HashMap<Integer, Vehicle> dataObjects, VehicleType type)
    {
    	HashMap<Integer, Vehicle> vehicle = new HashMap<Integer, Vehicle>();
    	Iterator<Entry<Integer, Vehicle>> i = vehicles.entrySet().iterator();
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
            data[j][4] = (Boolean)false;
            data[j][5] = (Boolean)false;
            j++;
        }
        return data;
    }
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
    
    public void tableChanged(TableModelEvent e)
    {
    	String questionRemove = "Are you sure you want to remove this vehicle?";
    	String titleRemove = "Removing vehicle";
    	int row = e.getFirstRow();
    	int column = e.getColumn();
    	TableModel tablemodel = (TableModel)e.getSource();
    	int id = (Integer)tablemodel.getValueAt(row, 0);
    	JTable table = this.table.get(vehicles.get(id).getType().getId()-1);
    	
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
    			removeFromTable(row, table);
    	break;
    	default:
    		
    	break;
    	}
    }
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
    		removeFromTable(row, table.get(prevType));
    		addToTable(data, table.get(newType-1));
    	}
    	else 
    	{
    		JOptionPane.showMessageDialog(canvas.getFrame(), "Could not change type", "Error", JOptionPane.ERROR_MESSAGE);
    	}
    }
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
    	
    	Object[] types = new Object[vehicletypes.size()];
    	Iterator<Entry<Integer, VehicleType>> i = vehicletypes.entrySet().iterator();
    	int j = 0;
    	while (i.hasNext())
    	{
    		Map.Entry<Integer, VehicleType> type = (Map.Entry<Integer, VehicleType>)i.next();
    		types[j] = type.getValue();
    		j++;
    	}
    	input[3] = new JComboBox(types);
    	
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
}
