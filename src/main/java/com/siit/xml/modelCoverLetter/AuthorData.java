//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.02.06 at 05:30:02 AM CET 
//


package com.siit.xml.modelCoverLetter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AuthorData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AuthorData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authorsName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="authorsEmail" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *       &lt;attribute name="authorsPhone" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="authorsAddress" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AuthorData", propOrder = {
    "authorsName",
    "authorsEmail"
})
public class AuthorData {

    @XmlElement(required = true)
    protected String authorsName;
    @XmlElement(required = true)
    protected String authorsEmail;
    @XmlAttribute(name = "authorsPhone")
    protected String authorsPhone;
    @XmlAttribute(name = "authorsAddress")
    protected String authorsAddress;

    /**
     * Gets the value of the authorsName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthorsName() {
        return authorsName;
    }

    /**
     * Sets the value of the authorsName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthorsName(String value) {
        this.authorsName = value;
    }

    /**
     * Gets the value of the authorsEmail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthorsEmail() {
        return authorsEmail;
    }

    /**
     * Sets the value of the authorsEmail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthorsEmail(String value) {
        this.authorsEmail = value;
    }

    /**
     * Gets the value of the authorsPhone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthorsPhone() {
        return authorsPhone;
    }

    /**
     * Sets the value of the authorsPhone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthorsPhone(String value) {
        this.authorsPhone = value;
    }

    /**
     * Gets the value of the authorsAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthorsAddress() {
        return authorsAddress;
    }

    /**
     * Sets the value of the authorsAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthorsAddress(String value) {
        this.authorsAddress = value;
    }

}
