package com.ezecalc.inventoryapp_mtw.ui.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ezecalc.inventoryapp_mtw.R


@Composable
fun DrawerContent(navController: NavHostController, onCloseDrawer: () -> Unit) {
    ModalDrawerSheet {
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text =  stringResource(id = R.string.inventory_name),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                fontSize = 20.sp,
                textAlign = TextAlign.Left
            )
            HorizontalDivider()
            DrawerItem(text =  stringResource(id = R.string.home_name), icon = Icons.Default.Home,
                onClick = {
                    navController.navigate("home")
                    onCloseDrawer()
                }
            )
            DrawerItem(text =  stringResource(id = R.string.product_name), icon = Icons.Default.ShoppingCart,
                onClick = {
                    navController.navigate("profile")
                    onCloseDrawer()
                }
            )
            DrawerItem(text =  stringResource(id = R.string.settings_name), icon = Icons.Default.Settings,
                onClick = {
                    navController.navigate("settings")
                    onCloseDrawer()
                }
            )

        }
    }

}

@Composable
fun DrawerItem(
    text: String, icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            icon, contentDescription = null, modifier = Modifier
                .size(48.dp)
                .padding(end = 16.dp)
        )
        Text(text, fontSize = 18.sp)
    }

}