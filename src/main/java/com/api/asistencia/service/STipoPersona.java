/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.api.asistencia.service;

import com.api.asistencia.models.ModelTipoPersona;
import com.api.asistencia.repository.Itipopersona;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author USUARIO
 */
@Service
public class STipoPersona 
{
    @Autowired
    Itipopersona itipopersona;
    
    public ModelTipoPersona guardar(ModelTipoPersona modeltipopersona){
        return itipopersona.save(modeltipopersona);
    }
    
    public ModelTipoPersona desactivar(ModelTipoPersona modeltipopersona){
        modeltipopersona.setEstadotp(false);
        return itipopersona.save(modeltipopersona);
    }
    
    public ModelTipoPersona activar(ModelTipoPersona modeltipopersona){
        modeltipopersona.setEstadotp(true);
        return itipopersona.save(modeltipopersona);
    }
    
    public List<ModelTipoPersona> listar()
    {
        return itipopersona.findByEstadotp(true);
    }
    
    public ModelTipoPersona BuscarPorIdTipoPersona(Long idtipopersona)
    {
        return itipopersona.findByIdtipopersona(idtipopersona);
    }
    
}
