/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.asistencia.controllerData;

import com.api.asistencia.models.ModelAsistencia;
import com.api.asistencia.models.ModelCurso;
import com.api.asistencia.models.ModelHorario;
import com.api.asistencia.models.ModelMatriculacion;
import com.api.asistencia.models.ModelPersona;
import com.api.asistencia.service.SAsistencia;
import com.api.asistencia.service.SCurso;
import com.api.asistencia.service.SHorario;
import com.api.asistencia.service.SPersona;
import com.api.asistencia.service.SRecursos;
import com.api.asistencia.utils.ApiReconocimiento;
import com.api.asistencia.utils.AsignarRecursoRequestCombinada;
import com.api.asistencia.utils.CreateHash;
import com.api.asistencia.utils.DistanciaHaversine;
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
@RequestMapping("/api/asistencia")
public class CAsistencia 
{
  
    @Autowired 
    SAsistencia sasistencia;
    

    @PostMapping("/listarmateriaparaasistenciaporestudiante")
    public ResponseEntity<?> ListarMateriaPorAsistenciaPorEstudiante(@RequestParam(value="idpersona") Long idpersona)
    {
        System.out.println("jajaja= "+idpersona);
        Map<String,Object> response=new HashMap();
        try
        {
            List<ModelHorario> lstmpg=sasistencia.ListarMateriaParaAsistenciaPorEstudiante(idpersona);

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
    
 
    
    
    
    /*NO SE USAN*/
    
    
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
