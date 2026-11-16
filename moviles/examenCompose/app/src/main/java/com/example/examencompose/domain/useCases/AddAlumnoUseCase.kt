package com.example.examenrecycler.domain.useCases

import com.example.examenrecycler.data.Alumnos
import com.example.examenrecycler.domain.model.Alumno

class AddAlumnoUseCase {

    fun addAlumno(alumno: Alumno){
        Alumnos.addAlumno(alumno)
    }

}
