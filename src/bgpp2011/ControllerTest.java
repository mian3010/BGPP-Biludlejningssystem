package bgpp2011;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Collection;
import static org.junit.Assert.*;

import org.junit.Test;

public class ControllerTest {

	@Test
	public void test() 
	{
	    
		 TestDropAllTables td = new TestDropAllTables();
		 td.dropAllTables();
		 TestSetupTables t = new TestSetupTables(); 
		 t.setupAllTables();
	     testCreateReservation();
	     testSearchVehicles();
	}
	
	public void testHashmaps()
	{
		Controller controller = new Controller();
	      HashMap<Integer, Vehicle> v = controller.getVehicles();
	      Collection<Vehicle> c = v.values();
	      Iterator<Vehicle> it = c.iterator();
	      while(it.hasNext())
	      {
	    	  System.out.println(it.next().getMake());
	      }
	}
	
	public void testCreateReservation()
	{
		Date d1 = new Date("110928");
		Date d2 = new Date("111023");
		Controller con = new Controller();
		Customer c = con.getCustomers().get(1);
		VehicleType v = con.getTypes().get(1);
		System.out.println(v.getName());
		Reservation r = con.createReservation(c, v, d1, d2);
		if(r==null)
		{
			System.out.println("No car");
		}
		else
		{
		System.out.println(r.getCustomer().getName());
		System.out.println(r.getVehicle().getMake());
		System.out.println(r.getStartdate());
		System.out.println(r.getEnddate());
	    }

}
	
	public void testSearchVehicles()
	{
		System.out.println("Testing search Vehicles");
		Controller conn = new Controller();
		VehicleType v = conn.getTypes().get(2);
		Date d1 = new Date("120928");
		Date d2 = new Date("121023");
		Vehicle ve = conn.searchVehicles(v, d1, d2);
		if(ve != null)
		{
		System.out.println(ve.getMake());
		}
		else
		{
			System.out.println("TestSearchVehicles: no Vehicles");
		}
		
	}
}
