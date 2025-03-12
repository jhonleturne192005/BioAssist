package com.example.movilestudiantes

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movilestudiantes.databinding.ActivityAsistenciaBinding
import com.example.movilestudiantes.databinding.ActivityMatriculacionBinding
import com.example.movilestudiantes.recycleviewasistencia.AsistenciaAdapter
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

class ActivityAsistencia : AppCompatActivity() {

    private lateinit var binding: ActivityAsistenciaBinding;

    companion object{
        var KEYIDHORARIO:String="idhorario";
        var KEYIDASISTENCIA:String="idasistencia";
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityAsistenciaBinding.inflate(layoutInflater);
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
            var objDataResponseHorario= RetrofitService().getRetrofit().create(ApiService::class.java).listarMateriaPorEstudianteParaAsistencia(GlobalDataUser.userId!!);
            if(objDataResponseHorario.isSuccessful)
            {
                try
                {
                    val listahorario=objDataResponseHorario.body()!!.data;
                    withContext(Dispatchers.Main)
                    {
                        var asistencia_Adapter =
                            AsistenciaAdapter(listahorario, { x, y -> Asistencia(x, y) });
                        binding.rvDataAsistencia.layoutManager = LinearLayoutManager(
                            this@ActivityAsistencia,
                            LinearLayoutManager.VERTICAL,
                            false
                        );
                        binding.rvDataAsistencia.adapter = asistencia_Adapter;
                    }
                }
                catch (e: Exception)
                {
                    Log.i(DataStatic.LogError, e.message.toString())
                }
            }
        }
    }


    private fun Asistencia(idhorario:Int,ubic_radio:String)
    {

        val intent = Intent(this@ActivityAsistencia, ActivityCamera::class.java)
        intent.putExtra(KEYIDHORARIO,idhorario)
        startActivity(intent)

        /*CoroutineScope(Dispatchers.IO).launch {
            var objDataResponseMatricula= RetrofitService().getRetrofit().create(ApiService::class.java).asistencia(
                GlobalDataUser.userId!!,idhorario,ubic_radio);
            if(objDataResponseMatricula.isSuccessful)
            {
                val toast= ToastMessage()

                try {
                    withContext(Dispatchers.Main)
                    {
                        toast.toast(
                            this@ActivityAsistencia,
                            objDataResponseMatricula.body()!!.successful
                        );

                    }
                }
                catch (e: Exception)
                {
                    withContext(Dispatchers.Main)
                    {
                        toast.toast(this@ActivityAsistencia, toast.informacionErrorMessageGuardar);
                    }
                }
            }
        }*/

    }

}