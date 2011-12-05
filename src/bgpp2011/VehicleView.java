package bgpp2011;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

/**
 *
 * @author Michael
 */
public class VehicleView extends View {
	
	private ArrayList<Vehicle> vehicles;
	private ArrayList<VehicleType> vehicletypes;
    private ArrayList<JTable> table;
    
    public VehicleView(Canvas canvas)
    {
        super(canvas);
        vehicles = new ArrayList<Vehicle>();
        vehicletypes = new ArrayList<VehicleType>();
        topText = "Vehicles";
        vehicletypes.add(new VehicleType(1, "2-door", 100.0));
        vehicletypes.add(new VehicleType(2, "4-door", 200.0));
        vehicletypes.add(new VehicleType(3, "mc", 50.0));
        
        vehicles.add(new Vehicle(1, "Toyota", "Corolla", 1992, vehicletypes.get(0)));
        vehicles.add(new Vehicle(2, "Nissan", "Almera", 2000, vehicletypes.get(0)));
        vehicles.add(new Vehicle(1, "Honda", "Accord", 2007, vehicletypes.get(1)));
        vehicles.add(new Vehicle(1, "Kawasaki", "Ninja", 2008, vehicletypes.get(2)));
        vehicles.add(new Vehicle(1, "BMW", "525i", 2000, vehicletypes.get(1)));
    }
    @Override
    public JPanel draw()
    {
        super.draw();
        String[] columnNames = {"ID", "Make", "Model", "Year", "Change type", "Remove"};
        int[] columnSizes = {10,1000,1000,200,100,100};
        ArrayList<JLabel> label = new ArrayList<JLabel>();
        table = new ArrayList<JTable>();
        ArrayList<JPanel> panel = new ArrayList<JPanel>();
        for (VehicleType vehicletype : vehicletypes)
        {
        	label.add(new JLabel(vehicletype.getName()+":"));
        	Object[][] data = parseObjects(vehicles, vehicletype);
        	table.add(createTable(columnNames, data, columnSizes));
        	panel.add(new JPanel());
        }
        
        for (JLabel currLabel : label)
        {
        	currLabel.setFont(new Font("Arial", Font.BOLD, 15));
        	currLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        }
        
        ArrayList<JScrollPane> scrollpane = new ArrayList<JScrollPane>();
        for (JTable currTable : table)
        	scrollpane.add(new JScrollPane(currTable));
        
        
        for (int i = 0; i < panel.size();i++)
        {
        	panel.get(i).setLayout(new BoxLayout(panel.get(i), BoxLayout.Y_AXIS));
        	scrollpane.get(i).setBorder(BorderFactory.createEmptyBorder(0,10,0,10));
        	panel.get(i).add(label.get(i));
        	panel.get(i).add(scrollpane.get(i));
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
    public Object[][] parseObjects(ArrayList<Vehicle> dataObjects, VehicleType type)
    {
    	ArrayList<Vehicle> vehicle = new ArrayList<Vehicle>();
    	for (int i = 0; i < dataObjects.size(); i++)
    		if (dataObjects.get(i).getType().equals(type))
    			vehicle.add(dataObjects.get(i));
    			
        Object[][] data = new Object[vehicle.size()][6];
        for (int j = 0; j < vehicle.size(); j++)
        {
            data[j][0] = vehicle.get(j).getId();
            data[j][1] = vehicle.get(j).getMake();
            data[j][2] = vehicle.get(j).getModel();
            data[j][3] = vehicle.get(j).getYear();
            data[j][4] = (Boolean)false;
            data[j][5] = (Boolean)false;
        }
        return data;
    }
}
