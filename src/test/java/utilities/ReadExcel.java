package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ReadExcel {

	public List<String> getRecipeFilterItemsList(String sheetname, int colNum) { 
		
		List<String> currCelValue = new ArrayList();
		String path = System.getProperty("user.dir") + "/src/test/resources/IngredientsAndComorbidities.xlsx";
		FileInputStream fis;
		try {
			fis = new FileInputStream(path);

			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workbook.getSheet(sheetname);
			Iterator<Row> rows = sheet.rowIterator();
			rows.next();
			rows.next();
			while (rows.hasNext()) {
				Row currRow = rows.next();
				Cell cell = currRow.getCell(colNum);
				if (cell != null) {
					String data = cell.getStringCellValue().trim(); // Trim to remove any leading/trailing whitespace
					if (!data.isEmpty()) {
						currCelValue.add(data);
					}
				}
			}
			workbook.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return currCelValue;

	}
}
