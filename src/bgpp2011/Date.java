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
    
    public Date(String dateString)
    {
    	this.dateString = dateString;
    		/*if(0< day <<31 && 0 < month<<13 && 2010 < year << 9999)
    		{
    			this.dateString = "" + year+month+day;
    		}
    		else {
    			throw new IllegalArgumentException("Non valid Date");
    		}*/
    		
    }
    
    public String getDateString()
    {
        return dateString;
    }
}




