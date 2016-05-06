package csc545project;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;
import oracle.jdbc.OracleStatement;

/**
 *
 * @author thomas_wilson78
 */
public class Recipes {
    Connection conn = null;
    OraclePreparedStatement pst = null;
    OracleResultSet rs = null;
    OracleStatement st = null;
    
    public DefaultListModel getRecipeNames(){
        conn = ConnectDB.setupConnnection();
        DefaultListModel model = new DefaultListModel();
        try {
            String sql = "select recipe_name from Recipes";
            st = (OracleStatement) conn.createStatement();
            rs = (OracleResultSet) st.executeQuery(sql);
            while (rs.next()) {
                model.addElement(rs.getString("recipe_name"));
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            ConnectDB.close(rs);
            ConnectDB.close(conn);
        }
        return model;
    }
    public DefaultListModel getRecipeIngredients(String recipeName){
        conn = ConnectDB.setupConnnection();
        DefaultListModel model = new DefaultListModel();
        try {
            String sql = "select ingredient_name from IngredientsRecipes where recipe_name=?";
            pst = (OraclePreparedStatement) conn.prepareStatement(sql);
            pst.setString(1, recipeName);
            rs = (OracleResultSet) pst.executeQuery();
            
            while (rs.next()) {
               model.addElement(rs.getString("ingredient_name"));
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
        return model;
    }
    public String getRecipesInstructions(String recipeName){
        String instructions = "";
        conn = ConnectDB.setupConnnection();
       
        try {
            String sql = "select instructions from Recipes where recipe_name=?";
            pst = (OraclePreparedStatement) conn.prepareStatement(sql);
            pst.setString(1, recipeName);
            rs = (OracleResultSet) pst.executeQuery();
            
            while (rs.next()) {
               instructions = rs.getString("instructions");
            }
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            ConnectDB.close(conn);
            ConnectDB.close(pst);
            ConnectDB.close(rs);
        }
        finally {
            ConnectDB.close(conn);
            ConnectDB.close(pst);
            ConnectDB.close(rs);
        }
        return instructions;
    }
    public DefaultListModel getRecipeCategory(String recipeName){
              conn = ConnectDB.setupConnnection();
        DefaultListModel model = new DefaultListModel();
        try {
            String sql = "select category_name from RecipeCategory where recipe_name=?";
            pst = (OraclePreparedStatement) conn.prepareStatement(sql);
            pst.setString(1, recipeName);
            rs = (OracleResultSet) pst.executeQuery();
            
            while (rs.next()) {
               model.addElement(rs.getString("category_name"));
            }
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            ConnectDB.close(conn);
            ConnectDB.close(pst);
            ConnectDB.close(rs);
        }
        finally {
            ConnectDB.close(conn);
            ConnectDB.close(pst);
            ConnectDB.close(rs);
        }
        return model;
    }
    
    public boolean checkExistingRecipes(String r) {
        conn = ConnectDB.setupConnnection();
        boolean doesExist = false;
        try {
            String sql = "select recipe_name from Recipes where recipe_name=?";
            pst = (OraclePreparedStatement) conn.prepareStatement(sql);
            pst.setString(1, r);
            rs = (OracleResultSet) pst.executeQuery();
            
            if (rs.isBeforeFirst())
                doesExist = true;
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        finally {
            ConnectDB.close(conn);
            ConnectDB.close(pst);
            ConnectDB.close(rs);
        }
        return doesExist;
    }
    public boolean addRecipes(String name, String inst, ArrayList<Integer> quan, ArrayList<String> cat, ArrayList<String> ing) {
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
            for (int i=0;i<ing.size();i++) {
                sql = "insert into IngredientsRecipes values (?, ?, ?)";
                pst = (OraclePreparedStatement) conn.prepareStatement(sql);
                pst.setString(1, name);
                pst.setString(2, ing.get(i));
                pst.setInt(3, quan.get(i));
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