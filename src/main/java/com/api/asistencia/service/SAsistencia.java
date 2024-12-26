/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.asistencia.service;

import com.api.asistencia.models.ModelAsistencia;
import com.api.asistencia.models.ModelPersona;
import com.api.asistencia.repository.Iasistencia;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author USUARIO
 */
@Service
public class SAsistencia 
{
    @Autowired
    Iasistencia iasistencia;
    
    public ModelAsistencia guardar(ModelAsistencia modelasistencia)
    {
        return iasistencia.save(modelasistencia);
    }
    
    public List<ModelAsistencia> ListarAsistenciaPorPersona(Long idpersona)
    {
        return iasistencia.ListarAsistenciaPorPersona(idpersona);
    }
    
    public List<ModelAsistencia> ListarAsistenciaPorHorario(Long idhorario)
    {
        return iasistencia.ListarAsistenciaPorHorario(idhorario);
    }
    
}
