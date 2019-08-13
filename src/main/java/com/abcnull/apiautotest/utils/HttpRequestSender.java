package com.abcnull.apiautotest.utils;

import com.abcnull.apiautotest.beans.PropertiesBean;
import com.abcnull.apiautotest.beans.XlsBean;
import com.abcnull.apiautotest.constants.BaseConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

/**
 * Http request sender
 * The util is used to send request according api configuration(PropertiesBean) and request info in xls file(xlsMap)
 *
 * @author abcnull
 * @version 1.0.0
 * @date 2019/8/9
 */
@Slf4j
public class HttpRequestSender {
    /**
     * API configuration file
     */
    private PropertiesBean propertiesBean;

    /**
     * Xls HTTP Requests
     */
    private Map<String, List<XlsBean>> xlsMap;

    /**
     * Http client getter
     */
    private CloseableHttpClient closeableHttpClient;

    /**
     * Uniform Resource Identifier
     */
    private URI uri;

    /**
     * Get request
     */
    private HttpGet httpGet;

    /**
     * Post request
     */
    private HttpPost httpPost;

    /**
     * Response model
     */
    private CloseableHttpResponse closeableHttpResponse;

    /**
     * Entity of Response
     */
    private HttpEntity httpEntity;

    /**
     * HttpRequestSender constructor
     *
     * @param propertiesBean Properties data
     * @param xlsMap Xls data
     */
    public HttpRequestSender(PropertiesBean propertiesBean, Map<String, List<XlsBean>> xlsMap){
        init(propertiesBean, xlsMap);
    }

    /**
     * Init fields related with request data
     */
    private void init(PropertiesBean propertiesBean, Map<String, List<XlsBean>> xlsMap){
        // API configuration data
        this.propertiesBean = propertiesBean;
        // HTTP Request data
        this.xlsMap = xlsMap;
        /* closeableHttpClient -> parameters -> uri -> httpGet/httpPost -> closeableHttpResponse */
        // HTTP client
        this.closeableHttpClient = HttpClientBuilder.create().build();
        // uri
        this.uri = null;
        // httpGet
        this.httpGet = null;
        // httpPost
        this.httpPost = null;
        // HTTP response
        this.closeableHttpResponse = null;
        // httpEntity
        this.httpEntity = null;
    }

