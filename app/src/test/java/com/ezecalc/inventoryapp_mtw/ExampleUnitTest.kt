package com.ezecalc.inventoryapp_mtw

import com.ezecalc.inventoryapp_mtw.data.repository.ProductRepository
import com.ezecalc.inventoryapp_mtw.data.model.Product
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 */
class ExampleUnitTest {

    // Crear una clase de simulación de Retrofit para pruebas
    class FakeProductRepository : ProductRepository() {
        // Simula una respuesta exitosa para getAllProducts
        override suspend fun getAllProducts(): List<Product>? {
            return listOf(
                Product("id1", 1, "1111", "Descripción 1", "fecha1", "Producto 1"),
                Product("id2", 2, "2222", "Descripción 2", "fecha2", "Producto 2")
            ) // Simulando respuesta exitosa
        }

        // Simula una respuesta fallida para getAllProducts
        suspend fun getAllProductsWithError(): List<Product>? {
            return null // Simulando error
        }

        // Simula la actualización exitosa de un producto
        suspend fun updateProduct(product: Product): Boolean {
            return true // Simulando éxito
        }

        // Simula la actualización fallida de un producto
        suspend fun updateProductWithError(product: Product): Boolean {
            return false // Simulando fallo
        }
    }

    @Test
    fun `test getAllProducts success`() = runBlocking {
        // Crear instancia de FakeProductRepository
        val repository = FakeProductRepository()

        // Llamar al método de repositorio simulado
        val result = repository.getAllProducts()

        // Verificar el resultado
        assertNotNull(result)
        assertEquals(2, result?.size)
        assertEquals("Producto 1", result?.get(0)?.nombre)
    }

    @Test
    fun `test getAllProducts failure`() = runBlocking {
        // Crear instancia de FakeProductRepository
        val repository = FakeProductRepository()

        // Llamar al método de repositorio simulado con error
        val result = repository.getAllProductsWithError()

        // Verificar que el resultado sea nulo
        assertNull(result)
    }

    @Test
    fun `test updateProduct success`() = runBlocking {
        // Crear instancia de FakeProductRepository
        val repository = FakeProductRepository()

        // Crear un producto de prueba
        val product = Product("id1", 1, "1111", "Descripción 1", "fecha1", "Producto 1")

        // Llamar al método de repositorio simulado para actualizar el producto
        val result = repository.updateProduct(product)

        // Verificar que la actualización fue exitosa
        assertTrue(result)
    }

    @Test
    fun `test updateProduct failure`() = runBlocking {
        // Crear instancia de FakeProductRepository
        val repository = FakeProductRepository()

        // Crear un producto de prueba
        val product = Product("id1", 1, "1111", "Descripción 1", "fecha1", "Producto 1")

        // Llamar al método de repositorio simulado con fallo
        val result = repository.updateProductWithError(product)

        // Verificar que la actualización falló
        assertFalse(result)
    }
}
