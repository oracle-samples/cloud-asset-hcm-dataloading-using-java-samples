/* Copyright © 2018, Oracle and/or its affiliates. All rights reserved.

The Universal Permissive License (UPL), Version 1.0
*/
package com.oracle.xmlns.adf.svc.types;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Conjunction.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Conjunction">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="And"/>
 *     &lt;enumeration value="Or"/>
 *     &lt;enumeration value="Not"/>
 *     &lt;enumeration value="AndNot"/>
 *     &lt;enumeration value="OrNot"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "Conjunction")
@XmlEnum
public enum Conjunction {

    @XmlEnumValue("And")
    AND("And"),
    @XmlEnumValue("Or")
    OR("Or"),
    @XmlEnumValue("Not")
    NOT("Not"),
    @XmlEnumValue("AndNot")
    AND_NOT("AndNot"),
    @XmlEnumValue("OrNot")
    OR_NOT("OrNot");
    private final String value;

    Conjunction(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Conjunction fromValue(String v) {
        for (Conjunction c: Conjunction.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
