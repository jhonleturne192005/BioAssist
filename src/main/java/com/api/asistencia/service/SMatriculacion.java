/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.asistencia.service;

import com.api.asistencia.models.ModelMatriculacion;
import com.api.asistencia.repository.Imatriculacion;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author USUARIO
 */
@Service
public class SMatriculacion 
{
    
    @Autowired
    Imatriculacion imatriculacion;
    
    
    public ModelMatriculacion guardar(ModelMatriculacion modelmatriculacion)
    {
        return imatriculacion.save(modelmatriculacion);
    }
    
    public List<ModelMatriculacion> listarPorEstudiante(Long idpersona)
    {
        return imatriculacion.BuscarPorIdpersona(idpersona);
    }
    
    public List<ModelMatriculacion> listar()
    {
        return imatriculacion.findAll();
    }
      
}
