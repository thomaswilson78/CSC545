package csc545project;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
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
    public DefaultComboBoxModel getFoodGroups(){
        String[] fgs = {"Fruits", "Vegetables", "Proteins","Dairy","Grains","Sugar/Fat"};
        return new DefaultComboBoxModel(fgs);
    }
    public ArrayList<String> getIngredientList(){
         ArrayList<String> ingredients = new ArrayList<>();
        conn = ConnectDB.setupConnnection();
        try {
            String sql = "select ingredient_name from Ingredients";
            pst = (OraclePreparedStatement) conn.prepareStatement(sql);
            rs = (OracleResultSet) pst.executeQuery();
            
            while (rs.next()) {
                ingredients.add(rs.getString("ingredient_name"));
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
        return ingredients;
    }
    public boolean doesExist(String name){
        conn = ConnectDB.setupConnnection();
        boolean doesExist = false;
        try {
            String sql = "select * from Ingredients where ingredient_name=?";

            pst = (OraclePreparedStatement) conn.prepareStatement(sql);
            pst.setString(1, name);
            rs = (OracleResultSet) pst.executeQuery();
            if(rs.next()){
                doesExist = true;
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
             ConnectDB.close(rs);
            ConnectDB.close(pst);
            ConnectDB.close(conn);
        }
        return doesExist;
    }
    public void upIngredient(int amount){
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
    public boolean addIngredient(String name, int cal, float pro, float sug, float fat, float sod, String fg, int quan){
        conn = ConnectDB.setupConnnection();
        boolean success = false;
            try {

                String sql = "insert into ingredients (ingredient_name, calories,"
                        + " protein, sugar, fat, sodium, food_group, quantity) values (?,?,?,?,?,?,?,?)";

                pst = (OraclePreparedStatement) conn.prepareStatement(sql);
                pst.setString(1, name);
                pst.setString(7, fg);
                    pst.setInt(2, cal);
                    pst.setFloat(3, pro);
                    pst.setFloat(4, sug);
                    pst.setFloat(5, fat);
                    pst.setFloat(6, sod);
                    pst.setInt(8,quan);

                int count = pst.executeUpdate();
                if(count>0){
                    success = true;
                }

            } catch (Exception e) {
                System.out.println(e);
            } finally {
                ConnectDB.close(pst);
                ConnectDB.close(conn);
            }
            return success;
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
            ArrayList<String> ingredientNames = new ArrayList<String>();
            while (rs.next()) {
                String name = rs.getString("ingredient_name");
                model.addElement(name);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            ConnectDB.close(rs);
            ConnectDB.close(conn);
        }
        
        return model;
    }
    
    public ArrayList<Ingredient> getShoppingList()
    { 
        ArrayList<Ingredient> allIngreds = new ArrayList<Ingredient>();
        conn = ConnectDB.setupConnnection();
        try {

            String sql = "select * from shoppinglist";

            st = (OracleStatement) conn.createStatement();

            rs = (OracleResultSet) st.executeQuery(sql);

            Ingredient ingred = new Ingredient(); 
            while (rs.next()) {
                String name = rs.getString("ingredient_name");
                int quan = rs.getInt("quantity");
                ingred.ingredient_name = name;
                ingred.quantity = quan;
                allIngreds.add(ingred);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            ConnectDB.close(rs);
            ConnectDB.close(conn);
        }
        
        return allIngreds;
    }
    
    public void increaseIngredient(String name, int quan)
    {
        conn = ConnectDB.setupConnnection();
        try {
            String sql = "Update Ingredients set quantity = quantity + ? where ingredient_name = ?";;
            pst = (OraclePreparedStatement) conn.prepareStatement(sql);
            pst.setInt(1, quan);
            pst.setString(2,name);
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
