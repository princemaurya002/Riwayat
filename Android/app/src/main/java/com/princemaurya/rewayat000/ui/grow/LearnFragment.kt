package com.princemaurya.rewayat000.ui.grow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.princemaurya.rewayat000.R
import com.princemaurya.rewayat000.databinding.FragmentLearnBinding

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.princemaurya.rewayat000.databinding.FragmentLearnBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LearnFragment : Fragment() {
    private var _binding: FragmentLearnBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LearnViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLearnBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservations()
    }

    private fun setupObservations() {
        viewModel.courses.observe(viewLifecycleOwner) { courses ->
            val adapter = CourseAdapter(courses) { course ->
                // Handle course click
            }
            binding.rvCourses.layoutManager = GridLayoutManager(requireContext(), 2)
            binding.rvCourses.adapter = adapter
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }
}

data class Course(
    val title: String,
    val description: String,
    val duration: String,
    val thumbnailRes: Int
)
