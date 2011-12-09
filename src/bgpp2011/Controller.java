
package bgpp2011;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collection;
import java.util.Iterator;

public class Controller
{

	private HashMap<Integer, VehicleType> types;
	private HashMap<Integer, Vehicle> vehicles;
	private HashMap<Integer, Customer> customers;
	private HashMap<Integer, Reservation> reservations;
	private Nexus nexus;
	/*
	 * Controlleren must execute operations given by the user. It must do all the checking and ordering.
	 * 
	 * The checkreservation() method checks if there is an overlap with another reservation. It takes a
	 * reservation as a parameter. First, it checks whether the startdate is before the enddate. Second,
	 * It checks whether the Vehicle of the Reservations re is the same as i the ArrayList. Then it checks
	 * whether the startdate is after the enddate, or if the enddate is before the startdate. If that is true,
	 * the reservation is possible and it will return true.
	 * New commit to help TOKE
	 */
	public Controller()
	{
		nexus = new Nexus();	
		boot();
		
	
	}
	//Method that loads the HashMaps with data from the database. Uses the getmethods() from the nexus.
	public void boot()
	{
		try
		{
			types = nexus.getTypes();
			vehicles = nexus.getVehicles();
			customers = nexus.getCostumers();
			reservations = nexus.getReservations();	
		}
		
		catch(SQLException e)
		{
			System.out.println("SQLException in boot()");
		}
		
	}
	
	/*
	 * This method check if a reservation overlaps another reservation. It compares the dates of the
	 * reservation given in the parameter with the dates from all reservations in the HashMap reservations.
	 * It uses the String-types compare method to check if the dates are overlapping.
	 *
	 */
	public boolean checkReservation(Reservation re)
	{
		//Checks if the startdate is before the enddate.
		int o = re.getStartdate().compareTo(re.getEnddate());
		if( o<0 )
		{
		
			Collection<Reservation> c = reservations.values();
			Iterator<Reservation> i = c.iterator();
			
			while(i.hasNext())
			{
				Reservation ra = i.next();
				Vehicle vt = ra.getVehicle();
				//Checks if the the vehicle re is the same as the vehicle ra.
				if(vt.getId() == re.getVehicle().getId()) 
				{
					//Checks if the stardate of re is after the enddate of ra.
					int startValue = re.getStartdate().compareTo(ra.getEnddate());
					//Checks if the enddate of re is before the startdate of ra.
					int endValue = re.getEnddate().compareTo(ra.getStartdate());
					//If this if-statement is true, the dates are overlapping. Returns false.
						if(startValue < 0 && endValue > 0)
						{
							
							return false;
			  
						}
			    	
					}	
				}
		     //If the code reaches this point. The reservation is possible and it will return true..
			  return true;
         } 
		//If the code reaches this, then the startdate is before the enddate, and therefore illegal.
		return false;
	}	
	
	public HashMap<Integer, Vehicle> searchOpenCars(VehicleType type, Date start, Date end)
	{
		HashMap<Integer,Vehicle> vmap = new HashMap<Integer,Vehicle>();
		
		return vmap;
	}
	/*
	 * This code is very VERY abstract. It start out by making an arrayList containing the cars of
	 *  a given type. Then it runs a for-each loop that checks if there is a car that has no reservations.
	 *  If that is not the case, it runs a for-each loop, that creates a temporary reservation and runs
	 *  the check reservation method on it. If it is true, it will return that car. If there is no car 
	 *  avaliable. It will return null.
	 */
	
	/*
	 * This method returns an ArrayList of Vehicles that have the specified vehicletype.
	 */
	private ArrayList<Vehicle> vehiclesByType(VehicleType v)
	{
	ArrayList<Vehicle> vlist = new ArrayList<Vehicle>();
	Collection<Vehicle> vc = vehicles.values();
	Iterator<Vehicle> it = vc.iterator();
	while(it.hasNext())
	{
		Vehicle v1 = it.next();
		if(v1.getType().getId() == v.getId())
		{
			vlist.add(v1);
		}
	}
	return vlist;
	
	}
	
	public Vehicle findCar(VehicleType v, Date start, Date end)
	{
		ArrayList<Vehicle> tmp = vehiclesByType(v);
		
		/*The for-each-loop creates a reservation containing the same VehicleType, start and enddate
		 * as given in the parameters. 
		 */
		for(Vehicle va : tmp)
		{
			Customer tmpCustomer = new Customer(0,"tmp", 1, "tmp", "tmp");
			Reservation res = new Reservation(0 ,tmpCustomer, va, start, end);
			//It runs the check reservation method on the Reservation res.
				if(checkReservation(res)) 
				{
					//If check reservation returns true, the vehicle is available and returns. 
					return va;
				}
	    }
					//If there is no available vehicle it returns null;
					return null;
}
	
