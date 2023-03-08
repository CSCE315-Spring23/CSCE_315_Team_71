package chickfila.logic;

import java.sql.*;

public class DB {

    private Connection sql;

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

    public ResultSet select(String query) throws SQLException {
        ResultSet result = sql.createStatement().executeQuery(query);
        return result;
    }

    public void performQuery(String query) throws SQLException{
        sql.createStatement().executeUpdate(query);
    }
    
    public void close() throws SQLException {
        sql.close();
    }
}
