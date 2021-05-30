package com.xyz.rms.controller;


import com.xyz.rms.dto.RateDTO;
import com.xyz.rms.response.RateResponse;
import com.xyz.rms.service.RateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Rate Controller which is having all the end-point to performing CRUD Operations
 *
 * @author shuhaibkv01
 * @version 11
 * @since 2021
 */
@RestController
@RequestMapping("/api")
public class RateController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RateController.class);

    @Autowired
    private RateService rateService;

    /**
     * End-point for searching rate details based a particular Rate ID
     *
     * @param rateId
     * @return Rate Details
     */
    @GetMapping("/rate/{rateId}")
    public ResponseEntity<RateResponse> searchRateData(@PathVariable long rateId) {
        RateResponse rateResponse = rateService.searchRate(rateId);
        LOGGER.info("Rate Details fetched from DB Successfully");
        return new ResponseEntity<>(rateResponse, HttpStatus.OK);
    }

    /**
     * End-point for adding rate details in DB
     *
     * @param rateDTO
     * @return Added Rate Details
     */
    @PostMapping("/rate")
    public ResponseEntity<RateDTO> addRateData(@RequestBody RateDTO rateDTO) {
        RateDTO rateAddResponse = rateService.addRate(rateDTO);
        LOGGER.info("Rate Details Inserted in DB Successfully");
        return new ResponseEntity<>(rateAddResponse, HttpStatus.CREATED);
    }

    /**
     * End-point for updating rate details
     *
     * @param rateDTO
     * @return
     */
    @PutMapping("/rate")
    public ResponseEntity<RateDTO> updateRateData(@RequestBody RateDTO rateDTO) {
        RateDTO rateDTOUpdateResponse = rateService.updateRate(rateDTO);
        LOGGER.info("Rate Details Updated in DB Successfully");
        return new ResponseEntity<>(rateDTOUpdateResponse, HttpStatus.CREATED);
    }

    /**
     * End-point for deleting rate details based a particular Rate ID
     *
     * @param rateId
     * @return
     */
    @DeleteMapping("/rate/{rateId}")
    public ResponseEntity<Void> deleteRateData(@PathVariable long rateId) {
        rateService.deleteRate(rateId);
        LOGGER.info("Rate Details Deleted in DB Successfully");
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
