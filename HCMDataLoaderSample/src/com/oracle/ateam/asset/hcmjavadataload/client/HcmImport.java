/* Copyright 2018, Oracle and/or its affiliates. All rights reserved.

The Universal Permissive License (UPL), Version 1.0
*/
package com.oracle.ateam.asset.hcmjavadataload.client;

import com.oracle.ateam.asset.hcmjavadataload.client.entities.ColumnConfig;
import com.oracle.ateam.asset.hcmjavadataload.client.entities.CombinedPersonData;
import com.oracle.ateam.asset.hcmjavadataload.client.entities.HcmImportResultFile;
import com.oracle.ateam.asset.hcmjavadataload.client.entities.UserDataRow;
import com.oracle.ateam.asset.hcmjavadataload.client.entities.UserFile;
import com.oracle.ateam.asset.hcmjavadataload.client.entities.WorkerDataRow;
import com.oracle.ateam.asset.hcmjavadataload.client.entities.WorkerFile;
import com.oracle.ateam.asset.hcmjavadataload.proxies.HCMDataLoaderUtil;
import com.oracle.ateam.asset.hcmjavadataload.proxies.UCMUploadUtil;
import com.oracle.ateam.asset.hcmjavadataload.utils.CSVReader;
import com.oracle.ateam.asset.hcmjavadataload.utils.CSVWriter;
import com.oracle.ateam.asset.hcmjavadataload.utils.ValidatorException;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import java.nio.channels.FileChannel;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import oracle.stellent.ridc.IdcClientException;


/**
 * Example for a client using Oracle HCM Cloud (Fusion) Import integration to
 * import Data from an external source (.csv) files <br>
 *
 * The integration flow implemented provides the following:
 * - Loops around files in the in directory and....
 * - Reads the csv File from the local system with the current data from an local file system
 * - Converts into two HCM compliant .Dat Files
 * - Creates a ZIP file containing these .Dat Files
 * - Uploads the ZIP file to Fusion UCM
 * - Calls the HCMDataLoader WebService to start the import
 * - Download and check status of HDL import load
 *
 */
public class HcmImport {
    private static int firstDataRow;
    private static final Logger LOGGER = Logger.getLogger(HcmImport.class.getName());
    private static HcmImportProperties propertiesFile = null;
    private static String fusionUserPass; // Used to store users password

    private HcmImport() {
        ; // This block intentionally left blank!
    }

    /*
     * main class for batch import tests
     */
    public static void main(String[] args) {
        HcmImport hcmImport = new HcmImport();
        try {
            HcmImport.setProjectConfiguration();
        } catch (Exception e) {
            LOGGER.severe("Error loading properties file, terminating");
            System.exit(-1);
        }
        hcmImport.performIntegration();
    }

    protected static void setProjectConfiguration() {
        firstDataRow = 1; // set to 1 if the first row contains the header. set to 0 if the first row
        // contains data
        // Read Properties File
        try {
            propertiesFile = new HcmImportProperties();
            HcmImportProperties.init();
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            System.exit(-1);
        }
        try {
            // Accept password from user
            Scanner scanReader = new Scanner(System.in); // Reading from System.in
            System.out.println("Enter password for user " +
                               propertiesFile.getProperty(HcmImportProperties.FUSION_USERNAME) + " :");
            fusionUserPass = scanReader.next(); // Scans the next token of the input as an int.
            scanReader.close();
        } catch (Exception e) {
            System.err.println("Error during password capture: " + e.getLocalizedMessage());
            // Exit program, closes all resources
            System.exit(-1);
        }
    }

    /**
     * This method provides the main steps for this test integration: - Read the
     * file from the local file system - After the integration we can move the
     * result files to the local (and remote) folders
     */
    protected void performIntegration() {
        try {
            File[] sourceFiles = getFileFromLocalPath();
            if (sourceFiles != null && sourceFiles.length > 0) {
                LOGGER.fine(String.format("Found %d files", sourceFiles.length));
                // Process Files one by one
                for (File sourceFile : sourceFiles) {
                    LOGGER.fine("Found sourceFile: " + sourceFile);
                    processFile(sourceFile);
                    // At the end of processing clean up the "in" and move file(s) to the "out"
                    // folder
                    moveFileFromInToOut(sourceFile);
                }
                LOGGER.info("End processing files");
            } else {
                LOGGER.info("No files found for processing, terminating");
            }
        } catch (Exception e) {
            LOGGER.info(String.format("Error during processing occured, %s", e.getMessage()));
        }
    }

