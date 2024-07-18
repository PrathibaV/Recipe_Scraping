package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
	//String xlsxFilePath = System.getProperty("user.dir")+"/src/test/resources/IngredientsAndComorbidities.xlsx";
    public static String xlsxFilePath= "C:/Users/bhanu/git/Team03_NinjaExtracters_Rec_Scrap/src/test/resources/IngredientsAndComorbidities.xlsx";
    int totalRow;
    String colName;
    
	public  List<String> getColDataFromSheet(String sheetName ,String colName) throws IOException
	{
		File file = new File(xlsxFilePath);
		XSSFWorkbook workbook = null;
		XSSFSheet sheet = null;
		
		try{
		FileInputStream inputStream = new FileInputStream(file);
		workbook = new XSSFWorkbook(inputStream);		   
	    sheet = workbook.getSheet(sheetName);		  
			
		} catch(Exception e) {
		} finally {
			if(workbook != null)
				workbook.close();
		}
		
		
		return  readColumnValues(sheet,colName);
	}
	
	private List<String> readColumnValues(XSSFSheet sheet, String colName) {
	    
	  List<String> colDataValue = new ArrayList<String>();
	  int totalRow=sheet.getPhysicalNumberOfRows();		
	  
	  
	  XSSFRow row2=sheet.getRow(1);
	  int  eleColIndex = -1;
	  Iterator<Cell> it = row2.cellIterator(); 
		while(it.hasNext()) 
		
		 {
			     Cell col = it.next();
			     
			     if(col.getStringCellValue().contentEquals(colName)) {
			     
			         eleColIndex = col.getColumnIndex();
			         break;
			  
			 }
		}
		
		if(eleColIndex > -1) {	
	  for(int currentRow=2; currentRow <= totalRow; currentRow++)
			{
				XSSFRow row=sheet.getRow(currentRow);
				if(null != row) {
					
					String item = row.getCell(eleColIndex).getStringCellValue();			
			
					//add to a list 
					if(item != null && !item.isBlank()) {
						colDataValue.add(item);
					}
				}
				
			}
		}
			return colDataValue;
			
	}
	
	
	
}
		
		
		
	

