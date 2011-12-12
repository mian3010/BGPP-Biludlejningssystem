package bgpp2011;

import java.sql.SQLException;
import java.util.HashMap;

public class ListHolder {
	
	private HashMap<Integer, Vehicle> vehicles;
	private HashMap<Integer, VehicleType> types;
	private HashMap<Integer, Customer> customers;
	private HashMap<Integer, Reservation> reservations;
	
	public ListHolder(Nexus n)
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
			
				if(id < reservations.size() && id>0)
				{
				return reservations.get(id);
				
				}
				else 
				{
					throw new IllegalArgumentException("No reservations in that position!");
				}
			
				
		}
		
		//Returns a given customer.
		public Customer getCustomer(int id)
		{
			
				if(id < customers.size() && id>0)
				{
				return customers.get(id);
				
				}
				else 
				{
					throw new IllegalArgumentException("No reservations in that position!");
				}
			
		}
		
		//Returns a given vehicle.
		public Vehicle getVehicle(int id)
		{
			
				if(id < vehicles.size() && id>0)
				{
				return vehicles.get(id);
				
				}
				else 
				{
					throw new IllegalArgumentException("No reservations in that position!");
				}
			
		}
		
		//Returns a given vehicletype.
		public VehicleType getType(int id)

		{
			
				if(id < types.size() && id>0)
				{
				return types.get(id);
				
				}
				else 
				{
					throw new IllegalArgumentException("No reservations in that position!");
				}
			
			
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
		
		public void add(int id, Object o)
		{
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
		
		}	
		
		public void remove(int id, Object o)
		{
			if(o instanceof Reservation)
				reservations.remove(id);
			else if(o instanceof Vehicle)
				vehicles.remove(id);
			else if(o instanceof VehicleType)
				types.remove(id);
			else if(o instanceof Customer)	
				customers.remove(id);
			else
				throw new IllegalArgumentException("This class can't hold that kind of object");
		}
}
