/* Copyright © 2018, Oracle and/or its affiliates. All rights reserved.

The Universal Permissive License (UPL), Version 1.0
*/
package com.oracle.xmlns.adf.svc.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ChildFindCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ChildFindCriteria">
 *   &lt;complexContent>
 *     &lt;extension base="{http://xmlns.oracle.com/adf/svc/types/}FindCriteria">
 *       &lt;sequence>
 *         &lt;element name="childAttrName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ChildFindCriteria", propOrder = {
    "childAttrName"
})
public class ChildFindCriteria
    extends FindCriteria
{

    @XmlElement(required = true)
    protected String childAttrName;

    /**
     * Gets the value of the childAttrName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChildAttrName() {
        return childAttrName;
    }

    /**
     * Sets the value of the childAttrName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChildAttrName(String value) {
        this.childAttrName = value;
    }

}
