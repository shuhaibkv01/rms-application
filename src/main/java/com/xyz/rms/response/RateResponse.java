package com.xyz.rms.response;

import com.xyz.rms.dto.RateDTO;

/**
 * RateResponse class with rate and surcharge details.
 *
 * @author shuhaibkv01
 * @version 11
 * @since 2021
 */
public class RateResponse {

    private RateDTO rateDTO;

    private SurchargeDetails surChargeDetails;

    public RateResponse(RateDTO rateDTO, SurchargeDetails surChargeDetails) {
        this.rateDTO = rateDTO;
        this.surChargeDetails = surChargeDetails;
    }

    /**
     * @return Gets the value of rateDTO and returns rateDTO
     */
    public RateDTO getRateDTO() {
        return rateDTO;
    }

    /**
     * Sets the rateDTO
     * You can use getRateDTO() to get the value of rateDTO
     */
    public void setRateDTO(RateDTO rateDTO) {
        this.rateDTO = rateDTO;
    }

    /**
     * @return Gets the value of surChargeDetails and returns surChargeDetails
     */
    public SurchargeDetails getSurChargeDetails() {
        return surChargeDetails;
    }

    /**
     * Sets the surChargeDetails
     * You can use getSurChargeDetails() to get the value of surChargeDetails
     */
    public void setSurChargeDetails(SurchargeDetails surChargeDetails) {
        this.surChargeDetails = surChargeDetails;
    }
}
