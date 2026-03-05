package com.princemaurya.rewayat000.ui.shop

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val firestore: FirebaseFirestore
) : ViewModel() {

    private val _orders = MutableLiveData<List<Order>>()
    val orders: LiveData<List<Order>> = _orders

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        loadOrders()
    }

    private fun loadOrders() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Real-time listening for orders
                firestore.collection("orders")
                    .orderBy("timestamp", Query.Direction.DESCENDING)
                    .addSnapshotListener { snapshot, error ->
                        if (error != null) {
                            // Fallback to mock if Firestore fails
                            _orders.value = getMockOrders()
                            return@addSnapshotListener
                        }
                        
                        val ordersList = snapshot?.documents?.mapNotNull { doc ->
                            Order(
                                orderId = doc.id,
                                productName = doc.getString("productName") ?: "",
                                amount = doc.getString("amount") ?: "",
                                status = doc.getString("status") ?: "New",
                                date = doc.getString("date") ?: ""
                            )
                        } ?: getMockOrders()
                        
                        _orders.value = ordersList
                    }
            } catch (e: Exception) {
                _orders.value = getMockOrders()
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun getMockOrders() = listOf(
        Order("ORD001", "Handwoven Shawl", "₹2,500", "New", "Mar 4, 2026"),
        Order("ORD002", "Ceramic Pottery", "₹1,200", "Processing", "Mar 3, 2026")
    )
}
