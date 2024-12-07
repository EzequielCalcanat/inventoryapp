package com.ezecalc.inventoryapp_mtw.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ezecalc.inventoryapp_mtw.R
import com.ezecalc.inventoryapp_mtw.data.model.Product

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel()
) {
    // Obtener los states desde el ViewModel
    val lowStockProducts by viewModel.lowStockProducts.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    // Cargar datos al iniciar la pantalla
    LaunchedEffect(Unit) {
        viewModel.loadLowStockProducts()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.home_title)) }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator()
                }
                errorMessage != null -> {
                    Text(
                        text = "Error: $errorMessage",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                lowStockProducts.isEmpty() -> {
                    Text(
                        text = stringResource(id = R.string.empty_inventory),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                else -> {
                    ProductList(products = lowStockProducts)
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewProductList() {
    // Lista de productos de ejemplo
    val products = listOf(
        Product(id = "1", nombre = "Producto 1", descripcion = "Descripción 1", codigo_barras = "11", fecha_actualizacion = "", cantidad = 10),
        Product(id = "2", nombre = "Producto 2", descripcion = "Descripción 2", codigo_barras = "22", fecha_actualizacion = "", cantidad = 20),
        Product(id = "3", nombre = "Producto 3", descripcion = "Descripción 3", codigo_barras = "33", fecha_actualizacion = "", cantidad = 30)
    )

    // Llamada a la función ProductList con la lista de productos
    ProductList(products = products)
}

@Composable
fun ProductList(products: List<Product>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(products) { product ->
            ProductItem(product)
        }
    }
}

@Preview
@Composable
fun ProductItem(
    product: Product = Product(
        id = "1",
        nombre = "Producto de ejemplo",
        descripcion = "Descripción del producto",
        cantidad = 50,
        codigo_barras = "9876543210",
        fecha_actualizacion = "2024-12-07"
    )
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = product.nombre,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(id = R.string.product_quantity) + ": ${product.cantidad}",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = product.descripcion,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}