package utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.testng.annotations.Test;


public class RecipeDataExtraction {
	//WebDriver driver;
	List<String> recipeCategoryCheckList = Arrays.asList("Breakfast","Lunch","Snacks","Dinner");
	List<String> foodCategoryCheckList = Arrays.asList("Vegetarian","Vegan","Non-Veg");
	List<String> cuisineCategoryCheckList = Arrays.asList("Indian");

	/*
	 * public CommonMethods(WebDriver driver ) throws IOException {
	 * driver=BaseTest.initializeDriver(); } 
	 */


	@Test
	public void recipeData(String url) throws IOException {
		//String url = "https://www.tarladalal.com/dal-khichdi-39570r";

			Document document = Jsoup.connect(url).get();

			//Recipe ID
		String recipeId=document.selectXpath("//form[@name='aspnetForm']").attr("action").split("recipeid=")[1];
		System.out.println("Recipe Id is: "+recipeId);

		//Recipe Name
		String recipeName=document.getElementById("ctl00_cntrightpanel_lblRecipeName").text();
		System.out.println("Recipe name is: "+recipeName);

		//Ingredients
		int ingreSize = document.selectXpath("//span[@itemprop='recipeIngredient']").size();
		String ingredients = "";
		for (int k = 1; k <= ingreSize; k++) {
			String eachIngrednt = document.selectXpath("//span[@itemprop='recipeIngredient'][" + k + "]").text();
			ingredients = ingredients + eachIngrednt + "|";
		}
		System.out.println("Ingredients are: "+ingredients);

		//Preparation time
		String prepTime = document.selectXpath("//time[@itemprop='prepTime']").text();
		System.out.println("Preparation time is: "+prepTime);

		//Cooking time
		String cookTime = document.selectXpath("//time[@itemprop='cookTime']").text();
		System.out.println("Cooking time is: "+cookTime);

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
		System.out.println("Tags are: "+tags);

		//Recipe category
				String recipeCategoryNames="";
		for (Element recipeTagName: recipeTagList) {
			for (String recipeCategory : recipeCategoryCheckList) {
				if (recipeTagName.text().contains(recipeCategory)) {
					 if (!recipeCategoryNames.contains(recipeCategory + "|")) {
			                recipeCategoryNames = recipeCategoryNames + recipeCategory + "|";
			            }
					 break;
		        }	
			}
		}
		if (!recipeCategoryNames.isEmpty()) {
		    recipeCategoryNames = recipeCategoryNames.substring(0, recipeCategoryNames.length() - 1);
		}
		System.out.println("The recipe category is: "+recipeCategoryNames);

		//Food category
		String foodCategoryNames="";
		for (Element recipeTagElement : recipeTagList) {		    		    
		    for (String foodCategory : foodCategoryCheckList) {
		        if (recipeTagElement.text().contains(foodCategory)) {
		        	if (!foodCategoryNames.contains(foodCategory + "|")) {
		        		foodCategoryNames = foodCategoryNames + foodCategory + "|";
		            }		            
		        	break; 
		        }
		    }
	}
		if (!foodCategoryNames.isEmpty()) {
			foodCategoryNames = foodCategoryNames.substring(0, foodCategoryNames.length() - 1);
		}
		System.out.println("The food category is: "+foodCategoryNames);

	//No of servings
		String noOfServings = document.selectXpath("//span[@itemprop='recipeYield']").text();
		System.out.println("No of servings is: "+noOfServings);

		//Cuisine category
		String cuisineCategoryNames="";
		for (Element recipeTagName: recipeTagList) {
			for (String cuisineCategory : cuisineCategoryCheckList) {
				if (recipeTagName.text().contains(cuisineCategory)) {
					if (!cuisineCategoryNames.contains(cuisineCategory + "|")) {
						cuisineCategoryNames = cuisineCategoryNames + cuisineCategory + "|";
		            }
		        	break; 
		        }	
			}
		}
		if (!cuisineCategoryNames.isEmpty()) {
			cuisineCategoryNames = cuisineCategoryNames.substring(0, cuisineCategoryNames.length() - 1);
		}
    	System.out.println("The cuisine category is: "+cuisineCategoryNames);		            


		//Recipe Description
    	String recDescrptn = document.getElementById("ctl00_cntrightpanel_lblDesc").text();
		System.out.println("Recipe description is: "+recDescrptn);

		//Preparation method
    	List<Element> methodList = document.selectXpath("//div[@id='recipe_small_steps']/span/ol/li");
    	String prepMethods = "";
		for (Element methods : methodList) {
			prepMethods = prepMethods + methods.text() + "\n";
		}
		System.out.println("Preparation method is: "+prepMethods);


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
		System.out.println("Nutrient value is: "+nutrientVal);


		//Recipe url
		System.out.println("Recipe url is: "+url);

	}		
}



	

