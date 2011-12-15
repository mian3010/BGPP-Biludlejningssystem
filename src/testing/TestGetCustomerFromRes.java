package testing;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Iterator;

import org.junit.Test;

import bgpp2011.Controller;
import bgpp2011.Customer;
import bgpp2011.Reservation;

/*
 * This test was made to test the link between customer and reservation in the map containing 
 * reservations. The test was created due to a problem occurring in the GUI.
 * @author Magnus Stahl
 */
public class TestGetCustomerFromRes {

	@Test
	public void testGetCusFromRes()
	{
		Controller con = new Controller();
		Collection<Reservation> r = con.getModel().reservationCollection();
		Iterator<Reservation> it = r.iterator();
		while(it.hasNext())
		{
			Reservation re = it.next();
			boolean a = false;
			if(re.getCustomer()!=null)
			{
				a = true;
			}
			assertEquals(a,true);
		}
	}
}
