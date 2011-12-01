package bgpp2011;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

/**
 *
 * @author Michael
 */
public class ReservationView extends View {
    public ReservationView(Canvas canvas)
    {
        super(canvas);
        
    }
    @Override
    public JPanel draw()
    {
        super.draw();
        return contentPane;
    }
}