    private static File[] getFileFromLocalPath() throws Exception {
        String dataDirectory =
            String.format("%s%s%s", propertiesFile.getProperty(HcmImportProperties.DATAPATH),
                          HcmImportConstants.INFOLDER, File.separator);

        LOGGER.info(String.format("Looking for data files in: %s ", dataDirectory));
        File inDir = new File(dataDirectory);
        // Get the list of files in this directory (do not in include any potential
        // subfolders)
        File[] content = inDir.listFiles(new FileFilter() {
            public boolean accept(File f) {
                if (f == null) {
                    return false;
                }
                if (f.isDirectory()) {
                    LOGGER.info(String.format("getFilesFrom Disk. next file found: %s (is a directory)", f.getName()));
                    return false;
                } else {
                    LOGGER.info("getFilesFromLocalPath(). next file found: " + f.getName());
                    // only download .csv files - not the temp files!
                    if (f.getName().endsWith(".csv")) {
                        return true;
                    } else {
                        LOGGER.info("getFilesFromLocalPath(): Only use .csv files. Do not import " + f.getName());
                        return false;
                    }
                }
            }
        });
        if (content == null) {
            String msg = "No data files found, please check DATAPATH location";
            LOGGER.severe(msg);
            throw new Exception(msg);
        }
        LOGGER.fine(String.format("getFilesFromLocalPath(): found: %d .csv file(s) in %s", content.length, inDir));
        if (content.length == 0) {
            return content;
        }
        return content;
    }

    private static void moveFileFromInToOut(File nextInFile) throws Exception {
        if (nextInFile == null) {
            return;
        }
        try {
            String dataPath = propertiesFile.getProperty("DATAPATH");
            if (dataPath == null || dataPath.isEmpty()) {
                return;
            }
            copyFile(nextInFile,
                     new File(dataPath + HcmImportConstants.OUTFOLDER + File.separator + nextInFile.getName()));
            nextInFile.delete();
        } catch (Exception e) {
            LOGGER.info("Unable to delete file ,continueing regardless " + e.getLocalizedMessage());
        }
    }

    protected void processFile(File nextInFile) throws Exception {
        if (nextInFile == null) {
            return;
        }
        String dataSeparator = propertiesFile.getProperty(HcmImportProperties.DATA_SEPERATOR);
        if (dataSeparator == null || dataSeparator.isEmpty()) {
            return;
        }
        // read the csv file and store each field in the input List
        CSVReader reader = new CSVReader();
        reader.setSeparator(dataSeparator.charAt(0));
        List<List<String>> inputData =
            reader.readCsvFile(nextInFile.getAbsolutePath(),
                               propertiesFile.getProperty(HcmImportProperties.CSV_FILE_ENCODING));
        if (inputData.isEmpty()) {
            LOGGER.fine("Datafile " + nextInFile.getPath() + " Is empty or corrupted");
            throw new Exception("error : invalid data file");
        }
        // first level validation. In case of a ('file wide') first level validation
        // error, we do not get to the next (row-by-row) step!
        fistLevelValidation(inputData);
        // we transform the input data and will use a map with the Person Number as a key
        // and the (transformed) User/Person data row as elements to store the input data
        Map<String, CombinedPersonData> inputDataMap = new HashMap<String, CombinedPersonData>();
        // transform and flatten the local csv data into the data fields required by Fusion HCM Data Loader
        // best practice is to flatten the data so that all operations to a single party are done in one block
        transformData(inputData, inputDataMap);
        File nextResultsFile =
            new File(nextInFile.getParentFile() + File.separator +
                     replaceAll(nextInFile.getName(), ".csv", ".result.csv"));
        // import the data rows (with no error so far) into Fusion HCM
        importDataIntoFusion(inputDataMap, nextResultsFile);
    }

