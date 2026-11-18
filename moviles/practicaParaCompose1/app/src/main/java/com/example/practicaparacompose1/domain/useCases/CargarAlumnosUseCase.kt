package com.example.practicaparacompose1.domain.useCases

import com.example.practicaparacompose1.data.Alumnos
import com.example.practicaparacompose1.domain.model.Alumno


class CargarAlumnosUseCase {

    fun dameLista() : List<Alumno> = Alumnos.dameLista()
}