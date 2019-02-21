/* Copyright © 2018, Oracle and/or its affiliates. All rights reserved.

The Universal Permissive License (UPL), Version 1.0
*/
package com.oracle.xmlns.adf.svc.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FindControl complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FindControl">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="retrieveAllTranslations" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FindControl", propOrder = {
    "retrieveAllTranslations"
})
public class FindControl {

    @XmlElement(defaultValue = "false")
    protected boolean retrieveAllTranslations;

    /**
     * Gets the value of the retrieveAllTranslations property.
     * 
     */
    public boolean isRetrieveAllTranslations() {
        return retrieveAllTranslations;
    }

    /**
     * Sets the value of the retrieveAllTranslations property.
     * 
     */
    public void setRetrieveAllTranslations(boolean value) {
        this.retrieveAllTranslations = value;
    }

}
