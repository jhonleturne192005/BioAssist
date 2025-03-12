/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.asistencia.utils;

import java.util.UUID;

/**
 *
 * @author USUARIO
 */
public class CreateHash 
{
    
    //este metodo genera el hash unico del sistema para el telefono
    //ademas de generar otro hash con las credenciales
    public static String[] crearhashdatos(String credenciales_telefono)
    {
        UUID uuid = UUID.randomUUID();
        String hash_sistema=encryptString.encriptPassword(uuid.toString());
        String hash_datos_sistema_movil=encryptString.encriptPassword(credenciales_telefono+hash_sistema);
        return new String[]
        {
            hash_sistema,
            hash_datos_sistema_movil 
        };
    }
    
    public static String[] recrearhashdatos(String credenciales_telefono,String credenciales_sistema)
    {
        String hash_sistema=credenciales_sistema;
        String hash_datos_sistema_movil=encryptString.encriptPassword(credenciales_telefono+hash_sistema);
        return new String[]
        {
            hash_sistema,
            hash_datos_sistema_movil 
        };
    }
    
    
}
