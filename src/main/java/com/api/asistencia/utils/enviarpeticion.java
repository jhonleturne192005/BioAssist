/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.api.asistencia.utils;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Form;
import jakarta.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Base64;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONObject;

/**
 *
 * @author USUARIO
 */
public class enviarpeticion 
{
  
    //https://oracle-max.com/como-consumir-un-api-restful-usando-el-metodo-post-en-java/
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        Integer opcion=2;
        switch(opcion) { 
            case 1 -> prueba();
            case 2 -> asignacion_de_recurso();
            case 3 -> entrenamiento_de_modelo();
            case 4 -> reconocimiento();
            case 5 -> base64prueba();
            default -> System.out.println("Opcion incorrecta");
        }
    
    }
    
    
    
    public static void prueba()
    {
        try
        {
            String ruta_video = "D:\\jhon 9no semestre\\CIENCIA, TECNOLOGIA, SOCIEDAD E INNOVACION\\proyectofinal\\reconocimiento_facial\\video_mark.mp4";
            InputStream is = new FileInputStream(ruta_video);
            byte[] bytes = is.readAllBytes();


            Form form=new Form();

            form.param("saludo", "hola");
            form.param("video", Base64.getEncoder().encodeToString(bytes));

            Client client = ClientBuilder.newClient();
            WebTarget target = client.target("http://127.0.0.1:8000/api/prueba/");

            Invocation.Builder builder = target.request("application/json");
            Invocation invocation = builder.buildPost(Entity.form(form));

            Response response = invocation.invoke();

            int statusCode = response.getStatus();
            String respuesta = response.readEntity(String.class);
            respuesta=respuesta.translateEscapes();
            respuesta=respuesta.substring(1,respuesta.length()-1);
            if (statusCode == 200) {
                System.out.println(respuesta);
                System.out.println(new JSONObject(respuesta).get("success"));
                System.out.println(new JSONObject(respuesta).getBoolean("estado"));
                System.out.println(statusCode);
            } 
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }
    
    public static void asignacion_de_recurso()
    {
        try
        {
            String ruta_video = "D:\\jhon 9no semestre\\CIENCIA, TECNOLOGIA, SOCIEDAD E INNOVACION\\proyectofinal\\reconocimiento_facial\\video_mark.mp4";
            InputStream is = new FileInputStream(ruta_video);
            byte[] bytes = is.readAllBytes();


            Form form=new Form();

            form.param("video", Base64.getEncoder().encodeToString(bytes));

            Client client = ClientBuilder.newClient();
            WebTarget target = client.target("http://127.0.0.1:8000/api/asignar_recurso/");

            Invocation.Builder builder = target.request("application/json");
            Invocation invocation = builder.buildPost(Entity.form(form));

            Response response = invocation.invoke();

            int statusCode = response.getStatus();
            String respuesta = response.readEntity(String.class);
            respuesta=respuesta.translateEscapes();
            respuesta=respuesta.substring(1,respuesta.length()-1);
            if (statusCode == 200) {
                System.out.println(respuesta);
                System.out.println(new JSONObject(respuesta).getString("success"));
                System.out.println(new JSONObject(respuesta).getString("ooid"));
                System.out.println(statusCode);
            } 
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }
    
    public static void entrenamiento_de_modelo()
    {
        try
        {

            Form form=new Form();

            Client client = ClientBuilder.newClient();
            WebTarget target = client.target("http://127.0.0.1:8000/api/entrenar_modelo/");

            Invocation.Builder builder = target.request("application/json");
            Invocation invocation = builder.buildPost(Entity.form(form));

            Response response = invocation.invoke();

            int statusCode = response.getStatus();
            String respuesta = response.readEntity(String.class);
            respuesta=respuesta.translateEscapes();
            respuesta=respuesta.substring(1,respuesta.length()-1);
            if (statusCode == 200) {
                System.out.println(respuesta);
                System.out.println(new JSONObject(respuesta).getString("success"));
                System.out.println(new JSONObject(respuesta).getString("nombre_modelo"));
                System.out.println(statusCode);
            } 
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }
    

    
    public static void reconocimiento()
    {
        try
        {
            String ruta_video = "D:\\jhon 9no semestre\\CIENCIA, TECNOLOGIA, SOCIEDAD E INNOVACION\\proyectofinal\\reconocimiento_facial\\fmark.PNG";
            InputStream is = new FileInputStream(ruta_video);
            byte[] bytes = is.readAllBytes();


            Form form=new Form();

            form.param("imagen", Base64.getEncoder().encodeToString(bytes));
            form.param("model", "model94a3bc9e-8ee7-41ff-8311-1e6ce9c380f2.xml.xml");

            Client client = ClientBuilder.newClient();
            WebTarget target = client.target("http://127.0.0.1:8000/api/reconocer/");

            Invocation.Builder builder = target.request("application/json");
            Invocation invocation = builder.buildPost(Entity.form(form));

            Response response = invocation.invoke();

            int statusCode = response.getStatus();
            String respuesta = response.readEntity(String.class);
            respuesta=respuesta.translateEscapes();
            respuesta=respuesta.substring(1,respuesta.length()-1);
            if (statusCode == 200) {
                System.out.println(respuesta);
                System.out.println(new JSONObject(respuesta).getString("success"));
                System.out.println(new JSONObject(respuesta).getString("etiqueta"));
                System.out.println(new JSONObject(respuesta).getBoolean("reconocio"));
                System.out.println(new JSONObject(respuesta).getString("base64img"));
                System.out.println(statusCode);
            } 
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    } 

    private static void base64prueba() 
    {
        try{
            String ruta_video = "D:\\jhon 9no semestre\\CIENCIA, TECNOLOGIA, SOCIEDAD E INNOVACION\\proyectofinal\\reconocimiento_facial\\video_mark.mp4";
            InputStream is = new FileInputStream(ruta_video);
            byte[] bytes = is.readAllBytes();
            System.out.println(Base64.getEncoder().encodeToString(bytes));   
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }
    
    
}
