package com.abcnull.apiautotest.utils;

import com.abcnull.apiautotest.beans.XlsBean;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Excels reader
 * The static util is used to read excels using java POI and save data in a LinkedHashMap
 *
 * @author abcnull
 * @version 1.0.0
 * @date 2019/8/5
 */
public class XlsReader {
    /**
     * XlsMap
     */
    private static Map<String, List<XlsBean>> xlsMap = new LinkedHashMap<>();

    /**
     * Read xls and save specified sheet data in a LinkedHashMap using POI and java reflection
     * row ==> XlsBean
     * sheet ==> ArrayList
     * xls ==> LinkedHashMap
     *
     * @param xlsPath the path of xls
     * @param sheetName the name of sheet
     * @return an object of LinkedHashMap saving xls data according to specified sheet
     * @throws Exception IOException IntrospectionException InvocationTargetException IllegalAccessException
     */
    synchronized public static Map<String, List<XlsBean>> readXls(String xlsPath, String sheetName) throws Exception {
        // create xlsFile
        File xlsFile = new File(xlsPath);
        // create workbook according to xlsFile
        Workbook workbook = WorkbookFactory.create(xlsFile);
        // create sheet by workbook
        Sheet sheet = workbook.getSheet(sheetName);
        // an ArrayList used for saving all rows data
        List<XlsBean> xlsList = new ArrayList<>();
        /* ========== Traverse every rows of specified sheet of Excel and add it to xlsList ========== */
        for(int i = 0; i < sheet.getPhysicalNumberOfRows(); i++){
            // a row
            Row row = sheet.getRow(i);
            // convert a XlsBean to an Object
            Object xlsObject = new XlsBean();
            // get all fields of XlsBean Class and save them into Field[]
            Field[] fields = XlsBean.class.getDeclaredFields();
            /* ========== Traverse every columns of specified rows of sheet of Excel and add it to XlsBean ========== */
            for(int j = 0; j < row.getPhysicalNumberOfCells(); j++){
                /* ========== Assign values of every column of specified rows to xlsObject through java reflection ========== */
                // get single Field by Field[] index
                Field field = fields[j];
                // get descriptor of specified Field
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), XlsBean.class);
                // get Method through descriptor above
                Method method = propertyDescriptor.getWriteMethod();
                // assign current column data to current Set method of current xlsObject
                method.invoke(xlsObject, row.getCell(j).toString());
            }
            // save current row data correspond to xlsBean in xlsList
            xlsList.add((XlsBean) xlsObject);
        }
        // save current sheet data correspond to xlsList in xlsMap
        xlsMap.put(sheetName, xlsList);
        return xlsMap;
    }

    /**
     * Read xls and save all data in a LinkedHashMap using POI and java reflection
     * row ==> XlsBean
     * sheet ==> ArrayList
     * xls ==> LinkedHashMap
     *
     * @param xlsPath the path of xls
     * @return an Object of LinkedHashMap saving xls data
     * @throws Exception IOException IntrospectionException InvocationTargetException IllegalAccessException
     */
    synchronized public static Map<String, List<XlsBean>> readXls(String xlsPath) throws Exception {
        // create xlsFile
        File xlsFile = new File(xlsPath);
        // create workbook according to xlsFile
        Workbook workbook = WorkbookFactory.create(xlsFile);
        /* ========== Traverse every sheet of specified Excel and add it to key of xlsMap ========== */
        for(int i = 0; i < workbook.getNumberOfSheets(); i++) {
            // create sheet by workbook
            Sheet sheet = workbook.getSheetAt(i);
            // get current sheet name
            String sheetName = sheet.getSheetName();
            /* ========== Call overloaded function ========== */
            readXls(xlsPath, sheetName);
        }
        return xlsMap;
    }

    /**
     * Get map value of excel by excelPath and sheetName
     *
     * @return xlsMap
     */
    public static Map<String, List<XlsBean>> getValue(){
        return xlsMap;
    }
}