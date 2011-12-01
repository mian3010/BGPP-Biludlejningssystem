package bgpp2011;
import java.sql.*;

/*
 * This is the COMMAND CENTER / Nexus part of the database. This 
 * class initialize the connection class and uses the syntax 
 * from the COMMANDS class to communicate with the database.
 * This class is the only link to the rest of the reservation program.
 * @author Magnus Stahl
 */

public class Nexus {
	 private DataBaseCom db;
	   
	    
	    /*
	      * Initialize Nexus and a connection to the database.
	      */
	    public Nexus()
	    {
	        db = new DataBaseCom();
	    }
	    
	    /*
	     * Closes down the database. Should be used in the GUI for closing 
	     * connection and in testing.
	     */
	    
	    public void closeDatabase()
	    {
	        db.close();
	    }
	    
	    /*
	     * Creator method. Updates db
	     * Incomplete for now
	     */
	    public void createEntry(Customer c)
	    {
	        db.update(Commands.createCustomer(c));
	    }
	    public void deleteEntry()
	    {}
	    
	    public void editEntry()
	    {}
	    
	    /*
	     * Getter methods. Returns data as ResultSets
	     * 
	     */
	    
	    public ResultSet getVehicles()
	    {
	        return db.get(Commands.getVehicles());
	    }
	    public ResultSet getReservations()
	    {
	        return db.get(Commands.getReservations());
	    }
	    public ResultSet getTypes()
	    {
	        return db.get(Commands.getTypes());
	    }
	    public ResultSet getCostumers()
	    {
	        return db.get(Commands.getCustomers());
	    }
}
