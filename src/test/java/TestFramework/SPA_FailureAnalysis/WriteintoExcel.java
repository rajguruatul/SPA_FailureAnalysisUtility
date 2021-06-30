package TestFramework.SPA_FailureAnalysis;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteintoExcel 
{
	
	public void  writeExcel(HashMap hm) throws IOException
	{
		String path = "C://DemoFile.xlsx";
		FileInputStream fs = new FileInputStream(path);
		Workbook wb = new XSSFWorkbook(fs);
		Sheet sheet1 = wb.getSheetAt(0);
		//int lastRow = sheet1.getLastRowNum();
		
		//*********************
		int rowCount = hm.size();
				
		
		for(int i=0; i<=rowCount; i++)
		{
			Row row1=sheet1.createRow(i);
			row1.createCell(0).setCellValue(path);
			
			
			/*
			 * Row row = sheet1.getRow(i);
			 * 
			 * for(int j=0;j<2;j++) { Cell cell = row.createCell(2);
			 * 
			 * cell.setCellValue("WriteintoExcel"); }
			 */
			
		
		}
		
		FileOutputStream fos = new FileOutputStream(path);
		wb.write(fos);
		fos.close();
	}

}