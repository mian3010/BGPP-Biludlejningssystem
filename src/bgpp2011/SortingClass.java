package bgpp2011;
import java.util.Collections;
import java.util.Collection;
import java.util.HashMap;
import java.util.ArrayList;

public class SortingClass

/*
 * This class contains static methods that returns sorted ArrayLists. 
 * It can be used by the GUI to get its data sorted.
 */

{
	public SortingClass()
	{

	}
	
	//Takes an HashMap of vehicles as parameter. Returns an ArrayList sorted by vehiclemake.
	public static ArrayList<Vehicle> sortVehicles(HashMap<Integer, Vehicle> hm)
	{
		//Makes a collection.
		Collection<Vehicle> c = hm.values();
		//Creates an ArrayList from that collection.
		ArrayList<Vehicle> a = new ArrayList<Vehicle>(c);		
		Collections.sort(a);
		return a;
	}
	
	//Takes a HashMap as parameter. Returns a sorted ArrayList of reservations.
	public static ArrayList<Reservation> sortReservations(HashMap<Integer, Reservation> hm)
	{
		//Makes a Collection.
		Collection<Reservation> c = hm.values();
		//Makes an ArrayList from that collection.
		ArrayList<Reservation> a = new ArrayList<Reservation>(c);
		Collections.sort(a);
		return a;
	}
	
	
	//Takes an HashMap of Customers as parameter. Returns a sorted ArrayList.
	public static ArrayList<Customer> sortCustomers(HashMap<Integer, Customer> hm)
	{
		//Makes a Collection.
		Collection<Customer> c = hm.values();
		//Makes an ArrayList from that collection.
		ArrayList<Customer> a = new ArrayList<Customer>(c);
		Collections.sort(a);
		return a;
	}
	
	//Takes an HashMap as parameter. Returns a sorted ArrayList.
	public static ArrayList<VehicleType> sortTypes(HashMap<Integer, VehicleType> hm)
	{
		//Makes a Collection.
		Collection<VehicleType> c = hm.values();
		//Makes an ArrayList from that collection.
		ArrayList<VehicleType> a = new ArrayList<VehicleType>(c);
		Collections.sort(a);
		return a;
	}
}

//Author Toke Jensen, tbrj <><><><><><><><><><><><><><><><><><><><><><<<<<<<<<>>>><<>><<<<<<<><><<<<
