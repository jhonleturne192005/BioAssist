/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.asistencia.repository;

import com.api.asistencia.models.ModelDiasSemana;
import com.api.asistencia.models.ModelGenero;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author USUARIO
 */
@Repository
public interface Igenero extends JpaRepository<ModelGenero,Serializable> {
}
