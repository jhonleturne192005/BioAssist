/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.api.asistencia.repository;

import com.api.asistencia.models.ModelCurso;
import com.api.asistencia.models.ModelHorario;
import com.api.asistencia.models.ModelMaterias;
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
public interface Ihorario extends JpaRepository<ModelHorario,Serializable>
{
    @Query(value="select * from func_listarhorarioporcurso(:idcurso)",nativeQuery=true)
    public List<ModelHorario> BuscarHorariosPorIdCurso(Long idcurso);

    public List<ModelHorario> findByIdhorario(Long idhorario);   
}
