package com.example.viewmodelejercicio.domain.useCases

import com.example.viewmodelejercicio.data.Animals


class DameNombreUseCase {

    fun dameNombre () = Animals.dameAnimal().nombre

}