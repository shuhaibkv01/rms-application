package com.xyz.rms.repository;

import com.xyz.rms.entity.Rate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class RateRepositoryTest {

    @Autowired
    private RateRepository rateRepository;

    private Rate rate;

    @BeforeEach
    public void setUp() {
        rate = new Rate(1, "Macbook Pro", LocalDate.now(), LocalDate.now().plusDays(15), 10000);
    }

    @AfterEach
    public void tearDown() {
        rateRepository.deleteAll();
    }


    @Test
    public void givenIdThenShouldReturnRateOfThatId() {
        Rate savedRate = rateRepository.save(rate);
        Optional<Rate> searchedRate = rateRepository.findById(savedRate.getRateId());
        assertEquals(savedRate.getRateId(), searchedRate.get().getRateId());
        assertEquals(savedRate.getRateDescription(), searchedRate.get().getRateDescription());
    }

    @Test
    public void givenIdThenShouldReturnEmptyOptionalResponse() {
        Optional<Rate> searchedRate = rateRepository.findById(3l);
        assertTrue(searchedRate.isEmpty());
    }

    @Test
    public void givenRateToAddShouldReturnAddedRate() {
        Rate savedRate = rateRepository.save(rate);
        Rate fetchedProduct = rateRepository.findById(savedRate.getRateId()).get();
        assertEquals("Macbook Pro", fetchedProduct.getRateDescription());
        assertEquals(10000, fetchedProduct.getAmount());
    }

    @Test
    public void givenRateToUpdateShouldReturnUpdatedRate() {
        Rate savedRate = rateRepository.save(rate);
        savedRate.setRateDescription("Macbook Pro 2021 Limited Edition");
        savedRate.setAmount(12000);
        Rate updatedRate = rateRepository.save(savedRate);
        assertEquals("Macbook Pro 2021 Limited Edition", updatedRate.getRateDescription());
        assertEquals(12000, updatedRate.getAmount());
    }

    @Test
    public void givenIdToDeleteThenShouldDeleteTheRate() {
        Rate savedRate = rateRepository.save(rate);
        rateRepository.deleteById(savedRate.getRateId());
        Optional deletedRate = rateRepository.findById(savedRate.getRateId());
        assertEquals(Optional.empty(), deletedRate);
    }

    @Test
    public void givenIdToDeleteThenShouldThrowNoSuchElementException() {
        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
            rateRepository.deleteById(10l);
        });
    }
}
