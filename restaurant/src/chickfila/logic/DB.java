package chickfila.logic;

import java.sql.*;


/**

The DB class provides methods for interacting with a PostgreSQL database.
*/
public class DB {

    private Connection sql;


    /**

    Creates a new database connection with the specified username and password.
    @param username the username to use for the database connection
    @param password the password to use for the database connection
    */
    public DB(String username, String password) {
        try {
            sql = DriverManager.getConnection("jdbc:postgresql://csce-315-db.engr.tamu.edu/csce315331_team_71", username, password);
        } 
        catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    /**
    Executes a select query on the database and returns the result set.
    @param query the select query to execute
    @return the result set returned by the query
    @throws SQLException if there is an error executing the query
    */
    public ResultSet select(String query) throws SQLException {
        ResultSet result = sql.createStatement().executeQuery(query);
        return result;
    }

    /**
    Executes a query on the database that does not return a result set.
    @param query the query to execute
    @throws SQLException if there is an error executing the query
    */
    public void performQuery(String query) throws SQLException{
        sql.createStatement().executeUpdate(query);
    }
    
    /**
    Closes the database connection.
    @throws SQLException if there is an error closing the connection
    */
    public void close() throws SQLException {
        sql.close();
    }
}
