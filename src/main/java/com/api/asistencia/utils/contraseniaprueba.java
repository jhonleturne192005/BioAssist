/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.api.asistencia.utils;

/**
 *
 * @author USUARIO
 */
public class contraseniaprueba {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        System.out.println(encryptString.encriptPassword("admin123"));
        System.out.println(encryptString.encriptPassword("profesor123"));
        System.out.println(encryptString.encriptPassword("profesor456"));
        System.out.println(encryptString.encriptPassword("estudiante123"));
        System.out.println(encryptString.encriptPassword("estudiante456"));
        
        
        System.out.println("JHON= "+encryptString.encriptPassword("jhon"));       
        System.out.println("8A= "+encryptString.encriptPassword("ochoa"));
        System.out.println("ORLANDO= "+encryptString.encriptPassword("orlando"));
        System.out.println("CHAUFA= "+encryptString.encriptPassword("chaufa"));
        
    }
    
}
