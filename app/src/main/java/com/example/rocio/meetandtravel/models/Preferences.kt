package com.example.rocio.meetandtravel.models

import android.content.Context
import android.preference.PreferenceManager

class Preferences(val context: Context){
    companion object {
        val userToken = "userToken"
        val userId = "userId"
        val time = "time"
    }

    val preferences = PreferenceManager.getDefaultSharedPreferences(context)

    var userToken: String? = preferences.getString(
            Preferences.userToken, "unset")
    set(value) = preferences.edit().putString(
            Preferences.userToken, value).apply()

    var userId: Int = preferences.getInt(
            Preferences.userId, 0)
    set(value) = preferences.edit().putInt(
                Preferences.userId, value).apply()

    var time: Long = preferences.getLong(
            Preferences.time, 0)
    set(value) = preferences.edit().putLong(
                Preferences.time, value).apply()
}