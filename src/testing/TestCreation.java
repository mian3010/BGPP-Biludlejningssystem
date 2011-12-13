package testing;

import static org.junit.Assert.*;

import java.sql.Date;

import org.junit.Test;

import bgpp2011.Controller;
import bgpp2011.Customer;
import bgpp2011.Reservation;
import bgpp2011.Vehicle;
import bgpp2011.VehicleType;

public class TestCreation {

	@Test
	public void testCreateReservation()
	{
		Controller con = new Controller();
		try {
		
		Date d1 = Date.valueOf("2011-09-28");
		Date d2 = Date.valueOf("2011-10-23");
		Customer ce = con.createCustomer("Jax Teller", 27879809, "Charming Road 102", "2109-84938492");
		VehicleType v = con.getModel().getTypes().get(1);
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
		con.close();
		
}
	@Test
	public void testCreateCustomer()
	{
		Controller con = new Controller();
		Customer c = con.createCustomer("Michael Bolton", 66666666, "LonelyIsland 42", "4444-235425345");
		Customer c2 = con.getModel().getCustomer(2);
		Customer cu = con.createCustomer(c2.getName(), c2.getNumber(),c2.getAddress(),c2.getBankAccount());
		boolean a = false;
			if(c != null)
			{
			a = true;
			}
			boolean b = false;
			if(cu == null)
			{
			b = true;
			}
				assertEquals(a, true);
				assertEquals(b, true);
			con.close();
	}
	
	@Test
	public void testCreateVehicle()
	{
		Controller con = new Controller();
		Vehicle v = con.getModel().getVehicle(4);
		Vehicle ve = con.createVehicle(v.getMake(), v.getModel(), v.getYear(), new VehicleType(12, "Fantasibil", 10000000));
		boolean a = false;
		if(ve == null)
		{
			a = true;
		}
		Vehicle v2 = con.createVehicle("Lada", "Tm 120 shit", 1962, con.getModel().getType(1));
		boolean b = false;
		if(v2 != null)
		{
			b = true;
		}
		
		assertEquals(a, true);
		assertEquals(b, true);
		con.close();
	}
	
	@Test
	public void testCreateVehicleType()
	{
		Controller con = new Controller();
		boolean a = con.createVehicleType("Supercar", 202021920);
		boolean b = con.createVehicleType(con.getModel().getType(1).getName(), 2000000);
		assertEquals(a, true);
		assertEquals(b, false);
		con.close();
		
	}
	

}
