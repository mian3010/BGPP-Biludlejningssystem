
package bgpp2011;

/**
 * The class used to represent groups of vehicles, here known as VehicleTypes.
 * This class contains a unique id for the type of vehicle, then a name to describe it
 * and a decimal number containing the supposed price per day.
 * @author Toke Jensen
 */
public class VehicleType implements Comparable<VehicleType>
{
    
	private int id;
    private String name;
    private double price;
    
    /*
     * Basic constructor of the VehicleType. Takes in a unique id along with a name and a price.
     * @param id Integer containing the id
     * @param name String containing the name
     * @param price double containing the price pr. day for renting the vehicle
     */
    public VehicleType(int id, String name, double price)
    {
        
        this.name = name;
        this.price = price;
        this.id = id;
        
    }
    /*
     * This method returns the name of the VehicleType
     * @return String containing the name.
     */
    public String getName()
    {
     return name;
    }
    /*
     * This method returns the name of the vehicletype. However, it uses
     * a method signature that's convenient for working with GUI's.
     * @return String containing the name
     */
    public String toString()
    {
    	return getName();
    }
	/* 
	 * This method returns the value of the price field.
	 * @return double containing the price field.
	 */
    public double getPrice()
    {
        return price;
    }
    /*
     * This method returns the value of the id field.
     * @return int containing the id.
     */
    public int getId()
    {
        return id;
    }
    
    public int compareTo(VehicleType vt)
    {
    	int i = name.compareTo(vt.getName());
    	return i;
    }
}
