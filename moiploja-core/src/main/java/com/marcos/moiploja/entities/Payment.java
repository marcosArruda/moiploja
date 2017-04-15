/**
 *
 */
package com.marcos.moiploja.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Marcos
 */
@Entity
@Table(name = "payments")
public class Payment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "cc_number")
    private String ccNumber;
    private String cvv;
    private BigDecimal amount;
    @Column(name = "cc_hash", nullable = true, unique = false, length = 2000)
    private String ccHash;

    @Column(name="moip_id", nullable = true, unique = true)
    private String moipId;

    public Payment(){}
    public Payment(Integer id, String ccHash){
        this.id = id;
        this.ccHash = ccHash;
    }
    public Payment(Integer id, String ccHash, BigDecimal amount){
        this.id = id;
        this.ccHash = ccHash;
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCcHash() {
        return ccHash;
    }

    public void setCcHash(String ccHash) {
        this.ccHash = ccHash;
    }

    public String getMoipId() {
        return moipId;
    }

    public void setMoipId(String moipId) {
        this.moipId = moipId;
    }
}
