package com.example.proyectofinalandroid.ui.screens.inicioApp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ScreenButtonRegisterAndLogin(
    modifier: Modifier,
    onLoginClick:() -> Unit,
    onRegisterClick: () -> Unit
) {
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Button(
            onClick = onLoginClick
        ) {
            Text(text = "Iniciar Sesión")
        }

        Spacer(Modifier.height(10.dp))

        Button(
            onClick = onRegisterClick
        ) {
            Text(text = "Registrarse")
        }
    }
}