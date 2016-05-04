/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;
/**
 *
 * @author thomas_wilson78
 */
public class ConnectDB {
    public static Connection setupConnnection() {
        String jdbcDriver = "oracle.jdbc.driver.OracleDriver";
        String jdbcUrl = "jdbc:oracle:thin:@csc545.cxqvw6gthohp.us-east-1.rds.amazonaws.com:1521/ORCL";
        String username = "recipeuser";
        String password = "recipe#159";
        
        //connect to DB
        try {
            //load jdbc driver
            Class.forName(jdbcDriver);
            //connect to DB
            Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
            return conn;
        }
        //if connecion failed, print error message
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }
    static void close(Connection conn) {
        if(conn!=null) {
            try {
                conn.close();
            }
            catch (Throwable whatever) {
                
            }
        }
    }
    
    static void close(OraclePreparedStatement pst) {
        if(pst!=null) {
            try {
                pst.close();
            }
            catch (Throwable whatever) {
                
            }
        }
    }
    
       static void close(OracleResultSet rs) {
        if(rs!=null) {
            try {
                rs.close();
            }
            catch (Throwable whatever) {
                
            }
        }
    }
}