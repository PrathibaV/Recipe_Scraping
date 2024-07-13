package pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import base.BaseTest;
import utilities.ExcelReaderCode;

public class LCHF_EliminationList_DietPlan {
	static WebDriver driver;
	public static ResourceBundle rb;
	public static String filteredExcel = System.getProperty("user.dir")
			+ "C:\\Users\\Ranji\\Downloads\\IngredientsAndComorbidities-ScrapperHackathon.xlsx";
	public static String[] strHeading = { "Recipe ID", "Recipe Name: ", "Recipe Category", "Food Category",
			"Ingredients", "Preparation Time", "Cooking Time","Tag","Number of Servings","Cuisine category","Recipe Description","Preparation Method", "Nutrient Values","Recipe URL" };
	public static List<String> LCHF_EliminationList = new ArrayList<String>();
	
	
	@FindBys(@FindBy(xpath="//*[@class='rcc_recipethumbnail']/.."))static List<WebElement> recipeList;	

	@FindBys(@FindBy(xpath="//article[@class='rcc_recipecard']/div[3]/span[1]"))
				List<WebElement> Names;
	@FindBys(@FindBy(xpath="//article[@class='rcc_recipecard']/div[2]/span[1]"))
				List<WebElement> IDs;	
	@FindBys(@FindBy(xpath="//*[@id='rcpnutrients']")) List<WebElement> listWENValues;
	@FindBy(xpath="//*[@id='rcpinglist']") WebElement Ingredients;
	@FindBy(xpath="//*[@id='recipe_small_steps']") WebElement PreparationMethod;
	@FindBy(xpath="//*[@id='rcpnutrients']") WebElement nutrientValue;	
	@FindBys(@FindBy(xpath="//*[@itemprop='prepTime']")) 
				List<WebElement> prepTime;
	@FindBys(@FindBy(xpath="//*[@itemprop='cookTime']")) 
				List<WebElement> cookTime;
	@FindBys(@FindBy(xpath="//div[@class='tags']/a")) List<WebElement> category;			
	
