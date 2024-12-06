package com.ezecalc.inventoryapp_mtw.data.remote

import com.ezecalc.inventoryapp_mtw.data.model.Product
import com.ezecalc.inventoryapp_mtw.ui.inventory.InventoryItem
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    // Obtener todos los productos
    @GET("/products")
    suspend fun getAllProducts(): Response<List<Product>>

    // Agregar un nuevo producto
    @POST("/products")
    suspend fun addProduct(@Body product: Product): Response<Void>

    @PUT("/products/{id}")
    suspend fun updateProduct(@Path("id") id: String, @Body product: InventoryItem): Response<Void>

    // Obtener un producto específico por ID
    @GET("/products/{id}")
    suspend fun getProductById(@Path("id") id: String): Response<Product>

    // Eliminar Producto
    @DELETE("/products/{id}")
    suspend fun deleteProduct(@Path("id") id: String): Response<Void>
}