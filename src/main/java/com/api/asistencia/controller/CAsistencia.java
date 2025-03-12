/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.asistencia.controller;

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
    
    @Autowired
    SHorario shorario;
    
    @Autowired 
    SPersona spersona;
    
    @Autowired
    private SRecursos srecursos;
    
    @PostMapping("/crear")
    public ResponseEntity<?> CrearAsistencia(
            @RequestBody AsignarRecursoRequestCombinada data)
    {
        Map<String,Object> response=new HashMap();
        try
        {
            List<ModelPersona> lstmodelpersona=spersona.BuscarPorIdPersona(data.getIdpersona());
            List<ModelHorario> lstmodelhorario=shorario.ListarHorario(data.getIdhorario());

            String idphone=data.getIdphone();
            String base64recurso=data.getBase64recurso();
            
            Boolean usuarioreconocido=false;
            Boolean habilitado_asistencia=false;
            Boolean dentro_rango=false;

            if(lstmodelpersona.get(0).getIdpersona()!=null && !idphone.equals("") && !base64recurso.equals(""))
            {
                JSONObject jo=ApiReconocimiento.Reconocer(base64recurso,srecursos.listar().get(0).getNombremodeloxml().trim());
                String etiqueta=jo.getString("etiqueta"); //si no existe genera una excepcion que es capturada
                //a3889740-49e6-47fd-a79b-7503fc6b8f21
                
                //String etiqueta="a3889740-49e6-47fd-a79b-7503fc6b8f21";
                List<ModelPersona> lstmp=spersona.BuscarPorEtiquetaPersona(etiqueta);
                
                if(!lstmp.isEmpty())
                {
                    if(lstmodelpersona.get(0).getIdpersona().equals(lstmp.get(0).getIdpersona()))
                        usuarioreconocido=true; 
                }
                else{
                    response.put(Messages.ERROR_KEY, "Usuario no reconocido");
                    return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST); 
                }
            }
            else{
                response.put(Messages.ERROR_KEY, Messages.ERROR_SISTEMA);
                return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);  
            }
            System.out.println("AAAAAAAAAAAAAAAAAAA= ");

            ModelHorario mh=lstmodelhorario.get(0);
            ModelPersona mp=lstmodelpersona.get(0);
            ModelPersona mprofesor=mh.getIdmateriasporpersona().getIdpersona();
  
            if(mp!=null && mh!=null && mprofesor!=null)
            {
                System.out.println("id telefono= "+data.getIdphone().trim());
                System.out.println("hash sistema= "+mp.getHash_generado().trim());
                String [] vechash=CreateHash.recrearhashdatos(data.getIdphone().trim(), mp.getHash_generado().trim());

                System.out.println("HASH 1= "+vechash[1]);
                System.out.println("HASH 2= "+mp.getClave_generada_telefono());

                if(vechash[1].equals(mp.getClave_generada_telefono()))
                    habilitado_asistencia=true;
                       
                
                System.out.println("ESTUDIANTE LATITUD= "+data.getLatitud());
                System.out.println("ESTUDIANTE LONGITUD= "+data.getLongitud());        
                
                System.out.println("PROFESOR LATITUD= "+mprofesor.getLatitud());
                System.out.println("PROFESOR LONGITUD= "+mprofesor.getLongitud());

                DistanciaHaversine dh=new DistanciaHaversine(
                        Double.parseDouble(data.getLatitud()), //ESTUDUIANTE
                        Double.parseDouble(data.getLongitud()),//ESTUDUIANTE
                        Double.parseDouble(mprofesor.getLatitud()),
                        Double.parseDouble(mprofesor.getLongitud()));
                

                
                
                System.out.println("RESULTADO EN KILOMETROS= "+dh.resultadoenkm);
                System.out.println("RESULTADO EN METROS= "+dh.resultadoenmetros);
                System.out.println("DENTRO DEL RADIO?= "+dh.verificarDistancia());
                
                dentro_rango=dh.verificarDistancia();
                
            }
            else
            {
                response.put(Messages.ERROR_KEY, Messages.ERROR_SISTEMA);
                return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);  
            }
            
            System.out.println("Usuario reconocido= "+usuarioreconocido);
            System.out.println("Habilitado asistencia= "+habilitado_asistencia);
            System.out.println("Dentro del rango= "+dentro_rango);
            
            System.out.println("LSTPERSON= "+lstmodelpersona.get(0).getIdpersona()!=null);
            System.out.println("LSTHORARIO= "+lstmodelhorario.get(0).getIdhorario()!=null);

            if(lstmodelpersona.get(0).getIdpersona()!=null && lstmodelhorario.get(0).getIdhorario()!=null && habilitado_asistencia && usuarioreconocido && dentro_rango)
            {
                System.out.println("Asistencia validada y guardada");
                ModelAsistencia mac=new ModelAsistencia();
                
                mac.setIdhorario(lstmodelhorario.get(0));
                mac.setIdpersona(lstmodelpersona.get(0));
                mac.setLatitud(data.getLatitud());
                mac.setLongitud(data.getLongitud());

                
                ModelAsistencia ma=sasistencia.guardar(mac);
                if(ma.getIdasistencia()>0)
                    response.put(Messages.SUCCESSFUL_KEY, Messages.DATOS_GUARDADOS); 
            }
            else
            {
                System.out.println("Asistencia NOOO validada y guardada");
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
