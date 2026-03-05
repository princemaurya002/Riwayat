package com.princemaurya.rewayat000.ui.operations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.princemaurya.rewayat000.R
import com.princemaurya.rewayat000.databinding.FragmentOperationsBinding

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.princemaurya.rewayat000.databinding.FragmentOperationsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OperationsFragment : Fragment() {
    private var _binding: FragmentOperationsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: OperationsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOperationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservations()
        viewModel.loadInsights()
    }

    private fun setupObservations() {
        viewModel.forecast.observe(viewLifecycleOwner) { forecast ->
            binding.tvForecast.text = forecast
        }

        viewModel.taxSummary.observe(viewLifecycleOwner) { tip ->
            binding.tvTaxTip.text = tip
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.cardAiInsights.alpha = if (isLoading) 0.5f else 1.0f
        }
    }

    private fun setupRecyclerView() {
        val tools = listOf(
            OperationTool("Shipping", "Manage deliveries and courier services", R.mipmap.ic_launcher),
            OperationTool("My Accounts", "Track income, expenses, and profits", R.mipmap.ic_launcher),
            OperationTool("Suppliers", "Find and manage material suppliers", R.mipmap.ic_launcher),
            OperationTool("Business Loans", "Access microfinance and loans", R.mipmap.ic_launcher),
            OperationTool("Inventory", "Manage stock and materials", R.mipmap.ic_launcher),
            OperationTool("Reports", "Generate business reports", R.mipmap.ic_launcher)
        )

        val adapter = OperationToolAdapter(tools) { tool ->
            when (tool.name) {
                "Shipping" -> {
                    // TODO: Navigate to shipping screen
                }
                "My Accounts" -> {
                    (activity as? com.princemaurya.rewayat000.MainActivity)?.showFragment(LedgerFragment())
                }
                "Suppliers" -> {
                    // TODO: Navigate to suppliers screen
                }
                "Business Loans" -> {
                    // TODO: Navigate to loans screen
                }
                "Inventory" -> {
                    // TODO: Navigate to inventory screen
                }
                "Reports" -> {
                    // TODO: Navigate to reports screen
                }
            }
        }

        binding.rvTools.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvTools.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

data class OperationTool(
    val name: String,
    val description: String,
    val iconRes: Int
)
