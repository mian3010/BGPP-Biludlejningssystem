package bgpp2011;
import java.awt.*;

import javax.swing.*;

/**
 *
 * @author Michael
 * @mail msoa@itu.dk
 * @date 26/11-2011
 * @time 10:30
 * 
 */
public class Canvas{
    private Container contentPane;
    private JFrame frame;
    private View frontpageview, vehicleview, customerview, reservationview;
    public Canvas()
    {
        //Setting up frame and contentpane
        frame = new JFrame("Cartopia");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        contentPane = new Container();
        contentPane.setLayout(new GridLayout(1,1));
        contentPane.setPreferredSize(new Dimension(1000,800));
        
        frontpageview = new FrontPageView(this);
        vehicleview = new VehicleView(this);
        customerview = new CustomerView(this);
        reservationview = new ReservationView(this);
        
        changeView(frontpageview);
        
        frame.pack();
        frame.setVisible(true);
    }
    public void changeView(View view)
    {
        contentPane.removeAll();
        contentPane.add(view.draw());
        frame.setContentPane(contentPane);
        frame.repaint();
    }
    public JFrame getFrame()
    {
    	return frame;
    }
    public View getView(String view)
    {
    	if (view == "frontpage")
    		return frontpageview;
    	else if (view == "vehicle")
    		return vehicleview;
    	else if (view == "customer")
    		return customerview;
    	else if (view == "reservation")
    		return reservationview;
    	else
    		return null;
    }
}