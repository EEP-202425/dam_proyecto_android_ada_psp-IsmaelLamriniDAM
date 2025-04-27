package com.example.proyectofinalandroid

import android.content.Context
import android.os.Bundle
import android.widget.SimpleCursorAdapter
import android.widget.Spinner
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.proyectofinalandroid.ui.theme.ProyectoFinalAndroidTheme
import kotlin.coroutines.coroutineContext


@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Retrofit

        enableEdgeToEdge()
        setContent {
            ProyectoFinalAndroidTheme {
                Scaffold(
                    topBar = {
                            CenterAlignedTopAppBar(title = {Text("Camiones")})
                             },
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    pintarTexto(
                        modifier = Modifier.padding(innerPadding),
                        "Probando"
                    )
                    Column {
                        listaDesplegable(
                            modifier = Modifier
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun pintarTexto(modifier: Modifier, texto : String) {
    Text(
        texto,
        modifier = modifier
    )
    modifier.fillMaxSize()
}

@Composable
fun listaDesplegable(modifier: Modifier) {
    SimpleCursorAdapter(
        context = Context,

    )


}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ProyectoFinalAndroidTheme {
        pintarTexto(modifier = Modifier, "Probando")
    }
}