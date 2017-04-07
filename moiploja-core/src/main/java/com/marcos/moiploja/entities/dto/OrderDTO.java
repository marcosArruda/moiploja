/**
 *
 */
package com.marcos.moiploja.entities.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * @author Marcos
 */
public class OrderDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "Nome é obrigatório")
    private String firstName;
    @NotEmpty(message = "Sobrenome é obrigatório")
    private String lastName;
    @NotEmpty(message = "Email é obrigatório")
    @Email
    private String emailId;
    @NotEmpty(message = "Telefone é obrigatório")
    private String phone;

    @NotEmpty(message = "Endereço1 é obrigatório")
    private String addressLine1;
    private String addressLine2;
    @NotEmpty(message = "Cidade é obrigatório")
    private String city;
    @NotEmpty(message = "Estado é obrigatório")
    private String state;
    @NotEmpty(message = "CEP é obrigatório")
    private String zipCode;
    @NotEmpty(message = "País é obrigatório")
    private String country;

    @NotEmpty(message = "Nome é obrigatório")
    private String billingFirstName;
    @NotEmpty(message = "Sobrenome é obrigatório")
    private String billingLastName;
    @NotEmpty(message = "Endereço1 é obrigatório")
    private String billingAddressLine1;
    private String billingAddressLine2;
    @NotEmpty(message = "Cidade é obrigatório")
    private String billingCity;
    @NotEmpty(message = "Estado é obrigatório")
    private String billingState;
    @NotEmpty(message = "CEP é obrigatório")
    private String billingZipCode;
    @NotEmpty(message = "Country é obrigatório")
    private String billingCountry;

    @NotEmpty(message = "Número do Cartão de Crédito é obrigatório")
    private String ccNumber;
    @NotEmpty(message = "CVV é obrigatório")
    private String cvv;

    private String ccHash;



    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCcNumber() {
        return ccNumber;
    }

    public void setCcNumber(String ccNumber) {
        this.ccNumber = ccNumber;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getBillingAddressLine1() {
        return billingAddressLine1;
    }

    public void setBillingAddressLine1(String billingAddressLine1) {
        this.billingAddressLine1 = billingAddressLine1;
    }

    public String getBillingAddressLine2() {
        return billingAddressLine2;
    }

    public void setBillingAddressLine2(String billingAddressLine2) {
        this.billingAddressLine2 = billingAddressLine2;
    }

    public String getBillingCity() {
        return billingCity;
    }

    public void setBillingCity(String billingCity) {
        this.billingCity = billingCity;
    }

    public String getBillingState() {
        return billingState;
    }

    public void setBillingState(String billingState) {
        this.billingState = billingState;
    }

    public String getBillingZipCode() {
        return billingZipCode;
    }

    public void setBillingZipCode(String billingZipCode) {
        this.billingZipCode = billingZipCode;
    }

    public String getBillingCountry() {
        return billingCountry;
    }

    public void setBillingCountry(String billingCountry) {
        this.billingCountry = billingCountry;
    }

    public String getBillingFirstName() {
        return billingFirstName;
    }

    public void setBillingFirstName(String billingFirstName) {
        this.billingFirstName = billingFirstName;
    }

    public String getBillingLastName() {
        return billingLastName;
    }

    public void setBillingLastName(String billingLastName) {
        this.billingLastName = billingLastName;
    }

    public String getCcHash() {
        return ccHash;
    }

    public void setCcHash(String ccHash) {
        this.ccHash = ccHash;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder()//
                .append("OrderDTO [")//
                .append("firstName=\"")//
                .append(firstName + "\"")//
                .append(",lastName=\"")//
                .append(lastName + "\"")//
                .append(",emailId=\"")//
                .append(emailId + "\"")//
                .append(",phone=\"")//
                .append(phone + "\"")//
                .append(",addressLine1=\"")//
                .append(addressLine1 + "\"")//
                .append(",addressLine2=\"")//
                .append(addressLine2 + "\"")//
                .append(",city=\"")//
                .append(city + "\"")//
                .append(",state=\"")//
                .append(state + "\"")//
                .append(",zipCode=\"")//
                .append(zipCode + "\"")//
                .append(",country=\"")//
                .append(country + "\"")//
                .append(",billingFirstName=\"")//
                .append(billingFirstName + "\"")//
                .append(",billingLastName=\"")//
                .append(billingLastName + "\"")//
                .append(",billingAddressLine1=\"")//
                .append(billingAddressLine1 + "\"")//
                .append(",billingAddressLine2=\"")//
                .append(billingAddressLine2 + "\"")//
                .append(",billingCity=\"")//
                .append(billingCity + "\"")//
                .append(",billingState=\"")//
                .append(billingState + "\"")//
                .append(",billingZipCode=\"")//
                .append(billingZipCode + "\"")//
                .append(",billingCountry=\"")//
                .append(billingCountry + "\"")//
                .append(",ccNumber=\"")//
                .append(ccNumber + "\"")//
                .append(",cvv=\"")//
                .append(cvv + "\"")//
                .append(",ccHash=\"")//
                .append(ccHash + "\"")//
                .append("]");
        return builder.toString();
    }
}
