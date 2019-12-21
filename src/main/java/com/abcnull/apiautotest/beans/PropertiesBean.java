package com.abcnull.apiautotest.beans;

import com.google.gson.Gson;
import lombok.Data;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

import java.util.*;

/**
 * PropertiesBean store properties file data which is api configuration
 *
 * @author abcnull
 * @version 1.0.0
 * @date 2019/8/5
 */
@Data
public class PropertiesBean {
    /**
     * Protocol of Request Default
     */
    private String protocol;

    /**
     * IP of Request Default
     */
    private String ip;

    /**
     * PortNumber of Request Default
     */
    private String portNumber;

    /**
     * Path of Request Default
     */
    private String path;

    /**
     * ContentEncoding of Request Default
     */
    private String contentEncoding;

    /**
     * Parameter of Request Default
     */
    private String parameters;

    /**
     * Comments of Request Default
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
     * PropertiesBean constructor
     */
    public PropertiesBean() {
        this.protocol = "";
        this.ip = "";
        this.portNumber = "";
        this.path = "";
        this.contentEncoding = "";
        this.parameters = "";
        this.comments = "";
        this.headers = "";
        this.cookies = "";
        this.username = "";
        this.password = "";
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
        Map<String, Object> map = new HashMap<String, Object> ();
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

    /**
     * Convert headers of String to Header Array
     *
     * @return Header[]
     */
    public Header[] getHeadersArray(){
        // headers array split by char ";"
        String[] headerArray = headers.replaceAll(" ","").split("\n");
        // Header[]
        Header[] header = new Header[headerArray.length];
        // fill in Header[]
        for (int i = 0; i < headerArray.length; i++) {
            // key:value split by char ":"
            header[i] = new BasicHeader(headerArray[i].split(":")[0], headerArray[i].split(":")[1]);
        }
        return header;
    }

    /**
     * Convert cookies of String to Cookie Array
     *
     * @return Cookie[]
     */
    public Cookie[] getCookiesArray(){
        // cookies array split by char ";"
        String[] cookieArray = cookies.replaceAll(" ", "").split(";");
        // Cookie[]
        Cookie[] cookie = new Cookie[cookieArray.length];
        // fill in Cookie[]
        for (int i = 0; i < cookieArray.length; i++) {
            // key:value split by char ":"
            cookie[i] = new BasicClientCookie(cookieArray[i].split(":")[0], cookieArray[i].split(":")[1]);
        }
        return cookie;
    }
}