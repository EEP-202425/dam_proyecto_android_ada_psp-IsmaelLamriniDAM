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
    object Loading: PersonUiState
    data class Login(val owner: Person) : PersonUiState
    data class Error(val message: String?) : PersonUiState
}

class PersonViewModel: ViewModel() {

    var personUiState: PersonUiState by mutableStateOf(PersonUiState.Loading)
        private set

    fun createdOwner(owner: Person) {
        viewModelScope.launch {
            try {
                val createdOwner = PersonApi.retrofitService.createdPerson(owner)
                personUiState  = PersonUiState.Login(createdOwner)
            } catch (e : IOException) {
                PersonUiState.Error(e.message)
            }
        }
    }

}
