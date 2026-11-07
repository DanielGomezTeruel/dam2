package com.example.composito

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composito.ui.theme.CompositoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CompositoTheme {
                MainPantalla()
            }
        }
    }
}

@Composable
fun MainPantalla(modifier: Modifier = Modifier,
                 viewModel : MainViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    MainPantallaContent(
        modifier = modifier,
        state = state,
        onAddText = viewModel::onAddText,
        onChangeName = viewModel::onChangeName,
        onChangeApellidos = viewModel::onChangeApellidos,
        onChangeTlf = viewModel::onChangeTlf,
        onChangeEmail = viewModel::onChangeEmail,

        )
}


@Composable
fun MainPantallaContent(
    modifier: Modifier = Modifier,
    state: MainState,
    onAddText: () -> Unit,
    onChangeName: (String) -> Unit,
    onChangeApellidos: (String) -> Unit,
    onChangeTlf: (String) -> Unit,
    onChangeEmail: (String) -> Unit,
) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(modifier= Modifier.padding(innerPadding)) {
            Row{
                Column(modifier = Modifier.weight(0.4F).padding(horizontal = 12.dp)){
                    Row() {
                        Text("Nombre")
                    }
                    Row() {
                        TextField(
                            value = state.textInputNombre,
                            onValueChange = { onChangeName(it) }
                        )
                    }
                }
                Column(modifier = Modifier.weight(0.6F).padding(horizontal = 12.dp)){
                    Row() {
                        Text("Apellidos")
                    }
                    Row() {
                        TextField(
                            value = state.textInputApellidos,
                            onValueChange = { onChangeApellidos(it) }
                        )
                    }
                }
            }
            Row{
                Column(modifier = Modifier.weight(0.4F).padding(horizontal = 12.dp)){
                    Row() {
                        Text("Teléfono")
                    }
                    Row() {
                        TextField(
                            value = state.textInputTlf,
                            onValueChange = { onChangeTlf(it) }
                        )
                    }
                }
                Column(modifier = Modifier.weight(0.6F).padding(horizontal = 12.dp)){
                    Row() {
                        Text("Email")
                    }
                    Row() {
                        TextField(
                            value = state.textInputEmail,
                            onValueChange = { onChangeEmail(it) }
                        )
                    }
                }
            }
            Column (
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                Row(
                    modifier = Modifier.padding(vertical = 16.dp)
                ) {
                    Button(
                        onClick = {
                            onAddText()
                        }
                    ) {
                        Text("Add")
                    }
                }
                Row() {
                    Greeting(
                        name = state.textNombre,
                        modifier = modifier,
                    )
                }
                Row() {
                    Text("Tus apellidos son: ${state.textApellidos}")
                }
                Row() {
                    Text("Tu teléfono es: ${state.textTlf}")

                }
                Row() {
                    Text("Tus email es: ${state.textEmail}")
                }
                Row() {
                    Text("Has clickado en el botón ${state.contadorAdd} veces")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun MainPantallaPreview() {
    CompositoTheme {
        MainPantallaContent(
            state = MainState(),
            onAddText = {},
            onChangeName = {},
            onChangeApellidos = {},
            onChangeTlf = {},
            onChangeEmail = {},
            )
    }
}