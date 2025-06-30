# AmericanEagleTestProject
A repository for test practicing using the web site https://www.ae.com/us/en. 

Please fill email and password in default.properties file to run tests with authorization. 

To run all tests with guest token use "gradle test" (or "gradle apiTests" for just API tests). 

To run all tests with authorization use "gradle test -Dtest.mode=AUTH" (or "gradle apiTests -Dtest.mode=AUTH" for just API tests).