/* Copyright © 2018, Oracle and/or its affiliates. All rights reserved.

The Universal Permissive License (UPL), Version 1.0
*//* Copyright © 2018, Oracle and/or its affiliates. All rights reserved.

The Universal Permissive License (UPL), Version 1.0
*/
package sdo.commonj;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;


/**
 * <p>Java class for BaseDataGraphType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BaseDataGraphType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="models" type="{commonj.sdo}ModelsType" minOccurs="0"/>
 *         &lt;element name="xsd" type="{commonj.sdo}XSDType" minOccurs="0"/>
 *         &lt;element name="changeSummary" type="{commonj.sdo}ChangeSummaryType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BaseDataGraphType", propOrder = {
    "models",
    "xsd",
    "changeSummary"
})
@XmlSeeAlso({
    DataGraphType.class
})
public abstract class BaseDataGraphType {

    @XmlElement(namespace = "")
    protected ModelsType models;
    @XmlElement(namespace = "")
    protected XSDType xsd;
    @XmlElement(namespace = "")
    protected ChangeSummaryType changeSummary;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    /**
     * Gets the value of the models property.
     * 
     * @return
     *     possible object is
     *     {@link ModelsType }
     *     
     */
    public ModelsType getModels() {
        return models;
    }

    /**
     * Sets the value of the models property.
     * 
     * @param value
     *     allowed object is
     *     {@link ModelsType }
     *     
     */
    public void setModels(ModelsType value) {
        this.models = value;
    }

    /**
     * Gets the value of the xsd property.
     * 
     * @return
     *     possible object is
     *     {@link XSDType }
     *     
     */
    public XSDType getXsd() {
        return xsd;
    }

    /**
     * Sets the value of the xsd property.
     * 
     * @param value
     *     allowed object is
     *     {@link XSDType }
     *     
     */
    public void setXsd(XSDType value) {
        this.xsd = value;
    }

    /**
     * Gets the value of the changeSummary property.
     * 
     * @return
     *     possible object is
     *     {@link ChangeSummaryType }
     *     
     */
    public ChangeSummaryType getChangeSummary() {
        return changeSummary;
    }

    /**
     * Sets the value of the changeSummary property.
     * 
     * @param value
     *     allowed object is
     *     {@link ChangeSummaryType }
     *     
     */
    public void setChangeSummary(ChangeSummaryType value) {
        this.changeSummary = value;
    }

    /**
     * Gets a map that contains attributes that aren't bound to any typed property on this class.
     * 
     * <p>
     * the map is keyed by the name of the attribute and 
     * the value is the string value of the attribute.
     * 
     * the map returned by this method is live, and you can add new attribute
     * by updating the map directly. Because of this design, there's no setter.
     * 
     * 
     * @return
     *     always non-null
     */
    public Map<QName, String> getOtherAttributes() {
        return otherAttributes;
    }

}
