package com.abcnull.apiautotest.bases;

import com.abcnull.apiautotest.beans.PropertiesBean;
import com.abcnull.apiautotest.beans.XlsBean;
import com.abcnull.apiautotest.constants.BaseConstant;
import com.abcnull.apiautotest.utils.RequestInfoTool;
import org.testng.annotations.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
     * A base Class to read xls files about API data of function simpleController
     */
    protected BaseXls baseXls;

    /**
     * PropertiesBean stores final request info of properties
     */
    protected PropertiesBean propertiesBean;

    /**
     * Xls stores final request info of xls
     */
    protected Map<String, List<XlsBean>> xlsMap;

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

        this.baseXls = null;

        this.propertiesBean = null;
        this.xlsMap = null;
    }

    /**
     * TestPlan front inside
     * Execute this function in TestPlan similar to Jmeter tool
     */
    @Parameters("apiConfigName_Plan")
    @BeforeSuite
    public void testPlanFrontInside(@Optional("") String apiConfigName_Plan) throws IOException {
        // make sure api configuration is in TestPlan or not
        if(!apiConfigName_Plan.equals("")){
            isPropertiesInTestPlan = true;
        }
        // if api configuration is in TestPlan...
        if(isPropertiesInTestPlan){
            /* ========== Read properties file in TestPlan ========== */
            // read all data in BaseProperties
            basePropertiesInTestPlan = new BaseProperties(apiConfigName_Plan, BaseConstant.PERIOD_TESTPLAN);
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
    @Parameters("apiConfigName_Thread")
    @BeforeTest
    public void threadGroupFrontInside(@Optional("") String apiConfigName_Thread) throws IOException {
        // make sure api configuration is in ThreadGroup or not
        if(!apiConfigName_Thread.equals("")){
            isPropertiesInThreadGroup = true;
        }
        // if api configuration is in ThreadGroup...
        if(isPropertiesInThreadGroup){
            /* ========== Read properties file in ThreadGroup ========== */
            // read all data in BaseProperties
            basePropertiesInThreadGroup = new BaseProperties(apiConfigName_Thread, BaseConstant.PERIOD_THREADGROUP);
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
     * SimpleController front inside
     * Execute this function in SimpleController similar to Jmeter tool
     * Reset the API configuration and read the API requests data by excelName and sheetName
     */
    @Parameters({"apiConfigName_Controller","excelName","sheetName"})
    @BeforeClass
    public void simpleControllerFrontInside(@Optional("") String apiConfigName_Controller, @Optional("") String excelName, @Optional("Sheet1") String sheetName) throws Exception {
        /* ========== Set API configuration ========== */
        // make sure api configuration is in SimpleController or not
        if(!apiConfigName_Controller.equals("")){
            isPropertiesInSimpleController = true;
        }
        // if api configuration is in SimpleController...
        if(isPropertiesInSimpleController){
            /* ========== Read properties file in SimpleController ========== */
            // read all data in BaseProperties
            basePropertiesInSimpleController = new BaseProperties(apiConfigName_Controller, BaseConstant.PERIOD_SIMPLECONTROLLER);
        }

        /* ========== Read Excel data ========== */
        if(sheetName.equals("")) {
            baseXls = new BaseXls(excelName);
        }
        else{
            baseXls = new BaseXls(excelName, sheetName);
        }
        // final request info in xlsMap
        xlsMap = baseXls.getXlsMap();
        /* ========== Select ultimate properties data ========== */
        ArrayList<PropertiesBean> arrayList = new ArrayList<>();
        if(isPropertiesInTestPlan){
            arrayList.add(basePropertiesInTestPlan.getPropertiesBean());
        }
        if(isPropertiesInThreadGroup){
            arrayList.add(basePropertiesInThreadGroup.getPropertiesBean());
        }
        if(isPropertiesInSimpleController){
            arrayList.add(basePropertiesInSimpleController.getPropertiesBean());
        }
        // final request info in propertiesBean
        propertiesBean = RequestInfoTool.getPropertiesBean(arrayList.toArray(new PropertiesBean[arrayList.size()]));
    }
    /**
     * SimpleController back inside
     * In order to release resources in the same thread
     */
    @AfterClass
    public void simpleControllerBackInside(){
        // release BasePropertiesInSimpleController
        basePropertiesInSimpleController = null;
        // set IsPropertiesInSimpleController default(false)
        isPropertiesInSimpleController = false;
        // set baseXls null
        baseXls = null;
        // set xlsBean null
        xlsMap = null;
        // set propertiesBean null
        propertiesBean = null;
    }

    /**
     * Execute this function in LogicController include all kinds of controllers similar to Jmeter tool
     * Here LogicController is not a concrete Controller,but an abstract structure or a abstract Controller which
     * include all kinds of controllers
     */
    @Parameters("")
    @BeforeMethod
    public void httpListFrontInside(){}
    /**
     * In order to release resources in the same thread
     */
    @AfterMethod
    public void httpListControllerBackInside(){}

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
}