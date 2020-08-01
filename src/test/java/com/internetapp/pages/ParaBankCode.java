package com.internetapp.pages;

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
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.maveric.core.testng.listeners.ReportListener;
import com.maveric.core.utils.web.WebActions;


public class ParaBankCode extends WebActions {
	DBConnection DBC = new DBConnection();
	CommonRepo rep = new CommonRepo();
	Properties prop = new Properties();
	HashMap<String, String> Testdata;
	FileReader reader;

	public ParaBankCode() {
		try {
			reader = new FileReader(System.getProperty("user.dir") + "/Locator_Properties/Parabank.properties");
			prop.load(reader);
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		rep.initialization();
		driver.manage().window().maximize();
		driver.get("https://parabank.parasoft.com/parabank/register.htm");
		ReportListener.logger.info("Driver launched successfully");
	}

	public void Register(String TC, String InputDataType) throws Throwable {
		if (InputDataType.equalsIgnoreCase("Excel")) {
			Testdata = rep.ReadExcel("/Users/gowriv/Desktop/DataFile.xlsx", "Data", "TC='" + TC + "'");
		} else {
			Testdata = DBC.getrow("Select * from Registration where TC='" + TC + "';");
		}
		rep.links(driver.findElement(By.xpath(prop.getProperty("Register"))));
	}

	public void EnterRegisterDetails() {
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
		rep.textbox(driver.findElement(By.xpath(prop.getProperty("Repeat_password"))),
				Testdata.get("Customerpassword"));
	}

	public void submitRegister() {
		rep.links(driver.findElement(By.xpath(prop.getProperty("Click_Register"))));
		ReportListener.logger.info("Registered successfully");
	}

	public void Loginaccount(String Username, String Password) throws Exception {
		rep.textbox(driver.findElement(By.xpath(prop.getProperty("Login_username"))), Username);
		rep.textbox(driver.findElement(By.xpath(prop.getProperty("Login_Password"))), Password);
		rep.links(driver.findElement(By.xpath(prop.getProperty("Login_Button"))));

		Assert.assertEquals(rep.gettext(driver.findElement(By.xpath("//h1[text()='Accounts Overview']"))),
				"Accounts Overview");
		ReportListener.logger.info("Logged in successfully");
	}

	public void OpenAccount(String Type) throws Exception {

		rep.links(driver.findElement(By.xpath(prop.getProperty("Open_New_account"))));
		rep.SelectByVisibleText(driver.findElement(By.xpath(prop.getProperty("Select_Account_type"))), Type);
		Thread.sleep(1000);
		rep.SelectByIndex(driver.findElement(By.xpath(prop.getProperty("From_Acccount_ID"))), 0);
		Thread.sleep(1000);
		rep.links(driver.findElement(By.xpath(prop.getProperty("Open_New_account_button"))));
		Thread.sleep(1000);
		Assert.assertEquals(rep.gettext(driver.findElement(By.xpath("//h1[text()='Account Opened!']"))),
				"Account Opened!");
		rep.links(driver.findElement(By.xpath(prop.getProperty("New_Account_ID"))));

		Thread.sleep(2000);
		ReportListener.logger.info("Account opened successfully");
	}

	public void Transferfund(String Amount) throws Exception {
		rep.links(driver.findElement(By.xpath(prop.getProperty("Account_Overview"))));
		rep.links(driver.findElement(By.xpath(prop.getProperty("Transfer_Funds"))));
		Thread.sleep(1000);
		rep.textbox(driver.findElement(By.xpath(prop.getProperty("Input_Amount"))), Amount);
		Thread.sleep(1000);
		rep.SelectByIndex(driver.findElement(By.xpath(prop.getProperty("Select_From_Account"))), 0);
		rep.SelectByIndex(driver.findElement(By.xpath(prop.getProperty("Select_To_Account"))), 1);
		Thread.sleep(1000);
		rep.links(driver.findElement(By.xpath(prop.getProperty("Transfer_button"))));
		Thread.sleep(1000);
		Assert.assertEquals(rep.gettext(driver.findElement(By.xpath("//h1[text()='Transfer Complete!']"))),
				"Transfer Complete!");
		ReportListener.logger.info("Transfer Completed");
	}

	public void SearchTrxn(String Filename) throws Throwable {
		rep.links(driver.findElement(By.xpath(prop.getProperty("Find_Trxns"))));
		rep.textbox(driver.findElement(By.xpath(prop.getProperty("Criertia_FromDate"))), rep.dateasmmddyyyy());
		rep.textbox(driver.findElement(By.xpath(prop.getProperty("Criertia_ToDate"))), rep.dateasmmddyyyy());
		rep.links(driver.findElement(By.xpath(prop.getProperty("Search_with_Date"))));
		Thread.sleep(1000);

		List<String> datefromtable = new ArrayList<String>();
		StringBuffer str = new StringBuffer();
		List<WebElement> tr = driver.findElements(By.xpath("//table[@id='transactionTable']/tbody/tr"));
		for (int i = 1; i <= tr.size(); i++) {
			datefromtable.add(driver.findElement(By.xpath("//table[@id='transactionTable']/tbody/tr[" + i + "]/td[1]"))
					.getText());
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
		System.out.println("after sort" + datefromtable);

		HashMap<String, String> table = new HashMap<String, String>();
		for (int j = 0; j < datefromtable.size(); j++) {
			List<WebElement> web = driver.findElements(By.xpath("//td[contains(text(),'" + datefromtable.get(j)
					+ "')]/following-sibling::td/span[contains(@ng-if,'Credit') or contains(@ng-if,'Debit')]"));
			for (int z = 0; z < web.size(); z++) {
				table.put(z + datefromtable.get(j), datefromtable.get(j) + " " + web.get(z).getText());
			}
		}

		System.out.println(table);

		for (Map.Entry m : table.entrySet()) {
			str.append(m.getValue());
			str.append("\n");
		}

		FileWriter fileWriter = new FileWriter(Filename);
		fileWriter.write(new String(str));
		fileWriter.close();
		Assert.assertEquals(rep.gettext(driver.findElement(By.xpath("//h1[text()='Transaction Results']"))),
				"Transaction Results");
		ReportListener.logger.info("Transactions file updated successfully");
	}

	public void Logout() throws Throwable {
		rep.links(driver.findElement(By.xpath(prop.getProperty("Logout"))));
		ReportListener.logger.info("Logged Out successfully");
	}
	
	public void Runquery(String Query) throws Throwable {
		DBC.InsertUpdateDeleteWithPrepared(Query);	
	}
	public void RunqueryWithoutPrepared(String Query) throws Throwable {
		DBC.InsertUpdateDeleteWithoutPrepared(Query);	
	}
}
