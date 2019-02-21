/* Copyright 2018, Oracle and/or its affiliates. All rights reserved.

The Universal Permissive License (UPL), Version 1.0
*/
package com.oracle.ateam.asset.hcmjavadataload.client.entities;

import java.util.Arrays;
import java.util.List;

public class HcmImportResultFile {
    private static final String[] headerPerson = {
        "EM-ID", "Result", "Error Messages Username", "Error Messages Worker(Email/Phone)" };

    public static List<String> getHeader() {
        return Arrays.asList(headerPerson);
    }
    private HcmImportResultFile() {
        // block intentionally left blank!
    }
}
