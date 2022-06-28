package com.example.whacamoleapp.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.whacamoleapp.R
import com.example.whacamoleapp.databinding.FragmentGameBinding
import com.example.whacamoleapp.domain.entity.GameResult
import com.example.whacamoleapp.domain.entity.Mole


class GameFragment : Fragment() {

    private val listButton: MutableList<ImageButton> by lazy { mutableListOf() }

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

        listButton.add(binding.ibMole1)
        listButton.add(binding.ibMole2)
        listButton.add(binding.ibMole3)
        listButton.add(binding.ibMole4)
        listButton.add(binding.ibMole5)
        listButton.add(binding.ibMole6)
        listButton.add(binding.ibMole7)
        listButton.add(binding.ibMole8)
        listButton.add(binding.ibMole9)
    }

    private fun observeViewModel() {
        with(gameViewModel) {
            formattedTime.observe(viewLifecycleOwner) {
                binding.tvTimer.text = it
            }
            countOfHitsMole.observe(viewLifecycleOwner) {
                binding.tvCountOfHits.text = requireContext().getString(R.string.hits_text, it)
            }
            moleList.observe(viewLifecycleOwner) {
                moleState(it)
            }
            isFinish.observe(viewLifecycleOwner) { isFinish ->
                if (isFinish) {
                    gameResult.observe(viewLifecycleOwner) { gameResult ->
                        launchGameFinishedFragment(gameResult)
                    }
                }
            }
        }
    }

    private fun moleState(list: List<Mole>) {

        listButton.forEachIndexed { index, imageButton ->
            imageButton.isEnabled = list[index].IsActive
            imageButton.setOnClickListener {
                gameViewModel.hittingTheMole(list[index])
            }
        }
    }

    private fun launchGameFinishedFragment(gameResult: GameResult) {
        val pref = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val edt = pref.edit()
        val record = pref.getString(SP_KEY, DEFAULT_VALUE_PREF)

        if (record != null) {
                if (record.toInt() < gameResult.countOfHits) {
                    edt.putString(SP_KEY, gameResult.countOfHits.toString())
                    edt.apply()
                }
        }


        findNavController().navigate(
            GameFragmentDirections.actionGameFragmentToGameFinishedFragment(
                gameResult
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val DEFAULT_VALUE_PREF = "0"
        private const val SP_KEY = "record"
    }
}