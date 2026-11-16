package com.example.examencompose.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.room.util.copy
import com.example.examenrecycler.domain.model.Alumno
import com.example.examenrecycler.domain.useCases.AddAlumnoUseCase
import com.example.examenrecycler.domain.useCases.CargarAlumnosUseCase
import com.example.examenrecycler.domain.useCases.DeleteAlumnoUseCase
import com.example.examenrecycler.domain.useCases.SelectAlumnoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.collections.copy

class MainViewModel(
    private val addAlumnoUseCase : AddAlumnoUseCase,
    private val cargarAlumnosUseCase : CargarAlumnosUseCase,
    private val deleteAlumnoUseCase : DeleteAlumnoUseCase,
    private val selectAlumnoUseCase : SelectAlumnoUseCase,
)
    : ViewModel() {

    private val _state = MutableStateFlow(
        MainState(
            alumnos = listOf(),
            labelListaAlumnos = "N/A",)
    )

    val state : StateFlow<MainState> = _state.asStateFlow()

    init{
        handleCargarAlumnos();
    }

    fun handleCargarAlumnos(){
        _state.update{
            it.copy(
                alumnos = cargarAlumnosUseCase.dameLista(),
                labelListaAlumnos = "Lista de alumnos (${cargarAlumnosUseCase.dameLista().size})"
            )
        }
    }

    fun handleDeleteAlumno(){
        deleteAlumnoUseCase.deleteAlumno()
        handleCargarAlumnos();
    }

    fun handleAddAlumno() {

        handleChangeAlumno(_state.value.alumno.copy(id = _state.value.alumnos.size+1))
        addAlumnoUseCase.addAlumno(_state.value.alumno)
        handleCargarAlumnos()
    }

    fun handleSelectAlumno(alumno: Alumno) {
        selectAlumnoUseCase.selectAlumno(alumno)
        handleCargarAlumnos();
    }

    fun handleChangeAlumno(alumno: Alumno) {
            _state.update {
                it.copy(
                    alumno = alumno
                )
            }
    }


}

class MainViewModelFactory(
    private val addAlumnoUseCase : AddAlumnoUseCase,
    private val cargarAlumnosUseCase : CargarAlumnosUseCase,
    private val deleteAlumnoUseCase : DeleteAlumnoUseCase,
    private val selectAlumnoUseCase : SelectAlumnoUseCase,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(
                addAlumnoUseCase,
                cargarAlumnosUseCase,
                deleteAlumnoUseCase,
                selectAlumnoUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}