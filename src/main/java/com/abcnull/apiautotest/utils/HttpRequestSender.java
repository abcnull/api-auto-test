package com.abcnull.apiautotest.utils;

import com.abcnull.apiautotest.beans.PropertiesBean;
import com.abcnull.apiautotest.beans.RequestBean;
import com.abcnull.apiautotest.beans.XlsBean;
import com.abcnull.apiautotest.constants.BaseConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.omg.PortableInterceptor.RequestInfo;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

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
        log.info("=== Start sending requests of Excel... ===");
        // store assert flag map
        Map<String, List<Boolean>> assertFlagMap = new LinkedHashMap<>();
        /* ========== Traverse sheet ========== */
        xlsMap.forEach((sheet, requestList) -> {
            // store assert flag map
            List<Boolean> assertFlagList = new LinkedList<>();
            /* ========== Traverse row request ========== */
            requestList.forEach((request) -> {
                /* final request configuration joins propertiesBean with request */
                RequestBean requestBean = RequestInfoTool.getRequestBean(propertiesBean, request);

                /* All request info */
                String number = requestBean.getNumber();
                String enable = requestBean.getEnable();
                String protocol = requestBean.getProtocol();
                String ip = requestBean.getIp();
                String portNumber = requestBean.getPortNumber();
                String method = requestBean.getMethod();
                String path = requestBean.getPath();
                String contentEncoding = requestBean.getContentEncoding();
                String parameters = requestBean.getParameters();
                String responseAssertion = requestBean.getResponseAssertion();
                String comments = requestBean.getComments();
                String headers = requestBean.getHeaders();
                Header[] headersArray = requestBean.getHeadersArray();
                String cookies = requestBean.getCookies();
                String username = requestBean.getUsername();
                String password = requestBean.getPassword();

                /* Check params at the beginning */
                // check ip to decide continue or not
                if (ip == null || ip.equals("")) {
                    // only allowed to throw Unchecked Exception for the bug of lambda expression,:(
                    log.error("═┄ ERROR: No." + number + " [" + method + "] No IP Not Allowed! Skip This Request");
                    return;
                }
                if (!protocol.equalsIgnoreCase("http") && !protocol.equalsIgnoreCase("httpss")) {
                    log.error("═┄ ERROR: No." + number + " [" + method + "] Other Protocol Not Allowed! Skip This Request");
                }
                // check method to decide continue or not
                if (!method.equalsIgnoreCase("GET") && !method.equalsIgnoreCase("POST")) {
                    log.warn("═┄ WARNING: No." + number + " " + method +" IS NOT SUPPORTED NOW! Skip This Request");
                    return;
                }
                // check number and enable fields
                if (number == null || number.equals("") || !enable.equalsIgnoreCase("y")) {
                    log.warn("═┄ WARNING: No Number Not Allowed Or Enable Not \"Y\"");
                }

                /* Other params */
                // uri
                try {
                    uri = new URIBuilder().setScheme(protocol).setHost(ip).setPort(Integer.parseInt(portNumber)).setPath(path).build();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                // if protocol is http
                if (protocol.equalsIgnoreCase("http")) {
                    closeableHttpClient = HttpClientBuilder.create().build();
                }
                // else https
                else if (protocol.equalsIgnoreCase("https")) {
                    try {
                        closeableHttpClient = wrapClient("https://", "");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                // else others
                else {
                    return;
                }
                closeableHttpResponse = null;
                httpEntity = null;
                String httpEntityStr = null;

                /* choose Get or Post method */
                if(method.equalsIgnoreCase("GET")){
                    /* ========== Send GET Request ========== */
                    log.info("┌┄ No." + number + " [" + method +"] READY TO SEND...");
                    // use Get
                    httpGet = new HttpGet(uri);
                    /* set Get request header */
                    log.info("├┄ Setting Request Header...(read from properties)");
                    httpGet.setHeaders(headersArray);
                    // execute GET request
                    try {
                        log.info("├┄ Sending...");
                        closeableHttpResponse = closeableHttpClient.execute(httpGet);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    if(method.equalsIgnoreCase("POST")){
                        /* ========== Send POST Request ========== */
                        log.info("┌┄ No." + number + " " + method +" SEND...");
                        // use POST
                        httpPost = new HttpPost(uri);
                        /* set POST request header */
                        log.info("├┄ Setting Request Header...");
                        httpPost.setHeaders(headersArray);
                        if (parameters == null || parameters.equals("")) {
                            parameters = "";
                            log.warn("├┄ WARNING: No Parameters Found!");
                        }
                        // set POST request entity
                        log.info("├┄ Setting Request Body And ContentEncoding...");
                        StringEntity stringEntity = new StringEntity(parameters, contentEncoding);
                        httpPost.setEntity(stringEntity);
                        // execute POST request
                        try {
                            log.info("├┄ Sending...");
                            closeableHttpResponse = closeableHttpClient.execute(httpPost);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        /* ========== Neither Get nor Post ========== */
                        return;
                    }
                }

                /* assert result */
                try {
                    log.info("├┄ Receiving...");
                    httpEntity = closeableHttpResponse.getEntity();
                    httpEntityStr = EntityUtils.toString(httpEntity, BaseConstant.DEFAULT_CONTENTENCODING);
                    /**
                     // response status
                     System.out.println("Response status is " + closeableHttpResponse.getStatusLine());
                     // the length of response entity
                     System.out.println("The length of response entity is " + httpEntity.getContentLength());
                     */
                    // set assert flag value in assertFlagList
                    if (httpEntityStr.contains(responseAssertion)) {
                        assertFlagList.add(true);
                    }
                    else {
                        assertFlagList.add(false);
                    }
                    // only assert response entity contains responseAssertion or not which is in the Excel
                    log.info("└┄ Assertion Result: ["+ httpEntityStr.contains(responseAssertion) +"]");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            // set assert flag value in assertFlagMap
            assertFlagMap.put(sheet, assertFlagList);
        });

        /* Assert every node in assertFlagMap until a false */
        assertFlagMap.forEach((sheet, assertFlagList) -> {
            assertFlagList.forEach((assertFlag) -> {
                assert assertFlag;
            });
        });
    }

    private static CloseableHttpClient wrapClient(String host,String path) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        if (host != null && host.startsWith("https://")) {
            return sslClient();
        }else if (StringUtils.isBlank(host) && path != null && path.startsWith("https://")) {
            return sslClient();
        }
        return httpClient;
    }

    /**
     * 在调用SSL之前需要重写验证方法，取消检测SSL
     * 创建ConnectionManager，添加Connection配置信息
     * @return HttpClient 支持https
     */
    private static CloseableHttpClient sslClient() {
        try {
            // 在调用SSL之前需要重写验证方法，取消检测SSL
            X509TrustManager trustManager = new X509TrustManager() {
                @Override public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                @Override public void checkClientTrusted(X509Certificate[] xcs, String str) {}
                @Override public void checkServerTrusted(X509Certificate[] xcs, String str) {}
            };
            SSLContext ctx = SSLContext.getInstance(SSLConnectionSocketFactory.TLS);
            ctx.init(null, new TrustManager[] { trustManager }, null);
            SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(ctx, NoopHostnameVerifier.INSTANCE);
            // 创建Registry
            RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT)
                    .setExpectContinueEnabled(Boolean.TRUE).setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM,AuthSchemes.DIGEST))
                    .setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC)).build();
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .register("https",socketFactory).build();
            // 创建ConnectionManager，添加Connection配置信息
            PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            CloseableHttpClient closeableHttpClient = HttpClients.custom().setConnectionManager(connectionManager)
                    .setDefaultRequestConfig(requestConfig).build();
            return closeableHttpClient;
        } catch (KeyManagementException ex) {
            throw new RuntimeException(ex);
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }

}