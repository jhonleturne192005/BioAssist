/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.asistencia.service;

import com.api.asistencia.models.ModelMateriasPorPersona;
import com.api.asistencia.models.ModelPersona;
import com.api.asistencia.repository.IMateriasPorPersona;
import com.api.asistencia.repository.Idiassemana;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
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
        return imateriaporpersona.ListarPorMaterias(idmateria);
    }   
        
    public List<ModelMateriasPorPersona> listarPorPersona(Long idpersona)
    {
        String resultjsonarray=imateriaporpersona.BuscarMateriasPorPersona(idpersona);
        JSONArray ja=new JSONArray(resultjsonarray);
        List<ModelMateriasPorPersona> lstmateriasporpersona=new ArrayList<>();
        for (int i = 0; i < ja.length(); i++) {
            
            JSONObject jsonObject = ja.getJSONObject(i);
            Long idpersonajo=jsonObject.getLong("idmateriaporpersona");
            lstmateriasporpersona.add(this.BuscarPorIdMateriasPorPersona(idpersonajo).get(0));
        }
    
        return lstmateriasporpersona;
    }
    
    public List<ModelMateriasPorPersona> BuscarPorIdMateriasPorPersona(Long idmateriasporpersona)
    {
        return imateriaporpersona.findByIdmateriaporpersona(idmateriasporpersona);
    }
    
    public String ListarAsistenciaMatriculados(Long idmateriasporpersona)
    {
        return imateriaporpersona.ListarAsistenciaMatriculados(idmateriasporpersona);
    }
    
    
    
}
