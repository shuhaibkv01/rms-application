package com.xyz.rms.response;

/**
 * SurchargeDetails class is having SurCharge
 *
 * @author shuhaibkv01
 * @version 11
 * @since 2021
 */
public class SurchargeDetails {

    private String status;

    public SurchargeDetails() {

    }

    public SurchargeDetails(String status) {
        this.status = status;
    }

    /**
     * @return Gets the value of status and returns status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status
     * You can use getStatus() to get the value of status
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
