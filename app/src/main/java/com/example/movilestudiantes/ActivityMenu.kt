package com.example.movilestudiantes

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.movilestudiantes.databinding.ActivityMatriculacionBinding
import com.example.movilestudiantes.databinding.ActivityMenuBinding

class ActivityMenu : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityMenuBinding.inflate(layoutInflater);
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
        binding.btninformacion.setOnClickListener{
            val intent = Intent(this@ActivityMenu, ActivityInformacion::class.java)
            startActivity(intent)
        }

        binding.btnmatricula.setOnClickListener{
            val intent = Intent(this@ActivityMenu, ActivityMatriculacion::class.java)
            startActivity(intent)
        }

        binding.btnasistencia.setOnClickListener{
            val intent = Intent(this@ActivityMenu, ActivityAsistencia::class.java)
            startActivity(intent)
        }
    }

}