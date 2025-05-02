package com.example.proyectofinalandroid.ui.screens.personsss

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.proyectofinalandroid.R
import com.example.proyectofinalandroid.ui.screens.viewmodel.PersonUiState

@Composable
fun PersonScreen (
    personUiState: PersonUiState,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
){
    when(personUiState) {
        is PersonUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is PersonUiState.Register -> RegisterSuccessful(modifier = modifier.fillMaxSize())
        is PersonUiState.Update -> UpdateSuccessful(modifier = modifier.fillMaxSize())
        is PersonUiState.Error -> ErrorScreen(
            personUiState.message,
            modifier = modifier.fillMaxSize()
        )
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
            modifier = modifier.size(100.dp),
            painter = painterResource(R.drawable.ic_check_circle),
            contentDescription = stringResource(R.string.login)
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
