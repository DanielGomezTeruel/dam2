package com.example.practicarecycler.domain.useCases

import com.example.practicarecycler.data.Animals
import com.example.practicarecycler.domain.model.Animal

class DeleteAnimalUseCase {

    fun deleteAnimal(animal: Animal){
        Animals.deleteAnimal(animal)
    }
}