package testing;



import java.sql.SQLException;
import java.util.HashMap;

import model.Customer;
import model.Reservation;
import model.Vehicle;
import model.VehicleType;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import database.Nexus;


import static org.junit.Assert.*;

/**
 * This test method runs the Nexus lists and tests some sample data from it, to ensure that it works.
 * It's meant as whitebox-testing of the Nexus class's listmaking methods, including herein the 
 * Commands methods related to these.
 * @author Magnus Stahl
 */
public class TestNexusLists {
    
    public TestNexusLists() {
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
    
    
 
   //Test the HashMaps using assertEquals statements.
   @Test 
   public void TestAllNexusLists()
   {
   Nexus n = new Nexus();
   try {
   HashMap<Integer, Customer> cmap =  n.getCostumers();
   HashMap<Integer, VehicleType> vtmap =  n.getTypes();
   HashMap<Integer, Vehicle> vmap =  n.getVehicles();
   HashMap<Integer, Reservation> rmap =  n.getReservations();
	assertEquals(vmap.get(1).getMake(),"Suzuki");
	assertEquals(vmap.get(10).getModel(),"TT");
	assertEquals(vmap.get(5).getYear(),2007);
	assertEquals(cmap.get(1).getName(),"Roose Bolton");
	assertEquals(cmap.get(4).getNumber(),30534521);
	assertEquals(cmap.get(10).getAddress(),"Braavos");
	assertEquals(rmap.get(1).getVehicle().getMake(),"Suzuki");
//	assertEquals(rmap.get(4).getStartdate(),"2001-04-17");
	assertEquals(rmap.get(8).getCustomer().getName(),"Tyrion Lannister");
	assertEquals(vtmap.get(1).getId(),1);
	assertEquals(vtmap.get(4).getName(),"3-door");
	n.closeDatabase();  
   }
   catch(SQLException exn)
   {
	   System.out.println("The was a critical error in the testing of the sample data: " + exn);
   }

   }
  
   
	   
	   
   }

