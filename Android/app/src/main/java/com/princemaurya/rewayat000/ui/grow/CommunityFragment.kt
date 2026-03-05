package com.princemaurya.rewayat000.ui.grow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.princemaurya.rewayat000.R
import com.princemaurya.rewayat000.databinding.FragmentCommunityBinding

class CommunityFragment : Fragment() {
    private var _binding: FragmentCommunityBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCommunityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val posts = listOf(
            CommunityPost(
                "Rajesh Kumar",
                "Pottery Artisan",
                "2 hours ago",
                "Just finished this beautiful ceramic vase! What do you think?",
                R.mipmap.ic_launcher,
                12,
                5
            ),
            CommunityPost(
                "Priya Sharma",
                "Textile Designer",
                "5 hours ago",
                "Looking for advice on natural dyeing techniques. Any suggestions?",
                R.mipmap.ic_launcher,
                8,
                3
            ),
            CommunityPost(
                "Amit Singh",
                "Wood Carver",
                "1 day ago",
                "Successfully completed my first export order! Thanks to the community for all the support.",
                R.mipmap.ic_launcher,
                25,
                12
            ),
            CommunityPost(
                "Sita Devi",
                "Embroidery Artist",
                "2 days ago",
                "Sharing some traditional embroidery patterns from my grandmother's collection.",
                R.mipmap.ic_launcher,
                18,
                7
            )
        )

        val adapter = CommunityPostAdapter(posts) { post ->
            // TODO: Navigate to post details
        }

        binding.rvPosts.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPosts.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

data class CommunityPost(
    val authorName: String,
    val authorRole: String,
    val timeAgo: String,
    val content: String,
    val imageRes: Int,
    val likes: Int,
    val comments: Int
)
