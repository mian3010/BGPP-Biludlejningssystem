
package testing;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Collection;
import static org.junit.Assert.*;
import java.sql.Date;
import org.junit.Test;

import bgpp2011.Controller;
import bgpp2011.Customer;
import bgpp2011.Reservation;
import bgpp2011.Vehicle;
import bgpp2011.VehicleType;

public class testControllerChecks {

	/*
	 * This testclass test all the non-trivial methods in the controller class. All test will return true
	 * In the tables in database has been reset before run. All the test have assertequals that will return
	 * false and a assertEquals that will return false. This ensures that the checks can both have a 
	 * succesful and an unsuccessful outcome.
	 */

	@Test
	public void testHashmaps()
	{
		  Controller controller = new Controller();
	      HashMap<Integer, Vehicle> v = controller.getModel().getVehicles();
	      Collection<Vehicle> c = v.values();
	      Iterator<Vehicle> it = c.iterator();
	      boolean a = true;
	      while(it.hasNext())
	      {
	    	  Vehicle ve = it.next();
	    	  if(ve == null)
	    		  a = false;
	      }
	      assertEquals(a, true);
	      controller.close();
	}
	
		
	@Test
	public void testEditReservations()
	{
		
		Controller con = new Controller();
		Customer ce = con.createCustomer("Peter Pan", 25938479, "Shotway 22", "343-345345345");
		Vehicle ve = con.createVehicle("Ellert", "el200", 1990, con.getModel().getTypes().get(1));
		Reservation r = new Reservation(100, ce, ve, Date.valueOf("2014-08-11"), Date.valueOf("2015-10-11"));
		int id = con.getModel().getReservation(1).getId();	
		con.editReservation(r, con.getModel().getReservation(1));
		System.out.println("Edit: " + con.getModel().getReservation(id).getCustomer().getName() + " " + con.getModel().getReservation(id).getVehicle().getMake());
		con.close();
	}
	
	@Test
	public void testCheckReservation()
	{
		Controller con = new Controller();
		Reservation r = con.getModel().getReservation(2);
		Reservation r2 = con.getModel().getReservation(4);
		Customer ce = con.createCustomer("Jack Sparrow", 426374858, "Tortuga", "343-3454545345");
		Vehicle ve = con.createVehicle("The black Pearl", "Glory42", 1990, con.getModel().getTypes().get(1));
		Reservation r3 = new Reservation(100, ce, ve, Date.valueOf("1988-08-11"), Date.valueOf("1989-10-11"));
		boolean a = con.checkReservation(r);
		boolean b = con.checkReservation(r2);
		boolean c = con.checkReservation(r3);
		assertEquals(a,false);
		assertEquals(b, false);
		assertEquals(c, true);
		con.close();
	}
	
	@Test
	public void testFindCar()
	{
		Controller con = new Controller();
		VehicleType v = con.getModel().getType(2);
		VehicleType v2 = con.getModel().getType(3);
		Date d1 = Date.valueOf("2011-02-02");
		Date d2 = Date.valueOf("2011-03-03");
		Date d3 = Date.valueOf("2012-03-03");
		Date d4 = Date.valueOf("2012-04-04");
		Vehicle ve = con.searchVehicles(v, d1, d2);
		Vehicle ve2 = con.searchVehicles(v2, d3, d4);
		assertEquals(ve.getType().getId(), v.getId());
		assertEquals(ve2.getType().getId(), v2.getId());
		con.close();
	}
	

}
//Author: Toke Jensen.