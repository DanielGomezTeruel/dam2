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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
        onChangeText = viewModel::onChangeText,
    )
}


@Composable
fun MainPantallaContent(
    modifier: Modifier = Modifier,
    state: MainState,
    onAddText: () -> Unit,
    onChangeText: (String) -> Unit,
) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(modifier= Modifier.padding(innerPadding)) {
            Row{
                Button(onClick = {
                    onAddText()
                }
                ){
                    Text("Add")
                }
                Column(){
                    TextField(
                        value = state.text,
                        onValueChange = { onChangeText(it)}
                    )
                }
            }
            Row() {
                Greeting(
                    name = state.addedText,
                    modifier = modifier,
                )
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
fun GreetingPreview() {
    CompositoTheme {
        Greeting("Android")
    }
}