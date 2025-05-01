package com.example.proyectofinalandroid.ui.screens.trucksss

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofinalandroid.network.Truck
import com.example.proyectofinalandroid.network.api.TruckApi
import com.example.proyectofinalandroid.network.api.TruckApi.retrofitService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

sealed interface TruckUiState {
    object Loading: TruckUiState
    data class Success(val trucks : List<Truck>) : TruckUiState
    data class Created(val truck: Truck) : TruckUiState
    data class Details(val truck: Truck) : TruckUiState
    data class Error(val message: String?) : TruckUiState
}

class TruckViewModel : ViewModel() {

    var trucksUiState by mutableStateOf<TruckUiState>(TruckUiState.Loading)
        private set

    init {
        getTrucks()
    }

    fun createdTruck(truck: Truck) {
        viewModelScope.launch {
            try {
                val created = retrofitService.createTruck(truck)
                Log.d("TruckVM", "API OK → asignando Created($created)")
                trucksUiState = TruckUiState.Created(created)
            } catch (e : IOException) {
                Log.e("TruckVM", "IOException creando camión", e)
                TruckUiState.Error(e.message)
                trucksUiState = TruckUiState.Error(e.message)
            } catch (e: HttpException) {
                Log.e("TruckVM", "HttpException creando camión", e)
                if(e.code() == 404) {
                    TruckUiState.Error("USUARIO NO ENCONTRADO")
                } else {
                    TruckUiState.Error(e.message)
                }
                trucksUiState = TruckUiState.Error(e.message)
            }
        }
    }

    fun getTrucks() {
        viewModelScope.launch {
            try {
                val list = withContext(Dispatchers.IO) {
                    TruckApi.retrofitService.getTrucks()
                }
                trucksUiState = TruckUiState.Success(list)
           } catch (e : IOException) {
                trucksUiState = TruckUiState.Error(e.message)
           }  catch (e: Exception) {
                trucksUiState = TruckUiState.Error(e.message)
            }
        }
    }

    fun getTruckById(id: Int) {
        viewModelScope.launch {
            try {
                val truckById = retrofitService.getTruckById(id)
                trucksUiState = TruckUiState.Details(truckById);
            } catch (e : IOException) {
                TruckUiState.Error(e.message)
            }
        }
    }


}

