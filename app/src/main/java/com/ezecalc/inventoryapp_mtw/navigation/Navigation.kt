package com.ezecalc.inventoryapp_mtw.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ezecalc.inventoryapp_mtw.ui.home.HomeScreen
import com.ezecalc.inventoryapp_mtw.ui.inventory.InventoryScreen
import com.ezecalc.inventoryapp_mtw.ui.settings.SettingsScreen

@Composable
fun Navigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    isDarkThemeEnabled: Boolean, onThemeChange: (Boolean) -> Unit
) {
    NavHost(navController= navController, startDestination = "home", modifier = modifier){
        composable("home"){ HomeScreen()}
        composable("profile"){ InventoryScreen()}
        composable("settings"){ SettingsScreen(isDarkThemeEnabled = isDarkThemeEnabled, onThemeChange = onThemeChange)}

    }
}