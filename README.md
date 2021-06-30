# SPA_FailureAnalysisUtility
This is a utility created for failure analysis


Problem statement:
on Azure Devops page user had a list of failed tests populating. 
To check the reason for failed test, user had to select a failed test and then Error message and dubug trace populated.
This was a tedious process as user had to select a test everytime to check the error message.
Hence I wrote this utility to do the following tasks:

1) Go to Azure DevOps result page
2) select a test
3) retrives the data -> failed test header | error message | stack trace message (logger message)
4) All this data is retrived and stored in an array
5) Only after the all the failed tests with the error message is retrived from the screen and stored in an array.
6) all this data is then dumped into an excel sheet kept at a predefined location.


Value added due to this utility:
Now all the failures with error messages are in one sheet
as excel sheet is used its predtty easy task to categories / sort these records.
I apply conditional formatting using the text contains feature and categorise similar failed  tests

earlier it use to take a QC guy to get these Failure Analysis done for  4 hours.
Now it takes like 20 mins max.

-------------------------------------
Updates to the utility
--------------------------------------
1) added WebDriver wait condition to improve the wait time.
