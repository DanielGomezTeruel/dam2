package com.example.composito

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel : ViewModel() {

    fun onAddText(){
        _state.update{it.copy(
            textNombre = it.textInputNombre,
            textApellidos = it.textInputApellidos,
            textTlf = it.textInputTlf,
            textEmail = it.textInputEmail,
            contadorAdd = it.contadorAdd+1,
        )
        }


    }

    fun onChangeName(nombre : String){
        _state.update{it.copy(textInputNombre = nombre)}
    }
    fun onChangeApellidos(apellidos : String){

        _state.update{it.copy(textInputApellidos = apellidos)}
    }

    fun onChangeTlf(tlf : String){
        _state.update{it.copy(textInputTlf = tlf)}
    }

    fun onChangeEmail(email : String){
        _state.update{it.copy(textInputEmail = email)}
    }


    private val _state = MutableStateFlow(MainState())

    val state: StateFlow<MainState> = _state.asStateFlow()


    class MainViewModelFactory(

    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST") return MainViewModel(
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
