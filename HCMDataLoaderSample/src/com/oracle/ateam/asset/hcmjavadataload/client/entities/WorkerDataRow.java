/* Copyright 2018, Oracle and/or its affiliates. All rights reserved.

The Universal Permissive License (UPL), Version 1.0
*/
/**
 * This class is used to store all the data rows for the HDL Worker.dat file to create/update the employee data
 *
 *
 */

package com.oracle.ateam.asset.hcmjavadataload.client.entities;

import com.oracle.ateam.asset.hcmjavadataload.client.HcmImportConstants;

import java.util.ArrayList;
import java.util.List;

public class WorkerDataRow {
    private String personNumber;
    private String workerEffectiveFrom;
    private String workerEffectiveTo;
    private String sex;
    private String firstName;
    private String lastName;
    private String loginName;
    private String emailAddress;
    private String address1;
    private String address2;
    private String city;
    private String company;
    private String costCenter;
    private String costType;
    private String language;
    private String currency;
    private String accountCurrency;
    private String timezone;
    private String application;
    private String defaultRoles;
    private String creationDate;
    private String deleteDate;
    private String leaderID;
    private String orgUnitKey;
    private String shoeSize;
    private String result;
    private String region1;
    private String region2;
    private String country;
    private String postCode;
    private String sourceSystemOwner;
    private String legislationCode;
    //Optional TODOs Optional extensions
    //              PersonNationalIdentifier
    //              Contract
    //              Managers
    //              AssignmentWorkMeasure
    //              PersonPhone

    /**
     * Creates one row for the Worker
     * Sample HDL
	METADATA|Worker|_sourceSystemOwner|SourceSystemId|EffectiveStartDate|EffectiveEndDate|PersonNumber|ActionCode|StartDate|WaiveDataProtectFlag|CategoryCode|SourceRefTableName=PERSON|SourceRef001=EMPLID
	MERGE|Worker|STUDENT5|STUDENT5_09412693|1994/04/01|4712/12/31|STUDENT5_PERSON_09412693|HIRE|1994/04/01|N|PER_EIT||STUDENT5_09412693
     ***/
    public List<String> getWorkerRow() {
        List<String> row = new ArrayList<String>();
        row.add("MERGE");
        row.add("Worker");
        row.add(sourceSystemOwner);
        row.add(sourceSystemOwner + "_" + personNumber);
        row.add(creationDate);
        row.add(workerEffectiveTo);
        row.add(sourceSystemOwner + "_PERSON_" + personNumber);
        row.add("HIRE");
        row.add(creationDate);
        row.add("N");
        row.add("PER_EIT");
        row.add(""); //SourceRefTableName=PERSON
        row.add(sourceSystemOwner + "_" + personNumber);
        return row;
    }

    /**
     * Creates one row for the name
     * Sample HDL
	METADATA|PersonName|_sourceSystemOwner|SourceSystemId|PersonId(SourceSystemId)|EffectiveStartDate|EffectiveEndDate|LegislationCode|NameType|FirstName|MiddleNames|LastName|Title
	MERGE|PersonName|STUDENT5|STUDENT5_NAME_09412693|STUDENT5_09412693|1994/04/01|4712/12/31|US|GLOBAL|John||Doe|MR.
     ***/
    public List<String> getPersonNameRow() {
        List<String> row = new ArrayList<String>();
        row.add("MERGE");
        row.add("PersonName");
        row.add(sourceSystemOwner);
        row.add(sourceSystemOwner + "_NAME_" + personNumber);
        row.add(sourceSystemOwner + "_" + personNumber);
        row.add(creationDate);
        row.add(workerEffectiveTo);
        row.add(legislationCode);
        row.add("GLOBAL");
        row.add(firstName);
        row.add("");
        row.add(lastName);
        row.add(sex);
        return row;
    }

