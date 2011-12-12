package bgpp2011;

import java.util.HashMap;

public class ListHolder {
	
	private HashMap<Integer, VehicleType> types;
	private HashMap<Integer, Vehicle> vehicles;
	private HashMap<Integer, Customer> customers;
	private HashMap<Integer, Reservation> reservations;
	
	public ListHolder(HashMap<Integer, VehicleType> types, HashMap<Integer, Vehicle> vehicles, HashMap<Integer, Customer> customers, HashMap<Integer, Reservation> reservations)
	{
		this.types = types;
		this.vehicles = vehicles;
		this.customers = customers;
		this.reservations = reservations;
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

}