    private void importDataIntoFusion(Map<String, CombinedPersonData> inputDataMap,
                                      File nextResultsFile) throws Exception {
        // only send to HCM if at least one User or Person is HcmImportConstants.OK
        boolean sendToHCM = false;
        // Create new data containers to store the transformed data for the Worker.dat,
        // Contact, ElementEntry and Salary.dat files
        WorkerFile workerFile = new WorkerFile();
        UserFile userFile = new UserFile();
        // add each row without error into the respective HDL .dat file
        Iterator<String> it = inputDataMap.keySet().iterator();
        while (it.hasNext()) {
            String personNumber = it.next();
            CombinedPersonData combinedPersonData = inputDataMap.get(personNumber);
            // get the worker data part of this container and if found, check if the data is
            // HcmImportConstants.OK and should be sent to HCM
            WorkerDataRow workerDataRow = combinedPersonData.getWorkerDataRow();
            if (HcmImportConstants.OK.equals(workerDataRow.getResult())) {
                sendToHCM = true;
                workerFile.addDataRowsWorker(workerDataRow.getWorkerRow());
                workerFile.addDataRowsPersonName(workerDataRow.getPersonNameRow());
                workerFile.addDataRowsPersonEmail(workerDataRow.getPersonEmailRow());
                // optionally: add other data columns
            } else {
                LOGGER.fine(String.format("importDataIntoFusion: Next WorkerDataRow is not HcmImportConstants.OK %s: %s",
                                          personNumber, workerDataRow.getResult()));
            }
            // get the Contact data part of this container and if found, add the row into
            // the User File
            UserDataRow userDataRow = combinedPersonData.getUserDataRow();
            if (userDataRow == null) {
                // nothing for now






            } else if (HcmImportConstants.OK.equals(userDataRow.getResult())) {
                sendToHCM = true;
                userFile.addDataRow(userDataRow.getRow());
            } else {
                LOGGER.fine(String.format("importDataIntoFusion: Next userDataRow is not HcmImportConstants.OK %s: %s",
                                          personNumber, userDataRow.getResult()));
            }
        }
        if (sendToHCM) {
            // create ZIP File out of the single .dat Files
            File hdlZipFile = createZipFile(workerFile, userFile);
            LOGGER.info("importDataIntoFusion: created hdlZipFile: " + hdlZipFile);
            // upload the ZIP File to Fusion UCM - get the content ID of the file on UCM
            String contentId = uploadZIPtoUCM(hdlZipFile);
            LOGGER.info("importDataIntoFusion: uploaded hdlZipFile to UCM. contentId =" + contentId);
            // with the contentId - start HDL Import
            String processId = null;
            try {
                processId =
                    HCMDataLoaderUtil.loadDataHDL(propertiesFile.getProperty(HcmImportProperties.HCM_DATA_LOADER_SERVICE_WSDL_LOCATION),
                                                  propertiesFile.getProperty(HcmImportProperties.FUSION_USERNAME),
                                                  fusionUserPass,
                                                  propertiesFile.getProperty(HcmImportProperties.KEYSTORE_LOCATION),
                                                  contentId);
            } catch (Exception e) {
                LOGGER.severe("Error loading data into Fusion HCM, process aborted. Error message : " +
                              e.getLocalizedMessage());
                throw e;
            }
            LOGGER.info("importDataIntoFusion: HDL import finished with response " + processId);
            // For now the error handling needs to be done manually by downloading the error report
            // the error handling is not implemented on this Test Import Class
            // If required, it is possible to download the BIP Report with all messages from the HDL Import
            String loadResult =
                HCMDataLoaderUtil.invokeGetDataSetStatus(propertiesFile.getProperty(HcmImportProperties.HCM_DATA_LOADER_SERVICE_WSDL_LOCATION),
                                                         propertiesFile.getProperty(HcmImportProperties.FUSION_USERNAME),
                                                         fusionUserPass,
                                                         propertiesFile.getProperty(HcmImportProperties.KEYSTORE_LOCATION),
                                                         new Long(processId),
                                                         propertiesFile.getProperty(HcmImportProperties.GETDATASETSTATUS_DELAY));
            // write result into file
            File hdlReportFile = new File(hdlZipFile.getPath() + replaceAll(hdlZipFile.getName(), ".zip", ".out.xml"));
            LOGGER.info("importDataIntoFusion: write HDL Report into File " + hdlReportFile);
            writeStringToFile(loadResult, hdlReportFile, HcmImportConstants.ENCODING);
            LOGGER.fine("importDataIntoFusion: HDL Report File written");
        } else {
            LOGGER.info("importRequisitions: sendToHCM is false. No Valid row(s) to send in the file");
            writeResultFileAllErrors(nextResultsFile, inputDataMap);
        }
    }

    private String uploadZIPtoUCM(File hdlZipFile) throws Exception {
        try {
            String dDocName = hdlZipFile.getName();
            String dDocTitle = hdlZipFile.getName();
            String dSecurityGroup = "FAFusionImportExport";
            String dDocType = "Document";
            LOGGER.fine("uploadZIPtoUCM: dDocTitle: " + dDocTitle);
            return UCMUploadUtil.uploadFileToUCMwithSOAP(propertiesFile.getProperty(HcmImportProperties.UCM_IDC_WEBSERVICE_LOCATION),
                                                         propertiesFile.getProperty(HcmImportProperties.FUSION_USERNAME),
                                                         fusionUserPass, hdlZipFile, "Test1", dDocName, dDocTitle,
                                                         dSecurityGroup, "hcm$/dataloader$/import$", dDocType);
        } catch (IdcClientException e) {
            LOGGER.severe("Eror uploading ZIP file to HCM, error from WS is " + e.getLocalizedMessage());
            throw e;
        }
    }

