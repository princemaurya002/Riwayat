package com.princemaurya.rewayat000.data.repository

import com.princemaurya.rewayat000.data.model.MarketingContent
import com.princemaurya.rewayat000.data.model.Product

import android.graphics.Bitmap

interface ContentRepository {
    suspend fun generateMarketingContent(product: Product, brandContext: String): Result<MarketingContent>
    suspend fun translateContent(text: String, targetLanguage: String): Result<String>
    suspend fun generateArtisanBio(qAs: Map<String, String>): Result<String>
    suspend fun analyzeProductImage(image: Bitmap): Result<MarketingContent>
}
