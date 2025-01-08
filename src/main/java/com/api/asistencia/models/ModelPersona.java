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
@Table(name="persona")
public class ModelPersona 
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long idpersona;
    
    @ManyToOne
    @JoinColumn(name = "idtipopersona", nullable = true, unique = false)
    ModelTipoPersona idtipopersona;
    
    @Column(name = "nombres", nullable = false, unique = false, length = 100)
    String nombres;
    
    @Column(name = "apellidos", nullable = false, unique = false, length = 100)
    String apellidos;
    
    @Column(name = "correo", nullable = false, unique = true, length = 100) //unique
    String correo;
    
    @Column(name = "contrasenia", nullable = false)
    String contrasenia;

    @Column(name = "numero_telefono", nullable = true, unique = true, length = 10) //unique //nullable
    String numero_telefono;
    
    @Column(name = "credenciales_telefono", nullable = true, unique = true) //unique //nullable
    String credenciales_telefono;
    
    @Column(name = "clave_generada_telefono", nullable = true, unique = true) //unique //nullable
    String clave_generada_telefono;
    
    @Column(name = "hash_generado", nullable = true, unique = true) //unique //nullable
    String hash_generado;
    
    @Column(name = "etiqueta_reconocer", nullable = true, unique = true) //unique //nullable
    String etiqueta_reconocer;
    
    @Column(name = "fecha_creacion_persona", nullable = false, unique = false)
    LocalDateTime  fecha_creacion_persona;
    
    @Column(name = "fecha_modificacion_persona", nullable = false, unique = false)
    LocalDateTime fecha_modificacion_persona;
    
    @Column(name = "estadopersona", nullable = false)
    Boolean estadopersona=true;
    
    @Column(name = "estadopersonaedicion", nullable = false)
    Boolean estadopersonaedicion=true;
    
    @Column(name = "administrador", nullable = false)
    Boolean administrador=false;
    
    
    @PrePersist 
    public  void  onPrePersist () { 
        this.fecha_creacion_persona=LocalDateTime.now(); 
        this.fecha_modificacion_persona=LocalDateTime.now(); 
    } 

    @PreUpdate 
    public  void  onPreUpdate () { 
        this.fecha_modificacion_persona=LocalDateTime.now(); 
    } 
    
}
