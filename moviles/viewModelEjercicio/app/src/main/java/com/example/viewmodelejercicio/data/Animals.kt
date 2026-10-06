package com.example.viewmodelejercicio.data

import android.R
import com.example.viewmodelejercicio.domain.model.Animal

object Animals {
    private val animales = mutableListOf(
        Animal("Groucho","Gato","Blanco y negro"),
        Animal("Jojo", "Gato", "Cálico")
    )


    fun dameAnimal() = animales[1]

    fun dameLista() = animales.toList()
}