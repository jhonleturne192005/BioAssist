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
import com.example.movilestudiantes.databinding.ActivityMenuBinding
import com.example.movilestudiantes.databinding.ActivityProfesorMateriasBinding
import com.example.movilestudiantes.utils.ApiService
import com.example.movilestudiantes.utils.GlobalDataUser
import com.example.movilestudiantes.utils.RetrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ActivityProfesorMaterias : AppCompatActivity() {

    companion object{
        var KEYIDMATERIAPERSONA:String="idmateriaporpersona";
    }

    private lateinit var binding: ActivityProfesorMateriasBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityProfesorMateriasBinding.inflate(layoutInflater);
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
            val objDataResponseMateriasProfesor= RetrofitService().getRetrofit().create(ApiService::class.java).ListarMateriasProfesores(GlobalDataUser.Email.toString());

            if(objDataResponseMateriasProfesor.isSuccessful)
            {
                withContext(Dispatchers.Main)
                {
                    val data_materias_profesor=objDataResponseMateriasProfesor.body()!!;
                    val tablerowencabezado=TableRow(this@ActivityProfesorMaterias);

                    val textviewc1=TextView(this@ActivityProfesorMaterias)
                    textviewc1.setText("Curso");
                    textviewc1.setTextColor(Color.BLACK)
                    textviewc1.gravity=Gravity.CENTER;
                    textviewc1.setTypeface(null, Typeface.BOLD);
                    textviewc1.setPadding (8);


                    val textviewc2=TextView(this@ActivityProfesorMaterias)
                    textviewc2.setText("Materia")
                    textviewc2.setTextColor(Color.BLACK)
                    textviewc2.gravity=Gravity.CENTER;
                    textviewc2.setTypeface(null, Typeface.BOLD);
                    textviewc2.setPadding (8);

                    val textviewc3=TextView(this@ActivityProfesorMaterias)
                    textviewc3.setText("Matriculados")
                    textviewc3.setTextColor(Color.BLACK)
                    textviewc3.gravity=Gravity.CENTER;
                    textviewc3.setTypeface(null, Typeface.BOLD);
                    textviewc3.setPadding (8);

                    tablerowencabezado.addView(textviewc1);
                    tablerowencabezado.addView(textviewc2);
                    tablerowencabezado.addView(textviewc3);

                    binding.tableLayout.addView(tablerowencabezado);

                    for(data in data_materias_profesor.data)
                    {
                        val tablerow=TableRow(this@ActivityProfesorMaterias);

                        val textviewc11=TextView(this@ActivityProfesorMaterias)
                        textviewc11.setText(data.idmateria.idcurso.curso);
                        textviewc11.setTextColor(Color.BLACK)
                        textviewc11.gravity=Gravity.CENTER;
                        textviewc11.setPadding (8);

                        val textviewc22=TextView(this@ActivityProfesorMaterias)
                        textviewc22.setText(data.idmateria.materia);
                        textviewc22.setTextColor(Color.BLACK)
                        textviewc22.gravity=Gravity.CENTER;
                        textviewc22.setPadding (8);

                        val buttonc3=Button(this@ActivityProfesorMaterias)
                        buttonc3.setText("Matriculados")
                        buttonc3.setTextColor(Color.WHITE)
                        buttonc3.gravity=Gravity.CENTER;
                        buttonc3.setPadding (8);

                        buttonc3.setOnClickListener {
                            val intent = Intent(this@ActivityProfesorMaterias, ActivityProfesorAsitenciasMatriculados::class.java)
                            intent.putExtra(KEYIDMATERIAPERSONA,data.idmateriaporpersona)
                            startActivity(intent)
                        }

                        tablerow.addView(textviewc11);
                        tablerow.addView(textviewc22);
                        tablerow.addView(buttonc3);

                        binding.tableLayout.addView(tablerow);

                    }
                }
            }
        }
    }


}