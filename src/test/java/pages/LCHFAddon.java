package pages;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import entity.NutrientValue;
import entity.Recipe;
import entity.RecipeIngredient;
import utilities.ExcelReaderCode;

public class LCHFAddon {

	//WebDriver driver;
		List<String> recipeCategoryCheckList = Arrays.asList("Breakfast","Lunch","Snacks","Dinner");
		List<String> foodCategoryCheckList = Arrays.asList("Vegetarian","Vegan","Non-Veg","Jain");
		List<String> cuisineCategoryCheckList = 
		Arrays.asList("Indian","Italian","Chinese","South Indian","Maharashtrian","Rajasthani","Punjabi","Gujarati");
		
		static List<String> eliminateList = new ArrayList<>();
		static List<String> addonList  = new ArrayList<>();

		/*
		 * public CommonMethods(WebDriver driver ) throws IOException {
		 * driver=BaseTest.initializeDriver(); } 
		 */
		static {
			initializeList();
		}
		public static void initializeList() {
			if(eliminateList == null || eliminateList.size() == 0) {
				File file = new File("src/test/resources/IngredientsAndComorbidities.xlsx");
				ExcelReaderCode excelReaderCode = new ExcelReaderCode(file.getAbsolutePath());
				Object[][] data = excelReaderCode.getSheetData("Final list for LCHFElimination");
				String eliminateItem = null;
				String addonItem = null;
				for(int i = 2; i < data.length; i++) {
					eliminateItem = (String)data[i][0];
					addonItem = (String)data[i][1];
					if(eliminateItem.length() > 0) {
						eliminateList.add(eliminateItem);
					}
					if(addonItem.length() > 0) {
						addonList.add(addonItem);
					}				
					
				}
				
			}
		}

		
		public Recipe recipeData(String url) throws IOException {
//			String url2 = "https://www.tarladalal.com/aam-ke-pakode-mango-bhajiya-36291r";
			
				Document document = Jsoup.connect(url).get();
				boolean hasEliminate = false;
				boolean hasAddon = false;
				//Recipe ID
			String recipeId=document.selectXpath("//form[@name='aspnetForm']").attr("action").split("recipeid=")[1];
			System.out.println("Recipe Id is: "+recipeId);
			
			//Recipe Name
			String recipeName=document.getElementById("ctl00_cntrightpanel_lblRecipeName").text();
			System.out.println("Recipe name is: "+recipeName);
					
			//Ingredients
			int ingreSize = document.selectXpath("//span[@itemprop='recipeIngredient']").size();
			String ingredients = "";
			Set<RecipeIngredient> recipeIngredients = new HashSet();
		
			for (int k = 1; k <= ingreSize; k++) {
				String eachIngrednt = document.selectXpath("//span[@itemprop='recipeIngredient'][" + k + "]/a/span[1]").text();
				String quantity = document.selectXpath("//span[@itemprop='recipeIngredient'][" + k + "]/span[1]").text();
				hasEliminate = hasEliminate || eliminateList.stream().anyMatch(e -> eachIngrednt.contains(e));
				
				hasAddon =  !hasEliminate &&
						(hasAddon || addonList.stream().anyMatch(a -> eachIngrednt.contains(a)));
				RecipeIngredient recipeIngredient = new RecipeIngredient() ;
				recipeIngredient.setIgredientName(eachIngrednt);
				recipeIngredient.setQuantity(quantity);
				recipeIngredients.add(recipeIngredient);
				
				ingredients = ingredients + eachIngrednt + "|";
			}
			
//		print list	
			 if (hasEliminate || hasAddon) {
		            System.out.println("Recipe contains ingredients from the specified lists:");
		            System.out.println("Ingredients: " + ingredients);
		            System.out.println("Eliminate List Ingredient Found: " + hasEliminate);
		            System.out.println("Addon List Ingredient Found: " + hasAddon);
		        }

			System.out.println("Ingredients are: "+ingredients);

			//Preparation time
			String prepTime = document.selectXpath("//time[@itemprop='prepTime']").text();
			System.out.println("Preparation time is: "+prepTime);
			
			//Cooking time
			String cookTime = document.selectXpath("//time[@itemprop='cookTime']").text();
			System.out.println("Cooking time is: "+cookTime);
			
			//Tags
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
			List<String> nutrientList = new ArrayList<>();
			int rows = document.selectXpath("//table[@id='rcpnutrients']//tr").size();
			Set<NutrientValue> nutrientValues = new HashSet<>();

			for (int r = 2; r <= rows; r++) { // Assuming the first row is headers
			    String nutrientName = document.selectXpath("//table[@id='rcpnutrients']//tr[" + r + "]/td[1]").text();
			    String nutrientValueStr = document.selectXpath("//table[@id='rcpnutrients']//tr[" + r + "]/td[2]").text();
			    
			    NutrientValue nutrientValue = new NutrientValue();
			    nutrientValue.setNutrientName(nutrientName);
			    nutrientValue.setNutrientValue(nutrientValueStr);
			    nutrientValues.add(nutrientValue);
			    
			    nutrientList.add(nutrientName + ": " + nutrientValueStr);
			}

			String nutrientVal = String.join("|", nutrientList);
			System.out.println("Nutrient value is: " + nutrientVal);
			
			//Recipe url
			System.out.println("Recipe url is: "+url);
			Recipe recipe = null;
			if(hasEliminate || hasAddon) {
				recipe = new Recipe();			
				recipe.setAddonOrEliminate(hasEliminate ? "Eliminate" : "Addon");
				recipe.setRecipeId(Long.parseLong(recipeId));
				recipe.setRecipeName(recipeName);
				recipe.setCuisineCategory(cuisineCategoryNames);
				recipe.setCookTime(cookTime);
				recipe.setPrepTime(prepTime);
//				recipe.setCookTime(Integer.parseInt(cookTime.split(" ")[0]));
//				recipe.setPrepTime(Integer.parseInt(prepTime.split(" ")[0]));
				recipe.setFoodCategory(foodCategoryNames);
//				recipe.setNoOfServing(noOfServings);
				try {
				recipe.setNoOfServings(Integer.parseInt(noOfServings));
				}catch(NumberFormatException e){
					System.err.println("Invalid format for no of serving");
				}
				recipe.setNutrientValues(nutrientValues);
				recipe.setPreparationMethod(prepMethods);
				recipe.setRecipeIngredients(recipeIngredients);
				recipe.setRecipeTags(tags);
				recipe.setRecipeUrl(url);
				recipe.setRecipeDesc(recDescrptn);
				recipe.setRecipeCategory(recipeCategoryNames);
			}
			return recipe;
		}		
}
