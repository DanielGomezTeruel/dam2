package com.example.examenrecycler.data

import com.example.examenrecycler.domain.model.Alumno

object Alumnos {
    private var alumnos = mutableListOf(
        Alumno(1, "Lucía","Álvarez García","1ºDAM", "lucia.alvarez@iesquevedo.es", "18", true, false),
        Alumno(2, "Marco","Castillo Ruiz","2ºASIR", "marco.castillo@iesquevedo.com", "19", true, false),
        Alumno(3, "Sofía","Pérez Martín","1ºSMR", "sofia.perez@iesquevedo.com", "17", false, false),
        Alumno(4, "Daniel","Rodríguez López","2ºDAM", "daniel.rodriguez@iesquevedo.com", "20", true, false),
        Alumno(5, "Elena","Moreno Sánchez","1ºASIR", "elena.moreno@iesquevedo.com", "18", true, false),
        )

    fun dameLista() = alumnos.toList()

    fun deleteAlumno(){
        alumnos.forEach {alumno ->
            if(alumno.selected){
                alumnos.remove(alumno)
            }
        }
    }

    fun addAlumno(alumno:Alumno){
        alumnos.add(alumno)
    }

    fun selectAlumno(alumno: Alumno){
        alumnos.forEach { it.selected = false }
        alumno.selected=true;
    }
}