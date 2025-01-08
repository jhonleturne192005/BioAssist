import { InterfaceCurso } from "./InterfaceCurso";

export interface InterfaceMateria {
    idmateria:                  number;
    idcurso:                    InterfaceCurso;
    materia:                    string;
    estadomateria:              boolean;
    fecha_creacion_materia:     Date;
    fecha_modificacion_materia: Date;
}