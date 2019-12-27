**[中文](https://github.com/abcnull/api-auto-test/blob/master/README_zh.md) | [Blog](https://blog.csdn.net/abcnull/article/details/103722306)**


[TOC]

# Framework Hierarchy

```
api-auto-test
	- src
		- main
			- java
				- com.abcnull.apiautotest
					- beans
						- PropertiesBean.java
						- RequestBean.java
						- XlsBean.java
					- constants
						- BaseConstant.java
					- listeners
						- RePrioritizingListener.java
						- TestLogListener.java
						- TestReportListener.java
					- utils
						- HttpRequestSender.java
						- PropertiesReader.java
						- RequestInfoTool.java
						- SSLClient.java
						- XlsReader.java
			- resources
				- log4j2.xml
		- test
			- java
				- com.abcnull.apiautotest
					- bases
						- BaseProperties.java
						- BaseTest.java
						- BaseXls.java
					- cases
						- ApiTest.java
			- resources
				- apiconfigs
					- simplecontroller
						- orderservice.properties
					- testplan
						- orderservice.properties
					- threadgroup
						- orderservice.properties
				- cases
					- orderservice.xls
				- templates
					- index.html
	- target
	- .gitignore
	- pom.xml
	- README.md
	- testng.xml
```

# General Idea

The framework imitates Jmeter's Test Plan, Thread Groups and Controller.In Jmeter, we use http request controller to read properties like header and others. In this project we can read different properties during different annotations of testng. Read every row of excel of requests is just like http request controller in jmeter in which we can config a series of requests information

![1577354515320](https://github.com/abcnull/Image-Resources/blob/master/api-auto-test/1577354515320.png)

Up to now, excel can stores a lot of requests but only single request allowed. Single request here means it can not be dependent on previous requests in excel, and means it is just as a singel request to be tested

![1577354129444](https://github.com/abcnull/Image-Resources/blob/master/api-auto-test/1577354129444.png)

This project has a Base class, in which some other small Base classes are invoked. Other small Base is mainly to do sth like read some files and other works. These Base can call some method in utils. The biggist Base contains lots of annotation of testng which controll the test running action during whole test period

![1577354168910](https://github.com/abcnull/Image-Resources/blob/master/api-auto-test/1577354168910.png)

All test case can extends TestBase so as to use request information in properties which is read in different periods in BaseTest

![1577354206817](https://github.com/abcnull/Image-Resources/blob/master/api-auto-test/1577354206817.png)

Properties data can be downward osmosis. For example, if in first stage it read a properties, in second stage it read b properties, finally it can get ultimate properties c(a∪b). When test case get excel request information, it can compare excel and ultimate properties data and use the ultimate data to send request and the excel information has the higher priority. A excel is actually a test case

![1577354542655](https://github.com/abcnull/Image-Resources/blob/master/api-auto-test/1577354542655.png)

![1577354575454](https://github.com/abcnull/Image-Resources/blob/master/api-auto-test/1577354575454.png)

![1577354619129](https://github.com/abcnull/Image-Resources/blob/master/api-auto-test/1577354619129.png)

Bean contains properties bean, excel row bean, request information bean

![1577354649075](https://github.com/abcnull/Image-Resources/blob/master/api-auto-test/1577354649075.png)

# Using Causions

**usage scenario**

Non-process request test. Until now it does not support cookie, https, and it only support get, post request. Later i will improve the framework constantly

**some cautions**

1. Only support post, get not support https and cookie
2. In three`.properties` file, headers parameter can use `\n` to splite different header, such as `A:a\nB:b` in which it could contain space char and this is allowed because of space char automatically cleared. In excel, more simply, you can copy request headers from Chrome Console and paste them to excel. In excel, one cell can display muti-line headers
3. As for parameters, whether it is form-data type data or json type data, you can directly open Chrome Console and find request body, click "view source" and then copy them, ultimately paste them to "parameters" column of excel. The way is also suitable for "parameters" in properties
4. Until now, some parameters in properties and excel has default value actually, such as http protocol defaults to http, port defaults to 8080, request path defaults to `/`, encoding defaults to UTF-8, request method defaults to GET
5. I suggest that if you want to test some service interface, you can create three properties. For example, project need test order service interface, you can config three properties and read them in BaseTest. If you want test lots of service interface, you can create relevant properteis, and then create specified excel to send request. If some service interface request informations are more similar except request path, they can share the same properties or  in excel they can use different sheets stand for every servcie. There are other ways to explore

# Key Contents

**framework highlight**

1. Set properties bean, excel row bean and request information bean
2. Utils contains excel, properties reader, http request sender and others
3. All test case extend BaseTest. BaseTest contains other small Base and test running periods
4. Test case extends BaseTest to use parameters in parent class
5. Mutiple properties data can be interinfiltration and produce a final properties. Properties and excel data can also be interinfiltration
6. A excel is just a test case. If you want test more test case, you can create more excel to test

**have realized**

1. All single interface test
2. Log
3. BeautifulReport
4. Imitate jmeter period, so the project can flexible driven different excel to send requests
6. Support post request, get request, parameters, headers, response body assert and others
7. Support reading excel and reading different sheets
8. Several kinds of listener

# Project Defects

**some minor problems to be improve**

1. Port number now is String not int
2. Recycling columns of excel, the exit of recycling now is a fixed value which is not friendly to function expansion. I suggest to change recycling exit to variable value
3. Consider to change bean class to bean interface
4. Thread safety synchronized keyword could be further optimized
5. In properties and excel, "  " is still identified as having data, but "" is identified as not having data. Later, i can consider not to identify space char

**to be realized**

1. Https request now has been identified as http. Although there is a util class about getting https and there is also some method about producing https request method in Base, they dose not work for i have tested. Later i will implement these
2. Now, this project support get and post. Later it will support more request method
3. Later, it will support cookie, and excel should also add a new column "cookie"
4. Now get method does not support parameter passing. It will be supported
5. Report use BeautifulReport and later it can support Allure
6. Due to the excel only test singel interface, later i consider to add a new function about use process interface to test
7. Etc
