package model;
import java.util.Collections;
import java.util.Collection;
import java.util.HashMap;
import java.util.ArrayList;



public class SortingClass

/**
 * This class contains static methods that returns sorted ArrayLists. 
 * It can be used by the GUI to get its data sorted.
 * @author tbrj
 */

{
	public SortingClass()
	{

	}
	
	/**
	 * Sorts a HashMap of Vehicles. Returns an ArrayList.
	 * @param hm HashMap
	 * @return ArrayList
	 */
	public static ArrayList<Vehicle> sortVehicles(HashMap<Integer, Vehicle> hm)
	{
		//Makes a collection.
		Collection<Vehicle> c = hm.values();
		//Creates an ArrayList from that collection.
		ArrayList<Vehicle> a = new ArrayList<Vehicle>(c);		
		Collections.sort(a);
		return a;
	}
	
	/**
	 * Sorts a HashMap of Reservations. Returns an ArrayList.
	 * @param hm HashMap
	 * @return ArrayList
	 */
	public static ArrayList<Reservation> sortReservations(HashMap<Integer, Reservation> hm)
	{
		//Makes a Collection.
		Collection<Reservation> c = hm.values();
		//Makes an ArrayList from that collection.
		ArrayList<Reservation> a = new ArrayList<Reservation>(c);
		Collections.sort(a);
		return a;
	}
	
	
	/**
	 * Sorts a HashMap of Customers. Returns an ArrayList.
	 * @param hm HashMap
	 * @return ArrayList
	 */
	public static ArrayList<Customer> sortCustomers(HashMap<Integer, Customer> hm)
	{
		//Makes a Collection.
		Collection<Customer> c = hm.values();
		//Makes an ArrayList from that collection.
		ArrayList<Customer> a = new ArrayList<Customer>(c);
		Collections.sort(a);
		return a;
	}
	
	/**
	 * Sorts a HashMap of VehicleTypes. Returns an ArrayList.
	 * @param hm; HashMap
	 * @return ArrayList
	 */
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
