//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.01.22 at 02:37:30 AM MSK 
//


package com.brest.bank.util;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for bankDepositReport complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="bankDepositReport">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="depositId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="depositName">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;pattern value="[A-Z][a-z]+[0-9]*"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="depositMinTerm">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *               &lt;minInclusive value="1"/>
 *               &lt;maxInclusive value="480"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="depositMinAmount">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *               &lt;minInclusive value="100"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="depositCurrency">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;pattern value="[A-Z]{3}"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="depositInterestRate">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *               &lt;minInclusive value="1"/>
 *               &lt;maxInclusive value="100"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="depositAddConditions">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;pattern value="[A-Z][a-z]+[0-9]*"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="depositorCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="depositorAmountSum" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="depositorAmountPlusSum" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="depositorAmountMinusSum" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "bankDepositReport", propOrder = {
    "depositId",
    "depositName",
    "depositMinTerm",
    "depositMinAmount",
    "depositCurrency",
    "depositInterestRate",
    "depositAddConditions",
    "depositorCount",
    "depositorAmountSum",
    "depositorAmountPlusSum",
    "depositorAmountMinusSum"
})
public class BankDepositReport {

    @XmlElement(required = true, type = Long.class, nillable = true)
    protected Long depositId;
    @XmlElement(required = true)
    protected String depositName;
    protected int depositMinTerm;
    protected int depositMinAmount;
    @XmlElement(required = true)
    protected String depositCurrency;
    protected int depositInterestRate;
    @XmlElement(required = true)
    protected String depositAddConditions;
    protected int depositorCount;
    protected int depositorAmountSum;
    protected int depositorAmountPlusSum;
    protected int depositorAmountMinusSum;

    /**
     * Gets the value of the depositId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getDepositId() {
        return depositId;
    }

    /**
     * Sets the value of the depositId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setDepositId(Long value) {
        this.depositId = value;
    }

    /**
     * Gets the value of the depositName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepositName() {
        return depositName;
    }

    /**
     * Sets the value of the depositName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepositName(String value) {
        this.depositName = value;
    }

    /**
     * Gets the value of the depositMinTerm property.
     * 
     */
    public int getDepositMinTerm() {
        return depositMinTerm;
    }

    /**
     * Sets the value of the depositMinTerm property.
     * 
     */
    public void setDepositMinTerm(int value) {
        this.depositMinTerm = value;
    }

    /**
     * Gets the value of the depositMinAmount property.
     * 
     */
    public int getDepositMinAmount() {
        return depositMinAmount;
    }

    /**
     * Sets the value of the depositMinAmount property.
     * 
     */
    public void setDepositMinAmount(int value) {
        this.depositMinAmount = value;
    }

    /**
     * Gets the value of the depositCurrency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepositCurrency() {
        return depositCurrency;
    }

    /**
     * Sets the value of the depositCurrency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepositCurrency(String value) {
        this.depositCurrency = value;
    }

    /**
     * Gets the value of the depositInterestRate property.
     * 
     */
    public int getDepositInterestRate() {
        return depositInterestRate;
    }

    /**
     * Sets the value of the depositInterestRate property.
     * 
     */
    public void setDepositInterestRate(int value) {
        this.depositInterestRate = value;
    }

    /**
     * Gets the value of the depositAddConditions property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepositAddConditions() {
        return depositAddConditions;
    }

    /**
     * Sets the value of the depositAddConditions property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepositAddConditions(String value) {
        this.depositAddConditions = value;
    }

    /**
     * Gets the value of the depositorCount property.
     * 
     */
    public int getDepositorCount() {
        return depositorCount;
    }

    /**
     * Sets the value of the depositorCount property.
     * 
     */
    public void setDepositorCount(int value) {
        this.depositorCount = value;
    }

    /**
     * Gets the value of the depositorAmountSum property.
     * 
     */
    public int getDepositorAmountSum() {
        return depositorAmountSum;
    }

    /**
     * Sets the value of the depositorAmountSum property.
     * 
     */
    public void setDepositorAmountSum(int value) {
        this.depositorAmountSum = value;
    }

    /**
     * Gets the value of the depositorAmountPlusSum property.
     * 
     */
    public int getDepositorAmountPlusSum() {
        return depositorAmountPlusSum;
    }

    /**
     * Sets the value of the depositorAmountPlusSum property.
     * 
     */
    public void setDepositorAmountPlusSum(int value) {
        this.depositorAmountPlusSum = value;
    }

    /**
     * Gets the value of the depositorAmountMinusSum property.
     * 
     */
    public int getDepositorAmountMinusSum() {
        return depositorAmountMinusSum;
    }

    /**
     * Sets the value of the depositorAmountMinusSum property.
     * 
     */
    public void setDepositorAmountMinusSum(int value) {
        this.depositorAmountMinusSum = value;
    }

}
