package utilities;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import base.BaseTest;

public class RecipesFilterer {
	DbConnection db = new DbConnection();
	private ReadExcel readExcel = new ReadExcel();
	private Connection conn;
	int count;
	
	public RecipesFilterer() {
		this.conn= db.DbSetup();
	}
	

	public void LFVEliminatedRecipes(Map<String, String> recipe) {
		List<String> lfvEliminateIngredients = readExcel.getRecipeFilterItemsList("Final list for LFV Elimination ", 0);
		List<String> lfvRecipesToAvoidIngredients = readExcel
				.getRecipeFilterItemsList("Final list for LFV Elimination ", 3);

		String recipeIngredients = recipe.get("Ingredients");
		String recipeName = recipe.get("Recipe Name");
		if (!isEliminateIngredientsPresent(lfvEliminateIngredients, recipeIngredients)
				&& !isAvoidItemsPresent(lfvRecipesToAvoidIngredients, recipeName)) {			
			DbConnection.insertRow(conn,"lfv_recipes_without_eliminateitems",recipe);
		} else {
	        System.out.println("Recipe " + recipeName + " contains eliminated ingredients or avoid items. Not inserting into database.");
	}
	}

	public void LCHFEliminatedRecipes(Map<String, String> recipe)  {
		List<String> lchfEliminateIngredients = readExcel.getRecipeFilterItemsList("Final list for LCHFElimination ",
				0);
		List<String> lchfRecipesToAvoidIngredients = readExcel
				.getRecipeFilterItemsList("Final list for LCHFElimination ", 2);

		String recipeIngredients = recipe.get("Ingredients");
		String recipeName = recipe.get("Recipe Name");

		if (!isEliminateIngredientsPresent(lchfEliminateIngredients, recipeIngredients)
				&& !isAvoidItemsPresent(lchfRecipesToAvoidIngredients, recipeName)) {			
			DbConnection.insertRow(conn,"lchf_recipes_without_eliminateitems",recipe);
		} else {
	        System.out.println("Recipe " + recipeName + " contains eliminated ingredients or avoid items. Not inserting into database.");
		}
	}

	public void LFVAddRecipes(Map<String, String> recipe) {
		List<String> lfvAddIngredients = readExcel.getRecipeFilterItemsList("Final list for LFV Elimination ", 1);
		List<String> lfvEliminateIngredients = readExcel.getRecipeFilterItemsList("Final list for LFV Elimination ", 0);
		List<String> lfvRecipesToAvoidIngredients = readExcel
				.getRecipeFilterItemsList("Final list for LCHFElimination ", 3);

		String recipeIngredients = recipe.get("Ingredients");
		String recipeName = recipe.get("Recipe Name");

		if (!isEliminateIngredientsPresent(lfvEliminateIngredients, recipeIngredients)
				&& !isAvoidItemsPresent(lfvRecipesToAvoidIngredients, recipeName)) {
			if (isAddIngredientsPresent(lfvAddIngredients, recipeIngredients)) {
				DbConnection.insertRow(conn,"lfv_recipes_with_addon_items",recipe);
			} else {
		        System.out.println("Recipe " + recipeName + " does not contains add ingredients. Not inserting into database.");
			}	
		}
	}

	public void LCHFAddRecipes(Map<String, String> recipe) {
		List<String> lchfAddIngredients = readExcel.getRecipeFilterItemsList("Final list for LCHFElimination ", 1);
		List<String> lchfEliminateIngredients = readExcel.getRecipeFilterItemsList("Final list for LCHFElimination ",
				0);
		List<String> lchfRecipesToAvoidIngredients = readExcel
				.getRecipeFilterItemsList("Final list for LCHFElimination ", 2);

		String recipeIngredients = recipe.get("Ingredients");
		String recipeName = recipe.get("Recipe Name");

		if (!isEliminateIngredientsPresent(lchfEliminateIngredients, recipeIngredients)
				&& !isAvoidItemsPresent(lchfRecipesToAvoidIngredients, recipeName)) {
			if (isAddIngredientsPresent(lchfAddIngredients, recipeIngredients)) {
				DbConnection.insertRow(conn,"lchf_recipes_with_addon_items",recipe);
			} else {
		        System.out.println("Recipe " + recipeName + " does not contains add ingredients. Not inserting into database.");
			}	
		}
	}
	
