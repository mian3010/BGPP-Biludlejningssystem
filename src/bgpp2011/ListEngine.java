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
    
    
    private ArrayList<VehicleTypes> types;
    private ArrayList<Vehicle> vehicle;
    private ArrayList<Customer> customer;
    private ArrayList<Reservation> reservation;
    
    public ListEngine(ResultSet t, ResultSet v, ResultSet c, ResultSet r)
    {
        makeListTypes(t);
        makeListVehicles(v);
        makeListCustomer(c);
        makeListReservation(r);
      
        }
    
    
    private ArrayList<VehicleTypes> makeListTypes(ResultSet r)
    {
        ArrayList<VehicleTypes> returnlist = new ArrayList<VehicleTypes>();
        try{     
        
          while(r.next())
          {
            VehicleTypes tmp = new VehicleTypes(r.getInt("id"),r.getString("name"),r.getDouble("price"));
                                              
            returnlist.add(tmp);
            r.next();
          } 
            types = returnlist;
            return returnlist;  
          }
         
          catch(SQLException e)
          {
            System.out.println("Non valid resultset");
            return returnlist;
          }
        
    }
    
    private ArrayList makeListVehicles(ResultSet r)
    {
         ArrayList returnlist = new ArrayList<Vehicle>();
          try{     
        
          while(r.next())
          {
            int id = r.getInt("typeId"); 
            Vehicle tmp = new Vehicle(r.getInt("id"), r.getString("make"),
                          r.getString("model"), r.getInt("year"),types.get(id));
                                              
            returnlist.add(tmp);
            r.next();
          } 
            vehicle = returnlist;
            return returnlist;  
          }
         
          catch(SQLException e)
          {
            System.out.println("Non valid resultset");
            return returnlist;
          }
    }
    
    private ArrayList makeListCustomer(ResultSet r)
    {
         ArrayList returnlist = new ArrayList<Customer>();
        try{     
        
          while(r.next())
          {
            Customer tmp = new Customer(r.getInt("id"),r.getString("name"),
                           r.getInt("phonenumber"), r.getString("address"),
                           r.getString("bankaccount"));
                                              
            returnlist.add(tmp);
            r.next();
          } 
            customer = returnlist;
            return returnlist;  
          }
         
          catch(SQLException e)
          {
            System.out.println("Non valid resultset");
            return returnlist;
          }
    }
    
    private ArrayList makeListReservation(ResultSet r)
    {
         ArrayList returnlist = new ArrayList<Reservation>();
        try{     
        
          while(r.next())
          {
            int cid = r.getInt("Customerid");
            int vid = r.getInt("VehicleId");
            Date startDate = new Date(r.getString("startdate"));
            Date endDate = new Date(r.getString("enddate"));
            Reservation tmp = new Reservation(r.getInt("id"),customer.get(cid), 
                              vehicle.get(vid), startDate, endDate);
                                              
            returnlist.add(tmp);
            r.next();
          } 
            reservation = returnlist;
            return returnlist;  
          }
         
          catch(SQLException e)
          {
            System.out.println("Non valid resultset");
            return returnlist;
          }
    }
    
    public ArrayList getTypes()
    {
        return types;
    }
    
    public ArrayList getVehicles()
    {
        
        return vehicle;
    }
    
    public ArrayList getCustomers()
    {
        return customer;
    }
    
    public ArrayList getReservations()
    {
        return reservation;
    }
}
