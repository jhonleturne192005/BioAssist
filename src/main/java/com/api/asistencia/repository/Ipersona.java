/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.api.asistencia.repository;

import com.api.asistencia.models.ModelPersona;
import com.api.asistencia.models.ModelTipoPersona;
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
public interface Ipersona extends JpaRepository<ModelPersona,Serializable>
{  
    public List<ModelPersona> findByEstadopersona(Boolean estado);   
    public List<ModelPersona> findByIdpersona(Long idpersona);   
    public List<ModelPersona> findByCorreo(String correo);   
    
    @Query(value="select * from func_listarpersonaportipo(:idtipopersona)",nativeQuery=true)  
    public String findPorTipoPersona(Integer idtipopersona);   
}
