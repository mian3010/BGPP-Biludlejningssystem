
package model;

/**
 * 
 * The basic class for modelling a customer. Contains information 
 * about the customer's id, name, phonenumber, address and bankaccount.
 * Its equipped with a constructor for defining the fields and basic
 * getter methods related to the different fields.
 * Also, this has a special toString() method to easily show in GUI's.
 * @author tbrj
 */
public class Customer implements Comparable<Customer>
{
    
    private int id;
    private String name;
    private int phonenumber;
    private String address;
    private String bankaccount;
    
    
    public Customer(int id, String name, int number, String address, String account)
    {
    	if(id < 0)
    		throw new IllegalArgumentException("Invalid id in Customer");
    	if(number < 0)
    		throw new IllegalArgumentException("Invalid phonenumber in customer");
    		this.name = name;
        	this.phonenumber = number;
        	this.address = address;
        	this.bankaccount = account;
        	this.id = id;
    }
    
    /** Returns the name of the customer.
     * @return String
     */
    public String getName()
    {
        return name;
    }
    
    /** Returns the number of the customer.
     * @return integer
     */
    public int getNumber()
    {
        return phonenumber;
    }
    
    /** Returns the customers address.
     * @return String
     */
    public String getAddress()
    {
        return address;
    }
    
    /** Returns the customers unique id.
     * @return id.
     */
    public int getId()
    {
        return id;
    }
    
    /** Returns the customers bankaccount.
     * @return String
     */
    public String getBankAccount()
    {
        return bankaccount;
    }
    
    /** Returns the customers name and phonenumber in a suitable way for a GUI.
     * @return String name + phonenumber
     */
    public String toString()
    {
     return name + ": " + phonenumber; 
    }
    
    /**
     * Compares name to the name of Customer c. Returns 1,0 or-1 if the name is less, even or more.
     * @param c Customer object
     * @return int
     */
    public int compareTo(Customer c)
    {
    	int i = name.compareTo(c.getName());
    	return i;
    }
    
}
