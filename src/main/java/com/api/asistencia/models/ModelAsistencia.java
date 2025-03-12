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
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
@Table(name="asistencia")
public class ModelAsistencia 
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    Long idasistencia;
    
    @ManyToOne
    @JoinColumn(name = "idpersona", nullable = true, unique = false)
    ModelPersona idpersona;
    
    @ManyToOne
    @JoinColumn(name = "idhorario", nullable = true, unique = false)
    ModelHorario idhorario;
    
    @Column(name = "latitud", nullable = true)
    String latitud;
    
    @Column(name = "longitud", nullable = true)
    String longitud;
    
    @Column(name = "fecha_creacion_asistencia", nullable = false, unique = false)
    LocalDateTime fecha_creacion_asistencia;
    
    
    @PrePersist 
    public  void  onPrePersist () { 
        this.fecha_creacion_asistencia=LocalDateTime.now(); 
    } 
    
}
