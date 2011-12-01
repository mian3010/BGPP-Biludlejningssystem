/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bgpp2011;

/**
 *
 * @author tokejensen
 * This class contains a single field: Type, which contains a Types object. Types
 * is an enum class. The purpose of this class is to contain information about
 * what type a given vehicle is.
 */
public class VehicleType
{
    
    private int id;
    private String name;
    private double price;
    
    public VehicleType(int id, String name, double price)
    {
        this.id = id;
        this.name = name;
        this.price = price;
        
    }
    
    public String getName()
    {
     return name;
    }
    
    public double getPrice()
    {
        return price;
    }
    
    public int getId()
    {
        return id;
    }
}
