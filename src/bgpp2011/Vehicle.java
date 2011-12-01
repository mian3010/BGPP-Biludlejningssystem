
package bgpp2011;

/**
 *
 * @author tokejensen
 * Vehicleclass contains information about a car. Make, model, year....ect.
 * It contains a vehicleType object in a field. This object specifies the price
 * and the type.
 */
public class Vehicle implements Data {
     
    private int id;
    private VehicleType vehicletype;
    private String make;   
    private String model;
    private int year; 
   
    private boolean rentable;
    
    public Vehicle(int id, String make, String model, int year, VehicleType type)
    {
        vehicletype = type;
        this.make = make;
        this.model = model;
        this.year = year;
        this.id = id;
        rentable = true;
    }
    
    public VehicleType getType()
    {
        return vehicletype;
    }
    
    public String getMake()
    {
            return make;
    }
    
    public String getModel()
    {
        return model;
    }
    
    public int getYear()
    {
        return year;
    }
    
    public int getId()
    {
        return id;
    }
    
    public boolean getRentable()
    {
        return rentable;
    }
    
    public void setRentable(boolean b)
    {
        rentable = b;
    }

}
