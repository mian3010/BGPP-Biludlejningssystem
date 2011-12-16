package testing;

import static org.junit.Assert.*;
import java.util.HashMap;
import java.util.ArrayList;

import model.Customer;
import model.Reservation;
import model.SortingClass;

import org.junit.Test;

import bgpp2011.Controller;
import bgpp2011.Vehicle;
import bgpp2011.VehicleType;

public class TestSortingClass {

	
	@Test
	public void test() {
		
	}
	
	@Test
	public void testSortVehicles()
	{

		Controller con = new Controller();
		HashMap<Integer, Vehicle> m = con.getModel().getVehicles();
		ArrayList<Vehicle> a = SortingClass.sortVehicles(m);
			assertEquals(a.get(0).toString(), "Coupé: 2011 Audi TT");
			assertEquals(a.get(3).toString(), "Motorcycle: 2010 Harley Davidson Fat Boy");
			assertEquals(a.get(4).toString().equals("5-door: 2003 BMW M1"), false);

	}
	
	
	@Test
	public void testSortReservations()
	{
		Controller con = new Controller();
		HashMap<Integer, Reservation> m = con.getModel().getReservations();
		ArrayList<Reservation> a = SortingClass.sortReservations(m);
		assertEquals(a.get(0).getStartdate(),"2011-02-05");
		assertEquals(a.get(4).getStartdate(),"2011-12-16");
		assertEquals(a.get(5).getStartdate().equals("2011-02-05"),false);

	}
	
	@Test
	public void testCustomers()
	{

		Controller con = new Controller();
		HashMap<Integer, Customer> m = con.getModel().getCustomers();
		ArrayList<Customer> a = SortingClass.sortCustomers(m);
		assertEquals(a.get(0).getName(),"Arya Stark");
		assertEquals(a.get(5).getName(),"Kjeld Ingrisch");
		assertEquals(a.get(9).getName().equals("Arya Stark"), false);
	}
	
	@Test
	public void testVehicleTypes()
	{
		Controller con = new Controller();
		HashMap<Integer, VehicleType> m = con.getModel().getTypes();
		ArrayList<VehicleType> a = SortingClass.sortTypes(m);
		assertEquals(a.get(0).getName(),"3-door");
		assertEquals(a.get(4).getName(),"Motorcycle");
		assertEquals(a.get(2).getName().equals("Coupé"),false);
		
	}

}

//Toke, tbrj----.--.--.-...-.-.-.-l-.-l.-l.-l.-l.-l.-l-.l.-l.l-.l-.-l.-l.-l-.l-.