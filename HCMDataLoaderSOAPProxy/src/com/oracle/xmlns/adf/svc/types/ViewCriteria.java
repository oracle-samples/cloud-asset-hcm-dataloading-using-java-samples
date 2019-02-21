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
 * <p>Java class for ViewCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ViewCriteria">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="conjunction" type="{http://xmlns.oracle.com/adf/svc/types/}Conjunction" minOccurs="0"/>
 *         &lt;element name="group" type="{http://xmlns.oracle.com/adf/svc/types/}ViewCriteriaRow" maxOccurs="unbounded"/>
 *         &lt;element name="nested" type="{http://xmlns.oracle.com/adf/svc/types/}ViewCriteria" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ViewCriteria", propOrder = {
    "conjunction",
    "group",
    "nested"
})
public class ViewCriteria {

    protected Conjunction conjunction;
    @XmlElement(required = true)
    protected List<ViewCriteriaRow> group;
    protected List<ViewCriteria> nested;

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
     * Gets the value of the group property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the group property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGroup().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ViewCriteriaRow }
     * 
     * 
     */
    public List<ViewCriteriaRow> getGroup() {
        if (group == null) {
            group = new ArrayList<ViewCriteriaRow>();
        }
        return this.group;
    }

    /**
     * Gets the value of the nested property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the nested property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNested().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ViewCriteria }
     * 
     * 
     */
    public List<ViewCriteria> getNested() {
        if (nested == null) {
            nested = new ArrayList<ViewCriteria>();
        }
        return this.nested;
    }

}
