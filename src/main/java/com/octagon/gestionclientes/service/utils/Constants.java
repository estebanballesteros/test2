package com.octagon.gestionclientes.service.utils;

public final class Constants {


    private Constants() {
        throw new IllegalStateException("Constants class");
    }

    /**
     * Parametria: Codigo Tabla
     */
    public static final int COD_TABLA_GENERO = 3;
    public static final int COD_TABLA_ESTADO_CLIENTE = 4;
    public static final int COD_TABLA_TIPO_PERSONA = 5;
    public static final int COD_TABLA_TIPO_TELEFONO = 10;
    public static final int COD_TABLA_TIPO_DOMICILIO = 12;
    public static final int COD_TABLA_TIPO_CLIENTE = 13;

    /**
     * Paramtetria
     */
    public static final String TIPO_GENERO = "tipo_genero";
    public static final String ESTADO_CLIENTE = "estado_cliente";
    public static final String TIPO_PERSONA = "tipo_persona";
    public static final String TIPO_TELEFONO = "tipo_telefono";
    public static final String TIPO_DOMICILIO = "tipo_domicilio";
    public static final String TIPO_CLIENTE = "tipo_cliente";

    /**
     * Parametria: CodigoItem Genero Cliente
     */
    public static final int COD_ITEM_GENERO_MASCULINO = 0;
    public static final int COD_ITEM_GENERO_FEMENINO = 1;
    public static final int COD_ITEM_GENERO_OTRO = 2;

    /**
     * Parametria 'estado_cliente': Descripcion Larga
     */
    public static final String MASCULINO = "Masculino";
    public static final String FEMENINO = "Femenino";
    public static final String OTRO = "Otro";

    /**
     * Parametria: CodigoItem Estado Cliente
     */
    public static final int COD_ITEM_ESTADO_CLIENTE_INACTIVO = 0;
    public static final int COD_ITEM_ESTADO_CLIENTE_ACTIVO = 1;
    public static final int COD_ITEM_ESTADO_CLIENTE_BLOQUEADO = 2;
    public static final int COD_ITEM_ESTADO_CLIENTE_ELIMINADO = 3;

    /**
     * Parametria 'estado_cliente': Descripcion Larga
     */
    public static final String INACTIVO = "Inactivo";
    public static final String ACTIVO = "Activo";
    public static final String BLOQUEADO = "Bloqueado";
    public static final String ELIMINADO = "Eliminado";

    /**
     * Parametria: CodigoItem Tipo Persona Cliente
     */
    public static final int COD_ITEM_PERSONA_FISICA = 0;
    public static final int COD_ITEM_PERSONA_JURIDICA = 1;

    /**
     * Parametria 'tipo_persona': Descripcion Larga
     */
    public static final String FISICA = "Física";
    public static final String JURIDICA = "Jurídica";

    /**
     * Parametria: CodigoItem Tipo Telefono
     */
    public static final int COD_ITEM_TIPO_TELEFONO_MOVIL = 0;
    public static final int COD_ITEM_TIPO_TELEFONO_FIJO = 1;


    /** Parametria 'tipo_telefono' Tipo telefono */
    public static final String TELEFONO_MOVIL = "Móvil";
    public static final String TELEFONO_FIJO = "Fijo";

    /**
     * Parametria: CodigoItem Genero Cliente
     */
    public static final int COD_ITEM_DOMICILIO_REAL = 0;
    public static final int COD_ITEM_DOMICILIO_LABORAL = 1;
    public static final int COD_ITEM_DOMICILIO_SIN_DEFINIR = 2;

    /** Parametria Tipo Domicilio */
    public static final String DOMICILIO_REAL = "Real";
    public static final String DOMICILIO_LABORAL = "Laboral";
    public static final String DOMICILIO_SIN_DEFINIR = "Sin definir";

    /**
     * Parametria: CodigoItem Tipo Cliente
     */
    public static final int COD_ITEM_CLIENTE_REGISTRADO = 0;
    public static final int COD_ITEM_CLIENTE_PROSPECT = 1;

    /**
     * Parametria 'tipo_cliente': Descripcion Larga
     */
    public static final String REGISTRADO = "Registrado";
    public static final String PROSPECT = "Prospect";


    /**
     * ClienteBase 'origen_registro'
     */
    public static final String ATM = "atm";
    public static final String APP = "app";
    public static final String BO = "backoffice";


    /**
     * Parametria 'estado_cuenta': Descripcion Larga
     */
    public static final String INACTIVA = "Inactiva";

    /** Mensajes sms: accion */
    public static final String CUENTA_ACTIVA = "CUENTA_ACTIVA";
    public static final String CLIENTE_PORTAL_PAGOS_CREADO = "CLIENTE_PORTAL_PAGOS_CREADO";

    /**
     * Parametria: 'canal_operacion'
     */
    public static final int COD_TABLA_PORTAL_PAGOS = 19;

    public static final String PORTAL_PAGOS = "Portal Pagos";




}
