package com.example.viewmodelejercicio.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.viewmodelejercicio.databinding.ItemAnimalBinding
import com.example.viewmodelejercicio.domain.model.Animal

class AnimalAdapter (
    val delAnimal : (Animal) -> Unit,
)
    : ListAdapter<Animal, AnimalAdapter.AnimalViewHolder>(AnimalDiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AnimalViewHolder {
        val binding = ItemAnimalBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AnimalViewHolder(binding, delAnimal)
    }

    override fun onBindViewHolder(
        holder: AnimalViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

class AnimalViewHolder(
    private val binding: ItemAnimalBinding,
    private val onClickDelete : (Animal) -> Unit,

): RecyclerView.ViewHolder(binding.root)
{
    fun bind(animal: Animal) {
        with(binding) {
            nombreAnimal.text = animal.nombre

            root.setOnClickListener {
                onClickDelete(animal)
            }
        }
    }

}


}


class AnimalDiffCallback : DiffUtil.ItemCallback<Animal>() {
    override fun areItemsTheSame(oldItem: Animal, newItem: Animal): Boolean =
        oldItem.nombre == newItem.nombre

    override fun areContentsTheSame(oldItem: Animal, newItem: Animal): Boolean =
        oldItem == newItem
}