package com.example.practicarecycler.ui.main

import com.example.practicarecycler.domain.model.Animal

data class MainState(
    val nombre: String,
    val animales : List<Animal>,
    val error: String? = null,
)
