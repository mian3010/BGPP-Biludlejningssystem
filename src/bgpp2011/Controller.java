
package bgpp2011;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.sql.Date;

import model.Customer;
import model.Model;
import model.Reservation;
import model.Vehicle;
import model.VehicleType;

import database.Nexus;

/**
 * The controller class is the class that does all the checks and calculations requested by the GUI.
 * It is the class that makes the calls for changes in the database, and the class that holds both
 * the Model and the Nexus. 
 * @author tbrj
 *
 */

public class Controller
{
	private Nexus nexus;
	private Model model;

	
	public Controller()
	{
		
			nexus = new Nexus();	
			model = Model.getInstance(nexus);
			
		
	}
	//Method that loads the HashMaps with data from the database. Uses the getMethods() from the nexus.
	
	/**
	 *
	 * This method check if a reservation overlaps another reservation. It compares the dates of the
	 * reservation given in the parameter with the dates from all reservations in the HashMap reservations.
	 * It uses the String-types compare method to check if the dates are overlapping.
	 *
	 */
	
	/**
	 *  This method check if a reservation overlaps another reservation. It compares the dates of the
	 * reservation given in the parameter with the dates from all reservations in the HashMap reservations.
	 * It uses the String-types compare method to check if the dates are overlapping.
	 * @param re
	 * @return boolean; returns true if the reservation in not overlapping any reservations in the
	 * collection: reservations.
	 */
	public boolean checkReservation(Reservation re)
	{
		//Checks if the startdate is before the enddate.
		int o = re.getDateStart().compareTo(re.getDateEnd());
		if( o<=0 )
		{
		
			Collection<Reservation> c = model.reservationCollection();
			Iterator<Reservation> i = c.iterator();
		
			while(i.hasNext())
			{
				Reservation ra = i.next();
			
				Vehicle vt = ra.getVehicle(); 
				//Checks if the the vehicle re is the same as the vehicle ra.
			
				if(vt.getId() == re.getVehicle().getId()) 
				{
					//Checks if the stardate of re is after the enddate of ra.
		
					int startValue = re.getDateStart().compareTo(ra.getDateEnd());
					//Checks if the enddate of re is before the startdate of ra.
				
					int endValue = re.getDateEnd().compareTo(ra.getDateStart());
		
					//If this if-statement is true, the dates are overlapping. Returns false.
						if(startValue < 0 && endValue > 0)
						{
							
							return false;
			  
						}
			    	
					}	
				}
		     //If the code reaches this point. The reservation is possible and it will return true..
			  return true;
         } 
		//If the code reaches this, then the startdate is before the enddate, and therefore illegal.
		return false;
	}	
	
	
	/**
	 *This code returns an ArrayList of vehicles grouped by the type they belong to,
	 *which is specified in the parameter.
	 *@param v a the VehicleType which selects the vehicles
	 *@return ArrayList<Vehicle> an ArrayList containing the vehicles grouped by type
	 */
	public ArrayList<Vehicle> vehiclesByType(VehicleType v)
	{
		ArrayList<Vehicle> vlist = new ArrayList<Vehicle>();
		Collection<Vehicle> vehiclec = model.vehicleCollection();
		Iterator<Vehicle> it = vehiclec.iterator();
		while(it.hasNext())
		{  
		Vehicle v1 = it.next();
		int id = v1.getType().getId();
		   if(id == v.getId())
		   	{
			   
			   vlist.add(v1);
		   	}   
		}
		return vlist;
	}
	
	/**
	 * This method is used when counting the number of vehicles avalaible in a certain date-range.
	 * @param v the VehicleType of which the vehicles should be counted
	 * @param start the Date object containing information about the Start of the range
	 * @param end the Date object containing information about the End of the date-range.
	 * @return int an Integer counting the number of free vehicles in the type.
	 */
	public int typeCounting(VehicleType v, Date start, Date end)
	{
		ArrayList<Vehicle> vlist = vacantVehicles(v,start,end);
		if(vlist == null)
			return 0;
		return vlist.size();
	}
	
	/**
	 * This method is used to fetch an arraylist of vehicles containing vehicles
	 * which are free to rent in the specified daterange, grouped by type.
	 * @param v the VehicleType of which the vehicles should be selected.
	 * @param start the Date object containing information about the Start of the range
	 * @param end the Date object containing information about the End of the date-range.
	 * @return ArrayList<Vehicle> a list containing the avalaible vehicles.
	 */
	public ArrayList<Vehicle> vacantVehicles(VehicleType v, Date start, Date end)
	{
		
		ArrayList<Vehicle> tmp = vehiclesByType(v);
		ArrayList<Vehicle> tmp1 = new ArrayList<Vehicle>();
		/*The for-each-loop creates a reservation containing the same VehicleType, start and enddate
		 * as given in the parameters. 
		 */
		
		for(Vehicle va : tmp)
		{
			Customer tmpCustomer = new Customer(0,"tmp", 1, "tmp", "tmp");
			Reservation res = new Reservation(0 ,tmpCustomer, va, start, end);
			//It runs the check reservation method on the Reservation res.
				if(checkReservation(res)) 
				{
					
					tmp1.add(va);
				}
	    }
			if(tmp1.size() == 0)
				return null;
			else
				return tmp1; // Returns the first vehicle that was added to the map
}
	
