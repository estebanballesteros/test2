package com.octagon.gestionclientes.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.NoSuchElementException;

/**
 * Properties specific to Gestionclientes.
 * <p>
 * Properties are configured in the application.yml file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {
    private String msGestionClientePrivateRsaKey = new String();

    public String getMsGestionClientePrivateRsaKey() {
        return msGestionClientePrivateRsaKey;
    }

    public void setMsGestionClientePrivateRsaKey(String msGestionClientePrivateRsaKey) {
        this.msGestionClientePrivateRsaKey = msGestionClientePrivateRsaKey;
    }

    // TODO add public keys in application-local.yml file to use this function
    public String getPublicRsaKey(String requestId) {
        switch (requestId) {
            case Constants.MS_CUENTA_TRANSACCION:
                return null;
            default:
                throw new NoSuchElementException("requestId name is not a valid value");
        }
    }
}
