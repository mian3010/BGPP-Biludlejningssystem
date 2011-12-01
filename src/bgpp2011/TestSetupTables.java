package bgpp2011;



import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
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
        assertEquals(db.update("CREATE TABLE VehicleType (id INT PRIMARY KEY,"
              + " name TEXT, price INT)"),true);    
        assertEquals(db.update("CREATE TABLE Vehicle (id INT PRIMARY KEY, "
              + "make TEXT, "  + "model TEXT, year INT, typeID INT, "
              + "FOREIGN KEY (typeID)" + "REFERENCES VehicleType (id))"),true);
        assertEquals(db.update("CREATE TABLE Customer (id INT PRIMARY KEY, "
              + "name TEXT," + "phone INT, address TEXT)"),true);
        assertEquals(db.update("CREATE TABLE Reservation (id INT PRIMARY KEY,"
              + " customerID INT," + " vehicleID INT, startdate TEXT, "
              + "enddate TEXT, FOREIGN KEY" + "(customerID) REFERENCES "
              + "Customer (id), FOREIGN KEY" + "(vehicleID) "
              + "REFERENCES Vehicle (id))"),true);
    
    }
}
