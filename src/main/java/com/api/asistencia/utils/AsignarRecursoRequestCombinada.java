/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.asistencia.utils;
import lombok.*;

/**
 *
 * @author USUARIO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AsignarRecursoRequestCombinada {
    private String base64recurso;
    private Long idpersona;
    private Long idhorario;
    private String idphone;
    private String latitud;
    private String longitud;
}
