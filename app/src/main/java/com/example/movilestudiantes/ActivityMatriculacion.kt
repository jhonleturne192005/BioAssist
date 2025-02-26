package com.example.movilestudiantes

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movilestudiantes.databinding.ActivityLoginBinding
import com.example.movilestudiantes.databinding.ActivityMainBinding
import com.example.movilestudiantes.databinding.ActivityMatriculacionBinding
import com.example.movilestudiantes.recycleviewmatriculacion.MatriculacionAdapter
import com.example.movilestudiantes.utils.ApiService
import com.example.movilestudiantes.utils.DataStatic
import com.example.movilestudiantes.utils.GlobalDataUser
import com.example.movilestudiantes.utils.RetrofitService
import com.example.movilestudiantes.utils.ToastMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ActivityMatriculacion : AppCompatActivity() {

    private lateinit var binding: ActivityMatriculacionBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityMatriculacionBinding.inflate(layoutInflater);
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
        CoroutineScope(Dispatchers.IO).launch {
            var objDataResponseHorario= RetrofitService().getRetrofit().create(ApiService::class.java).listarHorario();
            if(objDataResponseHorario.isSuccessful)
            {
                try
                {
                    val listahorario=objDataResponseHorario.body()!!.data;
                    withContext(Dispatchers.Main)
                    {
                        var matriculacion_Adapter =
                            MatriculacionAdapter(listahorario, { Matricularse(it) });
                        binding.rvDataMatriculacion.layoutManager = LinearLayoutManager(
                            this@ActivityMatriculacion,
                            LinearLayoutManager.VERTICAL,
                            false
                        );
                        binding.rvDataMatriculacion.adapter = matriculacion_Adapter;
                    }
                }
                catch (e: Exception)
                {
                    Log.i(DataStatic.LogError, e.message.toString())
                }
            }
        }
    }

    private fun Matricularse(idmateriaporpersona:Int)
    {
        CoroutineScope(Dispatchers.IO).launch {
            var objDataResponseMatricula= RetrofitService().getRetrofit().create(ApiService::class.java).matricularse(GlobalDataUser.userId!!,idmateriaporpersona);
            if(objDataResponseMatricula.isSuccessful)
            {
                val toast= ToastMessage()

                try {
                    withContext(Dispatchers.Main)
                    {
                        toast.toast(
                            this@ActivityMatriculacion,
                            objDataResponseMatricula.body()!!.successful
                        );

                    }
                }catch (e: Exception) {
                    withContext(Dispatchers.Main)
                    {
                        toast.toast(
                            this@ActivityMatriculacion,
                            toast.informacionErrorMessageActualizar
                        );
                    }
                }
            }
        }
    }





}