package com.example.movilestudiantes

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.TelephonyManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.movilestudiantes.databinding.ActivityInformacionBinding
import com.example.movilestudiantes.databinding.ActivityLoginBinding
import com.example.movilestudiantes.utils.ApiService
import com.example.movilestudiantes.utils.GlobalDataUser
import com.example.movilestudiantes.utils.LoginResponse
import com.example.movilestudiantes.utils.RetrofitService
import com.example.movilestudiantes.utils.ToastMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ActivityLogin : AppCompatActivity() {

    private lateinit var binding:ActivityLoginBinding;


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
        

        val tel = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

    }

    private fun init()
    {
        binding.btnIniciarSesion.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch{

                val correo=binding.etCorreo.text.toString().trim();
                val contrasenia=binding.etContrasenia.text.toString().trim();

                var objDataResponseLogin= RetrofitService().getRetrofit().create(ApiService::class.java).login(correo,contrasenia);
                if(objDataResponseLogin.isSuccessful)
                {
                    val loginresponse=objDataResponseLogin.body()!!;

                    if(loginresponse.estado)
                    {
                        withContext(Dispatchers.Main) {
                            ToastMessage().toast(this@ActivityLogin, loginresponse.successful)
                        }
                        GlobalDataUser.userId=loginresponse.id;
                        GlobalDataUser.Email=loginresponse.correo;
                        GlobalDataUser.Login=loginresponse.estado;
                        GlobalDataUser.etiquetareconocer=loginresponse.etiqueta_reconocer;

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
                else{
                    withContext(Dispatchers.Main)
                    {
                        ToastMessage().toast(this@ActivityLogin, "Correo o contraseñas incorrectas")
                    }
                }
            }
        }
    }

}