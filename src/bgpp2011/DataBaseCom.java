
package bgpp2011;

import java.sql.*;
/**
 * This is the basic databaseconnection. 
 * This has been tested and is working (except get, still testing).
 * @author STAHL7
 */
public class DataBaseCom {
    private Connection conn;
    private Statement dbStatement;
    
    public DataBaseCom() 
           
    {        
            openDb();
    }
        
    
    private void openDb()
    {
        try {   
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection
            ("jdbc:mysql://mysql.itu.dk/BGPP_1", "msta", "trololo");
             dbStatement = conn.createStatement();
        }
        catch (SQLException exn) {
            System.out.println("The database connection cannot be opened");
        }  catch (ClassNotFoundException exn) {
            System.out.println("There was an error finding the connected Java driver." + exn);
        }
    }
    
    public void reboot()
    {
        close();
        openDb();
               
    }

    public void close()
    {
        try {
            conn.close();
       }
        catch(SQLException exn) {
            System.out.println("The connection could not be closed: " + exn);
        }
    
    }
    
    public boolean update(String query)
    {
        try {
            dbStatement.execute(query);
            return true;
        }
        catch(SQLException exn)
        {
           System.out.println("The query could not be executed: " + exn);
           return false;
        } 
    }
    public ResultSet get(String query)
    {
        try{
            dbStatement.executeQuery(query);
            return dbStatement.getResultSet();
        }
       catch(SQLException exn)
       {
           System.out.println("The results you've asked for could not be "
                   + "retrieved :" + exn);
           return null;
           
       }
            
    }
   public ResultSet create(String query)
    {
    	try {
    		dbStatement.executeUpdate(query, dbStatement.RETURN_GENERATED_KEYS);
    		ResultSet res = dbStatement.getGeneratedKeys();
    		if(res == null)
    		{
    			System.out.println("ResultSet fail!");
    		}
    		return res;
    	}
    	catch(SQLException exn)
    	{
    		System.out.println("The entry could not be created" + exn);
    		return null;
    	}
    }
    
    
    
    
}