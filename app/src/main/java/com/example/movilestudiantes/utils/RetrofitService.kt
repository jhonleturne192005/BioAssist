package com.example.movilestudiantes.utils

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


//lo que hace es enviar una sola sessionid al servidor
class SessionCookieJar : CookieJar {

    //Almacén de cookies asi como el almacen de llaves :V
    private val cookieStore = mutableMapOf<String, MutableList<Cookie>>()

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        //Guarda las cookies enviadas por el servidor
        cookieStore[url.host()] = cookies.toMutableList()
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        //retornaaa las cookies asociadas al dominio
        return cookieStore[url.host()] ?: mutableListOf()
    }
}

//Usamos el CookieJar personalizado para una sola cookie
//se asigna solo una vez
/*val okHttpClient = OkHttpClient.Builder()
    .connectTimeout(60, TimeUnit.SECONDS) // Tiempo para establecer conexión
    .readTimeout(60, TimeUnit.SECONDS) // Tiempo de espera para la respuesta
    .writeTimeout(60, TimeUnit.SECONDS) // Tiempo para enviar datos
    .cookieJar(SessionCookieJar())
    .build()*/

val okHttpClient = OkHttpClient.Builder()
    .connectTimeout(60, TimeUnit.SECONDS) // Tiempo para establecer conexión
    .readTimeout(60, TimeUnit.HOURS) // Tiempo de espera para la respuesta
    .writeTimeout(60, TimeUnit.SECONDS) // Tiempo para enviar datos
    .cookieJar(SessionCookieJar())
    .build()

class RetrofitService
{
    fun getRetrofit(): Retrofit
    {
        val retrofit= Retrofit
            .Builder()
            .baseUrl(UrlAPI.URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit;
    }
}