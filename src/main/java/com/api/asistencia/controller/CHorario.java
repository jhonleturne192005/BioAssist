/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.asistencia.controller;

import com.api.asistencia.models.ModelCurso;
import com.api.asistencia.models.ModelHorario;
import com.api.asistencia.service.SHorario;
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
@RequestMapping("/api/horario")
public class CHorario 
{
    
    @Autowired
    SHorario shorario;
    
    
    @PostMapping("/crear")
    public ResponseEntity<?> CrearHorario(ModelHorario modelhorario)
    {
        Map<String,Object> response=new HashMap();
        try
        {
            if(modelhorario.getIdmateriasporpersona()!=null && 
               modelhorario.getIddiassemana()!=null && modelhorario.getHora_inicio()!=null && 
                modelhorario.getMinuto_inicio()!=null && modelhorario.getHora_fin()!=null && 
                    modelhorario.getMinuto_fin()!=null)
            {
                ModelHorario mmg=shorario.guardar(modelhorario);
                if(mmg.getIdhorario()>0)
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
    public ResponseEntity<?> ActualizarHorario(ModelHorario modelhorario)
    {
        Map<String,Object> response=new HashMap();
        try
        {
            if(modelhorario.getIdmateriasporpersona()!=null && 
               modelhorario.getIddiassemana()!=null && modelhorario.getHora_inicio()!=null && 
                modelhorario.getMinuto_inicio()!=null && modelhorario.getHora_fin()!=null && 
                    modelhorario.getMinuto_fin()!=null)
            {
                List<ModelHorario> lsthorarios=shorario.ListarHorario(modelhorario.getIdhorario());
                ModelHorario mo=lsthorarios.get(0);
                mo.setIdmateriasporpersona(modelhorario.getIdmateriasporpersona());
                mo.setIddiassemana(modelhorario.getIddiassemana());
                mo.setHora_inicio(modelhorario.getHora_inicio());
                mo.setHora_fin(modelhorario.getHora_fin());
                mo.setMinuto_inicio(modelhorario.getMinuto_inicio());
                mo.setMinuto_fin(modelhorario.getMinuto_fin());
                
                ModelHorario mmg=shorario.actualizar(mo);
                if(mmg.getIdhorario()>0)
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

    
    @PostMapping("/listarhorarioporcurso")
    public ResponseEntity<?> ListarHorariosporcurso(@RequestParam(value="idcurso") Long idcurso)
    {
        Map<String,Object> response=new HashMap();
        try
        {
            List<ModelHorario> lsthorarios=shorario.ListarHorarioPorCurso(idcurso);

            if(!lsthorarios.isEmpty())
            {
                response.put(Messages.SUCCESSFUL_KEY, Messages.OPERACION_CORRECTA);
                response.put(Messages.DATA, lsthorarios);
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
    
    
    @PostMapping("/obtenerhorarioporid")
    public ResponseEntity<?> ObtenerHorarioPorId(@RequestParam(value="idhorario") Long idhorario)
    {
        Map<String,Object> response=new HashMap();
        try
        {
            List<ModelHorario> lsthorarios=shorario.ListarHorario(idhorario);

            if(!lsthorarios.isEmpty())
            {
                response.put(Messages.SUCCESSFUL_KEY, Messages.OPERACION_CORRECTA);
                response.put(Messages.DATA, lsthorarios);
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
            List<ModelHorario> lsthorarios=shorario.Listar();

            if(!lsthorarios.isEmpty())
            {
                response.put(Messages.SUCCESSFUL_KEY, Messages.OPERACION_CORRECTA);
                response.put(Messages.DATA, lsthorarios);
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
