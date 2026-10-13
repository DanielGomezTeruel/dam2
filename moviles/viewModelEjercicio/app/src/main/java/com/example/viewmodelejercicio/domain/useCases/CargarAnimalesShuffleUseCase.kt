package com.example.viewmodelejercicio.domain.useCases

import com.example.viewmodelejercicio.data.Animals
import com.example.viewmodelejercicio.domain.model.Animal

class CargarAnimalesShuffleUseCase {
    fun dameListaShuffle() = Animals.dameListaShuffle()
}