/* Copyright 2018, Oracle and/or its affiliates. All rights reserved.

The Universal Permissive License (UPL), Version 1.0
*/
package com.oracle.ateam.asset.hcmjavadataload.client;


import java.io.InputStream;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * HcmImportProperties file
 */
public class HcmImportProperties {
    private static Properties props = null;
    private static final Logger LOGGER = Logger.getLogger(HcmImportProperties.class.getName());
    public static final String FUSION_USERNAME = "FUSION_USERNAME";
    public static final String DATAPATH = "DATAPATH";
    public static final String DATA_SEPERATOR = "DATA_SEPERATOR";
    public static final String KEYSTORE_LOCATION = "KEYSTORE_LOCATION";
    public static final String HCM_DATA_LOADER_SERVICE_WSDL_LOCATION = "HCM_DATA_LOADER_SERVICE_WSDL_LOCATION";
    public static final String GETDATASETSTATUS_DELAY = "GETDATASETSTATUS_DELAY";
    public static final String UCM_IDC_WEBSERVICE_LOCATION = "UCM_IDC_WEBSERVICE_LOCATION";
    public static final String CSV_FILE_ENCODING = "CSV_FILE_ENCODING";
    public HcmImportProperties() {
        LOGGER.setLevel(Level.ALL);
    }
    public static void init() {
        props = new Properties();
        InputStream propFile = HcmImportProperties.class.getClassLoader().getResourceAsStream("hcmimport.properties");
        if (propFile == null) {
            // Terminate now
            System.exit(-1);
        }
        try {
            props.load(propFile);
            propFile.close();
            LOGGER.info("Properties file read");
            props.list(System.out);
        } catch (Exception e) {
            LOGGER.severe("Error: unable to read hcmimport.properties file, " + e.getLocalizedMessage());
            System.exit(-1);
        }
    }

    public String getProperty(String propName) {
        if (propName == null || propName.isEmpty()) {
            return "";
        }
        if (props != null) {
            String propValue = props.getProperty(propName);
            if (propValue == null || propValue.equals("")) {
                LOGGER.severe("Search for Property " + propName + " yielded no value");
                return "";
            }
            return propValue;
        }
        init();
        return props.getProperty(propName);
    }
}
