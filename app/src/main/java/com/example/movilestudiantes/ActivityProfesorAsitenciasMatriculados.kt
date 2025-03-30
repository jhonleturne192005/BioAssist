package com.example.movilestudiantes

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.TableRow
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.setPadding
import com.example.movilestudiantes.ActivityAsistencia.Companion.KEYIDHORARIO
import com.example.movilestudiantes.ActivityProfesorMaterias.Companion.KEYIDMATERIAPERSONA
import com.example.movilestudiantes.databinding.ActivityProfesorAsitenciasMatriculadosBinding
import com.example.movilestudiantes.databinding.ActivityProfesorMateriasBinding
import com.example.movilestudiantes.utils.ApiService
import com.example.movilestudiantes.utils.GlobalDataUser
import com.example.movilestudiantes.utils.RetrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ActivityProfesorAsitenciasMatriculados : AppCompatActivity() {

    private lateinit var binding: ActivityProfesorAsitenciasMatriculadosBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityProfesorAsitenciasMatriculadosBinding.inflate(layoutInflater);
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
        val idmateriaporpersona= getIntent().getIntExtra(KEYIDMATERIAPERSONA,0);

        CoroutineScope(Dispatchers.IO).launch {
            val objDataResponseMatriculados= RetrofitService().getRetrofit().create(ApiService::class.java).ListarAsistenciaMatriculados(idmateriaporpersona);

            if(objDataResponseMatriculados.isSuccessful)
            {
                withContext(Dispatchers.Main)
                {
                    val data_matriculados=objDataResponseMatriculados.body()!!;
                    val tablerowencabezado= TableRow(this@ActivityProfesorAsitenciasMatriculados);

                    val textviewc1= TextView(this@ActivityProfesorAsitenciasMatriculados)
                    textviewc1.setText("Nombres");
                    textviewc1.setTextColor(Color.BLACK)
                    textviewc1.gravity= Gravity.CENTER;
                    textviewc1.setTypeface(null, Typeface.BOLD);
                    textviewc1.setPadding (8);


                    val textviewc2= TextView(this@ActivityProfesorAsitenciasMatriculados)
                    textviewc2.setText("Apellidos")
                    textviewc2.setTextColor(Color.BLACK)
                    textviewc2.gravity= Gravity.CENTER;
                    textviewc2.setTypeface(null, Typeface.BOLD);
                    textviewc2.setPadding (8);

                    /*val textviewc3= TextView(this@ActivityProfesorAsitenciasMatriculados)
                    textviewc3.setText("Correo")
                    textviewc3.setTextColor(Color.BLACK)
                    textviewc3.gravity= Gravity.CENTER;
                    textviewc3.setTypeface(null, Typeface.BOLD);
                    textviewc3.setPadding (8);

                    val textviewc4= TextView(this@ActivityProfesorAsitenciasMatriculados)
                    textviewc4.setText("Telefono")
                    textviewc4.setTextColor(Color.BLACK)
                    textviewc4.gravity= Gravity.CENTER;
                    textviewc4.setTypeface(null, Typeface.BOLD);
                    textviewc4.setPadding (8);*/

                    val textviewc5= TextView(this@ActivityProfesorAsitenciasMatriculados)
                    textviewc5.setText("Genero")
                    textviewc5.setTextColor(Color.BLACK)
                    textviewc5.gravity= Gravity.CENTER;
                    textviewc5.setTypeface(null, Typeface.BOLD);
                    textviewc5.setPadding (8);

                    val textviewc6= TextView(this@ActivityProfesorAsitenciasMatriculados)
                    textviewc6.setText("Asistencia")
                    textviewc6.setTextColor(Color.BLACK)
                    textviewc6.gravity= Gravity.CENTER;
                    textviewc6.setTypeface(null, Typeface.BOLD);
                    textviewc6.setPadding (8);

                    tablerowencabezado.addView(textviewc1);
                    tablerowencabezado.addView(textviewc2);
                    //tablerowencabezado.addView(textviewc3);
                    //tablerowencabezado.addView(textviewc4);
                    tablerowencabezado.addView(textviewc5);
                    tablerowencabezado.addView(textviewc6);

                    binding.tableLayout.addView(tablerowencabezado);

                    for(data in data_matriculados.data)
                    {
                        val tablerow= TableRow(this@ActivityProfesorAsitenciasMatriculados);

                        val textviewc11= TextView(this@ActivityProfesorAsitenciasMatriculados)
                        textviewc11.setText(data.nombres);
                        textviewc11.setTextColor(Color.BLACK)
                        textviewc11.gravity= Gravity.CENTER;
                        textviewc11.setPadding (8);

                        val textviewc22= TextView(this@ActivityProfesorAsitenciasMatriculados)
                        textviewc22.setText(data.apellidos);
                        textviewc22.setTextColor(Color.BLACK)
                        textviewc22.gravity= Gravity.CENTER;
                        textviewc22.setPadding (8);

                        /*val textviewc23= TextView(this@ActivityProfesorAsitenciasMatriculados)
                        textviewc23.setText(data.correo);
                        textviewc23.setTextColor(Color.BLACK)
                        textviewc23.gravity= Gravity.CENTER;
                        textviewc23.setPadding (8);

                        val textviewc24= TextView(this@ActivityProfesorAsitenciasMatriculados)
                        textviewc24.setText(data.numeroTelefono);
                        textviewc24.setTextColor(Color.BLACK)
                        textviewc24.gravity= Gravity.CENTER;
                        textviewc24.setPadding (8);*/

                        val textviewc25= TextView(this@ActivityProfesorAsitenciasMatriculados)
                        textviewc25.setText(data.genero);
                        textviewc25.setTextColor(Color.BLACK)
                        textviewc25.gravity= Gravity.CENTER;
                        textviewc25.setPadding (8);

                        val textviewc26= TextView(this@ActivityProfesorAsitenciasMatriculados)
                        textviewc26.setText(if (data.asistencia) "✓" else "x");
                        textviewc26.setTextColor(Color.BLACK)
                        textviewc26.gravity= Gravity.CENTER;
                        textviewc26.setPadding (8);

                        tablerow.addView(textviewc11);
                        tablerow.addView(textviewc22);
                        //tablerow.addView(textviewc23);
                        //tablerow.addView(textviewc24);
                        tablerow.addView(textviewc25);
                        tablerow.addView(textviewc26);

                        binding.tableLayout.addView(tablerow);

                    }
                }
            }
        }
    }

}