    /**
     * This method is used to transform the input data rows from the csv file into a
     * map - with the PERSON_NUMBER as a key and the CombinedPersonData object as
     * elements.
     *
     * In case there is some error or warning with the row, it will be reported.
     *
     * @throws MappingNotFoundException
     * @throws DateUtilException
     */
    protected void transformData(List<List<String>> inputData,
                                 Map<String, CombinedPersonData> inputDataMap) throws Exception {
        // the first row contains already the data - no header has been read !
        for (int i = firstDataRow; i < inputData.size(); i++) {
            // get the next row
            LOGGER.info("transformData *********************************** next row #: " + i);
            List<String> row = inputData.get(i);
            String personNumber = getTrimmedData(row, HcmImportConstants.PERSON_NUMBER);
            // check if there is already a row for this person_number in the map
            if (inputDataMap.containsKey(personNumber)) {
                LOGGER.info("transformData. Found another row for " + personNumber);
                CombinedPersonData combinedPersonData = inputDataMap.get(personNumber);
                setHDLDataRowsEmployeeData(row, combinedPersonData);
            } else {
                LOGGER.info("transformData. First row for " + personNumber);
                CombinedPersonData combinedPersonData = new CombinedPersonData();
                setHDLDataRowsEmployeeData(row, combinedPersonData);
                inputDataMap.put(personNumber, combinedPersonData);
            }
        }
    }

    /**
     * For each file, it will run a first level validation: - Check if the header
     * row has the correct number of columns - Check if all rows have a personNumber
     * - Unique Test is not implemented If any first level validation errors are
     * found, the program will report an error for this whole file and none of the
     * records within the file are imported .
     *
     * @param input
     * @throws ValidatorException
     */
    private void fistLevelValidation(List<List<String>> input) throws Exception {
        // this check validates that the input file has the valid number of columns
        // (since we count from zero - add one)
        checkInputFileHeader(input, HcmImportConstants.LAST_COLUMN + 1);
        // if the number of columns is ok - set the rows into a maps - with the ID as a
        // key - to check for duplicates
        for (int i = firstDataRow; i < input.size(); i++) {
            List<String> row = input.get(i);
            // remove the debug after initial tests:
            LOGGER.fine("uploadData: 1st level validation. Next row #: " + i);
            // The following lines check to see if the MANDATORY person number is available.
            String personNumber = getTrimmedData(row, HcmImportConstants.PERSON_NUMBER);
            if (isNullOrAbsent(personNumber)) {
                LOGGER.info("personNumber is null for row: " + row);
                throw new Exception("Found row with empty " + HcmImportConstants.PERSON_NUMBER.getName() +
                                    ". Import of this file stopped");
            } else {
                // OPTIONAL - Decide if for the Integration, it is possible and allowed to have
                // duplicates in their file.
                // The duplicate rows are later combined into one common data set for that person
                // This is why the following duplicate validation check is commented out:
                // if (allpersonNumbers.containsKey(personNumber)) {
                // String errorMessage = "Duplicate personNumber found: " + personNumber + ". Import of this files stopped";
                // //duplicate personalNumber found!
                // logger.error("validatepersonNumber: " + errorMessage + "\n" + row + "\n" + allpersonNumbers.get(personNumber));
                // throw new ValidatorException(errorMessage, PERSON_NUMBER.getName());
                // } else {
                // allpersonNumbers.put(personNumber, row);
                // }
            }
        }
    }

    /**
     * this check validates that the first line is the header has the correct length
     */
    protected void checkInputFileHeader(List<List<String>> input, int validNumberOfColumns) throws Exception {
        // the first Line must be the heading with the correct number of rows
        List<String> heading = input.get(0);
        // the first check, is if the file has enough columns:
        if (heading.size() < validNumberOfColumns) {
            String errorMessage =
                String.format("Input file has not enough columns. Must be %d, found only %d", validNumberOfColumns,
                              heading.size());
            LOGGER.severe(errorMessage);
            throw new ValidatorException(errorMessage);
        }
        if (heading.size() > validNumberOfColumns + 1) {
            String errorMessage =
                String.format("Input file has too many columns. Must be %d, found only %d", validNumberOfColumns,
                              heading.size());
            LOGGER.severe(errorMessage);
            throw new ValidatorException(errorMessage);
        }
    }

    public void writeListToFile(List<List<String>> fileList, File nextResultFile, List<String> headerList,
                                String encoding, char separator) throws Exception {
        if (nextResultFile == null) {
            return;
        }
        if (fileList == null) {
            return;
        }
        Writer pw = null;
        CSVWriter csvWriter = null;
        try {
            pw = new OutputStreamWriter(new FileOutputStream(nextResultFile), encoding);
            csvWriter = new CSVWriter(pw);
            csvWriter.setSeparator(separator);
            csvWriter.put(headerList);
            csvWriter.nl();
            for (int i = 0, size = fileList.size(); i < size; i++) {
                csvWriter.put((List<String>) fileList.get(i));
                csvWriter.nl();
            }
        } catch (Exception e) {
            String mesg = String.format("Error writing the file %s %s", nextResultFile, e.getMessage());
            throw new Exception(mesg, e);
        } finally {
            if (csvWriter != null) {
                csvWriter.close();
            }
            if (pw != null) {
                pw.close();
            }
        }
    }

