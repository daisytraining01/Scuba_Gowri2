package com.internetapp.tests;

import java.io.FileReader;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.internetapp.pages.CommonRepo;

public class Parabank {
	CommonRepo rep = new CommonRepo();
	WebDriver driver;
	Properties prop = new Properties();
	HashMap<String, String> Testdata;
    String Scrpath="";
	public ExtentHtmlReporter htmlreporter;
	public ExtentReports extent;
	public ExtentTest test;

	@BeforeTest
	public void ExtendSetup() {

		htmlreporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/test-output/myreport.html");

		htmlreporter.config().setDocumentTitle("Automation Report");
		htmlreporter.config().setReportName("TestNG Report");
		htmlreporter.config().setTheme(Theme.DARK);

		extent = new ExtentReports();
		extent.attachReporter(htmlreporter);

		extent.setSystemInfo("OS", "Mac");
		extent.setSystemInfo("Tester", "Gowri");
		extent.setSystemInfo("Browser", "Chrome");
	}
	@BeforeTest
	public void loadproperties() throws Throwable {
		FileReader reader = new FileReader(System.getProperty("user.dir") + "/Locator_Properties/Parabank.properties");
		prop.load(reader);
		Testdata = rep.ReadExcel("/Users/gowriv/Desktop/DataFile.xlsx", "Data", "TC='TC_5'");
		driver = rep.getdriver("Chrome");
		driver.manage().window().maximize();
		driver.get("https://parabank.parasoft.com/parabank/register.htm");
	}

