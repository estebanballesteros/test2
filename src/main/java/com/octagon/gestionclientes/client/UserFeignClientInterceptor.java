package com.octagon.gestionclientes.client;

import com.octagon.gestionclientes.config.ApplicationProperties;
import com.octagon.gestionclientes.security.SecurityUtils;
import com.octagon.gestionclientes.security.utils.CipherUtility;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class UserFeignClientInterceptor implements RequestInterceptor {

    @Autowired
    private ApplicationProperties applicationProperties;
    @Autowired
    private CipherUtility cipherUtility;

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER = "Bearer";
    private static final String REQUEST_ID_HEADER = "x-request-id";
    private static final String SIGNATURE_HEADER = "x-signature";

    private String jwtSignature;
    private String msGestionClientePrivateRsaKey;

    @PostConstruct
    public void init() {
        msGestionClientePrivateRsaKey = applicationProperties.getMsGestionClientePrivateRsaKey();
    }

    @Override
    public void apply(RequestTemplate template) {
        SecurityUtils.getCurrentUserJWT()
            .ifPresent(s -> {
                try{
                    jwtSignature = cipherUtility.encrypt(cipherUtility.hashString(s), cipherUtility.decodePrivateKey(msGestionClientePrivateRsaKey));
                }catch (Exception e){
                    //TODO: Handle exception
                }
                template.header(AUTHORIZATION_HEADER,String.format("%s %s", BEARER, s));
                template.header(REQUEST_ID_HEADER, String.format("%s", "ms-gestion-cliente"));
                template.header(SIGNATURE_HEADER, String.format("%s", jwtSignature));
            });
    }
}
