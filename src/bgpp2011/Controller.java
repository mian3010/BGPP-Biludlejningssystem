
package bgpp2011;
import java.util.ArrayList;

public class Controller
{
	private ArrayList<VehicleType> types;
	private ArrayList<Vehicle> vehicles;
	private ArrayList<Customer> customer;
	private ArrayList<Reservation> reservations;
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
				
					for(Reservation r : reservations)
					{
						checkReservation(r);
						return null;
					}
		
	}
		             
	/*
	 * Method, that makes a new reservation and adds it to the ArrayList called reservations.
	 * It uses the checkReservations() method to see if it is possible to make the reservations.
	 */
	public boolean makeReservation(int id, Customer c, Vehicle v, Date start, Date end)
	{
		Reservation reservation = new Reservation(id, c, v, start, end);
			if(checkReservation(reservation))
			{
				reservations.add(reservation);
				return true;
			}
			else {
				return false;
			}
			
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
	
}