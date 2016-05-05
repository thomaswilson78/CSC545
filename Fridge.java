package csc545project;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Connection;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;
import oracle.jdbc.OracleStatement;

/**
 *
 * @author CassieAlyce
 */
public class Fridge {
     ArrayList<Ingredient> ingredientsInFridge;
    Connection conn = null;
    OraclePreparedStatement pst = null;
    OracleResultSet rs = null;
    OracleStatement st = null;
    Fridge(){
       ingredientsInFridge = new ArrayList<>(); 
    }
    public void addIngredient(Ingredient newIngredient){
        ingredientsInFridge.add(newIngredient);
    }
    public void deleteIngredient(String ingredientName){
        for(Ingredient ingredient : ingredientsInFridge){
            if(ingredient.ingredient_name.equals(ingredientName)){
                   ingredientsInFridge.remove(ingredient);
            }
    }
    }
    public void incrementFridgeIngredient(Ingredient selectedIngr){
        conn = ConnectDB.setupConnnection();
        try {

            String sql = "update Ingredients set quantity=? where ingredient_name=?";
            selectedIngr.quantity++;
            pst = (OraclePreparedStatement) conn.prepareStatement(sql);
            pst.setInt(1, selectedIngr.quantity);
            pst.setString(2, selectedIngr.ingredient_name);
            rs = (OracleResultSet) pst.executeQuery();

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            ConnectDB.close(pst);
            ConnectDB.close(rs);
            ConnectDB.close(conn);
        }
        populateFridgeList();

    }
     public void decrease(Ingredient selectedIngr){
         if(selectedIngr.quantity>0){
        conn = ConnectDB.setupConnnection();
        try {

            String sql = "update Ingredients set quantity=? where ingredient_name=?";
            selectedIngr.quantity--;
            pst = (OraclePreparedStatement) conn.prepareStatement(sql);
            pst.setInt(1, selectedIngr.quantity);
            pst.setString(2, selectedIngr.ingredient_name);
            rs = (OracleResultSet) pst.executeQuery();

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            ConnectDB.close(pst);
            ConnectDB.close(rs);
            ConnectDB.close(conn);
        }
        }
         populateFridgeList();
    }
    public Ingredient getIngredient(String ingredientName){
        Ingredient selectedIngredient = new Ingredient();
        for (Ingredient ingredient : ingredientsInFridge) {
            if (ingredient.ingredient_name.equalsIgnoreCase(ingredientName)) {
                selectedIngredient = ingredient;
            }
        }
        return selectedIngredient;
    }
    public DefaultListModel populateFridgeList(){
        conn = ConnectDB.setupConnnection();
        DefaultListModel model = new DefaultListModel();
        try {

            String sql = "select * from Ingredients where quantity>0";

            st = (OracleStatement) conn.createStatement();

            rs = (OracleResultSet) st.executeQuery(sql);
            ingredientsInFridge.clear();
            while (rs.next()) {
                String name = rs.getString("ingredient_name");
                int calories = rs.getInt("calories");
                float protein = rs.getFloat("protein");
                float sugar = rs.getFloat("sugar");
                float fat = rs.getFloat("fat");
                float sodium = rs.getFloat("sodium");
                String food_group = rs.getString("food_group");
                int quantity = rs.getInt("quantity");
                Ingredient newIngredient = new Ingredient(name, calories, protein, sugar, fat, sodium, food_group, quantity);
                ingredientsInFridge.add(newIngredient);
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
     public DefaultListModel populateIngredientsList(){
        conn = ConnectDB.setupConnnection();
        DefaultListModel model = new DefaultListModel();
        try {

            String sql = "select * from Ingredients";

            st = (OracleStatement) conn.createStatement();

            rs = (OracleResultSet) st.executeQuery(sql);
            while (rs.next()) {
                String name = rs.getString("ingredient_name");
                int calories = rs.getInt("calories");
                float protein = rs.getFloat("protein");
                float sugar = rs.getFloat("sugar");
                float fat = rs.getFloat("fat");
                float sodium = rs.getFloat("sodium");
                String food_group = rs.getString("food_group");
                int quantity = rs.getInt("quantity");
                Ingredient newIngredient = new Ingredient(name, calories, protein, sugar, fat, sodium, food_group, quantity);
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

    
}
