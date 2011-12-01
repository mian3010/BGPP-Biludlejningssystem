package bgpp2011;

public class Commands {
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
}
