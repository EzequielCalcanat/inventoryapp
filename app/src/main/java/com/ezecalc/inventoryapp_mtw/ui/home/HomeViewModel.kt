package com.ezecalc.inventoryapp_mtw.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezecalc.inventoryapp_mtw.data.model.Product
import com.ezecalc.inventoryapp_mtw.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val repository = ProductRepository()

    // Estado para almacenar los productos con cantidad < 10
    private val _lowStockProducts = MutableStateFlow<List<Product>>(emptyList())
    val lowStockProducts: StateFlow<List<Product>> = _lowStockProducts

    // Estado para indicar si se está cargando
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // Estado para manejar errores
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    // Cargar productos con cantidad menor a 10
    fun loadLowStockProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null // Limpiar errores previos
            try {
                val products = repository.getAllProducts()
                if (products != null) {
                    _lowStockProducts.value = products.filter { it.cantidad < 10 }
                } else {
                    _errorMessage.value = "No se pudieron obtener los productos."
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}