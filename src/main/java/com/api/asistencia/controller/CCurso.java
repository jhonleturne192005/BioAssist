/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.asistencia.controller;

import com.api.asistencia.models.ModelCurso;
import com.api.asistencia.models.ModelMaterias;
import com.api.asistencia.service.SCurso;
import com.api.asistencia.service.SMaterias;
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
@RequestMapping("/api/curso")
public class CCurso 
{
    @Autowired 
    SCurso scurso;
    
    @PostMapping("/crear")
    public ResponseEntity<?> CrearCurso(ModelCurso modelcurso)
    {
        Map<String,Object> response=new HashMap();
        try
        {
            if(modelcurso.getCurso().trim()!="")
            {
                ModelCurso mmg=scurso.guardar(modelcurso);
                if(mmg.getIdcurso()>0)
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
            if(error.contains(Messages.EX_CURSO))
                response.put(Messages.ERROR_KEY, Messages.CURSO_EXISTENTE);
            else
                response.put(Messages.ERROR_KEY, Messages.ERROR_SISTEMA);
        }
        
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK); 

    }
    
    
    @PostMapping("/desactivar")
    public ResponseEntity<?> DesactivarCurso(@RequestParam(value="idcurso") Long idcurso)
    {
        Map<String,Object> response=new HashMap();
        try
        {
            if(idcurso!=null)
            {
                List<ModelCurso> lstbusquedacurso=scurso.BuscarPorIdCurso(idcurso);

                ModelCurso mmg=scurso.desactivar(lstbusquedacurso.get(0));
                if(mmg.getIdcurso()>0)
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
            response.put(Messages.ERROR_KEY, Messages.ERROR_SISTEMA);
        }
        
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK); 

    }
    
    
    @PostMapping("/activar")
    public ResponseEntity<?> ActivarCurso(@RequestParam(value="idcurso") Long idcurso)
    {
        Map<String,Object> response=new HashMap();
        try
        {
            if(idcurso!=null)
            {
                List<ModelCurso> lstbusquedacurso=scurso.BuscarPorIdCurso(idcurso);

                ModelCurso mmg=scurso.activar(lstbusquedacurso.get(0));
                if(mmg.getIdcurso()>0)
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
            response.put(Messages.ERROR_KEY, Messages.ERROR_SISTEMA);
        }
        
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK); 

    }
    
    
    
    @PostMapping("/actualizar")
    public ResponseEntity<?> ActualizarCurso(ModelCurso modelcurso)
    {
        Map<String,Object> response=new HashMap();
        try
        {
            
            List<ModelCurso> lstbusquedacursos=scurso.BuscarPorIdCurso(modelcurso.getIdcurso());

            if(modelcurso.getCurso().trim()!="")
            {
                ModelCurso mma=lstbusquedacursos.get(0);
                mma.setCurso(modelcurso.getCurso());
                
                ModelCurso mmg=scurso.actualizar(mma);
                if(mmg.getIdcurso()>0)
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
            response.put(Messages.ERROR_KEY, Messages.ERROR_SISTEMA);
        }
        
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK); 

    }    
    
    
    @PostMapping("/listar")
    public ResponseEntity<?> ListarCursos()
    {
        Map<String,Object> response=new HashMap();
        try
        {
            List<ModelCurso> lstcursos=scurso.listar();

            if(!lstcursos.isEmpty())
            {
                response.put(Messages.SUCCESSFUL_KEY, Messages.OPERACION_CORRECTA);
                response.put(Messages.DATA, lstcursos);
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
    
    
    @PostMapping("/obtenercursoid")
    public ResponseEntity<?> ObtenerCursoPorID(@RequestParam(value="idcurso") Long idcurso)
    {
        Map<String,Object> response=new HashMap();
        try
        {
            List<ModelCurso> lstcursos=scurso.BuscarPorIdCurso(idcurso);

            if(!lstcursos.isEmpty())
            {
                response.put(Messages.SUCCESSFUL_KEY, Messages.OPERACION_CORRECTA);
                response.put(Messages.DATA, lstcursos);
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
