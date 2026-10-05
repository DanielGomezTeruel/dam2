package com.example.viewmodelejercicio.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.viewmodelejercicio.domain.useCases.DameNombreUseCase

class MainViewModel(val dameNombreUseCase : DameNombreUseCase) : ViewModel() {

    private val _state = MutableLiveData(MainState())

    val state : LiveData<MainState> = _state


    fun handleDameNombre(): Unit{
        _state.value = _state.value?.copy(nombre = dameNombreUseCase.dameNombre()) ?: MainState(nombre = dameNombreUseCase.dameNombre())
    }
}

class MainViewModelFactory(private val dameNombreUseCase: DameNombreUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(dameNombreUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}