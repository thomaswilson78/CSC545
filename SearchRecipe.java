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
 * @author Ethan
 */
public class SearchRecipe {
    Connection conn = null;
    OraclePreparedStatement pst = null;
    OracleResultSet rs = null;
    
    public ArrayList<String> getValidRecipes(ArrayList<String> ingred, ArrayList<String> categ)
    {
        Recipes myR = new Recipes();
        ArrayList<String> validRecipes = new ArrayList<String>();
        ArrayList<ArrayList<String>> allRecipes = new ArrayList<ArrayList<String>>();
        for(int i = 0; i < ingred.size(); i++)
        {
            allRecipes.add(myR.getRecipeByIngredient(ingred.get(i)));
        }
        for(int i = 0; i < categ.size(); i++)
        {
            allRecipes.add(myR.getRecipeByCategory(categ.get(i))); 
        }
        
        validRecipes = getCommonItems(allRecipes);
        
        return validRecipes;
    }
    
    private ArrayList<String> getCommonItems(ArrayList<ArrayList<String>> r)
    {
        ArrayList<String> commonItems = new ArrayList<String>();
        ArrayList<String> temp = new ArrayList<String>();
        
        if(r.isEmpty())
        {
            
        }
        else
        {
            commonItems = r.get(0);
            for(int i = 1; i < r.size(); i++)
            {
                for(int j = 0; j < commonItems.size(); j++)
                {
                    for(int k = 0; k < r.get(i).size(); k++)
                    {
                        if(commonItems.get(j).equals(r.get(i).get(k)))
                            temp.add(commonItems.get(j));
                    }
                }
                commonItems.clear();
                commonItems.addAll(temp);
                temp.clear();
            }
        }
        
        return commonItems;
    }
}
