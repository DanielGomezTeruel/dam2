package com.example.practicarecycler.domain.useCases

import com.example.practicarecycler.data.Animals
import com.example.practicarecycler.domain.model.Animal

class CargarAnimalesUseCase {

    fun dameLista() : List<Animal> = Animals.dameLista()
}