package com.princemaurya.rewayat000.ui.create

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.chip.Chip
import com.princemaurya.rewayat000.databinding.FragmentVisualStudioBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VisualStudioFragment : Fragment() {
    private var _binding: FragmentVisualStudioBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CreateViewModel by viewModels()
    private var selectedBitmap: Bitmap? = null

    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            handleImageUri(uri)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVisualStudioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
        setupObservers()
    }

    private fun setupClickListeners() {
        binding.cardImagePicker.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.btnAnalyze.setOnClickListener {
            selectedBitmap?.let {
                viewModel.analyzeProductImage(it)
            }
        }

        binding.btnViewAr.setOnClickListener {
            (activity as? MainActivity)?.showArPreview()
        }
    }

    private fun handleImageUri(uri: Uri) {
        try {
            selectedBitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val source = ImageDecoder.createSource(requireContext().contentResolver, uri)
                ImageDecoder.decodeBitmap(source)
            } else {
                MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
            }

            binding.ivProductPhoto.setImageBitmap(selectedBitmap)
            binding.llEmptyState.visibility = View.GONE
            binding.btnAnalyze.visibility = View.VISIBLE
            binding.cardAiResult.visibility = View.GONE
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Failed to load image", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupObservers() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progress_bar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.btnAnalyze.isEnabled = !isLoading
        }

        viewModel.marketingContent.observe(viewLifecycleOwner) { content ->
            binding.cardAiResult.visibility = View.VISIBLE
            binding.tvResultTitle.text = content.title
            binding.tvResultDescription.text = content.description
            
            binding.cgHashtags.removeAllViews()
            content.hashtags.forEach { tag ->
                val chip = Chip(requireContext())
                chip.text = tag
                binding.cgHashtags.addView(chip)
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
