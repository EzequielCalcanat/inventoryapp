package com.ezecalc.inventoryapp_mtw.ui.inventory

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ezecalc.inventoryapp_mtw.R
import com.ezecalc.inventoryapp_mtw.data.model.Product
import com.ezecalc.inventoryapp_mtw.ui.theme.DeleteButton100
import com.ezecalc.inventoryapp_mtw.ui.theme.EditButton100
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
    var selectedProduct by remember { mutableStateOf<InventoryItem?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text( stringResource(id = R.string.inventory_name)) },
                actions = {
                    // Botón para mostrar el formulario
                    IconButton(onClick = { showAddProductForm = true }) {
                        Icon(Icons.Default.Add, contentDescription =  stringResource(id = R.string.add_product))
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
                        InventoryItemRow(
                            item = item,
                            onClick = { selectedProduct = item },
                            onDelete = { deletedItem ->
                                inventoryViewModel.deleteProduct(item.id)
                            }
                        )
                    }
                }
            }

        }
    }
    selectedProduct?.let { product ->
        ProductDetailsDialog(
            product = product,
            onDismiss = { selectedProduct = null }
        )
    }
}

@Composable
fun ProductDetailsDialog(
    product: InventoryItem,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            tonalElevation = 10.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text =  stringResource(id = R.string.product_details),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                HorizontalDivider()
                Text(text =  stringResource(id = R.string.product_barcode), style = MaterialTheme.typography.titleMedium)
                Text(text = " ${product.codigo_barras}")
                Text(text =  stringResource(id = R.string.product_name), style = MaterialTheme.typography.titleMedium)
                Text(text = product.nombre)
                Text(text =  stringResource(id = R.string.product_quantity), style = MaterialTheme.typography.titleMedium)
                Text(text = "${product.cantidad}")
                Text(text =  stringResource(id = R.string.product_description), style = MaterialTheme.typography.titleMedium)
                Text(text = product.descripcion)

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text( stringResource(id = R.string.close_button))
                }
            }
        }
    }
}


