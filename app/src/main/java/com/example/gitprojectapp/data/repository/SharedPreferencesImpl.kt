package com.example.gitprojectapp.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.gitprojectapp.domain.models.UserInfo
import com.example.gitprojectapp.domain.repository.SharedPreferenceRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

private const val SHARED_PREFS_NAME = "shared_prefs"
private const val TOKEN_KEY = "token"
private const val NAME_KEY = "name"
private const val DEFAULT_TOKEN = "token"

class SharedPreferencesImpl @Inject constructor(@ApplicationContext val context: Context) : SharedPreferenceRepository {
    val sharedPrefs = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
    override fun saveToken(user: UserInfo, token: String) {
        sharedPrefs.edit().putString(TOKEN_KEY, token).apply()
        sharedPrefs.edit().putString(NAME_KEY, user.name).apply()

    }

    override fun getToken(): String {
        return sharedPrefs.getString(TOKEN_KEY, DEFAULT_TOKEN) ?: DEFAULT_TOKEN
    }

    override fun getName(): String {
        return sharedPrefs.getString(NAME_KEY, DEFAULT_TOKEN) ?: DEFAULT_TOKEN
    }
}