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

public class WMenu {
    Connection conn = null;
    OraclePreparedStatement pst = null;
    OracleResultSet rs = null;

    public String changeDay(String day, String meal) {
        String rec = "";
        conn = ConnectDB.setupConnnection();
        try {
            String sql = "select recipe_name from WeeklyMenu where weekday=? and meal=?";
            pst = (OraclePreparedStatement) conn.prepareStatement(sql);
            pst.setString(1, day);
            pst.setString(2, meal);
            rs = (OracleResultSet) pst.executeQuery();
            
            //unfinished, as it is currently, issues can occur where it won't set items
            //properly.
            while (rs.next()) {
                rec = rs.getString("recipe_name");
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
        return rec;
    }
    
    public ArrayList<String> allRecipes() {
        ArrayList<String> rec = new ArrayList<>();
        conn = ConnectDB.setupConnnection();
        try {
            String sql = "select recipe_name from Recipes";
            pst = (OraclePreparedStatement) conn.prepareStatement(sql);
            rs = (OracleResultSet) pst.executeQuery();
            
            while (rs.next()) {
                rec.add(rs.getString("recipe_name"));
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
        return rec;
    }
    
    public void updateMenu(String day, String b, String l, String d) {
        conn = ConnectDB.setupConnnection();
        try {
            String sql = "update WeeklyMenu set recipe_name=? where weekday=? and meal='Breakfast'";
            pst = (OraclePreparedStatement) conn.prepareStatement(sql);
            pst.setString(1, b);
            pst.setString(2, day);
            pst.executeQuery();
            sql = "update WeeklyMenu set recipe_name=? where weekday=? and meal='Lunch'";
            pst = (OraclePreparedStatement) conn.prepareStatement(sql);
            pst.setString(1, l);
            pst.setString(2, day);
            pst.executeQuery();
            sql = "update WeeklyMenu set recipe_name=? where weekday=? and meal='Dinner'";
            pst = (OraclePreparedStatement) conn.prepareStatement(sql);
            pst.setString(1, d);
            pst.setString(2, day);
            pst.executeQuery();
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        finally {
            ConnectDB.close(conn);
            ConnectDB.close(pst);
            ConnectDB.close(rs);
        }
    }
    public void resetWeeklyMenu() {
        conn = ConnectDB.setupConnnection();
        try {
            String sql = "update WeeklyMenu set recipe_name=null";
            pst = (OraclePreparedStatement) conn.prepareStatement(sql);
            pst.executeQuery();
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        finally {
            ConnectDB.close(conn);
            ConnectDB.close(pst);
            ConnectDB.close(rs);
        }
    }
}
