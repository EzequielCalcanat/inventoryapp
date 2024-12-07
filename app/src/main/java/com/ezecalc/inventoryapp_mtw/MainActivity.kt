package com.ezecalc.inventoryapp_mtw

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.ezecalc.inventoryapp_mtw.ui.NavigationDrawerApp
import com.ezecalc.inventoryapp_mtw.ui.theme.InventoryApp_MTWTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AppContent()
        }
    }
}

@Composable
fun AppContent() {
    // Aquí manejamos el estado del tema
    var isDarkThemeEnabled by remember { mutableStateOf(false) }

    // Aplicamos el tema
    InventoryApp_MTWTheme(darkTheme = isDarkThemeEnabled) {
        NavigationDrawerApp(
            isDarkThemeEnabled = isDarkThemeEnabled,
            onThemeChange = { isDarkThemeEnabled = it }
        )
    }
}


