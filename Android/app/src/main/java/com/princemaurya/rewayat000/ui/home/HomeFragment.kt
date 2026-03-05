package com.princemaurya.rewayat000.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.princemaurya.rewayat000.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    private val speechResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val results = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            val transcript = results?.get(0)
            if (!transcript.isNullOrBlank()) {
                viewModel.processVoiceCommand(transcript)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
        setupObservations()
    }

    private fun setupClickListeners() {
        binding.fabVoice.setOnClickListener {
            startVoiceInput()
        }
    }

    private fun startVoiceInput() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak your business action (e.g., 'Sold 2 shawls')")
        }
        try {
            speechResultLauncher.launch(intent)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Speech recognition not available", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupObservations() {
        viewModel.homeCards.observe(viewLifecycleOwner) { cards ->
            val adapter = HomeCardAdapter(cards) { card ->
                // Handle card click
            }
            binding.rvHomeCards.layoutManager = LinearLayoutManager(requireContext())
            binding.rvHomeCards.adapter = adapter
        }

        viewModel.voiceAction.observe(viewLifecycleOwner) { action ->
            val message = "AI Action: ${action.actionType}\nProduct: ${action.product}\nAmount: ${action.amount}"
            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            // Handle loading state
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

data class HomeCard(
    val title: String,
    val description: String,
    val actionText: String,
    val type: HomeCardType
)

enum class HomeCardType {
    ORDERS, SCHEME, FAIR, TIP, ANALYTICS
}
