/* Copyright 2018, Oracle and/or its affiliates. All rights reserved.

The Universal Permissive License (UPL), Version 1.0
*/
package com.oracle.ateam.asset.hcmjavadataload.proxies;


import com.oracle.ateam.proxy.hcmloader.HCMDataLoader;
import com.oracle.ateam.proxy.hcmloader.HCMDataLoader_Service;
import com.oracle.ateam.proxy.hcmloader.ServiceException;

import java.io.IOException;
import java.io.StringReader;

import java.net.MalformedURLException;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.ws.BindingProvider;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import weblogic.wsee.jws.jaxws.owsm.SecurityPolicyFeature;


public class HCMDataLoaderUtil {
    private static final Logger LOGGER = Logger.getLogger(HCMDataLoaderUtil.class.getName());

    HCMDataLoaderUtil() {
        LOGGER.setLevel(Level.ALL);
    }

    /**
     * This is using the WebServices via the generated classed with wsdl see:
     * http://help.eclipse.org/kepler/index.jsp?topic=%2Forg.eclipse.jst.ws.cxf.doc.user%2Ftasks%2Fcreate_client.html
     *
     * @param contentId
     * @return
     * @throws ServiceException
     * @throws MalformedURLException
     */
    public static String loadDataHDL(String url, String username, String password, String keystore,
                                     String contentId) throws ServiceException, MalformedURLException {
        LOGGER.info("loadDataHDL: import contentId: " + contentId + " with hcmDataLoaderServiceLocation: " + url);
        HCMDataLoader hcmDataLoaderService =
            HCMDataLoaderUtil.getHCMDataLoaderService(url, username, password, keystore);
        String parameters = "";
        try {
            return hcmDataLoaderService.importAndLoadData(contentId, parameters);
        } catch (ServiceException e) {
            LOGGER.severe("Error occurred during importAndLoad call  ..." + e.getLocalizedMessage());
            throw e;
        }
    }

    public static String invokeGetDataSetStatus(String url, String username, String password, String keystore,
                                                Long processId, String getDataSetStatusDelay) throws ServiceException,
                                                                                                     ParserConfigurationException,
                                                                                                     SAXException,
                                                                                                     IOException,
                                                                                                     InterruptedException {
        HCMDataLoader hcmLoaderService = getHCMDataLoaderService(url, username, password, keystore);
        String response = hcmLoaderService.getDataSetStatus("ProcessId=" + processId);
        // Check status of Load
        String loadStatus = getLoadStatusFromResponse(response);
        if ("COMPLETED".equals(loadStatus) || "ERROR".equals(loadStatus)) {
            // Load completed or error, return
            LOGGER.info("Load completed with status: " + loadStatus);
            return response; // Return the entire payload for the logging file
        } else {
            try {
                LOGGER.info("Waiting for " + getDataSetStatusDelay + " secs, to recalculate the status ... ");
                Thread.sleep(Long.parseLong(getDataSetStatusDelay));
                response =
                    invokeGetDataSetStatus(url, username, password, keystore, processId,
                                           getDataSetStatusDelay); // Recursive
                // Loop, waiting for load to terminate
                return response;
            } catch (InterruptedException e) {
                System.err.println("\n\nException while thread waiting for report status....Program Exit");
                throw e;
            }
        }
    }

    public static String getLoadStatusFromResponse(String xmlResult) throws ParserConfigurationException, SAXException,
                                                                            IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new StringReader(xmlResult)));
        NodeList flowList = document.getElementsByTagName("DATA_SET_STATUS");
        NodeList childList;
        for (int i = 0; i < flowList.getLength(); i++) {
            childList = flowList.item(i).getChildNodes();
            for (int j = 0; j < childList.getLength(); j++) {
                Node childNode = childList.item(j);
                if ("DATA_SET".equals(childNode.getNodeName())) {
                    NodeList dataSeteChildNodes = childNode.getChildNodes();
                    for (int k = 0; k < dataSeteChildNodes.getLength(); k++) {
                        Node childDSNode = dataSeteChildNodes.item(k);
                        if ("STATUS".equals(childDSNode.getNodeName())) {
                            String status = dataSeteChildNodes.item(k)
                                                              .getTextContent()
                                                              .trim();
                            LOGGER.info("Current Status of the Data Set " + dataSeteChildNodes.item(k)
                                                                                              .getTextContent()
                                                                                              .trim());
                            return status;
                        }
                    }
                }
            }
        }
        return "ERROR";
    }

    public static HCMDataLoader getHCMDataLoaderService(String url, String username, String password,
                                                        String keystore) throws MalformedURLException {
        HCMDataLoader_Service hcmLoaderService = new HCMDataLoader_Service();
        // Depending on your system setup you may need to use a different policy
        // e.g.
        // 		oracle/wss_username_token_over_ssl_client_policy
        // 		oracle/wss11_username_token_with_message_protection_client_policy
        //
        SecurityPolicyFeature[] securityFeatures = new SecurityPolicyFeature[] {
            new SecurityPolicyFeature("oracle/wss_username_token_over_ssl_client_policy") };
        HCMDataLoader hcmDataLoader = hcmLoaderService.getHCMDataLoaderSoapHttpPort(securityFeatures);
        BindingProvider wsbp = (BindingProvider) hcmDataLoader;
        Map<String, Object> requestContext = wsbp.getRequestContext();
        requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url);
        requestContext.put(BindingProvider.USERNAME_PROPERTY, username);
        requestContext.put(BindingProvider.PASSWORD_PROPERTY, password);
        return hcmDataLoader;
    }
}
