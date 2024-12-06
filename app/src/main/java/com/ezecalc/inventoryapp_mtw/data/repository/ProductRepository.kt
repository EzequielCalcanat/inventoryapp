package com.ezecalc.inventoryapp_mtw.data.repository

import com.ezecalc.inventoryapp_mtw.data.model.Product
import com.ezecalc.inventoryapp_mtw.data.remote.RetrofitInstance
import com.ezecalc.inventoryapp_mtw.ui.inventory.InventoryItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class ProductRepository {

    // Obtener todos los productos
    suspend fun getAllProducts(): List<Product>? {
        return withContext(Dispatchers.IO) {
            try {
                val response: Response<List<Product>> = RetrofitInstance.api.getAllProducts()
                if (response.isSuccessful) {
                    response.body()
                } else {
                    null // Para los errores jeje
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    // Agregar un nuevo producto
    suspend fun updateProduct(product: InventoryItem): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response: Response<Void> = RetrofitInstance.api.updateProduct(product.id, product)
                response.isSuccessful
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }
    suspend fun addProduct(product: Product): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response: Response<Void> = RetrofitInstance.api.addProduct(product)
                response.isSuccessful
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }

    // Obtener un producto específico por ID
    suspend fun getProductById(id: String): Product? {
        return withContext(Dispatchers.IO) {
            try {
                val response: Response<Product> = RetrofitInstance.api.getProductById(id)
                if (response.isSuccessful) {
                    response.body()
                } else {
                    null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}