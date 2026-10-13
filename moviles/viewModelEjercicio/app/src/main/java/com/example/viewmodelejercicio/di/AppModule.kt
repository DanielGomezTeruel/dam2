package com.example.viewmodelejercicio.di

import com.example.viewmodelejercicio.domain.useCases.AddAnimalUseCase
import com.example.viewmodelejercicio.domain.useCases.CargarAnimalesShuffleUseCase
import com.example.viewmodelejercicio.domain.useCases.CargarAnimalesUseCase
import com.example.viewmodelejercicio.domain.useCases.DameNombreUseCase
import com.example.viewmodelejercicio.domain.useCases.DeleteAnimalUseCase
import com.example.viewmodelejercicio.ui.main.MainViewModel

object AppModule {
    val dameNombreUseCase : DameNombreUseCase = DameNombreUseCase()

    val deleteAnimalUseCase : DeleteAnimalUseCase = DeleteAnimalUseCase()
    val cargarAnimalesUseCase : CargarAnimalesUseCase = CargarAnimalesUseCase()

    val addAnimalUseCase : AddAnimalUseCase = AddAnimalUseCase()

    val cargarAnimalesShuffleUseCase : CargarAnimalesShuffleUseCase = CargarAnimalesShuffleUseCase()

}