/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.api.asistencia.repository;

import com.api.asistencia.models.ModelMaterias;
import com.api.asistencia.models.ModelMatriculacion;
import com.api.asistencia.models.ModelPersona;
import java.io.Serializable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author USUARIO
 */
@Repository
public interface Imatriculacion extends JpaRepository<ModelMatriculacion,Serializable>
{
    @Query(value="select * from func_listarregistros()",nativeQuery=true)
    public List<ModelMatriculacion> BuscarPorIdpersona(Long idpersona);   
}

