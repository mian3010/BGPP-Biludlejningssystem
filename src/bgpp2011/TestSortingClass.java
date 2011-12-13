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
