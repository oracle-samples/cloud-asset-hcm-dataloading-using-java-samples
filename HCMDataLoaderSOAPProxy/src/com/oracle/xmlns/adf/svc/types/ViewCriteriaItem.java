/* Copyright © 2018, Oracle and/or its affiliates. All rights reserved.

The Universal Permissive License (UPL), Version 1.0
*/
package com.oracle.xmlns.adf.svc.types;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ViewCriteriaItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ViewCriteriaItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="conjunction" type="{http://xmlns.oracle.com/adf/svc/types/}Conjunction" minOccurs="0"/>
 *         &lt;element name="upperCaseCompare" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="attribute" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="operator" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;choice>
 *           &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element name="nested" type="{http://xmlns.oracle.com/adf/svc/types/}ViewCriteria"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ViewCriteriaItem", propOrder = {
    "conjunction",
    "upperCaseCompare",
    "attribute",
    "operator",
    "value",
    "nested"
})
public class ViewCriteriaItem {

    protected Conjunction conjunction;
    @XmlElement(defaultValue = "false")
    protected boolean upperCaseCompare;
    @XmlElement(required = true)
    protected String attribute;
    @XmlElement(required = true)
    protected String operator;
    @XmlSchemaType(name = "anySimpleType")
    protected List<Object> value;
    protected ViewCriteria nested;

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
     * Gets the value of the attribute property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttribute() {
        return attribute;
    }

    /**
     * Sets the value of the attribute property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttribute(String value) {
        this.attribute = value;
    }

    /**
     * Gets the value of the operator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperator() {
        return operator;
    }

    /**
     * Sets the value of the operator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperator(String value) {
        this.operator = value;
    }

    /**
     * Gets the value of the value property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the value property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getValue().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Object }
     * 
     * 
     */
    public List<Object> getValue() {
        if (value == null) {
            value = new ArrayList<Object>();
        }
        return this.value;
    }

    /**
     * Gets the value of the nested property.
     * 
     * @return
     *     possible object is
     *     {@link ViewCriteria }
     *     
     */
    public ViewCriteria getNested() {
        return nested;
    }

    /**
     * Sets the value of the nested property.
     * 
     * @param value
     *     allowed object is
     *     {@link ViewCriteria }
     *     
     */
    public void setNested(ViewCriteria value) {
        this.nested = value;
    }

}
