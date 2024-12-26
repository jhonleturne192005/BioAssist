/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.asistencia.service;

import com.api.asistencia.models.ModelMaterias;
import com.api.asistencia.repository.Imaterias;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author USUARIO
 */
@Service
public class SMaterias 
{
    @Autowired
    Imaterias imaterias;
    
    public ModelMaterias guardar(ModelMaterias modelmaterias)
    {
        return imaterias.save(modelmaterias);
    }
    
    public ModelMaterias actualizar(ModelMaterias modelmaterias)
    {
        return imaterias.save(modelmaterias);
    }
    
    
    public ModelMaterias desactivar(ModelMaterias modelmaterias)
    {
        modelmaterias.setEstadomateria(false);
        return imaterias.save(modelmaterias);
    }
    
    public ModelMaterias activar(ModelMaterias modelmaterias)
    {
        modelmaterias.setEstadomateria(true);
        return imaterias.save(modelmaterias);
    }
    
    public List<ModelMaterias> listar()
    {
        return imaterias.findByEstadomateria(true);
    }
    
    public List<ModelMaterias> BuscarPorIdMateria(Long idmateria)
    {
        return imaterias.findByIdmateria(idmateria);
    }
   
}
