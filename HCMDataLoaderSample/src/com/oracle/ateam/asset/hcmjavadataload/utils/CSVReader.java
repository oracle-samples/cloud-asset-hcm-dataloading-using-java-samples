/* Copyright 2018, Oracle and/or its affiliates. All rights reserved.

The Universal Permissive License (UPL), Version 1.0
*/
package com.oracle.ateam.asset.hcmjavadataload.utils;


import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class CSVReader {
    private static final char DEFAULT_SEPARATOR = ',';
    private static final char DEFAULT_QUOTE = '"';
    private char sep = DEFAULT_SEPARATOR;
    private char quote = DEFAULT_QUOTE;

    public List<List<String>> readCsvFile(String fileName, String encoding) throws IOException {
        Scanner scanner = null;
        List<List<String>> ret = null;
        try {
            ret = new ArrayList<List<String>>();
            scanner = new Scanner(new File(fileName), encoding);
            while (scanner.hasNext()) {
                List<String> line = parseLine(scanner.nextLine());
                ret.add(line);
            }
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        return ret;
    }

    private List<String> parseLine(String cvsLine) {
        List<String> result = new ArrayList<String>();
        //if empty, return!
        if (cvsLine == null || cvsLine.isEmpty()) {
            return result;
        }
        StringBuilder curVal = new StringBuilder();
        boolean inQuotes = false;
        boolean startCollectChar = false;
        boolean doubleQuotesInColumn = false;
        char[] chars = cvsLine.toCharArray();
        for (char ch : chars) {
            if (inQuotes) {
                startCollectChar = true;
                if (ch == quote) {
                    inQuotes = false;
                    doubleQuotesInColumn = false;
                } else {
                    //Fixed : allow "" in custom quote enclosed
                    if (ch == '\"') {
                        if (!doubleQuotesInColumn) {
                            curVal.append(ch);
                            doubleQuotesInColumn = true;
                        }
                    } else {
                        curVal.append(ch);
                    }
                }
            } else {
                if (ch == quote) {
                    inQuotes = true;
                    //Fixed : allow "" in empty quote enclosed
                    if (chars[0] != '"' && quote == '\"') {
                        curVal.append('"');
                    }
                    //double quotes in column will hit this!
                    if (startCollectChar) {
                        curVal.append('"');
                    }
                } else if (ch == sep) {
                    result.add(curVal.toString());
                    curVal = new StringBuilder();
                    startCollectChar = false;
                } else if (ch == '\r') {
                    //ignore LF characters
                    continue;
                } else if (ch == '\n') {
                    //the end, break!
                    break;
                } else {
                    curVal.append(ch);
                }
            }
        }
        result.add(curVal.toString());
        return result;
    }


    public void setSeparator(char separator) {
        this.sep = separator;
    }

    public void setQuote(char quote) {
        this.quote = quote;
    }
}
