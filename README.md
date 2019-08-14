# api-auto-test
- FIRST CREATION IN EARLY AUGUST 2019
    
    The code include the overall framework but roughly structured.It also contains many loopholes and unreasonable point
    
- AMEND ON 2019/08/14

    The amended parts are as follows:
    1. add detailed log code,like `log.info`,`log.warn`,`log.error`,etc.
    2. supplement xls and allow xls add extra column by rewrite loop condition
    3. modify the way reading the file
    
    Now Problems:
    1. **NonePointerException**: There are some problems about assign column data to XlsBean in the Excel
    2. etc.
        
    To be added below:
    1. judge header allowed none
    2. params of get request haven't be dealt with and need judge params of post allowed none or not
    3. haven't deal with space of the String responseAssertion
    4. need select a comments between xls and properties to add in log info
    5. the function of pressure test need be added
    6. the function of test a serials requests which exist params dependencies
    7. etc.