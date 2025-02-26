package com.example.movilestudiantes.recycleviewmatriculacion

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movilestudiantes.databinding.ItemMatriculacionBinding
import com.example.movilestudiantes.utils.DataHorario
import com.example.movilestudiantes.utils.HorarioResponse

class MatriculacionAdapter(private val data:List<HorarioResponse>,private val eventMatricularse:(Int)->Unit)
    : RecyclerView.Adapter<MatriculacionViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatriculacionViewHolder {
        val binding=ItemMatriculacionBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MatriculacionViewHolder(binding);
    }

    override fun getItemCount(): Int {
        return data.size;
    }

    override fun onBindViewHolder(holder: MatriculacionViewHolder, position: Int) {
        holder.render(data.get(position),eventMatricularse)
        //holder.itemView.setOnClickListener{eventMatricularse(data.get(position).idmateriasporpersona.idmateriaporpersona)}
    }
}