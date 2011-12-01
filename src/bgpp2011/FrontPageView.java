package bgpp2011;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.*;

/**
 *
 * @author Michael Søby Andersen
 * @mail msoa@itu.dk
 * @date 27/11-2011
 * @time 12:42
 * 
 */
public class FrontPageView extends View {
    
    
    public FrontPageView(Canvas canvas)
    {
        super(canvas);
        topText = "Welcome to Cartopia";
    }
    @Override
    public JPanel draw()
    {
        super.draw();
        //The tree elements of the borderlayout (North, Center, South)
        JPanel gridLayout = new JPanel(new GridLayout(2,2,50,50));
        gridLayout.setBorder(BorderFactory.createEmptyBorder(100,100,100,100));
        
        //Configuring the four buttons
        ArrayList<JButton> buttons = new ArrayList<JButton>();
        ArrayList<View> views = new ArrayList<View>();
        Dimension size = new Dimension(100,100);
        Font font = new Font("Arial", Font.BOLD, 15);
        
        buttons.add(new JButton("Customers"));
        buttons.add(new JButton("Reservations"));
        buttons.add(new JButton("Vehicles"));
        buttons.add(new JButton("Vehicle Types"));
        
        views.add(new CustomerView(canvas));
        views.add(new ReservationView(canvas));
        views.add(new VehicleView(canvas));
        views.add(new VehicleView(canvas));
        
        
        //Setting size and font and adding to layout
        Iterator<JButton> i1 = buttons.iterator();
        Iterator<View> i2 = views.iterator();
        while (i1.hasNext() && i2.hasNext())
        {
            final JButton button = i1.next();
            final View view = i2.next();
            button.setPreferredSize(size);
            button.setFont(font);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) { 
                    canvas.changeView(view);
                }
            });
           
            gridLayout.add(button);
        }
        
        //Adding the element to borderlayout
        contentPane.add(gridLayout, BorderLayout.CENTER);
        
        return contentPane;
    }
}
