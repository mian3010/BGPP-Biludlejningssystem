package bgpp2011;

import static org.junit.Assert.*;
import java.util.HashMap;
import java.util.ArrayList;

import org.junit.Test;

public class TestSortingClass {

	@Test
	public void test() {
		
	}
	
	@Test
	public void testSortVehicles()
	{
		try {
		Controller con = new Controller();
		HashMap<Integer, Vehicle> m = con.getModel().getVehicles();
		ArrayList<Vehicle> a = SortingClass.sortVehicles(m);
			for(Object c : a)
			{
				System.out.println(c.toString() + "");
			}
			int i1 = a.get(0).toString().compareTo("4-d¿rs: 2001 Audi A4");
			assertEquals(i1, 0);
			int i2 = a.get(3).toString().compareTo("4-d¿rs: 3333 BMW MichaelsBimmer");
			assertEquals(i2, 0);
			boolean b = false;
			int i3 = a.get(4).toString().compareTo("999");
			if(i3 < 0)
				b = true;
			assertEquals(b, true);
			}
		catch(ClassCastException e)
			{
			System.out.println("Fail");
			}
	}
	
	
	@Test
	public void testSortReservations()
	{
		try {
		Controller con = new Controller();
		HashMap<Integer, Reservation> m = con.getModel().getReservations();
		ArrayList<Reservation> a = SortingClass.sortReservations(m);
			for(Reservation c : a)
			{
				System.out.println(c.getStartdate() + "");
			}
			}
		catch(ClassCastException e)
			{
			System.out.println("Fail");
			}
	}
	
	@Test
	public void testCustomers()
	{
		try {
		Controller con = new Controller();
		HashMap<Integer, Customer> m = con.getModel().getCustomers();
		ArrayList<Customer> a = SortingClass.sortCustomers(m);
			for(Object c : a)
			{
				System.out.println(c.toString() + "");
			}
			}
		catch(ClassCastException e)
			{
			System.out.println("Fail");
			}
	}
	
	@Test
	public void testVehicleTypes()
	{
		try {
		Controller con = new Controller();
		HashMap<Integer, VehicleType> m = con.getModel().getTypes();
		ArrayList<VehicleType> a = SortingClass.sortTypes(m);
			for(Object c : a)
			{
				System.out.println(c.toString() + "");
			}
			}
		catch(ClassCastException e)
			{
			System.out.println("Fail");
			}
	}

}
