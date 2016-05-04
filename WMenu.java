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

    public ArrayList<String> changeDay(String day) {
        ArrayList<String> rec = new ArrayList<String>();
        conn = ConnectDB.setupConnnection();
        try {
            String sql = "select recipe_name from WeeklyMenu where weekday=?";
            pst = (OraclePreparedStatement) conn.prepareStatement(sql);
            pst.setString(1, day);
            rs = (OracleResultSet) pst.executeQuery();
            
            while (rs.next()) {
                rec.add(rs.getString("recipe_name"));
            }
        }
        catch (SQLException e) {
            rec=null;
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
    
    public boolean updateMenu(String day, String b, String l, String d) {
        conn = ConnectDB.setupConnnection();
        try {
            String sql = "update WeeklyMenu set recipe_name=? where weekday=? and meal='Breakfast'";
            if(b.equals("-empty-")) {
                pst = (OraclePreparedStatement) conn.prepareStatement(sql);
                pst.setString(1, null);
                pst.setString(2, day);
                pst.executeQuery();
            }
            else {
                pst = (OraclePreparedStatement) conn.prepareStatement(sql);
                pst.setString(1, b);
                pst.setString(2, day);
                pst.executeQuery();
            }
            sql = "update WeeklyMenu set recipe_name=? where weekday=? and meal='Lunch'";
            if(l.equals("-empty-")){
                pst = (OraclePreparedStatement) conn.prepareStatement(sql);
                pst.setString(1, null);
                pst.setString(2, day);
                pst.executeQuery();
            }
            else {
                pst = (OraclePreparedStatement) conn.prepareStatement(sql);
                pst.setString(1, l);
                pst.setString(2, day);
                pst.executeQuery();
            }
            sql = "update WeeklyMenu set recipe_name=? where weekday=? and meal='Dinner'";
            if(d.equals("-empty-")){
                pst = (OraclePreparedStatement) conn.prepareStatement(sql);
                pst.setString(1, null);
                pst.setString(2, day);
                pst.executeQuery();
            }
            else {
                pst = (OraclePreparedStatement) conn.prepareStatement(sql);
                pst.setString(1, d);
                pst.setString(2, day);
                pst.executeQuery();
            }
            ConnectDB.close(conn);
            ConnectDB.close(pst);
            ConnectDB.close(rs);
            return true;
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            ConnectDB.close(conn);
            ConnectDB.close(pst);
            ConnectDB.close(rs);
            return false;
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
