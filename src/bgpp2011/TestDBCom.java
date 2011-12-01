/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class TestDBCom {
    
    public TestDBCom() {
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
     * The first JUnit testing of the Database! This method creates a sample
     * table in the database and tests if the opening, execution and closing
     * of the database is correct! (There are only print statements here to confirm it)!
     * @author Magnus Stahl
     */
   @Test
   public void TestDBConnection()
   {
       DataBaseCom instance = new DataBaseCom();
       System.out.println("If there are no errors displayed here, "
               + "the connection is open.");
    
       instance.close();
       System.out.println("If there are no error HERE, "
               + "the connection have been closed!");
               
   }
}
