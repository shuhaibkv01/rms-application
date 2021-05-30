package com.xyz.rms.service;

import com.xyz.rms.response.SurchargeDetails;

/**
 * SurchargeService Interface which fetches the surcharge details.
 *
 * @author shuhaibkv01
 * @version 11
 * @since 2021
 */
public interface SurchargeService {
    /**
     * Method to fetch Surcharge Details from External API
     * @return
     */
    public SurchargeDetails fetchSurchargeDetails();

}
