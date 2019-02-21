/* Copyright 2018, Oracle and/or its affiliates. All rights reserved.

The Universal Permissive License (UPL), Version 1.0
*/
package com.oracle.ateam.asset.hcmjavadataload.client.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class is used to create the HDL User.dat file to update the username:
 *
 * Example:
 * METADATA|User|UserId|PersonNumber|Username
 * MERGE|User||300000003613029|test.user
 * MERGE|User||300000003613030|john.gilmore

 *
 */
public class UserFile {
    /**
     * The Filename of this file must always be 'User.dat'
     * **/
    private static final String FILE_NAME = "User.dat";
    private static final String[] HEADER = { "METADATA", "User", "UserId", "PersonNumber", "Username" };
    private List<List<String>> dataRows = new ArrayList<List<String>>();
    public List<String> getHeader() {
        return Arrays.asList(HEADER);
    }

    public String getFileName() {
        return FILE_NAME;
    }

    public void addDataRow(List<String> dataRow) {
        dataRows.add(dataRow);
    }

    public List<List<String>> getDataRows() {
        return dataRows;
    }
}
