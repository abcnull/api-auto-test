package com.abcnull.apiautotest.beans;

import lombok.Data;

/**
 * RequestBean contains all information of one request
 *
 * @author lei.shi06@hand-china.com
 * @version 1.0.0
 * @date 2019/12/19
 */
@Data
public class RequestBean {
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
     * Header of Header Manager
     */
    private String headers;

    /**
     * Cookie of Cookie Manager
     */
    private String cookies;

    /**
     * Username of Authorization Manager
     */
    private String username;

    /**
     * Password of Authorization Manager
     */
    private String password;

    /**
     * RequestBean Constructor
     */
    public RequestBean() {
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
        this.headers = "";
        this.cookies = "";
        this.username = "";
        this.password = "";
    }
}