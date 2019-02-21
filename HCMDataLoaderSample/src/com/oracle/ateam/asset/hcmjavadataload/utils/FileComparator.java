/* Copyright 2018, Oracle and/or its affiliates. All rights reserved.

The Universal Permissive License (UPL), Version 1.0
*/
package com.oracle.ateam.asset.hcmjavadataload.utils;

import java.io.File;

import java.util.Comparator;
import java.util.Date;
import java.util.TimeZone;


/**
 * This class can be used to sort a list of Files by their file name, for
 * example using the a naming convention: </br>
 * "prefix" + date + "suffix"</br>
 * Either the prefix OR the suffix must be set and must be a "FIXED" value. The
 * naming convention must include:
 * <li>a valid date pattern (by default it is "yyyyMMdd")
 * <li>a fixed String to be searched before or after the date
 * <li>(implementation of regular expression to match flexile prefix and suffix
 * was getting to complicated) </br>
 * In case the prefix or suffix are not found and/or the date can not be parsed
 * for any of the filename, the comparator will trow an error
 *
 * By default it sorts in descending order (i.e. the oldest file is the first
 * one in the list)</br>
 * To sort in Ascending order, you need to use the setSortDesc method and set it
 * to 'false'
 *
 *
 */

public class FileComparator implements Comparator<File> {

    @Override
    public int compare(File f1, File f2) {
        return compareFileNames(f1.getName(), f2.getName());
    }

    private static int compareFileNames(String fileName1, String fileName2) {
        // try to get the date from the fileName:
        Date date1 = getDateFromUnixTimeStamp(fileName1);
        Date date2 = getDateFromUnixTimeStamp(fileName2);
        if (date1.before(date2)) {
            // for descending sorting - return -1 - for ascending +1
            return -1;
        } else if (date2.before(date1)) {
            // for descending sorting - return +1 - for ascending -1
            return +1;
        } else {
            // Some customers need to allow multiple files with the same date - in that case we do
            // not need to sort
            throw new ClassCastException("Can not sort the two files " + fileName1 + " and " + fileName2 +
                                         " by date, as they start with the same date");
        }
    }

    private static Date getDateFromUnixTimeStamp(String fileName) {
        try {
            String unixEpochTimes = fileName.substring(0, 10);
            TimeZone.setDefault(TimeZone.getTimeZone("CET"));
            return new Date((Long.parseLong(unixEpochTimes) * 1000));
        } catch (Exception e) {
            throw new ClassCastException("File " + fileName + " does not follow the  naming convention (\n" +
                                         "(The first 10 characters of the file are written with 'Unix Epoch Time'))");
        }
    }
}
