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
@Table(name="horario",
       uniqueConstraints=@UniqueConstraint(columnNames={"idmateriasporpersona", 
                                                                    "iddiassemana",
                                                                    "hora_inicio",
                                                                    "minuto_inicio",
                                                                    "hora_fin",
                                                                    "minuto_fin"
                                                            })
)
public class ModelHorario 
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    Long idhorario;
    
    @ManyToOne
    @JoinColumn(name = "idmateriasporpersona", nullable = true, unique = false)
    ModelMateriasPorPersona idmateriasporpersona;
    
    @ManyToOne
    @JoinColumn(name = "iddiassemana", nullable = true, unique = false)
    ModelDiasSemana iddiassemana;
     
    @Column(name = "hora_inicio", nullable = true, unique = false)
    Integer hora_inicio;
    
    @Column(name = "minuto_inicio", nullable = true, unique = false)
    Integer minuto_inicio;
    
    @Column(name = "hora_fin", nullable = true, unique = false)
    Integer hora_fin;
    
    @Column(name = "minuto_fin", nullable = true, unique = false)
    Integer minuto_fin;
    
    @Column(name = "fecha_creacion_horario", nullable = false, unique = false)
    LocalDateTime  fecha_creacion_horario;
    
    @Column(name = "fecha_modificacion_horario", nullable = false, unique = false)
    LocalDateTime fecha_modificacion_horario;
    
    
    @PrePersist 
    public  void  onPrePersist () { 
        this.fecha_creacion_horario=LocalDateTime.now(); 
        this.fecha_modificacion_horario=LocalDateTime.now(); 
    } 

    @PreUpdate 
    public  void  onPreUpdate () { 
        this.fecha_modificacion_horario=LocalDateTime.now(); 
    } 
    
}
