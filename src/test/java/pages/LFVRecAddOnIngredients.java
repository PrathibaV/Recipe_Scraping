package pages;

import java.io.IOException;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import base.BaseTest;
import utilities.CommonMethods;

public class LFVRecAddOnIngredients extends BaseTest {

	public LFVRecAddOnIngredients() throws IOException {
		driver = BaseTest.initializeDriver();
	}

	@Test
	public void scrapeLFVRecipes() {
		WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(10));
		int count = 0;
		driver.findElement(By.linkText("Recipe A To Z")).click();
		int recLinkSize = CommonMethods.findTotalRecLinks();
		for (int x = 2; x <= recLinkSize; x++) {
			driver.findElement(By.xpath("//table/tbody/tr/td[@onmouseover='Menu_HoverStatic(this)'][" + x + "]"))
					.click();

			int noOfPages = CommonMethods.findNumOfPages();
			for (int i = 1; i <= noOfPages; i++) {
				driver.findElement(
						By.xpath("//div[@id='maincontent']/div[1]/div[2]/a[contains (@href,'pageindex=" + i + "')]"))
						.click();
				int eachPageRecSize = CommonMethods.findNumOfRecInEachPage();
				System.out.println("Recipe Size is " + eachPageRecSize);

				for (int j = 1; j <= eachPageRecSize; j++) {
					explicitWait.until(ExpectedConditions.visibilityOfElementLocated(
							By.xpath("//div/div[@class='rcc_recipecard'][" + j + "]/div[3]/span[1]/a")));
					String recName = driver
							.findElement(By.xpath("//div/div[@class='rcc_recipecard'][" + j + "]/div[3]/span[1]/a"))
							.getText();
					System.out.println("Recipe Name is " + recName);
					String recId = driver
							.findElement(By.xpath("//div/div[@class='rcc_recipecard'][" + j + "]/div[2]/span[1]"))
							.getText();
					recId = recId.replaceAll("[^0-9]+", " ").split(" ")[1];
					System.out.println("Recipe Id is " + recId);
					// clicking each recipe
					driver.findElement(By.xpath("//div/div[@class='rcc_recipecard'][" + j + "]/div[3]/span[1]/a"))
							.click();
					driver.navigate().refresh();
					String recDescrptn = CommonMethods.findRecDescrptn();
					System.out.println("Description is " + recDescrptn);
					String tags = CommonMethods.findTags();
					System.out.println("Tags are " + tags);
					String url = CommonMethods.findRecUrl();
					System.out.println("URL is " + url);
					String prepTime = CommonMethods.findPrepTime();
					System.out.println("Preparation time is " + prepTime);
					String cookTime = CommonMethods.findCookTime();
					System.out.println("Cooking time is " + cookTime);
					String noOfServings = CommonMethods.findNumOfServings();
					System.out.println("Num servings is " + noOfServings);
					String prepMethods = CommonMethods.findPrepMethod();
					System.out.println("Preparation method is " + prepMethods);
					String ingredients = CommonMethods.findIngredientList();
					System.out.println("Ingredients are " + ingredients);
					// Nutrients Value table
					String nutrientVal = CommonMethods.findNutrientValue();
					System.out.println(nutrientVal);

					count++;
					System.out.print(count + " ");
				}
			}
		}
	}

}
