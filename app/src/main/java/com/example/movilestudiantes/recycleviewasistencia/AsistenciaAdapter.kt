package com.example.movilestudiantes.recycleviewasistencia

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movilestudiantes.databinding.ItemAsistenciaBinding
import com.example.movilestudiantes.databinding.ItemMatriculacionBinding
import com.example.movilestudiantes.recycleviewmatriculacion.MatriculacionViewHolder
import com.example.movilestudiantes.utils.HorarioResponse

class AsistenciaAdapter(private val data:List<HorarioResponse>, private val eventRegistrarAsistencia:(Int,String)->Unit)
    : RecyclerView.Adapter<AsistenciaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsistenciaViewHolder {
        val binding=ItemAsistenciaBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AsistenciaViewHolder(binding);
    }

    override fun getItemCount(): Int {
        return data.size;
    }

    override fun onBindViewHolder(holder: AsistenciaViewHolder, position: Int) {
        holder.render(data.get(position),eventRegistrarAsistencia)
    }
}