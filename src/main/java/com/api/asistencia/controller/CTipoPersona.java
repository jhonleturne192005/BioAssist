/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.asistencia.controller;

import com.api.asistencia.models.ModelTipoPersona;
import com.api.asistencia.service.STipoPersona;
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
@RequestMapping("/api/tipopersona")
public class CTipoPersona 
{
    
    @Autowired
    STipoPersona stipopersona;
    
    
    @PostMapping("/creartipopersona")
    public ResponseEntity<?> CrearTipoPersona(ModelTipoPersona modeltipopersona)
    {
        Map<String,Object> response=new HashMap();
        try
        {
            if(modeltipopersona.getTipo().trim()!="")
            {
                ModelTipoPersona mtpg=stipopersona.guardar(modeltipopersona);
                if(mtpg.getIdtipopersona()>0)
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
            if(error.contains(Messages.EX_TIPO_PERSONA))
                response.put(Messages.ERROR_KEY, Messages.TIPO_PERSONA_EXISTENTE);
            else
                response.put(Messages.ERROR_KEY, Messages.ERROR_SISTEMA);
        }
        
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK); 

    }
    
    
    @PostMapping("/desactivar")
    public ResponseEntity<?> DesactivarTipoPersona(@RequestParam(value="idtipopersona") Long idtipopersona)
    {
        Map<String,Object> response=new HashMap();
        try
        {
            ModelTipoPersona mtpbusqueda=stipopersona.BuscarPorIdTipoPersona(idtipopersona);

            if(mtpbusqueda!=null)
            {
                ModelTipoPersona mtpg=stipopersona.desactivar(mtpbusqueda);
                if(mtpg.getIdtipopersona()>0)
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
    
    
    @PostMapping("/listar")
    public ResponseEntity<?> ListarTipoPersona()
    {
        Map<String,Object> response=new HashMap();
        try
        {
            List<ModelTipoPersona> mtplista=stipopersona.listar();

            if(!mtplista.isEmpty())
            {
                response.put(Messages.SUCCESSFUL_KEY, Messages.OPERACION_CORRECTA);
                response.put(Messages.DATA, mtplista);
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
    
}
