package pages;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import base.BaseTest;
import utilities.DbConnection;
import utilities.ExcelReader;
//import utilities.RecipeDataExtraction;
import utilities.RecipeDataExtraction2;

public class LFVRecipeWithAddOnIngredients extends BaseTest {

	WebDriver driver;
	RecipeDataExtraction2 recipeDataExtraction = new RecipeDataExtraction2();
	DbConnection db = new DbConnection();
	Connection conn =null;
	
	
	
	public LFVRecipeWithAddOnIngredients() throws IOException {

		driver = BaseTest.initializeDriver();
	}

	public List<Recipe> scrappedRecipesList() throws IOException {

		List<Recipe> recipeList = new ArrayList<>();

		driver.findElement(By.xpath("//*[@title='Recipea A to Z']")).click();

		// Getting the list of page number link elements
		List<WebElement> alphabeticalIndex = driver
				.findElements(By.xpath("//a[contains(@class,'ctl00_cntleftpanel_mnuAlphabets_1')]"));

		String alphabeticIndexUrl = getUrl() + "RecipeAtoZ.aspx?beginswith=";

		// To iterate over pages based on Alphabetical index
		for (int i = 22; i < 23; i++) {
			// (alphabeticalIndex.size()-1)
			String alphabet = alphabeticalIndex.get(i).getText();
			System.out.println("alphabet :-"+alphabet);
			Document document = Jsoup.connect(alphabeticIndexUrl + alphabet).get();

			// To iterate over pages based on page number index in a specific alphabet
			List<Element> pageNumberIndex = document.selectXpath("(//div[@style='text-align:right;padding-bottom:15px;'])[1]/a");
			String lastIndex = document
					.selectXpath("(//div[@style='text-align:right;padding-bottom:15px;'])[1]/a[last()]").attr("href");// .substring(52);

			//System.out.println("print the number lastindex after substring: " + lastIndex.substring(40));

			int lastPageIndex = Integer.parseInt(lastIndex.substring(40));
			for (int j = 1; j <= lastPageIndex-1; j++) {
				// lastPageIndex
				String pageIndexUrl = document.selectXpath("(//a[text()='" + j + "'])[1]").attr("href");//
				// System.out.println("Print the page index url :"+getUrl()+pageIndexUrl);
				Document document1 = Jsoup.connect(getUrl() + pageIndexUrl).get();

				// To iterate over recipe cards in each page
				List<Element> recipeCardList = document1.select(".rcc_recipename");
				// System.out.println("Size of the recipe list :"+recipeCardList.size());

				for (int y = 0; y < recipeCardList.size(); y++) { // recipeCardList.size()
					String recipeHrefUrl = recipeCardList.get(y).select("a").attr("href");
					// System.out.println("Print the href of recipes before adding to list:
					// "+getUrl()+recipeHrefUrl);
					try {

						Recipe recipeObj = recipeDataExtraction.recipeData(getUrl() + recipeHrefUrl);
						recipeList.add(recipeObj);

					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
		}
		driver.quit();
		return recipeList;
	}

	
	@Test
	public void scrappLFVRecipeWithAddOnIngr() throws IOException {


		
		ExcelReader excelReader = new ExcelReader();

		List<String> eleminationIngredientList = excelReader.getColDataFromSheet("Final list for LFV Elimination ", "Eliminate");
		List<String> addOnIngredientList = excelReader.getColDataFromSheet("Final list for LFV Elimination ", "Add");
		List<String> avoidRecipeList = excelReader.getColDataFromSheet("Final list for LFV Elimination ", "Recipes to avoid");


		List<Recipe> scrappedRecipeList =scrappedRecipesList();

		// Filtering recipe:--- Verifying ingredient list contains eliminated item or not ,
		//if not contains then check AddOn item list and add to pojo cls if found add on item
		conn = db.connectToDb("recipescrapping", "postgres", "test123");
		db.createTable(conn, "LFV_Filtered_Recipes_Without_AddOn");
		db.createTable(conn, "LFV_Filtered_Recipes_With_AddOn");
		
		for (Recipe recipe : scrappedRecipeList)
		{
			boolean isValidRecipe = true;
			for(String eleminationIngredient : eleminationIngredientList) 
			{
				if(recipe.getIngredients().toLowerCase().contains(eleminationIngredient.toLowerCase())) 
				{
					isValidRecipe = false; 
					System.out.println("elemination Ingredient found :-"+eleminationIngredient);
					break;
				} 
			}
			
			if(isValidRecipe) {
				
				for(String avoidRecipe : avoidRecipeList) 
				{
					if(recipe.getTag().toLowerCase().contains(avoidRecipe.toLowerCase()) 
							||recipe.getRecipeName().toLowerCase().contains(avoidRecipe.toLowerCase())) 
					{
						isValidRecipe = false; 
						System.out.println("avoided recipe found :-"+avoidRecipe);
						break;
					} 
				}
			}
			
			if(isValidRecipe) {
				insertRecipeToDB("LFV_Filtered_Recipes_Without_AddOn", recipe);
			}
			
			if(isValidRecipe)
			{
				for(String addOnIngredient :addOnIngredientList) {

					if(recipe.getIngredients().toLowerCase().contains(addOnIngredient.toLowerCase()))
					{
						System.out.println("addOn Ingredient found :-"+addOnIngredient);
						insertRecipeToDB("LFV_Filtered_Recipes_With_AddOn", recipe);
						break;
					}

				}

			}
		}
	}

	private void insertRecipeToDB(String tableName, Recipe recipe) {
		db.insertRow(conn, tableName, recipe.getRecipeID(), recipe.getRecipeName(), recipe.getRecipeCategory(),
				 recipe.getFoodCategory(), recipe.getIngredients(), recipe.getPreparationTime(), recipe.getCookingTime(), recipe.getTag(), recipe.getServings(),
				 recipe.getCuisineCategory(), recipe.getRecipeDescription(), recipe.getPreparationMethod(), recipe.getNutrientValues(), recipe.getRecipeURL());
	}
		
}
