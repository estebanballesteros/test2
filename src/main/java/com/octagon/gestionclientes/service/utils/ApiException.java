package com.octagon.gestionclientes.service.utils;

import com.netflix.hystrix.exception.ExceptionNotWrappedByHystrix;
import com.octagon.gestionclientes.service.dto.ResponseDTO;

public class ApiException extends RuntimeException implements ExceptionNotWrappedByHystrix {

    private Boolean status;
    private Number code;
    private String message;
    private String description;

    public ApiException() { }

    public ApiException(ResponseDTO responseDTO) {
        this.code = responseDTO.getCode();
        this.message = responseDTO.getMessage();
        this.description = responseDTO.getDescription();
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

    @Override
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

}
