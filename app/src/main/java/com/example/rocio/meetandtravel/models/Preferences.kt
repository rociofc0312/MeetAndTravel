package com.example.rocio.meetandtravel.models

import android.content.Context
import android.preference.PreferenceManager

class Preferences(val context: Context){
    companion object {
        val userToken = "userToken"
    }

    val preferences = PreferenceManager.getDefaultSharedPreferences(context)
    var userToken: String? = preferences.getString(
            Preferences.userToken, null)
    set(value) = preferences.edit().putString(
            Preferences.userToken, value).apply()
}