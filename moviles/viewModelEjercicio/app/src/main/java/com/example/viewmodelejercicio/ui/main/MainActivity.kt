package com.example.viewmodelejercicio.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.viewmodelejercicio.R
import com.example.viewmodelejercicio.databinding.ActivityMainBinding
import com.example.viewmodelejercicio.di.AppModule

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels { MainViewModelFactory(AppModule.dameNombreUseCase) };

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) };

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        observarEstado()

        setupEventos()

    }

    private fun setupEventos() {
        binding.button.setOnClickListener {
            mainViewModel.handleDameNombre()
        }
    }

    private fun observarEstado() {
        mainViewModel.state.observe(this,{
            it?.nombre.let{
                binding.textView.text = it
            }
            it?.error.let{ error->
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
            }
        })
    }
}

