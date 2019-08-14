package com.abcnull.apiautotest.bases;

import com.abcnull.apiautotest.constants.BaseConstant;
import org.testng.annotations.*;

import java.io.IOException;

/**
 * BaseTest
 * This Class integrated with BaseProperties and BaseXls is aimed to initialize api configuration BeforeSuit,BeforeTest
 * and BeforeMethod and aimed to read requests info in the excel,so that HTTP request can be send successfully according
 * to api configuration and requests info in Test
 *
 * @author abcnull
 * @version 1.0.0
 * @date 2019/8/1
 */
public class BaseTest {
    /**
     * A base Class to read properties files of function testPlan
     */
    private BaseProperties basePropertiesInTestPlan;
    /**
     * Whether api configuration exists in TestPlan or not
     * Default false means not exist
     */
    private boolean isPropertiesInTestPlan;

    /**
     * A base Class to read properties files of function threadGroup
     */
    private BaseProperties basePropertiesInThreadGroup;
    /**
     * Whether api configuration exists in ThreadGroup or not
     * Default false means not exist
     */
    private boolean isPropertiesInThreadGroup;

    /**
     * A base Class to read properties files of function simpleController
     */
    private BaseProperties basePropertiesInSimpleController;
    /**
     * Whether api configuration exists in SimpleController or not
     * Default false means not exist
     */
    private boolean isPropertiesInSimpleController;

    /**
     * final baseProperties
     */
    protected BaseProperties baseProperties;

    /**
     * A base Class to read xls files about API data of function simpleController
     */
    protected BaseXls baseXls;

    /**
     * BaseTest constructor
     */
    public BaseTest(){
        this.basePropertiesInTestPlan = null;
        this.isPropertiesInTestPlan = false;

        this.basePropertiesInThreadGroup = null;
        this.isPropertiesInThreadGroup = false;

        this.basePropertiesInSimpleController = null;
        this.isPropertiesInSimpleController = false;

        this.baseProperties = null;
        this.baseXls = null;
    }

    /**
     * TestPlan front inside
     * Execute this function in TestPlan similar to Jmeter tool
     */
    @Parameters("apiConfigName")
    @BeforeSuite
    public void testPlanFrontInside(@Optional("") String apiConfigName) throws IOException {
        // make sure api configuration is in TestPlan or not
        if(!apiConfigName.equals("")){
            isPropertiesInTestPlan = true;
        }
        // if api configuration is in TestPlan...
        if(isPropertiesInTestPlan){
            /* ========== Read properties file in TestPlan ========== */
            // read all data in BaseProperties
            basePropertiesInTestPlan = new BaseProperties(apiConfigName, BaseConstant.PERIOD_TESTPLAN);
        }
    }
    /**
     * TestPlan back inside
     * In order to release resources in the same thread
     */
    @AfterSuite
    public void testPlanBackInside(){
        // release BasePropertiesInTestPlan
        basePropertiesInTestPlan = null;
        // set IsPropertiesInTestPlan default(false)
        isPropertiesInTestPlan = false;
    }

    /**
     * ThreadGroup front inside
     * Execute this function in ThreadGroup similar to Jmeter tool
     */
    @Parameters("apiConfigName")
    @BeforeTest
    public void threadGroupFrontInside(@Optional("") String apiConfigName) throws IOException {
        // make sure api configuration is in ThreadGroup or not
        if(!apiConfigName.equals("")){
            isPropertiesInThreadGroup = true;
        }
        // if api configuration is in ThreadGroup...
        if(isPropertiesInThreadGroup){
            /* ========== Read properties file in ThreadGroup ========== */
            // read all data in BaseProperties
            basePropertiesInThreadGroup = new BaseProperties(apiConfigName, BaseConstant.PERIOD_THREADGROUP);
        }
    }
    /**
     * ThreadGroup back inside
     * In order to release resources in the same thread
     */
    @AfterTest
    public void threadGroupBackInside(){
        // release BasePropertiesInThreadGroup
        basePropertiesInThreadGroup = null;
        // set IsPropertiesInThreadGroup default(false)
        isPropertiesInThreadGroup = false;
    }

    /**
     * Execute this function in LogicController include all kinds of controllers similar to Jmeter tool
     * Here LogicController is not a concrete Controller,but an abstract structure or a abstract Controller which
     * include all kinds of controllers
     */
    @Parameters("")
    @BeforeClass
    public void logicControllerFrontInside(){}
    /**
     * In order to release resources in the same thread
     */
    @AfterClass
    public void logicControllerBackInside(){}

    /**
     * Execute this function in GroupsController a little different from Jmeter
     * Here GroupsController is not a concrete Controller,but an abstract structure or a special Controller which
     * specifies an attribute that all Request in it must have otherwise these Request can not be in it
     */
    @Parameters("")
    @BeforeGroups
    public void GroupsControllerFrontInside(){}
    /**
     * In order to release resources in the same thread
     */
    @AfterGroups
    public void GroupsControllerBackInside(){}

    /**
     * SimpleController front inside
     * Execute this function in SimpleController similar to Jmeter tool
     * Reset the API configuration and read the API requests data by excelName and sheetName
     */
    @Parameters({"apiConfigName","excelName","sheetName"})
    @BeforeMethod
    public void simpleControllerFrontInside(@Optional("") String apiConfigName, @Optional("") String excelName, @Optional("Sheet1") String sheetName) throws Exception {
        /* ========== Set API configuration ========== */
        // make sure api configuration is in SimpleController or not
        if(!apiConfigName.equals("")){
            isPropertiesInSimpleController = true;
        }
        // if api configuration is in SimpleController...
        if(isPropertiesInSimpleController){
            /* ========== Read properties file in SimpleController ========== */
            // read all data in BaseProperties
            basePropertiesInSimpleController = new BaseProperties(apiConfigName, BaseConstant.PERIOD_SIMPLECONTROLLER);
        }
        /* ========== Read Excel data ========== */
        if(sheetName.equals("")) {
            baseXls = new BaseXls(excelName);
        }
        else{
            baseXls = new BaseXls(excelName, sheetName);
        }
        /* ========== Select ultimate properties ========== */
        if(isPropertiesInSimpleController){
            baseProperties = basePropertiesInSimpleController;
        }
        else if(isPropertiesInThreadGroup){
            baseProperties = basePropertiesInThreadGroup;
        }
        else if(isPropertiesInTestPlan){
            baseProperties = basePropertiesInTestPlan;
        }
    }
    /**
     * SimpleController back inside
     * In order to release resources in the same thread
     */
    @AfterMethod
    public void simpleControllerBackInside(){
        // release BasePropertiesInSimpleController
        basePropertiesInSimpleController = null;
        // set IsPropertiesInSimpleController default(false)
        isPropertiesInSimpleController = false;
        // set baseXls null
        baseXls = null;
        // release BaseProperties
        baseProperties = null;
    }
}