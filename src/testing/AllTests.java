package testing;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/*
 * This test suite is responsible for running a complete test of the internal structures of the system.
 * If these tests pass, all the methods in Commands, Nexus and DataBaseCom is working (in their basic functionality) 
 * as well as most of the tests in the Controller.
 */
@RunWith(Suite.class)
@SuiteClasses({ SetupSampleData.class, TestGetCustomerFromRes.class, TestNexusLists.class, testControllerChecks.class, 
	TestCreation.class, TestDeletion.class, SetupSampleData.class })
public class AllTests {

}