	public void LFVAllergicIngredientsRecipes(Map<String, String> recipe) {
		List<String> lfvEliminateIngredients = readExcel.getRecipeFilterItemsList("Final list for LFVElimination ",
				0);
		List<String> lfvRecipesToAvoidIngredients = readExcel
				.getRecipeFilterItemsList("Final list for LFVElimination ", 2);

		String recipeIngredients = recipe.get("Ingredients").toLowerCase();
		String recipeName = recipe.get("Recipe Name").toLowerCase();

		if (!isEliminateIngredientsPresent(lfvEliminateIngredients, recipeIngredients)
				&& !isAvoidItemsPresent(lfvRecipesToAvoidIngredients, recipeName)) {
			if (recipeIngredients.contains("hazelnut")) {
				DbConnection.insertRow(conn,"lfv_recipes_allergy_hazelnut",recipe);
			}
			else if (recipeIngredients.contains("sesame")) {
				DbConnection.insertRow(conn,"lfv_recipes_allergy_sesame",recipe);
			}
			else if (recipeIngredients.contains("walnut")) {
				DbConnection.insertRow(conn,"lfv_recipes_allergy_walnut",recipe);
			}
			else if (recipeIngredients.contains("almond")) {
				DbConnection.insertRow(conn,"lfv_recipes_allergy_almond",recipe);
			}
			else if (recipeIngredients.contains("cashew")) {
				DbConnection.insertRow(conn,"lfv_recipes_allergy_cashew",recipe);
			}
			else if (recipeIngredients.contains("peanuts")) {
				DbConnection.insertRow(conn,"lfv_recipes_allergy_pistachio",recipe);
			}
			else if (recipeIngredients.contains("pistachio")) {
				DbConnection.insertRow(conn,"lfv_recipes_allergy_pistachio",recipe);
			}
			
			
		}
	}
	
	public void LCHFAllergicIngredientsRecipes(Map<String, String> recipe) {
		List<String> lchfEliminateIngredients = readExcel.getRecipeFilterItemsList("Final list for LCHFElimination ",
				0);
		List<String> lchfRecipesToAvoidIngredients = readExcel
				.getRecipeFilterItemsList("Final list for LCHFElimination ", 2);

		String recipeIngredients = recipe.get("Ingredients").toLowerCase();
		String recipeName = recipe.get("Recipe Name").toLowerCase();

		if (!isEliminateIngredientsPresent(lchfEliminateIngredients, recipeIngredients)
				&& !isAvoidItemsPresent(lchfRecipesToAvoidIngredients, recipeName)) {
			if (recipeIngredients.contains("hazelnut")) {				
				DbConnection.insertRow(conn,"lchf_recipes_allergy_hazelnut",recipe);
			}
			else if (recipeIngredients.contains("sesame")) {				
				DbConnection.insertRow(conn,"lchfF_recipes_allergy_sesame",recipe);
			}
			else if (recipeIngredients.contains("walnut")) {				
				DbConnection.insertRow(conn,"lchf_recipes_allergy_walnut",recipe);
			}
			else if (recipeIngredients.contains("almond")) {
				DbConnection.insertRow(conn,"lchf_recipes_allergy_almond",recipe);
			}
			else if (recipeIngredients.contains("cashew")) {
				DbConnection.insertRow(conn,"lchf_recipes_allergy_cashew",recipe);
			}
			else if (recipeIngredients.contains("peanuts")) {
				DbConnection.insertRow(conn,"lchf_recipes_allergy_peanut",recipe);
			}
			else if (recipeIngredients.contains("pistachio")) {
				DbConnection.insertRow(conn,"lchf_recipes_allergy_pistachio",recipe);
			}
			
			
		}
	}

	public boolean isEliminateIngredientsPresent(List<String> elimnatedIngredients, String recipeIngredients) {
		recipeIngredients = recipeIngredients.toLowerCase();
		for (String elimnatedIngredient : elimnatedIngredients) {
			elimnatedIngredient = elimnatedIngredient.toLowerCase();
			if (recipeIngredients.contains(elimnatedIngredient)) {
				System.out.println("The eliminate ingredient is "+elimnatedIngredient+" and the recipe ingredient is "+recipeIngredients);
				return true;
			}
		}
		return false;
	}

	public boolean isAvoidItemsPresent(List<String> avoidItems, String recipeName) {
		recipeName = recipeName.toLowerCase();
		for (String avoidItem : avoidItems) {
			avoidItem = avoidItem.toLowerCase();
			if (recipeName.contains(avoidItem)) {
				System.out.println("The avoid ingredient is "+avoidItem+" and the recipe name is "+recipeName);

				return true;
			}
		}
		return false;
	}

	public boolean isAddIngredientsPresent(List<String> addIngredients, String recipeIngredients) {
		recipeIngredients = recipeIngredients.toLowerCase();
		for (String addIngredient : addIngredients) {
			addIngredient = addIngredient.toLowerCase();
			if (recipeIngredients.contains(addIngredient)) {
				System.out.println("The add ingredient is "+addIngredient+" and the recipe ingredient is "+recipeIngredients);
				return true;
			}
		}
		return false;
	}
	

}
