package utilities;

import java.io.IOException;
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
	private List<Map<String, String>> scrapedDataMappedList = new ArrayList<>();
	ReadExcel readExcel = new ReadExcel();
	int count;

	public void LFVEliminatedRecipes(Map<String, String> recipe) {
		List<String> lfvEliminateIngredients = readExcel.getRecipeFilterItemsList("Final list for LFV Elimination ", 0);
		List<String> lfvRecipesToAvoidIngredients = readExcel
				.getRecipeFilterItemsList("Final list for LFV Elimination ", 3);

		String recipeIngredients = recipe.get("Ingredients");
		String recipeName = recipe.get("Recipe Name");
		if (isEliminateIngredientsPresent(lfvEliminateIngredients, recipeIngredients)
				|| isAvoidItemsPresent(lfvRecipesToAvoidIngredients, recipeName)) {
			System.out.println(
					"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ The eliminated recipe for LFV is: "
							+ recipe);
		}
	}

	public void LCHFEliminatedRecipes(Map<String, String> recipe) throws IOException {
		List<String> lchfEliminateIngredients = readExcel.getRecipeFilterItemsList("Final list for LCHFElimination ",
				0);
		List<String> lchfRecipesToAvoidIngredients = readExcel
				.getRecipeFilterItemsList("Final list for LCHFElimination ", 2);

		String recipeIngredients = recipe.get("Ingredients");
		String recipeName = recipe.get("Recipe Name");

		if (isEliminateIngredientsPresent(lchfEliminateIngredients, recipeIngredients)
				|| isAvoidItemsPresent(lchfRecipesToAvoidIngredients, recipeName)) {
			System.out.println(
					"#################################################################### The eliminated recipe for LCHF is: "
							+ recipe);
		}
	}

	public void LFVAddRecipes(Map<String, String> recipe) throws IOException {
		List<String> lfvAddIngredients = readExcel.getRecipeFilterItemsList("Final list for LFV Elimination ", 1);
		List<String> lfvEliminateIngredients = readExcel.getRecipeFilterItemsList("Final list for LFV Elimination ", 0);
		List<String> lfvRecipesToAvoidIngredients = readExcel
				.getRecipeFilterItemsList("Final list for LCHFElimination ", 3);

		String recipeIngredients = recipe.get("Ingredients");
		String recipeName = recipe.get("Recipe Name");

		if (!isEliminateIngredientsPresent(lfvEliminateIngredients, recipeIngredients)
				&& !isAvoidItemsPresent(lfvRecipesToAvoidIngredients, recipeName)) {
			if (isAddIngredientsPresent(lfvAddIngredients, recipeIngredients)) {
				System.out.println(
						"$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ The to add recipe for LFV is: "
								+ recipe);
			}
		}
	}

	public void LCHFAddRecipes(Map<String, String> recipe) throws IOException {
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
				System.out.println(
						"********************************************************************************** The to add recipe for LCHF is: "
								+ recipe);
			}
		}
	}

	public boolean isEliminateIngredientsPresent(List<String> elimnatedIngredients, String recipeIngredients) {
		recipeIngredients = recipeIngredients.toLowerCase();
		for (String elimnatedIngredient : elimnatedIngredients) {
			elimnatedIngredient = elimnatedIngredient.toLowerCase();
			if (recipeIngredients.contains(elimnatedIngredient)) {
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
				return true;
			}
		}
		return false;
	}

}
