package bgpp2011;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
/*
 * This class needs alot of explanation.
 * Basically this is class designed to keep every bit of SQL-syntax-related code separated from the class that 
 * delivers the data from the database to the controlling part of the system. This is done in a somewhat 
 * cumbersome manner but the idea is that you should be able to change database fairly easy if you edit this class
 * and the CONNECTION part of the 'data-delivering-system' (here called Nexus). This class need not be instantiated anywhere
 * as it because of its 'translate'-like nature can be replaced as long as the parameters are the same.
 * @authors mainly msta & tbrj.
 */


public class Commands {
		/*
		 * These first methods takes in objects of different kinds and creates a SQL-command that creates the post in the 
		 * designated table. These commands are returned as strings to be used further in the Nexus.
		 * @param Different objects used in the Javaprogram such as Vehicle, VehicleType, Customer and Reservation
		 * @return String of valid SQL-syntax used to insert into SQL database.
		 */
	

		/*
		 * Creates valid SQL-syntax for inserting Vehicle data from an object into a database table
		 * named Vehicle.
		 */
	    public static String createVehicle(Vehicle v)
	    {
	       return "INSERT INTO Vehicle VALUES (null, \"" + v.getMake() + 
	        "\", \"" + v.getModel() + "\"," + v.getYear() + ","
	        +  v.getType().getId() + ");";  
	    }
	    
		/*
		 * Creates valid SQL-syntax for inserting VehicleType data from an object into a database table
		 * named VehicleType.
		 */
	    public static String createVehicleType(VehicleType t)
	    {
	            return "INSERT INTO VehicleType VALUES (null, " + t.getPrice()
	            		+ ",\"" + t.getName() + "\");";
	    }
	    
		/*
		 * Creates valid SQL-syntax for inserting Customer data from an object into a database table
		 * named Customer.
		 */
	    public static String createCustomer(Customer c)
	    {
	      return "INSERT INTO Customer VALUES (null, \"" + c.getName() 
	                + "\"," + c.getNumber() + ",\""  + c.getAddress() + "\", \""
	                + c.getBankAccount() + "\");";    
	    }
	    
		/*
		 * Creates valid SQL-syntax for inserting Reservation data from an object into a database table
		 * named Reservation.
		 */
	    public static String createReservation(Reservation r)
	    {
	          return "INSERT INTO Reservation VALUES(null,"
	                    + r.getCustomer().getId() + "," + r.getVehicle().getId()
	                    + ",\"" + r.getDateStart() + "\",\"" + r.getDateEnd()
	                            + "\");";
	    }	 
	   
	    /*
	     * SQL-syntax for deleting a post in the table Vehicle with information corresponding to the
	     * Vehicle.
	     * @param Vehicle Vehicle object containing the information that should be deleted from the table.
	     * @return String the corresponding SQL syntax.
	     */
	    public static String deleteVehicle(Vehicle v)
	    {
	    	return "DELETE FROM Vehicle WHERE id = " + v.getId() + ";";
	    }
	    public static String deleteReservation(Reservation r)
	    {
	    	return "DELETE FROM Reservation WHERE id = " + r.getId() + ";";
	    }
	    public static String deleteCustomer(Customer c)
	    {
	    	return "DELETE FROM Customer WHERE id = " + c.getId() + ";";
	    }
	    public static String deleteVehicleType(VehicleType vt)
	    {
	    	return "DELETE FROM VehicleType WHERE id = " + vt.getId() + ";";
	    }
	    /*
	     * The following methods are SQL-command for retrieving entire tables as ResultSets, usable in the Nexus.
	     */
	    
