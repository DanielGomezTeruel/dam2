package com.example.viewmodelejercicio.ui.main


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.viewmodelejercicio.domain.model.Animal
import com.example.viewmodelejercicio.domain.useCases.AddAnimalUseCase
import com.example.viewmodelejercicio.domain.useCases.CargarAnimalesShuffleUseCase
import com.example.viewmodelejercicio.domain.useCases.CargarAnimalesUseCase
import com.example.viewmodelejercicio.domain.useCases.DameNombreUseCase
import com.example.viewmodelejercicio.domain.useCases.DeleteAnimalUseCase

class MainViewModel(
    private val dameNombreUseCase : DameNombreUseCase,
    private val cargarAnimalesUseCase: CargarAnimalesUseCase,
    private val deleteAnimalUseCase: DeleteAnimalUseCase,
    private val addAnimalUseCase: AddAnimalUseCase,
    private val cargarAnimalesShuffleUseCase: CargarAnimalesShuffleUseCase,)
    : ViewModel() {

    private val _state = MutableLiveData(MainState("N/A", listOf()))

    val state : LiveData<MainState> = _state

    init{
        handleCargarAnimales();
    }
    fun handleDameNombre(){
        _state.value = _state.value?.copy(nombre = dameNombreUseCase.dameNombre()) ?: MainState(nombre = dameNombreUseCase.dameNombre(), listOf())
    }

    fun handleCargarAnimales(){
        _state.value=_state.value?.copy(animales = cargarAnimalesUseCase.dameLista())
    }

    fun handleDeleteAnimal(animal : Animal){
        deleteAnimalUseCase.deleteAnimal(animal)
        _state.value=_state.value?.copy(animales = cargarAnimalesUseCase.dameLista())
    }

    fun handleAddAnimal(animal : Animal) {
        addAnimalUseCase.addAnimal(animal)
        _state.value=_state.value?.copy(animales = cargarAnimalesUseCase.dameLista())
    }

    fun handleCargarAnimalesShuffle(){
        cargarAnimalesShuffleUseCase.dameListaShuffle()
        handleCargarAnimales()
    }
}

class MainViewModelFactory(
    private val dameNombreUseCase: DameNombreUseCase,
    private val cargarAnimalesUseCase: CargarAnimalesUseCase,
    private val deleteAnimalUseCase: DeleteAnimalUseCase,
    private val addAnimalUseCase: AddAnimalUseCase,
    private val cargarAnimalesShuffleUseCase: CargarAnimalesShuffleUseCase,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(dameNombreUseCase, cargarAnimalesUseCase, deleteAnimalUseCase, addAnimalUseCase, cargarAnimalesShuffleUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}