
package bgpp2011;

/**
 * 
 * The basic class for modelling a customer. Contains information 
 * about the customer's id, name, phonenumber, address and bankaccount.
 * Its equipped with a constructor for defining the fields and basic
 * getter methods related to the different fields.
 * Also, this has a special toString() method to easily show in GUI's.
 * @author tbrj
 */
public class Customer
{
    
    private int id;
    private String name;
    private int phonenumber;
    private String address;
    private String bankaccount;
    
    
    public Customer(int id, String name, int number, String address, String account)
    {
        this.name = name;
        this.phonenumber = number;
        this.address = address;
        this.bankaccount = account;
        this.id = id;
    }
    
    /* Returns the name of the customer.
     * @return String
     */
    public String getName()
    {
        return name;
    }
    
    /* Returns the number of the customer.
     * @return integer
     */
    public int getNumber()
    {
        return phonenumber;
    }
    
    /* Returns the customers address.
     * @return String
     */
    public String getAddress()
    {
        return address;
    }
    /* Returns the customers unique id.
     * @return id.
     */
    public int getId()
    {
        return id;
    }
    /* Returns the customers bankaccount.
     * @return String
     */
    public String getBankAccount()
    {
        return bankaccount;
    }
    /* Returns the customers name and phonenumber in a suitable way for a GUI.
     * @return String name + phonenumber
     */
    public String toString()
    {
     return name + ": " + phonenumber;
    }
}
