package com.xyz.rms.exception;


import org.springframework.http.HttpStatus;

/**
 * Exception Response Mapping Class
 *
 * @author shuhaibkv01
 * @version 11
 * @since 2021
 */
public class ApiException {
    private final String description;
    private final int statusCode;


    /**
     * Constructor to assign API Exception Details
     * @param description
     * @param statusCode
     */
    public ApiException(String description, int statusCode) {
        this.description = description;
        this.statusCode = statusCode;
    }

    /**
     * @return Gets the value of description and returns description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return Gets the value of statusCode and returns statusCode
     */
    public int getStatusCode() {
        return statusCode;
    }
}

