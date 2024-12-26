/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.asistencia.controller;

import com.api.asistencia.models.ModelCurso;
import com.api.asistencia.models.ModelMatriculacion;
import com.api.asistencia.models.ModelPersona;
import com.api.asistencia.service.SMaterias;
import com.api.asistencia.service.SMatriculacion;
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
@RequestMapping("/api/matriculacion")
public class CMatriculacion 
{
    @Autowired 
    SMatriculacion smatriculacion;

    @PostMapping("/crear")
    public ResponseEntity<?> CrearMatriculacion(ModelMatriculacion modelmatriculacion)
    {
        Map<String,Object> response=new HashMap();
        try
        {
            if(modelmatriculacion.getIdpersona()!=null && modelmatriculacion.getIdmateriasporpersona()!=null)
            {
                ModelMatriculacion mm=smatriculacion.guardar(modelmatriculacion);
                if(mm.getIdmatriculacion()>0)
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
    

    
    @PostMapping("/listarporestudiante")
    public ResponseEntity<?> ListarMatriculacionPorEstudiante(@RequestParam(value="idpersona",defaultValue="")Long idpersona)
    {
        Map<String,Object> response=new HashMap();
        try
        {
            List<ModelMatriculacion> lstmpg=smatriculacion.listarPorEstudiante(idpersona);

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
    
    
    @PostMapping("/listar")
    public ResponseEntity<?> Listar()
    {
        Map<String,Object> response=new HashMap();
        try
        {
            List<ModelMatriculacion> lstmpg=smatriculacion.listar();

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
