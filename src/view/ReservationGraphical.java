package view;
import java.awt.*;
import javax.swing.*;

import model.VehicleType;

import bgpp2011.Controller;

import java.sql.Date;
import java.util.*;
import java.util.Map.Entry;

/**
* 
* This class is used to make a JPanel with the graphical view inside.
* It therefore extends JPanel, and acts like a JPanel
* 
* @author Michael Søby Andersen
* @mail msoa@itu.dk
* 
*/

@SuppressWarnings("serial")
public class ReservationGraphical extends JPanel{
	
	/**
	 * Instance variables
	 * 
	 * startdate and enddate are used to set the start and end of graphical view
	 * controller is used to access hashmaps
	 * types is used to store the vehicletypes
	 * days is used to set the number of days to display
	 * date is used to model the different dates in the class
	 */
	private Date startdate, enddate;
	private Controller controller;
	private HashMap<Integer, VehicleType> types;
	private int days;
	private GregorianCalendar date = new GregorianCalendar();
	/**
	 * Constructor for ReservationGraphical
	 * 
	 * The constructor set start and end date, and set number of days. 
	 * It also initiates the controller and gets types
	 * 
	 * @param controller The controller
	 * @param startdate Date to start graphical view from
	 * @param days The number of days to view
	 */
	public ReservationGraphical(Controller controller, Date startdate, int days)
	{
		setDays(days);
		setStartDate(startdate);
		this.controller = controller;
		types = this.controller.getModel().getTypes();
	}
	/**
	 * Method paintComponent
	 * 
	 * This method is called when the JPanel component is painted.
	 * It paints everything
	 * 
	 * @param g The graphics object
	 */
	public void paintComponent(Graphics g)
	{
		this.removeAll();
		super.paintComponent(g);
		this.setDoubleBuffered(false);
		paintAll(startdate, enddate, g);
	}
	/**
	 * Method paintAll
	 * 
	 * This method paints everything, and is called by paintComponent
	 * 
	 * @param startdate Startdate for the graphical view
	 * @param enddate Enddate for the graphical view
	 * @param g The graphics object
	 */
	public void paintAll(Date startdate, Date enddate, Graphics g)
	{
		Date currentdate = new Date(startdate.getTime());
		
		for (int i = 0; i < days; i++)
		{
			date.setTimeInMillis(currentdate.getTime());
			paintTypes(g, i, currentdate);
			date.add(GregorianCalendar.DATE, 1);
			currentdate.setTime(date.getTimeInMillis());
		}
		
		paintTypes(g);
	}
	/**
	 * Method paintTypes
	 * 
	 * This method paints the rectangles for types according to how
	 * many that are available
	 * 
	 * @param g The graphics object
	 * @param offset The offset
	 * @param currentdate The day that is currently being printed
	 */
	public void paintTypes(Graphics g, int offset, Date currentdate)
	{
		Iterator<Entry<Integer, VehicleType>> i = types.entrySet().iterator();
		int width = 750/days;
		int heights = 680/types.size();
		int height = 2*((heights/2));
		int x = 100+(width/2)+(offset*width)+10;
		int y = 20+(heights/2)-(height/2);
		date.setTimeInMillis(currentdate.getTime());
		while (i.hasNext())
		{
			Map.Entry<Integer, VehicleType> object = (Map.Entry<Integer, VehicleType>)i.next();
			g.setColor(getColor(currentdate,object.getValue()));
			g.fillRect(x, y, width, height);
			
			y += (680/types.size());
		}
		g.setColor(Color.BLACK);
		g.drawString(""+date.get(GregorianCalendar.DATE), x-10+(width/2), 15);
		g.setColor(Color.LIGHT_GRAY);
		g.drawLine(x, 20, x, 700);
	}
	/**
	 * Method paintTypes
	 * 
	 * This method paints the label for the types
	 * 
	 * @param g The graphics object
	 */
	public void paintTypes(Graphics g)
	{
		Iterator<Entry<Integer, VehicleType>> i = types.entrySet().iterator();
		int offset = 20+(680/types.size()/2);
		g.setColor(Color.LIGHT_GRAY);
		g.drawLine(25, 20, 885, 20);
		while (i.hasNext())
		{
			g.setColor(Color.LIGHT_GRAY);
			g.drawLine(25, offset+(680/types.size()/2), 885, offset+(680/types.size()/2));
			Map.Entry<Integer, VehicleType> object = (Map.Entry<Integer, VehicleType>)i.next();
			g.setColor(Color.BLACK);
			g.drawString(object.getValue().getName(), 25, offset);
			offset += 680/types.size();
		}
	}
	/**
	 * Method getColor
	 * 
	 * This method returns the color for the rectangle according to
	 * how many that are available  at the date
	 * 
	 * @param date The date to check
	 * @param type The type to check
	 * @return Color The color (red, yellow, green)
	 */
	public Color getColor(Date date, VehicleType type)
	{
		int avail = controller.typeCounting(type, date, date);
		switch (avail)
		{
		case 0:
			return Color.RED;
		case 1:
			return Color.YELLOW;
		default:
			return Color.GREEN;
		}
	}
	/**
	 * Method setDays
	 * 
	 * This method sets the number of days that the graphical view should display
	 * 
	 * @param days The number of days
	 */
	public void setDays(int days)
	{
		this.days = days;
	}
	/**
	 * Method setStartDate
	 * 
	 * This method sets the startdate that the graphical view
	 * should start from
	 * 
	 * @param startdate The startdate
	 */
	public void setStartDate(Date startdate)
	{
		this.startdate = startdate;
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(startdate.getTime());
		calendar.add(GregorianCalendar.DATE, days);
		this.enddate = new Date(calendar.getTimeInMillis());
	}
}
