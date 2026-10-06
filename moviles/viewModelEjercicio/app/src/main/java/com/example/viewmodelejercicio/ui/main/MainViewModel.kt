package com.example.viewmodelejercicio.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.viewmodelejercicio.di.AppModule.cargarAnimalesUseCase
import com.example.viewmodelejercicio.domain.useCases.CargarAnimalesUseCase
import com.example.viewmodelejercicio.domain.useCases.DameNombreUseCase

class MainViewModel(val dameNombreUseCase : DameNombreUseCase, val cargarAnimalesUseCase: CargarAnimalesUseCase) : ViewModel() {

    private val _state = MutableLiveData(MainState("N/A", listOf()))

    val state : LiveData<MainState> = _state


    fun handleDameNombre(): Unit{
        _state.value = _state.value?.copy(nombre = dameNombreUseCase.dameNombre()) ?: MainState(nombre = dameNombreUseCase.dameNombre(), listOf())
    }

    fun handleCargarAnimales(): Unit{
        _state.value
    }
}

class MainViewModelFactory(
    private val dameNombreUseCase: DameNombreUseCase,
    cargarAnimalesUseCase: CargarAnimalesUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(dameNombreUseCase, cargarAnimalesUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}