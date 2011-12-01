package bgpp2011;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

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
        String[] columnNames = {"ID", "Name", "Address", "Phone Number"};
        Object[][] data = parseObjects(customers);
        int[] columnSizes = {10,1000,1000,200};
        TableModel model = new TableModel(columnNames, data);
        
        JTable table = createTable(columnSizes, model);
        JScrollPane scrollPane = new JScrollPane(table);
        
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));
        contentPane.add(scrollPane, BorderLayout.CENTER);
        
        JPanel boxLayout = new JPanel();
        boxLayout.setLayout(new BoxLayout(boxLayout, BoxLayout.Y_AXIS));
        
        ArrayList<JButton> buttons = new ArrayList<JButton>();
        ArrayList<View> views = new ArrayList<View>();
        
        buttons.add(new JButton("< Back"));
        buttons.add(new JButton("Add Customer"));
        
        views.add(new FrontPageView(canvas));
        views.add(new FrontPageView(canvas));
        
        Iterator<JButton> i1 = buttons.iterator();
        Iterator<View> i2 = views.iterator();
        while (i1.hasNext() && i2.hasNext())
        {        
            final JButton button = i1.next();
            final View view = i2.next();
            
            button.setMaximumSize(new Dimension(130,30));
            boxLayout.add(button);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) { 
                    canvas.changeView(view);
                }
            });
        }
        contentPane.add(boxLayout, BorderLayout.WEST);
        
        return contentPane;
    }
    public Object[][] parseObjects(ArrayList<Customer> dataObjects)
    {
        Object[][] data = new Object[dataObjects.size()][4];
        for (int j = 0; j < dataObjects.size(); j++)
        {
            data[j][0] = dataObjects.get(j).getId();
            data[j][1] = dataObjects.get(j).getName();
            data[j][2] = dataObjects.get(j).getNumber();
            data[j][3] = dataObjects.get(j).getAddress();
        }
        return data;
    }
    public void addCustomer(JTable table)
    {
        
    }
}
