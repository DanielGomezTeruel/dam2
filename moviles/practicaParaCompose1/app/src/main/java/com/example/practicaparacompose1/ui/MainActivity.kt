package com.example.practicaparacompose1.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.practicaparacompose1.R
import com.example.practicaparacompose1.di.AppModule
import com.example.practicaparacompose1.domain.model.Alumno
import com.example.practicaparacompose1.ui.theme.AppTheme
import com.example.practicaparacompose1.ui.theme.darkRedContainerDark
import com.example.practicaparacompose1.ui.theme.errorDark
import com.example.practicaparacompose1.ui.theme.errorLight
import com.example.practicaparacompose1.ui.theme.onDarkRedContainerDark
import com.example.practicaparacompose1.ui.theme.onErrorDark
import com.example.practicaparacompose1.ui.theme.onErrorLight
import com.example.practicaparacompose1.ui.theme.onPrimaryLight
import com.example.practicaparacompose1.ui.theme.onSurfaceVariantLight
import com.example.practicaparacompose1.ui.theme.primaryLight
import com.example.practicaparacompose1.ui.theme.secondaryDark
import com.example.practicaparacompose1.ui.theme.secondaryLight


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                MainPantalla()


            }
        }
    }
}

@Composable
fun MainPantalla(
                 viewModel : MainViewModel = viewModel(
                     factory = MainViewModelFactory(
                         AppModule.addAlumnoUseCase,
                         AppModule.cargarAlumnosUseCase,
                         AppModule.deleteAlumnoUseCase,
                         AppModule.selectAlumnoUseCase,
                     ),
                 )){
    val state by viewModel.state.collectAsStateWithLifecycle()

    MainPantallaContent(
        state = state,
        onDeleteAlumno = viewModel::handleDeleteAlumno,
        onSelectAlumno = viewModel::handleSelectAlumno,
        onChangeAlumno = viewModel::handleChangeAlumno,
        onAddAlumno = viewModel::handleAddAlumno,
        )

}

@Composable
fun MainPantallaContent(
    state: MainState,
    onDeleteAlumno: () -> Unit,
    onSelectAlumno: (Alumno) -> Unit,
    onChangeAlumno: (Alumno) -> Unit,
    onAddAlumno: () -> Unit,


    ){
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Row(
                modifier = Modifier
                    .weight(0.1F)
                    .fillMaxWidth()
                    .background(primaryLight),
                verticalAlignment = Alignment.CenterVertically,

                ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_school_24),
                    contentDescription = "GestionAlumnosIcon",
                    modifier = Modifier.size(48.dp).weight(0.25F),
                    tint = Color.White,

                )
                Text(
                    modifier = Modifier.weight(0.75F),
                    text = "Gestión de alumnos",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                )
            }
            Row(
                modifier = Modifier.weight(0.45F),
                ) {
                Column(modifier = Modifier.padding(16.dp)){
                    Text(
                        text = "Lista de alumnos (${state.alumnos.size})",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        )
                    RecyclerAlumnos(
                        state = state,
                        onSelectAlumno = onSelectAlumno,
                    )
                }
            }
            //Añadir form
            Row(
                modifier = Modifier.weight(0.35F),
                ) {
                Formulario(
                    state = state,
                    onChangeAlumno = onChangeAlumno,
                    onAddAlumno = onAddAlumno,

                )

            }
            //Borrar
            Row(
                modifier = Modifier.weight(0.1F),
                ) {
                Button(
                    onClick = onDeleteAlumno,
                    enabled = state.selected,
                    modifier = Modifier.fillMaxSize(),
                    colors = ButtonColors(
                        containerColor = darkRedContainerDark,
                        contentColor = onDarkRedContainerDark,
                        disabledContentColor = onErrorLight,
                        disabledContainerColor = onSurfaceVariantLight
                    ),
                ) {

                    Text("Borrar alummo seleccionado")
                }
            }
        }
    }
}

@Composable
fun Formulario(
    state: MainState,
    onChangeAlumno: (Alumno) -> Unit,
    onAddAlumno: () -> Unit,
    ) {
    Column() {
        Row (Modifier.weight(0.3F)){
            Column(modifier = Modifier.weight(0.5F).padding(horizontal = 12.dp)) {
                Row() {
                    Text("Nombre")
                }
                Row() {
                    TextField(
                        value = state.alumno.nombre,
                        onValueChange = { nombre: String -> onChangeAlumno(state.alumno.copy(nombre = nombre)) },
                        textStyle = TextStyle(fontSize = 16.sp),
                    )
                }
            }
            Column(modifier = Modifier.weight(0.5F).padding(horizontal = 12.dp)) {
                Row() {
                    Text("Apellidos")
                }
                Row() {
                    TextField(
                        value = state.alumno.apellidos,
                        onValueChange = { apellidos: String ->
                            onChangeAlumno(
                                state.alumno.copy(
                                    apellidos = apellidos
                                )
                            )
                        },
                        textStyle = TextStyle(fontSize = 16.sp),

                    )
                }
            }
        }
        Row (Modifier.weight(0.3F)){
            Column(modifier = Modifier.weight(0.5F).padding(horizontal = 12.dp)) {
                Row() {
                    Text("Curso")
                }
                Row() {
                    TextField(
                        value = state.alumno.curso,
                        onValueChange = { curso: String -> onChangeAlumno(state.alumno.copy(curso = curso)) },
                        textStyle = TextStyle(fontSize = 16.sp),

                    )
                }
            }
            Column(modifier = Modifier.weight(0.5F).padding(horizontal = 12.dp)) {
                Row() {
                    Text("Edad")
                }
                Row() {
                    TextField(
                        value = state.alumno.edad,
                        onValueChange = { edad: String -> onChangeAlumno(state.alumno.copy(edad = edad)) },
                        textStyle = TextStyle(fontSize = 16.sp),

                    )
                }
            }

        }
        Row(Modifier.weight(0.3F)) {
            Column(modifier = Modifier.weight(0.5F).padding(horizontal = 12.dp)) {
                Row() {
                    Text("Email")
                }
                Row() {
                    TextField(
                        value = state.alumno.email,
                        onValueChange = { email: String -> onChangeAlumno(state.alumno.copy(email = email)) }
                    )
                }
            }
            Column(
                modifier = Modifier.weight(0.5F),
                ) {
                Text("Activo")
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Checkbox(
                        checked = true,
                        onCheckedChange = { checked: Boolean -> onChangeAlumno(state.alumno.copy(activo = checked)) },

                    )
                    Text(
                        text = "Alumno activo",
                        modifier= Modifier.weight(0.3F),
                        fontSize = 16.sp,


                    )
                }
            }

        }
        Column(
            modifier = Modifier.weight(0.3F),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                Button(
                    onClick = {
                        onAddAlumno()
                    }
                ) {
                    Text("Add")
                }
            }
        }
    }
}

