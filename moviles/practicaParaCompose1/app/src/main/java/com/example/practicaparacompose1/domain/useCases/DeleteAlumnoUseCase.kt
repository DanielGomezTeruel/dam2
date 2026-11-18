package com.example.practicaparacompose1.domain.useCases

import com.example.practicaparacompose1.data.Alumnos

class DeleteAlumnoUseCase {

    fun deleteAlumno() {
        Alumnos.deleteAlumno()
    }
}