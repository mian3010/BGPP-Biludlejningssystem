package bgpp2011;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Collection;
import static org.junit.Assert.*;

import org.junit.Test;

public class ControllerTest {

	

	@Test
	public void testHashmaps()
	{
		  Controller controller = new Controller();
	      HashMap<Integer, Vehicle> v = controller.getVehicles();
	      Collection<Vehicle> c = v.values();
	      Iterator<Vehicle> it = c.iterator();
	      controller.close();
	}
	@Test
	public void testCreateReservation()
	{
		try {
		Controller con = new Controller();
		Date d1 = new Date("110928");
		Date d2 = new Date("111023");
		Customer ce = con.createCustomer("Jax Teller", 27879809, "Charming Road 102", "2109-84938492");
		VehicleType v = con.getTypes().get(1);
		System.out.println(v.getName());
		Reservation r = con.createReservation(ce, v, d1, d2);
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
	public void testCreateCustomer()
	{
		Controller con = new Controller();
		Customer c = con.createCustomer("Michael Bolton", 66666666, "LonelyIsland 42", "4444-235425345");
		assertEquals(c.getId(), con.getCustomer(c.getId()).getId());
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
		Customer ce = con.createCustomer("Peter Pan", 25938479, "Shotway 22", "343-345345345");
		Vehicle ve = con.createVehicle("Ellert", "el200", 1990, con.getTypes().get(1));
		Reservation r = new Reservation(100, ce, ve, new Date("140811"), new Date("151011"));
		int id = con.getReservation(1).getId();
		System.out.println("Status");
		con.editReservation(r, con.getReservation(1));
		System.out.println("Edit: " + con.getReservation(id).getCustomer().getName() + " " + con.getReservation(id).getVehicle().getMake());
		
	}
	
	@Test
	public void testCheckReservation()
	{
		Controller con = new Controller();
		Reservation r = con.getReservation(2);
		Reservation r2 = con.getReservation(4);
		Customer ce = con.createCustomer("Jack Sparrow", 426374858, "Tortuga", "343-3454545345");
		Vehicle ve = con.createVehicle("The black Pearl", "Glory42", 1990, con.getTypes().get(1));
		Reservation r3 = new Reservation(100, ce, ve, new Date("880811"), new Date("891011"));
		boolean a = con.checkReservation(r);
		boolean b = con.checkReservation(r2);
		boolean c = con.checkReservation(r3);
		assertEquals(a,false);
		assertEquals(b, false);
		assertEquals(c, true);
	}
	
	@Test
	public void testFindCar()
	{
		Controller con = new Controller();
		VehicleType v = con.getType(2);
		VehicleType v2 = con.getType(3);
		Date d1 = new Date("110202");
		Date d2 = new Date("110303");
		Date d3 = new Date("120303");
		Date d4 = new Date("120404");
		Vehicle ve = con.findCar(v, d1, d2);
		Vehicle ve2 = con.findCar(v2, d3, d4);
		assertEquals(ve.getType().getId(), v.getId());
		assertEquals(ve2.getType().getId(), v2.getId());
	}
}
