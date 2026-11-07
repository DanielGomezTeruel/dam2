package com.example.practicarecycler.di

import com.example.practicarecycler.domain.useCases.AddAnimalUseCase
import com.example.practicarecycler.domain.useCases.CargarAnimalesShuffleUseCase
import com.example.practicarecycler.domain.useCases.CargarAnimalesUseCase
import com.example.practicarecycler.domain.useCases.DameNombreUseCase
import com.example.practicarecycler.domain.useCases.DeleteAnimalUseCase


object AppModule {
    val dameNombreUseCase : DameNombreUseCase = DameNombreUseCase()

    val deleteAnimalUseCase : DeleteAnimalUseCase = DeleteAnimalUseCase()
    val cargarAnimalesUseCase : CargarAnimalesUseCase = CargarAnimalesUseCase()

    val addAnimalUseCase : AddAnimalUseCase = AddAnimalUseCase()

    val cargarAnimalesShuffleUseCase : CargarAnimalesShuffleUseCase = CargarAnimalesShuffleUseCase()

}