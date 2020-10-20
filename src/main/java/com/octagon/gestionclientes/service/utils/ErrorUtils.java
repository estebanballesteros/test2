package com.octagon.gestionclientes.service.utils;

import com.octagon.gestionclientes.service.dto.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import springfox.documentation.annotations.ApiIgnore;

import java.util.concurrent.atomic.AtomicReference;

public final class ErrorUtils {


    private ErrorUtils() {
        throw new IllegalStateException("ErrorUtils class");
    }

    /**
     * Return the details of each error founded in Validation Entity
     *
     * @param errors
     * @return String with the description
     */
    public static String getDescriptionErrors(Errors errors) {//TODO: Hacer privado, y usar siempre Validate()
        AtomicReference<String> descripcionError = new AtomicReference<>(ResponseStatus.BAD_REQUEST.getDescription());
        errors.getFieldErrors().stream().forEach(error -> {
            descripcionError.set(descripcionError.get().concat(error.getField().concat(": ").concat(error.getDefaultMessage())).concat(". "));
        });
        return descripcionError.get();
    }
    public static void Validate(Errors errors) throws  OctagonBusinessException{
        String descriptionErrors = "";
        if (errors.hasErrors()) {
            descriptionErrors = getDescriptionErrors(errors).concat(descriptionErrors);
            throw new OctagonBusinessException(ResponseStatus.BAD_REQUEST,ResponseStatus.BAD_REQUEST.getMessage().concat(descriptionErrors));
        }

    }
}
