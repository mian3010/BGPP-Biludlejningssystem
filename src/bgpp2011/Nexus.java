package bgpp2011;
import java.sql.*;
import java.util.ArrayList;

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
	    public Reservation createEntryReservation(Reservation r)
	    {
	    	ResultSet res = db.get(Commands.createReservation(r));
	        try {
	        	int id = res.getInt("id");
		    	return new Reservation(id,r.getCustomer(),r.getVehicle(),new Date(r.getStartdate()),new Date(r.getEnddate()));
	        }
	        catch(SQLException exn) {
	        	System.out.println("Database error while handing new reservation results.");
	        	return null;
	        }
	    }
	    
	  
	    public void deleteEntry()
	    {}
	    
	    public void editEntry()
	    {}
	    
	    /*
	     * The following methods returns ArrayLists containing the different types of objects 
	     * used in the Controller for modifying, searching and sorting. 
	     * These methods should be called at the loading of the program and ONLY there,
	     * as they do not guarantee consistency of data if the database itself has been changed. 
	     * Also the methods involves quite a bit of queries from the database but for now this seems
	     * like an okay method.
	     * @author msta & tbrj
	     */
	    
	    public ArrayList<Vehicle> getVehicles() // Depends on the VehicleType as the objects are linked
	    {
	        ResultSet r = db.get(Commands.getVehicles());
	        ArrayList<VehicleType> vtlist = getTypes();
	        return Commands.makeListVehicles(r, vtlist);
	        
	    }
	    public ArrayList<Reservation> getReservations() // Depends on Vehicles and Customer as the objects are also linked.
	    {
	        ResultSet r = db.get(Commands.getReservations());
	        ArrayList<Vehicle> vlist = getVehicles();
	        ArrayList<Customer> clist = getCostumers();
	        return Commands.makeListReservation(r, clist, vlist);
	        		
	    }
	    public ArrayList<VehicleType> getTypes() // Independent results from the database.
	    {
	       return Commands.makeListTypes(db.get(Commands.getReservations()));
	    }
	    public ArrayList<Customer> getCostumers() // Same as above.
	    {
	       return Commands.makeListCustomer(db.get(Commands.getCustomers()));
	        
	    }
}
