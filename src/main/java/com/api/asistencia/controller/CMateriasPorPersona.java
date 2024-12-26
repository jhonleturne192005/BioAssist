/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.asistencia.controller;

import com.api.asistencia.models.ModelAsistencia;
import com.api.asistencia.models.ModelCurso;
import com.api.asistencia.models.ModelMateriasPorPersona;
import com.api.asistencia.service.SMaterias;
import com.api.asistencia.service.SMateriasPorPersona;
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
@RequestMapping("/api/materiaporpersona")
public class CMateriasPorPersona 
{
    @Autowired 
    SMateriasPorPersona smateriasPorPersona;  
    
    
    @PostMapping("/crear")
    public ResponseEntity<?> CrearMateriaPorPersona(ModelMateriasPorPersona modelmateriasporpersona)
    {
        Map<String,Object> response=new HashMap();
        try
        {
            if(modelmateriasporpersona.getIdmateria()!=null && modelmateriasporpersona.getIdpersona()!=null)
            {
                ModelMateriasPorPersona mmpp=smateriasPorPersona.guardar(modelmateriasporpersona);
                if(mmpp.getIdmateriaporpersona()>0)
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
    
    
    @PostMapping("/actualizar")
    public ResponseEntity<?> ActualizarMateriaPorPersona(ModelMateriasPorPersona modelmateriasporpersona)
    {
        Map<String,Object> response=new HashMap();
        try
        {
            if(modelmateriasporpersona.getIdmateria()!=null && modelmateriasporpersona.getIdpersona()!=null)
            {
                List<ModelMateriasPorPersona> lstmateriasporpersonabusqueda=smateriasPorPersona.BuscarPorIdMateriasPorPersona(modelmateriasporpersona.getIdmateriaporpersona());
                ModelMateriasPorPersona mm=lstmateriasporpersonabusqueda.get(0);
                mm.setIdmateria(modelmateriasporpersona.getIdmateria());
                mm.setIdpersona(modelmateriasporpersona.getIdpersona());
                
                ModelMateriasPorPersona mmpp=smateriasPorPersona.guardar(mm);
                if(mmpp.getIdmateriaporpersona()>0)
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
            List<ModelMateriasPorPersona> lstmateriasporpersonabusqueda=smateriasPorPersona.listarPorPersona(idpersona);

            if(!lstmateriasporpersonabusqueda.isEmpty())
            {
                response.put(Messages.SUCCESSFUL_KEY, Messages.OPERACION_CORRECTA);
                response.put(Messages.DATA, lstmateriasporpersonabusqueda);
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
    
    
    @PostMapping("/listarpormateria")
    public ResponseEntity<?> ListarAsistenciaPorMateria(@RequestParam(value="idmateria") Long idmateria)
    {
        Map<String,Object> response=new HashMap();
        try
        {
            List<ModelMateriasPorPersona> lstmateriasporpersonabusqueda=smateriasPorPersona.listarPorMateria(idmateria);

            if(!lstmateriasporpersonabusqueda.isEmpty())
            {
                response.put(Messages.SUCCESSFUL_KEY, Messages.OPERACION_CORRECTA);
                response.put(Messages.DATA, lstmateriasporpersonabusqueda);
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
