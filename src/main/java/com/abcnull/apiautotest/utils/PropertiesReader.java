package com.abcnull.apiautotest.utils;

import com.abcnull.apiautotest.constants.BaseConstant;

import java.io.*;
import java.util.Properties;

/**
 * Properties files reader
 * The static util is used to read properties files and save data in an object of Properties
 *
 * @author abcnull
 * @version 1.0.0
 * @date 2019/8/2
 */
public class PropertiesReader {
    /**
     * Properties type
     */
    private static Properties properties = new Properties();

    /**
     * Read properties by path and load into an object of Properties
     *
     * @param propertiesPath the path of properties
     * @return an object of Properties which stores properties file data
     * @throws IOException throws at InputStream and InputStreamReader
     */
    synchronized public static Properties readProperties(String propertiesPath) throws IOException {
        // byte input stream
        InputStream inputStream = new FileInputStream(propertiesPath);
        // character input stream
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, BaseConstant.DEFAULT_CONTENTENCODING);
        // character buffered input stream
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        // load Properties
        properties.load(bufferedReader);
        return properties;
    }

    /**
     * Get value by key of properties
     *
     * @param key key in properties
     * @return the value of key
     */
    public static String getValue(String key) {
        return properties.getProperty(key);
    }
}