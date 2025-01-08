/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.asistencia.utils;

import java.util.Random;

/**
 *
 * @author USUARIO
 */
public class RamdomNums 
{
    public static int[] nums(){
        int inicio=0,fin=9;
        Random numAleatorio = new Random();
        return new int[]
        {
            numAleatorio.nextInt(inicio,fin),
            numAleatorio.nextInt(inicio,fin),
            numAleatorio.nextInt(inicio,fin),
            numAleatorio.nextInt(inicio,fin),
            numAleatorio.nextInt(inicio,fin),
            numAleatorio.nextInt(inicio,fin)
        };
    }
   
}
