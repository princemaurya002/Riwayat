package com.princemaurya.rewayat000.data.repository

import com.princemaurya.rewayat000.R
import com.princemaurya.rewayat000.data.model.Product
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    
    private val productsCollection = firestore.collection("products")

    suspend fun getProducts(): List<Product> {
        return try {
            val snapshot = productsCollection.get().await()
            snapshot.documents.mapNotNull { doc ->
                Product(
                    id = doc.id,
                    name = doc.getString("name") ?: "",
                    price = doc.getString("price") ?: "0",
                    description = doc.getString("description") ?: "",
                    imageRes = doc.getLong("imageRes")?.toInt() ?: R.mipmap.ic_launcher
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    suspend fun addProduct(product: Product): Boolean {
        return try {
            productsCollection.document(product.id).set(product).await()
            true
        } catch (e: Exception) {
            false
        }
    }
    
    suspend fun updateProduct(product: Product): Boolean {
        return try {
            productsCollection.document(product.id).set(product).await()
            true
        } catch (e: Exception) {
            false
        }
    }
    
    suspend fun deleteProduct(productId: String): Boolean {
        return try {
            productsCollection.document(productId).delete().await()
            true
        } catch (e: Exception) {
            false
        }
    }
}
