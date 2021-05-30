package com.xyz.rms.service;

import com.xyz.rms.response.SurchargeDetails;
import com.xyz.rms.serviceimpl.SurchargeServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(properties = {
        "surcharge.api.url=https://surcharge.free.beeceptor.com/surcharge",
})
public class SurchargeServiceTest {

    @InjectMocks
    private SurchargeServiceImpl surchargeService;

    @Mock
    private RestTemplate restTemplate;

    @Value("${surcharge.api.url}")
    String surchargeApiUrl;

    @Test
    public void shouldFetchValidResponseFromAPI() {
        SurchargeDetails surchargeDetails = new SurchargeDetails("Cool");
        String url = "abc";
        when(restTemplate.getForObject(surchargeApiUrl, SurchargeDetails.class)).thenReturn(surchargeDetails);
        SurchargeDetails surchargeResponse = surchargeService.fetchSurchargeDetails();
        Assertions.assertEquals("Cool", surchargeResponse.getStatus());
    }
}
