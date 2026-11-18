package com.example.practicaparacompose1.ui

import com.example.practicaparacompose1.domain.model.Alumno


data class MainState(
    val labelListaAlumnos : String,

    val alumnos : List<Alumno>,

    val alumno : Alumno = Alumno(
        id = 0,
        nombre = "Nombre del alumno",
        apellidos = "Apellidos del alumno",
        curso = "1º DAM",
        email = "correo@ejemplo.es",
        edad = "Edad",
        activo = true,
        selected = false,
        notas = emptyMap(),
    ),

    val selected: Boolean,


    val error: String? = null,


    )