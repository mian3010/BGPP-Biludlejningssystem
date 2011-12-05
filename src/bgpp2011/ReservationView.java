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
	
	private ArrayList<Reservation> reservations;
    private JTable table;
    
    public ReservationView(Canvas canvas)
    {
        super(canvas);
        reservations = new ArrayList<Reservation>();
        topText = "Reservations";
/*        reservations.add(new Reservation(1, new Customer(1, "Toke Jensen", 11111111, "Vej 1, By 1", "Konto 1"), ));
        reservations.add(new Reservation(2, new Customer(1, "Toke Jensen", 11111111, "Vej 1, By 1", "Konto 1"),));
        reservations.add(new Reservation(3, new Customer(2, "Magnus Stahl", 22222222, "Vej 2, By 2", "Konto 2"),));
        reservations.add(new Reservation(4, new Customer(3, "Michael Søby Andersen", 33333333, "Vej 3, By 3", "Konto 3"),));
        reservations.add(new Reservation(5, new Customer(4, "Jens Jensen", 44444444, "Vej 4, By 4", "Konto 4"),));
        */
        
    }
    public ReservationView(Canvas canvas, int customerID)
    {
        this(canvas);
        
    }
    @Override
    public JPanel draw()
    {
        super.draw();
        return contentPane;
    }
}
