/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.asistencia.utils;

import java.util.Arrays;

/**
 *
 * @author USUARIO
 */
public class ConvertsArrays 
{
    public static String IntArrayTOString(String separador,int[] code)
    {
        String [] arrayToString = Arrays.stream(code)
                                .mapToObj(String::valueOf)
                                .toArray(String[]::new);
       
        System.out.println(String.join("", arrayToString));
        return String.join(separador,arrayToString);
    }
}