	/**
	 * Makes a list of free vehicles using the vacantVehicles()-method. Returns the first vehicle in
	 * that list. Else it returns null.
	 * @param v a VehivleType object
	 * @param start A Date object containing the startdate
	 * @param end a Date object containing the eddate
	 * @return Vehivle returns a vehicle of the type v, that is free between the start and enddate.
	 * 		   If there is no such car, it returns null.	
	 */
	public Vehicle searchVehicles(VehicleType v, Date start, Date end)
	{
			ArrayList<Vehicle> vlist = vacantVehicles(v,start,end);
				if(vlist!=null)
					return vlist.get(0);
				else 
					return null;
	}

	
	 /**
	  * Creates a reservation. It checks if there is a free vehicle of
	  * the type t, between the start and end Date. If there is, it sends the reservation to the
	  * database, and gets the reservation back with a valid id. Then it returns the reservation.
	  * @param c a Customer object
	  * @param t a VehivleType Object
	  * @param start a Date object
	  * @param end a Date object
	  * @return Reservation object returns null if creation was unsuccesful.
	  */
	public Reservation createReservation(Customer c, VehicleType t, Date start, Date end)
	{
		
		//Search for vehicle method to see if a vehicle is avaliable.
		try
		{
			if(searchVehicles(t, start, end) == null)
			{
			 		return null;
			}
			else 
			{
				
				
					/*If a car is available it creates a temporary reservation and calls the
					 * createEntryReservation() method on it. 
					 */
					Vehicle v = searchVehicles(t, start, end);
					Reservation r = new Reservation(0,c,v,start,end);
					Reservation re = nexus.createEntryReservation(r);
					//If it doesnt return null, the reservation has been registered by the database.
					if(re != null)
					{
					//The reservation is added to the HashMap reservations. It returns the vehicle.
					model.add(re.getId(), re);
					return re;
					}
				
				
			}
					//If the code reaches this point, the reservation has not been successfully registered.
					return null;
		}
		catch(SQLException e)
		{
			return null;
		}
	}
	
	/**
	 * Creates a new customer. It checks if a customer with the same name and phonenumber
	 * is already in the system. If he is not, the system will send the customer to the database, and get
	 * the same customer with a valid id back.
	 * @param name String
	 * @param phonenumber int
	 * @param address String
	 * @param bankaccount String
	 * @return Customer object returns null if creations was unsuccessful.
	 */
	public Customer createCustomer(String name, int phonenumber, String address, String bankaccount)
	{
		//Creates a temporary customer with the parameters given in the constructor.
		Customer c = new Customer(0, name, phonenumber, address, bankaccount);
		Collection<Customer> customerC = model.getCustomers().values();
		Iterator<Customer> itt = customerC.iterator();
		//Runs through the customer HashMap and checks if there is a customer with same name and number.
			while(itt.hasNext())
			{
				Customer c1 = itt.next();
				if(c1.getName().equals(c.getName()) && c1.getNumber() == c.getNumber())
				
				{
					
					return null;
				}
			}
			//Creates a customer using the createEntryCustomer() in the nexus.
				try {
					Customer returnC = nexus.createEntryCustomer(c);
					model.add(returnC.getId(), returnC);
					//Returns the customer.
					return returnC;
				    }
				catch(Exception e)
					{
					//If the code reaches this point, there has been an error in the database.
					return null;
					}				
	}
	/**
	 * Creates a new vehicle. Checks if v is a valid VehivleType. If it is, it sends the vehicle to
	 * the database, which returns the vehicle with a valid id. 
	 * @param make String
	 * @param vmodel String
	 * @param year int
	 * @param v VehicleType object
	 * @return Vehicle object null if v is not a valid VehicleType.
	 */
	public Vehicle createVehicle(String make, String vmodel, int year, VehicleType v)
	{
		//Creates a temporary vehicle with the parameters of the constructor.
		Vehicle ve = new Vehicle(0, make, vmodel, year, v);		
		Collection<VehicleType> vehicleTypes = model.getTypes().values();
		Iterator<VehicleType> itt = vehicleTypes.iterator();
		boolean typeExists = false;
			//The while loop runs through the types HashMap. It checks if the given type exists.
			while(itt.hasNext())
			{
				if(ve.getType().getId() == itt.next().getId())
				{
					typeExists = true;
				}
			//If the type does not exist, it returns null.
			}
			if (!typeExists)
			{
				return null;
			}
			//If the type exist, it creates the vehicle, puts it in the HashMap and returns it.
			try {
				Vehicle returnV = nexus.createEntryVehicle(ve);
				model.add(returnV.getId(), ve);
				return returnV;
				}
			catch(Exception e) {
				//If the code reaches this point there has been an error in the database.
				return null;
				}
		
	}
	/**
	 * Creates a new VehicleType. Checks if a type with the same name already exists. If not,
	 * it sends the type to the database, which sends it back with a valid id.
	 * @param name String
	 * @param price double
	 * @return boolean false if the name already exists.
	 */
	public boolean createVehicleType(String name, double price)
	{
		//Creates a temporary vehicleType with the parameters.
		VehicleType vee = new VehicleType(0, name, price);
		Collection<VehicleType> c = model.typeCollection(); 
		Iterator<VehicleType> itt = c.iterator();
		//Checks of a type with the same name does exist. 
			while(itt.hasNext())
			{
				if(vee.getName() == itt.next().getName())
				{
					return false;
				}
				
			}
			//If the name dies not exist already, it creates the type, puts it in the map and returns it.
			try{
				VehicleType returnT = nexus.createEntryVehicleType(vee);
				
				model.add(returnT.getId(), returnT);
				return true;
				}
			catch(Exception e)
			//If the code reaches this point, there has been an error in the database.
			{
				return false;
			}
	}
	
