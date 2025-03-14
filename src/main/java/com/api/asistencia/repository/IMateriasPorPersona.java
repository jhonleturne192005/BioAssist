/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.api.asistencia.repository;

import com.api.asistencia.models.ModelAsistencia;
import com.api.asistencia.models.ModelCurso;
import com.api.asistencia.models.ModelMateriasPorPersona;
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
public interface IMateriasPorPersona extends JpaRepository<ModelMateriasPorPersona,Serializable>
{
    public List<ModelMateriasPorPersona> findByIdmateriaporpersona(Long idmateriaporpersona);   
    
    @Query(value="select * from func_buscarmateriasporpersona(:idpersona)",nativeQuery=true)  
    public String BuscarMateriasPorPersona(Long idpersona);       
    
    @Query(value="select * from func_listamateriamatriculadosparaasistencia(:idmateriaporpersona)",nativeQuery=true)  
    public String ListarAsistenciaMatriculados(Long idmateriaporpersona);    
    
    
    @Query(value="select * from func_listarpormateriasporpersona()",nativeQuery=true)  
    public List<ModelMateriasPorPersona> ListarPorMaterias(Long idmateria);    

}
