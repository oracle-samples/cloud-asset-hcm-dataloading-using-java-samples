/* Copyright 2018, Oracle and/or its affiliates. All rights reserved.

The Universal Permissive License (UPL), Version 1.0
*/
package com.oracle.ateam.asset.hcmjavadataload.client.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to store one Row of the DATA for the HDL User.dat file to update the username:
 *
 * Example:
 * METADATA|User|UserId|PersonNumber|Username
 * MERGE|User||300000003613029|test.user
 * MERGE|User||300000003613030|john.gilmore

 *
 */
public class UserDataRow {
    private String personNumber;
    private String userName;
    private String lastUserNameInHCM;
    private String result;
    public List<String> getRow() {
        List<String> row = new ArrayList<String>();
        row.add("MERGE");
        row.add("User");
        row.add("");
        row.add(this.personNumber);
        row.add(this.userName);
        return row;
    }

    public String getPersonNumber() {
        return this.personNumber;
    }

    public void setPersonNumber(String personNumber) {
        this.personNumber = personNumber;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "UserDataRow [personNumber=" + this.personNumber + ", this._userName=" + this.userName +
               ", this._last_UserName_In_HCM=" + this.lastUserNameInHCM + "]";
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return this.result;
    }

    public String getLastUserNameInHCM() {
        return this.lastUserNameInHCM;
    }

    /**
     * @param last_UserName_In_HCM
     */
    public void setLastUserNameInHCM(String lastUserNameInHCM) {
        this.lastUserNameInHCM = lastUserNameInHCM;
    }
}
