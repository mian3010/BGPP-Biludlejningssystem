/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bgpp2011;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Collection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 * This method
 * @author STAHL7
 */
public class TestNexus {
    
    public TestNexus() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    /*
     * Major test method for Nexus. Tests alot of different commands
     * including the connection etc.
     * All the print statements are by default a success,
     * the code is written so as exceptions will be thrown in case of 
     * errors.
     * @author Magnus Stahl
     */
    
    /*
     * The following methods does not work without some sample data. Or in fact they do,
     * but they wont really show anything.
     * The following methods are designed to test the Commands.get*** methods,
     * the Nexus HashMap retrievers and creaters from the database.
     * And some basic iteration to check the sample data is ok.
     */
    @Test
    public void TestCustomerLists()
    {
    	Nexus n = new Nexus();
    	HashMap<Integer, Customer> cmap =  n.getCostumers();
    	Collection<Customer> co = cmap.values();
    	Iterator<Customer> itr = co.iterator();
    	while(itr.hasNext())
    	{
    		Customer c = itr.next();
    		System.out.println(c.getId() + "--" + c.getName() + "--" + c.getAddress() + "\n");
    	}
    	n.closeDatabase();
    
              
    	
    }
    @Test
    public void TestVehicleTypeList()
    {
    	Nexus n = new Nexus();
    	HashMap<Integer, VehicleType> cmap =  n.getTypes();
    	Collection<VehicleType> co = cmap.values();
    	Iterator<VehicleType> itr = co.iterator();
    	while(itr.hasNext())
    	{
    		VehicleType v = itr.next();
    		System.out.println(v.getId() + "--" + v.getName() + "--" + v.getPrice() + "\n");
    	}
    	n.closeDatabase();
    
    }
    @Test 
    
    public void TestVehicleList()
    {
    Nexus n = new Nexus();
	HashMap<Integer, Vehicle> vmap =  n.getVehicles();
	Collection<Vehicle> co = vmap.values();
	Iterator<Vehicle> itr = co.iterator();
	while(itr.hasNext())
	{
		Vehicle v = itr.next();
		System.out.println(v.getId() + "--" + v.getMake() + "--" + v.getModel() + "\n");
	}
	n.closeDatabase();
    }
    @Test 
    
    public void TestReservationList()
    {
    Nexus n = new Nexus();
	HashMap<Integer, Reservation> vmap =  n.getReservations();
	Collection<Reservation> co = vmap.values();
	Iterator<Reservation> itr = co.iterator();
	while(itr.hasNext())
	{
		Reservation v = itr.next();
		System.out.println(v.getId() + "--" + v.getCustomer().getName() + "--" + v.getStartdate() + "\n");
	}
	n.closeDatabase();
    }
}
