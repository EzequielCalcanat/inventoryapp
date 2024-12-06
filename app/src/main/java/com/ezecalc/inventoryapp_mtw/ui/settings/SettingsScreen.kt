package com.ezecalc.inventoryapp_mtw.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
) {
    val isNotificationsEnabled = remember { mutableStateOf(true) }
    val isDarkThemeEnabled = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Configuraciones") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            SettingSwitch(
                label = "Habilitar notificaciones",
                checked = isNotificationsEnabled.value,
                onCheckedChange = { isNotificationsEnabled.value = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            SettingSwitch(
                label = "Tema oscuro",
                checked = isDarkThemeEnabled.value,
                onCheckedChange = { isDarkThemeEnabled.value = it }
            )

            Spacer(modifier = Modifier.height(16.dp))


            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Restablecer configuración")
            }
        }
    }
}

@Composable
fun SettingSwitch(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyLarge
        )
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}