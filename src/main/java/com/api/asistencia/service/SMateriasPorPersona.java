/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.asistencia.service;

import com.api.asistencia.models.ModelMateriasPorPersona;
import com.api.asistencia.repository.IMateriasPorPersona;
import com.api.asistencia.repository.Idiassemana;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author USUARIO
 */
@Service
public class SMateriasPorPersona 
{
    @Autowired
    IMateriasPorPersona imateriaporpersona;
    
    public ModelMateriasPorPersona guardar(ModelMateriasPorPersona modelmateriaporpersona)
    {
        return imateriaporpersona.save(modelmateriaporpersona);
    }
    
    public ModelMateriasPorPersona Actualizar(ModelMateriasPorPersona modelmateriaporpersona)
    {
        return imateriaporpersona.save(modelmateriaporpersona);
    }
    
    public List<ModelMateriasPorPersona> listar()
    {
        return imateriaporpersona.findAll();
    }
    
    public List<ModelMateriasPorPersona> listarPorMateria(Long idmateria)
    {
        return imateriaporpersona.BuscarMaterias(idmateria);
    }   
        
    public List<ModelMateriasPorPersona> listarPorPersona(Long idpersona)
    {
        return imateriaporpersona.BuscarMateriasPorPersona(idpersona);
    }
    
    public List<ModelMateriasPorPersona> BuscarPorIdMateriasPorPersona(Long idmateriasporpersona)
    {
        return imateriaporpersona.findByIdmateriaporpersona(idmateriasporpersona);
    }
    
    
}
