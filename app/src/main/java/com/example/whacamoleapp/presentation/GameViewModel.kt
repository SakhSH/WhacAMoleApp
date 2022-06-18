package com.example.whacamoleapp.presentation

import android.os.CountDownTimer
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.whacamoleapp.domain.entity.GameResult
import com.example.whacamoleapp.domain.entity.Mole
import kotlin.random.Random
import kotlin.random.nextInt

class GameViewModel : ViewModel() {


    private val listMoles: MutableList<Mole> by lazy { mutableListOf() }
    lateinit var gameResult: GameResult
    private var timer: CountDownTimer? = null
    private var timerMoleState: CountDownTimer? = null
    var countHitsMole = 0

    private var _formattedTime = MutableLiveData<String>()
    val formattedTime: LiveData<String>
        get() = _formattedTime

    private var _countOfHitsMole = MutableLiveData<Int>()
    val countOfHitsMole: LiveData<Int>
        get() = _countOfHitsMole

    private var _moleList = MutableLiveData<MutableList<Mole>>()
    val moleList: LiveData<MutableList<Mole>>
        get() = _moleList

    private var _isFinish = MutableLiveData<Boolean>()
    val isFinish: LiveData<Boolean>
        get() = _isFinish

    init {
        _countOfHitsMole.value = 0
        createMoleList()
        startGame()
    }

    private fun startGame() {
        startTimer()
    }

    private fun startTimer() {
        timer = object : CountDownTimer(
            30 * MILLIS_IN_SECONDS,
            MILLIS_IN_SECONDS
        ) {
            override fun onTick(millisUntilFinished: Long) {
                _formattedTime.value = formatTime(millisUntilFinished)
            }

            override fun onFinish() {
                finishGame()
            }
        }
        timer?.start()
    }

    private fun startTimerMoleState() {
        val randomNumber = Random.nextInt(0 until 9)
        listMoles[randomNumber].IsActive = true
        _moleList.value = listMoles

        timerMoleState = object : CountDownTimer(
            1 * MILLIS_IN_SECONDS,
            MILLIS_IN_SECONDS
        ) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                listMoles[randomNumber].IsActive = false
                _moleList.value = listMoles
                randomEnabledMole()
            }
        }
        timerMoleState?.start()
    }

    private fun formatTime(millisUntilFinished: Long): String {
        val seconds = millisUntilFinished / MILLIS_IN_SECONDS
        val minutes = seconds / SECONDS_IN_MINUTES
        val leftSeconds = seconds - (minutes * SECONDS_IN_MINUTES)
        return String.format("%02d:%02d", minutes, leftSeconds)
    }

    private fun checkResult(): Boolean = countHitsMole >= 10

    private fun finishGame() {
        gameResult = GameResult(
            GAME_RESULT_ID,
            checkResult(),
            countHitsMole
        )
        _isFinish.value = true
    }

    private fun createMoleList() {
        for (i in 0 until 9) {
            val item = Mole(
                i,
                false
            )
            listMoles.add(item)
        }
        _moleList.value = listMoles
    }

    fun hittingTheMole(mole: Mole) {
        if (mole.IsActive) {
            countHitsMole++
            _countOfHitsMole.value = countHitsMole
            listMoles[mole.id].IsActive = false
            _moleList.value = listMoles
        }
    }

    fun randomEnabledMole() {
        startTimerMoleState()
    }

    companion object {
        private const val GAME_RESULT_ID = 1
        private const val MILLIS_IN_SECONDS = 1000L
        private const val SECONDS_IN_MINUTES = 60
    }
}