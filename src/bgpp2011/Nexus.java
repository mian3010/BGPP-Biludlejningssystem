package bgpp2011;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;


/*
 * This class is essentially the outermost part of the 'Datastoragemanagement' part of the system.
 * This class handles all connections to the database and takes in requests primarily from the 'Controlling/Model' 
 * part of the program.
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
	     * Creator method for reservations. Receives an incomplete (missing ID) reservation object 
	     * and inserts it in the database with an autogenerated ID. 
	     */
	    public Reservation createEntryReservation(Reservation r)
	    {
	    	int id = Commands.getDbID(db.create(Commands.createReservation(r)));
	    	return new Reservation(id,r.getCustomer(),r.getVehicle(),new Date(r.getStartdate()),new Date(r.getEnddate()));
	        
	    }
	    public Customer createEntryCustomer(Customer c)
	    {
	    	int id = Commands.getDbID(db.create(Commands.createCustomer(c)));
	    			return new Customer(id, c.getName(), c.getNumber(), c.getAddress(),c.getBankAccount());

	    }
	    public VehicleType createEntryVehicleType(VehicleType vt)
	    { 
	    	int id = Commands.getDbID(db.create(Commands.createVehicleType(vt)));
	    		 	return new VehicleType(id,vt.getName(),vt.getPrice());    	
	    }
	    public Vehicle createEntryVehicle(Vehicle v)
	    {
	    	int id = Commands.getDbID(db.create(Commands.createVehicle(v)));
	    			return new Vehicle(id,v.getMake(),v.getModel(),v.getYear(),v.getType());	    	
	    }
	/*    public Data createEntry(Data entry)
	    {
	    	int id;
	    	ResultSet r; 
	    	switch(entry instanceof)
	    	{
	    	case Vehicle: 
	    	}
	    }
	    */
	  
	    public boolean deleteCustomer(Customer c)
	    {
	    	return db.update(Commands.deleteCustomer(c));
	    	
	    }
	    public boolean deleteReservation(Reservation r)
	    {
	    	return db.update(Commands.deleteReservation(r));
	    	
	    }
	    public boolean deleteCustomer(Vehicle v)
	    {
	    	return db.update(Commands.deleteVehicle(v));
	 	
	    }
	    public boolean deleteVehicleType(VehicleType vt)
	    {
	    	return db.update(Commands.deleteVehicleType(vt));
	    	
	    }
	     
	    public boolean editCustomer(Customer c)
	    {
	    	return db.update(Commands.updateCustomer(c));
	    
	    }
	    public boolean editReservation(Reservation r)
	    {
	    	return db.update(Commands.updateReservation(r));
	  
	    }
	    public boolean editVehicle(Vehicle v)
	    {
	    	return db.update(Commands.updateVehicle(v));
	    	
	    }
	    public boolean editVehicleType(VehicleType vt)
	    {
	    	return db.update(Commands.updateVehicleType(vt));
	  
	    }
	    
	    /*
	     * The following methods returns HashMaps containing the different types of objects 
	     * used in the Controller for modifying, searching and sorting. 
	     * These methods should be called at the loading of the program and ONLY there,
	     * as they do not guarantee consistency of data if the database itself has been changed. 
	     * Also the methods involves quite a bit of queries from the database but for now this seems
	     * like an okay method.
	     * @author msta & tbrj
	     */
	    
	    public HashMap<Integer, Vehicle> getVehicles() // Depends on the VehicleType as the objects are linked
	    {
	        HashMap<Integer, VehicleType> vtmap = getTypes();
	        ResultSet r = db.get(Commands.getVehicles());
	        return Commands.makeMapVehicles(r, vtmap);
	        
	    }
	    public HashMap<Integer, Reservation> getReservations() // Depends on Vehicles and Customer as the objects are also linked.
	    {
	        
	        HashMap<Integer, Vehicle> vmap = getVehicles();
	        HashMap<Integer, Customer> cmap = getCostumers();
	        ResultSet r = db.get(Commands.getReservations());
	        return Commands.makeMapReservation(r, cmap, vmap);
	        		
	    }
	    public HashMap<Integer, VehicleType> getTypes() // Independent results from the database.
	    {
	       return Commands.makeMapTypes(db.get(Commands.getTypes()));
	    }
	    public HashMap<Integer, Customer> getCostumers() // Same as above.
	    {
	       return Commands.makeMapCustomer(db.get(Commands.getCustomers()));
	        
	    }
}
