/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.asistencia.controllerReconocimiento;

import com.api.asistencia.models.ModelCurso;
import com.api.asistencia.models.ModelDiasSemana;
import com.api.asistencia.models.ModelGenero;
import com.api.asistencia.models.ModelHorario;
import com.api.asistencia.models.ModelMaterias;
import com.api.asistencia.models.ModelMateriasPorPersona;
import com.api.asistencia.models.ModelMatriculacion;
import com.api.asistencia.models.ModelPersona;
import com.api.asistencia.models.ModelTipoPersona;
import com.api.asistencia.service.SCurso;
import com.api.asistencia.service.SDiaSemana;
import com.api.asistencia.service.SGenero;
import com.api.asistencia.service.SHorario;
import com.api.asistencia.service.SMaterias;
import com.api.asistencia.service.SMateriasPorPersona;
import com.api.asistencia.service.SMatriculacion;
import com.api.asistencia.service.SPersona;
import com.api.asistencia.service.STipoPersona;
import com.api.asistencia.utils.Messages;
import java.util.HashMap;
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
@RequestMapping("/api/cargardatos")
public class CCargarDatos {
    
    @Autowired
    private SCurso sCurso;

    @Autowired
    private SDiaSemana sDiaSemana;

    @Autowired
    private SGenero sGenero;

    @Autowired
    private SHorario sHorario;

    @Autowired
    private SMaterias sMaterias;

    @Autowired
    private SMateriasPorPersona sMateriasPorPersona;

    @Autowired
    private SMatriculacion sMatriculacion;

    @Autowired
    private SPersona sPersona;

