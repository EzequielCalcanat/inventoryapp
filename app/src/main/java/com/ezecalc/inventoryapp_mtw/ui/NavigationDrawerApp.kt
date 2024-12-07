package com.ezecalc.inventoryapp_mtw.ui


import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.ezecalc.inventoryapp_mtw.R
import kotlinx.coroutines.launch
import com.ezecalc.inventoryapp_mtw.ui.navigation.DrawerContent
import com.ezecalc.inventoryapp_mtw.ui.navigation.Navigation
import com.ezecalc.inventoryapp_mtw.ui.home.HomeScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawerApp(isDarkThemeEnabled: Boolean, onThemeChange: (Boolean) -> Unit) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(navController = navController, onCloseDrawer = {
                scope.launch {
                    drawerState.close()
                }
            })
        },
        content = {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = stringResource(id = R.string.main_title),
                                color = Color.White,
                                fontSize = 20.sp,
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }) {
                                Icon(Icons.Default.Menu, contentDescription = "Menu",tint = Color.White)
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color(0xFF6200EE)
                        )
                    )
                }
            ) { paddingValues ->
                Navigation(
                    isDarkThemeEnabled = isDarkThemeEnabled,
                    onThemeChange = onThemeChange,
                    navController = navController,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    )
}