package pages;

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
import utilities.ReadExcel;
import utilities.RecipeDataExtraction;
import utilities.RecipeScraper;

public class RecipesFilterer  {
	private List<Map<String,String>> scrapedDataMappedList= new  ArrayList<>();
	ReadExcel readExcel = new ReadExcel();
	int count;
	
		public void LFVEliminatedRecipes(Map<String, String> recipe) throws IOException {
		//RecipeScraper recipeScraper = new RecipeScraper();;

		List<String> LFVEliminateIngredients= readExcel.getRecipeFilterItemsList("Final list for LFV Elimination ",0);
		List<String> LFVRecipesToAvoidIngredients= readExcel.getRecipeFilterItemsList("Final list for LFV Elimination ",3);

		//System.out.println("######################### Excel LFVEliminateIngredients: "+LFVEliminateIngredients);
		//System.out.println("######################### Excel LFVRecipesToAvoidIngredients: "+LFVRecipesToAvoidIngredients);

		//scrapedDataMappedList = recipeScraper.getScrapedDataMappedList();
		System.out.println("********************** LFVeliminate received the recipe: "+recipe);
		List<Map<String,String>> eliminatedRecipesList = new ArrayList();
		//List<Map<String,String>> recipesAfterEliminationList = new ArrayList();

		//Iterate through scrapedDataMappedList to get the ingredients and then filter the recipes
		//for (Map<String,String> recipeMappedData: scrapedDataMappedList) {
			String recipeIngredients= recipe.get("Ingredients");
			String recipeName= recipe.get("Recipe Name");
			
			boolean eliminateRecipe = false;
			boolean avoidRecipe = false;
			
			// Compare recipeIngredients with LFVEliminateIngredients
		    for (String ingredient : LFVEliminateIngredients) {
		        if (recipeIngredients.contains(ingredient)) {
		        	eliminateRecipe = true;
		            break;
		        }
		    }

		    // Compare recipeName with LFVRecipesToAvoidIngredients
		    for (String avoidItem : LFVRecipesToAvoidIngredients) {
		        String[] avoidWords = avoidItem.split(" "); 

		        // Check if any word in avoidItem is a substring of recipeName		       
		        for (String word : avoidWords) {
		            if (recipeName.contains(word)) {
		            	avoidRecipe = true;
		                break;
		            }
		        }

		        if (avoidRecipe) {
		            break;
		        }
		    }

		    // If either flag is true, add it to eliminatedRecipesList
		    if (eliminateRecipe || avoidRecipe) {
		    	eliminatedRecipesList.add(recipe);
				System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$ The eliminated recipe for LFV is: "+recipe);
		    }
		    
			System.out.println("The number of eliminated recipes for LFV are: "+eliminatedRecipesList.size());
		}
		

	}		

	
