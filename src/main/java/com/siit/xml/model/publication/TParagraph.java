//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.02.06 at 07:22:54 PM CET 
//


package com.siit.xml.model.publication;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TParagraph complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TParagraph">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="paragraphTitle" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;choice maxOccurs="unbounded">
 *           &lt;element name="text" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *           &lt;element name="citation" type="{http://foo.bar}TCitation"/>
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
@XmlType(name = "TParagraph", propOrder = {
    "paragraphTitle",
    "textOrCitation"
})
public class TParagraph {

    @XmlElement(required = true)
    protected String paragraphTitle;
    @XmlElements({
        @XmlElement(name = "text", type = String.class),
        @XmlElement(name = "citation", type = TCitation.class)
    })
    protected List<Object> textOrCitation;

    /**
     * Gets the value of the paragraphTitle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParagraphTitle() {
        return paragraphTitle;
    }

    /**
     * Sets the value of the paragraphTitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParagraphTitle(String value) {
        this.paragraphTitle = value;
    }

    /**
     * Gets the value of the textOrCitation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the textOrCitation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTextOrCitation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * {@link TCitation }
     * 
     * 
     */
    public List<Object> getTextOrCitation() {
        if (textOrCitation == null) {
            textOrCitation = new ArrayList<Object>();
        }
        return this.textOrCitation;
    }

}