	public LCHF_EliminationList_DietPlan() throws IOException
	{
		driver=BaseTest.initializeDriver();
	}
	public void RecipesPageWise( WebDriver driver, List<String> eliminatedList, 
			String strMorbidity, int sheetNo) 
			throws InterruptedException, IOException {

Thread.sleep(2000);
String strMainPage = driver.getCurrentUrl();
int i=0;
int nRecipes = recipeList.size();//a[contains(@href,"pageindex")]
XSSFRow row;
XSSFCell cell;		
String  name="", pTime="", cTime="", id="",Tag="",noOfservings="";
String ingredients="", nutrientValues="", preparationMethod="", url="",Description="";
System.out.println("No. of recipes : " + nRecipes);
String tagText = "";
int z;
String strRecipeCategory="";
String strFoodCategory="";
//nRecipes = 3;

//Scan through all the recipes on the page
for(i=0;i<nRecipes;i++) {
//bFound is a flag to check if the recipe has any eliminated ingredient
System.out.println("in the loop, i = " + i );
boolean bFound = false;

//get recipe ID
id = IDs.get(i).getText();
System.out.println("id : " + id);

//get recipe name
name = Names.get(i).getText();
System.out.println("name : " + name);


//check if the name of the recipe has an eliminated ingredient
//if yes, set the bFound to true
for (String word: eliminatedList ) {
bFound = name.toLowerCase().contains(word.toLowerCase());
if (bFound) {
break;
}
}

//if eliminated ingredients found in the name, continue through the loop, 
//don't go ahead for saving in the excel
if (bFound){
System.out.println("found in name..so navigating back to lists page..");
continue;
}
bFound = false;

//if eliminated ingredients not found in the name, open the recipe details page 
//System.out.println("xpathname : " + xPathName);
Names.get(i).click();		
System.out.println("recipe opened");
Thread.sleep(3000);

System.out.println(driver.getCurrentUrl());
				
ingredients = Ingredients.getText();

for (String word: eliminatedList ) {
bFound = ingredients.toLowerCase().contains(word.toLowerCase());
if (bFound) {
break;
}				
}

//if eliminated ingredients found in the ingredients of the recipe, continue through the loop
//don't go ahead for saving in the excel
if (bFound){
System.out.println("found in ingredients..so navigating back to lists page..");
driver.navigate().to(strMainPage);

Thread.sleep(2000);
continue;
}
bFound = false;

//You have reached here, means the recipe does not contain any eliminated ingredients
//scrape the recipe details

//get preparation time			
if(prepTime.size()>0){
pTime = prepTime.get(0).getText();
}
//get cooking time
if(cookTime.size()>0){
cTime = cookTime.get(0).getText();
}		

//get preparation method
preparationMethod = PreparationMethod.getText();

//some recipes do not have nutrition value chart, so check if it's there
if(listWENValues.size()>0) {
nutrientValues = listWENValues.get(0).getText();
}else {
nutrientValues = "no data";
}

url = driver.getCurrentUrl();

int noOfTags = category.size();
System.out.println("no of tags : " + noOfTags);
strRecipeCategory = "";
strFoodCategory = "";

for(z=0;z<noOfTags;z++) {
tagText = category.get(z).getText();
if (tagText.toLowerCase().contains("breakfast")){
if(!strRecipeCategory.contains("breakfast")){
	strRecipeCategory = strRecipeCategory + "-breakfast-";
}
}
if (tagText.toLowerCase().contains("lunch")){
if(!strRecipeCategory.contains("lunch")){
	strRecipeCategory = strRecipeCategory + "-lunch-";
}
}
if (tagText.toLowerCase().contains("snack")){
if(!strRecipeCategory.contains("snack")){
	strRecipeCategory = strRecipeCategory + "-snack-";
}
}
if (tagText.toLowerCase().contains("dinner")){
if(!strRecipeCategory.contains("dinner")){
	strRecipeCategory = strRecipeCategory + "-dinner-";
}	
}
if (tagText.toLowerCase().contains("jain")){
strFoodCategory = strFoodCategory + "-jain-";
}
if (tagText.toLowerCase().contains("vegan")){
strFoodCategory = strFoodCategory + "-vegan-";
}
}

if (strFoodCategory.equals("")){
strFoodCategory = "veg";
}


//Now it's time to save the recipe in the excel
if(!bFound) {
Path p = Paths.get(LCHF_EliminationList_DietPlan.filteredExcel);
boolean bFileExists = Files.exists(p);
XSSFWorkbook wb;

//if file already exists, check for the last row number
//and add a new row at the end
if(bFileExists) {
FileInputStream myxls = new FileInputStream(LCHF_EliminationList_DietPlan.filteredExcel);
wb = new XSSFWorkbook(myxls);

XSSFSheet sheet = wb.getSheetAt(sheetNo);
int lastRow=sheet.getLastRowNum();
row = sheet.createRow(++lastRow);
}

//if file doesn't exist, 
//create a new file and add headers as the first row
else {
wb = new XSSFWorkbook();
//CreationHelper ch = wb.getCreationHelper();

if (wb.getNumberOfSheets()<=1) {
wb.createSheet("Diabetes");
wb.createSheet("Hypothyroidism");
wb.createSheet("Hypertension");
wb.createSheet("PCOS");					
}

for(z = 0; z<4; z++) {
XSSFSheet sheet = wb.getSheetAt(z);
XSSFRow header = sheet.createRow(0);			
for(int k=0;k<LCHF_EliminationList_DietPlan.strHeading.length;k++) {
	XSSFCellStyle cs = wb.createCellStyle();
	XSSFFont font = wb.createFont();
	font.setColor(IndexedColors.BLUE.getIndex());
	font.setBold(true);
	cs.setFont(font);
	cs.setWrapText(true);
	cell = header.createCell(k);
	cell.setCellStyle(cs);
	cell.setCellValue(LCHF_EliminationList_DietPlan.strHeading[k]);
}
}

XSSFSheet sheet = wb.getSheet(strMorbidity);
row=sheet.createRow(1);
}

//add recipe details to the newly added row
cell=row.createCell(0);
cell.setCellValue(id);
cell=row.createCell(1);
cell.setCellValue(name);
cell=row.createCell(2);
cell.setCellValue(strFoodCategory);
cell=row.createCell(3);
cell.setCellValue(strRecipeCategory);
cell=row.createCell(4);
cell.setCellValue(ingredients);
cell=row.createCell(5);
cell.setCellValue(pTime);
cell=row.createCell(6);
cell.setCellValue(cTime);
cell=row.createCell(7);
cell.setCellValue(preparationMethod);
cell=row.createCell(8);
cell.setCellValue(nutrientValues);
cell=row.createCell(9);
cell.setCellValue(strMorbidity);
cell=row.createCell(10);
cell.setCellValue(url);
cell=row.createCell(11);
cell.setCellValue("");

//commit the newly added row to the excel and save the excel file
FileOutputStream fileOut = new FileOutputStream(LCHF_EliminationList_DietPlan.filteredExcel);
wb.write(fileOut);
fileOut.close();
wb.close();
}
LCHF_EliminationList_DietPlan.driver.navigate().to(strMainPage);
Thread.sleep(5000);
}
}
}


/*	
	
List<String> LCHF_EliminateItem = new ArrayList<String>();
public LCHF_EliminationList_DietPlan() throws IOException
{
	driver=BaseTest.initializeDriver();
}


@Test
	public void readExcel() {
		// TODO Auto-generated method stub
		ExcelReaderCode reader = new ExcelReaderCode("C:\\Users\\Ranji\\Downloads\\IngredientsAndComorbidities-ScrapperHackathon.xlsx");
		for (int i = 3; i <= 92; i++) {
			String elimntdItemsData = reader.getCellData("Final list for LCHFElimination ", 0 , i);
			LCHF_EliminateItem.add(elimntdItemsData);
			System.out.println(elimntdItemsData);
		}
	}
	@Test
	public void getRecipes()
	{
		driver.findElement(By.xpath("//a[@title='Recipea A to Z']")).click();
		
		
	}*/


	}


