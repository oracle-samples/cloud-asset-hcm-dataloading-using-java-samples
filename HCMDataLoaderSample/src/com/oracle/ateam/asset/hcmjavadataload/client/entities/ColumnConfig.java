/* Copyright 2018, Oracle and/or its affiliates. All rights reserved.

The Universal Permissive License (UPL), Version 1.0
*/

package com.oracle.ateam.asset.hcmjavadataload.client.entities;

/**
 * This class describes the configuration of the columns in the incoming or outgoing csv files of an integration
 */

public class ColumnConfig {
    // This field stores the place of the column in the file
    private int columnNo;
    // This field stores the external name of the column (=the header)
    private String name;
    // This field stores the if the data in this column is required or not
    private boolean required;
    // This optional field stores the maximum length of the data in this column. If not set it defaults to '0'
    private int maxLength = 0;

    public ColumnConfig(int columnNo, String name, boolean required) {
        this.columnNo = columnNo;
        this.name = name;
        this.required = required;
    }

    public ColumnConfig(int columnNo, String name, boolean required, int maxLength) {
        this.columnNo = columnNo;
        this.name = name;
        this.required = required;
        this.maxLength = maxLength;
    }

    /**
     * get the place of the column in the file
     * */
    public int getColumnNo() {
        return columnNo;
    }

    /**
     * get the external name of the column (=the header)
     * */
    public String getName() {
        return name;
    }

    /**
     * get if the data in this column is required or not
     * */
    public boolean isRequired() {
        return required;
    }

    /**
     * get the optional maximum length of the data in this column. If not set it defaults to '0'
     * */
    public int getMaxLength() {
        return maxLength;
    }

    @Override
    public String toString() {
        return "ColumnConfig [_columnNo=" + columnNo + ", _name=" + name + ", _required=" + required + ", _maxLength=" +
               maxLength + "]";
    }
}
