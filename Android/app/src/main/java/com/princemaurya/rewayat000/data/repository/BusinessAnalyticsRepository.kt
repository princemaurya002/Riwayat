package com.princemaurya.rewayat000.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.vertexai.GenerativeModel
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BusinessAnalyticsRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val generativeModel: GenerativeModel
) {
    suspend fun getSalesForecast(): Result<String> {
        return try {
            // Fetch last 3 months of ledger data
            val snapshot = firestore.collection("ledger")
                .orderBy("timestamp")
                .limit(50)
                .get()
                .await()

            val ledgerData = snapshot.documents.joinToString("\n") { 
                "${it.getString("type")}: ${it.getString("amount")} - ${it.getString("note")}"
            }

            val prompt = """
                You are a Business Intelligence expert for Indian Artisans.
                Based on the following recent ledger data, provide a concise (2-3 sentence) sales forecast and 1 actionable tip for next month.
                
                Ledger Data:
                $ledgerData
            """.trimIndent()

            val response = generativeModel.generateContent(prompt)
            Result.success(response.text ?: "Not enough data for forecast.")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getTaxSummary(): Result<String> {
        return try {
            val snapshot = firestore.collection("ledger").get().await()
            val totalIncome = snapshot.documents.filter { it.getString("type") == "INCOME" }
                .sumOf { it.getString("amount")?.toDouble() ?: 0.0 }
            
            val prompt = "Generate a very simple 1-sentence GST/Tax compliance summary for an artisan with an annual turnover of approximately ${totalIncome * 4} rupees (extrapolated)."
            val response = generativeModel.generateContent(prompt)
            Result.success(response.text ?: "Tax summary unavailable.")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
