package com.princemaurya.rewayat000

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.princemaurya.rewayat000.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btnLogin.setOnClickListener {
            // TODO: Implement login logic
            navigateToProfiling()
        }

        binding.btnSignup.setOnClickListener {
            // TODO: Implement signup logic
            navigateToProfiling()
        }

        binding.tvSkip.setOnClickListener {
            navigateToProfiling()
        }
    }

    private fun navigateToProfiling() {
        startActivity(Intent(this, ProfilingActivity::class.java))
        finish()
    }
}
