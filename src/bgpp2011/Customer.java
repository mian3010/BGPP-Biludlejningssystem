/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bgpp2011;

/**
 *
 * @author tokejensen
 * Contains information about a customer. Name, phonenumber.....ect.
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
    
    public String getName()
    {
        return name;
    }
    
    public int getNumber()
    {
        return phonenumber;
    }
    
    public String getAddress()
    {
        return address;
    }
    
    public int getId()
    {
        return id;
    }
    
    public String getBankAccount()
    {
        return bankaccount;
    }
}
