package com.example.viewmodelejercicio.domain.useCases

import com.example.viewmodelejercicio.data.Animals
import com.example.viewmodelejercicio.domain.model.Animal

class DeleteAnimalUseCase {

    fun deleteAnimal(animal: Animal){
        Animals.deleteAnimal(animal)
    }
}