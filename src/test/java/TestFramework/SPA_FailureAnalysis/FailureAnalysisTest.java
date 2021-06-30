package TestFramework.SPA_FailureAnalysis;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.datatype.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Connection.Base;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class FailureAnalysisTest extends baseTest
{

	public WebDriver driver;
	public static Logger log =LogManager.getLogger(Base.class.getName());
	WebDriverWait wait;
	
	@BeforeTest
	public void initialize() throws IOException
	{
		driver = initializeDriver();
		log.info("Driver intialized");
		driver.get(url);
		log.info("Navigated to HomePage : "+ url);
	}

	@Test
	public void startFailureAnalysis() throws IOException, InterruptedException
	{		
		loginMS();
		getFailCount();		
	}
	
	
	
	private void getFailCount() throws IOException 
	{
		wait = new WebDriverWait(driver, 4);
		
		String failedTestExpression=driver.findElement(By.xpath("//div[contains(@class,'failed-chart-total-count')]")).getText();
		System.out.println("failedTestExpression:"+failedTestExpression);
		
		int failedTestCount = Integer.parseInt(failedTestExpression);
		
		//Map<String, String> failedTests = new LinkedHashMap<String, String>();
		String[][] failedTests= new String[failedTestCount][3];
		
		
		for(int i=1;i<=failedTestCount;i++)
		{
			
			System.out.println(i);
			//WebElement testHeader = driver.findElement(By.xpath("//div[@data-list-index='"+i+"']//div[contains(@class,'ms-DetailsRow-fields')]//span[@class='clickable-text']"));
			WebElement testHeader = driver.findElement(By.xpath("//div[@data-list-index='"+i+"']//span[@class='clickable-text']/.."));
			
			//failedTests.put(testHeader.getText(), "value for :"+i);
		
			
			//System.out.println("\n"+i+" : "+testHeader.getText());
			wait.until(ExpectedConditions.elementToBeClickable(testHeader));
			testHeader.click();
			//javaScriptClick(testHeader);
			
			
			//click on Debug tab
			clickDebug();
			
			
			failedTests[i-1][0]=testHeader.getText();
			failedTests[i-1][1]=errorMessageStack();
			failedTests[i-1][2]=stackTrace();
												
			
			if(i%10==0)
			{			
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", testHeader);
				waitSomeTime(500);
			}	
		}
		
		System.out.println("data copied into String Array");
		System.out.println("Now calling the excel function");
		
		writeExcel(failedTests);
	}
	
	public void javaScriptClick(WebElement ele)
	{
		
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("arguments[0].click()", ele);
		ele.click();
		
	}
	
	
	private void loginMS()
	{
		
		WebElement emailInput = driver.findElement(By.id("i0116"));
		emailInput.sendKeys("<<paste your email id here>>");
		driver.findElement(By.id("idSIButton9"));	
		clickNext();
		
		WebElement passInput = driver.findElement(By.id("i0118"));
		passInput.sendKeys("<<paste your password here>>");
		clickNext();
		
		//clicking on Final Yes/no dialog
		clickNext();
	}
	
	public void waitSomeTime(int millis)
	{
		try{Thread.sleep(millis);} catch(Exception e) {System.out.println(e);} 
	}
	
	public void clickNext()
	{	

			waitSomeTime(2000);
			driver.findElement(By.id("idSIButton9")).click();
			

	}
	
	public void clickDebug()
	{
		
		WebElement debugTab= driver.findElement(By.xpath("//span[text()='Debug']"));
		wait.until(ExpectedConditions.elementToBeClickable(debugTab));
		debugTab.click();
				
		//System.out.println("Error trace:\n"+getErrorStack());
	}
	
	public String errorMessageStack()
	{		
		
		try 
		{
			WebElement errorMessageInput = driver.findElement(By.xpath("//div[@class='test-error-markdown-renderer']"));		
			wait.until(ExpectedConditions.visibilityOf(errorMessageInput));		
			return errorMessageInput.getText();	
		}
		catch(Exception e)
		{
			return "No errorMessageFound";
		}

	}
	
	
	public String stackTrace()
	{				
		try 
		{
			WebElement stackTraceInput = driver.findElement(By.xpath("//div[@class='stack-trace']")); 
			wait.until(ExpectedConditions.visibilityOf(stackTraceInput));
			return stackTraceInput.getText();		
		}
		catch(Exception e)
		{
			return "No StackTraceFound";
		}
	}
	
	
	
	
	
	public void  writeExcel(String[][] hm) throws IOException
	{
		String path = "C:\\atulBackup\\DemoFile.xlsx";
		FileInputStream fs = new FileInputStream(path);
		Workbook wb = new XSSFWorkbook(fs);
		Sheet sheet1 = wb.getSheetAt(0);
		//int lastRow = sheet1.getLastRowNum();
		
		//*********************
		int rowCount = hm.length;
				
		
		for(int i=0; i<rowCount; i++)
		{
			//System.out.println("Row:"+i);
			Row row1=sheet1.createRow(i);
			
			//System.out.println("Row:"+i+"cel 0"+ hm[i][0]);
			row1.createCell(0).setCellValue(hm[i][0]);
			//System.out.println("Row:"+i+"cel 0"+ hm[i][1]);
			row1.createCell(1).setCellValue(hm[i][1]);	
			row1.createCell(2).setCellValue(hm[i][2]);
		
		}
		
		FileOutputStream fos = new FileOutputStream(path);
		wb.write(fos);
		fos.close();
	}
	

	@AfterTest
	public void teardown()
	{
		driver.close();
		log.info("Closing the Browser");
	}
}
