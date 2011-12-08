
package bgpp2011;

/**
 * Vehicleclass contains information about a car. Make, model, year....ect.
 * It contains a vehicleType object in a field. This object specifies the price
 * and the type.
 * @author Toke Jensen
 */
public class Vehicle
{
     
    private int id;
    private VehicleType vehicletype;
    private String make;   
    private String model;
    private int year; 
   

    /*
     * Basic constructor for the vehicle. Takes in an in, the make and model of the vehicle,
     * the year in which it was constructed and a VehicleType object that contains further information
     * about the class of vehicle.
     */
    public Vehicle(int id, String make, String model, int year, VehicleType type)
    {
        vehicletype = type;
        this.make = make;
        this.model = model;
        this.year = year;
        this.id = id;
        
    }
    /*
     * Returns the VehicleType object referenced in the vehicle.
     * @return VehicleType
     */
    public VehicleType getType()
    {
        return vehicletype;
    }
    /*
     * Returns the make of the Vehicle.
     * @return String
     */
    public String getMake()
    {
            return make;
    }
    /*
     * Return the model of the vehicle.
     * @return String
     */
    public String getModel()
    {
        return model;
    }
    /*
     * Returns the made year of the vehicle.
     * @return int containing the year.
     */
    public int getYear()
    {
        return year;
    }
    /*
     * Returns the unique id of the vehicle.
     * @return integer containing the id.
     */
    public int getId()
    {
        return id;
    }
    /*
     * Returns information about the vehicle in a way suitable for a GUI.
     * @return String with the VehicleType's information along with year, make and model.
     */
    public String toString()
    {
     return vehicletype.toString() + ": " + year + " " + make + " " + model;
    }
    


}
