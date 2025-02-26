package com.example.movilestudiantes

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.movilestudiantes.databinding.ActivityInformacionBinding
import com.example.movilestudiantes.utils.ApiService
import com.example.movilestudiantes.utils.DataStatic
import com.example.movilestudiantes.utils.GeneroResponse
import com.example.movilestudiantes.utils.GlobalDataUser
import com.example.movilestudiantes.utils.PersonaResponse
import com.example.movilestudiantes.utils.RetrofitService
import com.example.movilestudiantes.utils.ToastMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ActivityInformacion : AppCompatActivity()
{
    private lateinit var binding: ActivityInformacionBinding;
    private lateinit var ListResponseGenero: List<GeneroResponse>
    private lateinit var ListResponsePersona: List<PersonaResponse>
    private var selectindexgenero:Int=0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityInformacionBinding.inflate(layoutInflater);
        setContentView(binding.root);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        init();
    }

    fun init()
    {

        CoroutineScope(Dispatchers.IO).launch {

            var objDataResponsePersona= RetrofitService().getRetrofit().create(ApiService::class.java).listarPorIdPersona(GlobalDataUser.userId!!);
            if(objDataResponsePersona.isSuccessful)
            {
                try
                {
                    ListResponsePersona=objDataResponsePersona.body()!!.data;
                    val pr=ListResponsePersona.get(0);
                    //atualiza el hilo principal de la aplicacion
                    withContext(Dispatchers.Main)
                    {
                        if(ListResponsePersona.size>0) {
                            binding.etCorreo.setText(pr.correo);
                            binding.etNombres.setText(pr.nombres);
                            binding.etApellidos.setText(pr.apellidos);
                            binding.etNumeroTelefono.setText(pr.numero_telefono);
                            binding.spGenero.setSelection(pr.idgenero.idgenero);
                            ToastMessage().toast(
                                this@ActivityInformacion,
                                "Datos cargados correctamente"
                            )
                        }
                    }
                }
                catch (e: Exception)
                {
                    Log.i(DataStatic.LogError, e.message.toString())
                }
            }
        }


        binding.btnenviarvideo.setOnClickListener{
            val intent = Intent(this@ActivityInformacion, ActivityCameraVideo::class.java)
            startActivity(intent)
        }

        ObtenerDataGenero();
        binding.btnRegistrar.setOnClickListener{guardarInformacion()}
    }

    private fun ObtenerDataGenero()
    {
        CoroutineScope(Dispatchers.IO).launch {

            var objDataResponseGenero= RetrofitService().getRetrofit().create(ApiService::class.java).listarGenero();
            if(objDataResponseGenero.isSuccessful)
            {
                try
                {
                    val listagenero=objDataResponseGenero.body()!!.data;
                    //atualiza el hilo principal de la aplicacion
                    withContext(Dispatchers.Main)
                    {
                        spinerGenero(listagenero);

                    }
                }
                catch (e: Exception)
                {
                    Log.i(DataStatic.LogError, e.message.toString())
                }
            }
        }
    }


    private fun spinerGenero(generos:List<GeneroResponse>)
    {
        ListResponseGenero=generos;
        val generolist = mutableListOf<String>("Genero");
        for(genero in generos)
            generolist.add(genero.genero)

        val adapter_genero=ArrayAdapter(this,android.R.layout.simple_spinner_item,generolist)
        binding.spGenero.adapter=adapter_genero;

        binding.spGenero.onItemSelectedListener=object:AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectindexgenero=ListResponseGenero[position].idgenero;
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }


    private fun guardarInformacion()
    {
        val correo=binding.etCorreo.text.toString().trim();
        val nombres=binding.etNombres.text.toString().trim();
        val apellidos=binding.etApellidos.text.toString().trim();
        val numero_telefono=binding.etNumeroTelefono.text.toString().trim();
        val contrasenia=binding.etContrasenia.text.toString().trim();

        if(!correo.equals("") && !nombres.equals("") && !apellidos.equals("") &&
            !numero_telefono.equals("") && !contrasenia.equals("") && selectindexgenero!=0) {
            CoroutineScope(Dispatchers.IO).launch {
                var objDataResponseInformacion= RetrofitService().getRetrofit().create(ApiService::class.java).guardarInformacion(
                    GlobalDataUser.userId!!,correo,nombres,apellidos,numero_telefono,contrasenia,selectindexgenero,"ac", GlobalDataUser.etiquetareconocer!!);

                val toast=ToastMessage()

                if(objDataResponseInformacion.isSuccessful) {
                    withContext(Dispatchers.Main)
                    {
                        toast.toast(
                            this@ActivityInformacion,
                            objDataResponseInformacion.body()!!.successful
                        )

                    }
                }else {
                    withContext(Dispatchers.Main)
                    {
                        toast.toast(
                            this@ActivityInformacion,
                            toast.informacionErrorMessageActualizar
                        );

                    }
                }
            }
        }
        else
        {
            ToastMessage().toast(this,"Existen campos vacios");
        }
    }



}