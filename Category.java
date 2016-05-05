package csc545project;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

public class Category {
    Connection conn = null;
    OraclePreparedStatement pst = null;
    OracleResultSet rs = null;
    
    public ArrayList<String> allCategories() {
        ArrayList<String> cat = new ArrayList<>();
        conn = ConnectDB.setupConnnection();
        try {
            String sql = "select category_name from Category";
            pst = (OraclePreparedStatement) conn.prepareStatement(sql);
            rs = (OracleResultSet) pst.executeQuery();
            
            while (rs.next()) {
                cat.add(rs.getString("category_name"));
            }
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            ConnectDB.close(conn);
            ConnectDB.close(pst);
            ConnectDB.close(rs);
            return null;
        }
        finally {
            ConnectDB.close(conn);
            ConnectDB.close(pst);
            ConnectDB.close(rs);
        }
        return cat;
    }
    public boolean addCategory(String cat) {
        conn = ConnectDB.setupConnnection();
        try {
            String sql = "insert into category values (?)";
            pst = (OraclePreparedStatement) conn.prepareStatement(sql);
            pst.setString(1, cat);
            pst.executeQuery();
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            ConnectDB.close(conn);
            ConnectDB.close(pst);
            ConnectDB.close(rs);
            return false;
        }
        finally {
            ConnectDB.close(conn);
            ConnectDB.close(pst);
            ConnectDB.close(rs);
        }
        return true;
    }
}