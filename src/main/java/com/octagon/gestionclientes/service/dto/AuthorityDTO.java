package com.octagon.gestionclientes.service.dto;

import io.swagger.annotations.ApiParam;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class AuthorityDTO implements Serializable {

    @NotNull
    @Size(min = 1, max = 50)
    @ApiParam(value = "Authority Name (Nombre de los Roles)", example = "ROLE_ADMIN")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
