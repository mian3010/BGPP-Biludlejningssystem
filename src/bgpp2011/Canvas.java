package bgpp2011;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.ImageObserver;

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
    public Canvas()
    {
        //Setting up frame and contentpane
        frame = new JFrame("Cartopia");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        contentPane = new Container();
        contentPane.setLayout(new GridLayout(1,1));
        contentPane.setPreferredSize(new Dimension(1000,800));
        
        changeView(new FrontPageView(this));
        
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
}