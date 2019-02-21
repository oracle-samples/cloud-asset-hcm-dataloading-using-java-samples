/* Copyright © 2018, Oracle and/or its affiliates. All rights reserved.

The Universal Permissive License (UPL), Version 1.0
*/
package com.oracle.xmlns.adf.svc.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProcessControl complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProcessControl">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="returnMode" type="{http://xmlns.oracle.com/adf/svc/types/}ReturnMode" minOccurs="0"/>
 *         &lt;element name="exceptionReturnMode" type="{http://xmlns.oracle.com/adf/svc/types/}ReturnMode" minOccurs="0"/>
 *         &lt;element name="partialFailureAllowed" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProcessControl", propOrder = {
    "returnMode",
    "exceptionReturnMode",
    "partialFailureAllowed"
})
public class ProcessControl {

    protected ReturnMode returnMode;
    protected ReturnMode exceptionReturnMode;
    @XmlElement(defaultValue = "false")
    protected boolean partialFailureAllowed;

    /**
     * Gets the value of the returnMode property.
     * 
     * @return
     *     possible object is
     *     {@link ReturnMode }
     *     
     */
    public ReturnMode getReturnMode() {
        return returnMode;
    }

    /**
     * Sets the value of the returnMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReturnMode }
     *     
     */
    public void setReturnMode(ReturnMode value) {
        this.returnMode = value;
    }

    /**
     * Gets the value of the exceptionReturnMode property.
     * 
     * @return
     *     possible object is
     *     {@link ReturnMode }
     *     
     */
    public ReturnMode getExceptionReturnMode() {
        return exceptionReturnMode;
    }

    /**
     * Sets the value of the exceptionReturnMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReturnMode }
     *     
     */
    public void setExceptionReturnMode(ReturnMode value) {
        this.exceptionReturnMode = value;
    }

    /**
     * Gets the value of the partialFailureAllowed property.
     * 
     */
    public boolean isPartialFailureAllowed() {
        return partialFailureAllowed;
    }

    /**
     * Sets the value of the partialFailureAllowed property.
     * 
     */
    public void setPartialFailureAllowed(boolean value) {
        this.partialFailureAllowed = value;
    }

}
