package com.example.movilestudiantes.utils

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService
{

    //    @FormUrlEncoded -> solo si se envia un field

    @POST("/api/genero/listar")
    suspend fun listarGenero(): Response<DataGenero>;


    @POST("/api/horario/listar")
    suspend fun listarHorario(): Response<DataHorario>;


    @FormUrlEncoded
    @POST("/api/asistencia/listarmateriaparaasistenciaporestudiante")
    suspend fun listarMateriaPorEstudianteParaAsistencia(@Field("idpersona") idpersona: Int): Response<DataHorario>;

    @FormUrlEncoded
    @POST("/api/persona/obtenerpersonaid")
    suspend fun listarPorIdPersona(@Field("idpersona") idpersona: Int): Response<DataPersona>;

    @FormUrlEncoded
    @POST("/api/persona/actualizardatosusuario")
    suspend fun guardarInformacion(
        @Field("idpersona") idpersona: Int,
        @Field("correo") correo: String,
        @Field("nombres") nombres: String,
        @Field("apellidos") apellidos: String,
        @Field("numero_telefono") numero_telefono: String,
        @Field("contrasenia") contrasenia: String,
        @Field("idgenero") genero: Int,
        @Field("credenciales_telefono") credenciales_telefono: String,
        @Field("etiquetareconocer") etiqueta_reconocer: String,
        ): Response<ActualizarDatosUsuarioResponse>;


    @FormUrlEncoded
    @POST("/api/persona/login")
    suspend fun login(
        @Field("correo") correo: String,
        @Field("contrasenia") contrasenia: String,
        @Field("latitud") latitud: String,
        @Field("longitud") longitud: String,
    ): Response<LoginResponse>;


    @FormUrlEncoded
    @POST("/api/matriculacion/crear")
    suspend fun matricularse(
        @Field("idpersona") idpersona: Int,
        @Field("idmateriasporpersona") idmateriasporpersona: Int,
    ): Response<DataSuccess>;



    @POST("/api/reconocimiento/crearasistencia")
    suspend fun asistencia(
        @Body base64recurso: AsignarRecursoRequestAsistencia
        ): Response<DataSuccess>;

    @POST("/api/reconocimiento/asignarrecurso")
    suspend fun asignarrecurso(@Body base64recurso: AsignarRecursoRequest): Response<DataReconocimientoAsignarRecurso>;

    @FormUrlEncoded
    @POST("/api/reconocimiento/reconocer")
    suspend fun reconocer(@Field("base64recurso") base64recurso: AsignarRecursoRequest): Response<DataReconocimientoReconocer>;


    @FormUrlEncoded
    @POST("/api/persona/actualizaretiquetareconocimientousuario")
    suspend fun ActualizarEtiquetaReconocimientoPersona(@Field("idpersona") idpersona: Int,
                          @Field("etiqueta") etiqueta: String): Response<DataSuccess>;

    /*nuevooo profesores*/

    @FormUrlEncoded
    @POST("/api/materiaporpersona/listarporpersona")
    suspend fun ListarMateriasProfesores(@Field("correopersona") correopersona: String): Response<DataMateriasProfesor>;

    @FormUrlEncoded
    @POST("/api/materiaporpersona/listarasistenciamatriculados")
    suspend fun ListarAsistenciaMatriculados(@Field("idmateriaporpersona") idmateriaporpersona: Int): Response<DataAsistenciaMatriculados>;

}