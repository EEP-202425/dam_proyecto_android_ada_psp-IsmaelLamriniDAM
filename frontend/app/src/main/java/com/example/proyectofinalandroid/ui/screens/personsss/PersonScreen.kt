package com.example.proyectofinalandroid.ui.screens.personsss

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyectofinalandroid.R
import com.example.proyectofinalandroid.network.Person
import com.example.proyectofinalandroid.ui.screens.viewmodel.PersonUiState
import com.example.proyectofinalandroid.ui.screens.viewmodel.PersonViewModel
import kotlinx.coroutines.delay

@Composable
fun PersonScreen (
    vm: PersonViewModel = viewModel(),
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onCreateUserClick:() -> Unit
){

    val personUiState by remember { derivedStateOf { vm.personUiState } }

    LaunchedEffect(personUiState) {
        if (personUiState is PersonUiState.RegisterSuccess) {
            delay(1500)
            onCreateUserClick()
        }
        if(personUiState is PersonUiState.Error){
            delay(1500)
            vm.resetToRegister()
        }
    }


    when(personUiState) {
        is PersonUiState.Register -> RegisterScreen(
            vm = vm,
            modifier = Modifier.fillMaxSize().padding(16.dp),
            onCreateUserClick = { person ->
                vm.createdOwner(person)
            }
            )
        is PersonUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is PersonUiState.RegisterSuccess -> RegisterSuccessful(modifier = modifier.fillMaxSize())
        is PersonUiState.Update -> UpdateSuccessful(modifier = modifier.fillMaxSize())
        is PersonUiState.Error -> {
            ErrorScreen(
                message = (personUiState as PersonUiState.Error).message,
                modifier = modifier.fillMaxSize()
            )

        }

    }
}

@Composable
fun LoadingScreen(modifier: Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun RegisterSuccessful(modifier: Modifier) {
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = modifier.size(400.dp),
            painter = painterResource(R.drawable.ic_check_circle),
            contentDescription = stringResource(R.string.register)
        )
    }
}

@Composable
fun UpdateSuccessful(modifier: Modifier) {
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = modifier.size(100.dp),
            painter = painterResource(R.drawable.ic_check_circle),
            contentDescription = stringResource(R.string.update)
        )
    }
}

@Composable
fun ErrorScreen(
    message: String?,
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error),
            contentDescription = "Error"
        )
        if(message != null){
            Text(text = message, modifier = Modifier.padding(16.dp))
        }
    }
}

@Composable
fun RegisterScreen(
    vm: PersonViewModel = viewModel(),
    modifier: Modifier,
    onCreateUserClick: (Person) -> Unit
) {

    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        var nameUser by remember { mutableStateOf("") }
        var lastNameUser by remember { mutableStateOf("") }
        var mail by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        Text(text = "REGISTRO:")


        OutlinedTextField(
            value = nameUser,
            onValueChange = {nameUser = it},
            label = { Text("USERNAME") }
        )

        OutlinedTextField(
            value = lastNameUser,
            onValueChange = {lastNameUser = it},
            label = { Text("LASTNAME") }
        )

        OutlinedTextField(
            value = mail,
            onValueChange = {mail = it},
            label = { Text("MAIL") }
        )

        OutlinedTextField(
            value = password,
            onValueChange = {password = it},
            label = { Text("PASSWORD") }
        )

        Spacer(Modifier.height(10.dp))

        Button(
            onClick = {
                val newPerson = Person(
                    id = 0,
                    name = nameUser,
                    lastName = lastNameUser,
                    mail = mail,
                    password = password
                )

                if(nameUser.isBlank() || lastNameUser.isBlank() || mail.isBlank() || password.isBlank()) {
                    vm.statusErrorRegister("CAMPOS IMCOMPLETOS")
                    return@Button
                }

                onCreateUserClick(newPerson)
            }
        ) {
            Text("REGISTRARSE")
        }
    }
}
