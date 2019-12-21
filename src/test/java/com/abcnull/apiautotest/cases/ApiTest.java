package com.abcnull.apiautotest.cases;

import com.abcnull.apiautotest.bases.BaseTest;
import com.abcnull.apiautotest.beans.PropertiesBean;
import com.abcnull.apiautotest.beans.XlsBean;
import com.abcnull.apiautotest.utils.HttpRequestSender;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

/**
 * Here the testCase send Http Requests
 *
 * @author abcnull
 * @version 1.0.0
 * @date 2019/8/5
 */
@Slf4j
public class ApiTest extends BaseTest {
    @Test(description = "apiTest")
    public void apiTest(){
        // init http request sender
        HttpRequestSender httpRequestSender = new HttpRequestSender(super.propertiesBean, super.xlsMap);
        // send all request
        httpRequestSender.send();
    }
}