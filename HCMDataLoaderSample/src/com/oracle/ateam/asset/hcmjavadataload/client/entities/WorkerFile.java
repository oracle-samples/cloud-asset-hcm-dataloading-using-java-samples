/* Copyright 2018, Oracle and/or its affiliates. All rights reserved.

The Universal Permissive License (UPL), Version 1.0
*/
package com.oracle.ateam.asset.hcmjavadataload.client.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class is used to create the HDL Worker.dat file
 *
 */
public class WorkerFile {

    /**
     * The Filename of this file must always be 'Worker.dat'
     **/
    public static final String FILE_NAME = "Worker.dat";
    private static final String[] headerWorker = {
        "METADATA", "Worker", "EffectiveStartDate", "EffectiveEndDate", "StartDate", "DateOfBirth", "CategoryCode",
        "ActionCode", "CorrespondenceLanguage", "SourceSystemOwner", "SourceSystemId"
    };
    private static final String[] headerPersonName = {
        "METADATA", "PersonName", "EffectiveStartDate", "EffectiveEndDate", "PersonId(SourceSystemId)",
        "LegislationCode", "NameType", "FirstName", "LastName", "Title", "KnownAs", "NameInformation29",
        "NameInformation7", "NameInformation30", "NameInformation8", "SourceSystemOwner", "SourceSystemId"
    };
    private static final String[] headerPersonLegislativeData = {
        "METADATA", "PersonLegislativeData", "EffectiveStartDate", "EffectiveEndDate", "PersonId(SourceSystemId)",
        "LegislationCode", "MaritalStatus", "Sex", "SourceSystemOwner", "SourceSystemId"
    };
    private static final String[] headerWorkRelationship = {
        "METADATA", "WorkRelationship", "LegalEmployerSeniorityDate", "EnterpriseSeniorityDate", "DateStart",
        "OnMilitaryServiceFlag", "PersonId(SourceSystemId)", "PrimaryFlag", "LegalEmployerName", "WorkerType",
        "ActionCode", "ProjectedTerminationDate", "SourceSystemOwner", "SourceSystemId"
    };
    private static final String[] headerWorkTerms = {
        "METADATA", "WorkTerms", "EffectiveStartDate", "EffectiveEndDate", "EffectiveSequence", "EffectiveLatestChange",
        "PeriodOfServiceId(SourceSystemId)", "PersonId(SourceSystemId)", "LegalEmployerName", "DateStart",
        "AssignmentName", "AssignmentStatusTypeCode", "AssignmentType", "BusinessUnitShortCode", "PositionOverrideFlag",
        "PrimaryWorkTermsFlag", "ActionCode", "ReportingEstablishment", "ManagerFlag", "SourceSystemOwner",
        "SourceSystemId"
    };
    private static final String[] headerAssignment = {
        "METADATA", "Assignment", "ActionCode", "EffectiveStartDate", "EffectiveEndDate", "EffectiveSequence",
        "EffectiveLatestChange", "WorkTermsAssignmentId(SourceSystemd)", "AssignmentType", "AssignmentStatusTypeCode",
        "BusinessUnitShortCode", "AssignmentCategory", "HourlySalariedCode", "JobCode", "LocationCode", "NormalHours",
        "Frequency", "DepartmentName", "PeriodOfServiceId(SourceSystemId)", "PersonId(SourceSystemId)", "PrimaryFlag",
        "FLEX:PER_ASG_DF", "BusinessTitle(PER_ASG_DF=Global Data Elements)", "PermanentTemporary",
        "ReportingEstablishment", "DateStart", "PrimaryAssignmentFlag", "ManagerFlag", "FullPartTime",
        "SourceSystemOwner", "SourceSystemId"
    };
    private static final String[] headerContract = {
        "METADATA", "Contract", "AssignmentId(SourceSystemId)", "PersonId(SourceSystemId)", "EffectiveStartDate",
        "EffectiveEndDate", "SourceSystemOwner", "SourceSystemId"
    };
    private static final String[] headerPersonEmail = {
        "METADATA", "PersonEmail", "PersonId(SourceSystemId)", "EmailType", "EmailAddress", "DateFrom",
        "EmailAddressId", "PrimaryFlag", "SourceSystemOwner", "SourceSystemId"
    };
    private static final String[] headerAssignmentWorkMeasure = {
        "METADATA", "AssignmentWorkMeasure", "AssignmentId(SourceSystemId)", "EffectiveStartDate", "EffectiveEndDate",
        "ActionCode", "Unit", "Value", "SourceSystemOwner", "SourceSystemId"
    };
    private static final String[] headerPersonCitizenship = {
        "METADATA", "PersonCitizenship", "PersonId(SourceSystemId)", "DateFrom", "DateTo", "LegislationCode",
        "CitizenshipStatus", "SourceSystemOwner", "SourceSystemId"
    };
    private static final String[] headerPersonVisa = {
        "METADATA", "PersonVisa", "EffectiveStartDate", "EffectiveEndDate", "PersonId(SourceSystemId)",
        "VisaPermitType", "LegislationCode", "CurrentVisaPermit", "IssueDate", "SourceSystemOwner", "SourceSystemId"
    };
    private static final String[] headerAssignmentSupervisor = {
        "METADATA", "AssignmentSupervisor", "EffectiveStartDate", "EffectiveEndDate", "ManagerType",
        "PersonId(SourceSystemId)", "AssignmentNumber", "PrimaryFlag", "ManagerPersonNumber", "SourceSystemOwner",
        "SourceSystemId"
    };
    private static final String[] headerExternalIdentifier = {
        "METADATA", "ExternalIdentifier", "ExternalIdentifierSequence", "AssignmentNumber", "PersonId(SourceSystemId)",
        "PersonNumber", "ExternalIdentifierNumber", "ExternalIdentifierType", "DateFrom", "DateTo", "SourceSystemOwner",
        "SourceSystemId"
    };
    private List<List<String>> dataRowsWorker = new ArrayList<List<String>>();
    private List<List<String>> dataRowsPersonName = new ArrayList<List<String>>();
    private List<List<String>> dataRowsPersonLegislativeData = new ArrayList<List<String>>();
    private List<List<String>> dataRowsWorkRelationship = new ArrayList<List<String>>();
    private List<List<String>> dataRowsWorkTerms = new ArrayList<List<String>>();
    private List<List<String>> dataRowsAssignment = new ArrayList<List<String>>();
    private List<List<String>> dataRowsContract = new ArrayList<List<String>>();
    private List<List<String>> dataRowsPersonEmail = new ArrayList<List<String>>();
    private List<List<String>> dataRowsAssignmentWorkMeasure = new ArrayList<List<String>>();
    private List<List<String>> dataRowsPersonCitizenship = new ArrayList<List<String>>();
    private List<List<String>> dataRowsPersonVisa = new ArrayList<List<String>>();
    private List<List<String>> dataRowsAssignmentSupervisor = new ArrayList<List<String>>();
    private List<List<String>> dataRowsExternalIdentifier = new ArrayList<List<String>>();

