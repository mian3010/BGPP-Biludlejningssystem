package bgpp2011;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
		 * The first methods takes in objects of different kinds and creates a SQL-command that creates the post in the 
		 * designated table. These commands are returned as strings to be used further in the Nexus.
		 */
	    public static String createVehicle(Vehicle v)
	    {
	       return "INSERT INTO Vehicle VALUES (" + v.getId() + ", \"" + v.getMake() + 
	        "\", \"" + v.getModel() + "\"," + v.getYear() + ","
	        +  v.getType().getId() + ");";
	     
	    }         
	    public static String createType(VehicleType t)
	    {
	            return "INSERT INTO VehicleType VALUES (" + t.getId() + ",\"" 
	                    + t.getName() + "\"," + t.getPrice() + ");";
	    }
	    public static String createCustomer(Customer c)
	    {
	       return "INSERT INTO Customer VALUES (" + c.getId() + ",\"" + c.getName() 
	                + "\"," + c.getNumber() + ",\""  + c.getAddress() + "\");";   
	    
	    }
	    public static String createReservation(Reservation r)
	    {
	          return "INSERT INTO Reservation VALUES("
	                    + r.getCustomer().getId() + "," + r.getVehicle().getId()
	                    + ",\"" + r.getStartdate() + "\",\"" + r.getEnddate()
	                            + "\");";
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
	        return "SELECT * FROM Vehicles;";
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
	     * These methods should be used when closing the program. They are intended to update the database with the newest content
	     * created while the program was open. It should be noticed that any type of overwriting is allowed; if you have an object
	     * with a corresponding id you will delete the former post in the table.
	     */
	    public static String updateVehicle(Vehicle v)
	    {
	        return "UPDATE Vehicle SET make = \"" + v.getMake() + "\", "
	                + "model = \"" + v.getModel() + "\", typeID = " + 
	                v.getType().getId() + ", year = " + v.getYear() + 
	                " where id = " + v.getId();
	    }
	    public static String updateCustomer(Customer c)
	    {
	        return "UPDATE Customer SET name = \"" + c.getName() + "\", "
	                + "address = \"" + c.getAddress() + "\", phone = " + 
	                c.getNumber() + " where id = " + c.getId();
	    }
	    public static String updateReservation(Reservation r)
	    {
	        return "UPDATE Reservation SET customer = " + r.getCustomer().getId() +
	                ", vehicle = " + r.getVehicle().getId() + ", startdate = \""
	                + r.getStartdate() + "\", enddate = \"" + r.getEnddate()
	                + "\" where id = " + r.getId();
	    }
	    public static String updateVehicleType(VehicleType t)
	    {   
	              return "UPDATE VehicleType SET name = \"" + t.getName() +
	              "\", price = " + t.getPrice() + " where id = " + t.getId();
	    }
	    /*
	     * These methods are responsible for taking in ResultSets from the database and returning them as ArrayList, usable
	     * in the Nexus and especially for further use in the controller. This stage of the 'translation' concludes the Nexus' 
	     * usability when opening the program.
	     */
	    public static ArrayList<Reservation> makeListReservation(ResultSet r, ArrayList<Customer> clist, ArrayList<Vehicle> vlist)
	    {
	         ArrayList<Reservation> returnlist = new ArrayList<Reservation>();
	        try{     
	        
	          while(r.next())
	          {
	            int cid = r.getInt("Customerid");
	            int vid = r.getInt("VehicleId");
	            Date startDate = new Date(r.getString("startdate"));
	            Date endDate = new Date(r.getString("enddate"));
	            Reservation tmp = new Reservation(r.getInt("id"),clist.get(cid), 
	                              vlist.get(vid), startDate, endDate);
	                                              
	            returnlist.add(tmp);
	            r.next();
	          } 
	         
	            return returnlist;  
	          }
	         
	          catch(SQLException e)
	          {
	            System.out.println("Non valid resultset");
	            return returnlist;
	          }
	    }
	    public static ArrayList<Customer> makeListCustomer(ResultSet r)
	    {
	         ArrayList<Customer> returnlist = new ArrayList<Customer>();
	        try{     
	        
	          while(r.next())
	          {
	            Customer tmp = new Customer(r.getInt("id"),r.getString("name"),
	                           r.getInt("phonenumber"), r.getString("address"),
	                           r.getString("bankaccount"));
	                                              
	            returnlist.add(tmp);
	            r.next();
	          } 
	           
	            return returnlist;  
	          }
	         
	          catch(SQLException e)
	          {
	            System.out.println("Non valid resultset");
	            return null;
	          }
	    }
	    public static ArrayList<Vehicle> makeListVehicles(ResultSet r, ArrayList<VehicleType> types)
	    {
	         ArrayList<Vehicle> returnlist = new ArrayList<Vehicle>();
	          try{     
	        
	          while(r.next())
	          {
	            int id = r.getInt("typeId"); 
	            Vehicle tmp = new Vehicle(r.getInt("id"), r.getString("make"),
	                          r.getString("model"), r.getInt("year"),types.get(id));
	                                              
	            returnlist.add(tmp);
	            r.next();
	          } 
	          
	            return returnlist;  
	          }
	         
	          catch(SQLException e)
	          {
	            System.out.println("Non valid resultset");
	            return returnlist;
	          }
	    }
	    public static ArrayList<VehicleType> makeListTypes(ResultSet r)
	    {
	    
		        ArrayList<VehicleType> vtlist = new ArrayList<VehicleType>();
		        try{     
		        
		          while(r.next())
		          {
		            VehicleType v = new VehicleType(r.getInt("id"),r.getString("name"),r.getDouble("price"));
		                                              
		            vtlist.add(v);
		            r.next();
		          }
		            return vtlist;  
		          }
		         
		          catch(SQLException e)
		          {
		            System.out.println("Non valid resultset");
		            return null;
		          }     
	        
	    }
	  	    
	    
	    
}
