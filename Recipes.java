/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package csc545project;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

/**
 *
 * @author thomas_wilson78
 */
public class Recipes {
    Connection conn = null;
    OraclePreparedStatement pst = null;
    OracleResultSet rs = null;
    
    public boolean checkExistingRecipes(String r) {
        conn = ConnectDB.setupConnnection();
        try {
            String sql = "select recipe_name from Recipes where recipe_name=?";
            pst = (OraclePreparedStatement) conn.prepareStatement(sql);
            pst.setString(1, r);
            rs = (OracleResultSet) pst.executeQuery();
            
            while (rs.next()) {
                ConnectDB.close(conn);
                ConnectDB.close(pst);
                ConnectDB.close(rs);
                return false;
            }
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
    public boolean addRecipes(String name, String inst, ArrayList<String> cat, ArrayList<String> ing) {
        conn = ConnectDB.setupConnnection();
        try {
            String sql = "insert into recipes values (?, ?)";
            pst = (OraclePreparedStatement) conn.prepareStatement(sql);
            pst.setString(1, name);
            pst.setString(2, inst);
            pst.executeQuery();
            for (String temp : cat) {
                sql = "insert into RecipeCategory values (?, ?)";
                pst = (OraclePreparedStatement) conn.prepareStatement(sql);
                pst.setString(1, name);
                pst.setString(2, temp);
                pst.executeQuery();
            }
            for (String temp : ing) {
                sql = "insert into IngredientsRecipes values (?, ?, 1)";
                pst = (OraclePreparedStatement) conn.prepareStatement(sql);
                pst.setString(1, name);
                pst.setString(2, temp);
                pst.executeQuery();
            }  
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
