package com.example.viewmodelejercicio.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.viewmodelejercicio.databinding.ItemAnimalBinding
import com.example.viewmodelejercicio.domain.model.Animal

class AnimalAdapter ()
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
        return AnimalViewHolder(binding)
    }

    override fun onBindViewHolder(
        holer: AnimalViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

class AnimalViewHolder(
    private val binding: ItemAnimalBinding

): RecyclerView.ViewHolder(binding.root)
{
    fun bind(animal: Animal) {
        binding.nombreAnimal.text = animal.nombre
    }

}


}


class AnimalDiffCallback : DiffUtil.ItemCallback<Animal>(){


}