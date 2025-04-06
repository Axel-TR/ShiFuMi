package com.example.shifumiapp

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.core.content.edit

class GameViewModel : ViewModel(){
    var strategy by mutableStateOf(false)
}

class GamePreferences(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("game_preferences", Context.MODE_PRIVATE)

    private val winStreakKey = "win_streak"

    fun getWinStreak(): Int {
        return sharedPreferences.getInt(winStreakKey, 0)
    }

    fun setWinStreak(winStreak: Int) {
        sharedPreferences.edit() { putInt(winStreakKey, winStreak) }
    }

    fun getBestWinStreak(): Int {
        return sharedPreferences.getInt("best_win_streak", 0)
    }


    fun setBestWinStreak(streak: Int) {
        sharedPreferences.edit().putInt("best_win_streak", streak).apply()
    }
}
