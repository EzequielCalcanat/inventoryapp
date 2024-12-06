package com.ezecalc.inventoryapp_mtw.ui.inventory

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ezecalc.inventoryapp_mtw.data.model.Product
import java.text.SimpleDateFormat
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryScreen(
    inventoryViewModel: InventoryViewModel = viewModel()
) {
    // Observa el estado del ViewModel
    val inventoryItems = inventoryViewModel.inventoryItems.collectAsState(initial = emptyList())

    // Puedes agregar una variable de estado para manejar la carga o posibles errores
    val isLoading = inventoryItems.value.isEmpty()
    var showAddProductForm by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Inventario") },
                actions = {
                    // Botón para mostrar el formulario
                    IconButton(onClick = { showAddProductForm = true }) {
                        Icon(Icons.Default.Add, contentDescription = "Agregar Producto")
                    }
                }
            )
        }
    ) { paddingValues ->
        // Muestra un mensaje de carga o la lista de productos
        if (isLoading) {
            // Muestra un indicador de carga
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            if (showAddProductForm) {
                AddProductForm(onDismiss = { showAddProductForm = false })
            } else {
                // Muestra la lista de los elementos del inventario
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp)
                ) {
                    items(inventoryItems.value.size) { index ->
                        val item = inventoryItems.value[index]
                        InventoryItemRow(item)
                    }
                }
            }

        }
    }
}

@Composable
fun InventoryItemRow(item: InventoryItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = item.nombre, style = MaterialTheme.typography.bodyLarge)
            Text(text = "${item.cantidad}", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductForm(
    onDismiss: () -> Unit,
    inventoryViewModel: InventoryViewModel = viewModel()
) {
    // Estados para los campos del formulario
    var cantidad by remember { mutableStateOf("") }
    var codigo_barras by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var isUpdate by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) } // Controla la expansión del dropdown

    // Estado para la lista de productos existentes
    val productList = inventoryViewModel.inventoryItems.collectAsState().value

    // Filtrar productos por código de barras
    val filteredProducts = productList.filter { it.codigo_barras.contains(codigo_barras, ignoreCase = true) }

    // Mostrar el formulario
    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = MaterialTheme.shapes.medium,
            tonalElevation = 10.dp
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Dropdown con ExposedDropdownMenuBox
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = codigo_barras,
                        onValueChange = {
                            codigo_barras = it
                            expanded = true // Abre el dropdown al escribir
                        },
                        label = { Text("Código de Barras") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        filteredProducts.forEach { product ->
                            DropdownMenuItem(
                                text = { Text(product.codigo_barras) },
                                onClick = {
                                    codigo_barras = product.codigo_barras
                                    cantidad = product.cantidad.toString()
                                    descripcion = product.descripcion
                                    nombre = product.nombre
                                    isUpdate = true
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                // Campos de entrada para la cantidad, descripción y nombre
                OutlinedTextField(
                    value = cantidad,
                    onValueChange = { cantidad = it },
                    label = { Text("Cantidad") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )

                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Botones para guardar o cancelar
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = { onDismiss() },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Cancelar")
                    }
                    Button(
                        onClick = {
                            val existingProduct = productList.find { it.codigo_barras == codigo_barras }
                            if (existingProduct != null) {
                                // Actualizar cantidad si el producto ya existe
                                val updatedProduct = existingProduct.copy(
                                    cantidad = existingProduct.cantidad + cantidad.toInt(),
                                    fecha_actualizacion = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(Date())
                                )
                                inventoryViewModel.updateProduct(updatedProduct)
                            } else {
                                // Crear nuevo producto si no existe
                                val newProduct = Product(
                                    id = null, // id es null porque es un nuevo producto
                                    cantidad = cantidad.toInt(),
                                    codigo_barras = codigo_barras,
                                    descripcion = descripcion,
                                    fecha_actualizacion = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(Date()),
                                    nombre = nombre
                                )
                                inventoryViewModel.addProduct(newProduct)
                            }
                            onDismiss()
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(if (isUpdate) "Actualizar" else "Agregar")
                    }
                }
            }
        }
    }
}

