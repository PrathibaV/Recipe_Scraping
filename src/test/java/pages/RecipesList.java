package pages;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

import base.BaseTest;

public class RecipesList extends BaseTest{
	WebDriver driver;
	public RecipesList() throws IOException {
		driver=BaseTest.initializeDriver();
	}

	@Test
	public void getA_ZRecipesList() throws InterruptedException {
		
		//To click Recipe A to Z link
		driver.findElement(By.xpath("//*[@title='Recipea A to Z']")).click();
		
		//Getting the list of page number link elements
		List<WebElement> alphabeticalIndex =driver.findElements(By.xpath("//a[contains(@class,'ctl00_cntleftpanel_mnuAlphabets_1')]"));
		//List<String> alphabeticalIndexText = alphabeticalIndex.stream().map(a -> a.findElement(By.xpath("//a[contains(@class,'ctl00_cntleftpanel_mnuAlphabets_1')]")).getText()).collect(Collectors.toList());

		String alphabeticIndexUrl= getUrl()+"RecipeAtoZ.aspx?beginswith=";
		List<String> recipeUrlList = new ArrayList<String>();

		//To iterate over pages based on Alphabetical index
		for (int i=1; i<(alphabeticalIndex.size()-1); i++) { 
			String alphabet= alphabeticalIndex.get(i).getText();
			driver.navigate().to(alphabeticIndexUrl+alphabet);
			
			//To iterate over pages based on page number index in a specific alphabet
			List<WebElement> pageNumberIndex= driver.findElements(By.xpath("(//div[@style='text-align:right;padding-bottom:15px;'])[1]/a"));
			String lastIndex=driver.findElement(By.xpath("(//div[@style='text-align:right;padding-bottom:15px;'])[1]/a[last()]")).getAttribute("href");//.substring(52);

					System.out.println("print the number lastindex: "+lastIndex);
					System.out.println("print the number lastindex after substring: "+lastIndex.substring(66));		
		    int lastPageIndex = Integer.parseInt(lastIndex.substring(66));
			for (int j=0; j<lastPageIndex; j++) { 
				
				String pageIndexUrl=pageNumberIndex.get(j).getAttribute("href");
				System.out.println("Print the page index url :"+pageIndexUrl);
				driver.navigate().to(pageIndexUrl);
				
				//To iterate over recipe cards in each page
				List<WebElement> recipeCardList=driver.findElements(By.className("rcc_recipename"));
				System.out.println("Size of the recipe list :"+recipeCardList.size());
				
				for (int y=0; y<recipeCardList.size();y++) { 
					String recipeHrefUrl=recipeCardList.get(y).findElement(By.tagName("a")).getAttribute("href");
					System.out.println("Print the href of recipes before adding to list: "+recipeHrefUrl);
					recipeUrlList.add(recipeHrefUrl);
					System.out.println("Print the href of recipes: "+recipeUrlList);
				}		
				
				pageNumberIndex= driver.findElements(By.xpath("(//div[@style='text-align:right;padding-bottom:15px;'])[1]/a"));
			}			
			
			alphabeticalIndex =driver.findElements(By.xpath("//table/tbody/tr/td[@onmouseover='Menu_HoverStatic(this)']"));
		}
		driver.quit();
	}
}
