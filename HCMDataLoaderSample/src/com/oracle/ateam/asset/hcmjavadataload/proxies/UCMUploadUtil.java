/* Copyright 2018, Oracle and/or its affiliates. All rights reserved.

The Universal Permissive License (UPL), Version 1.0
*/
package com.oracle.ateam.asset.hcmjavadataload.proxies;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.nio.charset.Charset;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import oracle.stellent.ridc.IdcClientException;

import oracle.ucm.idcws.client.UploadTool;
import oracle.ucm.idcws.client.UploadTool.UploadResults;
import oracle.ucm.idcws.client.bulk.UploadException;
import oracle.ucm.idcws.client.model.content.CheckinSource;
import oracle.ucm.idcws.client.model.content.TransferFile;
import oracle.ucm.idcws.client.model.response.CheckinResponse;

/**
 * This class provides methods to  create a new content item in Oracle WebCenter Content based on contents streamed from a local file.
 *
 *
 * The new methods use the Oracle preferred generic soap-based transfer utility (oracle.ucm.fa_genericclient_11.1.1.jar), which requires the Oracle JRF Web Service
 * supporting libraries and uses JAX/WS over HTTPS to communicate with the Oracle WebCenter Content Server.
 *
 * The original RIDC-based transfer utility used in a former version of this class (oracle.ucm.fa_client_11.1.1.jar)
 * is a feature-set Java library that encapsulates Oracle WebCenter Content RIDC and uses standard HTTPS to communicate with the Oracle WebCenter Content server.
 * Be aware that this form of the tool is impending deprecation in FA environments due to authentication obstacles which currently cannot be handled universally and programmatically.
 *
 * Therefore the method using RIDC-based transfer utility is now also deprecated in this class.
 *
 * The post below explains how to use GenericSoap Service. The service is over TLS encryption and support inline content as base64 or MTOM attachment.
 * http://www.ateam-oracle.com/fusion-applications-webcenter-content-integration-automating-file-importexport/
 */
public class UCMUploadUtil {
    private static final Logger LOGGER = Logger.getLogger(UCMUploadUtil.class.getName());

    UCMUploadUtil() {
        LOGGER.setLevel(Level.ALL);
    }

    /**
     * Download the file with the given documentId from UCM and save locally in the outputDir, using the filename on UCM
     * @param url for example, 'https://UpdateThisValue.com/idcws'
     * @param documentId
     * @param username
     * @param password
     * @param dSecurityGroup
     * @return
     * @throws IdcClientException
     */
    public static String uploadFileToUCMwithSOAP(String url, String username, String password, File uploadFile,
                                                 String identifier, String dDocName, String dDocTitle,
                                                 String dSecurityGroup, String dDocAccount,
                                                 String dDocType) throws Exception {
        List<String> coreArgs = new ArrayList<String>();
        coreArgs.add("url=" + url);
        coreArgs.add("policy=oracle/wss_username_token_over_ssl_client_policy");
        coreArgs.add("username=" + username);
        coreArgs.add("password=" + password);
        coreArgs.add("silent=false"); // minimal log output
        coreArgs.add("verbose=true"); // verbose log output
        List<String> argsList = new ArrayList<String>();
        argsList.addAll(coreArgs);
        argsList.add("threads=3");
        argsList.add("throwOnThreadException=false"); // run() will not throw an exception should a thread error
        String[] uploadArgs = argsList.toArray(new String[0]);
        UploadTool uploadTool = new UploadTool();
        // Setup the tool's initial configuration from the supplied arguments.
        boolean terminateEarly = uploadTool.setup(uploadArgs);
        if (terminateEarly) {
            throw new Exception("Error with the UCM UploadTool's initial configuration from the supplied arguments, please check arguments and try again.");
        }
        List<CheckinSource> items = new ArrayList<CheckinSource>();
        items.add(new LocalFileSource(identifier, dDocName, dDocTitle, dSecurityGroup, dDocAccount, dDocType,
                                      uploadFile.getAbsolutePath()));
        uploadTool.setCheckinItems(items);
        UploadResults uploadResults = uploadTool.run();
        if (uploadResults != null) {
            Map<Integer, Exception> failedCheckins = uploadResults.getAllFailedCheckinsKeyedByTaskNum();
            Map<Integer, UploadException> failedCheckinsDetailed = uploadResults.getFailedCheckinsKeyedByTaskNum();
            for (Map.Entry<Integer, Exception> entry : failedCheckins.entrySet()) {
                if (failedCheckinsDetailed.containsKey(entry.getKey())) {
                    UploadException e = failedCheckinsDetailed.get(entry.getKey());
                    LOGGER.info(String.format("Checkin with task number %d and identifier %d failed with message %s",
                                              e.getTaskNumber(), e.getIdentifier(), e.getMessage()));
                } else {
                    LOGGER.info(String.format("Checkin with task number %d failed with message %s ", entry.getKey(),
                                              entry.getValue().getMessage()));
                }
            }
            Map<Integer, CheckinResponse> successfulCheckins = uploadResults.getSuccessfulCheckinsKeyedByTaskNum();
            for (Map.Entry<Integer, CheckinResponse> entry : successfulCheckins.entrySet()) {
                CheckinResponse response = entry.getValue();
                LOGGER.info("Checkin with task number " + response.getTaskNumber() + " and identifier " +
                            response.getIdentifier() + " succeeded. dID=" + response.getDId() + " dName=" +
                            response.getDDocName());
            }
        }
        return dDocName; // dDocName is whats used by HDL Import
    }

