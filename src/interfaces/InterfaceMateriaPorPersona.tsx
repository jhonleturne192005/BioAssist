import { InterfaceMateria } from "./InterfaceMateria";
import { InterfacePersona } from "./InterfacePersona";

export interface InterfaceMateriaPorPersona {
    idmateriaporpersona:                     number;
    idmateria:                               InterfaceMateria;
    idpersona:                               InterfacePersona;
    fecha_creacion_materias_por_persona:     Date;
    fecha_modificacion_materias_por_persona: Date;
}