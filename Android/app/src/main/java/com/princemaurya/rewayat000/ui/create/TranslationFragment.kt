package com.princemaurya.rewayat000.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.princemaurya.rewayat000.databinding.FragmentTranslationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TranslationFragment : Fragment() {
    private var _binding: FragmentTranslationBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CreateViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTranslationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
        setupObservers()
    }

    private fun setupClickListeners() {
        binding.btnTranslate.setOnClickListener {
            val source = binding.etSourceText.text.toString()
            val chipId = binding.cgLanguages.checkedChipId
            
            if (source.isBlank()) {
                Toast.makeText(requireContext(), "Please provide text", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val language = when (chipId) {
                binding.chipHindi.id -> "Hindi"
                binding.chipMarathi.id -> "Marathi"
                binding.chipBengali.id -> "Bengali"
                binding.chipTamil.id -> "Tamil"
                else -> {
                    Toast.makeText(requireContext(), "Select a language", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }

            viewModel.translateContent(source, language)
        }
    }

    private fun setupObservers() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.btnTranslate.isEnabled = !isLoading
        }

        viewModel.marketingContent.observe(viewLifecycleOwner) { content ->
            if (content.regionalTranslation != null) {
                binding.cardResult.visibility = View.VISIBLE
                binding.tvTranslatedText.text = content.regionalTranslation
            }
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
