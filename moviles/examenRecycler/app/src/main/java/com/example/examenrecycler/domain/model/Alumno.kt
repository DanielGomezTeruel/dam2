package com.example.examenrecycler.domain.model

data class Alumno(
    val id : Int,
    val nombre: String,
    val apellidos: String,
    val curso: String,
    val email: String,
    val edad: String,
    val activo: Boolean,
    var selected: Boolean,
)
