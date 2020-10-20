package com.octagon.gestionclientes.config;

/**
 * Application constants.
 */
public final class Constants {

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^[_.@A-Za-z0-9-]*$";
    public static final String ONLY_LETTERS_REGEX = "^[A-Za-z]*$";
    public static final String ONLY_NUMBERS_REGEX = "^[0-9]*$";
    public static final String CELULAR_REGEX = "^[0-9]*$";

    public static final String SYSTEM_ACCOUNT = "system";
    public static final String ANONYMOUS_USER = "anonymoususer";
    public static final String DEFAULT_LANGUAGE = "en";

    //Request id names of microservices
    public static final String MS_CUENTA_TRANSACCION = "ms-cuenta-transaccion";

    private Constants() {
    }
}
