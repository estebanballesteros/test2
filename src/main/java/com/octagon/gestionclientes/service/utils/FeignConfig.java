package com.octagon.gestionclientes.service.utils;

import com.google.gson.Gson;
import com.octagon.gestionclientes.service.dto.ResponseDTO;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

public class FeignConfig {

    private static class MyErrorDecoder implements ErrorDecoder {

        private final Logger log = LoggerFactory.getLogger(FeignConfig.class);

        @Override
        public Exception decode(String methodKey, Response response) {
            log.error("Feign. Method: {}", methodKey);
            try {
                ResponseDTO responseDTO = (new Gson()).fromJson(response.body().asReader(), ResponseDTO.class);
                log.error("Error ResponseDTO obtained: {}", responseDTO);
                return new ApiException(responseDTO);
            } catch (Exception e) {
                return e;
            }
        }
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new MyErrorDecoder();
    }
}
