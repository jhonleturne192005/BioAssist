/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.asistencia.controllerData;

import com.api.asistencia.models.ModelAsistencia;
import com.api.asistencia.models.ModelCurso;
import com.api.asistencia.models.ModelMateriasPorPersona;
import com.api.asistencia.service.SMaterias;
import com.api.asistencia.service.SMateriasPorPersona;
import com.api.asistencia.service.SPersona;
import com.api.asistencia.utils.Messages;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
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
    
    @Autowired 
    SPersona spersona;
    
    
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
    
    
    @PostMapping("/listar")
    public ResponseEntity<?> Listar()
    {
        Map<String,Object> response=new HashMap();
        try
        {
            List<ModelMateriasPorPersona> lstmateriasporpersonabusqueda=smateriasPorPersona.listar();

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
    
    
    
    @PostMapping("/listarporpersona")
    public ResponseEntity<?> ListarMateriaPorPersonaPorPersona(@RequestParam(value="correopersona") String correopersona)
    {
        Map<String,Object> response=new HashMap();
        try
        {
            Long idpersona=spersona.BuscarPersonaPorCorreo(correopersona).get(0).getIdpersona();
            
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
   
    @PostMapping("/listarasistenciamatriculados")
    public ResponseEntity<?> ListarAsistenciaMatriculados(@RequestParam(value="idmateriaporpersona") Long idmateriaporpersona)
    {
        Map<String,Object> response=new HashMap();
        try
        {
            System.out.println(idmateriaporpersona);
            String jsonstring=smateriasPorPersona.ListarAsistenciaMatriculados(idmateriaporpersona);
                        System.out.println(jsonstring);

            JSONArray ja =new JSONArray(jsonstring);
   
            response.put(Messages.SUCCESSFUL_KEY, Messages.OPERACION_CORRECTA);
            response.put(Messages.DATA, ja.toList());
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
    public ResponseEntity<?> ListarMateriaPorPersonaPorMateria(@RequestParam(value="idmateria") Long idmateria)
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

    
    @PostMapping("/listarmateriaporpersonaid")
    public ResponseEntity<?> ListarMateriaPorPersonaById(@RequestParam(value="idmateriaporpersona") Long idmateriaporpersona)
    {
        Map<String,Object> response=new HashMap();
        try
        {
            List<ModelMateriasPorPersona> lstmateriasporpersonabusqueda=smateriasPorPersona.BuscarPorIdMateriasPorPersona(idmateriaporpersona);

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
