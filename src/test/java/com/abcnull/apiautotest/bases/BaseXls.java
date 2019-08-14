package com.abcnull.apiautotest.bases;

import com.abcnull.apiautotest.beans.XlsBean;
import com.abcnull.apiautotest.constants.BaseConstant;
import com.abcnull.apiautotest.utils.XlsReader;
import lombok.Data;

import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * BaseXls
 * This Class using util(XlsReader) is called by BaseTest Class mainly to get data of requests info excel
 *
 * @author abcnull
 * @version 1.0.0
 * @date 2019/8/5
 */
@Data
public class BaseXls {
    /**
     * ExcelName
     */
    private String excelName;

    /**
     * ExcelPath
     */
    private String excelPath;

    /**
     * SheetName
     */
    private String sheetName;

    /**
     * XlsBean
     */
    private XlsBean xlsBean;

    /**
     * XlsMap
     * ("sheet1", xlsBeanList)
     */
    private Map<String, List<XlsBean>> xlsMap;

    /**
     * BaseXls constructor with one param
     *
     * @param excelName the name of the Excel storing requests info divided by sheetName
     */
    public BaseXls(String excelName) throws Exception {
        init(excelName);
    }

    /**
     * BaseXls constructor with two params
     *
     * @param excelName the name of the Excel made up of sheets
     * @param sheetName the name of the sheet made up of HTTP Requests data
     */
    public BaseXls(String excelName, String sheetName) throws Exception {
        init(excelName, sheetName);
    }

    /**
     * Init BaseXls fields
     *
     * @param excelName the name of specified excel
     * @throws Exception trigger when XlsReader read
     */
    private void init(String excelName) throws Exception {
        // the name of excel
        this.excelName = excelName;
        // the path of the file named excelName
        this.excelPath = Paths.get(System.getProperty(BaseConstant.CURRENTPATH), excelName).toString();
        // the name of sheet in excel
        this.sheetName = null;
        // the xlsBean of xlsList in xlsMap
        this.xlsBean = null;
        /* ========== read and get values of xls need to be in a synchronized code block ========== */
        synchronized (this){
            // save xls data in an util of XlsReader
            XlsReader.readXls(excelPath);
            /* save the values of xls name excelName below in a xls bean */
            this.xlsMap = XlsReader.getValue();
        }
    }

    /**
     * Init BaseXls with specified sheet
     *
     * @param excelName the name of specified excel
     * @param sheetName the name of specified sheet
     * @throws Exception trigger when XlsReader read
     */
    private void init(String excelName, String sheetName) throws Exception {
        // the name of excel
        this.excelName = excelName;
        // the path of the file named excelName
        this.excelPath = Paths.get("src/test/resources/cases", excelName).toString();
        // the name of sheet in excel
        this.sheetName = sheetName;
        // the xlsBean of xlsList in xlsMap
        this.xlsBean = null;
        /* ========== read and get values of xls need to be in a synchronized code block ========== */
        synchronized (this){
            // save xls data in an util of XlsReader
            XlsReader.readXls(excelPath, sheetName);
            /* save the values of xls name excelName below in a xls bean */
            this.xlsMap = XlsReader.getValue();
        }
    }
}