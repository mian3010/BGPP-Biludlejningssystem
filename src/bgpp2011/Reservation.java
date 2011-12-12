/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bgpp2011;


/**

 * 
 * This class contains information about a reservation. It contains two Date objects
 * as well as a Customer object and a Vehicle object. The purpose of this class 
 * is to contain all information about a reservation.
 * @author tokejensen.
 */
public class Reservation {
    
	private int id;
    private final Customer customer;
    private final Vehicle vehicle;
    private Date startdate;
    private Date enddate;
    
    /*
     * Standard constructor for the Reservation. Takes in the necessary parameters to create a valid
     * reservation for use in the program.
     * @param id, Object Customer, Object Vehicle, Object Datestart, Object Dateend.
     */
    public Reservation(int id, Customer customer, Vehicle vehicle, Date start, Date end)
    {
        this.customer = customer;
        this.vehicle = vehicle;
        this.startdate = start;
        this.enddate = end;
        this.id = id;
    }
    /*
     * Returns the Customer object linked to the reservation.
     * @return Customer
     */
    public Customer getCustomer()
    {
        return customer;
    }
    /*
     * Returns the Vehicle object referenced in the reservation.
     * @return Vehicle
     */
    public Vehicle getVehicle()
    {
        return vehicle;
    }
    /*
     * Return the information of the Start Date object as a String.
     * @return String
     */
    public String getStartdate()
    {
        return startdate.getDateString();
    }
    /*
     * Return the information of the End Date object as a String.
     * @return String
     */
    public String getEnddate()
    {
        return enddate.getDateString();
    }
    
    /*
     * Returns the unique id of the reservation.
     * @return id
     */
    public int getId()
    {
        return id;
    }
}
