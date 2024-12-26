/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.api.asistencia.repository;

import com.api.asistencia.models.ModelCurso;
import com.api.asistencia.models.ModelMaterias;
import java.io.Serializable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author USUARIO
 */
@Repository
public interface Icurso extends JpaRepository<ModelCurso,Serializable>
{
    public List<ModelCurso> findByEstadocurso(Boolean estado_curso);   
    public List<ModelCurso> findByIdcurso(Long idcurso);   
}
