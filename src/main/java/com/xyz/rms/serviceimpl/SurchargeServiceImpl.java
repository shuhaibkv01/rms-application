package com.xyz.rms.serviceimpl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.xyz.rms.response.SurchargeDetails;
import com.xyz.rms.service.SurchargeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * SurchargeServiceImpl fetching surcharge details from external API.
 *
 * @author shuhaibkv01
 * @version 11
 * @since 2021
 */
@Service
public class SurchargeServiceImpl implements SurchargeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SurchargeServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${surcharge.api.url}")
    private String surchargeApiURL;

    /**
     * Fetching the Surcharge from External API
     *
     * @return SurCharge Details
     */
    @Override
    @HystrixCommand(fallbackMethod = "getDefaultSurCharge",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
            })
    public SurchargeDetails fetchSurchargeDetails() {
        SurchargeDetails surchargeDetails = restTemplate.getForObject(surchargeApiURL, SurchargeDetails.class);
        LOGGER.info("Fetched Surcharge Details from External API");
        return surchargeDetails;
    }

    /**
     * Fallback Method to return default Surcharge
     *
     * @return Default Surcharge Details
     */
    private SurchargeDetails getDefaultSurCharge() {
        LOGGER.warn("Could not Fetch Surcharge Details from External API, Getting the Default Surcharge");
        return new SurchargeDetails("Default Value");
    }
}
