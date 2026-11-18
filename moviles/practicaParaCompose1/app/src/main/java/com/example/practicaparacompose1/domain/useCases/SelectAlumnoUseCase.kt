package com.example.practicaparacompose1.domain.useCases

import com.example.practicaparacompose1.data.Alumnos
import com.example.practicaparacompose1.domain.model.Alumno

class SelectAlumnoUseCase {

    fun selectAlumno(alumno: Alumno) = Alumnos.selectAlumno(alumno)

}