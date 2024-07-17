package pages;

import java.io.IOException;
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
import utilities.RecipeDataExtraction;
import utilities.RecipesFilterer;


 public class RecipeScraper extends BaseTest {
	WebDriver driver;
	RecipeDataExtraction recipeDataExtraction=new RecipeDataExtraction();
	RecipesFilterer recipesFilterer = new RecipesFilterer();
	int count;
	
	public RecipeScraper()  {
		try {
			driver=BaseTest.initializeDriver();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void recipeScraperWithJsoup() {
		
		//To click Recipe A to Z link
		driver.findElement(By.xpath("//*[@title='Recipea A to Z']")).click();
		
		//Here come the code for radio button, with for loop
		//Getting the list of page number link elements
		List<WebElement> alphabeticalIndex =driver.findElements(By.xpath("//a[contains(@class,'ctl00_cntleftpanel_mnuAlphabets_1')]"));
		//List<String> alphabeticalIndexText = alphabeticalIndex.stream().map(a -> a.findElement(By.xpath("//a[contains(@class,'ctl00_cntleftpanel_mnuAlphabets_1')]")).getText()).collect(Collectors.toList());

		String alphabeticIndexUrl= getUrl()+"RecipeAtoZ.aspx?beginswith=";
		List<String> recipeUrlList = new ArrayList<String>();
		//scrapedDataMappedList = new  ArrayList<>();
		
		//To iterate over pages based on Alphabetical index
		for (int i=1; i<(alphabeticalIndex.size()-4); i++) { //(alphabeticalIndex.size()-4)
			String alphabet= alphabeticalIndex.get(i).getText();
			Document document;
			try {
				document = Jsoup.connect(alphabeticIndexUrl+alphabet).get();
			
			
			//To iterate over pages based on page number index in a specific alphabet
			List<Element> pageNumberIndex= document.selectXpath("(//div[@style='text-align:right;padding-bottom:15px;'])[1]/a");
			String lastIndex=document.selectXpath("(//div[@style='text-align:right;padding-bottom:15px;'])[1]/a[last()]").attr("href");
					//System.out.println("print the number lastindex: "+lastIndex);
					//System.out.println("print the number lastindex after substring: "+lastIndex.substring(40));
			lastIndex = lastIndex.substring(lastIndex.indexOf("pageindex=") + 10);
			int noOfPages = Integer.parseInt(lastIndex);
			//System.out.println("print the number lastindex: "+noOfPages);		
		   // int lastPageIndex = Integer.parseInt(lastIndex.substring(40));
			for (int j=1; j<=noOfPages; j++) { //noOfPages
				String pageIndexUrl= document.selectXpath("(//a[text()='"+j+"'])[1]").attr("href");//
				//String pageIndexUrl=pageNumberIndex.get(j).getAttribute("href");
				//System.out.println("Print the page index url :"+getUrl()+pageIndexUrl);
				Document document1;
				try {
					document1 = Jsoup.connect(getUrl()+pageIndexUrl).get();
				
				//driver.navigate().to(pageIndexUrl);
				
				//To iterate over recipe cards in each page
				List<Element> recipeCardList=document1.select(".rcc_recipename");
				//System.out.println("Size of the recipe list :"+recipeCardList.size());
				
				for (int y=0; y<recipeCardList.size();y++) { //recipeCardList.size()
					String recipeHrefUrl=recipeCardList.get(y).select("a").attr("href");
					//System.out.println("Print the href of recipes before adding to list: "+getUrl()+recipeHrefUrl);
					try {
						Map<String, String>dataMappedToHeader= recipeDataExtraction.recipeData(getUrl()+recipeHrefUrl); //getUrl()+recipeHrefUrl
						//scrapedDataMappedList.add(dataMappedToHeader);
						recipesFilterer.LFVEliminatedRecipes(dataMappedToHeader);
						recipesFilterer.LCHFEliminatedRecipes(dataMappedToHeader);
						recipesFilterer.LFVAddRecipes(dataMappedToHeader);
						recipesFilterer.LCHFAddRecipes(dataMappedToHeader);
						//System.out.println("All data collected as list: "+scrapedDataMappedList);
					} catch (IOException e) {
						e.printStackTrace();
					}
					//recipeUrlList.add(recipeHrefUrl);
					//System.out.println("Print the href of recipes: "+recipeUrlList);
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
