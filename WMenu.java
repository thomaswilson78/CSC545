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
    //function will create and return an array list with a full list of the meal plan for the provided day.
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
    //Grabs all the recipes in the database and populates them in the meal combo boxes in the Weekly Plan tab
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
    //Grabs the user's input and updates the weeklymenu table in the data base with the provided information for that day. If
    //the vaule is empty, than a null value will be inserted instead.
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
    //sets all the meal values in the weeklymenu table to null
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
