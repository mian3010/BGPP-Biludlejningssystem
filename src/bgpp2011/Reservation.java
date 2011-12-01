/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bgpp2011;


/**
 *
 * @author tokejensen
 * This class contains information about a reservation. It contains two Date objects
 * as well as a Customer object and a Vehicle object. The purpose of this class 
 * is to contain all information about a reservation.
 
 */
public class Reservation{
    
    private int id;
    private final Customer customer;
    private final Vehicle vehicle;
    private Date startdate;
    private Date enddate;
    
    public Reservation(int id, Customer customer, Vehicle vehicle, Date start, Date end)
    {
        this.customer = customer;
        this.vehicle = vehicle;
        this.startdate = start;
        this.enddate = end;
        this.id = id;
    }
    
    public Customer getCustomer()
    {
        return customer;
    }
    
    public Vehicle getVehicle()
    {
        return vehicle;
    }
    
    public String getStartdate()
    {
        return startdate.getDateString();
    }
    
    public String getEnddate()
    {
        return enddate.getDateString();
    }
    
    public int getId()
    {
        return id;
    }
}