	@Test(priority=0)
	public void Register() throws Exception {
		//driver.get("https://parabank.parasoft.com/parabank/register.htm");
		test = extent.createTest("Register");
		rep.links(driver.findElement(By.xpath(prop.getProperty("Register"))));
		rep.textbox(driver.findElement(By.xpath(prop.getProperty("Customer_Firstname"))),
				Testdata.get("CustomerFirstname"));
		rep.textbox(driver.findElement(By.xpath(prop.getProperty("Customer_Lastname"))),
				Testdata.get("Customerlastname"));
		rep.textbox(driver.findElement(By.xpath(prop.getProperty("Customer_Address"))),
				Testdata.get("Customeraddress"));
		rep.textbox(driver.findElement(By.xpath(prop.getProperty("Customer_City"))), Testdata.get("Customercity"));
		rep.textbox(driver.findElement(By.xpath(prop.getProperty("Customer_State"))), Testdata.get("CustomerState"));
		rep.textbox(driver.findElement(By.xpath(prop.getProperty("Customer_Zipcode"))),
				Testdata.get("CustomerZipcode"));
		rep.textbox(driver.findElement(By.xpath(prop.getProperty("Customer_Phonenumber"))),
				Testdata.get("Customermobnumber"));
		rep.textbox(driver.findElement(By.xpath(prop.getProperty("Customer_SSN"))), Testdata.get("Customerssn"));
		rep.textbox(driver.findElement(By.xpath(prop.getProperty("Customer_username"))),
				Testdata.get("Cusomerusername"));
		rep.textbox(driver.findElement(By.xpath(prop.getProperty("Customer_password"))),
				Testdata.get("Customerpassword"));
		rep.textbox(driver.findElement(By.xpath(prop.getProperty("Repeat_password"))), Testdata.get("Repeatpassword"));
		rep.takescreenshot("/Users/gowriv/Desktop/Automation","Before Register");
		rep.links(driver.findElement(By.xpath(prop.getProperty("Click_Register"))));
		rep.takescreenshot("/Users/gowriv/Desktop/Automation","Post Registration");
		//Assert.assertTrue(true);
	}
	@Test(priority=1)
	public void Loginaccount() throws Exception {
		//driver.get("https://parabank.parasoft.com/parabank/register.htm");
		test = extent.createTest("Login");
		rep.textbox(driver.findElement(By.xpath(prop.getProperty("Login_username"))),"Dummy5");
		rep.textbox(driver.findElement(By.xpath(prop.getProperty("Login_Password"))),"Test@123");
		rep.takescreenshot("/Users/gowriv/Desktop/Automation","Before Login");
		rep.links(driver.findElement(By.xpath(prop.getProperty("Login_Button"))));
		rep.takescreenshot("/Users/gowriv/Desktop/Automation","After Login");
		Assert.assertEquals(rep.gettext(driver.findElement(By.xpath("//h1[text()='Accounts Overview']"))), "Accounts Overview");
		Scrpath=rep.takescreenshot("/Users/gowriv/Desktop/Automation", "Report");
	}
	@Test(dependsOnMethods = { "Loginaccount" },priority=2)
	public void  OpenAccount() throws Exception {
		test = extent.createTest("Open Account");
		rep.links(driver.findElement(By.xpath(prop.getProperty("Open_New_account"))));
		rep.SelectByVisibleText(driver.findElement(By.xpath(prop.getProperty("Select_Account_type"))),"CHECKING");
		Thread.sleep(1000);
		rep.SelectByIndex(driver.findElement(By.xpath(prop.getProperty("From_Acccount_ID"))), 0);
		rep.takescreenshot("/Users/gowriv/Desktop/Automation","Before Accountopen");
		rep.links(driver.findElement(By.xpath(prop.getProperty("Open_New_account_button"))));
		Assert.assertEquals(rep.gettext(driver.findElement(By.xpath("//h1[text()='Account Opened!']"))), "Account Opened!");
		rep.links(driver.findElement(By.xpath(prop.getProperty("New_Account_ID"))));
		rep.takescreenshot("/Users/gowriv/Desktop/Automation","After Accountopen");
		Thread.sleep(2000);
		Scrpath=rep.takescreenshot("/Users/gowriv/Desktop/Automation", "Report");
	}
	@Test(dependsOnMethods = { "OpenAccount" },priority=3)
	public void Transferfund() throws Exception {
		test = extent.createTest("Transfer Fund");
		rep.links(driver.findElement(By.xpath(prop.getProperty("Account_Overview"))));
		rep.links(driver.findElement(By.xpath(prop.getProperty("Transfer_Funds"))));
		Thread.sleep(1000);
		rep.textbox(driver.findElement(By.xpath(prop.getProperty("Input_Amount"))), "10");
		Thread.sleep(1000);
		rep.SelectByIndex(driver.findElement(By.xpath(prop.getProperty("Select_From_Account"))), 0);
		rep.SelectByIndex(driver.findElement(By.xpath(prop.getProperty("Select_To_Account"))), 1);
		rep.takescreenshot("/Users/gowriv/Desktop/Automation","Before Transfer");
		rep.links(driver.findElement(By.xpath(prop.getProperty("Transfer_button"))));
		rep.takescreenshot("/Users/gowriv/Desktop/Automation","After Transfer");
		Assert.assertEquals(rep.gettext(driver.findElement(By.xpath("//h1[text()='Transfer Complete!']"))), "Transfer Complete!");
		Scrpath=rep.takescreenshot("/Users/gowriv/Desktop/Automation", "Report");
	}
	@Test(dependsOnMethods = { "Transferfund" },priority=4)
	public void SearchTrxn() throws Throwable {
		test = extent.createTest("Search Transactions");
		rep.links(driver.findElement(By.xpath(prop.getProperty("Find_Trxns"))));
		rep.textbox(driver.findElement(By.xpath(prop.getProperty("Criertia_FromDate"))), "07-20-2020");
		rep.textbox(driver.findElement(By.xpath(prop.getProperty("Criertia_ToDate"))), rep.dateasmmddyyyy());
		rep.links(driver.findElement(By.xpath(prop.getProperty("Search_with_Date"))));	
		rep.takescreenshot("/Users/gowriv/Desktop/Automation","Before Sort");
	
		List<String> datefromtable=new ArrayList<String>();
		StringBuffer str= new StringBuffer();
		List<WebElement> tr= driver.findElements(By.xpath("//table[@id='transactionTable']/tbody/tr"));
		for(int i=1;i<=tr.size();i++) {
			datefromtable.add(driver.findElement(By.xpath("//table[@id='transactionTable']/tbody/tr["+i+"]/td[1]")).getText());
		}
		System.out.println(datefromtable);
			
		Collections.sort(datefromtable, new Comparator<String>() {
			DateFormat f = new SimpleDateFormat("MM-dd-yyyy");
			@Override
			public int compare(String o1, String o2) {
			try {
			  return f.parse(o1).compareTo(f.parse(o2));
			    } catch (Exception e) {
			      throw new IllegalArgumentException(e);
			    }
			}
			});
		System.out.println("after sort"+datefromtable);
		
		HashMap<String,String> table=new HashMap<String,String> ();
		for(int j=0;j<datefromtable.size();j++) {
			List<WebElement> web= driver.findElements(By.xpath("//td[contains(text(),'"+ datefromtable.get(j)+"')]/following-sibling::td/span[contains(@ng-if,'Credit') or contains(@ng-if,'Debit')]"));
			for(int z=0;z<web.size();z++) {
				table.put(z+datefromtable.get(j),datefromtable.get(j)+" "+web.get(z).getText() );
			}
		}
		
		System.out.println(table);
		
		for(Map.Entry m :table.entrySet()) {
			str.append(m.getValue());
			str.append("\n");
		}
		
		FileWriter fileWriter = new FileWriter("/Users/gowriv/Desktop/Automation/Test.txt");
	    fileWriter.write(new String(str));
	    fileWriter.close(); 
	    Assert.assertEquals(rep.gettext(driver.findElement(By.xpath("//h1[text()='Transaction Results']"))), "Transaction Results");
	    Scrpath=rep.takescreenshot("/Users/gowriv/Desktop/Automation", "Report");
	}
	@AfterTest
	public void Logout() throws Throwable {
		rep.links(driver.findElement(By.xpath(prop.getProperty("Logout")))); 
		Scrpath=rep.takescreenshot("/Users/gowriv/Desktop/Automation", "Report");
		driver.close();
	}
	@AfterTest
	public void EndReport() {
		extent.flush();
	}
	@AfterMethod
	public void tearDown(ITestResult result) throws Exception {
		
		if (result.getStatus() == ITestResult.FAILURE) {
			test.log(Status.FAIL, "Test Case Faied is" + result.getName());
			test.log(Status.FAIL, "Test Case Failed is" + result.getThrowable());
			test.addScreenCaptureFromPath(Scrpath);
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			test.log(Status.PASS, "Test Case Passed is" + result.getName());
			test.addScreenCaptureFromPath(Scrpath);
		} else if (result.getStatus() == ITestResult.SKIP) {
			test.log(Status.SKIP, "Test Case Skipped" + " is" + result.getName());
		}
		
	}
}
