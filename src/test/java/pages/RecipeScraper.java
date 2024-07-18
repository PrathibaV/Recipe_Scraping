package pages;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import base.BaseTest;
import utilities.DbConnection;
import utilities.RecipeDataExtraction;
import utilities.RecipesFilterer;


 public class RecipeScraper extends BaseTest {
	WebDriver driver;
	RecipeDataExtraction recipeDataExtraction=new RecipeDataExtraction();
	RecipesFilterer recipesFilterer = new RecipesFilterer();
	int count;
	
	public RecipeScraper()  {
		try {
			
			this.driver=BaseTest.initializeDriver();	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void recipeScraperWithJsoup() throws SocketTimeoutException {
		 
		
		//To click Recipe A to Z link
		driver.findElement(By.xpath("//*[@title='Recipea A to Z']")).click();
		
		//Getting the list of page number link elements
		List<WebElement> alphabeticalIndex =driver.findElements(By.xpath("//a[contains(@class,'ctl00_cntleftpanel_mnuAlphabets_1')]"));
		//List<String> alphabeticalIndexText = alphabeticalIndex.stream().map(a -> a.findElement(By.xpath("//a[contains(@class,'ctl00_cntleftpanel_mnuAlphabets_1')]")).getText()).collect(Collectors.toList());

		String alphabeticIndexUrl= getUrl()+"RecipeAtoZ.aspx?beginswith=";
		List<String> recipeUrlList = new ArrayList<String>();
		
		//To iterate over pages based on Alphabetical index
		for (int i=1; i<(alphabeticalIndex.size()-4); i++) { //(alphabeticalIndex.size()-4)
			String alphabet= alphabeticalIndex.get(i).getText();
			try {
				Document  document = Jsoup.connect(alphabeticIndexUrl+alphabet).timeout(30 * 1000).get();
			
			
			//To iterate over pages based on page number index in a specific alphabet
			List<Element> pageNumberIndex= document.selectXpath("(//div[@style='text-align:right;padding-bottom:15px;'])[1]/a");
			String lastIndex=document.selectXpath("(//div[@style='text-align:right;padding-bottom:15px;'])[1]/a[last()]").attr("href");
					
			lastIndex = lastIndex.substring(lastIndex.indexOf("pageindex=") + 10);
			int noOfPages = Integer.parseInt(lastIndex);
			
			for (int j=0; j<=noOfPages; j++) { //noOfPages
				String pageIndexUrl= document.selectXpath("(//a[text()='"+j+"'])[1]").attr("href");//
				
				Document document1;
				try {
				  document1 = Jsoup.connect(getUrl()+pageIndexUrl).timeout(30 * 1000).get();
				
				//To iterate over recipe cards in each page
				List<Element> recipeCardList=document1.select(".rcc_recipename");
				//System.out.println("Size of the recipe list :"+recipeCardList.size());
				
				for (int y=0; y<recipeCardList.size();y++) { //recipeCardList.size()
					String recipeHrefUrl=recipeCardList.get(y).select("a").attr("href");
					System.out.println("Href url of the recipe: "+recipeHrefUrl);
					Map<String, String>dataMappedToHeader= recipeDataExtraction.recipeData(getUrl()+recipeHrefUrl); //getUrl()+recipeHrefUrl
					
					  recipesFilterer.LFVEliminatedRecipes(dataMappedToHeader);
					  recipesFilterer.LCHFEliminatedRecipes(dataMappedToHeader);
					  recipesFilterer.LFVAddRecipes(dataMappedToHeader);
					  recipesFilterer.LCHFAddRecipes(dataMappedToHeader);
					  recipesFilterer.LFVAllergicIngredientsRecipes(dataMappedToHeader);
					  recipesFilterer.LCHFAllergicIngredientsRecipes(dataMappedToHeader);

					System.out.println("Print the href of recipes: "+recipeUrlList);
					count++;
					System.out.println("Count: "+count);
				}				
			}			
			 catch (IOException e) {
				e.printStackTrace();
			}
		}		 
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		}
		driver.quit();
		
	}
	
		
}
