package com.example.proyectofinalandroid.ui.screens.trucksss

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.example.proyectofinalandroid.R
import com.example.proyectofinalandroid.network.Brand
import com.example.proyectofinalandroid.network.Model
import com.example.proyectofinalandroid.network.Person
import com.example.proyectofinalandroid.network.Truck
import com.example.proyectofinalandroid.ui.screens.viewmodel.TruckUiState
import com.example.proyectofinalandroid.ui.screens.viewmodel.TruckViewModel
import kotlinx.coroutines.delay


@Composable
fun ItemScreen(
    vm: TruckViewModel = viewModel(),
    onCreated: () -> Unit,
    modifier: Modifier = Modifier
) {

    val uiState = vm.trucksUiState

    when (uiState) {
        is TruckUiState.Delete,
        is TruckUiState.Loading,
        is TruckUiState.Success,
        is TruckUiState.Error,
        is TruckUiState.Details -> {
            TruckCreateScreen(
                onSubmit =  { newTruck ->
                    vm.createdTruck(newTruck)
                },
                modifier = modifier
            )

        }

        is TruckUiState.Created -> {
            CreatedScreenTruck(
                modifier = Modifier
            )
            LaunchedEffect(uiState.truck.id) {
                delay(1500)
                onCreated()
            }
        }

    }
}

@Composable
fun TruckCreateScreen(
    onSubmit: (Truck) -> Unit,
    modifier: Modifier = Modifier
) {
    var brandName by remember { mutableStateOf("") }
    var modelName by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var ownerName by remember { mutableStateOf("") }

    Column(
        modifier = modifier.fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Vender nuevo camión", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(
            value = brandName, onValueChange = { brandName = it },
            label = { Text("Marca") }
        )

        OutlinedTextField(
            value = modelName, onValueChange = { modelName = it },
            label = { Text("Modelo") }
        )

        OutlinedTextField(
            value = price, onValueChange = { price = it },
            label = { Text("Precio") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        OutlinedTextField(
            value = ownerName, onValueChange = { ownerName = it },
            label = { Text("Nombre de Usuario") },
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                val newTruck = Truck(
                    id    = 0,
                    brand = Brand(0, brandName),
                    model = Model(0, modelName),
                    preci = price.toDoubleOrNull() ?: 0.0,
                    owner = Person(name = ownerName)
                )
                onSubmit(newTruck)
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Confirmar venta")
        }
    }

}

@Composable
fun CreatedScreenTruck( modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = modifier.size(100.dp),
            painter = painterResource(R.drawable.ic_check_circle),
            contentDescription = stringResource(R.string.sell)
        )
        Text(text = stringResource(R.string.sell))
    }
}
