package com.example.examenrecycler.domain.useCases

import com.example.examenrecycler.data.Alumnos
import com.example.examenrecycler.domain.model.Alumno

class SelectAlumnoUseCase {

    fun selectAlumno(alumno: Alumno) = Alumnos.selectAlumno(alumno)

}