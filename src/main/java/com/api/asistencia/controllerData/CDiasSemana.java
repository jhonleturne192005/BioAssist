/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.asistencia.controllerData;

import com.api.asistencia.models.ModelCurso;
import com.api.asistencia.models.ModelDiasSemana;
import com.api.asistencia.service.SDiaSemana;
import com.api.asistencia.service.SPersona;
import com.api.asistencia.utils.Messages;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author USUARIO
 */
@RestController
@RequestMapping("/api/diassemana")
public class CDiasSemana 
{
    @Autowired 
    SDiaSemana sdiasemana;
    
    
    @PostMapping("/listar")
    public ResponseEntity<?> ListarDiasSemana()
    {
        Map<String,Object> response=new HashMap();
        try
        {
            List<ModelDiasSemana> lstdiassemana=sdiasemana.listar();

            if(!lstdiassemana.isEmpty())
            {
                response.put(Messages.SUCCESSFUL_KEY, Messages.OPERACION_CORRECTA);
                response.put(Messages.DATA, lstdiassemana);
            }
            else
            {
                response.put(Messages.ERROR_KEY, Messages.ERROR_SISTEMA);
                return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);  
            }
        }
        catch(Exception ex)
        {
            String error=ex.getMessage();
            System.out.println(error);
            response.put(Messages.ERROR_KEY, Messages.ERROR_SISTEMA);
        }
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK); 
    }
    
    
}
