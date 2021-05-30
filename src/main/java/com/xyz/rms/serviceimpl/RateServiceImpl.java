package com.xyz.rms.serviceimpl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.xyz.rms.dto.RateDTO;
import com.xyz.rms.entity.Rate;
import com.xyz.rms.repository.RateRepository;
import com.xyz.rms.response.RateResponse;
import com.xyz.rms.service.RateService;
import com.xyz.rms.service.SurchargeService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.NoSuchElementException;

/**
 * RateServiceImpl providing the implementation for CRUD Operations
 *
 * @author shuhaibkv01
 * @version 11
 * @since 2021
 */
@Service
public class RateServiceImpl implements RateService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RateServiceImpl.class);

    @Autowired
    private RateRepository rateRepository;

    @Autowired
    private SurchargeService surChargeService;

    private static ModelMapper modelMapper = new ModelMapper();


    /**
     * Searches Rate Details with Surcharge based on a particular Rate ID
     *
     * @param rateId
     * @return
     */
    @Override
    @HystrixCommand(
            commandKey = "searchRateByIdFromDB",
            fallbackMethod = "findCachedRateById",
            ignoreExceptions = {NoSuchElementException.class},
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
            }
    )
    public RateResponse searchRate(long rateId) {
        LOGGER.debug("Retrieving Rate Details from DB");
        Rate rateDetails = rateRepository.findById(rateId)
                .orElseThrow(() -> new NoSuchElementException());
        LOGGER.info("Retrieved Rate Details from DB");
        return new RateResponse(modelMapper.map(rateDetails, RateDTO.class), surChargeService.fetchSurchargeDetails());
    }

    /**
     * Add Rate Details to DB
     *
     * @param rateDTO
     * @return RateDTO Response
     */
    @Override
    public RateDTO addRate(RateDTO rateDTO) {
        Rate rate = modelMapper.map(rateDTO, Rate.class);
        return modelMapper.map(rateRepository.save(rate), RateDTO.class);
    }

    /**
     * Update the Rate Details based on a particular Rate ID
     *
     * @param rateDTO
     * @return Updated RateDTO Object
     */
    @Override
    public RateDTO updateRate(RateDTO rateDTO) {
        Rate rate = modelMapper.map(rateDTO, Rate.class);
        return modelMapper.map(rateRepository.save(rate), RateDTO.class);
    }

    /**
     * Delete the Rate Data based on a particular Rate ID
     *
     * @param rateId
     */
    @Override
    public void deleteRate(long rateId) {
        rateRepository.findById(rateId).orElseThrow(() -> new NoSuchElementException());
        rateRepository.deleteById(rateId);
    }

    /**
     * Finding the Rate Details from the cache(Using Dummy one for now)
     *
     * @param rateId
     * @return RateResponse with Surcharge
     */
    public RateResponse findCachedRateById(long rateId) {
        LOGGER.info("Returning Dummy Object");
        Rate rateDetails = new Rate(rateId, "Dummy Product", LocalDate.now(), LocalDate.now().plusDays(15), 0);
        return new RateResponse(modelMapper.map(rateDetails, RateDTO.class), surChargeService.fetchSurchargeDetails());

    }
}
