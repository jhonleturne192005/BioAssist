/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.asistencia.service;

import com.api.asistencia.models.ModelPersona;
import com.api.asistencia.repository.Ipersona;
import com.api.asistencia.utils.encryptString;
import java.util.List;
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
    
    public List<ModelPersona> listar()
    {
        return ipersona.findByEstadopersona(true);
    }
    
    public List<ModelPersona> BuscarPorIdPersona(Long idpersona)
    {
        return ipersona.findByIdpersona(idpersona);
    }
    
    
    
}