    public static class LocalFileSource implements CheckinSource {
        private String identifier = null;
        private String dDocName = null;
        private String dDocTitle = null;
        private String dSecurityGroup = null;
        private String dDocAccount = null;
        private String dDocType = null;
        private TransferFile primaryFile = null;
        private String primaryFileLanguage = null;
        private String primaryFileCharacterSet = null;
        private TransferFile alternateFile = null;
        private String alternateFileLanguage = null;
        private String alternateFileCharacterSet = null;
        private static final byte[] s_static_alternate_file_bytes =
            "See Primary File".getBytes(Charset.forName("UTF-8"));

        public LocalFileSource(String identifier, String dDocName, String dDocTitle, String dSecurityGroup,
                               String dDocAccount, String dDocType, String filePath) throws IOException {
            super();
            this.identifier = identifier;
            this.dDocName = dDocName;
            this.dDocTitle = dDocTitle;
            this.dSecurityGroup = dSecurityGroup;
            this.dDocAccount = dDocAccount;
            this.dDocType = dDocType;
            this.primaryFile = new TransferFile(new File(filePath), "application/octet-stream");
        }

        @Override
        public String getIdentifier() {
            return identifier;
        }

        @Override
        public String getDDocName() {
            return dDocName;
        }

        @Override
        public String getDDocTitle() {
            return dDocTitle;
        }

        @Override
        public String getDSecurityGroup() {
            return dSecurityGroup;
        }

        @Override
        public String getDDocAccount() {
            return dDocAccount;
        }

        @Override
        public String getDDocType() {
            return dDocType;
        }

        @Override
        public TransferFile getPrimaryFile() {
            return primaryFile;
        }

        @Override
        public String getPrimaryFileLanguage() {
            return primaryFileLanguage;
        }

        @Override
        public String getPrimaryFileCharacterSet() {
            return primaryFileCharacterSet;
        }

        @Override
        public TransferFile getAlternateFile() {
            if (alternateFile == null) {
                alternateFile =
                    new TransferFile(new ByteArrayInputStream(s_static_alternate_file_bytes), "alternate.txt",
                                     s_static_alternate_file_bytes.length, "text/plain");
            }
            return alternateFile;
        }

        @Override
        public String getAlternateFileLanguage() {
            return alternateFileLanguage;
        }

        @Override
        public String getAlternateFileCharacterSet() {
            return alternateFileCharacterSet;
        }

        @Override
        public void closePrimaryFile() {
            if (primaryFile != null) {
                InputStream is = null;
                try {
                    is = primaryFile.getInputStream();
                } catch (IOException ioe) {
                    LOGGER.warning("Warning: Error occured when closing file,continuing  " + ioe.getLocalizedMessage());
                } finally {
                    if (is != null) {
                        try {
                            is.close();
                        } catch (Exception e) {
                            ; // this block intentionally left blank!
                        }
                    }
                }
            }
        }

        @Override
        public void closeAlternateFile() {
            // Does nothing
            LOGGER.info("Alternative File closed");
        }
    }
}
