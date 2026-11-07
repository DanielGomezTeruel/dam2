package com.example.practicarecycler.domain.useCases

import com.example.practicarecycler.data.Animals


class DameNombreUseCase {

    fun dameNombre () = Animals.dameAnimal().nombre

}