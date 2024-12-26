/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.asistencia.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author USUARIO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="curso")
public class ModelCurso 
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    Long idcurso;
    
    @Column(name = "curso", nullable = false, unique = false, length = 100)
    String curso;
    
    @Column(name = "fecha_creacion_curso", nullable = false, unique = false)
    LocalDateTime  fecha_creacion_curso;
    
    @Column(name = "fecha_modificacion_curso", nullable = false, unique = false)
    LocalDateTime fecha_modificacion_curso;
    
    @Column(name = "estadocurso", nullable = false)
    Boolean estadocurso=true;
    
}
