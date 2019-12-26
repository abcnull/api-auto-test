**[中文](https://github.com/abcnull/api-auto-test/blob/master/README_zh.md) | [博客](https://blog.csdn.net/abcnull/article/details/103722306)**

[TOC]

# api-auto-test
- FIRST CREATION IN EARLY AUGUST 2019
    
    The code include the overall framework but roughly structured.It also contains many loopholes and unreasonable point
    
- AMEND ON 2019/08/14

    The amended parts are as follows:
    1. add detailed log code,like `log.info`,`log.warn`,`log.error`,etc.
    2. supplement xls and allow xls add extra column by rewrite loop condition
    3. modify the way reading the file
        
    To be added below:
    1. judge header allowed none
    2. params of get request haven't be dealt with and need judge params of post allowed none or not
    3. haven't deal with space of the String responseAssertion
    4. need select a comments between xls and properties to add in log info
    5. the function of pressure test need be added
    6. the function of test a serials requests which exist params dependencies
    7. etc.

    
- AMEND ON 2019/08/16

    The amended parts are as follows:
    1. set fields of bean to "",cuz null could cause unexpected problems
    2. amend if else at `getCell().toString()`,cuz null can't convert to String
    3. 
