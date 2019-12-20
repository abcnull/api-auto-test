package com.abcnull.apiautotest.beans;

import com.google.gson.Gson;
import com.sun.istack.internal.NotNull;
import lombok.Data;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * XlsBean stores a row of request info in Excel
 *
 * @author abcnull
 * @version 1.0.0
 * @date 2019/8/5
 */
@Data
public class XlsBean {
    /**
     * Number of HTTP Request
     */
    private String number;

    /**
     * Set API Request enable or disable
     */
    private String enable;

    /**
     * Protocol of API Request
     */
    private String protocol;

    /**
     * IP of API Request
     */
    private String ip;

    /**
     * PortNumber of API Request
     */
    private String portNumber;

    /**
     * Method of API Request
     */
    private String method;

    /**
     * Path of API Request
     * Expect not null but not mark @NotNull
     */
    private String path;

    /**
     * ContentEncoding of API Request
     */
    private String contentEncoding;

    /**
     * Parameters of API Request
     */
    private String parameters;

    /**
     * ResponseAssertion of API Request
     */
    private String responseAssertion;

    /**
     * Comments of API Request
     */
    private String comments;

    /**
     * XlsBean Constructor
     */
    public XlsBean() {
        this.number = "";
        this.enable = "";
        this.protocol = "";
        this.ip = "";
        this.portNumber = "";
        this.method = "";
        this.path = "";
        this.contentEncoding = "";
        this.parameters = "";
        this.responseAssertion = "";
        this.comments = "";
    }

    /**
     * Convert parameters of String to List of NameValuePair
     *
     * @return List of NameValuePair
     */
    public List<NameValuePair> getParametersList(){
        /* convert json string to map using gson */
        // gson
        Gson gson = new Gson();
        // hashMap
        Map<String, Object> map = new HashMap<>();
        // begin converting...
        map = gson.fromJson(parameters, map.getClass());
        // list
        List<NameValuePair> list = new ArrayList<>();
        // traverse map and fill map data in List<NameValuePair>
        map.forEach((str, obj) ->
                list.add(new BasicNameValuePair(str, (String)obj))
        );
        return list;
    }
}