    /**
     * Creates one row for the email
     * Sample HDL
	   METADATA|PersonEmail|_sourceSystemOwner|SourceSystemId|PersonId(SourceSystemId)|DateFrom|DateTo|EmailType|EmailAddress|PrimaryFlag
	   MERGE|PersonEmail|STUDENT5|STUDENT5_EMAIL_09412693|STUDENT5_09412693|1994/04/01|4712/12/31|W1|john.doe@TEST.COM|Y
     ***/
    public List<String> getPersonEmailRow() {
        List<String> row = new ArrayList<String>();
        row.add("MERGE");
        row.add("PersonEmail");
        row.add(sourceSystemOwner);
        row.add(sourceSystemOwner + "_EMAIL_" + personNumber);
        row.add(sourceSystemOwner + "_" + personNumber);
        row.add(creationDate);
        row.add(workerEffectiveTo);
        row.add("W1");
        row.add(emailAddress);
        row.add("Y");
        return row;
    }

    /**
     * Creates one row for the email
     * Sample HDL
	METADATA|PersonAddress|_sourceSystemOwner|SourceSystemId|EffectiveStartDate|EffectiveEndDate|PersonId(SourceSystemId)|AddressType|AddressLine1|AddressLine2|TownOrCity|Region1|Region2|Region3|Country|PostalCode|LongPostalCode|SourceRefTableName=ADDRESSES|SourceRef001=ADDRESS_ID
	MERGE|PersonAddress|STUDENT5|STUDENT5_ADDR_09412693|1994/04/01|4712/12/31|STUDENT5_09412693|HOME|StreetName 21||XYZStreet|XXCity|XX||US|20097|||STUDENT5_ADDR_09412693
     ***/
    public List<String> getPersonAddressRow() {
        List<String> row = new ArrayList<String>();
        row.add("MERGE");
        row.add("PersonAddress");
        row.add(sourceSystemOwner);
        row.add(sourceSystemOwner + "_ADDR_" + personNumber);
        row.add(creationDate);
        row.add(workerEffectiveTo);
        row.add(sourceSystemOwner + "_" + personNumber);
        row.add("HOME");
        row.add(address1);
        row.add(address2);
        row.add(city); //TownOrCity		
        row.add(region1); //Region1
        row.add(region2); //Region2
        row.add(""); //Region3
        row.add(country); //Country
        row.add(postCode); //PostalCode
        row.add(""); //LongPostalCode
        row.add(""); //SourceRefTableName=ADDRESSES
        row.add(sourceSystemOwner + "_ADDR_" + personNumber); //SourceRef001=ADDRESS_ID
        return row;
    }


    /**
     * Creates one row for the WorkRelationship
     * Sample HDL
	METADATA|WorkRelationship|_sourceSystemOwner|SourceSystemId|PersonId(SourceSystemId)|LegalEmployerName|DateStart|EnterpriseSeniorityDate|LegalEmployerSeniorityDate|ActualTerminationDate|Comments|LastWorkingDate|NotifiedTerminationDate|PrimaryFlag|ProjectedTerminationDate|RehireAuthorizerPersonId|RehireAuthorizor|RehireReason|WorkerNumber|WorkerType|OnMilitaryServiceFlag|RehireRecommendationFlag|SourceRefTableName=job_data|SourceRef001=job_id
	MERGE|WorkRelationship|STUDENT5|STD5_SERVICE_09412693|STUDENT5_09412693|US1 Legal Entity|1994/04/01|1985/01/05|1994/04/01|||||Y|||||STUDENT5_1|E|N|Y||STUDENT5_09412693::WR
     ***/
    public List<String> getWorkRelationshipRow() {
        List<String> row = new ArrayList<String>();
        row.add("MERGE");
        row.add("WorkRelationship");
        row.add(sourceSystemOwner);
        row.add(sourceSystemOwner + "_SERVICE_" + personNumber);
        row.add(sourceSystemOwner + "_" + personNumber);
        row.add(HcmImportConstants.DEFAULT_LEGAL_ENTITY);
        
        row.add(creationDate); //DateStart
        row.add(creationDate); //EnterpriseSeniorityDate
        row.add(creationDate); //LegalEmployerSeniorityDate
        row.add(""); //ActualTerminationDate
        row.add(""); //Comments
        row.add(""); //LastWorkingDate
        row.add(""); //NotifiedTerminationDate
        row.add("Y");
        row.add(""); //ProjectedTerminationDate|RehireAuthorizerPersonId|RehireAuthorizor|RehireReason|
        row.add("");
        row.add("");
        row.add("");
        row.add(sourceSystemOwner + "_WORKER_" + personNumber);
        row.add("E"); //E|N|Y||STUDENT5_09412693::WR
        row.add("N");
        row.add("Y");
        row.add("");
        row.add(sourceSystemOwner + "_" + personNumber + "_WR");
        return row;
    }

