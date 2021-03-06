//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.06.24 at 06:31:46 PM CEST 
//


package com.xml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for TPublication complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TPublication">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="basicInformations" type="{http://foo.bar}TBasicInformation"/>
 *         &lt;element name="paragraph" type="{http://foo.bar}TParagraph" maxOccurs="unbounded"/>
 *         &lt;element name="reference" type="{http://foo.bar}TReference" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="language" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="publicationId" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="status" default="reviewing">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="reviewing"/>
 *             &lt;enumeration value="reworking"/>
 *             &lt;enumeration value="withdrawn"/>
 *             &lt;enumeration value="rejected"/>
 *             &lt;enumeration value="accepted"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="created" use="required" type="{http://www.w3.org/2001/XMLSchema}date" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TPublication", propOrder = {
    "basicInformations",
    "paragraph",
    "reference"
})
public class TPublication {

    @XmlElement(required = true)
    protected TBasicInformation basicInformations;
    @XmlElement(required = true)
    protected List<TParagraph> paragraph;
    @XmlElement(required = true)
    protected List<TReference> reference;
    @XmlAttribute(name = "language")
    protected String language;
    @XmlAttribute(name = "publicationId", required = true)
    protected String publicationId;
    @XmlAttribute(name = "status")
    protected String status;
    @XmlAttribute(name = "created", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar created;

    /**
     * Gets the value of the basicInformations property.
     * 
     * @return
     *     possible object is
     *     {@link TBasicInformation }
     *     
     */
    public TBasicInformation getBasicInformations() {
        return basicInformations;
    }

    /**
     * Sets the value of the basicInformations property.
     * 
     * @param value
     *     allowed object is
     *     {@link TBasicInformation }
     *     
     */
    public void setBasicInformations(TBasicInformation value) {
        this.basicInformations = value;
    }

    /**
     * Gets the value of the paragraph property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the paragraph property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParagraph().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TParagraph }
     * 
     * 
     */
    public List<TParagraph> getParagraph() {
        if (paragraph == null) {
            paragraph = new ArrayList<TParagraph>();
        }
        return this.paragraph;
    }

    /**
     * Gets the value of the reference property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the reference property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReference().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TReference }
     * 
     * 
     */
    public List<TReference> getReference() {
        if (reference == null) {
            reference = new ArrayList<TReference>();
        }
        return this.reference;
    }

    /**
     * Gets the value of the language property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Sets the value of the language property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLanguage(String value) {
        this.language = value;
    }

    /**
     * Gets the value of the publicationId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPublicationId() {
        return publicationId;
    }

    /**
     * Sets the value of the publicationId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPublicationId(String value) {
        this.publicationId = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        if (status == null) {
            return "reviewing";
        } else {
            return status;
        }
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the created property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreated() {
        return created;
    }

    /**
     * Sets the value of the created property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreated(XMLGregorianCalendar value) {
        this.created = value;
    }

}
