/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.asistencia.service;

import com.api.asistencia.models.ModelHorario;
import com.api.asistencia.repository.Ihorario;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author USUARIO
 */
@Service
public class SHorario 
{
    @Autowired
    Ihorario ihorario;
    
    
    public ModelHorario guardar(ModelHorario modelhorario)
    {
        return ihorario.save(modelhorario);
    }
    
    public ModelHorario actualizar(ModelHorario modelhorario)
    {
        return ihorario.save(modelhorario);
    }
    
    
    public List<ModelHorario> ListarHorarioPorCurso(Long idcurso)
    {
        return ihorario.BuscarPorIdCurso(idcurso);
    }
    
    public List<ModelHorario> ListarHorario(Long idhorario)
    {
        return ihorario.findByIdhorario(idhorario);
    }
    
    
}
