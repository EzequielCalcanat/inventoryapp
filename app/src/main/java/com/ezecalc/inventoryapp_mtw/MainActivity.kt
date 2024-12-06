package com.ezecalc.inventoryapp_mtw

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ezecalc.inventoryapp_mtw.ui.MainScreen
import com.ezecalc.inventoryapp_mtw.ui.home.HomeScreen
import com.ezecalc.inventoryapp_mtw.ui.theme.InventoryApp_MTWTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InventoryApp_MTWTheme {
                MainScreen()
            }
        }
    }
}