@Composable
fun RecyclerAlumnos(
    state: MainState,
    onSelectAlumno: (Alumno) -> Unit,

    ) {
    LazyColumn(
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        items(
            items = state.alumnos,
            key = { alumno -> alumno.id }
        ) { alumno ->

            ItemAlumno(alumno, onSelectAlumno)
        }
    }
}

@Composable
fun ItemAlumno(
    alumno: Alumno,
    onSelectAlumno: (Alumno) -> Unit,
) {
    val selected : Boolean = alumno.selected
    Card(
        modifier= Modifier.fillMaxSize().clickable{
        onSelectAlumno(alumno)
        },

        colors = CardColors(
            containerColor = if(selected){secondaryLight }else{primaryLight},
            contentColor = onPrimaryLight,
            disabledContentColor = primaryLight,
            disabledContainerColor = primaryLight,
        ),


    ){
        Column() {
            Row() {
                Button(
                    onClick = { Unit },
                    modifier = Modifier.size(48.dp, 48.dp)

                ) {
                    Text(
                        text = alumno.nombre[0].toString(),
                        textAlign = TextAlign.Center,
                    )
                }
                Column() {
                    Text(
                        text = alumno.nombre + " " + alumno.apellidos,
                        fontWeight = FontWeight.Bold,
                    )
                    Row() {
                        Text(
                            text = alumno.curso + " · ",

                            )
                        Text(
                            text = alumno.edad + " años",

                            )
                    }

                    Text(
                        alumno.email,

                        )
                }
                Column(modifier = Modifier.fillMaxHeight()) {
                    Card(
                        colors = CardColors(
                            containerColor = if (alumno.activo) {
                                Color(0xFF9AFF9A)
                            } else {
                                Color(0xFFFF7878)
                            },
                            contentColor = if (alumno.activo) {
                                Color(0xFF005204)
                            } else {
                                Color(0xFF700000)
                            },
                            disabledContentColor = Color.Blue,
                            disabledContainerColor = Color.Blue,

                            ),

                        ) {
                        Text(
                            text = if (alumno.activo) {
                                "Activo"
                            } else {
                                "Inactivo"
                            },
                        )
                    }
                    }

                    Text(
                        text = if (alumno.selected) {
                            "Seleccionado"
                        } else {
                            ""
                        },
                    )


            }
            // Desplegamos las notas
            if (alumno.selected) {
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                alumno.notas.forEach { (asignatura, nota) ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = asignatura)
                        Text(text = nota.toString())
                    }
                }
            }
        }

    }}


@Preview(showBackground = true)
@Composable
fun MainPantallaPreview() {
    AppTheme {
        MainPantallaContent (
            state = MainState(
                alumnos = listOf(
                    Alumno(
                        1,
                        "Lucía",
                        "Álvarez García",
                        "1ºDAM",
                        "lucia.alvarez@iesquevedo.es",
                        "18",
                        true,
                        false,
                        emptyMap(),
                    ),
                    Alumno(
                        2,
                        "Marco",
                        "Castillo Ruiz",
                        "2ºASIR",
                        "marco.castillo@iesquevedo.com",
                        "19",
                        true,
                        false,
                        emptyMap(),

                        ),
                    Alumno(
                        3,
                        "Sofía",
                        "Pérez Martín",
                        "1ºSMR",
                        "sofia.perez@iesquevedo.com",
                        "17",
                        false,
                        false,
                        emptyMap(),

                        ),
                    Alumno(
                        4,
                        "Daniel",
                        "Rodríguez López",
                        "2ºDAM",
                        "daniel.rodriguez@iesquevedo.com",
                        "20",
                        true,
                        false,
                        emptyMap(),

                        ),
                    Alumno(
                        5,
                        "Elena",
                        "Moreno Sánchez",
                        "1ºASIR",
                        "elena.moreno@iesquevedo.com",
                        "18",
                        true,
                        false,
                        emptyMap(),

                        ),
                ),
                labelListaAlumnos = "Lista alumnos",

                selected = false,
            ),
            onDeleteAlumno = { },
            onSelectAlumno = { },
            onAddAlumno = { },
            onChangeAlumno = { },
            )
    }
}