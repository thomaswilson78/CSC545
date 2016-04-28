drop table Ingredients cascade constraints;
drop table Recipes cascade constraints;
drop table Category cascade constraints;
drop table WeeklyMenu cascade constraints;
drop table IngredientsRecipes cascade constraints;
drop table RecipeCategory cascade constraints;


create table Ingredients(
ingredient_name varchar(20) primary key,
calories number(4),
protein number(6,2),
sugar number(6,2),
fat number(6,2),
sodium number(6,2),
food_group varchar(15),
quantity number(6)
);

create table Recipes(
recipe_name varchar(50) primary key,
instructions varchar(2000)
);

create table Category(
category_name varchar(20) primary key
);

create table RecipeCategory(
recipe_name varchar(50)),
category_name varchar(20),
primary key (recipe_name,category_name)
);

create table WeeklyMenu(
meal varchar(10),
weekday varchar(10),
recipe_name varchar(50),
primary key (meal,weekday)
);

create table IngredientsRecipes(
recipe_name varchar(20),
ingredient_name varchar(20),
primary key (recipe_name,ingredient_name)
);

insert into Recipes VALUES ('Test','1.Test 2.Test 3.Test');
insert into Recipes VALUES ('Test2','1.Test 2.Test 3.Test');
alter table IngredientsRecipes add (constraint ingredientrecipes foreign key (recipe_name) references Recipes(recipe_name) on delete set null);
alter table IngredientsRecipes add (constraint ingredientrecipes2 foreign key (ingredient_name) references Ingredients(ingredient_name) on delete set null);

alter table RecipeCategory add (constraint ingredientcategory foreign key (recipe_name) references Recipes(recipe_name) on delete set null);
alter table RecipeCategory add (constraint ingredientcategory1 foreign key (category_name) references Category(category_name) on delete set null);

alter table WeeklyMenu add (constraint recipenameconstraint foreign key (recipe_name) references Recipes(recipe_name) on delete set null);


