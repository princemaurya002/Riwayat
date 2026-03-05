package com.princemaurya.rewayat000.ui.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.princemaurya.rewayat000.data.model.MarketingContent
import com.princemaurya.rewayat000.data.model.Product
import com.princemaurya.rewayat000.data.repository.ContentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateViewModel @Inject constructor(
    private val contentRepository: ContentRepository
) : ViewModel() {

    private val _marketingContent = MutableLiveData<MarketingContent>()
    val marketingContent: LiveData<MarketingContent> = _marketingContent

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun generateMarketingContent(product: Product, brandContext: String) {
        viewModelScope.launch {
            _isLoading.value = true
            contentRepository.generateMarketingContent(product, brandContext)
                .onSuccess {
                    _marketingContent.value = it
                }
                .onFailure {
                    _error.value = it.message ?: "Failed to generate content"
                }
            _isLoading.value = false
        }
    }

    fun translateContent(text: String, language: String) {
        viewModelScope.launch {
            _isLoading.value = true
            contentRepository.translateContent(text, language)
                .onSuccess {
                    // Update translation in existing content if possible
                    _marketingContent.value = _marketingContent.value?.copy(regionalTranslation = it)
                }
                .onFailure {
                    _error.value = it.message ?: "Translation failed"
                }
    fun analyzeProductImage(image: android.graphics.Bitmap) {
        viewModelScope.launch {
            _isLoading.value = true
            contentRepository.analyzeProductImage(image)
                .onSuccess {
                    _marketingContent.value = it
                }
                .onFailure {
                    _error.value = it.message ?: "Image analysis failed"
                }
            _isLoading.value = false
        }
    }
}
