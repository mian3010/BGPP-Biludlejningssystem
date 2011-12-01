package bgpp2011;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author Michael
 */
public class VehicleView extends View {
    public VehicleView(Canvas canvas)
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