    public String getFileName() {
        return FILE_NAME;
    }

    public List<String> getHeaderWorker() {
        return Arrays.asList(headerWorker);
    }

    public List<String> getHeaderPersonName() {
        return Arrays.asList(headerPersonName);
    }

    public List<String> getHeaderPersonLegislativeData() {
        return Arrays.asList(headerPersonLegislativeData);
    }

    public List<String> getHeaderWorkRelationship() {
        return Arrays.asList(headerWorkRelationship);
    }

    public List<String> getHeaderWorkTerms() {
        return Arrays.asList(headerWorkTerms);
    }

    public List<String> getHeaderAssignment() {
        return Arrays.asList(headerAssignment);
    }

    public List<String> getHeaderContract() {
        return Arrays.asList(headerContract);
    }

    public List<String> getHeaderPersonEmail() {
        return Arrays.asList(headerPersonEmail);
    }

    public List<String> getHeaderAssignmentWorkMeasure() {
        return Arrays.asList(headerAssignmentWorkMeasure);
    }

    public List<String> getHeaderPersonCitizenship() {
        return Arrays.asList(headerPersonCitizenship);
    }

    public List<String> getHeaderPersonVisa() {
        return Arrays.asList(headerPersonVisa);
    }

    public List<String> getHeaderAssignmentSupervisor() {
        return Arrays.asList(headerAssignmentSupervisor);
    }

