package com.octagon.gestionclientes.web.dto;

import javax.validation.constraints.Size;
import java.io.Serializable;

public class AuthorityResourceEndpointDTO implements Serializable {

    private Long id;

    @Size(max = 256)
    private String authorityName;

    @Size(max = 256)
    private String resourceName;

    @Size(max = 256)
    private String endpointName;

    public AuthorityResourceEndpointDTO() {
        // Empty constructor needed for Jackson.
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getEndpointName() {
        return endpointName;
    }

    public void setEndpointName(String endpointName) {
        this.endpointName = endpointName;
    }

    @Override
    public String toString() {
        return "AuthorityResourceEndpointDTO{" +
            "id=" + id +
            ", authorityName='" + authorityName + '\'' +
            ", resourceName='" + resourceName + '\'' +
            ", endpointName='" + endpointName + '\'' +
            '}';
    }

}
