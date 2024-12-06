package com.ezecalc.inventoryapp_mtw.ui.inventory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezecalc.inventoryapp_mtw.data.model.Product
import com.ezecalc.inventoryapp_mtw.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class InventoryItem(
    val id: String,
    val nombre: String,
    val cantidad: Int,
    val codigo_barras: String,
    val descripcion: String,
    val fecha_actualizacion: String
) {
}

class InventoryViewModel : ViewModel() {

    private val productRepository = ProductRepository()

    // Lista de productos como InventoryItem para la interfaz de usuario
    private val _inventoryItems = MutableStateFlow<List<InventoryItem>>(emptyList())
    val inventoryItems: StateFlow<List<InventoryItem>> = _inventoryItems

    init {
        // Cargar los productos desde la API al iniciar el ViewModel
        loadProducts()
    }

    // Cargar productos desde el repositorio y mapearlos a InventoryItem
    private fun loadProducts() {
        viewModelScope.launch {
            val products = productRepository.getAllProducts()
            _inventoryItems.value = products?.map { product ->
                InventoryItem(
                    id = product.id ?: "",  // Manejar el caso de id nulo
                    nombre = product.nombre,
                    cantidad = product.cantidad,
                    codigo_barras = product.codigo_barras,
                    descripcion = product.descripcion,
                    fecha_actualizacion = product.fecha_actualizacion
                )
            } ?: emptyList()
        }
    }

    // Agregar un nuevo producto a la lista de productos
    fun addProduct(product: Product) {
        viewModelScope.launch {
            val isSuccess = productRepository.addProduct(product)
            if (isSuccess) {
                loadProducts()  // Recargar los productos después de agregar
            }
        }
    }

    // Actualizar un producto en la lista
    fun updateProduct(product: InventoryItem) {
        viewModelScope.launch {
            val isSuccess = productRepository.updateProduct(product)
            if (isSuccess) {
                loadProducts()  // Recargar los productos después de actualizar
            }
        }
    }

    // Actualizar un producto en la lista
    fun deleteProduct(productId: String) {
        viewModelScope.launch {
            val isSuccess = productRepository.deleteProduct(productId)
            if (isSuccess) {
                loadProducts()  // Recargar los productos después de actualizar
            }
        }
    }

    // Convertir un Product a un InventoryItem para la interfaz
    private fun convertToInventoryItem(product: Product): InventoryItem {
        return InventoryItem(
            id = product.id ?: "", // Manejar el caso de id nulo
            nombre = product.nombre,
            cantidad = product.cantidad,
            codigo_barras = product.codigo_barras,
            descripcion = product.descripcion,
            fecha_actualizacion = product.fecha_actualizacion
        )
    }

    // Métodos adicionales para manejar la cantidad de productos, si es necesario
    fun updateItemQuantity(itemId: String, newQuantity: Int) {
        viewModelScope.launch {
            _inventoryItems.value = _inventoryItems.value.map { item ->
                if (item.id == itemId) item.copy(cantidad = newQuantity) else item
            }
        }
    }

    fun removeItem(itemId: String) {
        viewModelScope.launch {
            _inventoryItems.value = _inventoryItems.value.filter { it.id != itemId }
        }
    }
}