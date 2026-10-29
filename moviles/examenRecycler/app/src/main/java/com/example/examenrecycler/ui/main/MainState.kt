package com.example.examenrecycler.ui.main

import com.example.examenrecycler.domain.model.Alumno


data class MainState(
    val labelListaAlumnos : String,
    val alumnos : List<Alumno>,
    val error: String? = null,
)
