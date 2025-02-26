/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.asistencia.controller;

import com.api.asistencia.models.ModelMaterias;
import com.api.asistencia.models.ModelPersona;
import com.api.asistencia.models.ModelRecursos;
import com.api.asistencia.service.SRecursos;
import com.api.asistencia.utils.ApiReconocimiento;
import com.api.asistencia.utils.CreateHash;
import com.api.asistencia.utils.Messages;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author USUARIO
 */
@RestController
@RequestMapping("/api/reconocimiento")
public class CReconocimiento 
{
    
    @Autowired
    SRecursos srecursos;
    
  
    @PostMapping("/entrenar")
    public ResponseEntity<?> EntrenarModelo()
    {
        Map<String,Object> response=new HashMap();
        try
        {   
            String nombre_modelo=ApiReconocimiento.EntrenarModelo();
            if(nombre_modelo!=null)
            {
                List<ModelRecursos> lstmodelrecursos=srecursos.listar();
                nombre_modelo=nombre_modelo+".xml";
                ModelRecursos mr=null;
                
                if(!lstmodelrecursos.isEmpty())
                {
                    mr=lstmodelrecursos.get(0);
                    mr.setNombremodeloxml(nombre_modelo);
                }
                else
                {
                    mr=new ModelRecursos();
                    mr.setNombremodeloxml(nombre_modelo);
                }
                
                srecursos.crear_actualizar(mr);

                response.put(Messages.SUCCESSFUL_KEY, Messages.ENTRENADO_CORRECTAMENTE);
                response.put(Messages.MODELO, nombre_modelo);
            }
            else
            {
                response.put(Messages.ERROR_KEY, Messages.ERROR_SISTEMA);
                return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);  
            }
        }
        catch(Exception ex)
        {
            String error=ex.getMessage();
            System.out.println(error);
            response.put(Messages.ERROR_KEY, Messages.ERROR_SISTEMA);
        }
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK); 
    }
   
    
    @PostMapping("/listar")
    public ResponseEntity<?> ListarRecursos()
    {
        Map<String,Object> response=new HashMap();
        try
        {
            List<ModelRecursos> lstmodelrecursos=srecursos.listar();

            if(!lstmodelrecursos.isEmpty())
            {
                response.put(Messages.SUCCESSFUL_KEY, Messages.OPERACION_CORRECTA);
                response.put(Messages.DATA, lstmodelrecursos);
            }
            else
            {
                response.put(Messages.ERROR_KEY, Messages.NO_DATA);
                return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);  
            }
        }
        catch(Exception ex)
        {
            String error=ex.getMessage();
            System.out.println(error);
            response.put(Messages.ERROR_KEY, Messages.ERROR_SISTEMA);
        }
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK); 
    }
    
    @PostMapping("/asignarrecurso")
    public ResponseEntity<?> AsignarRecurso(@RequestBody  String base64recurso)
    {
        Map<String,Object> response=new HashMap();
        try
        {   
            System.out.println("Recurso: jejeje ");
            //System.out.println(new JSONObject( base64recurso).getString("base64recurso"));

            String ooiduser=ApiReconocimiento.AsignarRecurso(new JSONObject( base64recurso).getString("base64recurso").replace("\n",""));

            System.out.println("OOID= "+ooiduser);
            
//String ooiduser="";
            
            if(ooiduser!=null)
            {
                response.put(Messages.SUCCESSFUL_KEY, Messages.ENTRENADO_CORRECTAMENTE);
                response.put(Messages.OOID, ooiduser);
            }
            else
            {
                response.put(Messages.ERROR_KEY, Messages.ERROR_SISTEMA);
                return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);  
            }
        }
        catch(Exception ex)
        {
            String error=ex.getMessage();
            System.out.println(error);
            response.put(Messages.ERROR_KEY, Messages.ERROR_SISTEMA);
        }
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK); 
    }
   
    
    @PostMapping("/reconocer")
    public ResponseEntity<?> Reconocer(@RequestParam(value="base64recurso") String base64recurso)
    {
        Map<String,Object> response=new HashMap();
        try
        {
            System.out.println("Recurso: jejeje ");
            System.out.println(base64recurso);
            //JSONObject jo=ApiReconocimiento.Reconocer(base64recurso);
            JSONObject jo=null;

            if(jo!=null)
            {
                response.put(Messages.SUCCESSFUL_KEY, Messages.ENTRENADO_CORRECTAMENTE);
                response.put(Messages.DATA, jo);
            }
            else
            {
                response.put(Messages.ERROR_KEY, Messages.ERROR_SISTEMA);
                return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);  
            }
        }
        catch(Exception ex)
        {
            String error=ex.getMessage();
            System.out.println(error);
            response.put(Messages.ERROR_KEY, Messages.ERROR_SISTEMA);
        }
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK); 
    }

  
}
