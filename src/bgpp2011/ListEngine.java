/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bgpp2011;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 *
 * @author tokejensen
 * The list engine takes a resultset as an argument in its construktor. It has a method
 * makeList(). That takes the resulset as an argument and returns an ArrayList with the data of the resultset.
 * The responsobility of this class is to be a bridge between the SQL data from the
 * database, and the javacode of the GUI.
 * 
 */
public class ListEngine
{
    
    
    private ArrayList<VehicleType> types;
    private ArrayList<Vehicle> vehicle;
    private ArrayList<Customer> customer;
    private ArrayList<Reservation> reservation;
    
    public ListEngine()
    {
      
        }
    
    
  
  
   
    
    public ArrayList<VehicleType> getTypes()
    {
        return types;
    }
    
    public ArrayList<Vehicle> getVehicles()
    {
        
        return vehicle;
    }
    
    public ArrayList<Customer> getCustomers()
    {
        return customer;
    }
    
    public ArrayList<Reservation> getReservations()
    {
        return reservation;
    }
}
