package com.example.examenrecycler.ui.main


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.examenrecycler.domain.model.Alumno
import com.example.examenrecycler.domain.useCases.AddAlumnoUseCase
import com.example.examenrecycler.domain.useCases.CargarAlumnosUseCase
import com.example.examenrecycler.domain.useCases.DeleteAlumnoUseCase
import com.example.examenrecycler.domain.useCases.SelectAlumnoUseCase


class MainViewModel(
    private val addAlumnoUseCase : AddAlumnoUseCase,
    private val cargarAlumnosUseCase : CargarAlumnosUseCase,
    private val deleteAlumnoUseCase : DeleteAlumnoUseCase,
    private val selectAlumnoUseCase : SelectAlumnoUseCase,
)
    : ViewModel() {

    private val _state = MutableLiveData(MainState("N/A", listOf()))

    val state : LiveData<MainState> = _state

    init{
        handleCargarAlumnos();
    }

    fun handleCargarAlumnos(){
        _state.value=_state.value?.copy(alumnos = cargarAlumnosUseCase.dameLista(), labelListaAlumnos = "Lista de alumnos (${cargarAlumnosUseCase.dameLista().size})")
    }

    fun handleDeleteAlumno(){
        deleteAlumnoUseCase.deleteAlumno()
        handleCargarAlumnos();
    }

    fun handleAddAlumno(alumno : Alumno) {
        addAlumnoUseCase.addAlumno(alumno)
        handleCargarAlumnos();
    }

    fun handleSelectAlumno(alumno: Alumno) {
        selectAlumnoUseCase.selectAlumno(alumno)
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