/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.asistencia.service;

import com.api.asistencia.models.ModelPersona;
import com.api.asistencia.repository.Ipersona;
import com.api.asistencia.repository.Itipopersona;
import com.api.asistencia.utils.ManagedJson;
import com.api.asistencia.utils.encryptString;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author USUARIO
 */
@Service
public class SPersona 
{
    @Autowired
    Ipersona ipersona;
    
    @Autowired
    Itipopersona itipopersona;
    
    
    public ModelPersona guardar(ModelPersona modelpersona)
    {
        modelpersona.setContrasenia(encryptString.encriptPassword(modelpersona.getContrasenia()));
        return ipersona.save(modelpersona);
    }
    
    public ModelPersona actualizar(ModelPersona modelpersona,Boolean actualizar_contrasenia)
    {
        if(actualizar_contrasenia)
            modelpersona.setContrasenia(encryptString.encriptPassword(modelpersona.getContrasenia()));
        
        return ipersona.save(modelpersona);
    }
   
    
    public ModelPersona desactivar(ModelPersona modelpersona)
    {
        modelpersona.setEstadopersona(false);
        return ipersona.save(modelpersona);
    }
    
    public ModelPersona activar(ModelPersona modelpersona)
    {
        modelpersona.setEstadopersona(true);
        return ipersona.save(modelpersona);
    }
    
    public ModelPersona activarEdicion(ModelPersona modelpersona)
    {
        modelpersona.setEstadopersonaedicion(true);
        return ipersona.save(modelpersona);
    }
    
    public ModelPersona desactivarEdicion(ModelPersona modelpersona)
    {
        modelpersona.setEstadopersonaedicion(false);
        return ipersona.save(modelpersona);
    }
    
    
    public List<ModelPersona> listar()
    {
        return ipersona.findByEstadopersona(true);
    }
    
    
    public List<ModelPersona> listarPersonaProfesores()
    {
        
        String resultjsonarray=ipersona.findPorTipoPersona(2);
        JSONArray ja=new JSONArray(resultjsonarray);
        List<ModelPersona> lstpersonas=new ArrayList<>();
        for (int i = 0; i < ja.length(); i++) {
            JSONObject jsonObject = ja.getJSONObject(i);

            ModelPersona persona = new ModelPersona();
            persona.setIdpersona(jsonObject.getLong("idpersona"));
            persona.setNombres(jsonObject.getString("nombres"));
            persona.setApellidos(jsonObject.getString("apellidos"));
            persona.setCorreo(jsonObject.getString("correo"));
            persona.setNumero_telefono(jsonObject.getString("numero_telefono"));
            persona.setContrasenia(jsonObject.getString("contrasenia"));
            persona.setAdministrador(jsonObject.getBoolean("administrador"));
            persona.setEstadopersona(jsonObject.getBoolean("estadopersona"));
            persona.setFecha_creacion_persona(LocalDateTime.parse(jsonObject.getString("fecha_creacion_persona")));
            persona.setFecha_modificacion_persona(LocalDateTime.parse(jsonObject.getString("fecha_modificacion_persona")));
            persona.setIdtipopersona(itipopersona.findByIdtipopersona(jsonObject.getLong("idtipopersona")));
            
            lstpersonas.add(persona);
        }
            
        
        return lstpersonas;
    }
    
    public List<ModelPersona> BuscarPorIdPersona(Long idpersona)
    {
        return ipersona.findByIdpersona(idpersona);
    }
    
    public List<ModelPersona> BuscarPersonaPorCorreo(String correo)
    {
        return ipersona.findByCorreo(correo);
    }
    
    public List<ModelPersona> BuscarPorEtiquetaPersona(String etiqueta)
    {
        return ipersona.findByEtiquetareconocer(etiqueta);
    }
    
    
}
