/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.asistencia.utils;

/**
 *
 * @author USUARIO
 */
public class DistanciaHaversine 
{
    private double RADIO_TIERRA_PROMEDIO=6371; //en kilometros
    private double RADIO_CONDICION_ESTABLECIDO=300; //en metros
    private double latitud1;
    private double longitud1;
    private double latitud2;
    private double longitud2;
    public double resultadoenkm=0;
    public double resultadoenmetros=0;
      
    public DistanciaHaversine(double latitud1, double longitud1,double latitud2,double longitud2)
    {
        this.latitud1=latitud1;
        this.longitud1=longitud1;
        this.latitud2=latitud2;
        this.longitud2=longitud2;
        calculate();
    }

    private void calculate() 
    {
        double lat1Rad = Math.toRadians(this.latitud1);
        double lon1Rad = Math.toRadians(this.longitud1);
        double lat2Rad = Math.toRadians(this.latitud2);
        double lon2Rad = Math.toRadians(this.longitud2);
        
        double dosrsen=2*this.RADIO_TIERRA_PROMEDIO; //2*rad
        double primersen=Math.pow(Math.sin((lat2Rad-lat1Rad)/2), 2); //sen^2((lat2-lat1)/2)
        double multcos=Math.cos(lat1Rad)*Math.cos(lat2Rad); //cos(lat1)*cos(lat2)
        double segundosen=Math.pow(Math.sin((lon2Rad-lon1Rad)/2), 2); //sen^2((long2-long1)/2)
        
        
        System.out.println(lat1Rad);
        System.out.println(lon1Rad);
        System.out.println(lat2Rad);
        System.out.println(lon2Rad);
        
        System.out.println("**********");

        System.out.println(dosrsen);
        System.out.println(primersen);
        System.out.println(multcos);
        System.out.println(segundosen);
        
        double total=dosrsen*Math.asin(Math.sqrt(primersen+multcos*segundosen));
        
        System.out.println("RESULT FINAL= "+total);
        
   
        
        this.resultadoenkm=total;
        this.resultadoenmetros=total*1000;
    }
    
    public Boolean verificarDistancia()
    {
        return this.resultadoenmetros<=this.RADIO_CONDICION_ESTABLECIDO;
    }
    
    
    
}
