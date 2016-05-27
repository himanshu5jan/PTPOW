package smoke;

import static com.jayway.restassured.RestAssured.given;

import java.io.PrintWriter;
import java.io.StringWriter;

//import library.Jira;
import library.ReadData;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
/*
1. 
<dependencies>
	<dependency>
        <groupId>org.seleniumhq.selenium</groupId>
        <artifactId>selenium-java</artifactId>
        <version>2.48.2</version>
    </dependency>
    <dependency>
  		<groupId>org.testng</groupId>
  		<artifactId>testng</artifactId>
  		<version>6.8</version>
  	
	</dependency>
	<dependency>
	<groupId>org.apache.poi</groupId>
	<artifactId>poi</artifactId>
	<version>3.14</version>
</dependency>
<dependency>
	<groupId>org.apache.poi</groupId>
	<artifactId>poi-ooxml</artifactId>
	<version>3.14</version>
</dependency>
  <dependency>
	<groupId>com.relevantcodes</groupId>
	<artifactId>extentreports</artifactId>
	<version>2.40.2</version>
</dependency>
     
     <dependency>
			<groupId>com.jayway.restassured</groupId>
			<artifactId>rest-assured</artifactId>
			<version>2.8.0</version>
		</dependency>
     
          
     
  </dependencies>
*/
public class VerifyLinksAdminDPExcel {

	ExtentReports report;
	ExtentTest logger;
	WebDriver driver;
	ReadData excel;
	XSSFWorkbook wb;
	XSSFSheet sheet;
	int rowCount;
	int sheetCount;
	int count=2;
	@BeforeClass
	public void setup() {
		
		
		String fileName="TestData//AdminLinksWithoutHeader.xlsx";
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\username\\Desktop\\ChromeDriver\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();
		wb=new XSSFWorkbook();
		excel=new ReadData(fileName);
		rowCount=excel.getRowCount(0);
		sheetCount=excel.getSheetCount();
		
		String reportFile="Reports//results.html";
		report=new ExtentReports(reportFile);
		
	}
	
	
	@DataProvider(name="readnoheader")
	public Object[][] passData() {
		System.out.println("Total Sheets= "+sheetCount);
	    Object[][] data=new Object[rowCount][2];
	 		for(int i=0;i<rowCount;i++) 
	 		{
	 			data[i][0]=excel.getData(0,i,0);
	 			data[i][1]=excel.getData(0,i,1);
	 			
	 		}
	 	return data;
	}
	
	@Test
	public void loginPow() {
		//driver=new FirefoxDriver();
		driver.get("http://host/path/startadmin.do");
		String email = "username@yahoo.com";
		String password = "password";
		driver.findElement(By.id("email")).sendKeys(email);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.id("loginbutton")).click();
		logger=report.startTest("TC1 : loginPOW");
		logger.log(LogStatus.INFO, "Login to POW");
		Assert.assertTrue(driver.getTitle().contains("Dashboard"));
	}
	
	@Test(dataProvider="readnoheader",dependsOnMethods = {"loginPow"})
	public void linkVerification(String linkText, String title) {
		String counter=Integer.toString(count);
		count++;
		logger=report.startTest("TC"+counter+" : linkVerfication Admin");
		logger.log(LogStatus.INFO, "Verifying link "+linkText);
		driver.findElement(By.linkText(linkText)).click();
		Assert.assertTrue(driver.getTitle().contains(title));
	}
	
	
	
	
	@AfterMethod
	public void afterTest(ITestResult result) {
		String tName=result.getName();
		String tCaseId=result.toString();
	    //System.out.println(" What is the tCaseId "+tCaseId);
		if(result.getStatus() == ITestResult.FAILURE) {
			String thwomsg=result.getThrowable().getMessage();
			StringWriter sw=new StringWriter();
			result.getThrowable().printStackTrace(new PrintWriter(sw));
			String strace=sw.toString();
			System.out.println("Test Case "+tName+" Failed !!");
			logger.log(LogStatus.FAIL, thwomsg);
			String bugSummary= tName+" "+"Failed";
			String bugDesc=thwomsg;
		    // to be used in detailed logs
			//logger.log(LogStatus.FAIL, "Detailed logs:");
		    //logger.log(LogStatus.FAIL, strace);
			//String bugId=Jira.raiseBug(bugSummary, bugDesc);
			//logger.log(LogStatus.FAIL, "JIRA Defect ID :  "+bugId);
			logger.log(LogStatus.FAIL, "bug summary :  "+bugSummary);
			logger.log(LogStatus.FAIL, "bug desc :  "+bugDesc);

		} else {
			System.out.println("Test Case "+tName+" Passed !!");
			logger.log(LogStatus.PASS, tName+" Passed");
		}
		
	report.endTest(logger);
	report.flush();

	}
	
	@AfterClass
	public void teardown() {
		driver.get("C:\\Users\\system-name\\workspace\\Luna\\demo-project\\Reports\\results.html");
		driver.manage().window().maximize();
		//driver.close();
	}
	
	
}
