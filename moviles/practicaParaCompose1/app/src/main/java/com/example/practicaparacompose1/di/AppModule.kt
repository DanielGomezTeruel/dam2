package com.example.practicaparacompose1.di

import com.example.practicaparacompose1.domain.useCases.AddAlumnoUseCase
import com.example.practicaparacompose1.domain.useCases.CargarAlumnosUseCase
import com.example.practicaparacompose1.domain.useCases.DeleteAlumnoUseCase
import com.example.practicaparacompose1.domain.useCases.SelectAlumnoUseCase


object AppModule {

    val addAlumnoUseCase : AddAlumnoUseCase = AddAlumnoUseCase()

    val cargarAlumnosUseCase : CargarAlumnosUseCase = CargarAlumnosUseCase()

    val deleteAlumnoUseCase : DeleteAlumnoUseCase = DeleteAlumnoUseCase()

    val selectAlumnoUseCase : SelectAlumnoUseCase = SelectAlumnoUseCase()

}