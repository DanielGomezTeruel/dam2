package com.example.practicarecycler.data

import android.R
import com.example.practicarecycler.domain.model.Animal

object Animals {
    private var animales = mutableListOf(
        Animal(1, "Groucho","Gato","Blanco y negro"),
        Animal(2, "Jojo", "Gato", "Cálico")
    )


    fun dameAnimal() = animales[1]

    fun dameLista() = animales.toList()

    fun dameListaShuffle(){
        animales = animales.shuffled().toMutableList()
    }

    fun deleteAnimal(animal : Animal){
        animales.remove(animal)
    }

    fun addAnimal(animal:Animal){
        animales.add(animal)
    }
}