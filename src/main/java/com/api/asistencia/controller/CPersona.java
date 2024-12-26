/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.asistencia.controller;

import com.api.asistencia.models.ModelPersona;
import com.api.asistencia.models.ModelTipoPersona;
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
@RequestMapping("/api/persona")
public class CPersona 
{
    @Autowired 
    SPersona spersona;
    
    
    @PostMapping("/crear")
    public ResponseEntity<?> CrearPersona(ModelPersona modelpersona)
    {
        Map<String,Object> response=new HashMap();
        try
        {
            if(modelpersona.getNombres().trim()!="" && 
               modelpersona.getApellidos().trim()!="" &&
               modelpersona.getContrasenia().trim()!="" &&
               modelpersona.getCorreo().trim()!="")
            {
                ModelPersona mpg=spersona.guardar(modelpersona);
                if(mpg.getIdpersona()>0)
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
            if(error.contains(Messages.EX_CORREO))
                response.put(Messages.ERROR_KEY, Messages.CORREO_EXISTENTE);
            else
                response.put(Messages.ERROR_KEY, Messages.ERROR_SISTEMA);
        }
        
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK); 

    }
    
    
    @PostMapping("/desactivar")
    public ResponseEntity<?> DesactivarPersona(@RequestParam(value="idpersona") Long idpersona)
    {
        Map<String,Object> response=new HashMap();
        try
        {
            List<ModelPersona> lstmpg=spersona.BuscarPorIdPersona(idpersona);

            if(!lstmpg.isEmpty())
            {
                ModelPersona mpg=spersona.desactivar(lstmpg.get(0));
                if(mpg.getIdpersona()>0)
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
    public ResponseEntity<?> ListarPersonas()
    {
        Map<String,Object> response=new HashMap();
        try
        {
            List<ModelPersona> lstmpg=spersona.listar();

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
    
    @PostMapping("/actualizardatosadmin")
    public ResponseEntity<?> ActualizarDatosadmin(ModelPersona modelpersona)
    {
        Map<String,Object> response=new HashMap();
        try
        {
            Boolean actualizar_contrasenia=false;
            List<ModelPersona> lstmpg=spersona.BuscarPorIdPersona(modelpersona.getIdpersona());

            if(!lstmpg.isEmpty())
            {
                ModelPersona mp=lstmpg.get(0);
                mp.setIdtipopersona(modelpersona.getIdtipopersona());
                mp.setNombres(modelpersona.getNombres().trim());
                mp.setApellidos(modelpersona.getApellidos().trim());
                mp.setCorreo(modelpersona.getCorreo().trim());

                if(!"".equals(modelpersona.getContrasenia().trim())){
                    actualizar_contrasenia=true;
                    mp.setContrasenia(modelpersona.getContrasenia());
                }

                ModelPersona mpg=spersona.actualizar(modelpersona,actualizar_contrasenia);
                if(mpg.getIdpersona()>0)
                    response.put(Messages.SUCCESSFUL_KEY, Messages.DATOS_GUARDADOS); 
                
                response.put(Messages.SUCCESSFUL_KEY, Messages.ACTUALIZACION_CORRECTA);
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
    
    
    @PostMapping("/actualizardatosusuario")
    public ResponseEntity<?> ActualizarDatosUsuario(ModelPersona modelpersona)
    {
        Map<String,Object> response=new HashMap();
        try
        {
            Boolean actualizar_contrasenia=false;
            List<ModelPersona> lstmpg=spersona.BuscarPorIdPersona(modelpersona.getIdpersona());

            if(!lstmpg.isEmpty())
            {
                ModelPersona mp=lstmpg.get(0);
                
                mp.setNombres(!"".equals(modelpersona.getNombres().trim())?modelpersona.getNombres().trim():"");
                mp.setApellidos(!"".equals(modelpersona.getApellidos().trim())?modelpersona.getApellidos().trim():"");
                mp.setCorreo(!"".equals(modelpersona.getCorreo().trim())?modelpersona.getCorreo().trim():"");

                mp.setNumero_telefono(!"".equals(modelpersona.getNumero_telefono().trim())?modelpersona.getNumero_telefono().trim():"");
                mp.setCredenciales_telefono(!"".equals(modelpersona.getCredenciales_telefono().trim())?modelpersona.getCredenciales_telefono().trim():"");
                mp.setClave_generada_telefono(!"".equals(modelpersona.getClave_generada_telefono().trim())?modelpersona.getClave_generada_telefono().trim():"");
                mp.setHash_generado(!"".equals(modelpersona.getHash_generado().trim())?modelpersona.getHash_generado().trim():"");
                mp.setEtiqueta_reconocer(!"".equals(modelpersona.getEtiqueta_reconocer().trim())?modelpersona.getEtiqueta_reconocer().trim():"");

                if(!"".equals(modelpersona.getContrasenia().trim())){
                    actualizar_contrasenia=true;
                    mp.setContrasenia(modelpersona.getContrasenia());
                }

                ModelPersona mpg=spersona.actualizar(modelpersona,actualizar_contrasenia);
                if(mpg.getIdpersona()>0)
                    response.put(Messages.SUCCESSFUL_KEY, Messages.DATOS_GUARDADOS); 
                
                response.put(Messages.SUCCESSFUL_KEY, Messages.ACTUALIZACION_CORRECTA);
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
