/-----Meal Buddy README-----/

/----Introduction-----/
This application allows a user to create, store, and modify recipes. It also allows the creation, storage, 
and modificationof macro ingredients along with nutritional information and quantity currently in the user's
fridge. In addition the user cancreate a weekly menu for each day of the week and for each meal of the day 
and generate a shopping list based on the week's recipes. Lastly, the user can search for recipes based on
any number of ingredients and categories that are already part of the user's recipes.

/----Setup and Necessary Files----/
The program consists of java files to be ran in a Java IDE (in this case Netbeans) and uses
Oracle to store data. First the "TeamProject.sql" must be ran in an Oracle environment to initialize all
tables for the program. Then the project needs to be opened in a Java IDE to run the program.

/---Recipes Tab---/
The Recipes Tab has two main operations; the viewing of recipe information (ingredients, instructions, and
category) and the creation of new recipes through another frame. Clicking on a recipe in the recipe list
will populate the other text areas with information about the recipe selected. Clicking the "Add Recipe" 
button will open the add recipe frame where a new recipe can be created.

/---Add Recipe Frame---/
The Add Recipe Frame allows the user to enter a name, instructions, list of ingredients, and a list of 
categories for a new recipe.  The recipe is then submitted and added to the database when the "Add Recipe"
button is clicked.

/---Fridge Tab---/
The Fridge Tab allows the user to view and edit nutritional information and quantity owned by selecting an
ingredient from the list.  The list can either contain all ingredients or just ingredients currently in the
fridge by toggling the button below the list.

/---Weekly Menu Tab---/
The Weekly Menu Tab allows the user create a weekly menu by first selecting the day and then selecting the
recipe for the given meal in the combo boxes.  If there is already a meal for a given day and meal time it
will be loaded into the combo box.  Updates must be made one day at a time by selecting the recipes for the
day and then clicking the "Update" button.  The clear button will display a popup message confirming if the
user would like to reset the weekly menu before clearing all recipes from the given week.

/---Search Tab---/
The search tab contains two tables that contain all ingredients and categories as well as a list to display
the results of the search. To start a search select any number of ingredients and categories using the 
checkboxes next to entries and click the "Search" button. The program will then try to select all recipes
that match all of the selected ingredients and categories and place the results in the list to the left. The
"Clear" button unchecks all items in both the ingredients and categories tables.

/---Shopping Tab---/
The Shopping Tab allows the user to view what ingredients and their amounts that need to be purchased to 
make all the recipes for the given week and add ingredient quantites to the fridge.  The shopping list is
generate automatically when the progam first launches and when a change is made to the weekly menu. To add
ingredients to the fridge the user selects an ingredient from the combo box and enters the quantity bought
and then clicks the "Submit" button to complete the addition.

/----Credits----/
Created by Thomas Wilson, Cassie Allen, and Ethan LaFave