	/**
	 * Deletes a Reservation. It deletes a reservation from the database. Returns true of the deletion
	 * was successful, else it returns false.
	 * @param r Reservation object.
	 * @return boolean
	 */
	public boolean deleteReservation(Reservation r)

	{
		try {
			//Calls the delete() method from Model. Cast the class to be a Reservation.
			Reservation rescheck = (Reservation)model.delete(r.getId(), r);
			//Checks if it i not null.
			if(rescheck != null)
				return nexus.deleteReservation(rescheck);
			else
				return false;
			}
			catch(SQLException e)
			{
				return false;
			}
	
		
	}
	/**
	 * Deletes a Customer. If the deletion was successful, it runs trough all reservations, and
	 * deletes the reservations made by the deleted customer. Returns true if deletion was success.
	 * @param c Customer object
	 * @return boolean
	 */
	public boolean deleteCustomer(Customer c)

	{
		try
		{
			//Calls delete from model. It returns a Customer.
			Customer cuscheck = (Customer)model.delete(c.getId(), c);
			boolean boo = false;
			//If cuscheck-customer is not null.
			if(cuscheck != null)
			{
			//Nexus deletes the customer from the database. Returns true if successfull.
				boo = nexus.deleteCustomer(cuscheck);
			//If database-delete was successful.
				if(boo == true)
				{
					//Runs through every reservation
				Collection<Reservation> co = model.reservationCollection();
				ArrayList<Reservation> a = new ArrayList<Reservation>();
					for(Reservation r : co)
					{
						//Adds all Reservations with the same customerIs as c to an ArrayList.
						if(r.getCustomer().getId() == c.getId())
						{
							a.add(r);
						}
					}
					
						//Deletes all the reservations in the ArrayList a.
					for(Reservation re : a)
					{
						deleteReservation(re);
					}
					return true;
				}
				return false;
			}
				
			else 
			{
				return false;
			}
		}
		
		catch(SQLException e)
		{
				return false;
		}
	}
	
	/**
	 * Deletes a vehicle. If deletion was successful, it runs through all reservations, and deletes
	 * the reservations of the deleted vehicle. Returns true if deletion was successful.
	 * @param v Vehivle object
	 * @return boolean
	 */
	public boolean deleteVehicle(Vehicle v)

