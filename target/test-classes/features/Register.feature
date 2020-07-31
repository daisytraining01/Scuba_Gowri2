@parabank
Feature: Register a new Customer

@register
Scenario Outline: Registration Flow
Given user is on the Parabank registration page using testdata "<TC>" in "<Type>"
When user enters all the required fields
Then Customer is logged in with New Account created
Examples:
|TC|Type|
|TC_1|DB|


@transaction
Scenario Outline: Login
Given Login to parabank with username as "<Username>" and password as "<Password>"
When Create a "CHECKING" account and Verify account is created successfully
Then Verify account Details
And Transfer amount "10"
And Verify Transaction details and sort it and Record the transaction in "/Users/gowriv/Desktop/Automation/Test.txt"
Examples:
|Username|Password|
|Dummy11|Test@123|
|Dummy12|Test@123|

#@DBtransaction
#Scenario: Insert Update Delete Query for prepared Statement
#Given Insert a data to Registration Table "TC_6"
#Then Update a column to Registration Table "TC_6"
#And Delete a row in Registration Table "TC_6"


#@DBwithoutpreparedtransaction
#Scenario: Insert Update Delete Query for without prepared Statement
#Given Insert a data to Registration Table "TC_7" without prepared Statement
