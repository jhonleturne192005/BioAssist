/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.asistencia.controller;

import com.api.asistencia.models.ModelMaterias;
import com.api.asistencia.models.ModelPersona;
import com.api.asistencia.service.SMaterias;
import com.api.asistencia.service.SPersona;
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
@RequestMapping("/api/materia")
public class CMateria 
{
    @Autowired 
    SMaterias smaterias;
    
    
    @PostMapping("/crear")
    public ResponseEntity<?> CrearMateria(ModelMaterias modelmaterias)
    {
        Map<String,Object> response=new HashMap();
        try
        {
            if(modelmaterias.getMateria().trim()!="")
            {
                ModelMaterias mmg=smaterias.guardar(modelmaterias);
                if(mmg.getIdmateria()>0)
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
            if(error.contains(Messages.EX_MATERIA))
                response.put(Messages.ERROR_KEY, Messages.MATERIA_EXISTENTE);
            else
                response.put(Messages.ERROR_KEY, Messages.ERROR_SISTEMA);
        }
        
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK); 

    }
    
    @PostMapping("/desactivar")
    public ResponseEntity<?> DesactivarMateria(@RequestParam(value="idmateria") Long idmateria)
    {
        Map<String,Object> response=new HashMap();
        try
        {
            
            List<ModelMaterias> lstbusquedamaterias=smaterias.BuscarPorIdMateria(idmateria);
            
            if(idmateria!=null)
            {
                ModelMaterias mmg=smaterias.desactivar(lstbusquedamaterias.get(0));
                if(mmg.getIdmateria()>0)
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
    public ResponseEntity<?> ActivarMateria(@RequestParam(value="idmateria") Long idmateria)
    {
        Map<String,Object> response=new HashMap();
        try
        {
            
            List<ModelMaterias> lstbusquedamaterias=smaterias.BuscarPorIdMateria(idmateria);
            
            if(idmateria!=null)
            {
                ModelMaterias mmg=smaterias.activar(lstbusquedamaterias.get(0));
                if(mmg.getIdmateria()>0)
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
    public ResponseEntity<?> ActualizarMateria(ModelMaterias modelmaterias)
    {
        Map<String,Object> response=new HashMap();
        try
        {
            
            List<ModelMaterias> lstbusquedamaterias=smaterias.BuscarPorIdMateria(modelmaterias.getIdmateria());

            if(modelmaterias.getMateria().trim()!="")
            {
                ModelMaterias mma=lstbusquedamaterias.get(0);
                mma.setMateria(modelmaterias.getMateria());
                
                ModelMaterias mmg=smaterias.actualizar(mma);
                if(mmg.getIdmateria()>0)
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
    public ResponseEntity<?> ListarMaterias()
    {
        Map<String,Object> response=new HashMap();
        try
        {
            List<ModelMaterias> lstmaterias=smaterias.listar();

            if(!lstmaterias.isEmpty())
            {
                response.put(Messages.SUCCESSFUL_KEY, Messages.OPERACION_CORRECTA);
                response.put(Messages.DATA, lstmaterias);
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
    
    
    
    
    @PostMapping("/obtenermateriaid")
    public ResponseEntity<?> ObtenerMateriaPorID(@RequestParam(value="idmateria") Long idmateria)
    {
        Map<String,Object> response=new HashMap();
        try
        {
            List<ModelMaterias> lstmaterias=smaterias.BuscarPorIdMateria(idmateria);

            if(!lstmaterias.isEmpty())
            {
                response.put(Messages.SUCCESSFUL_KEY, Messages.OPERACION_CORRECTA);
                response.put(Messages.DATA, lstmaterias);
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
