package com.example.gitprojectapp.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.gitprojectapp.domain.repository.SharedPreferenceRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

private const val SHARED_PREFS_NAME = "shared_prefs"
private const val KEY = "token"
private const val DEFAULT_TOKEN = "token"

class SharedPreferencesImpl @Inject constructor(@ApplicationContext val context: Context) : SharedPreferenceRepository {
    val sharedPrefs = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
    override fun saveToken(token: String) {
        sharedPrefs.edit().putString(KEY, token).apply()
    }

    override fun getToken(): String {
        return sharedPrefs.getString(KEY, DEFAULT_TOKEN) ?: DEFAULT_TOKEN
    }
}