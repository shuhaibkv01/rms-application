package com.xyz.rms.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.xyz.rms.dto.RateDTO;
import com.xyz.rms.entity.Rate;
import com.xyz.rms.exception.ApiException;
import com.xyz.rms.exception.RMSApiExceptionHandler;
import com.xyz.rms.response.RateResponse;
import com.xyz.rms.response.SurchargeDetails;
import com.xyz.rms.service.RateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RateControllerTest {

    @Mock
    private RateService rateService;

    @InjectMocks
    private RateController rateController;

    @Autowired
    private MockMvc mockMvc;

    private RateDTO rateDTO;
    private RateDTO rateDTOWithoutId;

    private ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    public void setup() {
        rateDTO = new RateDTO(1, "Macbook Pro", LocalDate.now(), LocalDate.now().plusDays(15), 10000);
        rateDTOWithoutId = new RateDTO(0, "Macbook Pro", LocalDate.now(), LocalDate.now().plusDays(15), 10000);
        mockMvc = MockMvcBuilders.standaloneSetup(new RMSApiExceptionHandler(), rateController).build();
    }


    @Test
    public void GetMappingOfRateShouldReturnRespectiveRateWithSurcharge() throws Exception {
        RateResponse rateResponse = new RateResponse(rateDTO, new SurchargeDetails("Awesome"));
        when(rateService.searchRate(rateDTO.getRateId())).thenReturn(rateResponse);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/rate/1").
                contentType(MediaType.APPLICATION_JSON).
                content(asJsonString(rateResponse))).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void GetMappingOfRateShouldReturnNotFoundErrorMessage() throws Exception {
        ApiException apiException = new ApiException("RateId not found in RMS", 404);
        when(rateService.searchRate(anyLong())).thenThrow(new NoSuchElementException());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/rate/2").
                contentType(MediaType.APPLICATION_JSON).
                content(asJsonString(apiException))).
                andExpect(MockMvcResultMatchers.status().isNotFound()).
                andDo(MockMvcResultHandlers.print());
    }


    @Test
    public void PostMappingOfRateShouldReturnRate() throws Exception {
        when(rateService.addRate(any())).thenReturn(rateDTO);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/rate").
                contentType(MediaType.APPLICATION_JSON).
                content(asJsonString(rateDTOWithoutId))).
                andExpect(MockMvcResultMatchers.status().isCreated()).
                andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void PostMappingOfRateShouldReturnInternalServerError() throws Exception {
        when(rateService.addRate(any())).thenThrow(new RuntimeException());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/rate").
                contentType(MediaType.APPLICATION_JSON).
                content(asJsonString(new Rate()))).
                andExpect(MockMvcResultMatchers.status().is5xxServerError()).
                andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void PutMappingOfRateShouldReturnUpdatedRate() throws Exception {
        when(rateService.updateRate(any())).thenReturn(rateDTO);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/rate").
                contentType(MediaType.APPLICATION_JSON).
                content(asJsonString(rateDTOWithoutId))).
                andExpect(MockMvcResultMatchers.status().isCreated()).
                andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void PutMappingOfRateShouldThrowInternalServerError() throws Exception {
        when(rateService.updateRate(any())).thenThrow(new RuntimeException());
        mockMvc.perform(MockMvcRequestBuilders.put("/api/rate").
                contentType(MediaType.APPLICATION_JSON).
                content(asJsonString(new Rate()))).
                andExpect(MockMvcResultMatchers.status().is5xxServerError()).
                andDo(MockMvcResultHandlers.print());
    }


    @Test
    public void DeleteMappingUrlAndIdThenShouldReturnAcceptedCode() throws Exception {
        doNothing().when(rateService).deleteRate(rateDTO.getRateId());
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/rate/1"))
                .andExpect(MockMvcResultMatchers.status().isAccepted()).
                andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void DeleteMappingUrlAndIdThenShouldReturnNotFoundErrorCode() throws Exception {
        doThrow(new NoSuchElementException()).when(rateService).deleteRate(4);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/rate/4"))
                .andExpect(MockMvcResultMatchers.status().isNotFound()).
                andDo(MockMvcResultHandlers.print());
    }

    public static String asJsonString(final Object rate) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            return objectMapper.writeValueAsString(rate);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
