package com.example.proyectofinalandroid.ui.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(
    modifier: Modifier,
    onLoginClick: () -> Unit
) {
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        var nameUser by remember { mutableStateOf("") }
        var passwordUser by remember { mutableStateOf("") }


        Text(text = "INICIAR SESIÓN")

        OutlinedTextField(
            value = nameUser,
            onValueChange = {nameUser = it},
            label = {Text("USERNAME:")}
        )

        OutlinedTextField(
            value = passwordUser,
            onValueChange = {passwordUser = it},
            label = {Text("PASSWORD:")}
        )

        Spacer(Modifier.height(10.dp))

        Button(
            onClick = onLoginClick
        ) {
            Text(text = "ACCEDER")
        }
    }
}