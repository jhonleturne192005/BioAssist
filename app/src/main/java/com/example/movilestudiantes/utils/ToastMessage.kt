package com.example.movilestudiantes.utils

import android.content.Context
import android.widget.Toast

class ToastMessage
{

    val informacionErrorMessageActualizar="Ha ocurrido un error en la actualizacion de datos";
    val informacionErrorMessageGuardar="Ha ocurrido un error en guardar datos";

    public fun toast(context: Context, message:String)
    {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}