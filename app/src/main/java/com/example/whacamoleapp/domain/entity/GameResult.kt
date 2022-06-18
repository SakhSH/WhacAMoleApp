package com.example.whacamoleapp.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameResult(

    val id: Int,
    var winner: Boolean,
    var countOfHits: Int
) : Parcelable
