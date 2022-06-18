package com.example.whacamoleapp.presentation

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.whacamoleapp.R
import com.example.whacamoleapp.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {

    private var _binding: FragmentWelcomeBinding? = null
    private val binding: FragmentWelcomeBinding
        get() = _binding ?: throw RuntimeException("FragmentWelcomeBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pref = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val record = pref.getString(SP_KEY, DEFAULT_VALUE_PREF)

        binding.buttonStart.setOnClickListener {
            launchGameFragment()
        }
        binding.tvRecordGame.text = RECORD_TEXT + record
    }

    private fun launchGameFragment() {
        findNavController().navigate(R.id.action_welcomeFragment_to_gameFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        private const val RECORD_TEXT = "Hit record: "
        private const val DEFAULT_VALUE_PREF = "empty"
        private const val SP_KEY = "record"
    }
}