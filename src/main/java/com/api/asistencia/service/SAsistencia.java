/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.asistencia.service;

import com.api.asistencia.models.ModelAsistencia;
import com.api.asistencia.models.ModelHorario;
import com.api.asistencia.models.ModelPersona;
import com.api.asistencia.repository.Iasistencia;
import com.api.asistencia.repository.Ihorario;
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
public class SAsistencia 
{
    @Autowired
    Iasistencia iasistencia;
    
    @Autowired
    SHorario shorario;
    
    public ModelAsistencia guardar(ModelAsistencia modelasistencia)
    {
        return iasistencia.save(modelasistencia);
    }
    
    
    public List<ModelHorario> ListarMateriaParaAsistenciaPorEstudiante(Long idpersona)
    {
        String resultjsonarray=iasistencia.ListarMateriaParaAsistencia(idpersona);
        JSONArray ja=new JSONArray(resultjsonarray);
        List<ModelHorario> lsthorario=new ArrayList<>();

        for (int i = 0; i < ja.length(); i++) 
        {
            JSONObject jsonObject = ja.getJSONObject(i);
            List<ModelHorario> mh=shorario.ListarHorario(jsonObject.getLong("idhorario"));
            lsthorario.add(mh.get(0));
        }
        return lsthorario;
    }
    
    
    
    //no se usan
    public List<ModelAsistencia> ListarAsistenciaPorPersona(Long idpersona)
    {
        return iasistencia.ListarAsistenciaPorPersona(idpersona);
    }
    
    public List<ModelAsistencia> ListarAsistenciaPorHorario(Long idhorario)
    {
        return iasistencia.ListarAsistenciaPorHorario(idhorario);
    }
    
}
