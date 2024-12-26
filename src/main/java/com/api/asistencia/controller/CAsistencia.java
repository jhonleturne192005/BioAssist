/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.asistencia.controller;

import com.api.asistencia.models.ModelAsistencia;
import com.api.asistencia.models.ModelCurso;
import com.api.asistencia.models.ModelMatriculacion;
import com.api.asistencia.models.ModelPersona;
import com.api.asistencia.service.SAsistencia;
import com.api.asistencia.service.SCurso;
import com.api.asistencia.utils.Messages;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author USUARIO
 */
@RestController
@RequestMapping("/api/asistencia")
public class CAsistencia 
{
    @Autowired 
    SAsistencia sasistencia;
    
    @PostMapping("/crear")
    public ResponseEntity<?> CrearAsistencia(ModelAsistencia modelasistencia)
    {
        Map<String,Object> response=new HashMap();
        try
        {
            if(modelasistencia.getIdpersona()!=null && modelasistencia.getIdhorario()!=null)
            {
                ModelAsistencia ma=sasistencia.guardar(modelasistencia);
                if(ma.getIdasistencia()>0)
                    response.put(Messages.SUCCESSFUL_KEY, Messages.DATOS_GUARDADOS); 
            }
            else
            {
                response.put(Messages.ERROR_KEY, Messages.ERROR_DATOS_INCOMPLETOS);
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

    
    @PostMapping("/listarporpersona")
    public ResponseEntity<?> ListarAsistenciaPorPersona(@RequestParam(value="idpersona") Long idpersona)
    {
        Map<String,Object> response=new HashMap();
        try
        {
            List<ModelAsistencia> lstmpg=sasistencia.ListarAsistenciaPorPersona(idpersona);

            if(!lstmpg.isEmpty())
            {
                response.put(Messages.SUCCESSFUL_KEY, Messages.OPERACION_CORRECTA);
                response.put(Messages.DATA, lstmpg);
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
    
    
    @PostMapping("/listarporhorario")
    public ResponseEntity<?> ListarAsistenciaPorHorario(@RequestParam(value="idhorario") Long idhorario)
    {
        Map<String,Object> response=new HashMap();
        try
        {
            List<ModelAsistencia> lstmpg=sasistencia.ListarAsistenciaPorHorario(idhorario);

            if(!lstmpg.isEmpty())
            {
                response.put(Messages.SUCCESSFUL_KEY, Messages.OPERACION_CORRECTA);
                response.put(Messages.DATA, lstmpg);
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
