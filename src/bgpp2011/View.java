package bgpp2011;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 *
 * @author Michael Søby Andersen
 * @mail msoa@itu.dk
 * @date 26/11-2011
 * @time 10:23
 * 
 */
public abstract class View implements TableModelListener
{
	private static final long serialVersionUID = -2198358766333127667L;
	//Instance variables
    public JPanel contentPane = new JPanel();
    public String topText;
    public JLabel welcomeText;
    public JLabel copyrightText;
    public String addText;
    public Canvas canvas;
    private JTable table;
    public Controller controller;
    public boolean noChange;
    
    //Constructor
    public View(Canvas canvas)
    {
        this.canvas = canvas;
    }
    
    //Draw method
    public JPanel draw()
    {
        contentPane.setLayout(new BorderLayout());
        
        welcomeText = new JLabel(topText);
        welcomeText.setFont(new Font("Arial", Font.PLAIN, 24));
        welcomeText.setHorizontalAlignment(JLabel.CENTER);
        welcomeText.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        
        copyrightText = new JLabel("Copyright Toke Jensen, Magnus Stahl, Michael Søby Andersen, 2011");
        copyrightText.setFont(new Font("Arial", Font.ITALIC, 10));
        copyrightText.setHorizontalAlignment(JLabel.CENTER);
        
        contentPane.add(welcomeText, BorderLayout.NORTH);
        contentPane.add(copyrightText, BorderLayout.SOUTH);
        
        return contentPane;
    }
    public JTable createTable(String[] columnNames, Object[][] data, HashMap<Integer, Boolean> cellEditable, int[] columnSizes)
    {
        JTable table = new JTable(data, columnNames);
        table.setModel(new TableModel(data,columnNames, cellEditable));
        table.getModel().addTableModelListener(this);
        for (int i = 0; i < columnSizes.length; i++)
        {
            table.getColumnModel().getColumn(i).setPreferredWidth(columnSizes[i]);
        }
        this.table = table;
        return table;
    }
    public void addToTable(JTable table)
    {
        addToTable(null, table);
    }
    public void addToTable(Object[] data, JTable table)
    {
        TableModel model = (TableModel)table.getModel();
        model.addRow(data);
    }
    public void removeFromTable(int rowID, JTable table)
    {
    	TableModel model = (TableModel)table.getModel();
    	model.removeRow(rowID);
    }
    public void tableChanged(TableModelEvent e)
    {

    }
    public JPanel createButtons()
    {
    	JPanel boxLayout = new JPanel();
        boxLayout.setLayout(new BoxLayout(boxLayout, BoxLayout.Y_AXIS));
        
        ArrayList<JButton> buttons = new ArrayList<JButton>();
        ArrayList<Object> views = new ArrayList<Object>();
        
        buttons.add(new JButton("< Back"));
        buttons.add(new JButton("Add"));
        
        views.add(new FrontPageView(canvas));
        views.add("drawAddFrame");
        
        Iterator<JButton> i1 = buttons.iterator();
        Iterator<Object> i2 = views.iterator();
        while (i1.hasNext() && i2.hasNext())
        {        
            final JButton button = i1.next();
            final Object object = i2.next();
            final View view = this;
            
            button.setMaximumSize(new Dimension(130,30));
            boxLayout.add(button);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	try
                	{
                		canvas.changeView((View)object);
                	}
                	catch (ClassCastException e1)
                	{
                		try
                		{
                			Method m = view.getClass().getMethod(object.toString());
                			m.invoke(view);
                		}
                		catch (NoSuchMethodException e2){}
                		catch (InvocationTargetException e2){}
                		catch (IllegalAccessException e2) {}
                		
                	}
                    
                }
            });
        }
        return boxLayout;
    }
    public void drawAddFrame()
    {
    	
    }
    public JPanel drawAddFrameHead()
    {
    	JPanel contentPane = new JPanel();
    	contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
    	JLabel header = new JLabel(addText);
    	header.setFont(new Font("Arial", Font.PLAIN, 24));
    	header.setAlignmentX(0);
    	contentPane.add(header);
    	
    	return contentPane;
    }
    public JPanel drawAddFrameFoot(final JFrame frame, final Object[] input)
    {
    	
    	JPanel buttonscont = new JPanel();
    	buttonscont.setLayout(new FlowLayout(FlowLayout.LEFT));
    	
    	JButton[] buttons = new JButton[2];
    	buttons[0] = new JButton("Add");
    	buttons[0].addActionListener(new ActionListener() {
            	@Override
            	public void actionPerformed(ActionEvent e) {
            		boolean success = addEntry(input);
            		if (success)
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
    public boolean addEntry(Object[] input)
    {
    	return false;
    }
    public void paint(Graphics g)
    {
    	g.drawRect(0, 0, 100, 100);
    }
}
