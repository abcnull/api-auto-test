package com.abcnull.apiautotest.bases;

import com.abcnull.apiautotest.beans.PropertiesBean;
import com.abcnull.apiautotest.constants.BaseConstant;
import com.abcnull.apiautotest.utils.PropertiesReader;
import lombok.Data;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * BaseProperties
 * This Class using util(PropertiesReader) is called by BaseTest Class mainly to get data of api configuration properties
 *
 * @author abcnull
 * @version 1.0.0
 * @date 2019/8/2
 */
@Data
public class BaseProperties {
    /**
     * PropertiesName
     */
    private String apiConfig;

    /**
     * PropertiesPath
     */
    private String apiConfigPath;

    /**
     * PropertiesBean
     */
    private PropertiesBean propertiesBean;

    /**
     * BaseProperties constructor
     *
     * @param apiConfig the name of the api configuration file
     */
    public BaseProperties(String apiConfig, String period) throws IOException {
        init(apiConfig, period);
    }

    /**
     * Init BaseProperties fields
     *
     * @throws IOException read operation of PropertiesReader
     */
    private void init(String apiConfig, String period) throws IOException {
        // the name of api configuration file
        this.apiConfig = apiConfig;
        // the path of the file named apiConfig
        this.apiConfigPath = Paths.get("src/test/resources/apiconfigs/" + period, apiConfig).toString();
        // init PropertiesBean
        this.propertiesBean = new PropertiesBean();
        /* ========== read and get values of properties need to be in a synchronized code block ========== */
        synchronized (this){
            // save config data in an util of PropertiesReader
            PropertiesReader.readProperties(apiConfigPath);
            /* save the values of properties name apiConfig below in a properties bean */
            this.propertiesBean.setProtocol(PropertiesReader.getValue("protocol"));
            this.propertiesBean.setIp(PropertiesReader.getValue("ip"));
            this.propertiesBean.setPortNumber(PropertiesReader.getValue("portNumber"));
            this.propertiesBean.setPath(PropertiesReader.getValue("path"));
            this.propertiesBean.setContentEncoding(PropertiesReader.getValue("contentEncoding"));
            this.propertiesBean.setParameters(PropertiesReader.getValue("parameters"));
            this.propertiesBean.setComments(PropertiesReader.getValue("comments"));
            this.propertiesBean.setHeaders(PropertiesReader.getValue("headers"));
            this.propertiesBean.setCookies(PropertiesReader.getValue("cookies"));
            this.propertiesBean.setUsername(PropertiesReader.getValue("username"));
            this.propertiesBean.setPassword(PropertiesReader.getValue("password"));
            this.propertiesBean.setPassword(PropertiesReader.getValue("a\na"));
        }
    }
}