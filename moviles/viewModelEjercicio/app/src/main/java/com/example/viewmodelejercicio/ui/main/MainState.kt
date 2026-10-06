package com.example.viewmodelejercicio.ui.main

import com.example.viewmodelejercicio.domain.model.Animal

data class MainState(
    val nombre: String,
    val animales : List<Animal>,
    val error: String? = null,
)