    /**
     * Creates one row for the WorkTerms
     * Sample HDL
	METADATA|WorkTerms|ActionCode|_sourceSystemOwner|SourceSystemId|EffectiveStartDate|EffectiveEndDate|EffectiveSequence|EffectiveLatestChange|AssignmentName|AssignmentNumber|AssignmentStatusTypeCode|AssignmentType|BusinessUnitShortCode|PositionOverrideFlag|PrimaryWorkTermsFlag|PeriodOfServiceId(SourceSystemId)|PersonId(SourceSystemId)|LegalEmployerName|SystemPersonType
	MERGE|WorkTerms|HIRE|STUDENT5|STUDENT5_WRK_TERM_09412693|1994/04/01|4712/12/31|1|Y||STUDENT5_WRK_TERM_09412693|ACTIVE_PROCESS|ET|US1 Business Unit|Y|Y|STD5_SERVICE_09412693|STUDENT5_09412693|US1 Legal Entity|EMP
     ***/
    public List<String> getWorkTermsRow() {
        List<String> row = new ArrayList<String>();
        row.add("MERGE");
        row.add("WorkTerms");
        row.add("HIRE");
        row.add(sourceSystemOwner);
        row.add(sourceSystemOwner + "_WRK_TERM_" + personNumber);
        // EffectiveStartDate|EffectiveEndDate|EffectiveSequence|EffectiveLatestChange|AssignmentName|AssignmentNumber|AssignmentStatusTypeCode|AssignmentType|
        // 1994/04/01|4712/12/31|1|Y||STUDENT5_WRK_TERM_09412693|ACTIVE_PROCESS|ET|
        row.add(creationDate); //EffectiveStartDate
        row.add(workerEffectiveTo); //EffectiveEndDate
        row.add("1"); //EffectiveSequence
        row.add("Y"); //EffectiveLatestChange
        row.add(""); //AssignmentName
        row.add(sourceSystemOwner + "_WRK_TERM_" + personNumber); //AssignmentNumber
        row.add("ACTIVE_PROCESS"); //AssignmentStatusTypeCode
        row.add("ET"); //AssignmentType
        // BusinessUnitShortCode|PositionOverrideFlag|PrimaryWorkTermsFlag|PeriodOfServiceId(SourceSystemId)|PersonId(SourceSystemId)|LegalEmployerName|SystemPersonType
        // US1 Business Unit|Y|Y|STD5_SERVICE_09412693|STUDENT5_09412693|US1 Legal Entity|EMP
        row.add(HcmImportConstants.DEFAULT_BUSINESS_UNIT); //BusinessUnitShortCode
        row.add("Y"); //PositionOverrideFlag
        row.add("Y"); //PrimaryWorkTermsFlag
        row.add(sourceSystemOwner + "_SERVICE_" + personNumber); //PeriodOfServiceId(SourceSystemId)
        row.add(sourceSystemOwner + "_" + personNumber); //PersonId(SourceSystemId)
        row.add(HcmImportConstants.DEFAULT_LEGAL_ENTITY); //LegalEmployerName
        row.add("EMP"); //SystemPersonType
        return row;
    }

