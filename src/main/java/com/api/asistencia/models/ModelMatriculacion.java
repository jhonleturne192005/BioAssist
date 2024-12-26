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
@Table(name="matriculacion")
public class ModelMatriculacion 
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long idmatriculacion;
    
    @ManyToOne
    @JoinColumn(name = "idpersona", nullable = true, unique = false)
    ModelPersona idpersona;
    
    @ManyToOne
    @JoinColumn(name = "idmateriasporpersona", nullable = true, unique = false)
    ModelMateriasPorPersona idmateriasporpersona;
    
    @Column(name = "fecha_creacion_matricula", nullable = false, unique = false)
    LocalDateTime  fecha_creacion_matricula;
    
    @Column(name = "fecha_modificacion_matricula", nullable = false, unique = false)
    LocalDateTime fecha_modificacion_matricula;
    
    
    @PrePersist 
    public  void  onPrePersist () { 
        this.fecha_creacion_matricula=LocalDateTime.now(); 
        this.fecha_modificacion_matricula=LocalDateTime.now(); 
    } 

    @PreUpdate 
    public  void  onPreUpdate () { 
        this.fecha_modificacion_matricula=LocalDateTime.now(); 
    } 
    
}
