package com.ezecalc.inventoryapp_mtw.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings

import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route:String,val title:String, val icon:ImageVector){
    data object Home :Screen("home", "Inicio",Icons.Default.Home,)
    data object Profile :Screen("inventory", "Inventario",Icons.Default.DateRange,)
    data object Settings :Screen("settings", "Configuración",Icons.Default.Settings,)
}


