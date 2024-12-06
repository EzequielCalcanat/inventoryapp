package com.ezecalc.inventoryapp_mtw.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.ezecalc.inventoryapp_mtw.ui.theme.Purple40


@Composable
fun NavigationAppBar(navController: NavController) {

    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf(Screen.Home.route, Screen.Profile.route, Screen.Settings.route)

    NavigationBar(containerColor = Purple40, contentColor = Color.White) {
        items.forEachIndexed{index, item ->
            NavigationBarItem(
                onClick = {
                    navController.navigate(item){
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                    selectedItem = index
                },
                label = { Text(item)},
                selected = selectedItem == index,
                icon = {
                    when(item){
                        Screen.Home.route-> Icon(Screen.Home.icon, contentDescription = Screen.Home.title)
                        Screen.Profile.route-> Icon(Screen.Profile.icon, contentDescription = Screen.Profile.title)
                        Screen.Settings.route-> Icon(Screen.Settings.icon, contentDescription = Screen.Settings.title)
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor =  Color.Gray,
                    unselectedIconColor = Color.White,
                    selectedTextColor = Color.White,
                    unselectedTextColor = Color.White
                )

            )
        }

        //NavigationBarItem(selected = , onClick = { /*TODO*/ }, icon = { /*TODO*/ })
    }
}