    /**
     * Call this function to send GET/POST request according to 2 fields propertiesBean and xlsMap
     */
    public void send() {
        /* ========== Traverse sheet ========== */
        xlsMap.forEach((sheet, requestList) -> {
            /* ========== Traverse row request ========== */
            requestList.forEach((request) -> {
                /* check number and enable fields to decide whether continue or not */
                if(request.getNumber() == null || !request.getEnable().equalsIgnoreCase("y")){
                    log.warn("Warning:Number 为空或者是否执行没有标记为 Y！跳过此行请求！");
                    return;
                }
                /* init 4 params that uri need */
                String protocol = null;
                String ip = null;
                String portNumber = null;
                String path = null;
                /* check where these 4 params exist,in propertiesBean or xlsMap */
                // protocol
                if (request.getProtocol() != null && !request.getProtocol().equals("")) {
                    protocol = request.getProtocol();
                } else {
                    if (propertiesBean.getProtocol() != null && !propertiesBean.getProtocol().equals("")) {
                        protocol = propertiesBean.getProtocol();
                    } else {
                        protocol = BaseConstant.DEFAULT_PROTOCOL;
                    }
                }
                // ip
                if (request.getIp() != null && !request.getIp().equals("")) {
                    ip = request.getIp();
                } else {
                    if (propertiesBean.getIp() != null && !propertiesBean.getIp().equals("")) {
                        ip = propertiesBean.getIp();
                    } else {
                        // only allowed to throw Unchecked Exception for the bug of lambda expression,:(
                        log.error("Error:number 为 " + request.getNumber() + " 的请求 IP 为空是不被允许的！跳过此行请求！");
                        return;
                    }
                }
                // portNumber
                if (request.getPortNumber() != null && !request.getPortNumber().equals("")) {
                    portNumber = request.getPortNumber();
                } else {
                    if (propertiesBean.getPortNumber() != null && !propertiesBean.getPortNumber().equals("")) {
                        portNumber = propertiesBean.getPortNumber();
                    } else {
                        portNumber = BaseConstant.DEFAULT_PORTNUMBER;
                    }
                }
                // path
                if (request.getPath() != null && !request.getPath().equals("")) {
                    path = request.getPath();
                } else {
                    if (propertiesBean.getPath() != null && !propertiesBean.getPath().equals("")) {
                        path = propertiesBean.getPath();
                    } else {
                        // only allowed to throw Unchecked Exception for the bug of lambda expression,:(
                        log.error("Error:number 为 " + request.getNumber() + " 的请求 path 为空是不被允许的！跳过此行请求！");
                        return;
                    }
                }
                /* init uri */
                try {
                    uri = new URIBuilder().setScheme(protocol).setHost(ip).setPort(Integer.parseInt(portNumber)).setPath(path).build();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                /* choose Get or Post method */
                // init 3 params
                closeableHttpClient = HttpClientBuilder.create().build();
                closeableHttpResponse = null;
                httpEntity = null;
                if(request.getMethod().equalsIgnoreCase("GET") || request.getMethod().equals("")){
                    /* ========== Send GET Request ========== */
                    // use Get
                    httpGet = new HttpGet(uri);
                    /* set Get request header */
                    httpGet.setHeaders(propertiesBean.getHeadersArray());
                    // execute GET request
                    try {
                        closeableHttpResponse = closeableHttpClient.execute(httpGet);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    if(request.getMethod().equalsIgnoreCase("POST")){
                        /* ========== Send POST Request ========== */
                        // use POST
                        httpPost = new HttpPost(uri);
                        /* set POST request header */
                        httpPost.setHeaders(propertiesBean.getHeadersArray());
                        // init parameters
                        String parameters = null;
                        if (request.getParameters() == null || request.getParameters().equals("")) {
                            // if parameters not exist in request row
                            if (propertiesBean.getParameters() == null || propertiesBean.getParameters().equals("")) {
                                // if parameters not exist in api configuration
                                log.info("Info:number 为 " + request.getNumber() +" 的请求无参数");
                            }
                            else {
                                // parameters exist in api configuration
                                parameters = propertiesBean.getParameters();
                            }
                        }
                        else {
                            // parameters exist in request
                            parameters = request.getParameters();
                        }
                        // init contentEncoding
                        String contentEncoding = null;
                        if (request.getContentEncoding() == null || request.getContentEncoding().equals("")) {
                            // if contentEncoding not exist in request row
                            if (propertiesBean.getContentEncoding() == null || propertiesBean.getContentEncoding().equals("")) {
                                // if contentEncoding not exist in api configuration
                                log.info("Info:number 为 " + request.getNumber() +" 的请求未设置编码方式，默认采用 UTF-8");
                                contentEncoding = BaseConstant.DEFAULT_CONTENTENCODING;
                            }
                            else {
                                // contentEncoding exist in api configuration
                                contentEncoding = propertiesBean.getContentEncoding();
                            }
                        }
                        else {
                            // contentEncoding exist in request row
                            contentEncoding = request.getContentEncoding();
                        }
                        // set POST request entity
                        StringEntity stringEntity = new StringEntity(parameters, contentEncoding);
                        httpPost.setEntity(stringEntity);
                        // execute POST request
                        try {
                            closeableHttpResponse = closeableHttpClient.execute(httpPost);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        log.warn("Warning:number 为 " + request.getNumber() + " 的请求中请求方式仅支持 GET 和 POST，为空时默认为 GET，暂不支持其他类型请求，或者请求方式填写有误！请检查！跳过此请求！");
                        return;
                    }
                }
                /* assert result */
                try {
                    httpEntity = closeableHttpResponse.getEntity();
                    /**
                     // response status
                     System.out.println("Response status is " + closeableHttpResponse.getStatusLine());
                     // the length of response entity
                     System.out.println("The length of response entity is " + httpEntity.getContentLength());
                     */
                    // only assert response entity contains responseAssertion or not which is in the Excel
                    assert EntityUtils.toString(httpEntity).contains(request.getResponseAssertion());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });
    }
}