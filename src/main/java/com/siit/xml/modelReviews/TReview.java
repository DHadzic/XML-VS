//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.02.07 at 01:13:41 AM CET 
//


package com.siit.xml.modelReviews;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TReview complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TReview">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authors" type="{http://localhost:8080/Reviews}TAuthors"/>
 *         &lt;element name="comment" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="rateSubject">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="1"/>
 *               &lt;enumeration value="2"/>
 *               &lt;enumeration value="3"/>
 *               &lt;enumeration value="4"/>
 *               &lt;enumeration value="5"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="rateReadability">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="1"/>
 *               &lt;enumeration value="2"/>
 *               &lt;enumeration value="3"/>
 *               &lt;enumeration value="4"/>
 *               &lt;enumeration value="5"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="rateOriginality">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="1"/>
 *               &lt;enumeration value="2"/>
 *               &lt;enumeration value="3"/>
 *               &lt;enumeration value="4"/>
 *               &lt;enumeration value="5"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="reviewedBy" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="author"/>
 *             &lt;enumeration value="reviewer"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="paperId" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TReview", propOrder = {
    "authors",
    "comment",
    "rateSubject",
    "rateReadability",
    "rateOriginality"
})
public class TReview {

    @XmlElement(required = true)
    protected TAuthors authors;
    @XmlElement(required = true)
    protected String comment;
    @XmlElement(required = true)
    protected String rateSubject;
    @XmlElement(required = true)
    protected String rateReadability;
    @XmlElement(required = true)
    protected String rateOriginality;
    @XmlAttribute(name = "reviewedBy", required = true)
    protected String reviewedBy;
    @XmlAttribute(name = "paperId", required = true)
    protected String paperId;

    /**
     * Gets the value of the authors property.
     * 
     * @return
     *     possible object is
     *     {@link TAuthors }
     *     
     */
    public TAuthors getAuthors() {
        return authors;
    }

    /**
     * Sets the value of the authors property.
     * 
     * @param value
     *     allowed object is
     *     {@link TAuthors }
     *     
     */
    public void setAuthors(TAuthors value) {
        this.authors = value;
    }

    /**
     * Gets the value of the comment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the value of the comment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComment(String value) {
        this.comment = value;
    }

    /**
     * Gets the value of the rateSubject property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRateSubject() {
        return rateSubject;
    }

    /**
     * Sets the value of the rateSubject property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRateSubject(String value) {
        this.rateSubject = value;
    }

    /**
     * Gets the value of the rateReadability property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRateReadability() {
        return rateReadability;
    }

    /**
     * Sets the value of the rateReadability property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRateReadability(String value) {
        this.rateReadability = value;
    }

    /**
     * Gets the value of the rateOriginality property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRateOriginality() {
        return rateOriginality;
    }

    /**
     * Sets the value of the rateOriginality property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRateOriginality(String value) {
        this.rateOriginality = value;
    }

    /**
     * Gets the value of the reviewedBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReviewedBy() {
        return reviewedBy;
    }

    /**
     * Sets the value of the reviewedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReviewedBy(String value) {
        this.reviewedBy = value;
    }

    /**
     * Gets the value of the paperId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaperId() {
        return paperId;
    }

    /**
     * Sets the value of the paperId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaperId(String value) {
        this.paperId = value;
    }

}
