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
    public static void setupAllTables()
    {
        DataBaseCom db = new DataBaseCom();
        assertEquals(db.update("CREATE TABLE VehicleType (id INT PRIMARY KEY AUTO_INCREMENT,"
              + "price INT, name TEXT)"),true);    
        assertEquals(db.update("CREATE TABLE Vehicle (id INT PRIMARY KEY AUTO_INCREMENT, "
              + "make TEXT, "  + "model TEXT, year INT, typeID INT, "
              + "FOREIGN KEY (typeID)" + "REFERENCES VehicleType (id))"),true);
        assertEquals(db.update("CREATE TABLE Customer (id INT PRIMARY KEY AUTO_INCREMENT, "
              + "name TEXT," + "phonenumber INT, address TEXT, bankaccount TEXT)"),true);
        assertEquals(db.update("CREATE TABLE Reservation (id INT PRIMARY KEY AUTO_INCREMENT,"
              + " customerID INT," + " vehicleID INT, startdate TEXT, "
              + "enddate TEXT, FOREIGN KEY" + "(customerID) REFERENCES "
              + "Customer (id), FOREIGN KEY" + "(vehicleID) "
              + "REFERENCES Vehicle (id))"),true);
        db.close();
    }    
   @Test
   public static void setupSampleCustomers()
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
	  
	   clist.add(c7);
	   clist.add(c8);
	   clist.add(c9);
	   clist.add(c10);
	   for(Customer c : clist)
	   {
		   n.createEntryCustomer(c);
		
	   }
	   System.out.println("No exceptions so far? All customers created");
	   VehicleType vt1 = new VehicleType(1,"Motorcykel",700);
	   VehicleType vt2 = new VehicleType(2,"4-dørs",550);
	   VehicleType vt3 = new VehicleType(3,"5-dørs",650);
	   VehicleType vt4 = new VehicleType(4,"3-dørs",450);
	   VehicleType vt5 = new VehicleType(5,"2-dørs COUPE",7000);
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
	   Vehicle v1 = new Vehicle(1,"Suzuki","1000ccm",1990,vt1);
	   Vehicle v2 = new Vehicle(2,"Harley Davidson","Outlaw",1975,vt1);
	   Vehicle v3 = new Vehicle(3,"BMW","MichaelsBimmer",3333,vt2);
	   Vehicle v4 = new Vehicle(4,"Audi","A4",2001,vt2);
	   Vehicle v5 = new Vehicle(5,"Ford","Oscars Lortebil",1903,vt2);
	   Vehicle v6 = new Vehicle(6,"Porsche","Cayenne",2011,vt3);
	   Vehicle v7 = new Vehicle(7,"Audi","Q9",2005,vt3);
	   Vehicle v8 = new Vehicle(8,"Suzuki","Swift",2003,vt4);
	   Vehicle v9 = new Vehicle(9,"Kia","Caravan",1999,vt4);
	   Vehicle v10 = new Vehicle(10,"Audi","TT",2011,vt5);
	   Vehicle v11 = new Vehicle(11,"Porsche","911",2009,vt5);
	   ArrayList<Vehicle> vlist = new ArrayList<Vehicle>();
	   vlist.add(v1);
	   vlist.add(v2);
	   vlist.add(v3);
	   vlist.add(v4);
	   vlist.add(v5);
	   vlist.add(v6);
	   vlist.add(v7);
	   vlist.add(v8);
	   vlist.add(v9);
	   vlist.add(v10);
	   vlist.add(v11);
	   for(Vehicle v : vlist)
	   {
		   n.createEntryVehicle(v);
	  
	   }
	   
	   Reservation r1 = new Reservation(1,c1,v1,new Date("20-05-2011"),new Date("20-06-2011"));
	   Reservation r2 = new Reservation(2,c1,v2, new Date("20-05-2011"),new Date("20-06-2011"));
	   Reservation r3 = new Reservation(3,c2,v2, new Date("21-06-2011"),new Date("24-06-2011"));
	   Reservation r4 = new Reservation(4,c2,v2, new Date("17-04-2001"), new Date("17-02-2011"));
	   Reservation r5 = new Reservation(5,c3,v3,new Date("17-02-1999"), new Date("17-02-2000"));
	   Reservation r6 = new Reservation(6,c4,v4, new Date("184324234"), new Date("3534523123"));
	   Reservation r7 = new Reservation(7,c5,v4, new Date("3192389283"),new Date("53495843"));
	   Reservation r8 = new Reservation(8,c6,v6, new Date("Hmm? Bug?"), new Date("Bigass Bug?"));
	   Reservation r9 = new Reservation(9,c7,v7, new Date("20052011"),new Date("20062011"));
	   Reservation r10 = new Reservation(10,c1,v1, new Date("20052011"), new Date("20062011"));
	  ArrayList<Reservation> rlist = new ArrayList<Reservation>();
	  rlist.add(r1);
	  rlist.add(r2);
	  rlist.add(r3);
	  rlist.add(r4);
	  rlist.add(r5);
	  rlist.add(r6);
	  rlist.add(r7);
	  rlist.add(r8);
	  rlist.add(r9);
	  rlist.add(r10);
	  for(Reservation r : rlist)
	  {
		  n.createEntryReservation(r);
	  }
	System.out.println("If you have no errors thus far, check the database. You should have success");  
	n.closeDatabase();
   }
	   
	   
	   
   }

