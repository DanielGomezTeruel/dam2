package com.example.examenrecycler.ui.main

import android.graphics.Color.red
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.examenrecycler.databinding.ItemAlumnoBinding
import com.example.examenrecycler.domain.model.Alumno


class AlumnoAdapter (
    val selectAlumno : (Alumno) -> Unit,
)
    : ListAdapter<Alumno, AlumnoAdapter.AlumnoViewHolder>(AlumnoDiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AlumnoViewHolder {
        val binding = ItemAlumnoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AlumnoViewHolder(binding, selectAlumno)
    }

    override fun onBindViewHolder(
        holder: AlumnoViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

class AlumnoViewHolder(
    private val binding: ItemAlumnoBinding,
    private val onClickSelect : (Alumno) -> Unit,

    ): RecyclerView.ViewHolder(binding.root)
{
    fun bind(alumno: Alumno) {
        with(binding) {
            nombreAlumno.text = "${alumno.nombre} ${alumno.apellidos}"
            cursoAlumno.text = alumno.curso
            edadAlumno.text = "${alumno.edad} años  "
            emailAlumno.text = alumno.email
            if(alumno.activo){
                activoAlumno.text = "Activo"
            }
            else{
                activoAlumno.text = "Inactivo"
            }
            if(alumno.selected){
                selectedAlumno.text = "Selected"
            }

            if(!alumno.selected){
                selectedAlumno.text = ""
            }

            root.setOnClickListener {
                onClickSelect(alumno)
            }
        }
    }

}


}


class AlumnoDiffCallback : DiffUtil.ItemCallback<Alumno>() {
    override fun areItemsTheSame(oldItem: Alumno, newItem: Alumno): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Alumno, newItem: Alumno): Boolean =
        oldItem == newItem
}