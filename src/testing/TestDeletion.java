package testing;

import static org.junit.Assert.*;

import java.sql.Date;

import model.Customer;
import model.Reservation;

import org.junit.Test;

import bgpp2011.Controller;
import bgpp2011.Vehicle;
import bgpp2011.VehicleType;

public class TestDeletion {

	@Test
	public void testDelete()
	{
		// Delete reservation.
		Controller con = new Controller();
		Reservation r = con.getModel().getReservation(3);
		boolean a = con.deleteReservation(r);
		Customer ce = new Customer(100, "Clay Morrow", 25438479, "Charming cenemtary 22", "343-34545455345");
		Vehicle ve = new Vehicle(100, "Harley Davidson", "3000", 1962, con.getModel().getTypes().get(1));
		Reservation re = new Reservation(100, ce, ve, Date.valueOf("2014-08-11"), Date.valueOf("2015-10-11"));
		boolean b = con.deleteReservation(re);
		assertEquals(a, true);
		assertEquals(b, false);
		//Delete Customer.
		Customer cu = con.getModel().getCustomer(3);
		boolean c = con.deleteCustomer(cu);
		boolean d = con.deleteCustomer(ce);
		assertEquals(c, true);
		assertEquals(d, false);
		//Delete Vehicle
		Vehicle vec = con.getModel().getVehicle(2);
		boolean e = con.deleteVehicle(vec);
		boolean f = con.deleteVehicle(ve);
		assertEquals(e, true);
		assertEquals(f, false);
		//Delete VehicleType.
		VehicleType vt = con.getModel().getType(2);
		boolean g = con.deleteVehicleType(vt);
		VehicleType vt2 = new VehicleType(100, "TestType", 100000);
		boolean h = con.deleteVehicleType(vt2);
		assertEquals(g, true);
		assertEquals(h, false);
		con.close();
		
	}
}
