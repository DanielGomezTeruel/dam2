package com.example.practicarecycler.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.size
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practicarecycler.R
import com.example.practicarecycler.databinding.ActivityMainBinding
import com.example.practicarecycler.di.AppModule
import com.example.practicarecycler.domain.model.Animal

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels { MainViewModelFactory(
        AppModule.dameNombreUseCase,
        AppModule.cargarAnimalesUseCase,
        AppModule.deleteAnimalUseCase,
        AppModule.addAnimalUseCase,
        AppModule.cargarAnimalesShuffleUseCase
    ) }

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private lateinit var adapter : AnimalAdapter

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

        adapter = AnimalAdapter({mainViewModel.handleDeleteAnimal(it)})
        binding.listado.adapter = adapter


    }

    private fun setupEventos() {
        binding.button.setOnClickListener {
            mainViewModel.handleDameNombre()
        }

        binding.buttonAdd.setOnClickListener {
            val animal = Animal(binding.listado.size+1,
                binding.textInput.editText?.text.toString(), "gata", "blanca")
            mainViewModel.handleAddAnimal(animal)
        }

        binding.buttonShuffle.setOnClickListener {
            mainViewModel.handleCargarAnimalesShuffle()
        }
    }

    private fun observarEstado() {
        mainViewModel.state.observe(this,{state ->
            state?.nombre.let{
                binding.textView.text = it
            }

            state?.animales.let{
                adapter.submitList(it)
            }
            state?.error?.let{
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })
    }
}

