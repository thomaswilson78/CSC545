/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package csc545project;

import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Connection;
import javax.swing.JOptionPane;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

public class Ingredient {
    Connection conn = null;
    OraclePreparedStatement pst = null;
    OracleResultSet rs = null;
    
    String ingredient_name;
    int calories;
    float protein;
    float sugar;
    float fat;
    float sodium;
    String food_group;
    int quantity;
    Ingredient(){
        
    }
    Ingredient(String i, int c, float p, float su, float f, float so, String food, int q){
        ingredient_name = i;
        calories = c;
        protein = p;
        sugar = su;
        fat = f;
        sodium = so;
        food_group = food;
        quantity = q;
        
    }
    
    public ArrayList<String> allIngredientNames() {
        ArrayList<String> ingredName = new ArrayList<>();
        conn = ConnectDB.setupConnnection();
        try {
            String sql = "select ingredient_name from Ingredients";
            pst = (OraclePreparedStatement) conn.prepareStatement(sql);
            rs = (OracleResultSet) pst.executeQuery();
            
            while (rs.next()) {
                ingredName.add(rs.getString("ingredient_name"));
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
        return ingredName;
    }
}
