package com.xyz.rms.service;


import com.xyz.rms.dto.RateDTO;
import com.xyz.rms.entity.Rate;
import com.xyz.rms.repository.RateRepository;
import com.xyz.rms.response.RateResponse;
import com.xyz.rms.response.SurchargeDetails;
import com.xyz.rms.serviceimpl.RateServiceImpl;
import com.xyz.rms.serviceimpl.SurchargeServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RateServiceTest {

    @Mock
    private SurchargeServiceImpl surchargeService;

    @Mock
    private RateRepository rateRepository;

    @InjectMocks
    private RateServiceImpl rateService;

    private ModelMapper modelMapper = new ModelMapper();

    private RateDTO rateDTO1;
    private RateDTO rateDTO2;
    private SurchargeDetails surchargeDetails1;

    @BeforeEach
    public void setUp() {
        rateDTO1 = new RateDTO(1, "Macbook Air", LocalDate.now(), LocalDate.now().plusDays(15), 6000);
        rateDTO2 = new RateDTO(1, "Macbook Air 2021", LocalDate.now(), LocalDate.now().plusDays(15), 9000);
        surchargeDetails1 = new SurchargeDetails("Awesome");
    }

    @AfterEach
    public void tearDown() {
        rateDTO1 = rateDTO2 = null;
        surchargeDetails1 = null;
    }

    @Test
    public void givenIdThenShouldReturnRateWithSurchargeOfThatId() {
        when(rateRepository.findById(1l)).thenReturn(Optional.ofNullable(modelMapper.map(rateDTO1, Rate.class)));
        when(surchargeService.fetchSurchargeDetails()).thenReturn(surchargeDetails1);
        RateResponse rateResponse = rateService.searchRate(1l);
        Assertions.assertEquals("Macbook Air", rateResponse.getRateDTO().getRateDescription());
        Assertions.assertEquals(6000, rateResponse.getRateDTO().getAmount());
        Assertions.assertEquals("Awesome", rateResponse.getSurChargeDetails().getStatus());
    }


    @Test
    public void givenIdThenShouldThrowNoSuchElementException() {
        when(rateRepository.findById(2l)).thenReturn(Optional.empty());
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            RateResponse rateResponse = rateService.searchRate(2l);
        });
    }


    @Test
    public void givenRateToAddShouldReturnAddedRate() {
        when(rateRepository.save(any())).thenReturn(modelMapper.map(rateDTO1, Rate.class));
        RateDTO addedDTORate = rateService.addRate(rateDTO1);
        Assertions.assertEquals("Macbook Air", addedDTORate.getRateDescription());
        Assertions.assertEquals(LocalDate.now(), addedDTORate.getRateEffectiveDate());
    }

    @Test
    public void givenRateToUpdateShouldReturnUpdatedRate() {
        when(rateRepository.save(any())).thenReturn(modelMapper.map(rateDTO2, Rate.class));
        RateDTO updatedRateDTO = rateService.updateRate(rateDTO2);
        Assertions.assertEquals("Macbook Air 2021", updatedRateDTO.getRateDescription());
        Assertions.assertEquals(LocalDate.now(), updatedRateDTO.getRateEffectiveDate());
    }

    @Test
    public void givenIdTODeleteThenShouldDeleteTheRate() {
        when(rateRepository.findById(1l)).thenReturn(Optional.ofNullable(modelMapper.map(rateDTO1, Rate.class)));
        rateService.deleteRate(1l);
        verify(rateRepository, times(1)).deleteById(1l);
    }

    @Test
    public void givenIdTODeleteThenShouldThrowNoSuchElementException() {
        when(rateRepository.findById(3l)).thenReturn(Optional.empty());
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            rateService.deleteRate(3l);
        });
    }
}
