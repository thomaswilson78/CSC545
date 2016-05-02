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
