package utilities;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import base.BaseTest;
import entity.Recipe;
import pages.LCHFAddon;


public class RecipeScraper extends BaseTest {
	WebDriver driver;
	LCHFAddon lchfaddon =new LCHFAddon();
	
	public RecipeScraper() throws IOException {
		driver=BaseTest.initializeDriver();
	}
	
	public static Connection getDBConenction() {
		return DbConnection.connectToDb("postgres", "scrap_user", "pasword123");
	}
	
	@Test
	public void recipeScraperWithJsoup() throws InterruptedException, IOException {
		
		Connection conn = getDBConenction();
		
		//To click Recipe A to Z link
		driver.findElement(By.xpath("//*[@title='Recipea A to Z']")).click();
		
		//Getting the list of page number link elements
		List<WebElement> alphabeticalIndex =driver.findElements(By.xpath("//a[contains(@class,'ctl00_cntleftpanel_mnuAlphabets_1')]"));
		//List<String> alphabeticalIndexText = alphabeticalIndex.stream().map(a -> a.findElement(By.xpath("//a[contains(@class,'ctl00_cntleftpanel_mnuAlphabets_1')]")).getText()).collect(Collectors.toList());

		String alphabeticIndexUrl= getUrl()+"RecipeAtoZ.aspx?beginswith=";
		List<String> recipeUrlList = new ArrayList<String>();

		//To iterate over pages based on Alphabetical index
		for (int i=1; i<(alphabeticalIndex.size()-1); i++) { //(alphabeticalIndex.size()-1)
			String alphabet= alphabeticalIndex.get(i).getText();
			Document document = Jsoup.connect(alphabeticIndexUrl+alphabet).get();
			
			//To iterate over pages based on page number index in a specific alphabet
			List<Element> pageNumberIndex= document.selectXpath("(//div[@style='text-align:right;padding-bottom:15px;'])[1]/a");
			String lastIndex=document.selectXpath("(//div[@style='text-align:right;padding-bottom:15px;'])[1]/a[last()]").attr("href");//.substring(52);
					System.out.println("print the number lastindex: "+lastIndex);
					System.out.println("print the number lastindex after substring: "+lastIndex.substring(40));		
		    int lastPageIndex = Integer.parseInt(lastIndex.substring(40));
			for (int j=1; j<=lastPageIndex; j++) { //lastPageIndex
				String pageIndexUrl= document.selectXpath("(//a[text()='"+j+"'])[1]").attr("href");//
				//String pageIndexUrl=pageNumberIndex.get(j).getAttribute("href");
				System.out.println("Print the page index url :"+getUrl()+pageIndexUrl);
				Document document1 = Jsoup.connect(getUrl()+pageIndexUrl).get();
				//driver.navigate().to(pageIndexUrl);
				
				//To iterate over recipe cards in each page
				List<Element> recipeCardList=document1.select(".rcc_recipename");
				System.out.println("Size of the recipe list :"+recipeCardList.size());
				
				
				
				for (int y=0; y<recipeCardList.size();y++) { //recipeCardList.size()
					String recipeHrefUrl=recipeCardList.get(y).select("a").attr("href");
					System.out.println("Print the href of recipes before adding to list: "+getUrl()+recipeHrefUrl);
					try {
						Recipe recipe = lchfaddon.recipeData(getUrl()+recipeHrefUrl);
						if(recipe != null) {							
							DbConnection.insertRecipe(conn, recipe);							
						}
						
					} catch (IOException e) {
						e.printStackTrace();
					}
					//recipeUrlList.add(recipeHrefUrl);
					//System.out.println("Print the href of recipes: "+recipeUrlList);
				}		
				
				//pageNumberIndex= document.selectXpath("(//div[@style='text-align:right;padding-bottom:15px;'])[1]/a");
			}			
			
			//alphabeticalIndex =driver.findElements(By.xpath("//table/tbody/tr/td[@onmouseover='Menu_HoverStatic(this)']"));
		}
		driver.quit();
		
	}
	
	
	
	
}
