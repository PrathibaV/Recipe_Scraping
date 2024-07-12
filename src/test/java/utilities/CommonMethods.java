package utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import base.BaseTest;

public class CommonMethods extends BaseTest {
	public CommonMethods() throws IOException {
		driver = BaseTest.initializeDriver();
	}

	public static int findTotalRecLinks() {
		int recLinkSize = driver.findElements(By.xpath("//table/tbody/tr/td[@onmouseover='Menu_HoverStatic(this)']"))
				.size();
		return recLinkSize;
	}

	public static int findNumOfPages() {
		WebElement lastPageLink = driver.findElement(
				By.xpath("//div[@id='maincontent']/div[1]/div[2]/a[contains (@href,'pageindex=')][last()]"));
		String lastPageHref = lastPageLink.getAttribute("href");
		lastPageHref = lastPageHref.substring(lastPageHref.indexOf("pageindex=") + 10);
		int noOfPages = Integer.parseInt(lastPageHref);
		return noOfPages;
	}

	public static int findNumOfRecInEachPage() {
		int eachPageRecSize = driver.findElements(By.xpath("//div/div[@class='rcc_recipecard']")).size();
		return eachPageRecSize;
	}

	public static String findTags() {
		List<WebElement> tagList = driver.findElements(By.xpath("//div[@id='recipe_tags']/a"));
		String tags = "";
		for (WebElement tag : tagList) {
			tags = tags + tag.getText() + "\n";
		}
		return tags;
	}

	public static String findPrepMethod() {
		List<WebElement> methodList = driver.findElements(By.xpath("//div[@id='recipe_small_steps']/span/ol/li"));
		String prepMethods = "";
		for (WebElement methods : methodList) {
			prepMethods = prepMethods + methods.getText() + "\n";
		}
		return prepMethods;
	}

	public static String findIngredientList() {
		int ingreSize = driver.findElements(By.xpath("//div[@id='rcpinglist']/div/span")).size();
		String ingredients = "";
		for (int k = 2; k <= ingreSize; k++) {
			String eachIngrednt = driver.findElement(By.xpath("//div[@id='rcpinglist']/div/span[" + k + "]")).getText();
			ingredients = ingredients + eachIngrednt + "\n";
		}
		return ingredients;
	}

	public static String findNutrientValue() {
		List<String> nutrientList = new ArrayList<String>();
		int rows = driver.findElements(By.xpath("//table[@id='rcpnutrients']//tr")).size();
		int cols = driver.findElements(By.xpath("//table[@id='rcpnutrients']//tr[1]/td")).size();
		for (int r = 1; r <= rows; r++) {
			String nutrientVal = "";
			for (int c = 1; c <= cols; c++) {
				String nutrients = driver
						.findElement(By.xpath("//table[@id='rcpnutrients']//tr[" + r + "]/td[" + c + "]")).getText();
				nutrientVal = nutrientVal + nutrients + " ";
			}
			nutrientList.add(nutrientVal);
		}
		String nutrientVal = "";
		for (String nutrients : nutrientList) {
			nutrientVal = nutrientVal + nutrients + "\n";
		}
		return nutrientVal;
	}

	public static String findRecDescrptn() {
		String recDescrptn = driver.findElement(By.id("ctl00_cntrightpanel_lblDesc")).getText();
		return recDescrptn;
	}

	public static String findPrepTime() {
		String prepTime = driver.findElement(By.xpath("//time[@itemprop='prepTime']")).getText();
		return prepTime;
	}

	public static String findCookTime() {
		String cookTime = driver.findElement(By.xpath("//time[@itemprop='cookTime']")).getText();
		return cookTime;
	}

	public static String findNumOfServings() {
		String noOfServings = driver.findElement(By.id("ctl00_cntrightpanel_lblServes")).getText();
		return noOfServings;
	}

	public static String findRecUrl() {
		String url = driver.getCurrentUrl();
		return url;
	}

}
