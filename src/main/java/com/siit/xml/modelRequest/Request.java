//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.02.06 at 11:58:48 PM CET 
//


package com.siit.xml.modelRequest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for request complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="request">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="paperId" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *         &lt;element name="reviewerUsername" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "request", propOrder = {
    "paperId",
    "reviewerUsername"
})
public class Request {

    @XmlElement(required = true)
    protected Object paperId;
    @XmlElement(required = true)
    protected Object reviewerUsername;

    /**
     * Gets the value of the paperId property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getPaperId() {
        return paperId;
    }

    /**
     * Sets the value of the paperId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setPaperId(Object value) {
        this.paperId = value;
    }

    /**
     * Gets the value of the reviewerUsername property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getReviewerUsername() {
        return reviewerUsername;
    }

    /**
     * Sets the value of the reviewerUsername property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setReviewerUsername(Object value) {
        this.reviewerUsername = value;
    }

}
