/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bgpp2011;

import java.sql.ResultSet;
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
     */
    
              
    /*
     * A basic test for creating a vehicle. 
     * I have not yet created fully automatic tests,
     * these test require a look at the database tables to see whether
     * or not they were possible.
     * NB: It is also possible to receive SQL errors about 
     * the tables already existing. In such a case it could also be viewed as 
     * a success (the code works).
     */
   /* 
     @Test
     public void testCreateVehicle()
     {
         DataBaseCom db = new DataBaseCom();
        
         VehicleType t = new VehicleType(0, "hej", 200);
         Vehicle v1 = new Vehicle(0,"toyo", "lol", 1990, t);
         db.update(Commands.createVehicle(v1));
         db.close();
     }
     
     /*
      * Test for updating a vehicle with the id = 0.
      */
   /*
     @Test 
     public void testUpdateVeh()
     {
         DataBaseCom db = new DataBaseCom();
         
         VehicleType t = new VehicleType(0, "hej", 200);
         Vehicle v1 = new Vehicle(0,"toyotaaa", "lol", 1990, t);
         db.update(Commands.updateVehicle(v1));
         db.close();
     }
     */
    @Test
    public void testCreateRes()
    {
    	Nexus n = new Nexus();
    	Customer c = new Customer(1,"John Smith",4,"Lolroad","123234");
    	Date d1 = new Date("03022011");
    	Date d2 = new Date("03012999");
    	Vehicle v = new Vehicle(1, "toyota", "corolla", 1990, new VehicleType(1,"2dørs",345));
    	Reservation r = new Reservation(1,c,v,d1,d2);
    	Reservation r1 = n.createEntryReservation(r);
  
    	assertEquals(r1.getId(),1);
    	assertEquals(r1.getVehicle().getYear(), 1990);
    	assertEquals(c,r1.getCustomer());
    	
    }

     
   
}
