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
import androidx.compose.ui.unit.dp
import com.example.proyectofinalandroid.ui.navegation.TruckApp
import com.example.proyectofinalandroid.ui.screens.login.LoginScreen
import com.example.proyectofinalandroid.ui.theme.ProyectoFinalAndroidTheme
import kotlin.coroutines.coroutineContext


@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        enableEdgeToEdge()
        setContent {
            ProyectoFinalAndroidTheme {
                TruckApp()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ProyectoFinalAndroidTheme {
        LoginScreen(
            modifier = Modifier.fillMaxSize().padding(16.dp),
        ) { }
    }
}