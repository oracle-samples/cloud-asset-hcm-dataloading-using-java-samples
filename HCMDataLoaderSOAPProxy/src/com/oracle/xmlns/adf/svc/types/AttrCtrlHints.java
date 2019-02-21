/* Copyright © 2018, Oracle and/or its affiliates. All rights reserved.

The Universal Permissive License (UPL), Version 1.0
*/
package com.oracle.xmlns.adf.svc.types;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AttrCtrlHints complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AttrCtrlHints">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="attrName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ctrlHint" type="{http://xmlns.oracle.com/adf/svc/types/}CtrlHint" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AttrCtrlHints", propOrder = {
    "attrName",
    "ctrlHint"
})
public class AttrCtrlHints {

    @XmlElement(required = true)
    protected String attrName;
    @XmlElement(required = true)
    protected List<CtrlHint> ctrlHint;

    /**
     * Gets the value of the attrName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttrName() {
        return attrName;
    }

    /**
     * Sets the value of the attrName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttrName(String value) {
        this.attrName = value;
    }

    /**
     * Gets the value of the ctrlHint property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ctrlHint property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCtrlHint().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CtrlHint }
     * 
     * 
     */
    public List<CtrlHint> getCtrlHint() {
        if (ctrlHint == null) {
            ctrlHint = new ArrayList<CtrlHint>();
        }
        return this.ctrlHint;
    }

}
