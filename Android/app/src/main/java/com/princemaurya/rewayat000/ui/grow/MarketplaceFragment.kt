package com.princemaurya.rewayat000.ui.grow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.princemaurya.rewayat000.R
import com.princemaurya.rewayat000.databinding.FragmentMarketplaceBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MarketplaceFragment : Fragment() {
    private var _binding: FragmentMarketplaceBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMarketplaceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val materials = listOf(
            MaterialItem("Organic Cotton Yarn", "₹450/kg", "50kg available", "Source: Varanasi"),
            MaterialItem("Natural Indigo Dye", "₹1,200/liter", "10 liters available", "Source: Tamil Nadu"),
            MaterialItem("Hand-spun Mulberry Silk", "₹3,500/kg", "5kg available", "Source: Assam"),
            MaterialItem("Recycled Wood Blocks", "₹200/set", "20 sets available", "Source: Rajasthan")
        )

        val adapter = MaterialAdapter(materials) { item ->
            // Handle item click/contact supplier
        }
        binding.rvMaterials.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMaterials.adapter = adapter
    }
}

data class MaterialItem(
    val name: String,
    val price: String,
    val stock: String,
    val source: String
)
