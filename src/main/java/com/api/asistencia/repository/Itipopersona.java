/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.asistencia.repository;

import com.api.asistencia.models.ModelTipoPersona;
import java.io.Serializable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author USUARIO
 */
@Repository
public interface Itipopersona extends JpaRepository<ModelTipoPersona,Serializable>
{
    public List<ModelTipoPersona> findByEstadotp(Boolean estado);
    public ModelTipoPersona findByIdtipopersona(Long idtipopersona);   
}
