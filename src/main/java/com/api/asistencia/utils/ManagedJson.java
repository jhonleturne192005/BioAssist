/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.asistencia.utils;

import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author USUARIO
 */
public class ManagedJson 
{
    //[]
    public static List<Object> StringToJsonArrayList(String jsonarray)
    {
        JSONArray jo=new JSONArray(jsonarray);
        return jo.toList();
    }
    
    //{}
    public static Map<String,Object> StringToJsonObject(String json)
    {
        JSONObject jo=new JSONObject(json);
        return jo.toMap();
    }
    
    //[{}]
    public static Map<String,Object> StringToJsonArrayToJsonObjectMAP(String jsonarray)
    {
        JSONArray jo=new JSONArray(jsonarray);
        return jo.getJSONObject(0).toMap();
    }
}
