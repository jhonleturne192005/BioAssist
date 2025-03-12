/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.api.asistencia.utils;

import org.json.JSONObject;

/**
 *
 * @author USUARIO
 */
public class pruebajsonobj {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JSONObject jo = new JSONObject();
        jo.put("cedula", "1234567890");

        System.out.println(jo.getString("cedulaa"));
        

    }
    
}
