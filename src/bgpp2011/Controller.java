
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
			if(vt.getId() == r.getVehicle().getId()) {
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
	
}