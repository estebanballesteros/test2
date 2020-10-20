package com.octagon.gestionclientes.client;

import com.octagon.gestionclientes.client.dto.MetadataDTO;
import feign.Response;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "gateway/almacenamiento", configuration = AlmacenamientoClient.ClientConfig.class)
public interface AlmacenamientoClient {

    @PostMapping(value = "api/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    MetadataDTO create(@RequestPart("file") MultipartFile file, @RequestParam("dir") String dir, @RequestParam("filename") String filename);

    @GetMapping(value = "api/get")
    MetadataDTO get(@RequestParam("dir") String dir, @RequestParam("filename") String filename) throws EntityNotFoundException;

    @GetMapping(value = "api/delete")
    void delete(@RequestParam("dir") String dir, @RequestParam("filename") String filename);

    class ClientConfig {
        @Autowired
        private ObjectFactory<HttpMessageConverters> messageConverters;

        private class MyErrorDecoder implements ErrorDecoder {
            @Override
            public Exception decode(String methodKey, Response response) {
                switch(response.status()) {
                    case 404:
                        return new EntityNotFoundException();
                    default:
                        return new ErrorDecoder.Default().decode(methodKey, response);
                }
            }
        }

        @Bean
        public Encoder feignFormEncoder() {
            return new SpringFormEncoder(new SpringEncoder(messageConverters));
        }

        @Bean
        public ErrorDecoder errorDecoder() {
            return new MyErrorDecoder();
        }
    }


}
