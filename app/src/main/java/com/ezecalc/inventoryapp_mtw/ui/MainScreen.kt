package com.ezecalc.inventoryapp_mtw.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ezecalc.inventoryapp_mtw.ui.navigation.NavigationAppBar
import com.ezecalc.inventoryapp_mtw.ui.navigation.Screen
import com.ezecalc.inventoryapp_mtw.ui.navigation.TopBar
import com.ezecalc.inventoryapp_mtw.ui.home.HomeScreen
import com.ezecalc.inventoryapp_mtw.ui.inventory.InventoryScreen
import com.ezecalc.inventoryapp_mtw.ui.settings.SettingsScreen

@Composable
fun MainScreen(){
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { NavigationAppBar(navController)},
        topBar = { TopBar(title = "Inicio") }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(Screen.Home.route){ HomeScreen() }
            composable(Screen.Profile.route){ InventoryScreen(viewModel() ) }
            composable(Screen.Settings.route){ SettingsScreen( ) }
        }
    }
}