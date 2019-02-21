/* Copyright © 2018, Oracle and/or its affiliates. All rights reserved.

The Universal Permissive License (UPL), Version 1.0
*/
package com.oracle.xmlns.adf.svc.types;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.oracle.xmlns.adf.svc.types package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _FindCriteria_QNAME = new QName("http://xmlns.oracle.com/adf/svc/types/", "findCriteria");
    private final static QName _FindControl_QNAME = new QName("http://xmlns.oracle.com/adf/svc/types/", "findControl");
    private final static QName _ProcessControl_QNAME = new QName("http://xmlns.oracle.com/adf/svc/types/", "processControl");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.oracle.xmlns.adf.svc.types
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ServiceViewInfo }
     * 
     */
    public ServiceViewInfo createServiceViewInfo() {
        return new ServiceViewInfo();
    }

    /**
     * Create an instance of {@link ObjAttrHints }
     * 
     */
    public ObjAttrHints createObjAttrHints() {
        return new ObjAttrHints();
    }

    /**
     * Create an instance of {@link FindControl }
     * 
     */
    public FindControl createFindControl() {
        return new FindControl();
    }

    /**
     * Create an instance of {@link ProcessControl }
     * 
     */
    public ProcessControl createProcessControl() {
        return new ProcessControl();
    }

    /**
     * Create an instance of {@link FindCriteria }
     * 
     */
    public FindCriteria createFindCriteria() {
        return new FindCriteria();
    }

    /**
     * Create an instance of {@link ChildFindCriteria }
     * 
     */
    public ChildFindCriteria createChildFindCriteria() {
        return new ChildFindCriteria();
    }

    /**
     * Create an instance of {@link BigDecimalResult }
     * 
     */
    public BigDecimalResult createBigDecimalResult() {
        return new BigDecimalResult();
    }

    /**
     * Create an instance of {@link SortOrder }
     * 
     */
    public SortOrder createSortOrder() {
        return new SortOrder();
    }

    /**
     * Create an instance of {@link SortAttribute }
     * 
     */
    public SortAttribute createSortAttribute() {
        return new SortAttribute();
    }

    /**
     * Create an instance of {@link AmountType }
     * 
     */
    public AmountType createAmountType() {
        return new AmountType();
    }

    /**
     * Create an instance of {@link DataObjectResult }
     * 
     */
    public DataObjectResult createDataObjectResult() {
        return new DataObjectResult();
    }

    /**
     * Create an instance of {@link BooleanResult }
     * 
     */
    public BooleanResult createBooleanResult() {
        return new BooleanResult();
    }

    /**
     * Create an instance of {@link MeasureType }
     * 
     */
    public MeasureType createMeasureType() {
        return new MeasureType();
    }

    /**
     * Create an instance of {@link MethodResult }
     * 
     */
    public MethodResult createMethodResult() {
        return new MethodResult();
    }

    /**
     * Create an instance of {@link TimeResult }
     * 
     */
    public TimeResult createTimeResult() {
        return new TimeResult();
    }

    /**
     * Create an instance of {@link DateResult }
     * 
     */
    public DateResult createDateResult() {
        return new DateResult();
    }

    /**
     * Create an instance of {@link ViewCriteriaRow }
     * 
     */
    public ViewCriteriaRow createViewCriteriaRow() {
        return new ViewCriteriaRow();
    }

    /**
     * Create an instance of {@link BytesResult }
     * 
     */
    public BytesResult createBytesResult() {
        return new BytesResult();
    }

    /**
     * Create an instance of {@link CtrlHint }
     * 
     */
    public CtrlHint createCtrlHint() {
        return new CtrlHint();
    }

    /**
     * Create an instance of {@link DoubleResult }
     * 
     */
    public DoubleResult createDoubleResult() {
        return new DoubleResult();
    }

    /**
     * Create an instance of {@link ViewCriteriaItem }
     * 
     */
    public ViewCriteriaItem createViewCriteriaItem() {
        return new ViewCriteriaItem();
    }

    /**
     * Create an instance of {@link ShortResult }
     * 
     */
    public ShortResult createShortResult() {
        return new ShortResult();
    }

    /**
     * Create an instance of {@link BigIntegerResult }
     * 
     */
    public BigIntegerResult createBigIntegerResult() {
        return new BigIntegerResult();
    }

    /**
     * Create an instance of {@link DataHandlerResult }
     * 
     */
    public DataHandlerResult createDataHandlerResult() {
        return new DataHandlerResult();
    }

    /**
     * Create an instance of {@link IntegerResult }
     * 
     */
    public IntegerResult createIntegerResult() {
        return new IntegerResult();
    }

    /**
     * Create an instance of {@link FloatResult }
     * 
     */
    public FloatResult createFloatResult() {
        return new FloatResult();
    }

    /**
     * Create an instance of {@link StringResult }
     * 
     */
    public StringResult createStringResult() {
        return new StringResult();
    }

    /**
     * Create an instance of {@link ByteResult }
     * 
     */
    public ByteResult createByteResult() {
        return new ByteResult();
    }

    /**
     * Create an instance of {@link LongResult }
     * 
     */
    public LongResult createLongResult() {
        return new LongResult();
    }

    /**
     * Create an instance of {@link AttrCtrlHints }
     * 
     */
    public AttrCtrlHints createAttrCtrlHints() {
        return new AttrCtrlHints();
    }

    /**
     * Create an instance of {@link ViewCriteria }
     * 
     */
    public ViewCriteria createViewCriteria() {
        return new ViewCriteria();
    }

    /**
     * Create an instance of {@link TimestampResult }
     * 
     */
    public TimestampResult createTimestampResult() {
        return new TimestampResult();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindCriteria }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/adf/svc/types/", name = "findCriteria")
    public JAXBElement<FindCriteria> createFindCriteria(FindCriteria value) {
        return new JAXBElement<FindCriteria>(_FindCriteria_QNAME, FindCriteria.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindControl }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/adf/svc/types/", name = "findControl")
    public JAXBElement<FindControl> createFindControl(FindControl value) {
        return new JAXBElement<FindControl>(_FindControl_QNAME, FindControl.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProcessControl }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/adf/svc/types/", name = "processControl")
    public JAXBElement<ProcessControl> createProcessControl(ProcessControl value) {
        return new JAXBElement<ProcessControl>(_ProcessControl_QNAME, ProcessControl.class, null, value);
    }

}
