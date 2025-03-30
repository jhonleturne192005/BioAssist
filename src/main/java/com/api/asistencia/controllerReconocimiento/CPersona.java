/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.asistencia.controllerReconocimiento;

import com.api.asistencia.models.ModelPersona;
import com.api.asistencia.models.ModelTipoPersona;
import com.api.asistencia.service.SPersona;
import com.api.asistencia.utils.CreateHash;
import com.api.asistencia.utils.ManagedJson;
import com.api.asistencia.utils.Messages;
import com.api.asistencia.utils.encryptString;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    
    
    @PostMapping("/login")
    public ResponseEntity<?> Login(@RequestParam(value="correo") String correo,
                                    @RequestParam(value="contrasenia") String contrasenia,
                                    @RequestParam(value="longitud") String longitud,
                                    @RequestParam(value="latitud") String latitud
                                    ,HttpSession session)
    {
        Map<String,Object> response=new HashMap();
        try
        {
           
            System.out.println("LOGINNNN");
            System.out.println("LATITUD: "+latitud);
            System.out.println("LONGITUD: "+longitud);
            
            if(correo.trim()!="" && contrasenia.trim()!="")
            {
                List<ModelPersona> lstmpb=spersona.BuscarPersonaPorCorreo(correo.trim());

                if(!lstmpb.isEmpty())
                {
                    ModelPersona mpd=lstmpb.get(0);
                    mpd.setLatitud(latitud);
                    mpd.setLongitud(longitud);
                    mpd=spersona.actualizar(mpd,false);
    
                    if(mpd.getCorreo().trim().equals(correo) && mpd.getContrasenia().trim().equals(encryptString.encriptPassword(contrasenia)))
                    {
                        response.put(Messages.SUCCESSFUL_KEY, Messages.INICIO_SESSION_CORRECTO); 
                        response.put(Messages.CORREO, correo.trim()); 
                        response.put("etiqueta_reconocer", lstmpb.get(0).getEtiquetareconocer());
                        response.put("id", lstmpb.get(0).getIdpersona()); 
                        response.put("admin", mpd.getAdministrador()); 
                        response.put("tipousuario", mpd.getIdtipopersona().getTipo().toLowerCase()); 
                        response.put(Messages.ESTADO,true); 
                        session.setAttribute(correo, mpd);
                    }
                    else
                    {
                        response.put(Messages.SUCCESSFUL_KEY, Messages.DATOS_INCORRECTOS); 
                        response.put(Messages.ESTADO,false); 
                    }
                }
                else
                {
                    response.put(Messages.SUCCESSFUL_KEY, Messages.DATOS_INCORRECTOS); 
                    response.put(Messages.ESTADO,false); 
                }
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
    
    @PostMapping("/logout")
    public ResponseEntity<?> Logout(@RequestParam(value="correo") String correo
                                    ,HttpSession session)
    {
        Map<String,Object> response=new HashMap();
        try
        {
            ModelPersona mpd=(ModelPersona) session.getAttribute(correo);

            if(mpd!=null)
            {
                session.removeAttribute(correo);
                response.put(Messages.ERROR_KEY, Messages.CERRAR_SESSION);
                response.put(Messages.ESTADO, true);
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
    
    
    @PostMapping("/crear")
    public ResponseEntity<?> CrearPersona(ModelPersona modelpersona)
    {
        Map<String,Object> response=new HashMap();
        try
        {
            if(modelpersona.getNombres().trim()!="" && 
               modelpersona.getApellidos().trim()!="" &&
               modelpersona.getContrasenia().trim()!="" &&
               modelpersona.getCorreo().trim()!="" &&
               modelpersona.getIdtipopersona()!=null)
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
    
    
   
    @PostMapping("/activar")
    public ResponseEntity<?> ActivarPersona(@RequestParam(value="idpersona") Long idpersona)
    {
        Map<String,Object> response=new HashMap();
        try
        {
            List<ModelPersona> lstmpg=spersona.BuscarPorIdPersona(idpersona);

            if(!lstmpg.isEmpty())
            {
                ModelPersona mpg=spersona.activar(lstmpg.get(0));
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
    
    @PostMapping("/activaredicion")
    public ResponseEntity<?> ActivarEdicionPersona(@RequestParam(value="idpersona") Long idpersona)
    {
        Map<String,Object> response=new HashMap();
        try
        {
            List<ModelPersona> lstmpg=spersona.BuscarPorIdPersona(idpersona);

            if(!lstmpg.isEmpty())
            {
                ModelPersona mpg=spersona.activarEdicion(lstmpg.get(0));
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
    
    
    @PostMapping("/desactivaredicion")
    public ResponseEntity<?> desactivarEdicionPersona(@RequestParam(value="idpersona") Long idpersona)
    {
        Map<String,Object> response=new HashMap();
        try
        {
            List<ModelPersona> lstmpg=spersona.BuscarPorIdPersona(idpersona);

            if(!lstmpg.isEmpty())
            {
                ModelPersona mpg=spersona.desactivarEdicion(lstmpg.get(0));
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
    
    
    
    @PostMapping("/listarprofesores")
    public ResponseEntity<?> ListarPersonasProfesores()
    {
        Map<String,Object> response=new HashMap();
        try
        {
            List<ModelPersona> lstmpg=spersona.listarPersonaProfesores();

            
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
    
    
    @PostMapping("/obtenerpersonaid")
    public ResponseEntity<?> ObtenerPersonaPorID(@RequestParam(value="idpersona") Long idpersona)
    {
        Map<String,Object> response=new HashMap();
        try
        {
            List<ModelPersona> lstmpg=spersona.BuscarPorIdPersona(idpersona);

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

            if(!lstmpg.isEmpty() &&
                    !"".equals(modelpersona.getNombres().trim()) &&
                    !"".equals(modelpersona.getApellidos().trim()) && 
                    !"".equals(modelpersona.getCorreo().trim()) && 
                    !"".equals(modelpersona.getNumero_telefono().trim()) && 
                    !"".equals(modelpersona.getCredenciales_telefono().trim()) && 
                    !"".equals(modelpersona.getIdgenero()!=null)
                    )
            {
                ModelPersona mp=lstmpg.get(0);
                
                mp.setNombres(modelpersona.getNombres().trim());
                mp.setApellidos(modelpersona.getApellidos().trim());
                mp.setCorreo(modelpersona.getCorreo().trim());
                mp.setNumero_telefono(modelpersona.getNumero_telefono().trim());
                //mp.setEtiquetareconocer(!modelpersona.getEtiquetareconocer().trim().isBlank() || !modelpersona.getEtiquetareconocer().trim().isEmpty()?mp.getEtiquetareconocer().trim():"");

                
                if(!"".equals(modelpersona.getContrasenia().trim())){
                    actualizar_contrasenia=true;
                    mp.setContrasenia(modelpersona.getContrasenia());
                }
                
                if(mp.getEstadopersonaedicion()) //para que pueda crear sus datos
                {
                    if(!modelpersona.getCredenciales_telefono().trim().equals(""))
                    {

                        //creacion de claves unicas
                        String [] vechash=CreateHash.crearhashdatos(modelpersona.getCredenciales_telefono().trim());
                        mp.setCredenciales_telefono(modelpersona.getCredenciales_telefono().trim());
                        mp.setClave_generada_telefono(vechash[1]);
                        mp.setHash_generado(vechash[0]);
                        mp.setEstadopersonaedicion(false); //desactiva la edicion 
                        response.put(Messages.DISPOSITIVO_MESSAGE, Messages.MENSAJE_DISPOSITIVO);
                        response.put(Messages.DISPOSITIVO_ACTUALIZADO, true);
                        
                        System.out.println("id telefono= "+modelpersona.getCredenciales_telefono().trim());
                        System.out.println("hash sistema= "+vechash[0]);
                        
                    }
                    else
                    {
                        response.put(Messages.ERROR_KEY, Messages.ERROR_SISTEMA);
                        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);  
                    }
                }else  response.put(Messages.DISPOSITIVO_ACTUALIZADO, false);
                

                ModelPersona mpg=spersona.actualizar(mp,actualizar_contrasenia);
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
    
    
    @PostMapping("/actualizaretiquetareconocimientousuario")
    public ResponseEntity<?> ActualizarEtiquetaReconocimientoUsuario(@RequestParam(value="idpersona") Long idpersona,
            @RequestParam(value="etiqueta") String etiqueta,
                                                HttpSession session)
    {
        Map<String,Object> response=new HashMap();
        try
        {
            System.out.println("se actualizooooooooooooo");
            if(idpersona!=null)
            {
                List<ModelPersona> lstmpg=spersona.BuscarPorIdPersona(idpersona);
                ModelPersona mp=lstmpg.get(0);
                mp.setEtiquetareconocer(etiqueta);
                ModelPersona mpg=spersona.actualizar(mp,false);
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
}
