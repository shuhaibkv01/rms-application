package com.xyz.rms.repository;

import com.xyz.rms.entity.Rate;
import org.springframework.data.repository.CrudRepository;

/**
 * Rate Repository which is interacting with DB
 *
 * @author shuhaibkv01
 * @version 11
 * @since 2021
 */
public interface RateRepository extends CrudRepository<Rate, Long> {

}