    private void writeResultFileAllErrors(File nextResultFile,
                                          Map<String, CombinedPersonData> inputDataMap) throws Exception {
        if (nextResultFile == null) {
            return;
        }
        if (inputDataMap == null) {
            return;
        }
        List<List<String>> resultFileData = new ArrayList<List<String>>();
        Iterator<String> allADIDIterator = inputDataMap.keySet().iterator();
        while (allADIDIterator.hasNext()) {
            String nextPersonNumber = allADIDIterator.next();
            CombinedPersonData nextCombinedDataRow = inputDataMap.get(nextPersonNumber);
            if (nextCombinedDataRow == null) {
                continue;
            }
            List<String> nextDataRowResult = nextCombinedDataRow.getCombinedResultList();
            LOGGER.fine("writeResultFilesppalAllErrors: add nextDataRowResult: " + nextDataRowResult);
            // add the next row into the result file:
            resultFileData.add(nextDataRowResult);
        }
        LOGGER.fine("writeResultFilesppalAllErrors: write it into the resultFile " + nextResultFile.getName());
        writeListToFile(resultFileData, nextResultFile, HcmImportResultFile.getHeader(), HcmImportConstants.ENCODING,
                        ';');
    }

    /**
     * Transform the Local HR Data for this person into the HCM Data Loader (HDL)
     * Format, including the required value mappings and Column based Business Rules
     *
     * @param row
     * @param dataRow
     * @throws MappingNotFoundException
     * @throws DateUtilException
     */
    private void setHDLDataRowsEmployeeData(List<String> row, CombinedPersonData combinedPersonData) throws Exception {
        if (row == null) {
            return;
        }
        if (combinedPersonData == null) {
            return;
        }
        // create the data for the woker.dat file
        WorkerDataRow workerDataRow = new WorkerDataRow();
        // create the data for the user.dat file
        UserDataRow userDataRow = new UserDataRow();
        String personNumber = getTrimmedData(row, HcmImportConstants.PERSON_NUMBER);
        try {
            combinedPersonData.setPersonNumber(personNumber);
            /** this is the Enterprise Hire Date **/
            String legalEmpHireDateStringHdl =
                getHDLDateFromExcelString(row, HcmImportConstants.LEGAL_EMPLOYER_HIRE_DATE, true);
            workerDataRow.setWorkerEffectiveFrom(legalEmpHireDateStringHdl);
            workerDataRow.setWorkerEffectiveTo("4712/12/31");
            workerDataRow.setPersonNumber(personNumber);
            workerDataRow.setSourceSystemOwner(HcmImportConstants.SOURCE_KEY);
            workerDataRow.setLegislationCode(HcmImportConstants.LEGISLATION_CODE);
            workerDataRow.setEmailAddress(getTrimmedData(row, HcmImportConstants.EMAIL_ADDRESS));
            workerDataRow.setFirstName(getTrimmedData(row, HcmImportConstants.FIRST_NAME));
            workerDataRow.setLastName(getTrimmedData(row, HcmImportConstants.LAST_NAME));
            workerDataRow.setResult(HcmImportConstants.OK);
        } catch (ValidatorException ve) {
            String msg = "Data Error found: " + ve.getMessage() + " for element: " + ve.getElementCausingError();
            LOGGER.info("Found Validation Error: " + msg);
            workerDataRow.setResult(HcmImportConstants.ERROR + msg);
        }
        // the username can be set with our without an Activation Date
        String username = getTrimmedData(row, HcmImportConstants.USERNAME);
        if (isNullOrAbsent(username)) {
            userDataRow.setResult(HcmImportConstants.ERROR + ": Missing Username");
        } else {
            userDataRow.setPersonNumber(personNumber);
            userDataRow.setUserName(username);
            userDataRow.setResult(HcmImportConstants.OK);
        }
        combinedPersonData.setWorkerDataRow(workerDataRow);
        combinedPersonData.setUserDataRow(userDataRow);
    }

    /**
     * Convenience message to get the Date String in the right HDL compliant format
     *
     * @param row
     * @param column
     * @param required
     * @return
     * @throws ValidatorException
     */
    private String getHDLDateFromExcelString(List<String> row, ColumnConfig column,
                                             boolean required) throws ValidatorException {
        String dateExtract = "";
        if (row == null) {
            return dateExtract;
        }
        if (column == null) {
            return dateExtract;
        }
        if (required) {
            dateExtract = getRequiredTrimmedData(row, column);
        } else {
            dateExtract = getTrimmedData(row, column);
        }
        try {
            Date dateExtractDate = toDateFromString(dateExtract, HcmImportConstants.DATE_FORMAT_EXCEL_EXTRACT);
            return toString(dateExtractDate, HcmImportConstants.DATE_FORMAT_HDL);
        } catch (Exception e) {
            throw new ValidatorException(String.format("Invalid Date Format in column %s : Expected Format %s %s ",
                                                       column.getName(), dateExtract,
                                                       HcmImportConstants.DATE_FORMAT_EXCEL_EXTRACT, column.getName()));
        }
    }

