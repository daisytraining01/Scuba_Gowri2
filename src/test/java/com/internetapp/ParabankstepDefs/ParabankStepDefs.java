package com.internetapp.ParabankstepDefs;

import com.scubatraining.Gowri.seleniummethods.ParaBankCode;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;



public class ParabankStepDefs  {

	ParaBankCode PBC = new ParaBankCode();

	/*public ParabankStepDefs() {

		Given("user is on the Parabank registration page using testdata {string} in {string}",
				(String TC, String InputDataType) -> {
					PBC.Register(TC, InputDataType);
				});

		When("user enters all the required fields", () -> {
			PBC.EnterRegisterDetails();
		});
		Then("Customer is logged in with New Account created", () -> {
			PBC.submitRegister();
		});
	} */

	@Given("user is on the Parabank registration page using testdata {string} in {string}")
	public void user_is_on_the_Parabank_registration_page_using_testdata_in(String TC, String InputDataType)
			throws Throwable {
		PBC.Register(TC, InputDataType);
	}

	@When("user enters all the required fields")
	public void user_enters_all_the_required_fields() throws Exception {
		PBC.EnterRegisterDetails();
	}

	@Then("Customer is logged in with New Account created")
	public void customer_is_logged_in_with_New_Account_created() throws Exception {
		PBC.submitRegister();
	}

	@Given("Login to parabank with username as {string} and password as {string}")
	public void login_to_parabank_with_username_as_and_password_as(String Username, String Password) throws Throwable {
		PBC.Loginaccount(Username, Password);
	}

	@When("Create a {string} account and Verify account is created successfully")
	public void create_a_account_and_Verify_account_is_created_successfully(String Type) throws Exception {
		PBC.OpenAccount(Type);
	}

	@Then("Verify account Details")
	public void verify_account_Details() throws Exception {

	}

	@Then("Transfer amount {string}")
	public void transfer_amount(String Amount) throws Exception {
		PBC.Transferfund(Amount);
	}

	@Then("Verify Transaction details and sort it and Record the transaction in {string}")
	public void verify_Transaction_details_and_sort_it_and_Record_the_transaction_in(String Filename) throws Throwable {
		PBC.SearchTrxn(Filename);
	}

	@Given("Insert a data to Registration Table {string}")
	public void insert_a_data_to_Registration_Table(String TC) throws Throwable {
		PBC.Runquery(
				"INSERT INTO Registration ( TC, CustomerFirstname, Customerlastname, Customeraddress, Customercity, CustomerState, CustomerZipcode,  Customermobnumber,  Customerssn, Cusomerusername, Customerpassword) VALUES ('"
						+ TC
						+ "', 'AWS', 'ASE', '33 ERT ST' , 'Chennai', 'TN', '600098', '900087549', '9091', 'Dummy16', 'Test@123' );");
	}

	@Then("Update a column to Registration Table {string}")
	public void update_a_column_to_Registration_Table(String TC) throws Throwable {
		PBC.Runquery("UPDATE Registration SET CustomerFirstname='Gowri' where TC='" + TC + "';");
	}

	@Then("Delete a row in Registration Table {string}")
	public void delete_a_row_in_Registration_Table(String TC) throws Throwable {
		PBC.Runquery("DELETE FROM Registration where TC='" + TC + "';");
	}

	@Given("Insert a data to Registration Table {string} without prepared Statement")
	public void insert_a_data_to_Registration_Table_without_prepared_Statement(String TC) throws Throwable {
		PBC.RunqueryWithoutPrepared(
				"INSERT INTO Registration ( TC, CustomerFirstname, Customerlastname, Customeraddress, Customercity, CustomerState, CustomerZipcode,  Customermobnumber,  Customerssn, Cusomerusername, Customerpassword) VALUES ('"
						+ TC + "', ?, ?, ? , ?, ?, ?, ?, ?, ?, ? )");
	}
}
