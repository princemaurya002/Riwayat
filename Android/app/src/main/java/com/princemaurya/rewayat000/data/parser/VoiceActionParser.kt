package com.princemaurya.rewayat000.data.parser

import com.google.firebase.vertexai.GenerativeModel
import com.princemaurya.rewayat000.data.model.MarketingContent
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Serializable
data class VoiceAction(
    val actionType: String, // "SALE", "EXPENSE", "INVENTORY"
    val amount: String? = null,
    val product: String? = null,
    val quantity: Int? = null,
    val note: String? = null
)

@Singleton
class VoiceActionParser @Inject constructor(
    private val generativeModel: GenerativeModel
) {
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun parseCommand(transcript: String): Result<VoiceAction> {
        return try {
            val prompt = """
                You are a Business Intelligence parser for Project Riwayat.
                Extract the business intent from the following artisan's voice transcript:
                "$transcript"
                
                Possible actions:
                1. SALE: Recording a sale (e.g., "Sold 2 shawls for 5000")
                2. EXPENSE: Recording a cost (e.g., "Bought wool for 300")
                3. INVENTORY: Updating stock (e.g., "Added 10 pots to inventory")
                
                Respond ONLY with a valid JSON object matching this structure:
                {
                  "actionType": "SALE|EXPENSE|INVENTORY",
                  "amount": "1200", (if applicable)
                  "product": "item name",
                  "quantity": 1,
                  "note": "any relevant detail"
                }
            """.trimIndent()

            val response = generativeModel.generateContent(prompt)
            val responseText = response.text ?: throw Exception("Empty AI response")
            val action = json.decodeFromString<VoiceAction>(responseText)
            Result.success(action)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
