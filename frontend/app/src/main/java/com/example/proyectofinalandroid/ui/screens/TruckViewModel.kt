package com.example.proyectofinalandroid.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofinalandroid.network.Truck
import com.example.proyectofinalandroid.network.api.TruckApi
import com.example.proyectofinalandroid.network.api.TruckApi.retrofitService
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okio.IOException

sealed interface TruckUiState {
    object Loading: TruckUiState
    data class Success(val trucks : List<Truck>) : TruckUiState
    data class Created(val truck: Truck) : TruckUiState
    data class Details(val truck: Truck) : TruckUiState
    data class Error(val message: String?) : TruckUiState
}

class TruckViewModel : ViewModel() {

    var trucksUiState: TruckUiState by mutableStateOf(TruckUiState.Loading)
        private set

    fun createdTruck(truck: Truck) {
        viewModelScope.launch {
            try {
                val created = TruckApi.retrofitService.createTruck(truck)
                trucksUiState = TruckUiState.Created(created)
            } catch (e : IOException) {
                TruckUiState.Error(e.message)
            }
        }
    }

    fun getTrucks() {
        viewModelScope.launch {
           try {
               val list =  TruckApi.retrofitService.getTrucks()
               trucksUiState = TruckUiState.Success(list)
           } catch (e : IOException) {
               TruckUiState.Error(e.message)
           }
        }
    }

    fun getTruckById(id: Int) {
        viewModelScope.launch {
            try {
                val truckById = TruckApi.retrofitService.getTruckById(id)
                trucksUiState = TruckUiState.Details(truckById);
            } catch (e : IOException) {
                TruckUiState.Error(e.message)
            }
        }
    }


}