	{
		try 
		{
			//Deletes a customer from the model. Model returns a vehicle.
			Vehicle vcheck = (Vehicle)model.delete(v.getId(), v);
			boolean boo = false;
			//If vcheck vehicle is not null.
			if(vcheck != null)
			{
				//Nexus deletes the vehicle from the database.
				boo = nexus.deleteVehicle(vcheck);
				//If database delete is successful.
				if(boo == true)
				{
					//Runs through all reservations. 
					Collection<Reservation> co = model.reservationCollection();
					ArrayList<Reservation> a = new ArrayList<Reservation>();
					for(Reservation r : co)
					{
						//Adds all reservations with the same VehicleId as v to the ArrayList a.
						if(r.getVehicle().getId() == v.getId())
						{
							a.add(r);
						}
						
					}
					//Runs through the ArrayList a and deletes all the reservations in the ArrayList.
					for(Reservation re : a)
					{
						deleteReservation(re);
					}
					return true;
				}
				return false;
			}
			
			else
			{
				return false;
			}
		}
		
		catch(SQLException e)
		{
				return false;
		}
		}
	/**
	 * Deletes a VehicleType. Returns true of deletion was successful.
	 * @param vt VehicleType object
	 * @return boolean
	 */
	public boolean deleteVehicleType(VehicleType vt)
	{
		try
		{
			VehicleType vtcheck = (VehicleType)model.delete(vt.getId(), vt);
			if(vtcheck != null)
				return nexus.deleteVehicleType(vtcheck);
			else
				return false;
		}
		
		catch(SQLException e)
		{
				return false;
		}
	}
	
	/**
	 * Edits a Reservation. Takes the id from the oldR and attaches it to the newR. Returns true
	 * if edition was successful.
	 * @param newR Reservation Object
	 * @param oldR Reservation object
	 * @return boolean
	 */
	public boolean editReservation(Reservation newR, Reservation oldR)
	{
		try
		{
			//Runs the checkReservation on newR to see if the reservation is possible.
			// Removes the old reservation to allow a check of all reservations % the old one.
			
			int id = oldR.getId();
			model.getReservations().remove(id);
			if(checkReservation(newR))
			{
				//Creates a new reservation and with the data from newR and id from oldR.
				Reservation res = new Reservation(id, newR.getCustomer(), newR.getVehicle(), Date.valueOf(newR.getStartdate()), Date.valueOf(newR.getEnddate()));			
				if(nexus.editReservation(res))
				{
					// model.getReservations().remove(id);
					model.getReservations().put(id,res);
					return true;
			}		
				// In case there was an error with the database insertion, returns the old reservation to the maps.
					model.getReservations().put(id,oldR);
					return false;
		 }
			// In case the new reservation cannot be edited at all, preserves the old reservation.
			model.getReservations().put(id, oldR);
		    return false;
		}
		
		catch(SQLException e)
		{
			return false;
		}
	}
	
	/**
	 * Edits a vehicle. Takes the id from oldV and attaches it to newV. Returns true if edition
	 * was successful.
	 * @param newV Vehicle object
	 * @param oldV Vehicle object
	 * @return boolean
	 */
	public boolean editVehicle(Vehicle newV, Vehicle oldV)
	{
		
		try
		{
			int id = oldV.getId();
			//Creates a new vehicle with the data from newV and id from oldV.
			Vehicle v = new Vehicle(id, newV.getMake(), newV.getModel(), newV.getYear(), newV.getType());
			if(nexus.editVehicle(v))
			{
			model.getVehicles().remove(id);
			model.getVehicles().put(id,v);
			return true;
			}
			return false;
		}
		catch(SQLException e)
		{
			return false;
		}
		 
	}
	
	/**
	 * Edits a VehicleType. Takes the id from vtold and attaches it to vtnew. Returns true if edition
	 * was successful.
	 * @param vtnew VehicleType object
	 * @param vtold VehivleType object
	 * @return boolean
	 */
	public boolean editVehicleType(VehicleType vtnew, VehicleType vtold)
	{
		
		try
		{
		    int id = vtold.getId();
		    //Creates a new type with the data from vtnew and id from vtold.
			VehicleType vt = new VehicleType(id, vtnew.getName(), vtnew.getPrice());
			if(nexus.editVehicleType(vt))
			{
				model.getTypes().remove(id);
				model.getTypes().put(id,vt);
				return true;
			}
			return false;
		}
		catch(SQLException e)
		{
			return false;
		}
	}
	
	/**
	 * Edits a Customer. Takes the id of oldC and attaches it to newC. Returns true if edition was
	 * successful.
	 * @param newC Customer object
	 * @param oldC Customer object
	 * @return boolean
	 */
	public boolean editCustomer(Customer newC, Customer oldC)
	{
		try
		{
			int id = oldC.getId();
			Customer c = new Customer(id, newC.getName(), newC.getNumber(), newC.getAddress(), newC.getBankAccount());
			if(nexus.editCustomer(c))
			{
				model.getCustomers().remove(id);
				model.getCustomers().put(id, c);
				return true;
			}
				return false;
		}
		catch(SQLException e)
		{
			return false;
		}
	}

	
	/**
	 * Returns the model field.
	 * @return Model object
	 */
	public Model getModel()
	{
		return model;
	}
	
	/**
	 * Closes the connection to the database.
	 */
	public void close()
	{
		nexus.closeDatabase();
	}
	
}
