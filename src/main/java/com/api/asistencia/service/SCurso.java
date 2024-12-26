/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.asistencia.service;

import com.api.asistencia.models.ModelCurso;
import com.api.asistencia.models.ModelMaterias;
import com.api.asistencia.repository.Icurso;
import com.api.asistencia.repository.Imaterias;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author USUARIO
 */
@Service
public class SCurso 
{
    @Autowired
    Icurso icurso;
    
    public ModelCurso guardar(ModelCurso modelcurso)
    {
        return icurso.save(modelcurso);
    }
    
    public ModelCurso actualizar(ModelCurso modelcurso)
    {
        return icurso.save(modelcurso);
    }
    
    public ModelCurso desactivar(ModelCurso modelcurso)
    {
        modelcurso.setEstadocurso(false);
        return icurso.save(modelcurso);
    }
    
    public ModelCurso activar(ModelCurso modelcurso)
    {
        modelcurso.setEstadocurso(true);
        return icurso.save(modelcurso);
    }
    
    
    public List<ModelCurso> listar()
    {
        return icurso.findByEstadocurso(true);
    }
    
    public List<ModelCurso> BuscarPorIdCurso(Long idcurso)
    {
        return icurso.findByIdcurso(idcurso);
    }
    
}
