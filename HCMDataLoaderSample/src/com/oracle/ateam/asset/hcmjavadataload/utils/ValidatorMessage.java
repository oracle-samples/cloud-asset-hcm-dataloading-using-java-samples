/* Copyright 2018, Oracle and/or its affiliates. All rights reserved.

The Universal Permissive License (UPL), Version 1.0
*/
package com.oracle.ateam.asset.hcmjavadataload.utils;

/**
 * This class can be used to store the elements of a Validator Message
 *
 */
public class ValidatorMessage {
    private String dynaCode;
    private String message;
    /**
     * Default constructor - please note that the dynaCode is limited to 30 characters
     * @param dynaCode
     * @param message
     */
    public ValidatorMessage(String dynaCode, String message) {
        super();
        this.dynaCode = dynaCode;
        this.message = message;
    }

    public String getDynaCode() {
        return this.dynaCode;
    }

    public void setDynaCode(String dynaCode) {
        this.dynaCode = dynaCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toString() {
        return "ValidatorMessage [_dynaCode=" + dynaCode + ", _message=" + message + "]";
    }
}
