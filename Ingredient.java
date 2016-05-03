/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc545project;

import java.sql.Connection;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;
import oracle.jdbc.OracleStatement;

public class Ingredient {

    String ingredient_name;
    int calories;
    float protein;
    float sugar;
    float fat;
    float sodium;
    String food_group;
    int quantity;
    Connection conn = null;
    OraclePreparedStatement pst = null;
    OracleResultSet rs = null;
    OracleStatement st = null;

    Ingredient() {

    }

    Ingredient(String i, int c, float p, float su, float f, float so, String food, int q) {
        ingredient_name = i;
        calories = c;
        protein = p;
        sugar = su;
        fat = f;
        sodium = so;
        food_group = food;
        quantity = q;
    }
    public void addIngredient(int amount){
        conn = ConnectDB.setupConnnection();
        try {
            String sql = "Update Ingredients set quantity=? where ingredient_name=?";
            quantity+=amount;
            pst = (OraclePreparedStatement) conn.prepareStatement(sql);
            pst.setInt(1, quantity);
            pst.setString(2,ingredient_name);
            rs = (OracleResultSet) pst.executeQuery();

        } catch (Exception e) {
            System.out.println(e);
        } finally {
             ConnectDB.close(rs);
            ConnectDB.close(pst);
           
            ConnectDB.close(conn);
        }
    
    }
        public void subtractIngredient(int amount){
        if(quantity-amount>=0){
        conn = ConnectDB.setupConnnection();
        try {
            String sql = "Update Ingredients set quantity=? where ingredient_name=?";
            quantity-=amount;
            pst = (OraclePreparedStatement) conn.prepareStatement(sql);
            pst.setInt(1, quantity);
            pst.setString(2,ingredient_name);
            rs = (OracleResultSet) pst.executeQuery();

        } catch (Exception e) {
            System.out.println(e);
        } finally {
             ConnectDB.close(rs);
            ConnectDB.close(pst);
           
            ConnectDB.close(conn);
        }
    }
    }
    public void setIngredient(String ingrName){
        conn = ConnectDB.setupConnnection();
        try {
            String sql = "select * from Ingredients where ingredient_name=?";

            pst = (OraclePreparedStatement) conn.prepareStatement(sql);
            pst.setString(1, ingrName);
            rs = (OracleResultSet) pst.executeQuery();
            while (rs.next()) {
                ingredient_name = rs.getString("ingredient_name");
                calories = rs.getInt("calories");
                protein = rs.getFloat("protein");
                sugar = rs.getFloat("sugar");
                fat = rs.getFloat("fat");
                sodium = rs.getFloat("sodium");
                food_group = rs.getString("food_group");
                quantity = rs.getInt("quantity");  
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
             ConnectDB.close(rs);
            ConnectDB.close(pst);
           
            ConnectDB.close(conn);
        }
    }
    public DefaultComboBoxModel populateIngredientsDropDown() {
        conn = ConnectDB.setupConnnection();
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        try {

            String sql = "select ingredient_name from Ingredients";

            st = (OracleStatement) conn.createStatement();
            rs = (OracleResultSet) st.executeQuery(sql);
            while (rs.next()) {
                String name = rs.getString("ingredient_name");
                model.addElement(name);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            ConnectDB.close(rs);
            ConnectDB.close(conn);
            ConnectDB.close(pst);
        }
        
        return model;
    }

    public ArrayList<String> getIngredientList() {
        conn = ConnectDB.setupConnnection();
        ArrayList<String> model = new ArrayList<String>();
        try {

            String sql = "select ingredient_name from Ingredients";
            st = (OracleStatement) conn.createStatement();
            rs = (OracleResultSet) st.executeQuery(sql);
            while (rs.next()) {
                model.add(rs.getString("ingredient_name"));
            }
        } 
        catch (Exception e) {
            System.out.println(e);
        } 
        finally {
            ConnectDB.close(rs);
            ConnectDB.close(conn);
            ConnectDB.close(pst);
        }
        return model;
    }
}