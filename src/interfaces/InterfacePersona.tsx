import { InterfaceTipoPersona } from "./InterfaceTipoPersona";

export interface InterfacePersona {
    idpersona:                  number;
    idtipopersona:              InterfaceTipoPersona;
    nombres:                    string;
    apellidos:                  string;
    correo:                     string;
    contrasenia:                string;
    numero_telefono:            string;
    credenciales_telefono:      string;
    clave_generada_telefono:    string;
    hash_generado:              string;
    etiqueta_reconocer:         string;
    fecha_creacion_persona:     Date;
    fecha_modificacion_persona: Date;
    estadopersona:              boolean;
    estadopersonaedicion:       boolean;
    administrador:              boolean;
}