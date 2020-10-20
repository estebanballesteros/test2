package com.octagon.gestionclientes.service.utils;

import org.springframework.http.HttpStatus;

public enum ResponseStatus {


    /**
     * Response 'HTTP' status codes
     */
    OK(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), "La operación se realizo exitosamente. "),
    CREATED(HttpStatus.CREATED.value(), HttpStatus.CREATED.getReasonPhrase(), "La entidad se creo exitosamente. "),
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), "Los campos ingresados son incorrectos. "),
    NOT_FOUND(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), "No existen contenidos. "),
    /**
     * Response 'GestionCliente' Specific Business status codes (600-699)
     */
    EXCEPTION(600, "Unhandled exception","Excepción: "),
    ENTITY_NOT_FOUND(601, "id not exists", "La entidad no existe: "),
    ALREADY_ACTIVATED(602, "already activated", "La entidad ya ha sido activada: "),
    ERROR_ACCOUNT_ACTIVATION(603, "msCuentaTransaccion error", "Error de activacion, hay un problema en la activacion de la cuenta."),
    INVALID_DNI(604, "Invalid dni", "El dni ingresado es incorrecto."),
    DNI_NOT_FOUND(605, "Dni not found", "El dni ingresado no existe."),
    INVALID_ID(606, "Invalid id", "El id ingresado no puede ser nulo."),
    AGENDA_OBTAINED(609, "Agenda ok", "Se obtuvo la agenda de destinatarios exitosamente. "),
    SUCCESSFULLY_ACTIVATION(610, "Activacion ok", "La activacion se realizó exitosamente. "),
    INVALID_CUENTA_ORIGEN(611, "Invalid origenRegistro", "origenRegistro:  tiene que ser APP o ATM. "),
    TIPO_ARCHIVO_INVALIDO(612, "tipo archivo invalido", "tipo archivo invalido"),
    INAVALID_ALIAS(614, "Invalid Alias", "alias: no puede estar vacío para app."),
    ERROR_CALL_MS_PRESTAMO(613, "Error call prestamo microservice", "Se produjo un error al comunicarse con el microservicio de Prestamos"),
    DESTINATARIO_EXIST(615, "Invalid DNI", "El destinatario ya existe."),
    NOT_FOUND_CLIENTES(616, "Not found clientes for this financiera", "No existen cliente para la financiera ingresada"),
    ERROR_CALL_GATEWAY(617, "Error Call Gateway", "Hubo un error al comunicarse con el ms Gateway"),
    PARAMETRO_NOT_FOUND(618, "Not found Parametro", "El Parametro no existe"),
    ALREADY_INACTIVATED(619, "already inactivated", "La entidad ya ha sido desactivada: "),

    ;

    private final Number code;
    private final String message;
    private final String description;

    ResponseStatus(Number code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public Number getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
