package com.princemaurya.rewayat000.ui.shop

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.princemaurya.rewayat000.data.model.Product
import com.princemaurya.rewayat000.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {
    
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products
    
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    init {
        loadProducts()
    }
    
    private fun loadProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            
            try {
                val productList = productRepository.getProducts()
                _products.value = productList
            } catch (e: Exception) {
                // Handle error
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun addProduct(product: Product) {
        viewModelScope.launch {
            val success = productRepository.addProduct(product)
            if (success) {
                loadProducts() // Refresh the list
            }
        }
    }
    
    fun updateProduct(product: Product) {
        viewModelScope.launch {
            val success = productRepository.updateProduct(product)
            if (success) {
                loadProducts() // Refresh the list
            }
        }
    }
    
    fun deleteProduct(productId: String) {
        viewModelScope.launch {
            val success = productRepository.deleteProduct(productId)
            if (success) {
                loadProducts() // Refresh the list
            }
        }
    }
    
    fun refreshProducts() {
        loadProducts()
    }
}
