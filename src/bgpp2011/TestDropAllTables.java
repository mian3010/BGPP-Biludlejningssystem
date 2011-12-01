
package bgpp2011;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
// import static org.junit.Assert.*;

/**
 *
 * @author STAHL7
 */
public class TestDropAllTables {
    
    public TestDropAllTables() {
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
    public void dropAllTables()
    {
        DataBaseCom db = new DataBaseCom();
        db.update("DROP TABLE Vehicle;");
        db.update("DROP TABLE Customer;");        
        db.update("DROP TABLE Reservation;");
        db.update("DROP TABLE VehicleType;");
    }
}       
