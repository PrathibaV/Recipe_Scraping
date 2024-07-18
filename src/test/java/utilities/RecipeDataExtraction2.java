package utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import pages.Recipe;

public class RecipeDataExtraction2 {
	List<String> recipeCategoryCheckList = Arrays.asList("breakfast","lunch","snacks","dinner");
	List<String> foodCategoryCheckList = Arrays.asList("vegetarian","vegan","non-veg","jain");
	List<String> cuisineCategoryCheckList = Arrays.asList("gujarati","punjabi","rajasthani","maharashtrian","south indian","italian","chinese","indian");

	public Recipe recipeData(String url) throws IOException {
		//String url = "https://www.tarladalal.com/dal-khichdi-39570r";
		
			Document document = Jsoup.connect(url).get();
			
			Recipe recipeObj = new Recipe();						
			
		//Recipe ID
		String recipeId=document.selectXpath("//form[@name='aspnetForm']").attr("action").split("recipeid=")[1];
		recipeObj.setRecipeID(Integer.parseInt(recipeId));
		
		//Recipe Name
		String recipeName=document.getElementById("ctl00_cntrightpanel_lblRecipeName").text();
		recipeObj.setRecipeName(recipeName);
				
		//Ingredients
		int ingreSize = document.selectXpath("//span[@itemprop='recipeIngredient']").size();
		String ingredients = "";
		for (int k = 1; k <= ingreSize; k++) {
			String eachIngrednt = document.selectXpath("//span[@itemprop='recipeIngredient'][" + k + "]").text();
			ingredients = ingredients + eachIngrednt + "|";
		}
		recipeObj.setIngredients(ingredients);

		//Preparation time
		String prepTime = document.selectXpath("//time[@itemprop='prepTime']").text();
		recipeObj.setPreparationTime(prepTime);
		
		//Cooking time
		String cookTime = document.selectXpath("//time[@itemprop='cookTime']").text();
		recipeObj.setCookingTime(cookTime);

		//Tags
		List<Element> recipeTagList=document.selectXpath("//div[@id='recipe_tags']/a/span");
		String tags= ""; 
		for (Element recipeTag: recipeTagList) {
			String tagConcat= recipeTag.text();			
			tags= tags+tagConcat+"|";
		}
		if (!tags.isEmpty()) {
			tags = tags.substring(0, tags.length() - 1);
		}
		recipeObj.setTag(tags);  

		//Recipe category
		String recipeCategoryNames="";
		for (Element recipeTagName: recipeTagList) {
			for (String recipeCategory : recipeCategoryCheckList) {
				if (recipeTagName.text().toLowerCase().contains(recipeCategory.toLowerCase())) {
					 if (!recipeCategoryNames.toLowerCase().contains(recipeCategory.toLowerCase() + "|")) {
			                recipeCategoryNames = recipeCategoryNames + recipeCategory + "|";
			            }
					 break;
		        }	
			}
		}
		if (!recipeCategoryNames.isEmpty()) {
		    recipeCategoryNames = recipeCategoryNames.substring(0, recipeCategoryNames.length() - 1);
		}
		recipeObj.setRecipeCategory(recipeCategoryNames);
					
		//Food category
		String foodCategoryNames="";
		for (Element recipeTagElement : recipeTagList) {		    		    
		    for (String foodCategory : foodCategoryCheckList) {
		        if (recipeTagElement.text().toLowerCase().contains(foodCategory.toLowerCase())) {
		        	if (!foodCategoryNames.toLowerCase().contains(foodCategory.toLowerCase() + "|")) {
		        		foodCategoryNames = foodCategoryNames + foodCategory + "|";
		            }		            
		        	break; 
		        }
		    }
	}
		if (!foodCategoryNames.isEmpty()) {
			foodCategoryNames = foodCategoryNames.substring(0, foodCategoryNames.length() - 1);
		}
		recipeObj.setFoodCategory(foodCategoryNames);            
	  //No of servings
		String noOfServings = document.selectXpath("//span[@itemprop='recipeYield']").text();
		recipeObj.setServings(noOfServings);
		
		//Cuisine category
		String cuisineCategoryNames="";
		for (Element recipeTagName: recipeTagList) {
			for (String cuisineCategory : cuisineCategoryCheckList) {
				if (recipeTagName.text().toLowerCase().contains(cuisineCategory.toLowerCase())) {
					if (!cuisineCategoryNames.toLowerCase().contains(cuisineCategory.toLowerCase() + "|")) {
						cuisineCategoryNames = cuisineCategoryNames + cuisineCategory + "|";
		            }
		        	break; 
		        }	
			}
		}
		if (!cuisineCategoryNames.isEmpty()) {
			cuisineCategoryNames = cuisineCategoryNames.substring(0, cuisineCategoryNames.length() - 1);
		}
    	recipeObj.setCuisineCategory(cuisineCategoryNames);
		
		//Recipe Description
    	String recDescrptn = document.getElementById("ctl00_cntrightpanel_lblDesc").text();
    	recipeObj.setRecipeDescription(recDescrptn);

		//Preparation method
    	List<Element> methodList = document.selectXpath("//div[@id='recipe_small_steps']/span/ol/li");
    	String prepMethods = "";
		for (Element methods : methodList) {
			prepMethods = prepMethods + methods.text() + "\n";
		}
		recipeObj.setPreparationMethod(prepMethods);

		//Nutrient values
		List<String> nutrientList = new ArrayList<String>();
		int rows = document.selectXpath("//table[@id='rcpnutrients']//tr").size();
		int cols = document.selectXpath("//table[@id='rcpnutrients']//tr[1]/td").size();
		for (int r=1; r<=rows; r++) {
			String nutrientVal = "";
			for (int c=1; c<=cols; c++) {
				String nutrients = 
						document.selectXpath("//table[@id='rcpnutrients']//tr["+r+"]/td["+c+"]").text();
				nutrientVal = nutrientVal+nutrients;
				}
			nutrientList.add(nutrientVal);
		}
		String nutrientVal="";
		for (String nutrients : nutrientList) {
			nutrientVal = nutrientVal+nutrients+"|";
		}
		recipeObj.setNutrientValues(nutrientVal);
		
		//Recipe url
		//System.out.println("Recipe url is: "+url);
		recipeObj.setRecipeURL(url);
		
		return recipeObj;
	}		
}
	

