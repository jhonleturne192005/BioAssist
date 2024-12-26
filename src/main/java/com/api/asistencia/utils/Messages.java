/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.asistencia.utils;

/**
 *
 * @author USUARIO
 */
public class Messages 
{
    public static String ERROR_DATOS_INCOMPLETOS="Datos incompletos";
    public static String ACTUALIZACION_CORRECTA="Actualizado correctamente";
    public static String DATOS_GUARDADOS="Datos guardados correctamente";
    public static String OPERACION_CORRECTA="Datos cargados correctamente";
    public static String INICIO_SESSION_CORRECTO="Ha iniciado session correctamente";
    public static String CERRAR_SESSION="La session se cerro correctamente";
    
    
     //errores
    public static String YA_REGISTRO="Ya se encuentra registrado";
    public static String ERROR_DATOS_NO_ENCONTRADOS_LOGIN="El correo o la contraseña son incorrectas";
    public static String DATOS_INCORRECTOS="Correo o Contraseña incorrecta";
    public static String DATOS_EXISTENTES="Datos existentes en el sistema";
    public static String ERROR_SISTEMA="Ha ocurrido un error en el sistema";
    public static String ERROR_DATOS_NO_ENCONTRADOS_BUSQUEDA="No existen datos";

    //keys
    public static String ADMIN="admin";
    public static String KEY="key";
    public static String STATUS_LOGIN="status";
    public static String DATA="data";
    public static String ERROR_KEY="error";
    public static String SUCCESSFUL_KEY="successful";
    
    public static final String EX_MESSAGE_UNIQUE="could not execute statement [ERROR: llave duplicada viola restricción de unicidad «registro_%s_key»";
    public static String EX_TELEFONO_EXISTENTE=String.format(EX_MESSAGE_UNIQUE, "numero_telefono");
    public static String EX_TIPO_PERSONA=String.format(EX_MESSAGE_UNIQUE, "tipo");
    public static String EX_CORREO=String.format(EX_MESSAGE_UNIQUE, "correo");
    public static String EX_MATERIA=String.format(EX_MESSAGE_UNIQUE, "materia");
    public static String EX_CURSO=String.format(EX_MESSAGE_UNIQUE, "curso");

    
    //MM
    public static String TIPO_PERSONA_EXISTENTE="El tipo de persona ya se encuentra registrado";
    public static String TELEFONO_EXISTENTE="El telefono ya existe";
    public static String CORREO_EXISTENTE="El correo ya existe";
    public static String MATERIA_EXISTENTE="La materia ya existe";
    public static String CURSO_EXISTENTE="El curso ya existe";
    
    //permisos
    public static String PERMISOS_DENEGADO="No tiene permisos";
}
