package com.example.practicaparacompose1.data

import com.example.practicaparacompose1.domain.model.Alumno

object Alumnos {
    private var alumnos = mutableListOf(Alumno(
            id = 1,
            nombre = "Lucía",
            apellidos = "Álvarez García",
            curso = "1ºDAM",
            email = "lucia.alvarez@iesquevedo.es",
            edad = "18",
            activo = true,
            selected = false,
            notas = mutableMapOf(
                Pair("Mates", 8),
                Pair("Dibujo tecnico", 6),
                Pair("Lengua", 3),
                Pair("Historia", 4)
            ),
        ), Alumno(
            id = 2,
            nombre = "Marco",
            apellidos = "Castillo Ruiz",
            curso = "2ºASIR",
            email = "marco.castillo@iesquevedo.com",
            edad = "19",
            activo = true,
            selected = false,
            mutableMapOf(
                Pair("Mates", 8),
                Pair("Dibujo tecnico", 6),
                Pair("Lengua", 3),
                Pair("Historia", 4)
            ),
        ), Alumno(
            id = 3,
            nombre = "Sofía",
            apellidos = "Pérez Martín",
            curso = "1ºSMR",
            email = "sofia.perez@iesquevedo.com",
            edad = "17",
            activo = false,
            selected = false,
            mutableMapOf(
                Pair("Mates", 8),
                Pair("Dibujo tecnico", 6),
                Pair("Lengua", 3),
                Pair("Historia", 4)
            ),
        ), Alumno(
            id = 4,
            nombre = "Daniel",
            apellidos = "Rodríguez López",
            curso = "2ºDAM",
            email = "daniel.rodriguez@iesquevedo.com",
            edad = "20",
            activo = true,
            selected = false,
            mutableMapOf(
                Pair("Mates", 8),
                Pair("Dibujo tecnico", 6),
                Pair("Lengua", 3),
                Pair("Historia", 4)
            ),
        ), Alumno(
            id = 5,
            nombre = "Elena",
            apellidos = "Moreno Sánchez",
            curso = "1ºASIR",
            email = "elena.moreno@iesquevedo.com",
            edad = "18",
            activo = true,
            selected = false,
            mutableMapOf(
                Pair("Mates", 8),
                Pair("Dibujo tecnico", 6),
                Pair("Lengua", 3),
                Pair("Historia", 4)
            ),
        ),)

    fun dameLista() = alumnos.toList()

    fun deleteAlumno(){
        var selectedAlumno : Alumno? = null
        alumnos.forEach {alumno ->
            if(alumno.selected){
                selectedAlumno=alumno
            }
        }
        if(selectedAlumno!=null){
            alumnos.remove(selectedAlumno)
        }
    }

    fun addAlumno(alumno:Alumno){
        alumnos.add(alumno)
    }

    fun selectAlumno(alumno: Alumno) {
        alumnos = alumnos.map {
            it.copy(selected = it.id == alumno.id)
        }.toMutableList()
    }
}