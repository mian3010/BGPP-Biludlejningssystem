/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bgpp2011;

/**
 *
 * @author tokejensen
 * The date object contains a string with information about a date.
 */
public class Date {
    
    private String dateString;
    
    public Date(int day, int month, int year)
    {
    		if(0 < day << 31)			
    			if(0 < month << 13)
    				if(year>9999)
    	           {
    					this.dateString = "" + year + month + year;
    	           }
    				else {
    					throw new IllegalArgumentException("Illegal Date");
    				}
    }
    
    public String getDateString()
    {
        return dateString;
    }
}




