
package bgpp2011;
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
	 */
	public Controller()
	{
		nexus = new Nexus();	
		types = nexus.getTypes();
		vehicles = nexus.getVehicles();
		customers = nexus.getCostumers();
		reservations = nexus.getReservations();
		
	
	}
	
	
	private boolean checkReservation(Reservation re)
	{
		
		int o = re.getStartdate().compareTo(re.getEnddate());
		if( o<0 )
		{
			/*
			 * Remember to check if startdate the same or more than ennddate.
			 */
			Collection<Reservation> c = reservations.values();
			Iterator<Reservation> i = c.iterator();
			
			while(i.hasNext())
			{
				Reservation ra = i.next();
				Vehicle vt = ra.getVehicle();
				if(vt.getId() == ra.getVehicle().getId()) 
				{
					int startValue = re.getStartdate().compareTo(ra.getEnddate());
					int endValue = re.getEnddate().compareTo(ra.getStartdate());
						if(startValue < 0 && endValue > 0)
						{
							System.out.println("Checkreservation is returning false in the whileloop");
							return false;
			  
						}
			    	
					}	
				}
		      System.out.println("CR is returning true outside the whileloop.");     
			  return true;
         } 
		System.out.println("CR is returning false outside the whileloop.");
		return false;
	}	
	/*
	 * This code is very VERY abstract. It start out by making an arrayList containing the cars of
	 *  a given type. Then it runs a for-each loop that checks if there is a car that has no reservations.
	 *  If that is not the case, it runs a for-each loop, that creates a temporary reservation and runs
	 *  the check reservation method on it. If it is true, it will return that car. If there is no car 
	 *  avaliable. It will return null.
	 * 
	 */

	private Vehicle findCar(VehicleType v, Date start, Date end)
	{
		ArrayList<Vehicle> tmp = new ArrayList<Vehicle>();
		
		Collection<Vehicle> vehicleC = vehicles.values();
		Iterator<Vehicle> it = vehicleC.iterator();
		
		while(it.hasNext())
			{  
			Vehicle v1 = it.next();
			int id = v1.getType().getId();
			   if(id == v.getId())
			   	{
				   System.out.println("Are we adding anything?");
				   tmp.add(v1);
			   	}   
			}
					
					/*Collection<Reservation> reservationC = reservations.values();
					Iterator<Reservation> itt = reservationC.iterator();
					while(itt.hasNext())
					{
					Vehicle tmp1 = itt.next().getVehicle();
						if(!tmp.contains(tmp1))
					{
						return tmp1;
					}
					}*/
				
						for(Vehicle va : tmp)
						{
						Customer tmpCustomer = new Customer(0,"tmp", 1, "tmp", "tmp");
						Reservation res = new Reservation(0 ,tmpCustomer, va, start, end);
						System.out.println("Checking reservation?");
							if(checkReservation(res)) 
							{
								System.out.println("findCar is returning variable VA in the for loop");
								return va;
							}
						}
						System.out.println("findCar is returning null outside the for loop.");
						return null;
		
	       }
	
	public Vehicle searchVehicles(VehicleType v, Date start, Date end)
	{
		return findCar(v, start, end);
	}

	/*
	 * This method is called by the GUI and it checks if a reservation is avaliable, and return it 
	 * if it is. Else it resturns null.
	 */
	public Reservation createReservation(Customer c, VehicleType t, Date start, Date end)
	{
		
		if(findCar(t, start, end) == null)
				{
			System.out.println("CreateReservation returns null in the findcar IF statement");
			 		return null;
				}
		else {
					Vehicle v = findCar(t, start, end);
					Reservation r = new Reservation(0,c,v,start,end);
					Reservation re = nexus.createEntryReservation(r);
					if(re != null)
					{
					reservations.put(re.getId(), re);
					System.out.println("Reservation seems succesful returning re in the else statement.");
					return re;
					}
				
			}
		System.out.println("Returning null outside the else statement.");
					return null;
	}
	
	/*
	 * This method creates a new customer. It checks whether the customer is already in the system. 
	 */
	public boolean createCustomer(String name, int phonenumber, String address, String bankaccount)
	{
		Customer c = new Customer(0, name, phonenumber, address, bankaccount);
		Collection<Customer> customerC = customers.values();
		Iterator<Customer> itt = customerC.iterator();
			while(itt.hasNext())
			{
				if(itt.next()==c)
				{
					return false;
				}
			}
			
				try {
					Customer returnC = nexus.createEntryCustomer(c);
					customers.put(returnC.getId(), returnC);
					return true;
				    }
				catch(Exception e)
					{
					return false;
					}				
	}
	/*
	 * Creates a new vehicle. It check if the type is correct and already in the system.
	 * It sends a vehicle to the database which deligates an id to it. 
	 */

	public boolean createVehicle(String make, String model, int year, VehicleType v)
	{
		Vehicle ve = new Vehicle(0, make, model, year, v);
		
		Collection<Vehicle> vehicleC = vehicles.values();
		Iterator<Vehicle> itt = vehicleC.iterator();
			while(itt.hasNext())
			{
				if(ve.getType() != itt.next().getType())
				{
					return false;
				}
			}
			try {
				Vehicle returnV = nexus.createEntryVehicle(ve);
				vehicles.put(returnV.getId(), ve);
				return true;
				}
			catch(Exception e) {
				return false;
				}
		
	}
	/*
	 * Method that creates a new vehicletype. It sends a type to Nexus, witch deligates an id to it.
	 * It checks whether there is already a type with the same name in the system.
	 */
	public boolean createVehicleType(String name, double price)
	{
		VehicleType vee = new VehicleType(0, name, price);
		Collection<VehicleType> c = types.values(); 
		Iterator<VehicleType> itt = c.iterator();
			while(itt.hasNext())
			{
				if(vee.getName() == itt.next().getName())
				{
					return false;
				}
				
			}
			try{
				VehicleType returnT = nexus.createEntryVehicleType(vee);
				
				types.put(returnT.getId(), returnT);
				return true;
				}
			catch(Exception e)
			{
				return false;
			}
	}
	
	/*
	 * Basic accsessor methods!
	 */
	public Reservation getReservation(int id)
	{
		try 
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
		catch(NullPointerException e)
		{
			return null;
		}
			
	}
	
	public Customer getCustomer(int id)
	{
		try 
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
		catch(Exception e)
		{
			return null;
		}
	}
	
	public Vehicle getVehicle(int id)
	{
		try 
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
		catch(Exception e)
		{
			return null;
		}
	}
	
	public VehicleType getType(int id)

	{
		try 
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
		catch(Exception e)
		{
			return null;
		}
	}
	
	public boolean deleteReservation()
	{
		return true;
	}
	/*
	 * This method takes a new reservation as a parameter and puts it in Hashmap, with a key equal to
	 * the id of the reservation. 
	 */
	
	public boolean editReservation(Reservation newR, Reservation oldR)
	{
		
		if(checkReservation(newR))
		  {
			int id = oldR.getId();
			Reservation res = new Reservation(id, newR.getCustomer(), newR.getVehicle(), new Date(newR.getStartdate()), new Date(newR.getEnddate()));
			if(nexus.editReservation(res))
			{
			System.out.println("EDit: nexus.editR is true");
			Reservation r = nexus.editReservation(res);
			reservations.remove(id);
			reservations.put(id,res);
			return true;
			}
			System.out.println("edit: returns false");
			return false;
		  }
			System.out.println("edit: returns false2");
		    return false;
	}
	
	public boolean editVehicle(Vehicle newV, Vehicle oldV)
	{
		
			int id = oldV.getId();
			Vehicle v = new Vehicle(id, newV.getMake(), newV.getModel(), newV.getYear(), newV.getType());
			if(nexus.editVehicle(v))
			{
			vehicles.remove(id);
			vehicles.put(id,v);
			return true;
			}
			return false;
		 
	}
	
	/*
	 * Acssesor methods for the HashMaps.
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
	
	public void close()
	{
		nexus.closeDatabase();
	}
	
}