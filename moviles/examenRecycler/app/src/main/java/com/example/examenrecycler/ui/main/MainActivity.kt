package com.example.examenrecycler.ui.main

import android.os.Bundle
import android.widget.Toast

import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.size
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.examenrecycler.R
import com.example.examenrecycler.databinding.ActivityMainBinding
import com.example.examenrecycler.di.AppModule
import com.example.examenrecycler.domain.model.Alumno


class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            AppModule.addAlumnoUseCase,
            AppModule.cargarAlumnosUseCase,
            AppModule.deleteAlumnoUseCase,
            AppModule.selectAlumnoUseCase,
        )
    }

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private lateinit var adapter : AlumnoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        configRecycler()

        observarEstado()

        setupEventos()

    }

    private fun configRecycler() {
        binding.listado.layoutManager = LinearLayoutManager(this)

        adapter = AlumnoAdapter({mainViewModel.handleSelectAlumno(it)})
        binding.listado.adapter = adapter


    }

    private fun setupEventos() {

        binding.buttonAdd.setOnClickListener {
            val alumno = Alumno(
                binding.listado.size + 1,
                binding.textInputNombre.editText?.text.toString(),
                binding.textInputApellidos.editText?.text.toString(),
                binding.textInputCurso.editText?.text.toString(),
                binding.textInputEmail.editText?.text.toString(),
                binding.textInputEdad.editText?.text.toString(),
                binding.alumnoActivoCheckBox.isChecked,
                false,
            )
            mainViewModel.handleAddAlumno(alumno)
        }

        binding.buttonDelete.setOnClickListener {
            mainViewModel.handleDeleteAlumno()
        }

    }

    private fun observarEstado() {
        mainViewModel.state.observe(this,{state ->
            state?.labelListaAlumnos.let{
                binding.labelListaAlumnos.text = it
            }

            state?.alumnos.let{
                adapter.submitList(it)
            }
            state?.error?.let{
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })
    }
}

