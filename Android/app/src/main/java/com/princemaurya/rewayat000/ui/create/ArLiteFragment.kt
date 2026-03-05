package com.princemaurya.rewayat000.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.princemaurya.rewayat000.databinding.FragmentArLiteBinding

class ArLiteFragment : Fragment() {
    private var _binding: FragmentArLiteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArLiteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // This is a placeholder for ARCore/SceneView integration
        binding.tvArStatus.text = "AR Environment Ready. \nWaiting for product model..."
    }
}
