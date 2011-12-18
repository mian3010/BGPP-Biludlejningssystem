package view;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import bgpp2011.Canvas;
import bgpp2011.Controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 
 * This class is the abstract superclass of all the views. It implements TableModelListener
 * and MouseListener. The reason for this is that TableModelListener is used to add an action
 * when the table is changed. The MouseListener is used to add an action to when the table
 * is clicked on.
 * 
 * @author Michael Søby Andersen, msoa@itu.dk
 * 
 */
public abstract class View implements TableModelListener, MouseListener
{
	/**
	 * Instance variables.
	 * topText, welcomText, copyrightText and addText are all used so that the subclasses can
	 * set the different texts on the view.
	 * canvas is used whenever the application needs to change view
	 * controller is used to get all the data in hashmaps and to model the data
	 * noChange is used to stop the program from checking the change in the table
	 * when the program itself has changed it.
	 */
    protected String topText;
    protected JLabel welcomeText;
    protected JLabel copyrightText;
    protected String addText;
    protected Canvas canvas;
    protected Controller controller;
    protected boolean noChange;
    
    /**
     * Constructor for class view
     * The only thing the constructor does is to initiate canvas variable
     * 
     * @param canvas The canvas
     */
    public View(Canvas canvas)
    {
        this.canvas = canvas;
    }   
    /**
     * Method draw
     * This method draws the elements on the page that are the same for all the views
     * welcomeText and copyrightText
     */
    public JPanel draw()
    {
        JPanel contentPane = new JPanel();
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
    /**
     * Method createTable
     * 
     * This method creates a table, adds mouselistener and tablemodellistener and sets
     * the custom tablemodel
     * 
     * @param columnNames The names of the columns in the table
     * @param data The data that the table should contain
     * @param cellEditable A hashmap containing true/false for the column ids to set them editable
     * @param columnSizes An array containing the sizes of the columns
     * @return JTable the created table
     */
    public JTable createTable(String[] columnNames, Object[][] data, HashMap<Integer, Boolean> cellEditable, int[] columnSizes)
    {
        JTable table = new JTable(data, columnNames);
        table.setModel(new TableModel(data,columnNames, cellEditable));
        table.addMouseListener(this);
        table.getModel().addTableModelListener(this);
        for (int i = 0; i < columnSizes.length; i++)
        {
            table.getColumnModel().getColumn(i).setPreferredWidth(columnSizes[i]);
        }
        return table;
    }
    /**
     * Method addToTable
     * 
     * This method calls the addToTable method with arguments for data and table
     * 
     * @param table The table to add to
     */
    public void addToTable(JTable table)
    {
        addToTable(null, table);
    }
    /**
     * Method addToTable
     * 
     * This method adds some data to the table. It gets the tablemodel and uses the method
     * in that to add row with the data.
     * 
     * @param data The data to add to the table
     * @param table The table to add data to
     */
    public void addToTable(Object[] data, JTable table)
    {
        TableModel model = (TableModel)table.getModel();
        model.addRow(data);
    }
    /**
     * Method removeFromTable
     * 
     * This method removes a row from the table. It uses the tablemodels method removeRow
     * 
     * @param rowID The id of the row to remove
     * @param table The table to remove from
     */
    public void removeFromTable(int rowID, JTable table)
    {
    	TableModel model = (TableModel)table.getModel();
    	model.removeRow(rowID);
    }
    /**
     * Method tableChanged
     * 
     * This is a super-method for when a table has changed. Overridden in subclasses
     * 
     * @param e The event
     */
    public void tableChanged(TableModelEvent e)
    {

    }
    /**
     * Method createButtons
     * 
     * This method creates the buttons that are the same for all the views. It puts them in a 
     * JPanel and return that. It also adds actionlisteners to them
     * 
     * @return JPanel The panel containing the buttons
     */
    public JPanel createButtons()
    {
    	JPanel boxLayout = new JPanel();
        boxLayout.setLayout(new BoxLayout(boxLayout, BoxLayout.Y_AXIS));
        
        ArrayList<JButton> buttons = new ArrayList<JButton>();
        ArrayList<Object> views = new ArrayList<Object>();
        
        buttons.add(new JButton("Frontpage"));
        buttons.add(new JButton("Add"));
        
        views.add(canvas.getView("frontpage"));
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
                		//Try to change view. If object is not a view ClassCastException is cast
                    	//and it must be a method
                		canvas.changeView((View)object);
                	}
                	catch (ClassCastException e1)
                	{
                		//The object is the name of a method, and reflection is used
                		//to call the method
                		try
                		{
                			Method m = view.getClass().getMethod(object.toString());
                			m.invoke(view);
                		}
                		//Required catches for reflection
                		catch (NoSuchMethodException e2){}
                		catch (InvocationTargetException e2){}
                		catch (IllegalAccessException e2) {}
                	}
                    
                }
            });
        }
        return boxLayout;
    }
    /**
     * Method drawAddFrame
     * 
     * Super method overridden in all the subclasses
     */
    public void drawAddFrame()
    {
    	
    }
    /**
     * Method drawAddFrameHead
     * 
     * This method creates a panel containing the head of the frame for adding content
     * 
     * @return JPanel The panel with the head of add frame
     */
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
    /**
     * Method drawAddFrameFoot
     * 
     * This method draws the foot for the frame to add content. It draws to buttons. One
     * that adds content using addEntry, and one that closes window. 
     * 
     * @param frame The frame containing head and body
     * @param input The input fields from body
     * @return JPanel the panel with the foot in
     */
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
    /**
     * Method addEntry
     * 
     * Overridden in all the subclasses
     * 
     * @param input An array with the inputfields
     * @return boolean True if successfull, False if not
     */
    public boolean addEntry(Object[] input)
    {
    	return false;
    }
    /**
     * Method generateIcon
     * 
     * This method returns an imageicon to put in the table where you can either update or 
     * delete something
     * 
     * @param s The string selecting either delete or update icon
     * @return ImageIcon The imageicon to put in the table
     */
    public ImageIcon generateIcon(String s)
    {
    	if(s.equals("delete"))
    		return new ImageIcon(getClass().getResource("delete.gif"));		
		else if(s.equals("update"))
			return new ImageIcon(getClass().getResource("update.png"));
    	return new ImageIcon();
    
    }
    /**
     * Required when implementing MouseListener
     * Overridden in subclasses
     */
    @Override
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
