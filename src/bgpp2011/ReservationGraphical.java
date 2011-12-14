package bgpp2011;
import java.awt.*;
import javax.swing.*;
import java.sql.Date;
import java.util.*;
import java.util.Map.Entry;


public class ReservationGraphical extends JPanel{
	private Date startdate, enddate;
	private Controller controller;
	private HashMap<Integer, VehicleType> types;
	private int days;
	private GregorianCalendar date = new GregorianCalendar();
	
	public ReservationGraphical(Controller controller)
	{
		startdate = new Date(System.currentTimeMillis());
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.add(GregorianCalendar.DATE, 15);
		enddate = new Date(calendar.getTimeInMillis());
		this.controller = controller;
		types = this.controller.getModel().getTypes();
	}
	public void paintComponent(Graphics g)
	{
		this.removeAll();
		super.paintComponent(g);
		this.setDoubleBuffered(false);
	//	GregorianCalendar cal = new GregorianCalendar();
	//	cal.setTimeInMillis(enddate.getTime()-startdate.getTime());
	//	days = cal.get(GregorianCalendar.DATE);
		days = 15;
		paintAll(startdate, enddate, g);
	}
	public void paintAll(Date startdate, Date enddate, Graphics g)
	{
		Date currentdate = startdate;
		
		for (int i = 0; i < days; i++)
		{
			date.setTimeInMillis(currentdate.getTime());
			paintTypes(g, i, currentdate);
			date.add(GregorianCalendar.DATE, 1);
			currentdate.setTime(date.getTimeInMillis());
			System.out.println(i);
		}
		
		paintTypes(g);
	}
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
			g.setColor(Color.BLUE);
			g.fillRect(x, y, width, height);
			
			g.setColor(Color.BLACK);
			//g.drawRect(x, y, width, height);
			y += (680/types.size());
		}
		g.setColor(Color.BLACK);
		g.drawString(""+date.get(GregorianCalendar.DATE), x-10+(width/2), 15);
		g.setColor(Color.LIGHT_GRAY);
		g.drawLine(x, 20, x, 700);
	}
	public void paintTypes(Graphics g)
	{
		Iterator<Entry<Integer, VehicleType>> i = types.entrySet().iterator();
		int offset = 20+(680/types.size()/2);
		g.setColor(Color.LIGHT_GRAY);
		g.drawLine(25, 20, 900, 20);
		while (i.hasNext())
		{
			g.setColor(Color.LIGHT_GRAY);
			g.drawLine(25, offset+(680/types.size()/2), 900, offset+(680/types.size()/2));
			Map.Entry<Integer, VehicleType> object = (Map.Entry<Integer, VehicleType>)i.next();
			g.setColor(Color.BLACK);
			g.drawString(object.getValue().getName(), 25, offset);
			offset += 680/types.size();
		}
	}
	
}
