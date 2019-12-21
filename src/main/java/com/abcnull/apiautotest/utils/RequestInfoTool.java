package com.abcnull.apiautotest.utils;

import com.abcnull.apiautotest.beans.PropertiesBean;
import com.abcnull.apiautotest.beans.RequestBean;
import com.abcnull.apiautotest.beans.XlsBean;
import com.abcnull.apiautotest.constants.BaseConstant;

/**
 * Collect request info from xls and properties
 *
 * @author lei.shi06@hand-china.com
 * @version 1.0.0
 * @date 2019/12/19
 */
public class RequestInfoTool {
    /**
     * The union of propertiesBean and xlsBean
     *
     * @param propertiesBean properties info
     * @param xlsBean xls info
     * @return RequestBean
     */
    public static RequestBean getRequestBean(PropertiesBean propertiesBean, XlsBean xlsBean){
        RequestBean requestBean = new RequestBean();

        /* Xls Exclusive */
        // number
        requestBean.setNumber(xlsBean.getNumber());
        // enable
        requestBean.setEnable(xlsBean.getEnable());
        // method
        requestBean.setMethod(xlsBean.getMethod());
        // responseAssertion
        requestBean.setResponseAssertion(xlsBean.getResponseAssertion());

        /* Properties Exclusive */
        // cookies
        requestBean.setCookies(propertiesBean.getCookies());
        // username
        requestBean.setUsername(propertiesBean.getUsername());
        // password
        requestBean.setPassword(propertiesBean.getPassword());

        /* Common */
        // headers
        if(xlsBean.getHeaders() != null && !xlsBean.getHeaders().equals("")){
            requestBean.setHeaders(xlsBean.getHeaders());
        }
        else{
            requestBean.setHeaders(propertiesBean.getHeaders());
        }
        // protocol
        if(xlsBean.getProtocol() != null && !xlsBean.getProtocol().equals("")){
            requestBean.setProtocol(xlsBean.getProtocol());
        }
        else{
            requestBean.setProtocol(propertiesBean.getProtocol());
        }
        // ip
        if(xlsBean.getIp() != null && !xlsBean.getIp().equals("")){
            requestBean.setIp(xlsBean.getIp());
        }
        else{
            requestBean.setIp(propertiesBean.getIp());
        }
        // portNumber
        if(xlsBean.getPortNumber() != null && !xlsBean.getPortNumber().equals("")){
            requestBean.setPortNumber(xlsBean.getPortNumber());
        }
        else{
            requestBean.setPortNumber(propertiesBean.getPortNumber());
        }
        // path
        if(xlsBean.getPath() != null && !xlsBean.getPath().equals("")){
            requestBean.setPath(xlsBean.getPath());
        }
        else{
            requestBean.setPath(propertiesBean.getPath());
        }
        // contentEncoding
        if(xlsBean.getContentEncoding() != null && !xlsBean.getContentEncoding().equals("")){
            requestBean.setContentEncoding(xlsBean.getContentEncoding());
        }
        else{
            requestBean.setContentEncoding(propertiesBean.getContentEncoding());
        }
        // parameters
        if(xlsBean.getParameters() != null && !xlsBean.getParameters().equals("")){
            requestBean.setParameters(xlsBean.getParameters());
        }
        else{
            requestBean.setParameters(propertiesBean.getParameters());
        }
        // comments
        if(xlsBean.getComments() != null && !xlsBean.getComments().equals("")){
            requestBean.setComments(xlsBean.getComments());
        }
        else{
            requestBean.setComments(propertiesBean.getComments());
        }

        /* Set Default Value */
        // default protocol
        if(requestBean.getProtocol() == null || requestBean.getProtocol().equals("")){
            requestBean.setProtocol(BaseConstant.DEFAULT_PROTOCOL);
        }
        // default portNumber
        if(requestBean.getPortNumber() == null || requestBean.getPortNumber().equals("")){
            requestBean.setPortNumber(BaseConstant.DEFAULT_PORTNUMBER);
        }
        // default path
        if(requestBean.getPath() == null || requestBean.getPath().equals("")){
            requestBean.setPath(BaseConstant.DEFAULT_PATH);
        }
        // default contentEncoding
        if(requestBean.getContentEncoding() == null || requestBean.getContentEncoding().equals("")){
            requestBean.setContentEncoding(BaseConstant.DEFAULT_CONTENTENCODING);
        }
        // default method
        if(requestBean.getMethod() == null || requestBean.getMethod().equals("")){
            requestBean.setMethod(BaseConstant.DEFAULT_METHOD);
        }

        return requestBean;
    }

    /**
     * The union of several PropertiesBean
     *
     * @param propertiesBean PropertiesBean...
     * @return PropertiesBean
     */
    public static PropertiesBean getPropertiesBean(PropertiesBean... propertiesBean){
        PropertiesBean myPropertiesBean = new PropertiesBean();
        for(PropertiesBean pb : propertiesBean){
            // protocol
            if(pb.getProtocol() != null && !pb.getProtocol().equals("")){
                myPropertiesBean.setProtocol(pb.getProtocol());
            }
            // ip
            if(pb.getIp() != null && !pb.getIp().equals("")){
                myPropertiesBean.setIp(pb.getIp());
            }
            // portNumber
            if(pb.getPortNumber() != null && !pb.getPortNumber().equals("")){
                myPropertiesBean.setPortNumber(pb.getPortNumber());
            }
            // path
            if(pb.getPath() != null && !pb.getPath().equals("")){
                myPropertiesBean.setPath(pb.getPath());
            }
            // contentEncoding
            if(pb.getContentEncoding() != null && !pb.getContentEncoding().equals("")){
                myPropertiesBean.setContentEncoding(pb.getContentEncoding());
            }
            // parameters
            if(pb.getParameters() != null && !pb.getParameters().equals("")){
                myPropertiesBean.setParameters(pb.getParameters());
            }
            // comments
            if(pb.getComments() != null && !pb.getComments().equals("")){
                myPropertiesBean.setComments(pb.getComments());
            }
            // headers
            if(pb.getHeaders() != null && !pb.getHeaders().equals("")){
                myPropertiesBean.setHeaders(pb.getHeaders());
            }
            // cookies
            if(pb.getCookies() != null && !pb.getCookies().equals("")){
                myPropertiesBean.setCookies(pb.getCookies());
            }
            // username
            if(pb.getUsername() != null && !pb.getUsername().equals("")){
                myPropertiesBean.setUsername(pb.getUsername());
            }
            // password
            if(pb.getPassword() != null && !pb.getPassword().equals("")){
                myPropertiesBean.setPassword(pb.getPassword());
            }
        }
        return myPropertiesBean;
    }
}
