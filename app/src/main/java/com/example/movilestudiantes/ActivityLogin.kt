package com.example.movilestudiantes

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings.Secure
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.movilestudiantes.databinding.ActivityLoginBinding
import com.example.movilestudiantes.utils.ApiService
import com.example.movilestudiantes.utils.DataStatic
import com.example.movilestudiantes.utils.GlobalDataUser
import com.example.movilestudiantes.utils.LocationService
import com.example.movilestudiantes.utils.RetrofitService
import com.example.movilestudiantes.utils.ToastMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ActivityLogin : AppCompatActivity() {

    private lateinit var binding:ActivityLoginBinding;
    private val locationService:LocationService=LocationService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityLoginBinding.inflate(layoutInflater);
        setContentView(binding.root);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        init();
    }

    private fun init()
    {
        checkPermissions();
        binding.btnIniciarSesion.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch{

                val correo=binding.etCorreo.text.toString().trim();
                val contrasenia=binding.etContrasenia.text.toString().trim();
                val objDataResponseLogin= RetrofitService().getRetrofit().create(ApiService::class.java).login(correo,contrasenia,GlobalDataUser.latitud,GlobalDataUser.longitud);
                if(objDataResponseLogin.isSuccessful)
                {
                    val loginresponse=objDataResponseLogin.body()!!;

                    try{

                        if(loginresponse.estado)
                        {
                            withContext(Dispatchers.Main) {
                                ToastMessage().toast(this@ActivityLogin, loginresponse.successful)
                            }
                            GlobalDataUser.Login=loginresponse.estado;
                            GlobalDataUser.userId=loginresponse.id;
                            GlobalDataUser.Email=loginresponse.correo;
                            GlobalDataUser.Login=loginresponse.estado;
                            GlobalDataUser.etiquetareconocer=loginresponse.etiqueta_reconocer;
                            GlobalDataUser.tipousuario=loginresponse.tipousuario.toString().trim()
                            val intent = Intent(this@ActivityLogin, ActivityMenu::class.java)
                            startActivity(intent)
                        }
                        else
                        {
                            withContext(Dispatchers.Main) {
                                ToastMessage().toast(
                                    this@ActivityLogin,
                                    "Correo o contraseñas incorrectas"
                                )
                            }
                        }

                    }
                    catch (e: Exception)
                    {
                        Log.i(DataStatic.LogError, e.message.toString())
                    }

                }
                else{
                    withContext(Dispatchers.Main)
                    {
                        ToastMessage().toast(this@ActivityLogin, "Correo o contraseñas incorrectas")
                    }
                }

            }
        }
    }

    private fun ubicacion() {
        lifecycleScope.launch {
            val result=locationService.getUserLocation(this@ActivityLogin)
            if(result!=null){
                GlobalDataUser.latitud=result.latitude.toString();
                GlobalDataUser.longitud=result.longitude.toString();
                binding.latlong.text="Latitud ${result.latitude} y longitud: ${result.longitude} "
            }
        }

    }

    private fun insertidphone()
    {
        val deviceId = Secure.getString(contentResolver, Secure.ANDROID_ID);
        binding.datatelefono.text=deviceId;
        GlobalDataUser.IDPHONE=deviceId;
    }



    // ****************** permisos *********************
    //https://www.youtube.com/watch?v=rdfjT0bQBgs

    private fun checkPermissions()
    {
        if(
            ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this,Manifest.permission.READ_PHONE_STATE)!=PackageManager.PERMISSION_GRANTED

        )
        {
            ToastMessage().toast(this,"Permisos nooo aceptados")
            //permiso no aceptado por el momento
            requestCOARSE_FINELocationPermission();
        }
        else
        {
            ToastMessage().toast(this,"Permisos aceptados")
            ubicacion();
            insertidphone();
        }
    }

    private fun requestCOARSE_FINELocationPermission()
    {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_COARSE_LOCATION) ||
            ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION) ||
            ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_PHONE_STATE)){
            //rechazo permisos
            ToastMessage().toast(this,"Permisos rechazados, activar en configuraciones")
        }
        else{
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_PHONE_STATE),777)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
        deviceId: Int
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId)

        if(requestCode==777){
            if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED && grantResults[2]==PackageManager.PERMISSION_GRANTED)
            {
                ToastMessage().toast(this,"Permisos de localizacion aceptados")
                ubicacion();
                insertidphone();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                   // binding.datatelefono.text= Build.getSerial()
                };
            }
            else{
                ToastMessage().toast(this,"Permisos rechazados por primera vez")
            }
        }

    }
}