    /**
     * Creates one row for the WorkTerms
     * Sample HDL
	METADATA|Assignment|ActionCode|_sourceSystemOwner|SourceSystemId|EffectiveStartDate|EffectiveEndDate|EffectiveSequence|EffectiveLatestChange|AssignmentName|AssignmentNumber|AssignmentStatusTypeCode|AssignmentType|BusinessUnitShortCode|PositionOverrideFlag|PrimaryAssignmentFlag|PrimaryFlag|SystemPersonType|LegalEmployerName|JobId(SourceSystemId)|LocationId(SourceSystemId)|OrganizationId(SourceSystemId)|PositionId(SourceSystemId)|GradeId(SourceSystemId)|PeriodOfServiceId(SourceSystemId)|PersonId(SourceSystemId)|PersonTypeCode|ManagerFlag|LabourUnionMemberFlag|WorkTermsAssignmentId(SourceSystemId)|AssignmentCategory|Frequency|HourlySalariedCode|NormalHours|NoticePeriod|ProbationPeriod|ProbationUnit|ReasonCode
	MERGE|Assignment|HIRE|STUDENT5|STUDENT5_ASSIGN_ID_09412693|1994/04/01|4712/12/31|1|Y|ASN100|STUDENT5_ASSIGN_NUM_09412693|ACTIVE_PROCESS|E|US1 Business Unit|Y|Y|Y|EMP|US1 Legal Entity|STUDENT5_JOB_20000852|STUDENT5_LOC1|STUDENT5_DEPT_20000852||STUDENT5_GRADE1|STD5_SERVICE_09412693|STUDENT5_09412693|Employee|Y|N|STUDENT5_WRK_TERM_09412693|FR|W|S|40|0|||NEWHIRE
     ***/
    public List<String> getAssignmentRow() {
        List<String> row = new ArrayList<String>();
        row.add("MERGE");
        row.add("Assignment");
        row.add("HIRE");
        row.add(sourceSystemOwner);
        row.add(sourceSystemOwner + "_ASSIGN_ID_" + personNumber);
        row.add(creationDate); //EffectiveStartDate
        row.add(workerEffectiveTo); //EffectiveEndDate
        row.add("1"); //EffectiveSequence
        row.add("Y"); //EffectiveLatestChange
        row.add(sourceSystemOwner + "_ASSIGN_NUM_" + personNumber); //AssignmentName
        row.add(sourceSystemOwner + "_ASSIGN_NUM_" + personNumber); //AssignmentNumber
        row.add("ACTIVE_PROCESS"); //AssignmentStatusTypeCode
        row.add("E"); //AssignmentType
        row.add("US1 Business Unit"); //BusinessUnitShortCode
        row.add("Y"); //PositionOverrideFlag
        row.add("Y"); //PrimaryAssignmentFlag
        row.add("Y"); //PrimaryFlag
        row.add("EMP"); //SystemPersonType
        row.add(HcmImportConstants.DEFAULT_LEGAL_ENTITY); //LegalEmployerName
        row.add(sourceSystemOwner + "_JOB_" + orgUnitKey); //JobId(SourceSystemId)
        row.add(sourceSystemOwner + "_LOC1"); //LocationId(SourceSystemId)
        row.add(sourceSystemOwner + "_DEPT_" + orgUnitKey); //OrganizationId(SourceSystemId)
        row.add(""); //PositionId(SourceSystemId)
        row.add(sourceSystemOwner +
                "_GRADE1"); //so far, have hardcoded it, since loading grades makes no sense 
        row.add(sourceSystemOwner + "_SERVICE_" + personNumber); //PeriodOfServiceId(SourceSystemId)
        row.add(sourceSystemOwner + "_" + personNumber); //PersonId(SourceSystemId)
        row.add("Employee"); //PersonTypeCode
        row.add("Y"); //ManagerFlag
        row.add("N"); //LabourUnionMemberFlag
        row.add(sourceSystemOwner + "_WRK_TERM_" + personNumber); //WorkTermsAssignmentId(SourceSystemId)
        row.add("FR"); //AssignmentCategory
        row.add("W"); //Frequency
        row.add("S"); //HourlySalariedCode
        row.add("40"); //NormalHours
        row.add("0"); //NoticePeriod
        row.add(""); //ProbationPeriod
        row.add(""); //ProbationUnit
        row.add("NEWHIRE"); //ReasonCode
        return row;
    }