@Composable
fun InventoryItemRow(item: InventoryItem, onClick: (InventoryItem) -> Unit, onDelete: (InventoryItem) -> Unit) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showEditForm by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick(item) }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()

        ) {
            // Nombre y cantidad del producto en un Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = item.nombre, style = MaterialTheme.typography.titleMedium)
                Text(text = "${item.cantidad}", style = MaterialTheme.typography.titleMedium)
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Row para los botones Modificar y Eliminar
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Modificar
                Button(
                    onClick = { showEditForm = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = EditButton100
                    ),
                    modifier = Modifier
                        .height(36.dp)
                        .padding(vertical = 4.dp),
                    shape = MaterialTheme.shapes.small
                ) {
                    Icon(
                        imageVector = Icons.Default.Create,
                        contentDescription =  stringResource(id = R.string.update_button),
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = { showDeleteDialog = true }, // Mostrar el diálogo
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DeleteButton100
                    ),
                    modifier = Modifier
                        .height(36.dp)
                        .padding(vertical = 4.dp),
                    shape = MaterialTheme.shapes.small
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription =  stringResource(id = R.string.delete_button),
                        tint = Color.White
                    )
                }
            }
        }
    }
    // Diálogo de eliminación
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text( stringResource(id = R.string.delete_title)) },
            text = { Text( stringResource(id = R.string.delete_text)) },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDelete(item) // Llamar a la función de eliminación
                        showDeleteDialog = false // Cerrar el diálogo
                    }
                ) {
                    Text( stringResource(id = R.string.delete_button), color = DeleteButton100)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDeleteDialog = false } // Cerrar el diálogo
                ) {
                    Text( stringResource(id = R.string.cancel_button))
                }
            }
        )
    }
    if (showEditForm) {
        AddProductForm(
            onDismiss = { showEditForm = false },
            inventoryViewModel = viewModel(),
            existingProduct = item
        )
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductForm(
    onDismiss: () -> Unit,
    inventoryViewModel: InventoryViewModel = viewModel(),
    existingProduct: InventoryItem? = null
) {
    var actualProduct: InventoryItem? = null
    if(existingProduct != null) {
        actualProduct = existingProduct
    }
    // Estados para los campos del formulario
    var cantidad by remember { mutableStateOf(existingProduct?.cantidad?.toString() ?: "") }
    var codigo_barras by remember { mutableStateOf(existingProduct?.codigo_barras ?: "") }
    var descripcion by remember { mutableStateOf(existingProduct?.descripcion ?: "") }
    var nombre by remember { mutableStateOf(existingProduct?.nombre ?: "") }
    var isUpdate by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) } // Controla la expansión del dropdown

    val update_message = stringResource(id = R.string.update_alert)
    val added_message = stringResource(id = R.string.added_alert)

    // Estado para la lista de productos existentes
    val productList = inventoryViewModel.inventoryItems.collectAsState().value

    // Filtrar productos por código de barras
    val filteredProducts = productList.filter { it.codigo_barras.contains(codigo_barras, ignoreCase = true) }

    // Estados para mostrar el Snackbar
    var showSnackbar by remember { mutableStateOf(false) }
    var snackbarMessage by remember { mutableStateOf("") }

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
                    onExpandedChange = { expanded = it }
                ) {
                    OutlinedTextField(
                        value = codigo_barras,
                        onValueChange = {
                            codigo_barras = it
                        },
                        label = { Text( stringResource(id = R.string.product_barcode)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        readOnly = false
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        filteredProducts.forEach { product ->
                            DropdownMenuItem(
                                text = { Text(product.nombre) },
                                onClick = {
                                    codigo_barras = product.codigo_barras
                                    descripcion = product.descripcion
                                    nombre = product.nombre
                                    isUpdate = true
                                    expanded = false
                                }
                            )
                        }
                    }
                }
                Text(
                    text =  stringResource(id = R.string.scan_barcode),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                        .clickable {},
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.End
                )

                // Campos de entrada para la cantidad, descripción y nombre
                OutlinedTextField(
                    value = cantidad,
                    onValueChange = { cantidad = it },
                    label = { Text( stringResource(id = R.string.product_quantity)) },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )

                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text( stringResource(id = R.string.product_details)) },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text( stringResource(id = R.string.product_name)) },
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
                        Text( stringResource(id = R.string.cancel_button))
                    }
                    Button(
                        onClick = {
                            /*
                            * Esta condición sirve para validar que recibimos un item desde la
                            * función, si no recibimos nada, quiere decir que el Dialog se
                            * abrió desde agregar producto, por lo que es necesario validar
                            * el código de barras para obtener el producto y utilizarlo
                            * para actualizarlo (sumando la cantidad)
                            * */
                            if (existingProduct == null) {
                                actualProduct = productList.find { it.codigo_barras == codigo_barras }
                            }
                            if (existingProduct != null || actualProduct != null) {
                                var qty: Int = 0
                                if (existingProduct != null) {
                                    qty = cantidad.toInt()
                                } else {
                                    qty = actualProduct!!.cantidad + cantidad.toInt()
                                }
                                val updatedProduct = actualProduct!!.copy(
                                    cantidad = qty,
                                    codigo_barras = codigo_barras,
                                    descripcion = descripcion,
                                    fecha_actualizacion = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(Date()),
                                    nombre = nombre
                                )
                                inventoryViewModel.updateProduct(updatedProduct)
                                snackbarMessage = update_message
                                showSnackbar = true
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
                                snackbarMessage = added_message
                                showSnackbar = true
                            }
                            onDismiss()
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(if (isUpdate) stringResource(id = R.string.update_button) else stringResource(id = R.string.add_button))
                    }
                }
            }
        }
    }
    // Mostrar Snackbar si showSnackbar es verdadero
    if (showSnackbar) {
        Snackbar(
            action = {
                TextButton(onClick = { showSnackbar = false }) {
                    Text(stringResource(id = R.string.close_button))
                }
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(snackbarMessage)
        }
    }
}