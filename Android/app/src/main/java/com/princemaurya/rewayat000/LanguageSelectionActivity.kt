package com.princemaurya.rewayat000

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.princemaurya.rewayat000.databinding.ActivityLanguageSelectionBinding

class LanguageSelectionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLanguageSelectionBinding
    private lateinit var prefs: SharedPreferences
    private lateinit var adapter: LanguageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLanguageSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

        setupRecyclerView()
        setupContinueButton()
    }

    private fun setupRecyclerView() {
        val languages = listOf(
            Language("English", "English", "en"),
            Language("हिंदी", "Hindi", "hi"),
            Language("தமிழ்", "Tamil", "ta"),
            Language("తెలుగు", "Telugu", "te"),
            Language("मराठी", "Marathi", "mr")
        )

        adapter = LanguageAdapter(languages) { selectedLanguage ->
            // Save selected language
            prefs.edit().putString("selected_language", selectedLanguage.code).apply()
            binding.btnContinue.isEnabled = true
        }

        binding.rvLanguages.layoutManager = LinearLayoutManager(this)
        binding.rvLanguages.adapter = adapter
    }

    private fun setupContinueButton() {
        binding.btnContinue.setOnClickListener {
            val selectedLanguage = prefs.getString("selected_language", "en") ?: "en"
            // Navigate to Auth flow
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
        }
    }
}

data class Language(
    val nativeName: String,
    val englishName: String,
    val code: String
)
