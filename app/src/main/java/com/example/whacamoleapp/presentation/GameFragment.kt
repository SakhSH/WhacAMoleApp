package com.example.whacamoleapp.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.whacamoleapp.databinding.FragmentGameBinding
import com.example.whacamoleapp.domain.entity.Mole


class GameFragment : Fragment() {


    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentWelcomeBinding == null")

    private val gameViewModel by viewModels<GameViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        gameViewModel.randomEnabledMole()
    }

    private fun observeViewModel() {
        with(gameViewModel) {
            formattedTime.observe(viewLifecycleOwner) {
                binding.tvTimer.text = it
            }
            countOfHitsMole.observe(viewLifecycleOwner) {
                binding.tvCountOfHits.text = HITS_TEXT + it
            }
            moleList.observe(viewLifecycleOwner) {
                moleState(it)
            }
            isFinish.observe(viewLifecycleOwner) {
                if (it) {
                    launchGameFinishedFragment()
                }
            }
        }
    }

    private fun moleState(list: List<Mole>) {
        with(binding) {
            ibMole1.isEnabled = list[0].IsActive
            ibMole2.isEnabled = list[1].IsActive
            ibMole3.isEnabled = list[2].IsActive
            ibMole4.isEnabled = list[3].IsActive
            ibMole5.isEnabled = list[4].IsActive
            ibMole6.isEnabled = list[5].IsActive
            ibMole7.isEnabled = list[6].IsActive
            ibMole8.isEnabled = list[7].IsActive
            ibMole9.isEnabled = list[8].IsActive

            ibMole1.setOnClickListener {
                gameViewModel.hittingTheMole(list[0])
            }
            ibMole2.setOnClickListener {
                gameViewModel.hittingTheMole(list[1])
            }
            ibMole3.setOnClickListener {
                gameViewModel.hittingTheMole(list[2])
            }
            ibMole4.setOnClickListener {
                gameViewModel.hittingTheMole(list[3])
            }
            ibMole5.setOnClickListener {
                gameViewModel.hittingTheMole(list[4])
            }
            ibMole6.setOnClickListener {
                gameViewModel.hittingTheMole(list[5])
            }
            ibMole7.setOnClickListener {
                gameViewModel.hittingTheMole(list[6])
            }
            ibMole8.setOnClickListener {
                gameViewModel.hittingTheMole(list[7])
            }
            ibMole9.setOnClickListener {
                gameViewModel.hittingTheMole(list[8])
            }
        }
    }

    private fun launchGameFinishedFragment() {
        val pref = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val edt = pref.edit()
        val record = pref.getString(SP_KEY, DEFAULT_VALUE_PREF)
        if (record != null) {
            if (record.toInt() < gameViewModel.gameResult.countOfHits) {
                edt.putString(SP_KEY, gameViewModel.gameResult.countOfHits.toString())
                edt.apply()
            }
        }

        findNavController().navigate(
            GameFragmentDirections.actionGameFragmentToGameFinishedFragment(
                gameViewModel.gameResult
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val DEFAULT_VALUE_PREF = "empty"
        private const val SP_KEY = "record"
        private const val HITS_TEXT = "Hits: "
    }
}