    private String getRequiredTrimmedData(List<String> row, ColumnConfig column) throws ValidatorException {
        String value = getTrimmedData(row, column);
        if (isNullOrAbsent(value)) {
            throw new ValidatorException("Missing required field " + column.getName(), column.getName());
        }
        return value;
    }

    protected File createZipFile(WorkerFile workerFile, UserFile userFile) throws IOException {
        if (workerFile == null) {
            return null;
        }
        if (userFile == null) {
            return null;
        }
        String timeStamp = new SimpleDateFormat(HcmImportConstants.DATE_FORMAT_UCM).format(new Date());
        String zipFileName = HcmImportConstants.UCM_DOCNAME + timeStamp + ".zip";
        File zipFile = new File(propertiesFile.getProperty(HcmImportProperties.DATAPATH) + zipFileName);
        LOGGER.info("createZipFile. Create zip file:  " + zipFileName + " (from the dat files: " +
                    workerFile.getFileName() + ";  " + userFile.getFileName() + ")");
        FileOutputStream zipos = null;
        ZipOutputStream zip = null;
        FileInputStream in = null;
        try {
            // create a new output stream to write the zip file
            zipos = new FileOutputStream(zipFile);
            zip = new ZipOutputStream(zipos);
            in = addNextFileToZip(workerFile.getFileName(), zip, getWorkerDatFile(workerFile));
            in = addNextFileToZip(userFile.getFileName(), zip, getUserDatFile(userFile));
        } finally {
            // close the streams
            if (in != null) {
                in.close();
            }
            if (zip != null) {
                zip.close();
            }
            if (zipos != null) {
                zipos.close();
            }
        }
        return zipFile;
    }

    private static FileInputStream addNextFileToZip(String fileName, ZipOutputStream zip,
                                             File datFile) throws IOException {
        if (datFile == null || fileName == null || zip == null) {
            return null;
        }
        if (fileName.isEmpty()) {
            throw new FileNotFoundException("File name for Zip file is empty!");
        }
        FileInputStream in = null;
        try {
            in = new FileInputStream(datFile);
            zip.putNextEntry(new ZipEntry(fileName));
            // Transfer bytes from the file to the ZIP file
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                zip.write(buf, 0, len);
            }
            zip.closeEntry();
            // delete the "original" .dat file.
            // comment out after inital test:
            datFile.delete();
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return in;
    }

    private static File getWorkerDatFile(WorkerFile workerFile) throws IOException {
        if (workerFile == null) {
            return null;
        }
        File workerDatFile =
            new File(propertiesFile.getProperty(HcmImportProperties.DATAPATH) + workerFile.getFileName());
        FileOutputStream fs = null;
        Writer resultWriter = null;
        try {
            fs = new FileOutputStream(workerDatFile);
            resultWriter = new OutputStreamWriter(fs, HcmImportConstants.ENCODING);
            CSVWriter csvWriter = new CSVWriter(resultWriter, CSVWriter.MINIMAL_QUOTES);
            csvWriter.setSeparator(HcmImportConstants.SEPARATOR);
            addNextItem(csvWriter, workerFile.getHeaderWorker(), workerFile.getDataRowsWorker());
            addNextItem(csvWriter, workerFile.getHeaderPersonName(), workerFile.getDataRowsPersonName());
            addNextItem(csvWriter, workerFile.getHeaderPersonLegislativeData(),
                        workerFile.getDataRowsPersonLegislativeData());
            addNextItem(csvWriter, workerFile.getHeaderWorkRelationship(), workerFile.getDataRowsWorkRelationship());
            addNextItem(csvWriter, workerFile.getHeaderWorkTerms(), workerFile.getDataRowsWorkTerms());
            addNextItem(csvWriter, workerFile.getHeaderAssignment(), workerFile.getDataRowsAssignment());
            addNextItem(csvWriter, workerFile.getHeaderContract(), workerFile.getDataRowsContract());
            addNextItem(csvWriter, workerFile.getHeaderPersonEmail(), workerFile.getDataRowsPersonEmail());
            addNextItem(csvWriter, workerFile.getHeaderAssignmentWorkMeasure(),
                        workerFile.getDataRowsAssignmentWorkMeasure());
            addNextItem(csvWriter, workerFile.getHeaderPersonCitizenship(), workerFile.getDataRowsPersonCitizenship());
            addNextItem(csvWriter, workerFile.getHeaderPersonVisa(), workerFile.getDataRowsPersonVisa());
            addNextItem(csvWriter, workerFile.getHeaderAssignmentSupervisor(),
                        workerFile.getDataRowsAssignmentSupervisor());
            addNextItem(csvWriter, workerFile.getHeaderExternalIdentifier(),
                        workerFile.getDataRowsExternalIdentifier());
        } finally {
            try {
                if (resultWriter != null) {
                    resultWriter.close();
                }
                if (fs != null) {
                    fs.close();
                }
            } catch (IOException ioe) {
                LOGGER.fine("Error reading WorkderDat Data file " + ioe.getLocalizedMessage());
            }
        }
        return workerDatFile;
    }

