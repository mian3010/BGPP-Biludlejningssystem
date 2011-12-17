package view;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import bgpp2011.Canvas;


import java.util.*;

/**
 * 
 * This class is a subclass to view and draws the frontpage type of view.
 * It therefore extends View
 * 
 * @author Michael Søby Andersen
 * @mail msoa@itu.dk
 * 
 */
public class FrontPageView extends View {
    
    /**
     * Constructor for FrontPageView
     * 
     * The constructor initiates the controller and set the text to be displayed
     * at the top of the view
     * 
     * @param canvas The canvas
     */
    public FrontPageView(Canvas canvas)
    {
        super(canvas);
        topText = "Welcome to Cartopia";
    }
    /**
     * Method draw
     * 
     * This method draws the entire view for front page. It overrides the superclass draw
     * but also calls this. It draws the table, the buttons and everything else
     * 
     * @return JPanel The panel that contains the entire view
     */
    @Override
    public JPanel draw()
    {
        JPanel contentPane = super.draw();
        //The tree elements of the borderlayout (North, Center, South)
        JPanel gridLayout = new JPanel(new GridLayout(1,3,50,50));
        gridLayout.setBorder(BorderFactory.createEmptyBorder(100,100,100,100));
        
        //Configuring the four buttons
        ArrayList<JButton> buttons = new ArrayList<JButton>();
        ArrayList<View> views = new ArrayList<View>();
        Dimension size = new Dimension(100,100);
        Font font = new Font("Arial", Font.BOLD, 15);
        
        buttons.add(new JButton("Vehicles"));
        buttons.add(new JButton("Customers"));
        buttons.add(new JButton("Reservations"));
        
        views.add(canvas.getView("vehicle"));
        views.add(canvas.getView("customer"));
        views.add(canvas.getView("reservation"));
        
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
    /**
     * Required when implementing MouseListener
     * Not used
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
