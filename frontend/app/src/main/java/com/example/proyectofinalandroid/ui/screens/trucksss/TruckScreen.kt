package com.example.proyectofinalandroid.ui.screens.trucksss

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.proyectofinalandroid.R
import com.example.proyectofinalandroid.network.Brand
import com.example.proyectofinalandroid.network.Model
import com.example.proyectofinalandroid.network.Person
import com.example.proyectofinalandroid.network.Truck
import com.example.proyectofinalandroid.ui.screens.viewmodel.TruckUiState
import com.example.proyectofinalandroid.ui.theme.ProyectoFinalAndroidTheme

@Composable
fun TruckScreen(
    truckUiState: TruckUiState,
    onDeleteClick: () -> Unit,
    onDeleteClose: () -> Unit = {},
    onTruckClick: (Int) -> Unit,
    onCreateClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    when(truckUiState) {

        is TruckUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is TruckUiState.Success -> ShowTrucks(
            truckUiState.trucks,
            onItemClick = onTruckClick,
            modifier.padding(top = contentPadding.calculateTopPadding())
        )
        is TruckUiState.Created -> CreatedScreen(
            truckUiState.truck,
            modifier = modifier.fillMaxSize()
        )
        is TruckUiState.Details -> showDetails(
            truck = truckUiState.truck,
            onDeleteClose = onDeleteClose,
            onDeleteClick = onDeleteClick,
            modifier = modifier.fillMaxSize()
        )
        is TruckUiState.Error -> ErrorScreen(
            truckUiState.message,
            modifier = modifier.fillMaxSize()
        )
        is TruckUiState.Delete -> DeleteSucces(
            message = "Se ha comprado con éxito",
            onClose = onDeleteClose
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
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier
){
    Text(text = "CAMIONES", modifier = Modifier.padding(163.dp, 35.dp))

    LazyColumn (
        modifier = modifier.fillMaxSize().padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ){

        items (trucks) {
            truck ->
            Card(
                modifier = Modifier.fillMaxWidth()
                    .clickable { onItemClick(truck.id) },
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ){
                Column (
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                ) {
                    Text(text = "ID: ${truck.id}")
                    Text(text = "Marca: ${truck.brand.name}")
                    Text(text = "Modelo: ${truck.model.name}")
                    Text(text = "Precio: ${truck.preci}")
                    Text(text = "Dueño: ${truck.owner.name}")
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
            contentDescription = stringResource(R.string.sell)
        )
    }
}

@Composable
fun showDetails(
    truck: Truck,
    onDeleteClose: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Text(text = "CAMION")

        Text(text = "ID: ${truck.id}")
        Text(text = "Marca: ${truck.brand.name}")
        Text(text = "Modelo: ${truck.model.name}")
        Text(text = "Precio: ${truck.preci}")
        Text(text = "Dueño: ${truck.owner.name}, ${truck.owner.lastName}, ${truck.owner.mail}")
        Button(
            onClick = onDeleteClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Comprar")
        }

        Button(
            onClick = onDeleteClose,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver")
        }
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
@Composable
fun DeleteSucces(
    message:  String,
    onClose: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = message,
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(Modifier.height(16.dp))
            Button(onClick = onClose) {
                Text("Volver")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ProyectoFinalAndroidTheme {
        val newTruck = Truck(
            id    = 0,
            brand = Brand(0, "caca"),
            model = Model(0, "caca"),
            preci = 0.0,
            owner = Person(name = "caca")
        )
        showDetails(
            truck = newTruck,
            onDeleteClick =  {},
            onDeleteClose = {},
            modifier = Modifier,
        )
    }
}


