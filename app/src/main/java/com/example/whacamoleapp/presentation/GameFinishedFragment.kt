package com.example.whacamoleapp.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.whacamoleapp.R
import com.example.whacamoleapp.databinding.FragmentGameFinishedBinding


class GameFinishedFragment : Fragment() {

    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() = _binding ?: throw RuntimeException("FragmentWelcomeBinding == null")

    private val args by navArgs<GameFinishedFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            if (args.gameResult.winner) {
                tvResult.text = RESULT_VICTORY
                ivLogo.setImageResource(R.drawable.ic_smile)
            } else {
                tvResult.text = RESULT_FAILED
                ivLogo.setImageResource(R.drawable.ic_sad)
            }
            tvResultHits.text = RESULT_HITS + args.gameResult.countOfHits.toString()
            buttonAgain.setOnClickListener { launchWelcomeFragment() }
        }
    }

    private fun launchWelcomeFragment() {
        findNavController().navigate(R.id.action_gameFinishedFragment_to_welcomeFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val RESULT_HITS = "Hits: "
        private const val RESULT_VICTORY = "VICTORY"
        private const val RESULT_FAILED = "FAILED"
    }
}