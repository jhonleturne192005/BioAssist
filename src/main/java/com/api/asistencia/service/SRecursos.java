/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.asistencia.service;

import com.api.asistencia.models.ModelRecursos;
import com.api.asistencia.repository.Irecursos;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author USUARIO
 */
@Service
public class SRecursos 
{
    @Autowired
    Irecursos irecursos;
    
    
    public ModelRecursos crear_actualizar(ModelRecursos mr)
    {
        return irecursos.save(mr);
    }
    
    
    public List<ModelRecursos> listar()
    {
        return irecursos.findAll();
    }

}
