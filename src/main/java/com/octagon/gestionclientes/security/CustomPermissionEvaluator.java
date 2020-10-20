package com.octagon.gestionclientes.security;

import com.octagon.gestionclientes.client.Gateway;
import com.octagon.gestionclientes.web.dto.AuthorityResourceEndpointDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

    @Autowired
    private Gateway gatewayClient;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {

        Set<AuthorityResourceEndpointDTO> authSet = new HashSet<>();

        authentication.getAuthorities().stream()
            .map(auth -> authSet.addAll(gatewayClient.getAuthorityResourceEndpointSetByAuth(auth.getAuthority()).getResponseData()))
            .collect(Collectors.toSet());

        boolean hasPermission = false;
        if (authentication != null && targetDomainObject instanceof String && permission instanceof String) {

            for (AuthorityResourceEndpointDTO roleResource : authSet) {
                if (roleResource.getResourceName().contains(String.valueOf(targetDomainObject))
                    && roleResource.getEndpointName().contains(String.valueOf(permission))) {
                    hasPermission = true;
                    break;
                }
            }

        } else {
            hasPermission = false;
        }
        return hasPermission;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        throw new RuntimeException("Id and Class permissions are not supported by this application");
    }

}