	    public static String getReservations()
	    {
	          return "SELECT * FROM Reservation;";
	    }
	    public static String getVehicles()
	    {
	        return "SELECT * FROM Vehicle;";
	    }
	    public static String getCustomers()
	    {
	        return "SELECT * FROM Customer;";
	    }
	    public static String getTypes()
	    {
	        return "SELECT * from VehicleType;";
	    }
	    /*
	     * The following methods are used when editing different types of object in the java structure,
	     * then wanting to update them in the database.
	     * Please note that these methods are ID specific, which means that they will overwrite
	     * any post in the table with the same ID, no questions asked.
	     * Returns valid SQL syntax usable in the DataBaseConnections.
	     */
	    public static String updateVehicle(Vehicle v)
	    {
	        return "UPDATE Vehicle SET make = \"" + v.getMake() + "\", "
	                + "model = \"" + v.getModel() + "\", typeID = " + 
	                v.getType().getId() + ", year = " + v.getYear() + 
	                " where id = " + v.getId() + ";";
	    }
	    public static String updateCustomer(Customer c)
	    {
	        return "UPDATE Customer SET name = \"" + c.getName() + "\", "
	                + "address = \"" + c.getAddress() + "\", phonenumber = " + 
	                c.getNumber() + " where id = " + c.getId() + ";";
	    }
	    public static String updateReservation(Reservation r)
	    {
	        return "UPDATE Reservation SET customerID = " + r.getCustomer().getId() +
	                ", vehicleID = " + r.getVehicle().getId() + ", startdate = \""
	                + r.getDateStart() + "\", enddate = \"" + r.getDateEnd()
	                + "\" where id = " + r.getId() + ";";
	    }
	    public static String updateVehicleType(VehicleType t)
	    {   
	              return "UPDATE VehicleType SET name = \"" + t.getName() +
	              "\", price = " + t.getPrice() + " where id = " + t.getId() + ";";
	    }
	    /*
	     * These methods are responsible for taking in ResultSets from the database and returning them as HashMaps, usable
	     * in the Nexus and especially for further use in the controller. This stage of the 'translation' concludes the Nexus' 
	     * usability when opening the program.
	     */
	    public static HashMap<Integer, Reservation> makeMapReservation(ResultSet r, HashMap<Integer, Customer> cmap, HashMap<Integer, Vehicle> vmap)
	    {
	         HashMap<Integer, Reservation> returnmap = new HashMap<Integer, Reservation>();
	        try{     
	        
	          while(r.next())
	          {
	            int cid = r.getInt("Customerid");
	            int vid = r.getInt("VehicleId");
	            Date startDate = Date.valueOf(r.getString("startdate"));
	            Date endDate = Date.valueOf(r.getString("enddate"));
	            int id = r.getInt("id");
	            Reservation tmp = new Reservation(id,cmap.get(cid), 
	                              vmap.get(vid), startDate, endDate);
	                                              
	            returnmap.put(id,tmp);
	        
	          } 
	         
	            return returnmap;  
	          }
	         
	          catch(SQLException e)
	          {
	            System.out.println("Non valid resultset at reservation creation :" + e);
	            return null;
	          }
	    }
	    
	    public static HashMap<Integer, Customer> makeMapCustomer(ResultSet r)
	    {
	         HashMap<Integer, Customer> returnmap = new HashMap<Integer, Customer>();
	        try{     
	        
	          while(r.next())
	          {
	        	 int id = r.getInt("id");
	            Customer tmp = new Customer(id,r.getString("name"),
	                           r.getInt("phonenumber"), r.getString("address"),
	                           r.getString("bankaccount"));
	                                              
	            returnmap.put(id,tmp);
	            
	          } 
	           
	            return returnmap;  
	          }
	         
	          catch(SQLException exn)
	          {
	            System.out.println("Non valid resultset" + exn);
	            return null;
	          }
	    }
	    
	    public static HashMap<Integer, Vehicle> makeMapVehicles(ResultSet r, HashMap<Integer, VehicleType> types)
	    {
	         HashMap<Integer, Vehicle> returnmap = new HashMap<Integer, Vehicle>();
	          try{     
	        	 
	          while(r.next())
	          {
	        	 
	        	int vid = r.getInt("id");
	        	
	            int id = r.getInt("typeId");
	         
	            Vehicle tmp = new Vehicle(vid,r.getString("make"),
	                          r.getString("model"), r.getInt("year"),types.get(id));
	           
	                                              
	            returnmap.put(vid, tmp);
	           
	          } 
	          
	            return returnmap;  
	          }
	         
	          catch(SQLException exn)
	          {
	            System.out.println("Non valid resultset at vehicle map creation" + exn);
	            return returnmap;
	          }
	    }
	   
	    public static HashMap<Integer, VehicleType> makeMapTypes(ResultSet r)
	    {
	    
		        HashMap<Integer, VehicleType> vtmap = new HashMap<Integer, VehicleType>();
		        try{     
		        
		          while(r.next())
		          {
		        	int id = r.getInt("id");
		            VehicleType v = new VehicleType(id, r.getString("name") ,r.getDouble("price"));
		                                              
		            vtmap.put(id,v);
		            
		          }
		            return vtmap;  
		          }
		         
		          catch(SQLException exn)
		          {
		            System.out.println("Non valid resultset at vehicletype creation: " + exn);
		            return null;
		          }     
	        
	    }
  	   
	    /*
	     * This method returns an integer when given a ResultSet containing an auto-generated key from an SQL
	     * database. This will throw an exception when the ResultSet is either closed too soon or 
	     * not valid for retrieving id's from it.
	     * Returns an integer relating to the requested auto-generated key.
	     */
	    public static int getDbID(ResultSet r)
  	    {
  	    	try {
  	    		if(r == null)
  	    			System.out.println("The resultset is not valid at getDBID request.");
  	    		r.next();	
  	    		return r.getInt(1);
  	    	}
  	    	catch(SQLException exn) {
  	    		throw new IllegalArgumentException("The system could not fetch the ID for the requested object:" + exn);
  	    	}
  	    }
	    
	    
}
