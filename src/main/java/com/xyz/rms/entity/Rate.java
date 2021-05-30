package com.xyz.rms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Rate Entity which represents Rate Table in the DB
 *
 * @author shuhaibkv01
 * @version 11
 * @since 2021
 */
@Entity
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @Column(name = "rate_id")
    @GenericGenerator(
            name = "native",
            strategy = "native"
    )
    private long rateId;

    @Column(name = "rate_description")
    @NotNull
    private String rateDescription;

    @Column(name = "rate_effective_date")
    private LocalDate rateEffectiveDate;

    @Column(name = "rate_expiration_date")
    private LocalDate rateExpirationDate;

    @Column(name = "amount")
    private int amount;

    public Rate() {

    }

    /**
     * Constructor for creating objects with initialization
     * @param rateId
     * @param rateDescription
     * @param rateEffectiveDate
     * @param rateExpirationDate
     * @param amount
     */
    public Rate(long rateId, String rateDescription, LocalDate rateEffectiveDate, LocalDate rateExpirationDate, int amount) {
        this.rateId = rateId;
        this.rateDescription = rateDescription;
        this.rateEffectiveDate = rateEffectiveDate;
        this.rateExpirationDate = rateExpirationDate;
        this.amount = amount;
    }

    /**
     * @return Gets the value of rateId and returns rateId
     */
    public long getRateId() {
        return rateId;
    }

    /**
     * Sets the rateId
     * You can use getRateId() to get the value of rateId
     */
    public void setRateId(long rateId) {
        this.rateId = rateId;
    }

    /**
     * @return Gets the value of rateDescription and returns rateDescription
     */
    public String getRateDescription() {
        return rateDescription;
    }

    /**
     * Sets the rateDescription
     * You can use getRateDescription() to get the value of rateDescription
     */
    public void setRateDescription(String rateDescription) {
        this.rateDescription = rateDescription;
    }

    /**
     * @return Gets the value of rateEffectiveDate and returns rateEffectiveDate
     */
    public LocalDate getRateEffectiveDate() {
        return rateEffectiveDate;
    }

    /**
     * Sets the rateEffectiveDate
     * You can use getRateEffectiveDate() to get the value of rateEffectiveDate
     */
    public void setRateEffectiveDate(LocalDate rateEffectiveDate) {
        this.rateEffectiveDate = rateEffectiveDate;
    }

    /**
     * @return Gets the value of rateExpirationDate and returns rateExpirationDate
     */
    public LocalDate getRateExpirationDate() {
        return rateExpirationDate;
    }

    /**
     * Sets the rateExpirationDate
     * You can use getRateExpirationDate() to get the value of rateExpirationDate
     */
    public void setRateExpirationDate(LocalDate rateExpirationDate) {
        this.rateExpirationDate = rateExpirationDate;
    }

    /**
     * @return Gets the value of amount and returns amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Sets the amount
     * You can use getAmount() to get the value of amount
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }
}
