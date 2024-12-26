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
@Table(name="tipo_persona")
public class ModelTipoPersona 
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long idtipopersona;
    
    @Column(name = "tipo", nullable = false, unique = true, length = 50)
    String tipo;
    
    @Column(name = "estadotp", nullable = false, unique = false)
    Boolean estadotp=true;
}
