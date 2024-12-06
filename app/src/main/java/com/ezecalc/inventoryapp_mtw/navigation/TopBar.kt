package com.ezecalc.inventoryapp_mtw.ui.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.ezecalc.inventoryapp_mtw.ui.theme.Purple40


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(title: String) {
    TopAppBar(
        title = { Text(title) },
        colors = topAppBarColors(
            containerColor = Purple40,
            titleContentColor = Color.White
        )
    )
}