	/*
	 * This method returns a HashMap containing the Vehicles who is available from the 
	 * Date start to Date end, grouped by a type passed as a parameter.
	 */
	public HashMap<Integer, Vehicle> searchVehicles(VehicleType vt, Date start, Date end)
	{
		HashMap<Integer,Vehicle> vmap = new HashMap<Integer,Vehicle>();
		ArrayList<Vehicle> vlist = vehiclesByType(vt);
		for(Vehicle v : vlist)
		{
			Reservation r = new Reservation(0, new Customer(0,"tmp",0,"tmp","tmp"),v, start, end);
			if(checkReservation(r))
			{
				vmap.put(v.getId(),v);
			}
		}
		return vmap;
	}

	/*
	 * This method is called by the GUI and it checks if a reservation is avaliable, and return it 
	 * if it is. Else it returns null.
	 */
	public Reservation createReservation(Customer c, VehicleType t, Date start, Date end)
	{
		
		//Runs the find car method to see if a car is avaliable.
		try
		{
			if(findCar(t, start, end) == null)
			{
			 		return null;
			}
			else 
			{
				
				
					/*If a car is available it creates a temporary reservation and calls the
					 * createEntryReservation() method on it. 
					 */
					Vehicle v = findCar(t, start, end);
					Reservation r = new Reservation(0,c,v,start,end);
					Reservation re = nexus.createEntryReservation(r);
					//If it doesnt return null, the reservation has been registered by the database.
					if(re != null)
					{
					//The reservation is added to the HashMap reservations. It returns the vehicle.
					reservations.put(re.getId(), re);
					boot();
					return re;
					}
				
				
			}
					//If the code reaches this point, the reservation has not been successfully registered.
					return null;
		}
		catch(SQLException e)
		{
			System.out.println("Check reservation error: " + e);
			return null;
		}
	}
	
	/*
	 * This method creates a new customer. It checks whether the customer is already in the system. 
	 */
	public Customer createCustomer(String name, int phonenumber, String address, String bankaccount)
	{
		//Creates a temporary customer with the parameters given in the constructor.
		Customer c = new Customer(0, name, phonenumber, address, bankaccount);
		Collection<Customer> customerC = customers.values();
		Iterator<Customer> itt = customerC.iterator();
		//Runs through the customer HashMap and checks if there is a customer with same name and number.
			while(itt.hasNext())
			{
				Customer c1 = itt.next();
				if(c1.getName().equals(c.getName()) && c1.getNumber() == c.getNumber())
				
				{
					
					return null;
				}
			}
			//Creates a customer using the createEntryCustomer() in the nexus.
				try {
					Customer returnC = nexus.createEntryCustomer(c);
					customers.put(returnC.getId(), returnC);
					//Returns the customer.
					return returnC;
				    }
				catch(Exception e)
					{
					//If the code reaches this point, there has been an error in the database.
					return null;
					}				
	}
	/*
	 * Creates a new vehicle. It check if the type is correct and already in the system.
	 * It sends a vehicle to the database which deligates an id to it. 
	 */
	public Vehicle createVehicle(String make, String model, int year, VehicleType v)
	{
		//Creates a temporary vehicle with the parameters of the constructor.
		Vehicle ve = new Vehicle(0, make, model, year, v);		
		Collection<VehicleType> vehicleTypes = types.values();
		Iterator<VehicleType> itt = vehicleTypes.iterator();
		boolean typeExists = false;
			//The while loop runs through the types HashMap. It checks if the given type exists.
			while(itt.hasNext())
			{
				if(ve.getType().getId() == itt.next().getId())
				{
					typeExists = true;
				}
			//If the type does not exist, it returns null.
			}
			if (!typeExists)
			{
				return null;
			}
			//If the type exist, it creates the vehicle, puts it in the HashMap and returns it.
			try {
				Vehicle returnV = nexus.createEntryVehicle(ve);
				vehicles.put(returnV.getId(), ve);
				return returnV;
				}
			catch(Exception e) {
				//If the code reaches this point there has been an error in the database.
				return null;
				}
		
	}
	/*
	 * Method that creates a new vehicletype. It sends a type to Nexus, witch deligates an id to it.
	 * It checks whether there is already a type with the same name in the system.
	 */
	public boolean createVehicleType(String name, double price)
	{
		//Creates a temporary vehicleType with the parameters.
		VehicleType vee = new VehicleType(0, name, price);
		Collection<VehicleType> c = types.values(); 
		Iterator<VehicleType> itt = c.iterator();
		//Checks of a type with the same name does exist. 
			while(itt.hasNext())
			{
				if(vee.getName() == itt.next().getName())
				{
					return false;
				}
				
			}
			//If the name dies not exist already, it creates the type, puts it in the map and returns it.
			try{
				VehicleType returnT = nexus.createEntryVehicleType(vee);
				
				types.put(returnT.getId(), returnT);
				return true;
				}
			catch(Exception e)
			//If the code reaches this point, there has been an error in the database.
			{
				return false;
			}
	}
	
	//Returns a given reservation.
	public Reservation getReservation(int id)
	{
		
			if(id < reservations.size() && id>0)
			{
			return reservations.get(id);
			
			}
			else 
			{
				throw new IllegalArgumentException("No reservations in that position!");
			}
		
			
	}
	
