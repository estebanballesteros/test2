package com.octagon.gestionclientes.service.dto;

import com.octagon.gestionclientes.service.utils.ResponseStatus;
import com.octagon.gestionclientes.web.rest.util.HeaderUtil;
import org.springframework.http.ResponseEntity;

public class ResponseDTO<T> {

    /**
     * status: true or false, when the service's response is success and have completed the functionality set true otherwise false
     */
    private Boolean status;
    /**
     * code: ' HTTP ResponseStatus status codes'
     * * 1xx: Informative answers
     * * 2xx: Correct requests(200,201,204)
     * * 2xx: Correct requests
     * * 4xx: Clients errors(400,404, e.getCode() 'from exceptions'
     * * 5xx: Server errors
     * * 6xx: Business errors see 'ResponseStatus Code Error'
     */
    private Number code;
    /**
     * ' HTTP ResponseStatus status message' ( OK,Created,No Content,Bad Request,Not Found), 'Error' or 'Specific error codes of the micro services'
     */
    private String message;
    /**
     * 'Details about the result', example: 'Entity create successfully', e.getMessage() 'from exceptions' .
     */
    private String description;
    /**
     * 'Service request'
     */
    private T responseData;

    public ResponseDTO() {
    }

    public ResponseDTO(T responseData) {
        this.code = ResponseStatus.OK.getCode();
        this.message = ResponseStatus.OK.getMessage();
        this.description = ResponseStatus.OK.getDescription();
        this.status = true;
        this.responseData = responseData;
    }

    public ResponseDTO(Boolean status, Number code, String messages, String description) {
        this.status = status;
        this.code = code;
        this.message = messages;
        this.description = description;
    }

    public ResponseDTO(Boolean status, ResponseStatus responseStatus) {
        this.status = status;
        this.code = responseStatus.getCode();
        this.message = responseStatus.getMessage();
        this.description = responseStatus.getDescription();
    }

    public ResponseDTO(Boolean status, ResponseStatus responseStatus, String description) {
        this.status = status;
        this.code = responseStatus.getCode();
        this.message = responseStatus.getMessage();
        this.description = description;
    }

    public ResponseDTO(Boolean status, ResponseStatus responseStatus, T responseData) {
        this.status = status;
        this.code = responseStatus.getCode();
        this.message = responseStatus.getMessage();
        this.description = responseStatus.getDescription();
        this.responseData = responseData;
    }

    public ResponseDTO(Boolean status, ResponseStatus responseStatus, String description, T responseData) {
        this.status = status;
        this.code = responseStatus.getCode();
        this.message = responseStatus.getMessage();
        this.description = description;
        this.responseData = responseData;
    }

    public ResponseDTO(Exception e) {
        this.code = ResponseStatus.EXCEPTION.getCode();
        this.status = false;
        this.message = ResponseStatus.EXCEPTION.getMessage();
        this.description = e.getMessage();
    }

    public ResponseDTO(Boolean status, Exception e) {
        this.status = status;
        this.message = e.getMessage();
        this.description = e.getMessage();
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }


    public Number getCode() {
        return code;
    }

    public void setCode(Number code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public T getResponseData() {
        if (responseData == null) {
            responseData = (T) null;
        }
        return responseData;
    }

    public void setResponseData(T responseData) {
        this.responseData = responseData;
    }


}
