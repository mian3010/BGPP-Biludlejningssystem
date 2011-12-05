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
	    
		 
	     testCreateReservation();
	     testSearchVehicles();
	}
	@Test
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
	      controller.close();
	}
	@Test
	public void testCreateReservation()
	{
		try {
		Date d1 = new Date("110928");
		Date d2 = new Date("111023");
		Controller con = new Controller();
		Customer c = new Customer(100, "Jax Teller", 27879809, "Charming Road 102", "2109-84938492");
		VehicleType v = con.getTypes().get(1);
		System.out.println(v.getName());
		Reservation r = con.createReservation(c, v, d1, d2);
		if(r==null)
		{
			System.out.println("No car");
			con.close();
		}
		else
		{
		System.out.println(r.getCustomer().getName());
		System.out.println(r.getVehicle().getMake());
		System.out.println(r.getStartdate());
		System.out.println(r.getEnddate());
		con.close();
	    }
		}
		catch(NullPointerException e) {
			System.out.println("Null pointer exeption");
		
		}
		
		

}
	@Test
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
			conn.close();
		}
		else
		{
			System.out.println("TestSearchVehicles: no Vehicles");
		}
		conn.close();
		
	}
	@Test
	public void testEditReservations()
	{
		Controller con = new Controller();
		Customer c = new Customer(100, "Peter Pan", 25938479, "Shotway 22", "343-345345345");
		Vehicle v = new Vehicle(100, "Ellert", "el200", 1990, con.getTypes().get(1));
		Reservation r = new Reservation(100, c, v, new Date("130911"), new Date("131011"));
		int id = con.getReservation(1).getId();
		con.editReservation(r, con.getReservation(1));
		System.out.println(con.getReservation(id));
		
	}
}
