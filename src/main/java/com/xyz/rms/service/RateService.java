package com.xyz.rms.service;

import com.xyz.rms.dto.RateDTO;
import com.xyz.rms.response.RateResponse;

/**
 * Rate Service Interface which interacts with Rate Repository and Perform Data Base Operations
 *
 * @author shuhaibkv01
 * @version 11
 * @since 2021
 */
public interface RateService {

    /**
     * Searches Rate Details with Surcharge based on a particular Rate ID
     *
     * @param rateID
     * @return
     */
    public RateResponse searchRate(long rateID);


    /**
     * Add Rate Details to DB
     *
     * @param rateDTO
     * @return Added Rate Details
     */
    public RateDTO addRate(RateDTO rateDTO);

    /**
     * Update the Rate Details based on a particular Rate ID
     *
     * @param rateDTO
     * @return Updated Rate Details
     */
    public RateDTO updateRate(RateDTO rateDTO);


    /**
     * Delete the Rate Data based on a particular Rate ID
     *
     * @param rateId
     */
    public void deleteRate(long rateId);

}
