
package bgpp2011;
import java.util.ArrayList;

public class Controller
{
	private ArrayList<VehicleType> types;
	private ArrayList<Vehicle> vehicles;
	private ArrayList<Customer> customer;
	private ArrayList<Reservation> reservations;
	private Nexus nexus;
	/*
	 * Controlleren must execute operations given by the user. It must do all the checking and ordering.
	 * 
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
		reservations = new ArrayList<Reservation>();
	}
	
	
	public boolean checkReservation(Reservation re)
	{
		
		int o = re.getStartdate().compareTo(re.getEnddate());
		if( o<0 )
		{
			/*
			 * Remember to check if startdate the same or more than ennddate.
			 */
			for(Reservation r : reservations)
			{
				Vehicle vt = r.getVehicle();
				if(vt.getId() == r.getVehicle().getId()) 
				{
					int startValue = re.getStartdate().compareTo(r.getEnddate());
					int endValue = re.getEnddate().compareTo(r.getStartdate());
						if(startValue < 0 && endValue > 0)
						{
							return false;
			  
						}
			    	
				}	
            }
		           
			         
         } 
		return true;
	}	
	/*
	 * This code is very VERY abstract. It start out by making an arrayList containing the cars of
	 *  a given type. Then it runs a for-each loop that checks if there is a car that has no reservations.
	 *  If that is not the case, it runs a for-each loop, that creates a temporary reservation and runs
	 *  the check reservation method on it. If it is true, it will return that car. If there is no car 
	 *  avaliable. It will return null.
	 * 
	 */

	public Vehicle findCar(VehicleType v, Date start, Date end)
	{
		ArrayList<Vehicle> tmp = new ArrayList<Vehicle>();
		for(Vehicle ve : vehicles)
			{  
			   if(ve.getType() == v)
			   	{
				   tmp.add(ve);
			   	}   
			}
					for(Reservation r : reservations)
					{
					Vehicle tmp1 = r.getVehicle();
						if(!tmp.contains(tmp1))
					{
						return tmp1;
					}
					}
				
						for(Vehicle va : tmp)
						{
						Customer tmpCustomer = new Customer(1,"tmp", 1, "tmp", "tmp");
						Reservation res = new Reservation(0, tmpCustomer, va, start, end);
							if(checkReservation(res)) 
							{
								return va;
							}
						}
						return null;
		
	       }
		             
	/*
	 * Method, that makes a new reservation and adds it to the ArrayList called reservations.
	 * It uses the checkReservations() method to see if it is possible to make the reservations.
	 */
	private Reservation makeReservation(Customer c, VehicleType t, Date start, Date end)
	{
		if(findCar(t, start, end) == null)
		{
			return null;
		}
		else {
			Vehicle tmp = findCar(t, start, end);
			Reservation reservation = new Reservation(0, c, tmp, start, end);	
			return reservation;
						
			}			
	}
	
	public Reservation createReservation(Customer c, VehicleType t, Date start, Date end)
	{
		if(findCar(t, start, end) == null)
				{
			 		return null;
				}
		else {
					if(Nexus.createEntryReservation(makeReservation(c, t, start, end) != null))
					{
					Reservation r = Nexus.createEntryReservation(makeReservation(c, t, start, end));
					return r;
					}
				
				}
		return null;
	}
	
	
	public Reservation getReservation(int i)
	{
		if(i < reservations.size())
		{
		return reservations.get(i);
		}
		else {
			throw new IllegalArgumentException("No reservations in that position!");
		}
	}
	
	public boolean changeReservation(int i, Reservation newR)
	{
		
		if(checkReservation(newR))
		  {
			reservations.remove(i);
			reservations.add(i,newR);
			return true;
		  }
		    return false;
	}
	
	public String addReservation()
	{
		VehicleType vt = new VehicleType(4, "4door", 130);
		Vehicle v = new Vehicle(1, "ford", "escort", 1990, vt);
		Vehicle v2 = new Vehicle(2, "mazda", "626", 1999, vt);
		Customer c1 = new Customer(1, "Poul Larsen", 37226455, "R¿devej 7, ", "2299-8287378834");
		Customer c2 = new Customer(2, "Rudolph Martins", 67241594, "Alberts v¾nge 32, ", "2596-822343781234");
		Date d1 = new Date("110623");
		Date d2 = new Date("110328");
		Reservation r1 = new Reservation(1, c1, v, d1, d2);
		Reservation r2 = new Reservation(2, c2, v2, d1, d2);
		reservations.add(r1);
		reservations.add(r2);
		String s = "";
		for(Reservation r : reservations)
		{
			Vehicle veh = r.getVehicle();
			s = s + "" + veh.getMake() + " " + veh.getModel();
		}
		return s;
	}
	
}