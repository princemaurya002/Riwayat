package com.princemaurya.rewayat000.ui.grow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.princemaurya.rewayat000.databinding.FragmentOpportunitiesBinding

class OpportunitiesFragment : Fragment() {
    private var _binding: FragmentOpportunitiesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOpportunitiesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val opportunities = listOf(
            Opportunity(
                "Delhi Handicrafts Fair 2024",
                "Apply by Dec 15, 2024",
                "Showcase your products at India's largest handicrafts fair",
                "Apply Now",
                OpportunityType.FAIR
            ),
            Opportunity(
                "Corporate Gifting Opportunity",
                "Tata Group - 500 units needed",
                "Supply traditional crafts for corporate gifts",
                "Submit Quote",
                OpportunityType.CORPORATE
            ),
            Opportunity(
                "Wholesaler Partnership",
                "Craft Connect - Mumbai",
                "Become a supplier for retail chain",
                "Apply Now",
                OpportunityType.WHOLESALER
            ),
            Opportunity(
                "Export Opportunity",
                "Global Crafts Ltd",
                "Export your products to international markets",
                "Learn More",
                OpportunityType.EXPORT
            )
        )

        val adapter = OpportunityAdapter(opportunities) { opportunity ->
            // TODO: Handle opportunity click
        }

        binding.rvOpportunities.layoutManager = LinearLayoutManager(requireContext())
        binding.rvOpportunities.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

data class Opportunity(
    val title: String,
    val deadline: String,
    val description: String,
    val actionText: String,
    val type: OpportunityType
)

enum class OpportunityType {
    FAIR, CORPORATE, WHOLESALER, EXPORT
}
