//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.02.18 at 11:04:04 PM MSK 
//


package com.brest.bank.util;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="bankDepositor" type="{http://bank.brest.com/soap}bankDepositor"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "bankDepositor"
})
@XmlRootElement(name = "getBankDepositorByIdResponse")
public class GetBankDepositorByIdResponse {

    @XmlElement(required = true)
    protected BankDepositor bankDepositor;

    /**
     * Gets the value of the bankDepositor property.
     * 
     * @return
     *     possible object is
     *     {@link BankDepositor }
     *     
     */
    public BankDepositor getBankDepositor() {
        return bankDepositor;
    }

    /**
     * Sets the value of the bankDepositor property.
     * 
     * @param value
     *     allowed object is
     *     {@link BankDepositor }
     *     
     */
    public void setBankDepositor(BankDepositor value) {
        this.bankDepositor = value;
    }

}