	//Returns a given customer.
	public Customer getCustomer(int id)
	{
		
			if(id < customers.size() && id>0)
			{
			return customers.get(id);
			
			}
			else 
			{
				throw new IllegalArgumentException("No reservations in that position!");
			}
		
	}
	
	//Returns a given vehicle.
	public Vehicle getVehicle(int id)
	{
		
			if(id < vehicles.size() && id>0)
			{
			return vehicles.get(id);
			
			}
			else 
			{
				throw new IllegalArgumentException("No reservations in that position!");
			}
		
	}
	
	//Returns a given vehicletype.
	public VehicleType getType(int id)

	{
		
			if(id < types.size() && id>0)
			{
			return types.get(id);
			
			}
			else 
			{
				throw new IllegalArgumentException("No reservations in that position!");
			}
		
		
	}
	
	//Deletes a reservation.
	public boolean deleteReservation(Reservation r)
	{
		try {
		Reservation rescheck = reservations.remove(r.getId());
		if(rescheck != null)
			return nexus.deleteReservation(rescheck);
		else
			return false;
		}
		catch(SQLException e)
		{
			return false;
		}
	
		
	}
	public boolean deleteCustomer(Customer c)
	{
		try
		{
			Customer cuscheck = customers.remove(c.getId());
			if(cuscheck != null)
				return nexus.deleteCustomer(cuscheck);
			else
				return false;
		}
		
		catch(SQLException e)
		{
				return false;
		}
	}
	public boolean deleteVehicle(Vehicle v)
	{
		try 
		{
			Vehicle vcheck = vehicles.remove(v.getId());
			if(vcheck != null)
				return nexus.deleteVehicle(vcheck);
			else
				return false;
		}
		
		catch(SQLException e)
		{
				return false;
		}
		}
	
	public boolean deleteVehicleType(VehicleType vt)
	{
		try
		{
			VehicleType vtcheck = types.remove(vt.getId());
			if(vtcheck != null)
				return nexus.deleteVehicleType(vtcheck);
			else
				return false;
		}
		
		catch(SQLException e)
		{
				return false;
		}
	}
	/*
	 * This method takes a new reservation as a parameter and puts it in Hashmap, with a key equal to
	 * the id of the reservation. 
	 */
	
	public boolean editReservation(Reservation newR, Reservation oldR)
	{
		try
		{
			//Runs the checkReservation on newR to see if the reservation is possible.
			if(checkReservation(newR))
			{
				int id = oldR.getId();
				//Creates a new reservation and with the data from newR and id from oldR.
				Reservation res = new Reservation(id, newR.getCustomer(), newR.getVehicle(), new Date(newR.getStartdate()), new Date(newR.getEnddate()));			
				if(nexus.editReservation(res))
				{
					reservations.remove(id);
					reservations.put(id,res);
					return true;
			}		
					return false;
		 }
			
		    return false;
		}
		
		catch(SQLException e)
		{
			return false;
		}
	}
	
	//Edits a vehicle in the database. Returns a boolean.
	public boolean editVehicle(Vehicle newV, Vehicle oldV)
	{
		
		try
		{
			int id = oldV.getId();
			//Creates a new vehicle with the data from newV and id from oldV.
			Vehicle v = new Vehicle(id, newV.getMake(), newV.getModel(), newV.getYear(), newV.getType());
			nexus.editVehicle(v);
			//The exception will be caught here if it comes;
			vehicles.remove(id);
			vehicles.put(id,v);
			return true;
		}
		catch(SQLException e)
		{
			return false;
		}
		 
	}
	
	//Edits a VehicleType. Returns a boolean.
	public boolean editVehicleType(VehicleType vtnew, VehicleType vtold)
	{
		
		try
		{
		    int id = vtold.getId();
		    //Creates a new type with the data from vtnew and id from vtold.
			VehicleType vt = new VehicleType(id, vtnew.getName(), vtnew.getPrice());
			if(nexus.editVehicleType(vt))
			{
				types.remove(id);
				types.put(id,vt);
				return true;
			}
			return false;
		}
		catch(SQLException e)
		{
			return false;
		}
	}
	
	//Edits a customer in the database. Returns a boolean.
	public boolean editCustomer(Customer newC, Customer oldC)
	{
		try
		{
			int id = oldC.getId();
			Customer c = new Customer(id, newC.getName(), newC.getNumber(), newC.getAddress(), newC.getBankAccount());
			if(nexus.editCustomer(c))
			{
				customers.remove(id);
				customers.put(id, c);
				return true;
			}
				return false;
		}
		catch(SQLException e)
		{
			return false;
		}
	}
	/*
	 * Accessor methods for the HashMaps.
	 */
	public HashMap<Integer, Reservation> getReservations()
	{
		return reservations;
	}
	
	public HashMap<Integer, Customer> getCustomers()
	{
		return customers;
	}
	
	public HashMap<Integer, Vehicle> getVehicles()
	{
		return vehicles;
	}
	
	public HashMap<Integer, VehicleType> getTypes()
	{
		return types;
	}
	
	/*
	 * Closes the connection to the database. Uses the close database method from the Nexusclass.
	 */
	public void close()
	{
		nexus.closeDatabase();
	}
	
}
