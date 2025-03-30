/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.asistencia.controllerReconocimiento;

import com.api.asistencia.models.ModelAsistencia;
import com.api.asistencia.models.ModelHorario;
import com.api.asistencia.models.ModelMaterias;
import com.api.asistencia.models.ModelPersona;
import com.api.asistencia.models.ModelRecursos;
import com.api.asistencia.service.SAsistencia;
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
@RequestMapping("/api/reconocimiento")
public class CReconocimiento 
{
    @Autowired 
    SAsistencia sasistencia;
    
    @Autowired
    SHorario shorario;
    
    @Autowired
    SRecursos srecursos;
    
    @Autowired 
    SPersona spersona;
    
    @PostMapping("/entrenar")
    public ResponseEntity<?> EntrenarModelo()
    {
        Map<String,Object> response=new HashMap();
        try
        {   
            String nombre_modelo=ApiReconocimiento.EntrenarModelo();
            if(nombre_modelo!=null)
            {
                List<ModelRecursos> lstmodelrecursos=srecursos.listar();
                nombre_modelo=nombre_modelo+".xml";
                ModelRecursos mr=null;
                
                if(!lstmodelrecursos.isEmpty())
                {
                    mr=lstmodelrecursos.get(0);
                    mr.setNombremodeloxml(nombre_modelo);
                }
                else
                {
                    mr=new ModelRecursos();
                    mr.setNombremodeloxml(nombre_modelo);
                }
                
                srecursos.crear_actualizar(mr);

                response.put(Messages.SUCCESSFUL_KEY, Messages.ENTRENADO_CORRECTAMENTE);
                response.put(Messages.MODELO, nombre_modelo);
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
    public ResponseEntity<?> ListarRecursos()
    {
        Map<String,Object> response=new HashMap();
        try
        {
            List<ModelRecursos> lstmodelrecursos=srecursos.listar();

            if(!lstmodelrecursos.isEmpty())
            {
                response.put(Messages.SUCCESSFUL_KEY, Messages.OPERACION_CORRECTA);
                response.put(Messages.DATA, lstmodelrecursos);
            }
            else
            {
                response.put(Messages.ERROR_KEY, Messages.NO_DATA);
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
    
    @PostMapping("/asignarrecurso")
    public ResponseEntity<?> AsignarRecurso(@RequestBody AsignarRecursoRequestCombinada data)
    {
        Map<String,Object> response=new HashMap();
        try
        {   
            System.out.println("Recurso: jejeje ");
                      //  System.out.println("Recurso: jejeje "+data.getBase64recurso());

            //System.out.println(new JSONObject( base64recurso).getString("base64recurso"));

            String ooiduser=ApiReconocimiento.AsignarRecurso( new String(data.getBase64recurso()));

            System.out.println("OOID= "+ooiduser);
                        
            if(ooiduser!=null)
            {
                List<ModelPersona> mp=spersona.BuscarPorIdPersona(data.getIdpersona());
                if(mp.size()>0)
                {
                    ModelPersona mpg=mp.get(0);
                    mpg.setEtiquetareconocer(ooiduser);
                    spersona.actualizar(mpg,false);
                }
                
                response.put(Messages.SUCCESSFUL_KEY, Messages.RECURSO_ASIGNADO_CORRECTAMENTE);
                response.put(Messages.OOID, ooiduser);
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
   
    
    @PostMapping("/reconocer")
    public ResponseEntity<?> Reconocer(@RequestParam(value="base64recurso") String base64recurso)
    {
        Map<String,Object> response=new HashMap();
        try
        {
            System.out.println("Recurso: jejeje ");
            System.out.println(base64recurso);
            JSONObject jo=ApiReconocimiento.Reconocer(base64recurso,srecursos.listar().get(0).getNombremodeloxml().trim());
            //JSONObject jo=null;

            if(jo!=null)
            {
                response.put(Messages.SUCCESSFUL_KEY, Messages.RECONOCIDO_CORRECTAMENTE);
                response.put(Messages.DATA, jo);
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
    

    @PostMapping("/crearasistencia")
    public ResponseEntity<?> CrearAsistencia(
            @RequestBody AsignarRecursoRequestCombinada data)
    {
        Map<String,Object> response=new HashMap();
        try
        {
            System.out.println("DATOS**********");
            System.out.println("id horario= "+data.getIdhorario());
            System.out.println("ID TELEFONO= "+data.getIdphone());
            System.out.println("ID PERSONA= "+data.getIdpersona());
            System.out.println("LATITUD= "+data.getLatitud());
            System.out.println("LONGITUD= "+data.getLongitud());

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
                
                System.out.println("ESTA ES LA ETIQUETA= "+etiqueta);

                //String etiqueta="a3889740-49e6-47fd-a79b-7503fc6b8f21";
                List<ModelPersona> lstmp=spersona.BuscarPorEtiquetaPersona(etiqueta);
                
                if(!lstmp.isEmpty())
                {
                    if(lstmodelpersona.get(0).getIdpersona().equals(lstmp.get(0).getIdpersona()))
                        usuarioreconocido=true; 
                }
                else{
                    System.out.println("Usuario no reconocido");
                    response.put(Messages.ERROR_KEY, "Usuario no reconocido");
                    return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST); 
                }
            }
            else{
                System.out.println("ERROR");
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
}
