package com.princemaurya.rewayat000.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.princemaurya.rewayat000.data.model.Product
import com.princemaurya.rewayat000.databinding.FragmentAiMarketingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AiMarketingFragment : Fragment() {
    private var _binding: FragmentAiMarketingBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CreateViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAiMarketingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
        setupObservers()
    }

    private fun setupClickListeners() {
        binding.btnGenerate.setOnClickListener {
            val name = binding.etProductName.text.toString()
            val details = binding.etProductDetails.text.toString()

            if (name.isBlank() || details.isBlank()) {
                Toast.makeText(requireContext(), "Please provide name and details", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Create a temporary product object for generation
            val product = Product(id = "temp", name = name, price = "0", description = details, imageRes = 0)
            viewModel.generateMarketingContent(product, "Handmade craft from a local artisan")
        }

        binding.btnCopy.setOnClickListener {
            // Copy logic
            Toast.makeText(requireContext(), "Copied to clipboard", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupObservers() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.btnGenerate.isEnabled = !isLoading
        }

        viewModel.marketingContent.observe(viewLifecycleOwner) { content ->
            binding.cardResult.visibility = View.VISIBLE
            binding.tvResultTitle.text = content.title
            binding.tvResultDescription.text = content.description
            binding.tvResultSocial.text = content.socialMediaCaption
            binding.tvResultHashtags.text = content.hashtags.joinToString(" ")
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