    private static File getUserDatFile(UserFile userFile) throws IOException {
        if (userFile == null) {
            return null;
        }
        File userDatFile = new File(propertiesFile.getProperty("DATAPATH") + userFile.getFileName());
        FileOutputStream fs = null;
        Writer resultWriter = null;
        try {
            fs = new FileOutputStream(userDatFile);
            resultWriter = new OutputStreamWriter(fs, HcmImportConstants.ENCODING);
            CSVWriter csvWriter = new CSVWriter(resultWriter);
            csvWriter.setSeparator(HcmImportConstants.SEPARATOR);
            csvWriter.put(userFile.getHeader());
            csvWriter.nl();
            csvWriter.put(userFile.getDataRows());
            csvWriter.close();
        } finally {
            try {
                if (resultWriter != null) {
                    resultWriter.close();
                }
                if (fs != null) {
                    fs.close();
                }
            } catch (IOException ioe) {
                LOGGER.info("Error reading UserDat Data file " + ioe.getLocalizedMessage());
            }
        }
        return userDatFile;
    }

    private static void addNextItem(CSVWriter csvWriter, List<String> header, List<List<String>> dataRows) {
        if (csvWriter == null || header == null || dataRows == null) {
            return;
        }
        csvWriter.put(header);
        csvWriter.nl();
        csvWriter.put(dataRows);
    }

    /**
     * The HDL upload report list contains error messages for many different things.
     * We try to find all the ones relevant for a given person Number
     *
     * @param reportList
     * @return
     */
    private Map<String, List<String>> getErrorMatrix(List<List<String>> reportList) {
        Map<String, List<String>> errorMatrix = new HashMap<String, List<String>>();
        LOGGER.fine("getErrorMatrix for " + reportList);
        if (reportList == null) {
            return errorMatrix;
        }
        for (Iterator<List<String>> iterator = reportList.iterator(); iterator.hasNext();) {
            List<String> list = iterator.next();
            LOGGER.fine("getErrorMatrix: next line " + list);
            // in this list, we get some of the most important fields
            String errorLocation = getTrimmedData(list, HcmImportConstants.ERR_LOCATION);
            String messageType = getTrimmedData(list, HcmImportConstants.MESSAGE_TYPE);
            String messageText = getTrimmedData(list, HcmImportConstants.MSG_TEXT);
            String fileLine = getTrimmedData(list, HcmImportConstants.FILE_LINE);
            String businessObject = getTrimmedData(list, HcmImportConstants.BUSINESSOBJECT);
            if ("Service Error".equalsIgnoreCase(errorLocation)) {
                // no idea what to make out of this
                LOGGER.info("getErrorMatrix: errorLocation is 'Service Error': messageText: " + messageText);
            } else {
                if (!isNullOrAbsent(fileLine)) {
                    StringTokenizer st = new StringTokenizer(fileLine, "|");
                    List<String> stringArrayList = new ArrayList<String>();
                    while (st.hasMoreElements()) {
                        stringArrayList.add(st.nextElement().toString());
                    }
                    // from this array use the third item which should be the personNumber
                    String personNumber = "";
                    if ("PersonPhone".equalsIgnoreCase(businessObject)) {
                        personNumber = getTrimmedData(stringArrayList, 5);
                    } else if ("PersonEmail".equalsIgnoreCase(businessObject)) {
                        personNumber = getTrimmedData(stringArrayList, 3);
                    }
                    String errorMessage =
                        messageType + ": " + businessObject + ": " + messageText + " (" + fileLine + ")";
                    LOGGER.fine("getErrorMatrix: personNumber:" + personNumber + " errorMessage: " + errorMessage);
                    addToMatrix(errorMatrix, personNumber, errorMessage);
                } else {
                    LOGGER.fine("getErrorMatrix:  fileLine is empty. messageText: " + messageText);
                }
            }
        }
        return errorMatrix;
    }

    private static void addToMatrix(Map<String, List<String>> errorMatrix, String personNumber, String errorMessage) {
        if (errorMatrix == null || personNumber == null) {
            return;
        }
        if (errorMatrix.containsKey(personNumber)) {
            List<String> errors = errorMatrix.get(personNumber);
            errors.add(errorMessage);
        } else {
            List<String> errors = new ArrayList<String>();
            errors.add(errorMessage);
            errorMatrix.put(personNumber, errors);
        }
    }

