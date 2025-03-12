//import { InterfaceCurso } from "./InterfaceCurso";
import { InterfaceDiaSemana } from "./InterfaceDiaSemana";
import { InterfaceMateriaPorPersona } from "./InterfaceMateriaPorPersona";

export interface InterfaceHorario 
{
    idhorario: number; 
    idmateriaporpersona: InterfaceMateriaPorPersona;
    iddia: InterfaceDiaSemana;
    //idcurso: InterfaceCurso;
    hora_inicio: number;
    minuto_inicio: number;
    hora_fin: number;
    minuto_fin: number;
    fecha_creacion_horario: Date;
    fecha_actualizacion_horario: Date;
}

