package com.example.movilestudiantes.recycleviewasistencia

import androidx.recyclerview.widget.RecyclerView
import com.example.movilestudiantes.databinding.ItemAsistenciaBinding
import com.example.movilestudiantes.databinding.ItemMatriculacionBinding
import com.example.movilestudiantes.utils.HorarioResponse

class AsistenciaViewHolder(val binding: ItemAsistenciaBinding): RecyclerView.ViewHolder(binding.root)
{
    fun render(Data: HorarioResponse, eventRegistrarAsistencia:(Int,String)->Unit)
    {
        val hora_inicio=String.format("%02d",Data.hora_inicio);
        val hora_fin=String.format("%02d",Data.hora_fin);
        val minuto_inicio=String.format("%02d",Data.minuto_inicio);
        val minuto_fin=String.format("%02d",Data.minuto_fin);

        val horario=hora_inicio+":"+minuto_inicio+" - "+hora_fin+":"+minuto_fin;

        binding.materia.text=Data.idmateriasporpersona.idmateria.materia;
        binding.curso.text=Data.idmateriasporpersona.idmateria.idcurso.curso;
        binding.profesor.text=Data.idmateriasporpersona.idpersona.nombres+" "+Data.idmateriasporpersona.idpersona.apellidos;
        binding.horario.text=Data.iddiassemana.dia+", "+horario;

        binding.btnregistrarasistencia.setOnClickListener{eventRegistrarAsistencia(Data.idmateriasporpersona.idmateriaporpersona,"")}
    }
}