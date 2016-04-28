/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package csc545project;

public class Ingredient {
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
    
}