    /**
     * Copies a file using the fast <code>java.nio</code> implementation.
     *
     * @param in
     *            The source file.
     * @param out
     *            The target file.
     *
     * @throws IOException
     *             If some I/O error occurs.
     */
    public static void copyFile(File in, File out) throws IOException {
        if (in == null || out == null) {
            return;
        }
        FileInputStream is = null;
        FileOutputStream os = null;
        try {
            is = new FileInputStream(in);
            os = new FileOutputStream(out);
            copyFile(is.getChannel(), os.getChannel());
            os.flush();
        } finally {
            if (os != null) {
                os.close();
            }
            if (is != null) {
                is.close();
            }
        }
    }

    /**
     * Copies a file using the fast <code>java.nio</code> implementation.
     *
     * @param in
     *            The source file channel.
     * @param out
     *            The target file channel.
     *
     * @throws IOException
     *             If some I/O error occurs.
     */
    public static void copyFile(FileChannel in, FileChannel out) throws IOException {
        if (in == null || out == null) {
            return;
        }
        out.transferFrom(in, 0, in.size());
    }

    /**
     * Very basic method to write a String into a file.
     *
     * @param text
     * @param file
     * @throws IOException
     */
    public static void writeStringToFile(String text, File file, String encoding) throws IOException {
        if (file == null) {
            return;
        }
        Writer writer = null;
        try {
            writer = new OutputStreamWriter(new FileOutputStream(file), encoding);
            writer.write(text);
            writer.flush();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    /**
     * This method can be used to get the trimmed data from a row from the specified
     * column. It can be used to avoid unmanaged break in case one line does not have
     * enough columns.
     *
     * @param nextRow
     * @param column
     * @return
     */
    public String getTrimmedData(List<String> row, ColumnConfig column) {
        return getTrimmedData(row, column.getColumnNo());
    }

    /**
     * This method can be used to get the trimmed data from a data row from the
     * specified column. It can be used to avoid unmanaged break in case one line
     * does not have enough columns.
     *
     * @param nextRow
     * @param column
     * @return
     */
    public String getTrimmedData(List<String> row, int column) {
        if (row == null || row.size() <= column) {
            return "";
        }
        try {
            return trim(row.get(column));
        } catch (IndexOutOfBoundsException ie) {
            return "";
        }
    }

    /**
     * this method returns trimmed data in case it is not null.
     *
     * @param data
     * @return
     */
    public String trim(String data) {
        if (!isNullOrAbsent(data)) {
            // clean the NON-BREAK SPACEs: (needed in case someone has used Excel and the
            // "Alt-Shift Space option")
            data = replaceAll(data, "\u00A0", "");
            return data.trim();
        } else {
            return data;
        }
    }

    /**
     * Converts a date string into a Date object, in the form given, for example
     * dd/MM/yyyy. <b>NOTE:</b>
     *
     * @param date
     *            The <code>String</code> date to convert to
     *            <code>java.util.Date</code>
     * @return the date that the string represents.
     * @throws DateUtilException
     *             if the String cannot be parsed.
     */
    public static Date toDateFromString(String date, String pattern) throws ValidatorException {
        if (date == null) {
            return null;
        }
        try {
            return new SimpleDateFormat(pattern).parse(date);
        } catch (ParseException e) {
            throw new ValidatorException("Unable to parse the date supplied");
        }
    }

    /**
     * Converts a Date instance into a customised pattern format, using the
     * specified pattern. This should be used sparingly, use
     * {@link #formatDate(Locale, Date) formatDate} instead as it provides
     * internationalisation.
     *
     * @param date
     *            the Date to convert.
     * @param pattern
     *            the pattern to use to convert the Date.
     * @return the formatted Date.
     */
    public static String toString(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat(pattern).format(date);
    }

    /**
     * This method returns TRUE if the test is NULL or empty string.
     *
     * @param test
     * @return TRUE if the test is NULL or empty string
     */
    public static boolean isNullOrAbsent(String test) {
        return ((test == null) || test.length() == 0);
    }

    /**
     * Returns a string based on <code>text</code>, where all occurrences of
     * <code>toBeReplaced</code> in <code>text</code> have been replaced by
     * <code>toBeReplacedWith</code>.
     */
    public static String replaceAll(String text, String toBeReplaced, String toBeReplacedWith) {
        String returnValue = text;
        if (text != null && toBeReplacedWith != null && text.indexOf(toBeReplaced) != -1) {
            int i = 0;
            StringBuilder textBuf = new StringBuilder(text);
            int toBeReplacedLen = toBeReplaced.length();
            int toBeReplacedWithLen = toBeReplacedWith.length();
            while ((i = textBuf.toString().indexOf(toBeReplaced, i)) >= 0) {
                textBuf.replace(i, i + toBeReplacedLen, toBeReplacedWith);
                i += toBeReplacedWithLen;
            }
            returnValue = textBuf.toString();
        }
        return returnValue;
    }
}
