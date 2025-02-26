/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.asistencia.utils;

import com.api.asistencia.service.SRecursos;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Form;
import jakarta.ws.rs.core.Response;
import java.util.Base64;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author USUARIO
 */
public class ApiReconocimiento 
{
    
    @Autowired
    private static SRecursos srecursos;
    
    private static String URL_API_PYTHON="http://127.0.0.1:8000/api";
    
    
    public static String EntrenarModelo()
    {
        try
        {
            Form form=new Form();

            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(URL_API_PYTHON+"/entrenar_modelo/");

            Invocation.Builder builder = target.request("application/json");
            Invocation invocation = builder.buildPost(Entity.form(form));
            
            Response response = invocation.invoke();

            int statusCode = response.getStatus();
            String respuesta = response.readEntity(String.class);
            respuesta=respuesta.translateEscapes();
            respuesta=respuesta.substring(1,respuesta.length()-1);
            JSONObject jo=new JSONObject(respuesta);
            if (statusCode == 200) {
                System.out.println(respuesta);
                System.out.println(new JSONObject(respuesta).getString("success"));
                System.out.println(new JSONObject(respuesta).getString("nombre_modelo"));
                System.out.println(statusCode);
            } 
            return jo.getString("nombre_modelo");
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        return null;
    }
    
    
    
    public static String AsignarRecurso(String base64recurso)
    {
        try
        {
            Form form=new Form();
            form.param("video", base64recurso);

            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(URL_API_PYTHON+"/asignar_recurso/");

            Invocation.Builder builder = target.request("application/json");
            Invocation invocation = builder.buildPost(Entity.form(form));
            
            Response response = invocation.invoke();

            int statusCode = response.getStatus();
            String respuesta = response.readEntity(String.class);
            respuesta=respuesta.translateEscapes();
            respuesta=respuesta.substring(1,respuesta.length()-1);
            JSONObject jo=new JSONObject(respuesta);
            if (statusCode == 200) {
                System.out.println(respuesta);
                System.out.println(new JSONObject(respuesta).getString("success"));
                System.out.println(new JSONObject(respuesta).getString("ooid"));
                System.out.println(statusCode);
            } 
            return jo.getString("ooid");
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        return null;
    }
    
    
    public static JSONObject Reconocer(String imagebase64)
    {
        try
        {
            Form form=new Form();
            form.param("imagen", imagebase64);
            form.param("model", srecursos.listar().get(0).getNombremodeloxml());

            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(URL_API_PYTHON+"/reconocer/");

            Invocation.Builder builder = target.request("application/json");
            Invocation invocation = builder.buildPost(Entity.form(form));
            
            Response response = invocation.invoke();

            int statusCode = response.getStatus();
            String respuesta = response.readEntity(String.class);
            respuesta=respuesta.translateEscapes();
            respuesta=respuesta.substring(1,respuesta.length()-1);
            JSONObject jo=new JSONObject(respuesta);
            if (statusCode == 200) {
                System.out.println(respuesta);
                System.out.println(new JSONObject(respuesta).getString("success"));
                System.out.println(new JSONObject(respuesta).getString("nombre_modelo"));
                System.out.println(statusCode);
            } 
            return jo;
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        return null;
    }
    
}