    public List<String> getHeaderExternalIdentifier() {
        return Arrays.asList(headerExternalIdentifier);
    }

    public void addDataRowsWorker(List<String> dataRowsWorker) {
        this.dataRowsWorker.add(dataRowsWorker);
    }

    public List<List<String>> getDataRowsWorker() {
        return dataRowsWorker;
    }

    public void addDataRowsPersonName(List<String> dataRowPersonName) {
        dataRowsPersonName.add(dataRowPersonName);
    }

    public List<List<String>> getDataRowsPersonName() {
        return dataRowsPersonName;
    }

    public void addDataRowsPersonLegislativeData(List<String> dataRowPersonLegislativeData) {
        dataRowsPersonLegislativeData.add(dataRowPersonLegislativeData);
    }

    public List<List<String>> getDataRowsPersonLegislativeData() {
        return dataRowsPersonLegislativeData;
    }

    public List<List<String>> getDataRowsWorkRelationship() {
        return dataRowsWorkRelationship;
    }

    public void addDataRowsWorkTerms(List<String> dataRowWorkTerms) {
        dataRowsWorkTerms.add(dataRowWorkTerms);
    }

    public List<List<String>> getDataRowsWorkTerms() {
        return dataRowsWorkTerms;
    }

    public void addDataRowsAssignment(List<String> dataRowAssignment) {
        dataRowsAssignment.add(dataRowAssignment);
    }

    public List<List<String>> getDataRowsAssignment() {
        return dataRowsAssignment;
    }

    public void addDataRowsContract(List<String> dataRowContract) {
        dataRowsContract.add(dataRowContract);
    }

    public List<List<String>> getDataRowsContract() {
        return dataRowsContract;
    }

    public void addDataRowsPersonEmail(List<String> dataRowPersonEmail) {
        dataRowsPersonEmail.add(dataRowPersonEmail);
    }

    public List<List<String>> getDataRowsPersonEmail() {
        return dataRowsPersonEmail;
    }

    public void addDataRowsAssignmentWorkMeasure(List<String> dataRowAssignmentWorkMeasure) {
        dataRowsAssignmentWorkMeasure.add(dataRowAssignmentWorkMeasure);
    }

    public List<List<String>> getDataRowsAssignmentWorkMeasure() {
        return dataRowsAssignmentWorkMeasure;
    }

    public void addDataRowsPersonCitizenship(List<String> dataRowPersonCitizenship) {
        dataRowsPersonCitizenship.add(dataRowPersonCitizenship);
    }

    public List<List<String>> getDataRowsPersonCitizenship() {
        return dataRowsPersonCitizenship;
    }

    public void addDataRowsPersonVisa(List<String> dataRowPersonVisa) {
        dataRowsPersonVisa.add(dataRowPersonVisa);
    }

    public List<List<String>> getDataRowsPersonVisa() {
        return dataRowsPersonVisa;
    }

    public void addDataRowsAssignmentSupervisor(List<String> dataRowAssignmentSupervisor) {
        dataRowsAssignmentSupervisor.add(dataRowAssignmentSupervisor);
    }

    public List<List<String>> getDataRowsAssignmentSupervisor() {
        return dataRowsAssignmentSupervisor;
    }

    public void addDataRowsExternalIdentifier(List<String> dataRowExternalIdentifier) {
        dataRowsExternalIdentifier.add(dataRowExternalIdentifier);
    }

    public List<List<String>> getDataRowsExternalIdentifier() {
        return dataRowsExternalIdentifier;
    }
}
