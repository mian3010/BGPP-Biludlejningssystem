package model;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;



import database.Nexus;

/*
 * This class models the data from the database and stores them into HashMaps.
 * This class is modeled after the Singleton pattern as it is better to only have
 * a single instance of the data from the database.
 * @author msta & tbrj
 */

public class Model {
	
	private HashMap<Integer, Vehicle> vehicles;
	private HashMap<Integer, VehicleType> types;
	private HashMap<Integer, Customer> customers;
	private HashMap<Integer, Reservation> reservations;
	private static Model instance;
	
	public static Model getInstance(Nexus n)
	{
		if(instance == null)
			instance = new Model(n);
		return instance;
	}
	
	private Model(Nexus n)
	{
		try {
		this.types = n.getTypes();
		this.vehicles = n.getVehicles();
		this.customers = n.getCostumers();
		this.reservations = n.getReservations();
		}
		catch(SQLException s)
		{
			System.out.println("Listholder construktor sql error.");
		}
	}
	
	//Returns a given reservation.
		public Reservation getReservation(int id)
		{
			return reservations.get(id);
		}
		
		//Returns a given customer.
		public Customer getCustomer(int id)
		{
			return customers.get(id);
			
		}
		
		//Returns a given vehicle.
		public Vehicle getVehicle(int id)
		{
			return vehicles.get(id);
			
		}
		
		//Returns a given vehicletype.
		public VehicleType getType(int id)

		{
			return types.get(id);
		}
		
		/*
		 * Accessor methods for the HashMaps.
		 */
		public HashMap<Integer, Reservation> getReservations()
		{
			return reservations;
		}
		
		public HashMap<Integer, Customer> getCustomers()
		{
			return customers;
		}
		
		public HashMap<Integer, Vehicle> getVehicles()
		{
			return vehicles;
		}
		
		public HashMap<Integer, VehicleType> getTypes()
		{
			return types;
		}
		
		//Adds an Item to the hashmap.
		public void add(int id, Object o)
		{
			//Checks if the item is an instance of a rservation, vehicle, vehicletype or customer.
			if(o instanceof Reservation)
				reservations.put(id,(Reservation)o);
			else if(o instanceof Vehicle)
				vehicles.put(id, (Vehicle)o);
			else if(o instanceof VehicleType)
				types.put(id, (VehicleType)o);
			else if(o instanceof Customer)	
				customers.put(id,(Customer)o);
			else
				throw new IllegalArgumentException("This class can't hold that kind of object");
			//Returns the item.
		
		}	
		
		//Deletes an item from the hashmaps.
		public Object delete(int id, Object o)
		{
			//Checks if Item is an instance of a reservation, vehicle, vehicletype or customer.
			if(o instanceof Reservation)
				return reservations.remove(id);
			else if(o instanceof Vehicle)
				return vehicles.remove(id);
			else if(o instanceof VehicleType)
				return types.remove(id);
			else if(o instanceof Customer)	
				return customers.remove(id);
			else
				throw new IllegalArgumentException("This class can't hold that kind of object");
			//Returns the item.
		}
		
		public Collection<Vehicle> vehicleCollection()
		{
			return vehicles.values();
		}
		
		public Collection<Reservation> reservationCollection()
		{
			return reservations.values();
		}
		public Collection<Customer> customerCollection()
		{
			return customers.values();
		}
		public Collection<VehicleType> typeCollection()
		{
			return types.values();
		}
		
		
}
