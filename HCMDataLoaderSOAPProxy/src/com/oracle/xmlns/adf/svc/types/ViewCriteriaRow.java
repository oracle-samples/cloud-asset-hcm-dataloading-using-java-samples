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
 * <p>Java class for ViewCriteriaRow complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ViewCriteriaRow">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="conjunction" type="{http://xmlns.oracle.com/adf/svc/types/}Conjunction" minOccurs="0"/>
 *         &lt;element name="upperCaseCompare" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="item" type="{http://xmlns.oracle.com/adf/svc/types/}ViewCriteriaItem" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ViewCriteriaRow", propOrder = {
    "conjunction",
    "upperCaseCompare",
    "item"
})
public class ViewCriteriaRow {

    protected Conjunction conjunction;
    @XmlElement(defaultValue = "false")
    protected boolean upperCaseCompare;
    @XmlElement(required = true)
    protected List<ViewCriteriaItem> item;

    /**
     * Gets the value of the conjunction property.
     * 
     * @return
     *     possible object is
     *     {@link Conjunction }
     *     
     */
    public Conjunction getConjunction() {
        return conjunction;
    }

    /**
     * Sets the value of the conjunction property.
     * 
     * @param value
     *     allowed object is
     *     {@link Conjunction }
     *     
     */
    public void setConjunction(Conjunction value) {
        this.conjunction = value;
    }

    /**
     * Gets the value of the upperCaseCompare property.
     * 
     */
    public boolean isUpperCaseCompare() {
        return upperCaseCompare;
    }

    /**
     * Sets the value of the upperCaseCompare property.
     * 
     */
    public void setUpperCaseCompare(boolean value) {
        this.upperCaseCompare = value;
    }

    /**
     * Gets the value of the item property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the item property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ViewCriteriaItem }
     * 
     * 
     */
    public List<ViewCriteriaItem> getItem() {
        if (item == null) {
            item = new ArrayList<ViewCriteriaItem>();
        }
        return this.item;
    }

}
