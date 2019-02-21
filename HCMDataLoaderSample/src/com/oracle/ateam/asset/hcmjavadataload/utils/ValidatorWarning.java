/* Copyright 2018, Oracle and/or its affiliates. All rights reserved.

The Universal Permissive License (UPL), Version 1.0
*/
package com.oracle.ateam.asset.hcmjavadataload.utils;

public class ValidatorWarning extends ValidatorException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public ValidatorWarning() {
    }

    public ValidatorWarning(String msg) {
        super(msg);
    }

    public ValidatorWarning(Throwable cause) {
        super(cause);
    }

    public ValidatorWarning(String msg, Throwable cause) {
        super(msg, cause);
    }

    public ValidatorWarning(String msg, String elementCausingError) {
        super(msg, elementCausingError);
    }

    public ValidatorWarning(String msg, String elementCausingError, Throwable cause) {
        super(msg, elementCausingError, cause);
    }
}
