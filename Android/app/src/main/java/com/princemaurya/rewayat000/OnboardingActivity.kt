package com.princemaurya.rewayat000

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.princemaurya.rewayat000.R
import com.princemaurya.rewayat000.databinding.ActivityOnboardingBinding

class OnboardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var adapter: OnboardingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewPager()
        setupClickListeners()
    }

    private fun setupViewPager() {
        val pages = listOf(
            OnboardingPage(
                "Welcome to Rewayat",
                "Your AI-powered marketplace assistant for local artisans",
                R.mipmap.ic_launcher
            ),
            OnboardingPage(
                "Smart Product Listing",
                "Use AI to create compelling product descriptions and optimize your listings",
                R.mipmap.ic_launcher
            ),
            OnboardingPage(
                "Market Insights",
                "Get real-time analytics and market trends to grow your business",
                R.mipmap.ic_launcher
            ),
            OnboardingPage(
                "Community & Growth",
                "Connect with fellow artisans and discover new opportunities",
                R.mipmap.ic_launcher
            )
        )

        adapter = OnboardingAdapter(pages)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { _, _ -> }.attach()
    }

    private fun setupClickListeners() {
        binding.btnNext.setOnClickListener {
            if (binding.viewPager.currentItem < adapter.itemCount - 1) {
                binding.viewPager.currentItem = binding.viewPager.currentItem + 1
            } else {
                navigateToMain()
            }
        }

        binding.btnSkip.setOnClickListener {
            navigateToMain()
        }

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.btnNext.text = if (position == adapter.itemCount - 1) "Get Started" else "Next"
            }
        })
    }

    private fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}

data class OnboardingPage(
    val title: String,
    val description: String,
    val imageRes: Int
)
