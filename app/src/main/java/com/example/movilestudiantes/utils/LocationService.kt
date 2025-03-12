package com.example.movilestudiantes.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine

@OptIn(ExperimentalCoroutinesApi::class)
class LocationService {

    suspend fun getUserLocation(context: Context): Location?{
        val fusedLocationProvicerClient= LocationServices.getFusedLocationProviderClient(context)
        var permisos=false;
        if(
            ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED
        )
        {
            permisos=true;
        }
        else ToastMessage().toast(context,"no hay permisos :(")

        val locationManager=context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGPSEnabled=locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) || locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        if(!permisos && !isGPSEnabled) {
            ToastMessage().toast(context,"negativo pasa algo :(")
            return null;
        }

        return suspendCancellableCoroutine { cont->
            fusedLocationProvicerClient.lastLocation.apply {
                ToastMessage().toast(context,"siiiiiiiii")

                if(isComplete)
                {
                    if(isSuccessful){
                        ToastMessage().toast(context,"Chevere")
                        cont.resume(result){}
                    }else{
                        ToastMessage().toast(context,"NOO Chevere")
                        cont.resume(null){}
                    }
                    return@suspendCancellableCoroutine
                }else{
                    ToastMessage().toast(context,"NOO HAY")

                }

                addOnSuccessListener { cont.resume(it){} }
                addOnFailureListener{ cont.resume(null){} }
                addOnCanceledListener { cont.resume(null){} }
            }
        };
    }


}