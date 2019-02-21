/* Copyright 2018, Oracle and/or its affiliates. All rights reserved.

The Universal Permissive License (UPL), Version 1.0
*/
package com.oracle.ateam.asset.hcmjavadataload.utils;

import java.util.Map;

/**
 * Title:        Integration Utilities
 * Description:  This Exception is used when the Validator Error/Warning or Message needs to be managed
 * exception
 *
 * This Exception is thrown from the Validator
 *
 */
public class ValidatorException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String elementCausingError;
    private Integer columnCausingError;
    private String dynaKey;
    private Map keyValuePair;
    private String idCausingError;

    public ValidatorException() {
        super();
    }

    public ValidatorException(String msg) {
        super(msg);
    }

    public ValidatorException(Throwable cause) {
        super(cause);
    }

    public ValidatorException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public ValidatorException(String msg, String elementCausingError) {
        super(msg);
        this.elementCausingError = elementCausingError;
    }

    public ValidatorException(String msg, String elementCausingError,
                              Throwable cause) {
        super(msg, cause);
        this.elementCausingError = elementCausingError;
    }

    public ValidatorException(String msg, String elementCausingError,
                              String dynaKey) {
        super(msg);
        this.elementCausingError = elementCausingError;
        this.dynaKey = dynaKey;
    }

    public ValidatorException(String msg, String elementCausingError,
                              String dynaKey, Throwable cause) {
        super(msg, cause);
        this.elementCausingError = elementCausingError;
        this.dynaKey = dynaKey;
    }


    public ValidatorException(ValidatorMessage message,
                              String elementNameCausingError) {
        super(message.getMessage());
        this.elementCausingError = elementNameCausingError;
        this.dynaKey = message.getDynaCode();
    }

    public ValidatorException(ValidatorMessage message,
                              String elementNameCausingError,
                              Throwable cause) {
        super(message.getMessage(), cause);
        this.elementCausingError = elementNameCausingError;
        this.dynaKey = message.getDynaCode();
    }


    public ValidatorException(ValidatorMessage message,
                              String elementNameCausingError,
                              Integer columnCausingError) {
        super(message.getMessage());
        this.elementCausingError = elementNameCausingError;
        this.dynaKey = message.getDynaCode();
        this.columnCausingError = columnCausingError;
    }

    public ValidatorException(ValidatorMessage message,
                              String elementNameCausingError,
                              int columnCausingError) {
        super(message.getMessage());
        this.elementCausingError = elementNameCausingError;
        this.dynaKey = message.getDynaCode();
        this.columnCausingError = columnCausingError;
    }

    public ValidatorException(ValidatorMessage message,
                              String elementNameCausingError,
                              Integer columnCausingError, Throwable cause) {
        super(message.getMessage(), cause);
        this.elementCausingError = elementNameCausingError;
        this.dynaKey = message.getDynaCode();
        this.columnCausingError = columnCausingError;
    }


    public void setElementCausingError(String elementCausingError) {
        this.elementCausingError = elementCausingError;
    }

    public String getElementCausingError() {
        return this.elementCausingError;
    }

    /**
     * This method can be used to set the dynaKey (='TEXT_KEY') of the error message used for the lookup in the config_integration_messages
     * (NOTE that the max length of the dynaKey is limited to 30 characters)
     * @param dynaKey
     */
    public void setDynaKey(String dynaKey) {
        this.dynaKey = dynaKey;
    }

    public String getDynaKey() {
        return this.dynaKey;
    }

    public Map getKeyValuePair() {
        return keyValuePair;
    }

    public void setKeyValuePair(Map keyValuePair) {
        this.keyValuePair = keyValuePair;
    }

    public String getIdCausingError() {
        return idCausingError;
    }

    public void setIdCausingError(String idCausingError) {
        this.idCausingError = idCausingError;
    }

    /**
     * This method set optionally the column causing the error
     * @param columnCausingError
     */
    public void setColumnCausingError(Integer columnCausingError) {
        this.columnCausingError = columnCausingError;
    }

    /**
     * This method returns the (optional) column number of the element causing the error.
     * If not set, it returns 'null'
     *
     * @return
     */
    public Integer getColumnCausingError() {
        return columnCausingError;
    }
}
