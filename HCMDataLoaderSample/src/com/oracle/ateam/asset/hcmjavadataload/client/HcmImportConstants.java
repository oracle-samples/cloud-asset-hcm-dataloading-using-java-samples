/* Copyright 2018, Oracle and/or its affiliates. All rights reserved.

The Universal Permissive License (UPL), Version 1.0
*/
package com.oracle.ateam.asset.hcmjavadataload.client;

import com.oracle.ateam.asset.hcmjavadataload.client.entities.ColumnConfig;

/**
 * Class to store constants, which are used for the HCMImport  integration
 */
public class HcmImportConstants {
    /**
     * In and out folders
     * */
    public static final String INFOLDER = "in";
    public static final String OUTFOLDER = "out";
    public static final String NL = "\r\n";
    public static final String ENCODING = "UTF-8";
    public static final char SEPARATOR = '|';
    /**
     * The date format used by the HDL for example 2012/12/01
     **/
    public static final String DATE_FORMAT_HDL = "yyyy/MM/dd";
    /**
     * The date format used by the HCM Extract for example 2016-02-22
     **/
    public static final String DATE_FORMAT_EXCEL_EXTRACT = "dd-MM-yyyy";
    /**
     * The first part of the doc name of the file uploaded to
     **/
    public static final String UCM_DOCNAME = "UserWorker";
    /**
     * The date format used by the HCM Extract for example 2016-02-22
     **/
    public static final String DATE_FORMAT_UCM = "yyyyMMdd-HHmmss";
    public static final String SOURCE_KEY = "Test_SourceKey";
    public static final String LEGISLATION_CODE = "Test_LegislationCode";
    /**
     * Describe the main fields of the Employee Information Sheet in the Excel file
     */
    public static final ColumnConfig PERSON_NUMBER = new ColumnConfig(0, "PersonNumber", false);
    public static final ColumnConfig TITLE = new ColumnConfig(1, "Title ", false);
    public static final ColumnConfig FIRST_NAME = new ColumnConfig(2, "First Name ", false);
    public static final ColumnConfig LAST_NAME = new ColumnConfig(3, "Last Name ", false);
    public static final ColumnConfig DATE_OF_BIRTH = new ColumnConfig(4, "Date of birth ", false);
    public static final ColumnConfig MARITAL_STATUS = new ColumnConfig(5, "Marital Status ", false);
    public static final ColumnConfig EMAIL_ADDRESS = new ColumnConfig(6, "Email address ", false);
    public static final ColumnConfig USERNAME = new ColumnConfig(7, " UserName ", false);
    public static final ColumnConfig LEGAL_EMPLOYER_HIRE_DATE = new ColumnConfig(8, " HireDate ", false);
    public static final int LAST_COLUMN = LEGAL_EMPLOYER_HIRE_DATE.getColumnNo();
    /** The HDL Error Results File:
     * 0	P_CONTENT_ID
     * 1	P_BUSINESS_OBJECT
     * 2	P_MESSAGE_TEXT
     * 3	P_ENABLE_TRACE
     * 4	ORDERBY
     * 5	ERR_LOCATION
     * 6	MESSAGE_TYPE
     * 7	MSG_TEXT
     * 8	DATA_SET_NAME
     * 9	STACK_TRACE
     * 10	UI_USER_KEY
     * 11	BUSINESSOBJECT
     * 12	SOURCESYSTEMID
     * 13	FILE_LINE
     * 14	DATA_FILE_NAME
     * 15	UCM_CONTENT_ID
     * */
    public static final int HDL_ERROR_P_CONTENT_ID = 0;
    public static final int HDL_ERROR_P_BUSINESS_OBJECT = 1;
    public static final int HDL_ERROR_P_MESSAGE_TEXT = 2;
    public static final int HDL_ERROR_P_ENABLE_TRACE = 3;
    public static final int HDL_ERROR_ORDERBY = 4;
    public static final int HDL_ERROR_ERR_LOCATION = 5;
    public static final int HDL_ERROR_MESSAGE_TYPE = 6;
    public static final int HDL_ERROR_MSG_TEXT = 7;
    public static final int HDL_ERROR_DATA_SET_NAME = 8;
    public static final int HDL_ERROR_STACK_TRACE = 9;
    public static final int HDL_ERROR_UI_USER_KEY = 10;
    public static final int HDL_ERROR_BUSINESSOBJECT = 11;
    public static final int HDL_ERROR_SOURCESYSTEMID = 12;
    public static final int HDL_ERROR_FILE_LINE = 13;
    public static final int HDL_ERROR_DATA_FILE_NAME = 14;
    public static final int HDL_ERROR_UCM_CONTENT_ID = 15;
    public static final ColumnConfig ERR_LOCATION = new ColumnConfig(6, "ERR_LOCATION", false);
    public static final ColumnConfig MESSAGE_TYPE = new ColumnConfig(6, "MESSAGE_TYPE", false);
    public static final ColumnConfig MSG_TEXT = new ColumnConfig(7, "MSG_TEXT", false);
    public static final ColumnConfig BUSINESSOBJECT = new ColumnConfig(11, "BUSINESSOBJECT", false);
    public static final ColumnConfig FILE_LINE = new ColumnConfig(13, "FILE_LINE", false);
    public static final ColumnConfig DATA_FILE_NAME = new ColumnConfig(14, "DATA_FILE_NAME", false);
    /**
     * This constant is used for the result reporting in case of OK
     */
    public static final String OK = "OK";
    /**
     * This constant is used for the result reporting in case of Error
     */
    public static final String ERROR = "Error";
    /**
     * This constant is used for the result reporting in case of Warning
     */
    public static final String WARNING = "Warning";
    
    
    // Default constants for worker structure in WorkerDataRow
    public static String DEFAULT_LEGAL_ENTITY="US1 Legal Entity";
    public static String DEFAULT_BUSINESS_UNIT="US1 Business Unit";    
    private HcmImportConstants() {
        // block intentionally left blank!
    }
}

