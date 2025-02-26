package com.example.movilestudiantes.utils

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class DataResponce {
}

class DataSuccess
    (
    @SerializedName("successful") val successful:String,
):Serializable

/*************** para list *************/

class DataGenero
    (
    @SerializedName("data") val data:List<GeneroResponse>,
):Serializable


class DataHorario
    (
    @SerializedName("data") val data:List<HorarioResponse>,
):Serializable

class DataPersona
    (
    @SerializedName("data") val data:List<PersonaResponse>,
):Serializable


/*reconocimiento*/

class DataReconocimientoAsignarRecurso
    (
    @SerializedName("successful") val successful:String,
    @SerializedName("ooid") val ooid:String,
    ):Serializable

class DataReconocimientoEntrenar
    (
    @SerializedName("successful") val successful:String,
    @SerializedName("modelo") val modelo:String,
):Serializable

class DataReconocimientoReconocer
    (
    @SerializedName("successful") val successful:String,
    @SerializedName("data") val data:ReconocimientoResponse,
):Serializable

/********************************************/

data class ReconocimientoResponse
    (
    @SerializedName("success") val success:Int,
    @SerializedName("etiqueta") val etiqueta:String,
    @SerializedName("reconocio") val reconocio:Boolean,
    @SerializedName("base64img") val base64img:String,
    ): Serializable



data class ActualizarDatosUsuarioResponse
    (
    @SerializedName("dispositivo") val dispositivo:Int,
    @SerializedName("dispositivomensaje") val dispositivomensaje:String,
    @SerializedName("successful") val successful:String,
): Serializable

data class LoginResponse
    (
    @SerializedName("id") val id:Int,
    @SerializedName("estado") val estado:Boolean,
    @SerializedName("etiqueta_reconocer") val etiqueta_reconocer:String,
    @SerializedName("correo") val correo:String,
    @SerializedName("admin") val admin:Boolean,
    @SerializedName("successful") val successful:String,
    ): Serializable

data class HorarioResponse
    (
    @SerializedName("idhorario") val idhorario:Int,
    @SerializedName("iddiassemana") val iddiassemana:DiaSemanaResponse,
    @SerializedName("idmateriasporpersona") val idmateriasporpersona:MateriaPorPersonaResponse,
    @SerializedName("hora_inicio") val hora_inicio:Int,
    @SerializedName("minuto_inicio") val minuto_inicio:Int,
    @SerializedName("hora_fin") val hora_fin:Int,
    @SerializedName("minuto_fin") val minuto_fin:Int,
): Serializable

data class MateriaPorPersonaResponse(
    @SerializedName("idmateriaporpersona") val idmateriaporpersona:Int,
    @SerializedName("idmateria") val idmateria:MateriaResponse,
    @SerializedName("idpersona") val idpersona:PersonaResponse,
    ): Serializable


data class MateriaResponse(
    @SerializedName("idmateria") val idmateria:Int,
    @SerializedName("materia") val materia:String,
    @SerializedName("idcurso") val idcurso:CursoResponse,
    @SerializedName("estadomateria") val estadomateria:Boolean,
): Serializable

data class CursoResponse(
    @SerializedName("idcurso") val idcurso:Int,
    @SerializedName("curso") val curso:String,
    @SerializedName("estadocurso") val estadocurso:Boolean,
): Serializable


data class PersonaResponse(
    @SerializedName("idpersona") val idpersona:Int,
    @SerializedName("idtipopersona") val idtipopersona:TipoPersonaResponse,
    @SerializedName("idgenero") val idgenero:GeneroResponse,
    @SerializedName("nombres") val nombres:String,
    @SerializedName("apellidos") val apellidos:String,
    @SerializedName("correo") val correo:String,
    @SerializedName("numero_telefono") val numero_telefono:String,
    @SerializedName("credenciales_telefono") val credenciales_telefono:String,
    @SerializedName("clave_generada_telefono") val clave_generada_telefono:String,
    @SerializedName("hash_generado") val hash_generado:String,
    @SerializedName("etiqueta_reconocer") val etiqueta_reconocer:String,
    @SerializedName("estadopersona") val estadopersona:Boolean,
    @SerializedName("estadopersonaedicion") val estadopersonaedicion:Boolean,
    @SerializedName("administrador") val administrador:Boolean,
): Serializable


data class TipoPersonaResponse(
    @SerializedName("idtipopersona") val idtipopersona:Int,
    @SerializedName("tipo") val tipo:String,
    @SerializedName("estadotp") val estadotp:Boolean,
): Serializable

data class GeneroResponse(
    @SerializedName("idgenero") val idgenero:Int,
    @SerializedName("genero") val genero:String,
    @SerializedName("estadotp") val estadotp:Boolean,
): Serializable

data class DiaSemanaResponse(
    @SerializedName("iddia") val iddia:Int,
    @SerializedName("dia") val dia:String,
): Serializable


data class AsignarRecursoRequest(
    val base64recurso: String
)
