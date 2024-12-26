/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.asistencia.service;

import com.api.asistencia.models.ModelDiasSemana;
import com.api.asistencia.repository.Idiassemana;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author USUARIO
 */
@Service
public class SDiaSemana 
{
    
    @Autowired
    Idiassemana idiasemana;
    
    public List<ModelDiasSemana> listar()
    {
        return idiasemana.findAll();
    }
   
}
