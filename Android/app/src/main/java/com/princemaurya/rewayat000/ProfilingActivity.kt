package com.princemaurya.rewayat000

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.princemaurya.rewayat000.databinding.ActivityProfilingBinding

class ProfilingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfilingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfilingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btnContinue.setOnClickListener {
            // TODO: Save profiling data
            navigateToOnboarding()
        }
    }

    private fun navigateToOnboarding() {
        startActivity(Intent(this, OnboardingActivity::class.java))
        finish()
    }
}
