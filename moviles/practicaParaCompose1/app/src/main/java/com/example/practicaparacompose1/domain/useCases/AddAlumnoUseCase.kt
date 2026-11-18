package com.example.practicaparacompose1.domain.useCases

import com.example.practicaparacompose1.data.Alumnos
import com.example.practicaparacompose1.domain.model.Alumno

class AddAlumnoUseCase {

    fun addAlumno(alumno: Alumno){
        Alumnos.addAlumno(alumno)
    }

}
