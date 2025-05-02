package com.example.proyectofinalandroid.ui.navegation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.proyectofinalandroid.R
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyectofinalandroid.ui.screens.viewmodel.TruckViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.proyectofinalandroid.ui.screens.trucksss.ItemScreen
import com.example.proyectofinalandroid.ui.screens.trucksss.TruckScreen
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import com.example.proyectofinalandroid.ui.screens.inicioApp.ScreenButtonRegisterAndLogin
import com.example.proyectofinalandroid.ui.screens.login.LoginScreen

enum class ScreenAppTruck() {
    Start,
    Register,
    Login,
    List,
    Details,
    Create,
    Delete
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TruckAppBar(
    canNavigateBack: Boolean,
    navigateUp: () -> Unit
){
    TopAppBar(
        title = { Text(stringResource(R.string.app_name)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = stringResource(R.string.backButton)
                    )
                }
            }
        }
    )
}

@Composable
fun TruckApp() {
    val navController = rememberNavController()
    val canPop = navController.previousBackStackEntry != null

    val vm: TruckViewModel = viewModel()

    val backStack by navController.currentBackStackEntryAsState()
    val route = backStack?.destination?.route
    val isList = route == ScreenAppTruck.List.name

    Scaffold (
        topBar = {
            TruckAppBar(
                canNavigateBack = canPop,
                navigateUp = {navController.popBackStack()}
            )
        },
        floatingActionButton = {
            if (isList) {
                FloatingActionButton(onClick = {
                    navController.navigate(ScreenAppTruck.Create.name)
                }) {
                    Icon(Icons.Default.Create, contentDescription = "Vender camión")
                }
            }
        }
    ) { innerPadding: PaddingValues ->
        NavHost(
            navController = navController,
            startDestination = ScreenAppTruck.Start.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable (route = ScreenAppTruck.Start.name){
                ScreenButtonRegisterAndLogin(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    onLoginClick = {
                        navController.navigate("${ScreenAppTruck.Login.name}")
                    },
                    onRegisterClick = {
                        navController.navigate("${ScreenAppTruck.Register.name}")
                    }
                )
            }

            composable (route = ScreenAppTruck.Login.name) {
                LoginScreen(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    onLoginClick = {
                        navController.navigate("${ScreenAppTruck.List.name}")
                    }
                )
            }

            composable (route = ScreenAppTruck.List.name) {

                val uiState = vm.trucksUiState

                TruckScreen(
                    truckUiState = uiState,
                    onDeleteClick = {},
                    onTruckClick = {id ->
                        navController.navigate("${ScreenAppTruck.Details.name}/$id")
                    },
                    onCreateClick =  {
                        navController.navigate(ScreenAppTruck.Create.name)
                    },
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = innerPadding
                )
            }

            composable (
                route = "${ScreenAppTruck.Details.name}/{truckId}",
                arguments = listOf(navArgument("truckId") { type = NavType.IntType })
            ){ backStack ->
                val id = backStack.arguments!!.getInt("truckId")


                LaunchedEffect(id) { vm.getTruckById(id) }

                val uiState = vm.trucksUiState

                TruckScreen(
                    truckUiState   = uiState,
                    onDeleteClick  = { vm.deleteTruck(id) },
                    onDeleteClose  = {
                        vm.getTrucks()
                        navController.popBackStack()
                    },
                    onTruckClick   = {},   // no usado aquí
                    onCreateClick  = {},   // no usado aquí
                    modifier       = Modifier.fillMaxSize(),
                    contentPadding = innerPadding
                )
            }

            composable (ScreenAppTruck.Create.name) {
                ItemScreen(
                    vm = vm,
                    onCreated = {
                        vm.getTrucks()
                        navController.popBackStack()
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}