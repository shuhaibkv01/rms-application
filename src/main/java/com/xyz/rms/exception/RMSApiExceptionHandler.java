package com.xyz.rms.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

/**
 * REST Api Exception Handler which handles exceptions and provide a meaning full response to the client.
 *
 * @author shuhaibkv01
 * @version 11
 * @since 2021
 */
@RestControllerAdvice
public class RMSApiExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RMSApiExceptionHandler.class);

    /**
     * Handles NoSuchElementException Exception
     *
     * @param noSuchElementException
     * @return NoSuchElementException Response
     */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException noSuchElementException) {
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        ApiException apiException = new ApiException("RateId not found in RMS", notFound.value());
        LOGGER.error("RateId not found in RMS : {}", noSuchElementException.getMessage());
        return buildResponseEntity(apiException, notFound);
    }


    /**
     * Handles InvalidFormatException Exception
     *
     * @param httpMessNotReadableEx
     * @return Exception Response
     */

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException httpMessNotReadableEx) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException("Request could not be parsed, please verify it", badRequest.value());
        LOGGER.error("Exception occurred while parsing the input request : {}", httpMessNotReadableEx.getMessage());
        return buildResponseEntity(apiException, badRequest);
    }

    /**
     * Handles InvalidFormatException Exception
     *
     * @param exception
     * @return Exception Response
     */

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleCommonException(Exception exception) {
        HttpStatus internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;
        ApiException apiException = new ApiException("Internal server error. Please contact admin", internalServerError.value());
        LOGGER.error("Exception occurred : {}", exception.getMessage());
        return buildResponseEntity(apiException, internalServerError);
    }

    /**
     * Building Response Object
     *
     * @param apiException
     * @return
     */
    protected ResponseEntity<Object> buildResponseEntity(ApiException apiException, HttpStatus httpStatus) {
        return new ResponseEntity<>(apiException, httpStatus);
    }
}
