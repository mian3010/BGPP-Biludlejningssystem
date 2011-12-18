package model;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;



import database.Nexus;

/**
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
	
	/**
	 * Returns the model. This method ensures that the model is a singleton. It only creates a new
	 * Model if the model does NOT already exist.
	 * @param n Nexus object
	 * @return Model object
	 */
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
	
	/**
	 * Returns a Reservation.
	 * @param id int
	 * @return Reservation
	 */
		public Reservation getReservation(int id)
		{
			return reservations.get(id);
		}
		
		/**
		 * Returns a Customer.
		 * @param id; int
		 * @return Customer;
		 */
		public Customer getCustomer(int id)
		{
			return customers.get(id);
			
		}
		
		/**
		 * Returns a Vehicle.
		 * @param id int
		 * @return Vehicle
		 */
		public Vehicle getVehicle(int id)
		{
			return vehicles.get(id);
			
		}
		
		/**
		 * Returns a VehivleType.
		 * @param id int
		 * @return VehicleType
		 */
		public VehicleType getType(int id)

		{
			return types.get(id);
		}
		
		/**
		 * Returns the reservations HashMap.
		 * @return HashMap;
		 */
		public HashMap<Integer, Reservation> getReservations()
		{
			return reservations;
		}
		
		/**
		 * Returns the customers HashMap.
		 * @return HashMap;
		 */
		public HashMap<Integer, Customer> getCustomers()
		{
			return customers;
		}
		
		/**
		 * Returns the vehicles HashMap.
		 * @return HashMap;
		 */
		public HashMap<Integer, Vehicle> getVehicles()
		{
			return vehicles;
		}
		
		/**
		 * Returns the types HashMap.
		 * @return HashMap;
		 */
		public HashMap<Integer, VehicleType> getTypes()
		{
			return types;
		}
		
		/**
		 * Adds an object to a HashMap. Uses the instanceOf statement to determine which HashMap to
		 * add the object to.
		 * @param id int
		 * @param o Object
		 */
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
		
		/**
		 * Deletes an Object from a HashMap. Uses the instanceOf statement to determine which HashMap
		 * to delete the Object from.
		 * @param id int
		 * @param o Object
		 * @return Object The deleted object
		 */
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
		
		/**
		 * Returns the vehicles-HashMap as a Collection.
		 * @return Collection<Vehiles>
		 */
		public Collection<Vehicle> vehicleCollection()
		{
			return vehicles.values();
		}
		
		/**
		 * Returns the reservations-HashMap as a Collection.
		 * @return Collection<Resrevation>;
		 */
		public Collection<Reservation> reservationCollection()
		{
			return reservations.values();
		}
		
		/**
		 * Returns the customers-HashMap as a Collection.
		 * @return Collection<Customer>;
		 */
		public Collection<Customer> customerCollection()
		{
			return customers.values();
		}
		
		/**
		 * Returns the types-HashMap as a Collection.
		 * @return Collection<VehivleType>;
		 */
		public Collection<VehicleType> typeCollection()
		{
			return types.values();
		}
		
		
}
