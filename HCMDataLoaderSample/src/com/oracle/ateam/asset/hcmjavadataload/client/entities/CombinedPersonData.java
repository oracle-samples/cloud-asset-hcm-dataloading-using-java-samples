/* Copyright 2018, Oracle and/or its affiliates. All rights reserved.

The Universal Permissive License (UPL), Version 1.0
*/
package com.oracle.ateam.asset.hcmjavadataload.client.entities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class is used to store the combined data of all Rows for one Person,
 * including the data from  and the information required for the Worker.dat and User.dat

 *
 */
public class CombinedPersonData {
    private String personNumber;
    private List<List<String>> exportDataRows = new ArrayList<List<String>>();
    private WorkerDataRow workerDataRow;
    private UserDataRow userDataRow;
    private List<String> errors;
    public String getPersonNumber() {
        return personNumber;
    }

    public void setPersonNumber(String pPersonNumber) {
        this.personNumber = pPersonNumber;
    }

    public WorkerDataRow getWorkerDataRow() {
        return this.workerDataRow;
    }

    public void setWorkerDataRow(WorkerDataRow workerDataRow) {
        this.workerDataRow = workerDataRow;
    }

    public UserDataRow getUserDataRow() {
        return this.userDataRow;
    }

    public void setUserDataRow(UserDataRow userDataRow) {
        this.userDataRow = userDataRow;
    }

    public List<List<String>> getExportDataRows() {
        return this.exportDataRows;
    }

    public void setExportDataRows(List<List<String>> exportDataRows) {
        this.exportDataRows = exportDataRows;
    }

    public void addExportDataRow(List<String> exportDataRow) {
        this.exportDataRows.add(exportDataRow);
    }

    @Override
    public String toString() {
        return "CombinedPersonData [personNumber=" + personNumber + ", _WorkerDataRow=" + workerDataRow +
               ", _UserDataRow=" + userDataRow + "]";
    }

    /**
     * 	private static final String[] header =  {
		"PersonNumber",
		"Username",
		"Email",
		"Result",
		"Error Messages Username",
		"Error Messages Worker(Email/Phone)"
	};
     * @return
     */
    public List<String> getCombinedResultList() {
        List<String> result = new ArrayList<String>();
        result.add(personNumber);
        result.add(getEmail());
        result.add(getCombinedResult());
        result.add("");
        result.add(getErrorMessages());
        return result;
    }

    private String getEmail() {
        if (workerDataRow != null) {
            return workerDataRow.getEmailAddress();
        } else {
            return "";
        }
    }

    private String getErrorMessages() {
        if (errors == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Iterator iterator = errors.iterator(); iterator.hasNext();) {
            String string = (String) iterator.next();
            sb.append(string + "\r\n");
        }
        return sb.toString();
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;

    }

    public String getCombinedResult() {
        StringBuilder combinedResult = new StringBuilder();
        // in case of first level validation:
        if (userDataRow != null && userDataRow.getResult() != null &&
            (!userDataRow.getResult().equalsIgnoreCase("OK"))) {
            combinedResult.append("Error found in UserName Data: " + userDataRow.getResult() + "\r\n");
        }
        //in case of first level validation:
        if (workerDataRow != null && workerDataRow.getResult() != null &&
            (!workerDataRow.getResult().equalsIgnoreCase("OK"))) {
            combinedResult.append("Error found in Email/Phone Data: " + workerDataRow.getResult() + "\r\n");
        }
        if (errors != null && !errors.isEmpty()) {
            combinedResult.append("Error(s) found from HDL process: ");
            for (Iterator iterator = errors.iterator(); iterator.hasNext();) {
                String string = (String) iterator.next();
                combinedResult.append(string + "\r\n");
            }
        }
        if (combinedResult.length() == 0) {
            combinedResult.append("OK");
        }
        return combinedResult.toString();
    }
}
