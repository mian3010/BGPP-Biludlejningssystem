/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bgpp2011;
import java.util.ArrayList;
import java.sql.ResultSet;

/**
 *
 * @author tokejensen
 * This class puts together the nexus from the sql part, and calls the list methods
 * from the listeEngine. It controlls the methods called from the gui. 
 */
public class Controller {
    
    private Nexus nexus;
    
    
    public Controller()
    {
        nexus = new Nexus();
        
    }
    /*
     * The following metods creates a ListEngine with the resultsets from the nexus
     * as parameters. Then calls the makeList methoed from the engine and returns
     * the ArrayList.
     */
    public ArrayList getVehicles()
    {
        ResultSet r = nexus.getVehicles();
        ListEngine l = new ListEngine(r);
        ArrayList returnlist = l.makeList();
        return returnlist;
    }
    
    public ArrayList getCustomers()
    {
        ResultSet r = nexus.getCustomers();
        ListEngine l = new ListEngine(r);
        ArrayList returnlist = l.makeList();
        return returnlist;
    }
    
    public ArrayList getReservations()
    {
        ResultSet r = nexus.getReservations();
        ListEngine l = new ListEngine(r);
        ArrayList returnlist = l.makeList();
        return returnlist;
    }
    
    public ArrayList getTypes()
    {
        ResultSet r = nexus.getTypes();
        ListEngine l = new ListEngine(r);
        ArrayList returnlist = l.makeList();
        return returnlist;
    }
}
