package com.example.examenrecycler.di

import com.example.examenrecycler.domain.useCases.AddAlumnoUseCase
import com.example.examenrecycler.domain.useCases.CargarAlumnosUseCase
import com.example.examenrecycler.domain.useCases.DeleteAlumnoUseCase
import com.example.examenrecycler.domain.useCases.SelectAlumnoUseCase


object AppModule {

    val addAlumnoUseCase : AddAlumnoUseCase = AddAlumnoUseCase()

    val cargarAlumnosUseCase : CargarAlumnosUseCase = CargarAlumnosUseCase()

    val deleteAlumnoUseCase : DeleteAlumnoUseCase = DeleteAlumnoUseCase()

    val selectAlumnoUseCase : SelectAlumnoUseCase = SelectAlumnoUseCase()

}