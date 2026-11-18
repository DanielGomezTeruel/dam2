package com.example.practicaparacompose1.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.practicaparacompose1.domain.model.Alumno
import com.example.practicaparacompose1.domain.useCases.AddAlumnoUseCase
import com.example.practicaparacompose1.domain.useCases.CargarAlumnosUseCase
import com.example.practicaparacompose1.domain.useCases.DeleteAlumnoUseCase
import com.example.practicaparacompose1.domain.useCases.SelectAlumnoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.collections.listOf


class MainViewModel(
    private val addAlumnoUseCase: AddAlumnoUseCase,
    private val cargarAlumnosUseCase: CargarAlumnosUseCase,
    private val deleteAlumnoUseCase: DeleteAlumnoUseCase,
    private val selectAlumnoUseCase: SelectAlumnoUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(
        MainState(
            alumnos = listOf(),
            labelListaAlumnos = "N/A",
            selected = false,
        )
    )

    val state: StateFlow<MainState> = _state.asStateFlow()

    init {
        handleCargarAlumnos()
    }

    fun handleCargarAlumnos() {
        _state.update {
            it.copy(
                alumnos = cargarAlumnosUseCase.dameLista(),
                labelListaAlumnos = "Lista de alumnos (${cargarAlumnosUseCase.dameLista().size})"
            )
        }
    }

    fun handleDeleteAlumno() {
        deleteAlumnoUseCase.deleteAlumno()
        handleCargarAlumnos()
    }

    fun handleAddAlumno() {

        handleChangeAlumno(_state.value.alumno.copy(id = _state.value.alumnos.size + 1))
        addAlumnoUseCase.addAlumno(_state.value.alumno)
        handleCargarAlumnos()
    }

    fun handleSelectAlumno(alumno: Alumno) {
        selectAlumnoUseCase.selectAlumno(alumno)
        _state.update {
            it.copy(
                alumnos = cargarAlumnosUseCase.dameLista(),
                labelListaAlumnos = "Lista de alumnos (${cargarAlumnosUseCase.dameLista().size})",
                selected = true,
            )
        }
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
    private val addAlumnoUseCase: AddAlumnoUseCase,
    private val cargarAlumnosUseCase: CargarAlumnosUseCase,
    private val deleteAlumnoUseCase: DeleteAlumnoUseCase,
    private val selectAlumnoUseCase: SelectAlumnoUseCase,
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