    /**
     *    Creates one row for the phone
     *    Sample HDL
     *    METADATA|PersonPhone|PhoneId|PhoneType|DateFrom  |PersonNumber            |PhoneNumber |CountryCodeNumber|AreaCode|Extension|SpeedDialNumber|Validity|PrimaryFlag
          MERGE   |PersonPhone|       |W1       |1990/07/08|300000003613029         |555-155511-1234|                 |        |         |               |        | Y|
     * **/
    public List<String> getWorkPhoneRow() {
        List<String> row = new ArrayList<String>();
        row.add("MERGE");
        row.add("PersonPhone");
        //row.add(_phoneId); // _phoneId as a test!
        row.add("W1");
        //row.add( _phoneDateFrom);
        row.add(personNumber);
        //row.add( _phoneNumber);
        row.add("");
        row.add("");
        row.add("");
        row.add("");
        row.add("");
        row.add("Y");
        return row;
    }

    /**
     *    Creates one row for the phone
     *    Sample HDL
     *    METADATA|PersonPhone|PhoneId|PhoneType|DateFrom  |PersonNumber            |PhoneNumber |CountryCodeNumber|AreaCode|Extension|SpeedDialNumber|Validity|PrimaryFlag
          MERGE   |PersonPhone|       |WM       |1990/07/08|300000003613029         |555-333-1234|                 |        |         |               |        | Y|
     * **/
    public List<String> getWorkMobilePhoneRow() {
        List<String> row = new ArrayList<String>();
        row.add("MERGE");
        row.add("PersonPhone");
        //row.add(_mobilePhoneId); // _phoneId as a test!
        row.add("WM");
        //row.add( _mobilePhoneDateFrom);
        row.add(personNumber);
        //row.add( _mobilePhoneNumber);
        row.add("");
        row.add("");
        row.add("");
        row.add("");
        row.add("");
        row.add("N");
        return row;
    }

    @Override
    public String toString() {
        return "WorkerDataRow [_personNumber=" + personNumber + "_firstName=" + firstName + "_lastName=" + lastName +
               "]";
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return (this.result);
    }

    public String getBigCompany() {
        return result;
    }

    public String getPersonNumber() {
        return personNumber;
    }

    public void setPersonNumber(String personNumber) {
        this.personNumber = personNumber;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }

    public void setCostType(String costType) {
        this.costType = costType;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setAccountCurrency(String accountCurrency) {
        this.accountCurrency = accountCurrency;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public void setDefaultRoles(String defaultRoles) {
        this.defaultRoles = defaultRoles;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public void setDeleteDate(String deleteDate) {
        this.deleteDate = deleteDate;
    }

    public void setLeaderID(String leaderID) {
        this.leaderID = leaderID;
    }

    public void setOrgUnitKey(String orgUnitKey) {
        this.orgUnitKey = orgUnitKey;
    }

    public void setShoeSize(String shoeSize) {
        this.shoeSize = shoeSize;
    }

    public void setWorkerEffectiveFrom(String workerEffectiveFrom) {
        this.workerEffectiveFrom = workerEffectiveFrom;
    }

    public void setWorkerEffectiveTo(String workerEffectiveTo) {
        this.workerEffectiveTo = workerEffectiveTo;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setRegion1(String region1) {
        this.region1 = region1;
    }

    public void setRegion2(String region2) {
        this.region2 = region2;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public void setSourceSystemOwner(String sourceSystemOwner) {
        this.sourceSystemOwner = sourceSystemOwner;
    }

    public String getSourceSystemOwner() {
        return this.sourceSystemOwner;
    }

    public void setLegislationCode(String legislationCode) {
        this.legislationCode = legislationCode;
    }
}
