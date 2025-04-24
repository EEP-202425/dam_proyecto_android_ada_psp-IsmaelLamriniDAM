package com.example.proyectofinalandroid.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofinalandroid.network.Truck
import com.example.proyectofinalandroid.network.api.TruckApi
import com.example.proyectofinalandroid.network.api.TruckApi.retrofitService
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

sealed interface TruckUiState {

}

class TruckViewModel : ViewModel() {

    fun getTrucks() {
        viewModelScope.launch {
            val trucks : List<Truck> = retrofitService.getTrucks()
        }
    }

}

