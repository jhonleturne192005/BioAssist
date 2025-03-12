/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.api.asistencia.utils;

/**
 *
 * @author USUARIO
 */
public class pruebacordenada {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        distancia();
    }

    private static void distancia() {
        
        //DistanciaHaversine dh=new DistanciaHaversine(-1.026367,-79.457187,-1.036913,-79.468572);
        DistanciaHaversine dh=new DistanciaHaversine(-1.0371975,-79.4587862,-1.0371872,-79.4587961);
        System.out.println("RESULTADO EN KILOMETROS= "+dh.resultadoenkm);
        System.out.println("RESULTADO EN METROS= "+dh.resultadoenmetros);
        System.out.println("DENTRO DEL RADIO?= "+dh.verificarDistancia());
    }
    
}
