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
 /*The Recipes class contains the functions that are directly relevant to recipes such as getRecipeNames(), getRecipeIngredients(), etc*/
public class Recipes {
    Connection conn = null;
    OraclePreparedStatement pst = null;
    OracleResultSet rs = null;
    OracleStatement st = null;
    /*getRecipeNames() returns a DefaultListModel with all of the recipe names found in the database*/
    public DefaultListModel getRecipeNames(){
        conn = ConnectDB.setupConnnection();
        DefaultListModel model = new DefaultListModel();
        try {
            String sql = "select recipe_name from Recipes";
            st = (OracleStatement) conn.createStatement();
            rs = (OracleResultSet) st.executeQuery(sql);
            while (rs.next()) {
                model.addElement(rs.getString("recipe_name")); //Add next recipe_name to the DefaultListModel
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            ConnectDB.close(rs);
            ConnectDB.close(conn);
        }
        return model;
    }
    /*getRecipeIngredients returns a DefaultListModel with the ingredients for a recipe that has a recipename that matches 
    the parameter recipeName*/
    public DefaultListModel getRecipeIngredients(String recipeName){
        conn = ConnectDB.setupConnnection();
        DefaultListModel model = new DefaultListModel();
        try {
            String sql = "select ingredient_name from IngredientsRecipes where recipe_name=?";
            pst = (OraclePreparedStatement) conn.prepareStatement(sql);
            pst.setString(1, recipeName);
            rs = (OracleResultSet) pst.executeQuery();
            
            while (rs.next()) {
               model.addElement(rs.getString("ingredient_name")); //add ingredient to list of ingredients
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
    /*getRecipesInstructions() returns a String containing the instructions for a given recipe with the parameter recipeName*/
    public String getRecipesInstructions(String recipeName){
        String instructions = "";
        conn = ConnectDB.setupConnnection();
       
        try {
            String sql = "select instructions from Recipes where recipe_name=?";
            pst = (OraclePreparedStatement) conn.prepareStatement(sql);
            pst.setString(1, recipeName);
            rs = (OracleResultSet) pst.executeQuery();
            
            while (rs.next()) {
               instructions = rs.getString("instructions"); //store instructions
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
    /*getRecipeCategory returns a DefaultListModel of categories that the recipe with paramater recipeName falls into*/
    public DefaultListModel getRecipeCategory(String recipeName){
              conn = ConnectDB.setupConnnection();
        DefaultListModel model = new DefaultListModel();
        try {//get categories of recipe
            String sql = "select category_name from RecipeCategory where recipe_name=?";
            pst = (OraclePreparedStatement) conn.prepareStatement(sql);
            pst.setString(1, recipeName);
            rs = (OracleResultSet) pst.executeQuery();
            
            while (rs.next()) {
               model.addElement(rs.getString("category_name")); //add category to DefaultListModel
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
    /*checkExistingRecipes() returns true when a recipe with recipe name r exists, returns false when recipe with recipe
    name r does not exist.*/
    public boolean checkExistingRecipes(String r) {
        conn = ConnectDB.setupConnnection();
        boolean doesExist = false;
        try {
            String sql = "select recipe_name from Recipes where recipe_name=?";
            pst = (OraclePreparedStatement) conn.prepareStatement(sql);
            pst.setString(1, r);
            rs = (OracleResultSet) pst.executeQuery();
            
            if (rs.isBeforeFirst())//If recipe_name is found set doesExist to true
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
    
    public ArrayList<String> getRecipeByIngredient(String ingredientName) {
        ArrayList<String> recipes = new ArrayList<>();
        conn = ConnectDB.setupConnnection();
        try {
            String sql = "select distinct recipe_name from IngredientsRecipes where ingredient_name = ?";
            pst = (OraclePreparedStatement) conn.prepareStatement(sql);
            pst.setString(1, ingredientName);
            rs = (OracleResultSet) pst.executeQuery();
            
            while (rs.next()) {
                recipes.add(rs.getString("recipe_name"));
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
        return recipes;
    }
    
    public ArrayList<String> getRecipeByCategory(String categoryName) {
        ArrayList<String> recipes = new ArrayList<>();
        conn = ConnectDB.setupConnnection();
        try {
            String sql = "select distinct recipe_name from RecipeCategory where category_name = ?";
            pst = (OraclePreparedStatement) conn.prepareStatement(sql);
            pst.setString(1, categoryName);
            rs = (OracleResultSet) pst.executeQuery();
            
            while (rs.next()) {
                recipes.add(rs.getString("recipe_name"));
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
        return recipes;
    }
}
