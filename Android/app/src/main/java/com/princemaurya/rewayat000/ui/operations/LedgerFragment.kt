package com.princemaurya.rewayat000.ui.operations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.princemaurya.rewayat000.databinding.FragmentLedgerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LedgerFragment : Fragment() {
    private var _binding: FragmentLedgerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLedgerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupClickListeners()
    }

    private fun setupRecyclerView() {
        // Placeholder for transaction list
        binding.rvTransactions.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupClickListeners() {
        binding.btnRecordIncome.setOnClickListener {
            // Logic to record income
        }
        binding.btnRecordExpense.setOnClickListener {
            // Logic to record expense
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
