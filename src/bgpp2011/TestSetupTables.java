package bgpp2011;



import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This method sets up all the tables without any data.
 * @author STAHL7
 */
public class TestSetupTables {
    
    public TestSetupTables() {
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
    @Test
    public void setupAllTables()
    {
        DataBaseCom db = new DataBaseCom();
        assertEquals(db.update("CREATE TABLE VehicleType (id INT PRIMARY KEY AUTO_INCREMENT,"
              + " name TEXT, price INT)"),true);    
        assertEquals(db.update("CREATE TABLE Vehicle (id INT PRIMARY KEY AUTO_INCREMENT, "
              + "make TEXT, "  + "model TEXT, year INT, typeID INT, "
              + "FOREIGN KEY (typeID)" + "REFERENCES VehicleType (id))"),true);
        assertEquals(db.update("CREATE TABLE Customer (id INT PRIMARY KEY AUTO_INCREMENT, "
              + "name TEXT," + "phone INT, address TEXT)"),true);
        assertEquals(db.update("CREATE TABLE Reservation (id INT PRIMARY KEY AUTO_INCREMENT,"
              + " customerID INT," + " vehicleID INT, startdate TEXT, "
              + "enddate TEXT, FOREIGN KEY" + "(customerID) REFERENCES "
              + "Customer (id), FOREIGN KEY" + "(vehicleID) "
              + "REFERENCES Vehicle (id))"),true);
        db.close();
    }    
   @Test
   public void setupSampleCustomers()
   {
	   Nexus n = new Nexus();
	   ArrayList<Customer> clist = new ArrayList<Customer>();
	   Customer c1 = new Customer(1, "John Mogensen", 43982342,"Mogensvej","4234890-4324");
	   Customer c2 = new Customer(2, "Magnus Stahl", 34235656,"Skodsborggade", "32543634-421");
	   Customer c3 = new Customer(3, "Povl Dissing", 34234123, "Lungevej", "3124345-2312");
	   Customer c4 = new Customer(4, "Kim Larsen", 30534521, "Cancer Allé", "45345345-21311");
	   Customer c5 = new Customer(5, "Michael Søby-Andersen", 23982749, "Allerød", "42342-232");
	   Customer c6 = new Customer(6, "Toke Loke Bruno", 2342348, "Rådmandsgade", "123124-42545");
	   Customer c7 = new Customer(7, "Kjeld Ingrisch", 423498, "SlagerHuset", "4234234-3123");
	   Customer c8 = new Customer(8, "Helmut Lotti", 472394, "LegendaryRoad", "534523-1231");
	   Customer c9 = new Customer(9, "Michael Jackson", 234234, "6-foot-underRoad", "432434-2312");
	   Customer c10 = new Customer(10, "Arya Stark", 234234, "Winterfell", "3123543-8674");
	   clist.add(c1);
	   clist.add(c2);
	   clist.add(c3);
	   clist.add(c4);
	   clist.add(c5);
	   clist.add(c6);
	   clist.add(c6);
	   clist.add(c7);
	   clist.add(c8);
	   clist.add(c9);
	   clist.add(c10);
	   for(Customer c : clist)
	   {
		   n.createEntryCustomer(c);
		
	   }
	   System.out.println("No exceptions so far? All customers created");
	   VehicleType vt1 = new VehicleType(0,"Motorcykel",700);
	   VehicleType vt2 = new VehicleType(1,"4-dørs",550);
	   VehicleType vt3 = new VehicleType(2,"5-dørs",650);
	   VehicleType vt4 = new VehicleType(3,"3-dørs",450);
	   VehicleType vt5 = new VehicleType(4,"2-dørs COUPE",7000);
	   ArrayList<VehicleType> vtlist = new ArrayList<VehicleType>();
	   vtlist.add(vt1);
	   vtlist.add(vt2);
	   vtlist.add(vt3);
	   vtlist.add(vt4);
	   vtlist.add(vt5);
	   for(VehicleType vt : vtlist)
	   {
		   n.createEntryVehicleType(vt);
	   }
	   System.out.println("No exceptions so far? All vehicletypes created");
   
	   
   
   
   }
   
}
