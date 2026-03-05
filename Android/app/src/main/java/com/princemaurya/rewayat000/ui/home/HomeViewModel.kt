package com.princemaurya.rewayat000.ui.home

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

import com.princemaurya.rewayat000.data.parser.VoiceAction
import com.princemaurya.rewayat000.data.parser.VoiceActionParser

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val voiceActionParser: VoiceActionParser
) : ViewModel() {

    private val _voiceAction = MutableLiveData<VoiceAction>()
    val voiceAction: LiveData<VoiceAction> = _voiceAction

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error
    
    private val _homeCards = MutableLiveData<List<HomeCard>>()
    val homeCards: LiveData<List<HomeCard>> = _homeCards
    
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _earnings = MutableLiveData<String>()
    val earnings: LiveData<String> = _earnings
    
    init {
        loadHomeData()
    }
    
    private fun loadHomeData() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Fetch stats (mocking for now, will connect to real logic later)
                _earnings.value = "₹12,450"

                // Fetch Actionable Alerts from Firestore
                val snapshot = firestore.collection("alerts")
                    .orderBy("timestamp", Query.Direction.DESCENDING)
                    .limit(5)
                    .get()
                    .await()

                val cards = snapshot.documents.mapNotNull { doc ->
                    val title = doc.getString("title") ?: ""
                    val description = doc.getString("description") ?: ""
                    val actionText = doc.getString("actionText") ?: "View"
                    val typeStr = doc.getString("type") ?: "TIP"
                    val type = try { HomeCardType.valueOf(typeStr) } catch (e: Exception) { HomeCardType.TIP }
                    
                    HomeCard(title, description, actionText, type)
                }

                if (cards.isEmpty()) {
                    // Fallback to default cards if Firestore is empty
                    _homeCards.value = getDefaultCards()
                } else {
                    _homeCards.value = cards
                }
            } catch (e: Exception) {
                // Handle error and fallback
                _homeCards.value = getDefaultCards()
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun getDefaultCards() = listOf(
        HomeCard("Welcome to Riwayat", "Your digital co-pilot is ready to help you grow.", "Get Started", HomeCardType.TIP),
        HomeCard("New Orders", "Check your latest orders from ONDC.", "View Orders", HomeCardType.ORDERS),
        HomeCard("Sales Analytics", "Your sales increased by 15% this week", "View Details", HomeCardType.ANALYTICS)
    )
    
    fun processVoiceCommand(transcript: String) {
        viewModelScope.launch {
            _isLoading.value = true
            voiceActionParser.parseCommand(transcript)
                .onSuccess {
                    _voiceAction.value = it
                    // In a real app, we would automatically update Firestore here
                }
                .onFailure {
                    _error.value = it.message ?: "Failed to parse voice command"
                }
            _isLoading.value = false
        }
    }

    fun refreshHomeCards() {
        loadHomeData()
    }
}
