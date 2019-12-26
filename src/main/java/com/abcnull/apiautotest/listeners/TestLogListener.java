package com.abcnull.apiautotest.listeners;

import lombok.extern.slf4j.Slf4j;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

/**
 * TestLogListener
 *
 * @author lei.shi06@hand-china.com
 * @version 1.0.0
 * @date 2019/12/26
 */
@Slf4j
public class TestLogListener extends TestListenerAdapter {
    /**
     * When project start
     *
     * @param iTestContext iTestContext
     */
    @Override
    public void onStart(ITestContext iTestContext) {
        super.onStart( iTestContext );
        log.info( String.format( "====================%s project start ====================", iTestContext.getName() ) );
    }

    /**
     * When test start
     *
     * @param iTestResult iTestResult
     */
    @Override
    public void onTestStart(ITestResult iTestResult) {
        super.onTestStart( iTestResult );
        log.info( String.format( "========%s.%s test start ========", iTestResult.getInstanceName(), iTestResult.getName()) );
    }

    /**
     * When test success
     *
     * @param iTestResult iTestResult
     */
    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        super.onTestSuccess( iTestResult );
        log.info( String.format( "========%s.%s test pass ========", iTestResult.getInstanceName(), iTestResult.getName()) );
    }

    /**
     * When test fail
     *
     * @param iTestResult iTestResult
     */
    @Override
    public void onTestFailure(ITestResult iTestResult) {
        super.onTestFailure( iTestResult );
        log.error( String.format( "========%s.%s test fail, cause of test is: \n%s========", iTestResult.getInstanceName(), iTestResult.getName(), iTestResult.getThrowable() ));
    }

    /**
     * When test skip
     * @param iTestResult iTestResult
     */
    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        super.onTestSkipped( iTestResult );
        log.info( String.format( "========%s.%s test skip ========", iTestResult.getInstanceName(), iTestResult.getName()) );
    }

    /**
     * When project finish
     *
     * @param iTestContext iTestResult
     */
    @Override
    public void onFinish(ITestContext iTestContext) {
        super.onFinish( iTestContext );
        log.info( String.format( "====================%s test over ====================", iTestContext.getName() ) );
    }
}
