package com.example.gitprojectapp.domain.repository

import com.example.gitprojectapp.domain.models.UserInfo

interface SharedPreferenceRepository {
    fun saveToken(user: UserInfo, token: String)
    fun getToken(): String
    fun getName(): String

    fun clearSharedPreferences()

}