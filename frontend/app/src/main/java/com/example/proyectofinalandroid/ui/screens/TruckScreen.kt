package com.example.proyectofinalandroid.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.proyectofinalandroid.R
import com.example.proyectofinalandroid.network.Truck

@Composable
fun TruckScreen(
    truckUiState: TruckUiState,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    when(truckUiState) {
        is TruckUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is TruckUiState.Success -> ShowTrucks(
            truckUiState.trucks,
            modifier.padding(top = contentPadding.calculateTopPadding())
        )
        is TruckUiState.Created -> CreatedScreen(
            truckUiState.truck,
            modifier = modifier.fillMaxSize()
        )
        is TruckUiState.Details -> showDetails(
            truckUiState.truck,
            modifier = modifier.fillMaxSize()
        )
        is TruckUiState.Error -> ErrorScreen(
            truckUiState.message,
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
fun ShowTrucks(
    trucks: List<Truck>,
    modifier: Modifier = Modifier
){
    LazyColumn (
        modifier = modifier.fillMaxSize().padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ){
        items (trucks) {
            truck ->
            Card(
                modifier = Modifier.fillMaxSize(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ){
                Column (
                    modifier = Modifier.fillMaxWidth().padding(16.dp).verticalScroll(rememberScrollState())
                ) {
                    Text(text = "ID: ${truck.id}")
                    Text(text = "Marca: ${truck.brand}")
                    Text(text = "Modelo: ${truck.model}")
                    Text(text = "Precio: ${truck.preci}")
                    Text(text = "Dueño: ${truck.owner}")
                }
            }
        }
    }
}

@Composable
fun CreatedScreen(
    truck: Truck,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            modifier = modifier.size(100.dp),
            painter = painterResource(R.drawable.ic_check_circle),
            contentDescription = stringResource(R.string.created)
        )

        Text(text = "ID: ${truck.id}")
        Text(text = "Marca: ${truck.brand}")
        Text(text = "Modelo: ${truck.model}")
        Text(text = "Precio: ${truck.preci}")
        Text(text = "Dueño: ${truck.owner}")
    }
}

@Composable
fun showDetails(
    truck: Truck,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = "ID: ${truck.id}")
        Text(text = "Marca: ${truck.brand}")
        Text(text = "Modelo: ${truck.model}")
        Text(text = "Precio: ${truck.preci}")
        Text(text = "Dueño: ${truck.owner}")
    }
}

@Composable
fun ErrorScreen(
    messsage: String?,
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error),
            contentDescription = ""
        )
        if(messsage != null){
            Text(text = messsage, modifier = Modifier.padding(16.dp))
        }
    }
}