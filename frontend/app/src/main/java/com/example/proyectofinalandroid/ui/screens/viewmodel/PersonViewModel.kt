package com.example.proyectofinalandroid.ui.screens.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofinalandroid.network.Person
import com.example.proyectofinalandroid.api.PersonApi
import kotlinx.coroutines.launch
import okio.IOException


sealed interface PersonUiState {
    object Register: PersonUiState
    object Loading: PersonUiState
    data class RegisterSuccess(val message: String?) : PersonUiState
    data class Update(val message: String?) :  PersonUiState
    data class Error(val message: String?) : PersonUiState
}

class PersonViewModel: ViewModel() {

    var personUiState: PersonUiState by mutableStateOf(PersonUiState.Register)
        private set

    fun createdOwner(owner: Person) {
        viewModelScope.launch {
            personUiState = PersonUiState.Loading
            try {
                val response = PersonApi.retrofitService.createdPerson(owner)
                if (response.isSuccessful){
                personUiState = PersonUiState.RegisterSuccess("Usuario registrado con éxito")
                } else {
                    personUiState = PersonUiState.Error("Error ${response.code()} ${response.message()}")
                }
            } catch (e: IOException) {
                personUiState =  PersonUiState.Error(e.message)
            } catch (e: retrofit2.HttpException) {
                personUiState =  PersonUiState.Error("${e.code()}, ${e.message()}")
            }
        }
    }

    fun updateOwner(id: Int, owner: Person) {
        viewModelScope.launch {
            try {
                val update = PersonApi.retrofitService.updatePerson(id, owner)
                personUiState = PersonUiState.Update("Se ha actualizado con éxito")
            } catch (e: IOException) {
                PersonUiState.Error(e.message)
            }
        }
    }

    fun statusErrorRegister (message: String?) {
        personUiState = PersonUiState.Error(message)
    }

    fun resetToRegister(){
        personUiState = PersonUiState.Register
    }
}
