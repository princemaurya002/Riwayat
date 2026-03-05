package com.princemaurya.rewayat000.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.princemaurya.rewayat000.databinding.BottomSheetCreateBinding

class CreateBottomSheet : BottomSheetDialogFragment() {
    private var _binding: BottomSheetCreateBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetCreateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btnAddProduct.setOnClickListener {
            (activity as? MainActivity)?.showVisualStudio()
            dismiss()
        }

        binding.btnAiMarketing.setOnClickListener {
            (activity as? MainActivity)?.showAiMarketing()
            dismiss()
        }

        binding.btnTranslate.setOnClickListener {
            (activity as? MainActivity)?.showTranslation()
            dismiss()
        }

        binding.btnSocialPost.setOnClickListener {
            // Placeholder for Social Post
            dismiss()
        }

        binding.btnLogExpense.setOnClickListener {
            // Placeholder for Expense logging
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(): CreateBottomSheet {
            return CreateBottomSheet()
        }
    }
}