    @Autowired
    private STipoPersona sTipoPersona;
    
   
    @PostMapping("/crear")
    public ResponseEntity<?> initData()
    {
        Map<String,Object> response=new HashMap();

        try{
            if(sTipoPersona.listar().isEmpty())
            {
                /*dias semana*/

                ModelDiasSemana mds1 = new ModelDiasSemana();
                mds1.setIddia(1L);
                mds1.setDia("Lunes");

                ModelDiasSemana mds2 = new ModelDiasSemana();
                mds2.setIddia(2L);
                mds2.setDia("Martes");

                ModelDiasSemana mds3 = new ModelDiasSemana();
                mds3.setIddia(3L);
                mds3.setDia("Miércoles");

                ModelDiasSemana mds4 = new ModelDiasSemana();
                mds4.setIddia(4L);
                mds4.setDia("Jueves");

                ModelDiasSemana mds5 = new ModelDiasSemana();
                mds5.setIddia(5L);
                mds5.setDia("Viernes");

                ModelDiasSemana mds6 = new ModelDiasSemana();
                mds6.setIddia(6L);
                mds6.setDia("Sábado");

                ModelDiasSemana mds7 = new ModelDiasSemana();
                mds7.setIddia(0L);
                mds7.setDia("Domingo");

                mds1 = sDiaSemana.guardar(mds1);
                mds2 = sDiaSemana.guardar(mds2);
                mds3 = sDiaSemana.guardar(mds3);
                mds4 = sDiaSemana.guardar(mds4);
                mds5 = sDiaSemana.guardar(mds5);
                mds6 = sDiaSemana.guardar(mds6);
                mds7 = sDiaSemana.guardar(mds7);

                /*TIPO PERSONA*/

                ModelTipoPersona modeltipopersona1=new ModelTipoPersona();
                modeltipopersona1.setTipo("Administrador");

                ModelTipoPersona modeltipopersona2=new ModelTipoPersona();
                modeltipopersona2.setTipo("Profesor");       

                ModelTipoPersona modeltipopersona3=new ModelTipoPersona();
                modeltipopersona3.setTipo("Estudiante");  

                ModelTipoPersona mtp1=sTipoPersona.guardar(modeltipopersona1);
                ModelTipoPersona mtp2=sTipoPersona.guardar(modeltipopersona2);
                ModelTipoPersona mtp3=sTipoPersona.guardar(modeltipopersona3);


                /*GENERO*/

                ModelGenero mg1 =new ModelGenero();
                mg1.setGenero("Masculino");

                ModelGenero mg2 =new ModelGenero();
                mg2.setGenero("Femenino");

                mg1 =sGenero.guardar(mg1);
                mg2 =sGenero.guardar(mg2);

                /*PERSONA*/

                ModelPersona mpAdmin = new ModelPersona();
                mpAdmin.setIdgenero(mg1);
                mpAdmin.setIdtipopersona(mtp1);
                mpAdmin.setNombres("admin");
                mpAdmin.setApellidos("admin");
                mpAdmin.setCorreo("admin@uteq.edu.ec");
                mpAdmin.setContrasenia("admin");
                mpAdmin.setNumero_telefono("0998765432");
                mpAdmin.setAdministrador(true);

                /*profesores*/    
                ModelPersona mpProfesor1=new ModelPersona();
                mpProfesor1.setIdgenero(mg1);
                mpProfesor1.setIdtipopersona(mtp2);
                mpProfesor1.setNombres("profesor1");
                mpProfesor1.setApellidos("profesor1");
                mpProfesor1.setCorreo("profesor1@uteq.edu.ec");
                mpProfesor1.setContrasenia("profesor1");
                mpProfesor1.setNumero_telefono("0987456385");

                ModelPersona mpProfesor2 = new ModelPersona();
                mpProfesor2.setIdgenero(mg1);
                mpProfesor2.setIdtipopersona(mtp2);
                mpProfesor2.setNombres("profesor2");
                mpProfesor2.setApellidos("profesor2");
                mpProfesor2.setCorreo("profesor2@uteq.edu.ec");
                mpProfesor2.setContrasenia("profesor2");
                mpProfesor2.setNumero_telefono("0991234567");

                ModelPersona mpProfesor3 = new ModelPersona();
                mpProfesor3.setIdgenero(mg1);
                mpProfesor3.setIdtipopersona(mtp2);
                mpProfesor3.setNombres("profesor3");
                mpProfesor3.setApellidos("profesor3");
                mpProfesor3.setCorreo("profesor3@uteq.edu.ec");
                mpProfesor3.setContrasenia("profesor3");
                mpProfesor3.setNumero_telefono("0986543210");

                ModelPersona mpProfesor4 = new ModelPersona();
                mpProfesor4.setIdgenero(mg1);
                mpProfesor4.setIdtipopersona(mtp2);
                mpProfesor4.setNombres("profesor4");
                mpProfesor4.setApellidos("profesor4");
                mpProfesor4.setCorreo("profesor4@uteq.edu.ec");
                mpProfesor4.setContrasenia("profesor4");
                mpProfesor4.setNumero_telefono("0978456123");

                ModelPersona mpProfesor5 = new ModelPersona();
                mpProfesor5.setIdgenero(mg1);
                mpProfesor5.setIdtipopersona(mtp2);
                mpProfesor5.setNombres("profesor5");
                mpProfesor5.setApellidos("profesor5");
                mpProfesor5.setCorreo("profesor5@uteq.edu.ec");
                mpProfesor5.setContrasenia("profesor5");
                mpProfesor5.setNumero_telefono("0969874321");

                /*estudiante*/
                ModelPersona mpJhon=new ModelPersona();
                mpJhon.setIdgenero(mg1);
                mpJhon.setIdtipopersona(mtp3);
                mpJhon.setNombres("Jhon Byron");
                mpJhon.setApellidos("Leturne Pluas");
                mpJhon.setCorreo("jleturnep@uteq.edu.ec");
                mpJhon.setContrasenia("jhon");
                mpJhon.setNumero_telefono("0983704110");

                ModelPersona mpOrlando = new ModelPersona();
                mpOrlando.setIdgenero(mg1);
                mpOrlando.setIdtipopersona(mtp3);
                mpOrlando.setNombres("Orlando Jesus");
                mpOrlando.setApellidos("Cedeño Salvatierra");
                mpOrlando.setCorreo("ocedenos@uteq.edu.ec");
                mpOrlando.setContrasenia("orlando");
                mpOrlando.setNumero_telefono("0987654321"); 

                ModelPersona mpOchoa = new ModelPersona();
                mpOchoa.setIdgenero(mg1);
                mpOchoa.setIdtipopersona(mtp3);
                mpOchoa.setNombres("Geovanny Alexander");
                mpOchoa.setApellidos("Ochoa Gilces");
                mpOchoa.setCorreo("gochoag@uteq.edu.ec");
                mpOchoa.setContrasenia("ochoa");
                mpOchoa.setNumero_telefono("0991122334"); 

                mpAdmin = sPersona.guardar(mpAdmin);
                mpProfesor1 = sPersona.guardar(mpProfesor1);
                mpProfesor2 = sPersona.guardar(mpProfesor2);
                mpProfesor3 = sPersona.guardar(mpProfesor3);
                mpProfesor4 = sPersona.guardar(mpProfesor4);
                mpProfesor5 = sPersona.guardar(mpProfesor5);
                mpJhon = sPersona.guardar(mpJhon);
                mpOrlando = sPersona.guardar(mpOrlando);
                mpOchoa = sPersona.guardar(mpOchoa);


                /*CURSO*/    

                ModelCurso mc1 =new ModelCurso();
                mc1.setCurso("Curso1");

                ModelCurso mc2 =new ModelCurso();
                mc2.setCurso("Curso2");

                mc1=sCurso.guardar(mc1);

                /*MATERIA*/

                ModelMaterias mms1 = new ModelMaterias();
                mms1.setMateria("Lenguaje");
                mms1.setIdcurso(mc1);

                ModelMaterias mms2 = new ModelMaterias();
                mms2.setMateria("Matemáticas");
                mms2.setIdcurso(mc1);

                ModelMaterias mms3 = new ModelMaterias();
                mms3.setMateria("Ciencias Naturales");
                mms3.setIdcurso(mc1);

                ModelMaterias mms4 = new ModelMaterias();
                mms4.setMateria("Estudios Sociales");
                mms4.setIdcurso(mc1);

                ModelMaterias mms5 = new ModelMaterias();
                mms5.setMateria("Física");
                mms5.setIdcurso(mc1);

                ModelMaterias mms6 = new ModelMaterias();
                mms6.setMateria("Química");
                mms6.setIdcurso(mc1);

                ModelMaterias mms7 = new ModelMaterias();
                mms7.setMateria("Inglés");
                mms7.setIdcurso(mc1);

                ModelMaterias mms8 = new ModelMaterias();
                mms8.setMateria("Educación Física");
                mms8.setIdcurso(mc1);

                ModelMaterias mms9 = new ModelMaterias();
                mms9.setMateria("Computación");
                mms9.setIdcurso(mc1);

                ModelMaterias mms10 = new ModelMaterias();
                mms10.setMateria("Emprendimiento");
                mms10.setIdcurso(mc1);


                mms1 = sMaterias.guardar(mms1);
                mms2 = sMaterias.guardar(mms2);
                mms3 = sMaterias.guardar(mms3);
                mms4 = sMaterias.guardar(mms4);
                mms5 = sMaterias.guardar(mms5);
                mms6 = sMaterias.guardar(mms6);
                mms7 = sMaterias.guardar(mms7);
                mms8 = sMaterias.guardar(mms8);
                mms9 = sMaterias.guardar(mms9);
                mms10 = sMaterias.guardar(mms10);

                /*MATERIAS POR PERSONA*/

                ModelMateriasPorPersona mmpp1 = new ModelMateriasPorPersona();
                mmpp1.setIdpersona(mpProfesor1);
                mmpp1.setIdmateria(mms1); // Lenguaje

                ModelMateriasPorPersona mmpp2 = new ModelMateriasPorPersona();
                mmpp2.setIdpersona(mpProfesor1);
                mmpp2.setIdmateria(mms2); // Matemáticas

                ModelMateriasPorPersona mmpp3 = new ModelMateriasPorPersona();
                mmpp3.setIdpersona(mpProfesor2);
                mmpp3.setIdmateria(mms3); // Ciencias Naturales

                ModelMateriasPorPersona mmpp4 = new ModelMateriasPorPersona();
                mmpp4.setIdpersona(mpProfesor2);
                mmpp4.setIdmateria(mms4); // Estudios Sociales

                ModelMateriasPorPersona mmpp5 = new ModelMateriasPorPersona();
                mmpp5.setIdpersona(mpProfesor3);
                mmpp5.setIdmateria(mms5); // Física

                ModelMateriasPorPersona mmpp6 = new ModelMateriasPorPersona();
                mmpp6.setIdpersona(mpProfesor3);
                mmpp6.setIdmateria(mms6); // Química

                ModelMateriasPorPersona mmpp7 = new ModelMateriasPorPersona();
                mmpp7.setIdpersona(mpProfesor4);
                mmpp7.setIdmateria(mms7); // Inglés

                ModelMateriasPorPersona mmpp8 = new ModelMateriasPorPersona();
                mmpp8.setIdpersona(mpProfesor4);
                mmpp8.setIdmateria(mms8); // Educación Física

                ModelMateriasPorPersona mmpp9 = new ModelMateriasPorPersona();
                mmpp9.setIdpersona(mpProfesor5);
                mmpp9.setIdmateria(mms9); // Computación

                ModelMateriasPorPersona mmpp10 = new ModelMateriasPorPersona();
                mmpp10.setIdpersona(mpProfesor5);
                mmpp10.setIdmateria(mms10); // Emprendimiento

                sMateriasPorPersona.guardar(mmpp1);
                sMateriasPorPersona.guardar(mmpp2);
                sMateriasPorPersona.guardar(mmpp3);
                sMateriasPorPersona.guardar(mmpp4);
                sMateriasPorPersona.guardar(mmpp5);
                sMateriasPorPersona.guardar(mmpp6);
                sMateriasPorPersona.guardar(mmpp7);
                sMateriasPorPersona.guardar(mmpp8);
                sMateriasPorPersona.guardar(mmpp9);
                sMateriasPorPersona.guardar(mmpp10);                

                /*Horarios*/

                ModelHorario mh1 = new ModelHorario();
                mh1.setIdmateriasporpersona(mmpp1); // Lenguaje - Lunes
                mh1.setIddiassemana(mds1);
                mh1.setHora_inicio(7);
                mh1.setMinuto_inicio(30);
                mh1.setHora_fin(9);
                mh1.setMinuto_fin(30);

                ModelHorario mh2 = new ModelHorario();
                mh2.setIdmateriasporpersona(mmpp2); // Matemáticas - Lunes
                mh2.setIddiassemana(mds1);
                mh2.setHora_inicio(9);
                mh2.setMinuto_inicio(30);
                mh2.setHora_fin(11);
                mh2.setMinuto_fin(30);

                ModelHorario mh3 = new ModelHorario();
                mh3.setIdmateriasporpersona(mmpp3); // Ciencias Naturales - Martes
                mh3.setIddiassemana(mds2);
                mh3.setHora_inicio(7);
                mh3.setMinuto_inicio(30);
                mh3.setHora_fin(9);
                mh3.setMinuto_fin(30);

                ModelHorario mh4 = new ModelHorario();
                mh4.setIdmateriasporpersona(mmpp4); // Estudios Sociales - Martes
                mh4.setIddiassemana(mds2);
                mh4.setHora_inicio(9);
                mh4.setMinuto_inicio(30);
                mh4.setHora_fin(11);
                mh4.setMinuto_fin(30);

                ModelHorario mh5 = new ModelHorario();
                mh5.setIdmateriasporpersona(mmpp5); // Física - Miércoles
                mh5.setIddiassemana(mds3);
                mh5.setHora_inicio(7);
                mh5.setMinuto_inicio(30);
                mh5.setHora_fin(9);
                mh5.setMinuto_fin(30);

                ModelHorario mh6 = new ModelHorario();
                mh6.setIdmateriasporpersona(mmpp6); // Química - Miércoles
                mh6.setIddiassemana(mds3);
                mh6.setHora_inicio(9);
                mh6.setMinuto_inicio(30);
                mh6.setHora_fin(11);
                mh6.setMinuto_fin(30);

                ModelHorario mh7 = new ModelHorario();
                mh7.setIdmateriasporpersona(mmpp7); // Inglés - Jueves
                mh7.setIddiassemana(mds4);
                mh7.setHora_inicio(7);
                mh7.setMinuto_inicio(30);
                mh7.setHora_fin(9);
                mh7.setMinuto_fin(30);

                ModelHorario mh8 = new ModelHorario();
                mh8.setIdmateriasporpersona(mmpp8); // Educación Física - Jueves
                mh8.setIddiassemana(mds4);
                mh8.setHora_inicio(9);
                mh8.setMinuto_inicio(30);
                mh8.setHora_fin(11);
                mh8.setMinuto_fin(30);

                ModelHorario mh9 = new ModelHorario();
                mh9.setIdmateriasporpersona(mmpp9); // Computación - Viernes
                mh9.setIddiassemana(mds5);
                mh9.setHora_inicio(7);
                mh9.setMinuto_inicio(30);
                mh9.setHora_fin(9);
                mh9.setMinuto_fin(30);

                ModelHorario mh10 = new ModelHorario();
                mh10.setIdmateriasporpersona(mmpp10); // Emprendimiento - Viernes
                mh10.setIddiassemana(mds5);
                mh10.setHora_inicio(9);
                mh10.setMinuto_inicio(30);
                mh10.setHora_fin(11);
                mh10.setMinuto_fin(30);


                sHorario.guardar(mh1);
                sHorario.guardar(mh2);
                sHorario.guardar(mh3);
                sHorario.guardar(mh4);
                sHorario.guardar(mh5);
                sHorario.guardar(mh6);
                sHorario.guardar(mh7);
                sHorario.guardar(mh8);
                sHorario.guardar(mh9);
                sHorario.guardar(mh10);                


                /*matriculacion*/

                ModelMatriculacion mm1 = new ModelMatriculacion();
                mm1.setIdpersona(mpJhon);
                mm1.setIdmateriasporpersona(mmpp1);

                ModelMatriculacion mm2 = new ModelMatriculacion();
                mm2.setIdpersona(mpJhon);
                mm2.setIdmateriasporpersona(mmpp2);

                ModelMatriculacion mm3 = new ModelMatriculacion();
                mm3.setIdpersona(mpJhon);
                mm3.setIdmateriasporpersona(mmpp3);

                ModelMatriculacion mm4 = new ModelMatriculacion();
                mm4.setIdpersona(mpJhon);
                mm4.setIdmateriasporpersona(mmpp4);

                ModelMatriculacion mm5 = new ModelMatriculacion();
                mm5.setIdpersona(mpJhon);
                mm5.setIdmateriasporpersona(mmpp5);

                ModelMatriculacion mm6 = new ModelMatriculacion();
                mm6.setIdpersona(mpJhon);
                mm6.setIdmateriasporpersona(mmpp6);

                ModelMatriculacion mm7 = new ModelMatriculacion();
                mm7.setIdpersona(mpJhon);
                mm7.setIdmateriasporpersona(mmpp7);

                ModelMatriculacion mm8 = new ModelMatriculacion();
                mm8.setIdpersona(mpJhon);
                mm8.setIdmateriasporpersona(mmpp8);

                ModelMatriculacion mm9 = new ModelMatriculacion();
                mm9.setIdpersona(mpJhon);
                mm9.setIdmateriasporpersona(mmpp9);

                ModelMatriculacion mm10 = new ModelMatriculacion();
                mm10.setIdpersona(mpJhon);
                mm10.setIdmateriasporpersona(mmpp10);

                // Matriculación de Orlando
                ModelMatriculacion mm11 = new ModelMatriculacion();
                mm11.setIdpersona(mpOrlando);
                mm11.setIdmateriasporpersona(mmpp1);

                ModelMatriculacion mm12 = new ModelMatriculacion();
                mm12.setIdpersona(mpOrlando);
                mm12.setIdmateriasporpersona(mmpp2);

                ModelMatriculacion mm13 = new ModelMatriculacion();
                mm13.setIdpersona(mpOrlando);
                mm13.setIdmateriasporpersona(mmpp3);

                ModelMatriculacion mm14 = new ModelMatriculacion();
                mm14.setIdpersona(mpOrlando);
                mm14.setIdmateriasporpersona(mmpp4);

                ModelMatriculacion mm15 = new ModelMatriculacion();
                mm15.setIdpersona(mpOrlando);
                mm15.setIdmateriasporpersona(mmpp5);

                ModelMatriculacion mm16 = new ModelMatriculacion();
                mm16.setIdpersona(mpOrlando);
                mm16.setIdmateriasporpersona(mmpp6);

                ModelMatriculacion mm17 = new ModelMatriculacion();
                mm17.setIdpersona(mpOrlando);
                mm17.setIdmateriasporpersona(mmpp7);

                ModelMatriculacion mm18 = new ModelMatriculacion();
                mm18.setIdpersona(mpOrlando);
                mm18.setIdmateriasporpersona(mmpp8);

                ModelMatriculacion mm19 = new ModelMatriculacion();
                mm19.setIdpersona(mpOrlando);
                mm19.setIdmateriasporpersona(mmpp9);

                ModelMatriculacion mm20 = new ModelMatriculacion();
                mm20.setIdpersona(mpOrlando);
                mm20.setIdmateriasporpersona(mmpp10);

                // Matriculación de Ochoa
                ModelMatriculacion mm21 = new ModelMatriculacion();
                mm21.setIdpersona(mpOchoa);
                mm21.setIdmateriasporpersona(mmpp1);

                ModelMatriculacion mm22 = new ModelMatriculacion();
                mm22.setIdpersona(mpOchoa);
                mm22.setIdmateriasporpersona(mmpp2);

                ModelMatriculacion mm23 = new ModelMatriculacion();
                mm23.setIdpersona(mpOchoa);
                mm23.setIdmateriasporpersona(mmpp3);

                ModelMatriculacion mm24 = new ModelMatriculacion();
                mm24.setIdpersona(mpOchoa);
                mm24.setIdmateriasporpersona(mmpp4);

                ModelMatriculacion mm25 = new ModelMatriculacion();
                mm25.setIdpersona(mpOchoa);
                mm25.setIdmateriasporpersona(mmpp5);

                ModelMatriculacion mm26 = new ModelMatriculacion();
                mm26.setIdpersona(mpOchoa);
                mm26.setIdmateriasporpersona(mmpp6);

                ModelMatriculacion mm27 = new ModelMatriculacion();
                mm27.setIdpersona(mpOchoa);
                mm27.setIdmateriasporpersona(mmpp7);

                ModelMatriculacion mm28 = new ModelMatriculacion();
                mm28.setIdpersona(mpOchoa);
                mm28.setIdmateriasporpersona(mmpp8);

                ModelMatriculacion mm29 = new ModelMatriculacion();
                mm29.setIdpersona(mpOchoa);
                mm29.setIdmateriasporpersona(mmpp9);

                ModelMatriculacion mm30 = new ModelMatriculacion();
                mm30.setIdpersona(mpOchoa);
                mm30.setIdmateriasporpersona(mmpp10);


                sMatriculacion.guardar(mm1);
                sMatriculacion.guardar(mm2);
                sMatriculacion.guardar(mm3);
                sMatriculacion.guardar(mm4);
                sMatriculacion.guardar(mm5);
                sMatriculacion.guardar(mm6);
                sMatriculacion.guardar(mm7);
                sMatriculacion.guardar(mm8);
                sMatriculacion.guardar(mm9);
                sMatriculacion.guardar(mm10);
                sMatriculacion.guardar(mm11);
                sMatriculacion.guardar(mm12);
                sMatriculacion.guardar(mm13);
                sMatriculacion.guardar(mm14);
                sMatriculacion.guardar(mm15);
                sMatriculacion.guardar(mm16);
                sMatriculacion.guardar(mm17);
                sMatriculacion.guardar(mm18);
                sMatriculacion.guardar(mm19);
                sMatriculacion.guardar(mm20);
                sMatriculacion.guardar(mm21);
                sMatriculacion.guardar(mm22);
                sMatriculacion.guardar(mm23);
                sMatriculacion.guardar(mm24);
                sMatriculacion.guardar(mm25);
                sMatriculacion.guardar(mm26);
                sMatriculacion.guardar(mm27);
                sMatriculacion.guardar(mm28);
                sMatriculacion.guardar(mm29);
                sMatriculacion.guardar(mm30);

                response.put(Messages.SUCCESSFUL_KEY, "todo se guardo correctamente"); 
            }
            else{            
                response.put(Messages.ERROR_KEY, "Lista llena");
                return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);  
            }
        }
        catch(Exception ex)
        {
            String error=ex.getMessage();
            System.out.println(error);
            response.put(Messages.ERROR_KEY, Messages.ERROR_SISTEMA);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);  
        }
        
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK); 
    }
}
