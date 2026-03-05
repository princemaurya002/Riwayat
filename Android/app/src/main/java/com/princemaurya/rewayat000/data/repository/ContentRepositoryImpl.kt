package com.princemaurya.rewayat000.data.repository

import com.google.firebase.vertexai.GenerativeModel
import com.google.firebase.vertexai.type.Schema
import com.google.firebase.vertexai.type.content
import com.google.firebase.vertexai.type.generationConfig
import com.princemaurya.rewayat000.data.model.MarketingContent
import com.princemaurya.rewayat000.data.model.Product
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContentRepositoryImpl @Inject constructor(
    private val generativeModel: GenerativeModel
) : ContentRepository {

    private val json = Json { ignoreUnknownKeys = true }

    override suspend fun generateMarketingContent(product: Product, brandContext: String): Result<MarketingContent> {
        return try {
            val prompt = """
                You are an expert marketing co-pilot for an Indian artisan. 
                Product Name: ${product.name}
                Basic Description: ${product.description}
                Brand Context: $brandContext
                
                Generate a comprehensive marketing package including:
                1. A rich, culturally resonant, SEO-optimized product description.
                2. A social media caption (Instagram/Facebook style).
                3. A list of 10 relevant hashtags.
                4. A list of 5 structured search keywords for marketplace discovery.
                
                Respond ONLY with a valid JSON object matching the following structure:
                {
                  "title": "Product Title",
                  "description": "Full Description",
                  "socialMediaCaption": "Caption text",
                  "hashtags": ["#tag1", "#tag2"],
                  "searchKeywords": ["keyword1", "keyword2"]
                }
            """.trimIndent()

            val response = generativeModel.generateContent(
                content { text(prompt) },
                generationConfig = generationConfig {
                    responseMimeType = "application/json"
                }
            )

            val responseText = response.text ?: throw Exception("Empty AI response")
            val content = json.decodeFromString<MarketingContent>(responseText)
            Result.success(content)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun translateContent(text: String, targetLanguage: String): Result<String> {
        return try {
            val prompt = "Translate the following text to $targetLanguage: $text"
            val response = generativeModel.generateContent(prompt)
            Result.success(response.text ?: text)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun generateArtisanBio(qAs: Map<String, String>): Result<String> {
        return try {
            val context = qAs.entries.joinToString("\n") { "${it.key}: ${it.value}" }
            val prompt = "Based on the following answers, write a professional and emotionally engaging artisan bio in first person:\n$context"
            val response = generativeModel.generateContent(prompt)
            Result.success(response.text ?: throw Exception("Failed to generate bio"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun analyzeProductImage(image: android.graphics.Bitmap): Result<MarketingContent> {
        return try {
            val prompt = """
                You are a Visual AI expert for Project Riwayat. 
                Analyze this product photo and generate a marketing kit.
                Identify the craft type, material, and potential story behind the piece.
                
                Respond ONLY with a valid JSON object matching the following structure:
                {
                  "title": "A catchy product title",
                  "description": "An emotionally resonant description (100 words)",
                  "socialMediaCaption": "A punchy social media caption",
                  "hashtags": ["#tag1", "#tag2"],
                  "searchKeywords": ["keyword1", "keyword2"]
                }
            """.trimIndent()

            val response = generativeModel.generateContent(
                content {
                    image(image)
                    text(prompt)
                },
                generationConfig = generationConfig {
                    responseMimeType = "application/json"
                }
            )

            val responseText = response.text ?: throw Exception("Empty AI response")
            val content = json.decodeFromString<MarketingContent>(responseText)
            Result.success(content)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
