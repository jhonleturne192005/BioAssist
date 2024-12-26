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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
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
@Table(name="materiasporpersona")
public class ModelMateriasPorPersona 
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long idmateriaporpersona;
    
    @ManyToOne
    @JoinColumn(name = "idmateria", nullable = true, unique = false)
    ModelMaterias idmateria;
    
    @ManyToOne
    @JoinColumn(name = "idpersona", nullable = true, unique = false)
    ModelPersona idpersona;

    @Column(name = "fecha_creacion", nullable = false, unique = false)
    LocalDateTime  fecha_creacion_materias_por_persona;
    
    @Column(name = "fecha_modificacion", nullable = false, unique = false)
    LocalDateTime fecha_modificacion_materias_por_persona;
    

    @PrePersist 
    public  void  onPrePersist () { 
        this.fecha_creacion_materias_por_persona=LocalDateTime.now(); 
        this.fecha_modificacion_materias_por_persona=LocalDateTime.now(); 
    } 

    @PreUpdate 
    public  void  onPreUpdate () { 
        this.fecha_modificacion_materias_por_persona=LocalDateTime.now(); 
    } 
    
    
    
}
